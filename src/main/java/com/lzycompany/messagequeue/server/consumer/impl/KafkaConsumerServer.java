package com.lzycompany.messagequeue.server.consumer.impl;

import com.lzycompany.messagequeue.common.util.SpringContextUtil;
import com.lzycompany.messagequeue.server.consumer.AbstractKafkaConsumerServer;
import com.lzycompany.messagequeue.server.consumer.KafkaConsumerRunner;
import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import com.lzycompany.messagequeue.server.returnresult.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * * @decscription: 消费者服务器
 * * @author:lzy
 * * @date:2019-03-18 14:04
 **/
@Slf4j
@Component
public class KafkaConsumerServer implements AbstractKafkaConsumerServer {

    //消费者数量
    @Value("${kafka.consumerNum}")
    private int consumerNum;

    //消费者线程
    private List<KafkaConsumerRunner> listConsumerThread = new ArrayList<KafkaConsumerRunner>();

    /**
     * *@decscription: 启动消费线程
     * *@author: lzy
     * *@date: 2019/3/18 14:05
     **/
    @PostConstruct
    @Override
    public void startRun() {
        for (int i = 0; i < consumerNum; i++) {
            KafkaConsumerRunner runner = SpringContextUtil.getObject(KafkaConsumerRunner.class);
            listConsumerThread.add(runner);
            Thread thread = new Thread(runner);
            thread.start();
            log.info("==================启动了kafka消费线程   111111  =============");
        }
    }

    /**
     * *@decscription: 停止消费线程
     * *@author: lzy
     * *@date: 2019/3/18 14:06
     **/
    @Override
    public void stopRun() {
        for (KafkaConsumerRunner kafkaConsumerRunner : listConsumerThread) {
            kafkaConsumerRunner.shutDown(true);
        }
    }

    /**
     * *@decscription: 执行异步消费
     * *@author: lzy
     * *@date: 2019/3/18 14:06
     **/
    @Override
    public void handleAsy(KafkaMessageContext context) {

    }

    /**
     * *@decscription: 执行同步消费线程
     * *@author: lzy
     * *@date: 2019/3/18 14:06
     **/
    @Override
    public ReturnResult handleAyn(KafkaMessageContext context) {
        return null;
    }
}
