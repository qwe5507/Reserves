package com.marketboro.point.domain.reserves.repository;

import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.marketboro.point.domain.reserves.QReserves.reserves;

@RequiredArgsConstructor
public class ReservesRepositoryCustomImpl implements ReservesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Reserves> findAllByMemberIdAndStatusAndNotExpired(String memberId, ReservesStatus reservesStatus, Long nowTime) {

        return queryFactory.selectFrom(reserves)
            .where(
                    reserves.memberId.eq(memberId),
                    reserves.status.eq(reservesStatus),
                    reserves.expiredAt.gt(nowTime)
            ).orderBy(reserves.expiredAt.asc())
            .fetch();
    }

    @Override
    public List<Reserves> findAllByInIdAndNotExpired(List<Long> reserveIdList) {

        return queryFactory.selectFrom(reserves)
                .where(
                        reserves.id.in(reserveIdList)
                ).orderBy(reserves.expiredAt.asc())
                .fetch();
    }

    @Override
    public long updateExpiredReservesByExpiredAt(Long expiredAt) {

        long result = queryFactory.update(reserves)
                .set(reserves.status, ReservesStatus.EXPIRE)
                .where(reserves.expiredAt.lt(expiredAt))
                .execute();

        //벌크 연산 후엔 영속성 컨텍스트 초기화
        em.clear();

        return result;

    }

}
