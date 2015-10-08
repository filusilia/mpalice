package com.alice.common;

/**
 * 常量类
 */
public class Constant {

    /**
     * 菜单
     */
    public static String menu_left;

    /**
     * wechat token
     */
    public static String access_token = "";

    /**
     * token
     */
    public static String token;

    /**
     * 应用ID
     */
    public static String appid;

    /**
     * 应用秘钥
     */
    public static String appsecret;

    /**
     * 授权类型
     */
    public static String grant_type;

    /**
     * 文本
     */
    public static final String MESSAGE_TYPE_TEXT = "text";

    /**
     * 推送
     */
    public static final String MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "click";

    /**
     * 事件类型，subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型，unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 扫描功能
     */
    public static final String EVENT_TYPE_ = "SCAN";

    /**
     * 扫描功能
     */
    public static final String EVENT_KEY_SCAN = "SCAN";

    /**
     * 签到
     */
    public static final String EVENT_KEY_REGISTRATION = "REGISTRATION";

    /**
     * 开发者微信号
     */
    public static final String TO_USER_NAMR = "ToUserName";

    /**
     * 发送方帐号
     */
    public static final String FROM_USER_NAME = "FromUserName";

    /**
     * 消息创建时间
     */
    public static final String CREATE_TIME = "CreateTime";

    /**
     * 消息类型
     */
    public static final String MSG_TYPE = "MsgType";

    /**
     * 事件类型
     */
    public static final String EVENT = "Event";

    /**
     * 事件KEY值
     */
    public static final String EVENT_KEY = "EventKey";

    /**
     * 消息内容
     */
    public static final String CONTENT = "Content";

    public String getMenu_left() {
        return menu_left;
    }

    public void setMenu_left(String menu_left) {
        Constant.menu_left = menu_left;
    }

    public static String getAccess_token() {
        return access_token;
    }

    public static void setAccess_token(String access_token) {
        Constant.access_token = access_token;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        Constant.token = token;
    }


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        Constant.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        Constant.appsecret = appsecret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        Constant.grant_type = grant_type;
    }

    public static String getEventTypeUnsubscribe() {
        return EVENT_TYPE_UNSUBSCRIBE;
    }

    public static String getEventType() {
        return EVENT_TYPE_;
    }

    public static String getMessageTypeText() {
        return MESSAGE_TYPE_TEXT;
    }

    public static String getMessageTypeEvent() {
        return MESSAGE_TYPE_EVENT;
    }

    public static String getEventTypeClick() {
        return EVENT_TYPE_CLICK;
    }

    public static String getEventTypeSubscribe() {
        return EVENT_TYPE_SUBSCRIBE;
    }

    public static String getToUserNamr() {
        return TO_USER_NAMR;
    }

    public static String getFromUserName() {
        return FROM_USER_NAME;
    }

    public static String getCreateTime() {
        return CREATE_TIME;
    }

    public static String getMsgType() {
        return MSG_TYPE;
    }

    public static String getEVENT() {
        return EVENT;
    }

    public static String getEventKey() {
        return EVENT_KEY;
    }

    public static String getCONTENT() {
        return CONTENT;
    }

    public static String getEventKeyScan() {
        return EVENT_KEY_SCAN;
    }

    public static String getEventKeyRegistration() {
        return EVENT_KEY_REGISTRATION;
    }
}
