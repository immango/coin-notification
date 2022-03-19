package com.ldh.coin.notification.alarm.email;

import com.ldh.coin.notification.alarm.event.CoinEvent;
import com.ldh.coin.notification.entity.AnnouncementEntity;
import com.ldh.coin.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
    private NotificationService notificationService;
    private final Logger log = LoggerFactory.getLogger(EmailAlarmImpl.class);
    private final String ALARM_TYPE = "EMAIL";

    @Value("${mail.users}")
    private String emailUsers;
    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public void onApplicationEvent(CoinEvent event) {
        try {
            AnnouncementEntity announcementEntity = event.getAnnouncementEntity();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(emailFrom);
            String[] users = emailUsers.split(";");
            mimeMessageHelper.setTo(users);
            mimeMessageHelper.setSubject("纪念币通知提醒");
            String email = MessageFormat.format(getEmailTemplate(), announcementEntity.getTitle(),
                    announcementEntity.getDate(), announcementEntity.getDetail(),
                    announcementEntity.getUrl(), announcementEntity.getUrl(), LocalDateTime.now());
            mimeMessageHelper.setText(email, true);

            // hash值计算
            String hashCode = DigestUtils.md5DigestAsHex(announcementEntity.getTitle().getBytes());
            if (!notificationService.checkIfSend(hashCode, ALARM_TYPE)) {
                mailSender.send(mimeMessage);
                notificationService.saveSend(hashCode, ALARM_TYPE, email, emailUsers, LocalDateTime.now());
                log.info("通知邮件已发送，通知人员为: [{}]", emailUsers);
            }else {
                log.info("通知重复，本次不发送通知。通知hashCode[{}]", hashCode);
            }
        }catch (Exception e) {
            log.error("发送邮箱提醒出现未知异常: ", e);
        }
    }


    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
