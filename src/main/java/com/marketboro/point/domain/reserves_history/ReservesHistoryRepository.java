package com.marketboro.point.domain.reserves_history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservesHistoryRepository extends JpaRepository<ReservesHistory, Long> {

    public List<ReservesHistory> findAllByHistoryId(Long id);
}
