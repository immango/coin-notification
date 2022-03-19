package com.ldh.coin.notification.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author mango
 */
@Repository
@Mapper
public interface NotificationMapper {
    /**
     * sql查询是否已经发送过该通知消息
     * @param hashCode 消息hashcode
     * @param alarmType 通知类型
     * @return
     */
    @Select("select count(*) from t_coin_notification_alarm where hash_code = #{hashCode} and alarm_type = #{alarmType}")
    int queryIfSend(@Param("hashCode") String hashCode, @Param("alarmType") String alarmType);

    /**
     * 将新的通知写入表
     * @param hashCode 通知hash值
     * @param alarmType 通知类型
     * @param alarmText 通知内容
     * @param alarmUsers 通知人
     * @param now 时间
     * @return
     */
    @Insert("insert into t_coin_notification_alarm (hash_code, alarm_type, alarm_content, alarm_users, create_date) values (#{hashCode}, #{alarmType}, #{alarmText}, #{alarmUsers}, #{now})")
    int insertSend(@Param("hashCode") String hashCode, @Param("alarmType") String alarmType,
                   @Param("alarmText") String alarmText, @Param("alarmUsers") String alarmUsers, @Param("now") LocalDateTime now);
}
