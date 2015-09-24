package com.alice.model;

/**
 * 客服-消息发送-文本实体
 */
public class Text extends ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = 1192673615040735769L;

    public Text() {
        super();
    }

    public Text(String content) {
        this.content = content;
    }

    /**
     * 文本消息内容
     */
    private String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
