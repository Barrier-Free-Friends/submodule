package org.bf.global.infrastructure.config;

import org.bf.global.domain.event.DomainEventBuilder;
import org.bf.global.domain.event.EventPublisher;
import org.bf.global.infrastructure.kafka.KafkaEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig {
    @Value("${spring.application.name:unknown-service}")
    private String currentServiceName;

    @Bean
    @ConditionalOnMissingBean(DomainEventBuilder.class)
    public DomainEventBuilder domainEventBuilder() {
        return new DomainEventBuilder(currentServiceName);
    }

    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventPublisher(kafkaTemplate);
    }
}
