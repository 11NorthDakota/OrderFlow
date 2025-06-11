package by.northdakota.inventoryservice.Service;

import by.northdakota.inventoryservice.Entity.InventoryItem;
import by.northdakota.inventoryservice.Entity.OrderItem;
import by.northdakota.inventoryservice.Entity.OrderStatus;
import by.northdakota.inventoryservice.Event.OrderEventImpl.CreateOrderEvent;
import by.northdakota.inventoryservice.Event.ProductEventImpl.CreateProductEvent;
import by.northdakota.inventoryservice.Exception.AlreadyExistsException;
import by.northdakota.inventoryservice.Exception.NotFoundException;
import by.northdakota.inventoryservice.Repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;

    public Long createInventoryItem(CreateProductEvent productEvent){
        Long productId = productEvent.getId();
        if(inventoryItemRepository.existsById(productId)){
            throw new AlreadyExistsException("Product already exists!");
        }
        InventoryItem item = new InventoryItem();
        item.setProductId(productId);
        item.setStock(10);
        item = inventoryItemRepository.save(item);
        log.info("product with productId={},saved", productId);
        return item.getId();
    }

    public void deleteInventoryItem(Long productId){
        if(!inventoryItemRepository.existsByProductId(productId)){
           throw new NotFoundException("Product not found!");
        }
        inventoryItemRepository.deleteByProductId(productId);
        log.info("product with productId={},deleted", productId);
    }

    public CreateOrderEvent createOrder(CreateOrderEvent orderEvent){
        List<OrderItem> orderItems = orderEvent.getItems();
        Map<Long, Boolean> result = orderItems.stream()
                .collect(Collectors.toMap(
                        OrderItem::getProductId,
                        this::checkOrderItem,
                        (a, b) -> a && b
                ));
        if (result.containsValue(false)) {
            orderEvent.setStatus(OrderStatus.FAILED);
        } else {
            orderEvent.setStatus(OrderStatus.RESERVED);
        }
        return orderEvent;
    }

    private boolean checkOrderItem(OrderItem orderItem){
        InventoryItem optionalOrderItem = inventoryItemRepository
                .findByProductId(orderItem.getProductId()).get();
        return optionalOrderItem.getStock() >= orderItem.getQuantity();
    }
}
