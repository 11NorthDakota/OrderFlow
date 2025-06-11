package by.northdakota.inventoryservice.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
    @Bean
    NewTopic inventoryEventTopic(){
        return TopicBuilder
                .name("inventory-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
