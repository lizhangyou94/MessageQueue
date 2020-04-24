package com.lzycompany.messagequeue.server.consumer;

import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import com.lzycompany.messagequeue.server.returnresult.ReturnResult;

/**
 * * @decscription: 消费者服务接口类
 * * @author:lzy
 * * @date:2019-03-18 13:58
 **/
public interface AbstractKafkaConsumerServer {

    /**
     * *@decscription: 开启消费线程
     * *@author: lzy
     * *@date: 2019/3/18 14:02
     **/
    public void startRun();

    /**
     * *@decscription: 关闭消费线程
     * *@author: lzy
     * *@date: 2019/3/18 14:02
     **/
    public void stopRun();

    /**
     * *@decscription: 执行异步消费任务
     * *@author: lzy
     * *@date: 2019/3/18 14:03
     **/
    public void handleAsy(KafkaMessageContext context);

    /**
     * *@decscription: 执行同步消费任务
     * *@author: lzy
     * *@date: 2019/3/18 14:03
     **/
    public ReturnResult handleAyn(KafkaMessageContext context);
}
