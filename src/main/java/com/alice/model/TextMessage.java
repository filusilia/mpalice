package com.alice.model;

/**
 * 文本消息
 * 
 * @author hyl
 * 
 */
public class TextMessage extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7946672461979587091L;

    /**
     * 文本消息内容
     */
    private String Content = "";

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
