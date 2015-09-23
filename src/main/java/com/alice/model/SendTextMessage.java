package com.alice.model;

/**
 * 客服-发送消息实体类
 * 
 * @author hyl
 * 
 */
public class SendTextMessage extends ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = -8235769080225484196L;

    /**
     * 普通用户openid
     */
    private String touser = "";

    /**
     * 消息类型，文本为text
     */
    private String msgtype = "text";

    /**
     * 客服-消息发送-文本实体
     */
    private Text text;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

}
