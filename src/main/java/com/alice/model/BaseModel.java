package com.alice.model;

/**
 * 消息基类（公众帐号 -> 普通用户）
 */
public class BaseModel extends ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = 3722318081073068273L;

    /**
     * 开发者微信号(公众平台微信号)
     */
    private String ToUserName = "";

    /**
     * 发送方帐号(OpenID,关注者)
     */
    private String FromUserName = "";

    /**
     * 消息创建时间 （整型）
     */
    private long CreateTime;

    /**
     * 消息类型，event
     */
    private String MsgType = "";

    /**
     * 事件类型，CLICK
     */
    private String Event = "";

    /**
     * 事件KEY值，由开发者在创建菜单时设定
     */
    private String EventKey = "";

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

}
