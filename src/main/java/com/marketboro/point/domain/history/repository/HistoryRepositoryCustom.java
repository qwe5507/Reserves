package com.marketboro.point.domain.history.repository;

import com.marketboro.point.domain.history.projection.HistoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryRepositoryCustom {
    Page<HistoryProjection> findAllByMemberId(String memberId, Pageable pageable);
}
