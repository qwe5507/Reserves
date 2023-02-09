package com.marketboro.point.domain.reserves.repository;

import com.marketboro.point.config.TestConfig;
import com.marketboro.point.domain.reserves.Reserves;
import com.marketboro.point.dto.enums.ReservesStatus;
import com.marketboro.point.dto.request.SaveReservesReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import(TestConfig.class)
class ReservesRepositoryTest {

    @Autowired
    private ReservesRepository reservesRepository;

    @BeforeEach
    public void 데이터준비() {

        Reserves reserves1 = Reserves.of(new SaveReservesReq("qwe5507", 10000L));
        Reserves reserves2 = Reserves.of(new SaveReservesReq("qwe5507", 20000L));
        Reserves reserves3 = Reserves.of(new SaveReservesReq("zxc5507", 30000L));
        Reserves reserves4 = Reserves.builder()
                .memberId("zxc5507")
                .amount(40000L)
                .balance(40000L)
                .status(ReservesStatus.UNUSED)
                .expiredAt(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        reservesRepository.save(reserves1);
        reservesRepository.save(reserves2);
        reservesRepository.save(reserves3);
        reservesRepository.save(reserves4);
    }

    @Test
    public void findAllByMemberIdAndStatusAndNotExpiredTest() {
        // given
        Long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String memberId = "qwe5507";

        // when
        List<Reserves> reservesList = reservesRepository.findAllByMemberIdAndStatusAndNotExpired(memberId, ReservesStatus.UNUSED, nowTime);

        // then
        assertThat(2).isEqualTo(reservesList.size());
        assertThat(10000L).isEqualTo(reservesList.get(0).getAmount());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void findAllByInIdAndNotExpiredTest() {
        // given
        List<Long> reservesIdList = Arrays.asList(1L, 2L, 3L);

        // when
        List<Reserves> reservesList = reservesRepository.findAllByInIdAndNotExpired(reservesIdList);

        // then
        assertThat(3).isEqualTo(reservesList.size());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void updateExpiredReservesByExpiredAtTest() {
        // given
        long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // when
        long result = reservesRepository.updateExpiredReservesByExpiredAt(nowTime);

        // then
        assertThat(1).isEqualTo(result);
    }

}