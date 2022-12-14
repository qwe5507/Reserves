package com.marketboro.point.domain.reserves_history;

import com.marketboro.point.domain.reserves.Reserves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservesHistoryRepository extends JpaRepository<ReservesHistory, Long> {
}
