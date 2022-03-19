package com.ldh.coin.notification.service;

import com.ldh.coin.notification.dao.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author mango
 * @description 通知类
 * @date 2022/3/19 11:38
 */
@Service
public class NotificationService {

    private NotificationMapper notificationMapper;

    public Boolean checkIfSend(String messageHashCode, String alarmType) {
        return notificationMapper.queryIfSend(messageHashCode, alarmType) > 0;
    }

    public Boolean saveSend(String hashCode, String alarmType, String alarmText, String alarmUsers, LocalDateTime now) {
        return notificationMapper.insertSend(hashCode, alarmType, alarmText, alarmUsers, now) > 0;
    }

    @Autowired
    public void setNotificationMapper(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }
}
