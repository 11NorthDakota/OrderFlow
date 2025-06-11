package by.northdakota.orderservice.Mapper;

import by.northdakota.orderservice.Dto.OrderDto;
import by.northdakota.orderservice.Dto.OrderItemDto;
import by.northdakota.orderservice.Entity.Order;
import by.northdakota.orderservice.Entity.OrderItem;
import by.northdakota.orderservice.Event.OrderEventImpl.CreateOrderEvent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);
    List<OrderItem> toEntity(List<OrderItemDto> orderItemDto);
    OrderItemDto toDto(OrderItem orderItem);
    CreateOrderEvent toCreateOrderEvent(Order order);
    List<OrderDto> toListDto(List<Order> orders);
}
