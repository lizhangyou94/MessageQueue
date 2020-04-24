package com.lzycompany.messagequeue;

import com.lzycompany.messagequeue.common.util.RedisUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * * @decscription:
 * * @author:lzy
 * * @date:2019-04-16 14:21
 **/
@RunWith(SpringRunner.class)
@PropertySource(value = {"classpath:config/email.properties"})
@SpringBootTest
public class Test {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private com.lzycompany.messagequeue.common.util.RedisDistributedLockUtil redisDistributedLockUtil;

    @org.junit.Test
    public void test() {
        System.err.println(redisDistributedLockUtil.getLock("11", "11", 100));
    }

    public static void main(String[] arv) {

    }


}

