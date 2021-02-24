package com.ldh.coin.notification.alarm.event;

import com.ldh.coin.notification.entity.AnnouncementEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author mango
 * @description coin 事件
 * @date 2021/2/24 21:55
 */
public class CoinEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2199366021450366571L;
    private final AnnouncementEntity source;

    public CoinEvent(AnnouncementEntity source) {
        super(source);
        this.source = source;
    }

    public AnnouncementEntity getAnnouncementEntity() {
        return source;
    }
}
