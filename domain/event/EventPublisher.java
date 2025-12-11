package org.bf.global.domain.event;

public interface EventPublisher {
    /**
     * 표준 이벤트 발행 메서드
     * 메시지 키는 DomainEventBuilder가 자동 주입한 EventId(UUID)를 사용
     *
     * @param event AbstractDomainEvent를 상속받은 DTO 객체 (Payload와 메타데이터 포함)
     */
    void publish(DomainEvent event);

    /**
     * 순서 보장 등 특수 목적으로 메시지 키를 명시해야 할 때 사용하는 오버로드 메서드
     *
     * @param topicName 발행할 대상 토픽 이름
     * @param key       메시지 키 (특정 파티션 지정을 위해 사용, 예: User ID, Order ID)
     * @param payload   발행할 실제 객체 (Payload)
     */
    void publish(String topicName, String key, Object payload);
}
