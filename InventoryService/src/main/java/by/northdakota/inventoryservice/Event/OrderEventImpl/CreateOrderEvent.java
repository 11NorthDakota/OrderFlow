package by.northdakota.inventoryservice.Event.OrderEventImpl;

import by.northdakota.inventoryservice.Entity.OrderItem;
import by.northdakota.inventoryservice.Entity.OrderStatus;
import by.northdakota.inventoryservice.Event.OrderEvent;
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
