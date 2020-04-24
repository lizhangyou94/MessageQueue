package com.lzycompany.messagequeue;

import com.lzycompany.messagequeue.server.producer.KafkaProducerCallback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * * @decscription:
 * * @author:lzy
 * * @date:2020-04-02 10:29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {

    @Test
    public void sendMessageTest(){
        // kafka生产者配置信息
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "114.215.179.123:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // kafka生产者
        KafkaProducer<String, String> producer=new KafkaProducer<String, String>(kafkaProps) ;
        ProducerRecord  record = new ProducerRecord<String, String>("test0",
                "111",
                "2222");
        try {
            producer.send(record,new KafkaProducerCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("====发送消息=========");
    }
}
