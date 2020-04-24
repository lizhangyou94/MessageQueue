package com.lzycompany.messagequeue.server.returnresult;

import java.util.Map;

/**
 * * @decscription: 调用kafka同步发送消息的返回对象
 * * @author:lzy
 * * @date:2019-03-15 9:47
 **/
public class ReturnResult {

    private int resultCode;

    private String resultDecs;

    private Map<String, Object> map;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDecs() {
        return resultDecs;
    }

    public void setResultDecs(String resultDecs) {
        this.resultDecs = resultDecs;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
