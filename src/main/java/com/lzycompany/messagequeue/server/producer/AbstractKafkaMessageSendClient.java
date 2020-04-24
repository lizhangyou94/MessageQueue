package com.lzycompany.messagequeue.server.producer;

import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import com.lzycompany.messagequeue.server.returnresult.ReturnResult;

/**
 * * @decscription:发送消息的接口
 * * @author:lzy
 * * @date:2019-03-14 18:12
 **/
public interface AbstractKafkaMessageSendClient {

    /**
     * *@decscription: 发送异步消息
     * *@author: lzy
     * *@date: 2019/3/14 18:16
     **/
    public void sendAsy(KafkaMessageContext messageContext);

    /**
     * *@decscription: 发送同步消息
     * *@author: lzy
     * *@date: 2019/3/14 18:15
     **/
    public ReturnResult sendAyn(KafkaMessageContext messageContext);
}
