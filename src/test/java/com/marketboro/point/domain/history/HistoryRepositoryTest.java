package com.marketboro.point.domain.history;

import com.marketboro.point.config.TestConfig;
import com.marketboro.point.domain.history.projection.HistoryProjection;
import com.marketboro.point.dto.enums.HistoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import(TestConfig.class)
class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @BeforeEach
    public void 데이터준비() {
        historyRepository.save(History.of("qwe5507", 10000L, HistoryType.SAVE, false));
        historyRepository.save(History.of("qwe5507", 20000L, HistoryType.SAVE, false));
    }

    @Test
    public void 적립금사용내역조회() {
        // given
        String memberId = "qwe5507";
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<HistoryProjection> pageHistory = historyRepository.findAllByMemberId(memberId, pageRequest);

        // then
        assertThat(2).isEqualTo(pageHistory.getTotalElements());
        assertThat(10000L).isEqualTo(pageHistory.getContent().get(0).getAmount());
        assertThat(20000L).isEqualTo(pageHistory.getContent().get(1).getAmount());
    }
}