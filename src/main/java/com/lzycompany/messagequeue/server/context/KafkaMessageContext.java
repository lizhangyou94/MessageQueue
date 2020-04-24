package com.lzycompany.messagequeue.server.context;

import java.io.Serializable;
import java.util.Map;

/**
 * *@decscription: kafka消息主体
 * *@author: lzy
 * *@date: 2019/3/14 17:39
 **/
public class KafkaMessageContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    //处理消息的service的code
    private String serviceCode;

    //处理消息的service的方法的名称
    private String methodName;

    private Map<String, Object> messageMap;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, Object> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, Object> messageMap) {
        this.messageMap = messageMap;
    }
}
