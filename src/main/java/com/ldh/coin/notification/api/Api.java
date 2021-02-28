package com.ldh.coin.notification.api;
import com.ldh.coin.notification.sync.CoinNotificationServiceSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mango
 * @description api接口(备用)
 * @date 2021/1/31 16:35
 */
@RestController
public class Api {
    private CoinNotificationServiceSync coinNotificationServiceSync;
    private final Logger log = LoggerFactory.getLogger(Api.class);

    @GetMapping("/api/v1/manual_intervention")
    public String manualIntervention() {
        log.info("接口主动触发纪念币程序");
        coinNotificationServiceSync.coinTask();
        return "Manual Intervention";
    }

    @Autowired
    public void setCoinNotificationServiceSync(CoinNotificationServiceSync coinNotificationServiceSync) {
        this.coinNotificationServiceSync = coinNotificationServiceSync;
    }
}
