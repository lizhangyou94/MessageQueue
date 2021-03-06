package com.lzycompany.messagequeue.server.producer;

import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * * @decscription: kafka生产者
 * * @author:lzy
 * * @date:2019-03-15 11:12
 **/
@Slf4j
@Component("kafkaMessageProducer")
public class KafkaMessageProducer {

    @Value("${kafka.bootstrap_server}")
    private String bootstrapServer;

    @Value("${kafka.my_topic}")
    private String myTopic;

    @Value("${kafka.acks}")
    private String acks;

    @Value("${kafka.retries}")
    private String retries;

    @Value("${kafka.buffer.memory}")
    private String bufferMemory;

    @Value("${kafka.partitioner.class}")
    private String partitionerClass;

    @Value("${kafka.key.serializer}")
    private String keySerializer;

    @Value("${kafka.value.serializer}")
    private String valueSerializer;

    // kafka生产者配置信息
    private Properties props = new Properties();

    // kafka生产者
    private Producer<String, byte[]> producer;

    /**
     * *@decscription: 配置并初始化kafkaProducer
     * *@author: lzy
     * *@date: 2019/3/15 14:48
     **/
    @PostConstruct
    private Producer<String, byte[]> createKafkaProducer() {
        props.put("bootstrap.servers", bootstrapServer);
        props.put("acks", acks);
        props.put("retries", retries);
        props.put("buffer.memory", bufferMemory);
//        props.put("partitioner.class", partitionerClass);
        props.put("key.serializer", keySerializer);
        props.put("value.serializer", valueSerializer);
        producer = new KafkaProducer<>(props);
        return producer;
    }

    /**
     * *@decscription: 发送异步的方法
     * *@author: lzy
     * *@date: 2019/3/15 14:58
     **/
    public void sendAsy(KafkaMessageContext context) {
        producer.send(new ProducerRecord<String, byte[]>(myTopic,
                context.getUserId(),
                serializContext(context)),new KafkaProducerCallback());
    }

    /**
     * *@decscription: 发送同步的方法
     * *@author: lzy
     * *@date: 2019/3/15 14:58
     **/
    public void sendAyn(KafkaMessageContext context) {
        Future<RecordMetadata> future = producer.send(new ProducerRecord<String, byte[]>(myTopic,
                context.getUserId(),
                serializContext(context)));
        //producer的send方法返回Future对象，我们使用Future对象的get方法来实现同步发送消息。
        //Future对象的get方法会产生阻塞，直到获取kafka集群的响应，响应结果分两种：
        //1、响应中有异常：此时get方法会抛出异常，我们可以捕获此异常进行相应的业务处理
        //2、响应中无异常：此时get方法会返回RecordMetadata对象，此对象包含了当前发送成功的消息在Topic中的offset、partition等信息
        try {
            RecordMetadata recordMetadata = future.get();
        } catch (InterruptedException e) {
            log.error("===执行同步发送kafka消息异常===", e);
        } catch (ExecutionException e) {
            log.error("===执行同步发送kafka消息异常===", e);
        }
    }


    /**
     * *@decscription: 序列化消息对象
     * *@author: lzy
     * *@date: 2019/3/15 16:11
     **/
    private byte[] serializContext(KafkaMessageContext context) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream byteOS = null;
        try {
            byteOS = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteOS);
            oos.writeUnshared(context);
        } catch (IOException e) {
            log.error("===序列化消息（KafkaMessageContext）异常===", e);
            return null;
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                log.error("===序列化消息（KafkaMessageContext）异常===", e);
            }
        }
        return byteOS.toByteArray();
    }

}
