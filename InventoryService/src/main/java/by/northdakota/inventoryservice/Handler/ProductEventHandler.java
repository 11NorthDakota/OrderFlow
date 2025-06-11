package by.northdakota.inventoryservice.Handler;

import by.northdakota.inventoryservice.Event.ProductEventImpl.CreateProductEvent;
import by.northdakota.inventoryservice.Event.ProductEventImpl.DeleteProductEvent;
import by.northdakota.inventoryservice.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics="product-event",containerFactory = "listenerContainerFactory")
@Slf4j
@RequiredArgsConstructor
public class ProductEventHandler {

    private final InventoryService inventoryService;

    @KafkaHandler
    public void productCreateEventHandle(CreateProductEvent event){
        log.info("Create product event accepted, event:{}",event);
        Long id = inventoryService.createInventoryItem(event);
        log.info("Creation event completed, inventory item id:{}",id);
    }

    @KafkaHandler
    public void deleteProductEventHandle(DeleteProductEvent event){
        log.info("Delete product event accepted, event:{}",event);
        inventoryService.deleteInventoryItem(event.getId());
        log.info("Delete product event completed");
    }
}
