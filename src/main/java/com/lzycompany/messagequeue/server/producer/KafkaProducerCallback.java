package com.lzycompany.messagequeue.server.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * * @decscription: 异步发送kafka消息的返回接口处理类
 * * @author:lzy
 * * @date:2020-04-02 10:57
 **/
public class KafkaProducerCallback  implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            e.printStackTrace();
            System.err.println( "发送消息错误啦："+e.getMessage());
        }else{
            System.out.println("发送消息成功啦，德玛西亚！！");
        }
    }
}
