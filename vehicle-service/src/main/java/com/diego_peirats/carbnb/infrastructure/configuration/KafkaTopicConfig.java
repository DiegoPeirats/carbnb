package com.diego_peirats.carbnb.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic offerTopic() {
        return new NewTopic("offer-topic", 1, (short) 1);
    }
}