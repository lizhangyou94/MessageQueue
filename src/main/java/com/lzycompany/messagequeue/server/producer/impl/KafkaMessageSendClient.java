package com.lzycompany.messagequeue.server.producer.impl;

import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import com.lzycompany.messagequeue.server.producer.AbstractKafkaMessageSendClient;
import com.lzycompany.messagequeue.server.producer.KafkaMessageProducer;
import com.lzycompany.messagequeue.server.returnresult.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * * @decscription:发送kafka消息的客户端
 * * @author:lzy
 * * @date:2019-03-15 9:34
 **/
@Component
public class KafkaMessageSendClient implements AbstractKafkaMessageSendClient {

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    /**
     * *@decscription: 异步方法
     * *@author: lzy
     * *@date: 2019/3/15 9:54
     **/
    @Override
    public void sendAsy(KafkaMessageContext messageContext) {
        kafkaMessageProducer.sendAsy(messageContext);
    }

    /**
     * *@decscription: 同步方法
     * *@author: lzy
     * *@date: 2019/3/15 9:54
     **/
    @Override
    public ReturnResult sendAyn(KafkaMessageContext messageContext) {
        kafkaMessageProducer.sendAyn(messageContext);
        return null;
    }
}
