package com.marketboro.point.service;

import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservesService {

    private final ReservesRepository reservesRepository;
    private final ReservesHistoryRepository reservesHistoryRepository;
    private final HistoryRepository historyRepository;

    public void savedReserves(SaveReservesReq saveReservesReq) {
        Reserves savedReserves = reservesRepository.save(Reserves.of(saveReservesReq));
        History savedHistory = historyRepository.save(History.of(saveReservesReq));

        reservesHistoryRepository.save(ReservesHistory.of(savedReserves, savedHistory));
    }

    public Long getReservesTotal(String memberId) {
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<Reserves> reservesList = reservesRepository.findAllByMemberIdAndStatusAndNotExpired(memberId, ReservesStatus.UNUSED, nowTime);

        if (reservesList.isEmpty()) {
            return 0L;
        }

        return reservesList.stream().mapToLong(i -> i.getBalance().longValue()).sum();
    }
}
