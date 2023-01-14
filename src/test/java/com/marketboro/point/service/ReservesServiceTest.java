package com.marketboro.point.service;

import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.enums.HistoryType;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @DisplayName("적립금 적립")
    public void savedReserves() {
        // given
        SaveReservesReq saveReservesReq = new SaveReservesReq("qwe5507", 1000L);
        Reserves reserves = Reserves.of(saveReservesReq);
        History history = History.of(saveReservesReq.getMemberId(), saveReservesReq.getAmount(), HistoryType.SAVE, false);

        // mocking
        BDDMockito.given(reservesRepository.save(any(Reserves.class))).willReturn(reserves);
        BDDMockito.given(historyRepository.save(any(History.class))).willReturn(history);

        // when
        reservesService.savedReserves(saveReservesReq);

        // then
        verify(reservesRepository,times(1)).save(argThat(new IsReservesWillBeInserted()));
    }

    private static class IsReservesWillBeInserted implements ArgumentMatcher<Reserves> {
        @Override
        public boolean matches(Reserves reserves) {
            return equals(reserves.getMemberId(),"qwe5507")
                    && equals(reserves.getAmount(), 1000L)
                    && equals(reserves.getBalance(), 1000L)
                    && equals(reserves.getStatus(), ReservesStatus.UNUSED);
        }
        private boolean equals(Object actual,Object expected){
            return expected.equals(actual);
        }
    }

    @Test
    @DisplayName("적립금 합계 조회 시, 적립 내역이 없을 떄")
    public void 적립금_합계_조회_시_적립내역이_없을때() {
        // given
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String memberId = "1";

        // mocking
        when(reservesRepository.findAllByMemberIdAndStatusAndNotExpired("1", ReservesStatus.UNUSED, nowTime))
                .thenReturn(new ArrayList<Reserves>());

        // when
        Long reservesTotal = reservesService.getReservesTotal(memberId, nowTime);

        // then
        assertThat(reservesTotal).isEqualTo(0L);
    }

    @Test
    @DisplayName("적립금 합계 조회 시, 적립 내역이 있을 때")
    public void 적립금_합계_조회_시_적립내역이_있을때() {
        // given
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String memberId = "1";
        Reserves reserves1 = Reserves.builder()
                    .memberId("1")
                    .balance(1L)
                    .build();
        Reserves reserves2 = Reserves.builder()
                    .memberId("1")
                    .balance(2L)
                    .build();

        // mocking
        when(reservesRepository.findAllByMemberIdAndStatusAndNotExpired("1", ReservesStatus.UNUSED, nowTime))
                .thenReturn(Arrays.asList(reserves1, reserves2));

        //when
        Long reservesTotal = reservesService.getReservesTotal("1", nowTime);

        //then
        assertThat(reservesTotal).isEqualTo(3L);
    }


}