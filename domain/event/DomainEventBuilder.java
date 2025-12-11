package org.bf.global.domain.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DomainEventBuilder {

    // Spring 환경 설정에서 주입받을 서비스 이름
    private final String currentServiceName;

    public DomainEventBuilder(String currentServiceName) {
        this.currentServiceName = currentServiceName;
    }

    /**
     * AbstractDomainEvent를 상속받은 DTO에 필수 메타데이터를 자동 주입
     *
     * @param event Payload만 채워진 DTO 객체
     * @param <T> AbstractDomainEvent를 상속받은 타입
     * @return 메타데이터가 채워진 T 타입의 DTO
     */
    public <T extends AbstractDomainEvent> T build(T event) {

        // 이벤트 키 자동 생성
        event.setEventId(UUID.randomUUID().toString());

        // 이벤트 발생 시각 자동 생성 (ISO 8601 형식)
        event.setOccurredAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // 출처 서비스 이름 자동 주입
        event.setSourceService(this.currentServiceName);

        return event;
    }
}
