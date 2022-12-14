package com.marketboro.point.domain.history;

import com.marketboro.point.domain.BaseTimeEntity;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.dto.enums.HistoryType;
import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.dto.request.UseReservesReq;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class History extends BaseTimeEntity {

    @Column(nullable = false, length = 32)
    private String memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private HistoryType type;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Boolean isCanceled;

    @OneToMany(mappedBy = "history")
    private List<ReservesHistory> reservesHistoryList = new ArrayList<>();

    public static History of(String memberId, Long amount, HistoryType type, Boolean isCanceled) {
        return History.builder()
                .type(type)
                .amount(amount)
                .isCanceled(isCanceled)
                .memberId(memberId)
                .build();
    }

}
