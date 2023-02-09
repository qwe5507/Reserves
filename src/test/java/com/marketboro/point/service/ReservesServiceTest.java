package com.marketboro.point.service;

import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistory;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.enums.HistoryType;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import com.marketboro.point.dto.request.UseReservesReq;
import com.marketboro.point.exception.InvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        verify(reservesRepository, times(1)).save(argThat(new IsReservesWillBeInserted()));
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

    @Test
    @DisplayName("적립금 사용, 적립금이 부족할 때")
    public void 적립금_사용_적립금이_부족할_떄() {
        // given
        UseReservesReq useReservesReq = new UseReservesReq("qwe5507", 10000L);
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // mocking
        when(reservesRepository.findAllByMemberIdAndStatusAndNotExpired(useReservesReq.getMemberId(), ReservesStatus.UNUSED, nowTime))
                .thenReturn(Arrays.asList(Reserves.builder()
                        .memberId("qwe5507")
                        .balance(1000L)
                        .build()));
        // when then
        assertThrows(InvalidException.class, () -> reservesService.useReserves(useReservesReq, nowTime));
    }


    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("적립금 사용 시, 5만원 적립 2번 후, 1만원 적립금 사용 시 오래된 적립금이 한번 사용 된다.")
    public void 적립금_사용시_5만원적립_2번후_1만원_적립금_사용시_오래된_적립금이_한번_사용된다() {
        // given
        UseReservesReq useReservesReq = new UseReservesReq("qwe5507", 10000L); // 적립금 1만원 사용
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        List<Reserves> rerservesList = getRerservesList();
        History history = History.of(useReservesReq.getMemberId(), useReservesReq.getAmount(), HistoryType.USE, false);

        // mocking
        when(reservesRepository.findAllByMemberIdAndStatusAndNotExpired(useReservesReq.getMemberId(), ReservesStatus.UNUSED, nowTime))
                .thenReturn(rerservesList); // 적립금 10만원 보유
//        BDDMockito.given(historyRepository.save(any(History.class))).willReturn(history);

//        when(reservesHistoryRepository.save(ReservesHistory.of(reserves, savedHistory)))
//                .thenReturn(getRerservesList()); // 적립금 10만원 보유

        // when
        reservesService.useReserves(useReservesReq, nowTime);

        // then
        verify(historyRepository, times(1)).save(argThat(new IsHistoryWillBeInserted()));
    }

    private List<Reserves> getRerservesList() {
        Reserves reserves1 = Reserves.builder()
                .memberId("qwe5507")
                .balance(50000L)
                .amount(50000L)
                .build();

        Reserves reserves2 = Reserves.builder()
                .memberId("qwe5507")
                .balance(50000L)
                .amount(50000L)
                .build();

        return Arrays.asList(reserves1, reserves2);
    }

    private static class IsReservesWillBeInserted implements ArgumentMatcher<Reserves> {
        @Override
        public boolean matches(Reserves reserves) {
            return equals(reserves.getMemberId(), "qwe5507")
                    && equals(reserves.getAmount(), 1000L)
                    && equals(reserves.getBalance(), 1000L)
                    && equals(reserves.getStatus(), ReservesStatus.UNUSED);
        }

        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

    private static class IsHistoryWillBeInserted implements ArgumentMatcher<History> {
        @Override
        public boolean matches(History history) {
            return equals(history.getMemberId(), "qwe5507")
                    && equals(history.getAmount(), 10000L)
                    && equals(history.getType(), HistoryType.USE)
                    && equals(history.isCanceled(), false);
        }

        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

    private static class IsReservesHistoryWillBeInserted implements ArgumentMatcher<ReservesHistory> {
        @Override
        public boolean matches(ReservesHistory reservesHistory) {
            return equals(reservesHistory.getHistory().getMemberId(), "qwe5507")
                    && equals(reservesHistory.getHistory().getAmount(), 10000L)
                    && equals(reservesHistory.getHistory().getType(), HistoryType.USE)
                    && equals(reservesHistory.getHistory().isCanceled(), false)
                    && equals(reservesHistory.getReserves().getMemberId(), "qwe5507")
                    && equals(reservesHistory.getReserves().getBalance(), 50000L);
        }

        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

}