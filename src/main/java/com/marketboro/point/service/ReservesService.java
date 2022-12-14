package com.marketboro.point.service;

import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.request.SaveReservesReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
