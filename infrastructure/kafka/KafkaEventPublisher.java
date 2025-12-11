package org.bf.global.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bf.global.domain.event.DomainEvent;
import org.bf.global.domain.event.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 표준 이벤트 발행
     * */
    @Override
    public void publish(DomainEvent event) {
        String topic = event.getTopicName();
        String key = event.getEventId();

        this.publishMessage(topic, key, event);
    }

    @Override
    public void publish(String topicName, String key, Object payload) {
        this.publishMessage(topicName, key, payload);
    }

    /**
     * 실제 Kafka 전송 로직 및 콜백 처리 (CompletableFuture 기반 비동기 처리)
     */
    private void publishMessage(String topic, String key, Object payload) {
        // 비동기 전송: KafkaTemplate.send()는 CompletableFuture를 반환
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, payload);

        // 전송 결과에 대한 콜백 리스너 등록
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event published. Topic: {}, Key: {}, Offset: {}",
                        topic, key, result.getRecordMetadata().offset());
            } else {
                log.info("Failed to publish event. Topic: {}, Key: {}, Reason: {}",
                        topic, key, ex.getMessage());
                // TODO: 재시도, DLQ, 알림 등 실패 처리 로직 구현
            }
        });
    }
}
