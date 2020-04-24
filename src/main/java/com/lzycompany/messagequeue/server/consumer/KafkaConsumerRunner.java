package com.lzycompany.messagequeue.server.consumer;

import com.lzycompany.messagequeue.common.util.HandleServiceEnum;
import com.lzycompany.messagequeue.common.util.SpringContextUtil;
import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import com.lzycompany.messagequeue.server.returnresult.ReturnResult;
import com.lzycompany.messagequeue.server.service.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * * @decscription: 消费线程
 * * @author:lzy
 * * @date:2019-03-18 11:12
 **/
@Slf4j
@Component("kafkaConsumerRunner")
public class KafkaConsumerRunner implements Runnable {

    private Properties props = new Properties();

    @Value("${kafka.bootstrap_server}")
    private String bootstrapServer;

    @Value("${kafka.my_topic}")
    private List<String> topic;

    @Value("${kafka.enable.auto.commit}")
    private String autoCommit;

    @Value("${kafka.auto.commit.interval.ms}")
    private String intervalMs;

    @Value("${kafka.key.deserializer}")
    private String keyDeserializer;

    @Value("${kafka.value.deserializer}")
    private String valueDeserializer;


    @Value("${kafka.value.serializer}")
    private String valueSerializer;

    @Value("${kafka.group.id}")
    private String groupID;

    //是否停止线程：true-停止，false-开启
    private boolean closed = false;

    @Override
    public void run() {
        log.info("=============启动了kafka消费线程=============");
        //kafka消费者
        KafkaConsumer<String, byte[]> consumer = createKafkaConsumer();
        while (!closed) {
            try {
                ConsumerRecords<String, byte[]> record = consumer.poll(1000);
                Iterator<ConsumerRecord<String, byte[]>> iterator = record.iterator();
                while (iterator.hasNext()) {
                    log.info("============接受到消息了=============");
                    ConsumerRecord<String, byte[]> consumerRecord = iterator.next();
                    //序列化消息对象
                    KafkaMessageContext context = deserialize(consumerRecord.value());
                    if (context != null) {
                        execute(context);
                    } else {
                        log.error("===kafka消息对象为空！！！===");
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("===消费线程休眠出错，德玛西亚！！！===", e);
                }
            } catch (Exception e) {
                log.error("===消费消息出错啦，德玛西亚！！！===", e);
            }
        }
    }

    /**
     * *@decscription: 停止消费线程
     * *@author: lzy
     * *@date: 2019/3/18 11:21
     **/
    public void shutDown(boolean closed) {
        this.closed = closed;
    }

    /**
     * *@decscription: 初始化消费者线程
     * *@author: lzy
     * *@date: 2019/3/18 13:49
     **/
    public KafkaConsumer<String, byte[]> createKafkaConsumer() {
        props.put("bootstrap.servers", bootstrapServer);
        props.put("group.id", groupID);
        props.put("enable.auto.commit", autoCommit);
        props.put("auto.commit.interval.ms", intervalMs);
        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);
        props.put("value.deserializer", valueDeserializer);
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
        consumer.subscribe(topic);
        return consumer;
    }

    /**
     * *@decscription: 把byte反序列化成对象
     * *@author: lzy
     * *@date: 2019/3/18 16:00
     **/
    private KafkaMessageContext deserialize(byte[] ioByte) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ioByte);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            KafkaMessageContext kafkaMessageContext = (KafkaMessageContext) objectInputStream.readObject();
            return kafkaMessageContext;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("===把byte反序列化成对象异常===", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("===把byte反序列化成对象异常===", e);
        } finally {
            try {
                objectInputStream.close();
                byteArrayInputStream.close();
            } catch (IOException e) {
                log.error("===把byte反序列化成对象异常===", e);
            }
        }
        return null;
    }

    /**
     * *@decscription: 执行消息码对于的处理类的方法
     * *@author: lzy
     * *@date: 2019/3/19 10:53
     **/
    public ReturnResult execute(KafkaMessageContext context) {
        String serviceCode = context.getServiceCode();
        if (!StringUtils.isEmpty(serviceCode)) {
            //根据消息码获取处理类的beanName
            String serviceBeanName = HandleServiceEnum.getHandleServiceEnumByCode(serviceCode).getServiceBeanName();
            //根据beanName获取处理类
            ParentService service = (ParentService) SpringContextUtil.getObject(serviceBeanName);
            String methodName = context.getMethodName();
            log.info("执行消息serviceBeanName=" + serviceBeanName + ",methodName=" + methodName);
            if (!StringUtils.isEmpty(methodName)) {
                try {
                    //通过反射执行方法
                    Method method = service.getClass().getMethod(methodName, String.class, Map.class);
                    method.invoke(service, context.getUserId(), context.getMessageMap());
                } catch (NoSuchMethodException e) {
                    log.error("===没有这个方法！！！===", e);
                } catch (IllegalAccessException e) {
                    log.error("===通过反射执行方法出错！！！===", e);
                } catch (InvocationTargetException e) {
                    log.error("===通过反射执行方法出错！！！===", e);
                }
            } else {
                //没有传递方法名称时，执行默认的方法
                service.execute(context.getUserId(), context.getMessageMap());
            }
        } else {
            log.error("===消息码，不能为空！！！===");
        }
        return null;
    }

}
