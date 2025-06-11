package by.northdakota.notificationservice.Handler;

import by.northdakota.notificationservice.Event.OrderEventImpl.CreateOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "inventory-event",containerFactory = "listenerContainerFactory")
@Slf4j
public class InventoryEventHandler {

    @KafkaHandler
    public void createOrderEvent(CreateOrderEvent event){
      log.info("Create order event accepted,event:{}",event);
      log.info(event.getStatus().toString());
    }

}
