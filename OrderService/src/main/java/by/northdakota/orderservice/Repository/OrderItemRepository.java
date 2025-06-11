package by.northdakota.orderservice.Repository;

import by.northdakota.orderservice.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
