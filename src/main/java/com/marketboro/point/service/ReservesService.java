package com.marketboro.point.service;

import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.history.projection.HistoryProjection;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.enums.ErrorAction;
import com.marketboro.point.dto.enums.ErrorCode;
import com.marketboro.point.dto.enums.HistoryType;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.dto.request.UseReservesReq;
import com.marketboro.point.exception.InvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
        History history = History.of(saveReservesReq.getMemberId(), saveReservesReq.getAmount(), HistoryType.SAVE, false);
        History savedHistory = historyRepository.save(history);

        reservesHistoryRepository.save(ReservesHistory.of(savedReserves, savedHistory));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Long getReservesTotal(String memberId) {
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<Reserves> reservesList = reservesRepository.findAllByMemberIdAndStatusAndNotExpired(memberId, ReservesStatus.UNUSED, nowTime);

        if (reservesList.isEmpty()) {
            return 0L;
        }

        return reservesList.stream().mapToLong(i -> i.getBalance()).sum();
    }

    public void useReserves(UseReservesReq useReservesReq) {

        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<Reserves> reservesList = reservesRepository.findAllByMemberIdAndStatusAndNotExpired(useReservesReq.getMemberId(), ReservesStatus.UNUSED, nowTime);

        Long totalReserves = reservesList.stream().mapToLong(i -> i.getBalance()).sum();
        Long useReserves = useReservesReq.getAmount();

        // 적립금이 부족 할 떄
        if (totalReserves < useReserves) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("적립금이 부족합니다. [부족적립금 : %s]", useReserves - totalReserves));
        }

        // History add //
        History history = History.of(useReservesReq.getMemberId(), useReservesReq.getAmount(), HistoryType.USE, false);
        History savedHistory = historyRepository.save(history);

        List<Reserves> usedReservesList = new ArrayList<>();
        // Reservers Update
        for (Reserves reserves : reservesList) {
            Long balance = reserves.getBalance();

            usedReservesList.add(reserves);
            if (useReserves >= balance) {
                reserves.useReserves();
            } else {
                reserves.updateBalance(balance - useReserves);
                break;
            }

            useReserves = useReserves - balance;
        }

        // Reservers History add
        for (Reserves reserves : usedReservesList) {
            reservesHistoryRepository.save(ReservesHistory.of(reserves, savedHistory));
        }
    }

    public Page<HistoryProjection> getReservesList(String memberId, Pageable pageable){
        return historyRepository.findAllByMemberId(memberId, pageable);
    }
}
