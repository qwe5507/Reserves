package com.marketboro.point.domain.reserves.repository;

import com.marketboro.point.domain.member.Member;
import com.marketboro.point.domain.member.MemberProvider;
import com.marketboro.point.domain.reserves.QReserves;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.marketboro.point.domain.member.QMember.member;
import static com.marketboro.point.domain.reserves.QReserves.reserves;

@RequiredArgsConstructor
public class ReservesRepositoryCustomImpl implements ReservesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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

}
