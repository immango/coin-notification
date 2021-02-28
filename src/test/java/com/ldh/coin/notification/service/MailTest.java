package com.ldh.coin.notification.service;

import com.ldh.coin.notification.entity.AnnouncementEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.time.LocalDateTime;

import static com.ldh.coin.notification.alarm.email.EmailTemplate.getEmailTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    private JavaMailSender mailSender;
    private final Logger log = LoggerFactory.getLogger(MailTest.class);

    @Test
    public void sendMailTest() {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("xxx@foxmail.com");
            mimeMessageHelper.setTo("xxx@qq.com");
            mimeMessageHelper.setSubject("纪念币通知提醒");
            String email = MessageFormat.format(getEmailTemplate(), "announcementEntity.getTitle()",
                    "announcementEntity.getDate()", "announcementEntity.getDetail()",
                    "announcementEntity.getUrl()", "announcementEntity.getUrl()", LocalDateTime.now().toString().split("\\.")[0]);
            mimeMessageHelper.setText(email, true);
            //mailSender.send(mimeMessage);
            log.info("LOG: 邮件已发送");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("error: ", e);
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
