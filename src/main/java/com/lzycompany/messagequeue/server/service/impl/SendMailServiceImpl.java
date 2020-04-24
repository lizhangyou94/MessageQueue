package com.lzycompany.messagequeue.server.service.impl;

import com.lzycompany.messagequeue.common.util.RedisUtil;
import com.lzycompany.messagequeue.common.util.SendMailUtil;
import com.lzycompany.messagequeue.common.util.SpringContextUtil;
import com.lzycompany.messagequeue.server.service.ParentService;
import com.lzycompany.messagequeue.server.service.SendMailService;
import com.lzycompany.messagequeue.server.vo.SendMeilVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * * @decscription: 发送邮箱消息
 * * @author:lzy
 * * @date:2019-03-19 10:41
 **/
@Slf4j
@Service("sendMailService")
public class SendMailServiceImpl extends ParentService implements SendMailService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * * @decscription: 发送邮箱消息
     * * @author:lzy
     * * @date:2019-03-19 10:41
     **/
    @Override
    public void sendMail(String userId, Map<String, Object> params) {
        log.info("=====sendMail===== in params=" + params);
        System.out.println(redisUtil.get("11"));
        if (params == null || params.isEmpty()) {
            return;
        }
        Long startTime = System.currentTimeMillis();
        SendMeilVO vo = new SendMeilVO();
        vo.setTo((String) params.get("receiveAccount"));
        vo.setSubject((String) params.get("subject"));
        vo.setText((String) params.get("text"));
        SendMailUtil sendMailUtil = SpringContextUtil.getObject(SendMailUtil.class);
        sendMailUtil.sendSimpleMail(vo);
        log.info("=====sendMail===== out 消耗时间=" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
