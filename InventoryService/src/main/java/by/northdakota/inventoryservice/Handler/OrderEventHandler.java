package by.northdakota.inventoryservice.Handler;

import by.northdakota.inventoryservice.Event.OrderEventImpl.CreateOrderEvent;
import by.northdakota.inventoryservice.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@KafkaListener(topics = "order-event",containerFactory = "listenerContainerFactory")
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final InventoryService inventoryService;

    @KafkaHandler
    public void crateOrderEventHandle(CreateOrderEvent event){
        log.info("Order event accepted, event:{}",event);
        CreateOrderEvent checkedOrder = inventoryService.createOrder(event);
        kafkaTemplate.send("inventory-event", checkedOrder);
        log.info("Inventory event send, event:{}",checkedOrder);
    }
}
