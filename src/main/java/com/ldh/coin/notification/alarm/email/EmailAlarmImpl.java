package com.ldh.coin.notification.alarm.email;

import com.ldh.coin.notification.alarm.event.CoinEvent;
import com.ldh.coin.notification.entity.AnnouncementEntity;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author mango
 * @description 简述这个类的作用
 * @date 2021/2/24 22:01
 */
@Service
public class EmailAlarmImpl implements ApplicationListener<CoinEvent> {
    @Override
    public void onApplicationEvent(CoinEvent event) {
        AnnouncementEntity announcementEntity = event.getAnnouncementEntity();
        // todo 完成邮件发送
        System.out.println(announcementEntity.toString());
    }
}
