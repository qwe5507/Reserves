package com.marketboro.point.domain.reserves;

import com.marketboro.point.domain.BaseTimeEntity;
import com.marketboro.point.domain.member.DeleteMember;
import com.marketboro.point.domain.member.Email;
import com.marketboro.point.domain.member.MemberProvider;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.dto.enums.ReservesStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private ReservesStatus status;

    @Column(nullable = false)
    private Long expiredAt;

    @OneToMany(mappedBy = "reserves")
    private List<ReservesHistory> reservesHistoryList = new ArrayList<>();
}
