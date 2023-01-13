package com.marketboro.point.service;

import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservesServiceTest {
    @InjectMocks
    private ReservesService reservesService;

    @Mock
    private ReservesRepository reservesRepository;

    @Mock
    private ReservesHistoryRepository reservesHistoryRepository;

    @Mock
    private HistoryRepository historyRepository;


    /*
            Reserves savedReserves = reservesRepository.save(Reserves.of(saveReservesReq));
        History history = History.of(saveReservesReq.getMemberId(), saveReservesReq.getAmount(), HistoryType.SAVE, false);
        History savedHistory = historyRepository.save(history);

        reservesHistoryRepository.save(ReservesHistory.of(savedReserves, savedHistory));
     */
    @Test
    @Disabled
    public void savedReserves() {

        reservesService.savedReserves(new SaveReservesReq("qwe5507", 1000L));

        verify(reservesRepository,times(1)).save(argThat(new IsReservesWillBeInserted()));
    }

    private static class IsReservesWillBeInserted implements ArgumentMatcher<Reserves> {
        @Override
        public boolean matches(Reserves reserves) {
            return equals(reserves.getMemberId(),"qwe5507")
                    && equals(reserves.getAmount(),1000L);
        }
        private boolean equals(Object actual,Object expected){
            return expected.equals(actual);
        }
    }

    @Test
    @DisplayName("적립금 합계 조회 시, 적립 내역이 없을 떄")
    public void 적립금_합계_조회_시_적립내역이_없을때() {
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String memberId = "1";

        when(reservesRepository.findAllByMemberIdAndStatusAndNotExpired("1", ReservesStatus.UNUSED, 2L))
                .thenReturn(new ArrayList<Reserves>());

        assertThat(reservesService.getReservesTotal("1")).isEqualTo(0L);
    }

    @Test
    @DisplayName("적립금 합계 조회 시, 적립 내역이 있을 때")
    public void 적립금_합계_조회_시_적립내역이_있을때() {
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String memberId = "1";

        Reserves reserves1 = Reserves.builder()
                .memberId("1")
                .amount(1L)
                .build();

        Reserves reserves2 = Reserves.builder()
                .memberId("1")
                .amount(2L)
                .build();

        when(reservesRepository.findAllByMemberIdAndStatusAndNotExpired("1", ReservesStatus.UNUSED, nowTime))
                .thenReturn(Arrays.asList(reserves1, reserves2));

        assertThat(reservesService.getReservesTotal("1")).isEqualTo(3L);
    }
}