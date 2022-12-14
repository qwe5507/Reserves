package com.marketboro.point.domain.history;

import com.marketboro.point.domain.history.repository.HistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long>, HistoryRepositoryCustom {
}
