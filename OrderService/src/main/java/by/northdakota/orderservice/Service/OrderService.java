package by.northdakota.orderservice.Service;

import by.northdakota.orderservice.Dto.OrderDto;
import by.northdakota.orderservice.Dto.ProductValidationRequest;
import by.northdakota.orderservice.Dto.ProductValidationResult;
import by.northdakota.orderservice.Entity.Order;
import by.northdakota.orderservice.Entity.OrderItem;
import by.northdakota.orderservice.Entity.OrderStatus;
import by.northdakota.orderservice.Event.OrderEventImpl.CreateOrderEvent;
import by.northdakota.orderservice.Exception.NotFoundException;
import by.northdakota.orderservice.Mapper.OrderMapper;
import by.northdakota.orderservice.Repository.OrderItemRepository;
import by.northdakota.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final WebClient productServiceClient;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long createOrder(OrderDto orderDto) {
        Order order = new Order();
        List<OrderItem> orderItem = orderMapper.toEntity(orderDto.getItems());
        orderItem = orderItemRepository.saveAll(orderItem);
        order.setCustomerEmail(orderDto.getCustomerEmail());
        order.setItems(orderItem);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        ProductValidationResult result = validateProducts(orderItem);
        if(!result.isAllExist()){
            throw new NotFoundException("Products with id= "+result.getMissingProductIds()+" not found");
        }
        order = orderRepository.save(order);
        CreateOrderEvent event = orderMapper.toCreateOrderEvent(order);
        eventPublisher.publishEvent(event);
        log.info("Order created: {}", order);
        return order.getId();
    }

    @Transactional
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = orderMapper.toListDto(orders);
        log.info("Orders count found: {}", orders.size());
        return orderDtos;
    }

    @Transactional
    public OrderDto getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new NotFoundException("Order with id= "+orderId+" not found");
        }
        log.info("Order found: {}", order);
        return orderMapper.toDto(order.get());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishProductCreateEvent(CreateOrderEvent event) {
        kafkaTemplate.send("order-event", String.valueOf(event.getId()), event);
    }

    @SneakyThrows
    private ProductValidationResult validateProducts(List<OrderItem> orderItems){

        List<Long> productIds = orderItems.stream()
                .map(OrderItem::getProductId)
                .toList();

        ProductValidationResult response;

        try {
            response = productServiceClient
                    .post()
                    .uri("/products/validate")
                    .bodyValue(new ProductValidationRequest(productIds))
                    .retrieve()
                    .bodyToMono(ProductValidationResult.class)
                    .block(); // Блокируем для синхронного выполнения

            if (response != null && !response.isAllExist()) {
                List<Long> missingProducts = response.getMissingProductIds();
                throw new NotFoundException("Products not found: " + missingProducts);
            }

        } catch (WebClientException e) {
            log.error("Failed to validate products: {}", e.getMessage());
            throw new ServiceUnavailableException("Product service is unavailable");
        }
        return response;
    }
}
