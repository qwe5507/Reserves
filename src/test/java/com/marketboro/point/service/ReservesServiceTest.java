package com.marketboro.point.service;

import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.domain.reserves_history.ReservesHistoryRepository;
import com.marketboro.point.dto.request.SaveReservesReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}