package by.northdakota.inventoryservice.Config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfiguration {

    @Value("${spring.kafka.consumer.bootstrap-servers:localhost:9091}")
    private String bootstrapServer;

    Map<String,Object> consumerConfig(){
        Map<String, Object> conf = new HashMap<>();
        conf.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        conf.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        conf.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        conf.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-service");
        conf.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        conf.put(JsonDeserializer.TYPE_MAPPINGS,
                "by.northdakota.productservice.Event.ProductEventImpl.CreateProductEvent:" +
                        "by.northdakota.inventoryservice.Event.ProductEventImpl.CreateProductEvent," +
                        "by.northdakota.productservice.Event.ProductEventImpl.DeleteProductEvent:" +
                        "by.northdakota.inventoryservice.Event.ProductEventImpl.DeleteProductEvent," +
                        "by.northdakota.orderservice.Event.OrderEventImpl.CreateOrderEvent:"+
                        "by.northdakota.inventoryservice.Event.OrderEventImpl.CreateOrderEvent"
                );
        return conf;
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Object> listenerContainerFactory(
            ConsumerFactory<String,Object> consumerFactory){
       ConcurrentKafkaListenerContainerFactory<String,Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
       factory.setConsumerFactory(consumerFactory);
       return factory;
    }

}
