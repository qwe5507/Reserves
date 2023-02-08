package com.marketboro.point.domain.reserves.repository;

import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.dto.enums.ReservesStatus;

import java.util.List;

public interface ReservesRepositoryCustom {

    List<Reserves> findAllByMemberIdAndStatusAndNotExpired(String memberId, ReservesStatus reservesStatus, Long nowTime);

    List<Reserves> findAllByInIdAndNotExpired(List<Long> reserveIdList);

    long updateExpiredReservesByExpiredAt(Long expiredAt);
}
