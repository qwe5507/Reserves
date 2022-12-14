package com.marketboro.point.domain.history;

import com.marketboro.point.domain.BaseTimeEntity;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.dto.enums.HistoryType;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class History extends BaseTimeEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private HistoryType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean isCanceled;

    @OneToMany(mappedBy = "history")
    private List<ReservesHistory> reservesHistoryList = new ArrayList<>();

    public static History of(SaveReservesReq saveReservesReq) {
        return History.builder()
                .type(HistoryType.SAVE)
                .amount(saveReservesReq.getAmount())
                .isCanceled(false)
                .build();
    }

}
