package com.marketboro.point.domain.reserves;

import com.marketboro.point.domain.BaseTimeEntity;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(
//    uniqueConstraints = {
//        @UniqueConstraint(name = "uni_member_1", columnNames = {"uid", "provider"}),
//        @UniqueConstraint(name = "uni_member_2", columnNames = {"name"})
//    }
)
public class Reserves extends BaseTimeEntity {

    @Column(nullable = false, length = 32)
    private String memberId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private ReservesStatus status;

    @Column(nullable = false)
    private Long expiredAt;

    @OneToMany(mappedBy = "reserves")
    private List<ReservesHistory> reservesHistoryList = new ArrayList<>();

    public static Reserves of(SaveReservesReq saveReservesReq) {
        return Reserves.builder()
                .memberId(saveReservesReq.getMemberId())
                .amount(saveReservesReq.getAmount())
                .balance(saveReservesReq.getAmount())
                .expiredAt(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .status(ReservesStatus.UNUSED)
                .build();
    }

    public void updateBalance(Long balance) {
        this.balance = balance;
    }

    public void useReserves() {
        this.balance = 0L;
        this.status = ReservesStatus.USED;
    }

    public void updateStatus(ReservesStatus stauts) {
        this.status = stauts;
    }

    public void unused() {
        this.balance = this.amount;
        this.status = ReservesStatus.UNUSED;
    }

}
