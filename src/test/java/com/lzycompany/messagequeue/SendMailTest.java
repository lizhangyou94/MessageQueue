package com.lzycompany.messagequeue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * * @decscription:
 * * @author:lzy
 * * @date:2019-03-20 17:25
 **/
@RunWith(SpringRunner.class)
@PropertySource(value = {"classpath:config/email.properties"})
@SpringBootTest
public class SendMailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件
     */
    @Test
    public void send_mail() {
        try {
            //创建连接对象 连接到邮件服务器
            Properties properties = new Properties();
            //设置发送邮件的基本参数
            //发送邮件服务器(注意，此处根据你的服务器来决定，如果使用的是QQ服务器，请填写smtp.qq.com)
            properties.put("mail.smtp.host", "smtp.qq.com");
            //发送端口（根据实际情况填写，一般均为25）
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.put("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
            properties.put("spring.mail.properties.mail.smtp.ssl.enable", "true");
            //POP3 :  ehbplrfcucaqbdic
            //设置发送邮件的账号和密码
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    //两个参数分别是发送邮件的账户和密码(注意，如果配置后不生效，请检测是否开启了 POP3/SMTP 服务，QQ邮箱对应设置位置在: [设置-账户-POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务])
                    return new PasswordAuthentication("805955621@qq.com", "ehbplrfcucaqbdic");
                }
            });

            //创建邮件对象
            Message message = new MimeMessage(session);
            //设置发件人
            message.setFrom(new InternetAddress("805955621@qq.com"));
            //设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("1209414113@qq.com"));
            //设置主题
            message.setSubject("这是一份测试邮件");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("测试", "text/html;charset=UTF-8");
            //发送一封邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @Test
    public void sendSimpleMail() {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom("805955621@qq.com");
            //邮件接收人
            simpleMailMessage.setTo("1209414113@qq.com");
            //邮件主题
            simpleMailMessage.setSubject("测试主题");
            //邮件内容
            simpleMailMessage.setText("测试内容");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.err.println("邮件发送失败" + e.getMessage());
        }
    }


}
