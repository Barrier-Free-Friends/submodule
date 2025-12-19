package org.bf.global.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bf.global.domain.event.AbstractDomainEvent;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportMapInfoEvent extends AbstractDomainEvent {
    private UUID userId;
    private String tagCode;
    private List<ReportMapImageInfo> images;
    private UUID sourceId;
    private String sourceTable;

    @Override
    public String getTopicName() { return "map-events"; }
}
