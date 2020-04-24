package com.lzycompany.messagequeue.server.service;

import com.lzycompany.messagequeue.server.returnresult.ReturnResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * * @decscription: 消息系统的处理类的父类
 * * @author:lzy
 * * @date:2019-03-19 11:06
 **/
@Slf4j
public class ParentService {

    /**
     * *@decscription: 处理类的默认处理方法
     * *@author: lzy
     * *@date: 2019/3/19 11:15
     **/
    public ReturnResult execute(String userId, Map<String, Object> params) {
        log.info("===执行父类的处理方法=====， user=" + userId + "，params=" + params);
        return null;
    }

}
