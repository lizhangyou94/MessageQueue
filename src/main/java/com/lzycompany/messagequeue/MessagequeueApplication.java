package com.lzycompany.messagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:config/email.properties","classpath:config/redis.properties","classpath:config/kafka.properties"})
@SpringBootApplication
public class MessagequeueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagequeueApplication.class, args);
    }

}
