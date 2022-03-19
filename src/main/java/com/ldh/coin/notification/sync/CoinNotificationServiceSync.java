package com.ldh.coin.notification.sync;

import com.ldh.coin.notification.common.CoinEnum;
import com.ldh.coin.notification.service.CoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author mango
 * @description 简易定时任务
 * @date 2021/2/28 12:54
 */
@Configuration
@EnableScheduling
public class CoinNotificationServiceSync {
    @Value("${coin.browser.type}")
    private CoinEnum browserType;
    @Value("${coin.driver.path}")
    private String driverPath;
    @Value("${coin.pbc.baseUrl}")
    private String url;

    private CoinService coinService;
    private final Logger log = LoggerFactory.getLogger(CoinNotificationServiceSync.class);

    @Scheduled(cron = "0 0 18 * * ?")
    public void coinTask() {
        log.info("纪念币定时任务已启动: {}", LocalDateTime.now());
        coinService.getCoinInformation(browserType, driverPath, url);
        log.info("纪念币定时任务已结束: {}", LocalDateTime.now());
    }

    @Autowired
    public void setCoinService(CoinService coinService) {
        this.coinService = coinService;
    }
}
