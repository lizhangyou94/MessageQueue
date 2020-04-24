package com.lzycompany.messagequeue.server.vo;

/**
 * * @decscription: 发送邮箱内容实体
 * * @author:lzy
 * * @date:2019-04-03 16:16
 **/
public class SendMeilVO {
    //标题
    private String subject;
    //内容
    private String text;
    //接收人
    private String to;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "SendMeilVO{" +
                "subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
