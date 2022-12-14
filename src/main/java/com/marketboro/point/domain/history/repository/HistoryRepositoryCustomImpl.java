package com.marketboro.point.domain.history.repository;

import com.marketboro.point.domain.history.projection.HistoryProjection;
import com.marketboro.point.domain.history.projection.QHistoryProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.marketboro.point.domain.history.QHistory.history;

@RequiredArgsConstructor
public class HistoryRepositoryCustomImpl implements HistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<HistoryProjection> findAllByMemberId(String memberId, Pageable pageable) {
        List<HistoryProjection> list = queryFactory
                .select(new QHistoryProjection(
                        history.id,
                        history.type,
                        history.amount,
                        history.isCanceled,
                        history.createAt))
                .where(history.memberId.eq(memberId))
                .from(history)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if(list.isEmpty()) return new PageImpl<>(list, pageable, 0);

        List<Long> totalCount = queryFactory
                .select(history.id)
                .from(history)
                .where(history.memberId.eq(memberId))
                .fetch();

        return new PageImpl<>(list, pageable, totalCount.size());
    }
}
