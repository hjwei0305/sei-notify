package com.changhong.sei.notify.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.notify.service.BulletinService;
import com.changhong.sei.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-29 10:56
 */
@Configuration
@EnableScheduling
public class SaticScheduleTask {

    @Autowired
    private BulletinService bulletinService;

    @Scheduled(cron = "0 15 01 ? * *")
    private void configureTasks() {
        LogUtil.info("执行更新有效期状态任务时间: {}", LocalDateTime.now());

        try {
            Thread.sleep(RandomUtils.getInteger(1000, 10000));
        } catch (Exception ignored) {
        }

        ResultData<Integer> resultData = bulletinService.updateEffective();
        LogUtil.info("任务执行结果为: {}", resultData.getMessage());
    }
}
