package by.northdakota.orderservice.Event.OrderEventImpl;

import by.northdakota.orderservice.Entity.OrderItem;
import by.northdakota.orderservice.Entity.OrderStatus;
import by.northdakota.orderservice.Event.OrderEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderEvent implements OrderEvent {
    private Long id;
    private String customerEmail;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private List<OrderItem> items;
}
