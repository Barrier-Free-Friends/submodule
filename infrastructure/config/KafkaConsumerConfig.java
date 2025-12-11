package org.bf.global.infrastructure.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    // Spring Boot의 Kafka 설정을 자동으로 주입
    public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * 범용 Object 타입 Consumer Factory 정의
     * Value Deserializer는 JsonDeserializer를 사용하며, 타입 헤더를 읽어 DTO를 매핑
     */
    @Bean(name = "genericKafkaListenerContainerFactory") // 명확한 Bean 이름 지정
    public ConcurrentKafkaListenerContainerFactory<String, Object> genericKafkaListenerContainerFactory() {

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();

        // 역직렬화할 때 헤더 정보를 활용하도록 설정 (Producer에서 전송한 __TypeId__를 읽음)
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);

        // Value Default Type은 Object로 설정 (헤더를 통해 실제 DTO로 변환)
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class.getName());

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        // ConsumerFactory 생성
        ConsumerFactory<String, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(props);

        // ContainerFactory 생성
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}