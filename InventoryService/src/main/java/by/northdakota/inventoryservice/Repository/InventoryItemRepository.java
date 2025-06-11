package by.northdakota.inventoryservice.Repository;

import by.northdakota.inventoryservice.Entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    Optional<InventoryItem> findByProductId(Long productId);
    boolean existsByProductId(Long productId);
    void deleteByProductId(Long productId);


}
