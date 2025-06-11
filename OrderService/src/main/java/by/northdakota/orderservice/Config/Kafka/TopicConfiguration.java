package by.northdakota.orderservice.Config.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
    @Bean
    NewTopic createNewTopic(){
        return TopicBuilder
                .name("order-event")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
