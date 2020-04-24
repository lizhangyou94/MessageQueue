package com.lzycompany.messagequeue;

import com.lzycompany.messagequeue.common.util.HandleServiceEnum;
import com.lzycompany.messagequeue.common.util.SpringContextUtil;
import com.lzycompany.messagequeue.server.context.KafkaMessageContext;
import com.lzycompany.messagequeue.server.producer.impl.KafkaMessageSendClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagequeueApplicationTests {

    @Test
    public void contextLoads() {
        KafkaMessageSendClient kafkaMessageSendClient = SpringContextUtil.getBean(KafkaMessageSendClient.class);
        KafkaMessageContext kafkaMessageContext = new KafkaMessageContext();
        kafkaMessageContext.setUserId("111111111");
        kafkaMessageContext.setServiceCode(HandleServiceEnum.SEND_MAIL.getServiceCode());
        kafkaMessageContext.setMethodName("sendMail");
        Map<String, Object> messageMap = new HashMap<String, Object>();
        messageMap.put("text", "德玛西亚万岁！");
        messageMap.put("subject", "峡谷之巅见");
        messageMap.put("receiveAccount", "1209414113@qq.com");
        kafkaMessageContext.setMessageMap(messageMap);
        kafkaMessageSendClient.sendAyn(kafkaMessageContext);
    }

}
