package com.marketboro.point.domain.reserves_history;

import com.marketboro.point.domain.BaseTimeEntity;
import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.reserves.Reserves;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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

}
