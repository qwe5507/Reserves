package com.marketboro.point.domain.history.projection;

import com.marketboro.point.dto.enums.HistoryType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class HistoryProjection {

    private final Long id;
    private final HistoryType type;
    private final Long amount;
    private final Boolean isCanceled;

    private final LocalDateTime createAt;

    @QueryProjection
    public HistoryProjection(Long id, HistoryType type, Long amount, Boolean isCanceled, LocalDateTime createAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.isCanceled = isCanceled;
        this.createAt = createAt;
    }
}
