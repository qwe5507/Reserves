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
import com.marketboro.point.dto.request.CancelReq;
import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.dto.request.UseReservesReq;
import com.marketboro.point.exception.ConflictException;
import com.marketboro.point.exception.InvalidException;
import com.marketboro.point.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    public void cancel(CancelReq cancelReq) {
        History history = historyRepository.findById(cancelReq.getHistoryId())
                .orElseThrow(() -> new NotFoundException("History 없음"));

        if (history.isCanceled()) {
            throw new ConflictException("이미 취소된 History 입니다");
        }

        if (HistoryType.USE != history.getType()) {
            throw new InvalidException("취소 할수 없는 Type History 입니다.");
        }

        List<ReservesHistory> reserveHistoryList = reservesHistoryRepository.findAllByHistoryId(history.getId());
        log.info("reserveHistoryList : {}", reserveHistoryList);

        List<Long> reserveIdList = reserveHistoryList.stream().map(ReservesHistory -> ReservesHistory.getReserves().getId()).collect(Collectors.toList());

        List<Reserves> cancelReservesList = reservesRepository.findAllByInIdAndNotExpired(reserveIdList);

        Long remainCancelAmount = history.getAmount();
        for (Reserves reserves : cancelReservesList) {
            if (ReservesStatus.EXPIRE == reserves.getStatus()) {
                throw new InvalidException("취소 기한이 지났습니다.");
            }
            Long tempAmount = reserves.getBalance() + remainCancelAmount;

            if (tempAmount < reserves.getAmount()) {
                reserves.updateBalance(remainCancelAmount + reserves.getBalance());
            } else {
                reserves.unused();
                remainCancelAmount = tempAmount - reserves.getAmount();
            }

            if (reserves.getAmount() == reserves.getBalance()) {
                reserves.updateStatus(ReservesStatus.UNUSED);
            }
        }
        history.isCancle();
    }
}
