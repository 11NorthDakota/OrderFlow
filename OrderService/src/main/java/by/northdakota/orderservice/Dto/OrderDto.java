package by.northdakota.orderservice.Dto;

import by.northdakota.orderservice.Entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private String customerEmail;
    private List<OrderItemDto> items;
}
