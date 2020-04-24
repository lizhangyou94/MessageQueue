package com.lzycompany.messagequeue.common.util;

import com.lzycompany.messagequeue.common.constant.HandleServiceCodeConstant;

/**
 * * @decscription: 消息码与处理类的名称的对于关系
 * * @author:lzy
 * * @date:2019-03-19 9:51
 **/
public enum HandleServiceEnum {

    SEND_MAIL(HandleServiceCodeConstant.SEND_MAIL_CODE, "sendMailService"),

    RECORD_BROWSE_LOG(HandleServiceCodeConstant.RECORD_BROWSE_LOG, "");

    private String serviceCode;
    private String serviceBeanName;

    HandleServiceEnum(String serviceCode, String serviceBeanName) {
        this.serviceCode = serviceCode;
        this.serviceBeanName = serviceBeanName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceBeanName() {
        return serviceBeanName;
    }

    public void setServiceBeanName(String serviceBeanName) {
        this.serviceBeanName = serviceBeanName;
    }

    /**
     * *@decscription: 根据消息码获取处理类的枚举
     * *@author: lzy
     * *@date: 2019/3/19 10:37
     **/
    public static HandleServiceEnum getHandleServiceEnumByCode(String serviceCode) {
        for (HandleServiceEnum handleServiceEnum : HandleServiceEnum.values()) {
            if (serviceCode.equalsIgnoreCase(handleServiceEnum.getServiceCode())) {
                return handleServiceEnum;
            }
        }
        return null;
    }
}
