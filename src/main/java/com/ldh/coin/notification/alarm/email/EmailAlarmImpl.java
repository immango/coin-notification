package com.ldh.coin.notification.alarm.email;

import com.ldh.coin.notification.alarm.event.CoinEvent;
import com.ldh.coin.notification.entity.AnnouncementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.time.LocalDateTime;

import static com.ldh.coin.notification.alarm.email.EmailTemplate.getEmailTemplate;

/**
 * @author mango
 * @description 简述这个类的作用
 * @date 2021/2/24 22:01
 */
@Service
public class EmailAlarmImpl implements ApplicationListener<CoinEvent> {
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(CoinEvent event) {
        try {
            AnnouncementEntity announcementEntity = event.getAnnouncementEntity();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("Notification.ES@foxmail.com");
            mimeMessageHelper.setTo("1807659863@qq.com");
            mimeMessageHelper.setSubject("纪念币通知提醒");
            String email = MessageFormat.format(getEmailTemplate(), announcementEntity.getTitle(),
                    announcementEntity.getDate(), announcementEntity.getDetail(),
                    announcementEntity.getUrl(), announcementEntity.getUrl(), LocalDateTime.now());
            mimeMessageHelper.setText(email, true);
            mailSender.send(mimeMessage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
