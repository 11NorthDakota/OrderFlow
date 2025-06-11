package by.northdakota.notificationservice.Event.OrderEventImpl;

import by.northdakota.notificationservice.Entity.OrderItem;
import by.northdakota.notificationservice.Entity.OrderStatus;
import by.northdakota.notificationservice.Event.OrderEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
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
