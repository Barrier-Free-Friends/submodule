package org.bf.global.domain.event;

public interface DomainEvent {
    /**
     * 도메인 이벤트 발생 시 필수적으로 기입해야 하는 내용 정의
     * - EventId : 이벤트의 고유 ID (Kafka key로 사용_
     * - OccurredAt : 이벤트 발생 시각
     * - SourceService : 이벤트 발행 서비스
     * - TopicName : 토픽 이름
     * */
    String getEventId();
    String getOccurredAt();
    String getSourceService();
    String getTopicName();

    /**
     * 토픽 이름을 제외하고 DomainEventBuilder에서 자동으로 값 주입
     * */
    void setEventId(String eventId);
    void setOccurredAt(String occurredAt);
    void setSourceService(String sourceService);
}
