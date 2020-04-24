package com.lzycompany.messagequeue.server.service;

import java.util.Map;

/**
 * * @decscription: 发送邮箱验证码
 * * @author:lzy
 * * @date:2019-03-19 10:41
 **/
public interface SendMailService {

    /**
     * *@decscription: 发送邮箱验证码
     * *@author: lzy
     * *@date: 2019/3/19 10:43
     **/
    void sendMail(String userId, Map<String, Object> params);
}
