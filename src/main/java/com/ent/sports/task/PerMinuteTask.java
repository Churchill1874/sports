package com.ent.sports.task;

import com.ent.sports.service.FootballMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 */
@Slf4j
@Component
public class PerMinuteTask {

    @Autowired
    private FootballMatchService footballMatchService;

    /**
     * 每分钟执行定时任务
     */
    @Scheduled(cron = "0 */1 * * * ?")
    private void perMinute() {
        log.info("执行每分钟定时任务");

        footballMatchService.updateInProgressStatus();
        log.info("执行---足球赛事修改开始状态定时任务");

        footballMatchService.updateFinishedStatus();
        log.info("执行---足球赛事修改结束状态定时任务");

    }


}