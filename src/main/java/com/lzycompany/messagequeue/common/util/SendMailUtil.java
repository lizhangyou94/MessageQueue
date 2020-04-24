package com.lzycompany.messagequeue.common.util;

import com.lzycompany.messagequeue.server.vo.SendMeilVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * * @decscription:
 * * @author:lzy
 * * @date:2019-04-03 16:06
 **/
@Slf4j
@Component
public class SendMailUtil {

    @Value("${spring.mail.username}")
    private String userName;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * *@decscription: 发送简单邮件
     * *@author: lzy
     * *@date: 2019/4/3 16:22
     **/
    public void sendSimpleMail(SendMeilVO vo) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(userName);
            //邮件接收人
            simpleMailMessage.setTo(vo.getTo());
            //邮件主题
            simpleMailMessage.setSubject(vo.getSubject());
            //邮件内容
            simpleMailMessage.setText(vo.getText());
            javaMailSender.send(simpleMailMessage);
            log.info("===发送邮箱信息成功===");
        } catch (Exception e) {
            log.error("邮件发送失败", e);
        }
    }
}
