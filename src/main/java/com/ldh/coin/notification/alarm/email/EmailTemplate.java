package com.ldh.coin.notification.alarm.email;

/**
 * @author mango
 */
public class EmailTemplate {
    public static String getEmailTemplate() {
        return "<div>" +
                "<b><p>★注意★关于纪念币的通知</b></p></div>" +
                "<div><p></p></div>"+
                "<div><p>已监测到有关纪念币相关信息，具体如下：</p></div>" +
                "<div></div>" +
                "<div><p>标题: {0}</p></div><div>" +
                "<p>时间: {1}</p></div>" +
                "<div><p>正文:</p></div>" +
                "<div>&nbsp;{2}</div>" +
                "<div><p>相关链接:&nbsp;<a href=\"{3}\">{4}</a></p></div>" +
                "<div><p></p></div>" +
                "<div><p></p></div>" +
                "<div>Thanks.</div>" +
                "<div>ES.Notification</div>" +
                "<div>邮件发送时间 CST: {5}</div>" +
                "<div><br></div>";
    }
}
