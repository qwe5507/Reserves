package com.marketboro.point.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerInit {
    private final Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        // 클러스터링을 설정했을 경우는 인스턴스 중 한번만 실행되어야 함.
        removeAllTrigger();
        removeAllJob();

        log.info("Remove all schedule. - init()");

        registerTriggers();
    }

    private void removeAllTrigger() throws SchedulerException {
        for (String triggerGroupName : scheduler.getTriggerGroupNames()) {
            for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName))) {
                String triggerName = triggerKey.getName();
                scheduler.unscheduleJob(new TriggerKey(triggerName, triggerGroupName));
            }
        }
    }

    private void removeAllJob() throws SchedulerException {
        for (String jobGroupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))) {
                String jobName = jobKey.getName();
                scheduler.deleteJob(new JobKey(jobName, jobGroupName));
            }
        }
    }

    private static final TimeZone TIMEZONE_KST = TimeZone.getTimeZone("GMT+9");

    private void registerTriggers() throws SchedulerException {
        // transact token 삭제
        scheduler.scheduleJob(
                JobBuilder.newJob(ReservesExpiredUpdateJob.class).withIdentity(ReservesExpiredUpdateJob.class.getSimpleName()).build(),
                TriggerBuilder.newTrigger().withIdentity("reservesExpiredUpdateTrigger").withSchedule(
                        CronScheduleBuilder
                                .cronSchedule("0 0/10 * 1/1 * ? *")                     /* 10분 마다 */
                                .inTimeZone(TIMEZONE_KST)
                ).build());
    }
}
