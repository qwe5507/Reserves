package com.marketboro.point.domain.history;

import com.marketboro.point.domain.reserves_history.ReservesHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
