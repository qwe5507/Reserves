package com.marketboro.point.domain.reserves_history;

import com.marketboro.point.config.TestConfig;
import com.marketboro.point.domain.history.History;
import com.marketboro.point.domain.history.HistoryRepository;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.domain.reserves.repository.ReservesRepository;
import com.marketboro.point.dto.enums.HistoryType;
import com.marketboro.point.dto.request.SaveReservesReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import(TestConfig.class)
class ReservesHistoryRepositoryTest {
    @Autowired
    private ReservesHistoryRepository reservesHistoryRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ReservesRepository reservesRepository;


    @BeforeEach
    public void 데이터준비() {
        Reserves reserves = Reserves.of(new SaveReservesReq("qwe5507", 10000L));
        History history = History.of("qwe5507", 10000L, HistoryType.SAVE, false);
        ReservesHistory reservesHistory = ReservesHistory.of(reserves, history);

        historyRepository.save(history);
        reservesRepository.save(reserves);
        reservesHistoryRepository.save(reservesHistory);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void findAllByHistoryIdTest() {
        // given
        Long historyId = 1L;

        // when
        List<ReservesHistory> reservesHistoryList = reservesHistoryRepository.findAllByHistoryId(historyId);

        // then
        assertThat("qwe5507").isEqualTo(reservesHistoryList.get(0).getReserves().getMemberId());
        assertThat(10000L).isEqualTo(reservesHistoryList.get(0).getReserves().getAmount());
        assertThat("qwe5507").isEqualTo(reservesHistoryList.get(0).getHistory().getMemberId());
        assertThat(10000L).isEqualTo(reservesHistoryList.get(0).getHistory().getAmount());
    }
}