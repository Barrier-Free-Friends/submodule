package org.bf.global.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractDomainEvent implements DomainEvent {
    @JsonProperty
    protected String eventId;
    @JsonProperty
    protected String occurredAt;
    @JsonProperty
    protected String sourceService;

    @Override
    public String getEventId() { return eventId; }
    @Override
    public void setEventId(String eventId) { this.eventId = eventId; }

    @Override
    public String getOccurredAt() { return occurredAt; }
    @Override
    public void setOccurredAt(String occurredAt) { this.occurredAt = occurredAt; }

    @Override
    public String getSourceService() { return sourceService; }
    @Override
    public void setSourceService(String sourceService) { this.sourceService = sourceService; }

    /**
     * 각 서비스에서 구현해야 하는 메서드
     * */
    @Override
    @JsonIgnore // Kafka Payload에 토픽 이름이 중복 포함되는 것을 방지
    public abstract String getTopicName();
}
