package com.marketboro.point.scheduler;

import com.marketboro.point.service.ReservesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class ReservesExpiredUpdateJob extends QuartzJobBean {

    private final ReservesService reservesService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("[ReservesExpiredUpdateJob] start");
            reservesService.reservesExpiredUpdate();

        } catch (Exception e) {
            log.error("[ReservesExpiredUpdateJob]", e);
            throw new JobExecutionException(e);
        } finally {
            log.info("[ReservesExpiredUpdateJob] end");
        }
    }
}