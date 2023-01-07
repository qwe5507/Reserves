package com.marketboro.point.domain.reserves_history;

import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.reserves.Reserves;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = @Index(name = "idx_history_id", columnList = "history_id", unique = false))
@ToString
public class ReservesHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserves_id")
    private Reserves reserves;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    public static ReservesHistory of(Reserves reserves, History history) {
        return ReservesHistory.builder()
                .reserves(reserves)
                .history(history)
                .build();
    }
}
