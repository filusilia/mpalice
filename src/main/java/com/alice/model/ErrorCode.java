package com.alice.model;

import java.io.Serializable;

/**
 * 用于接收微信公众平台的全局返回码
 */
public class ErrorCode implements Serializable {

    private static final long serialVersionUID = 201509221632000L;

    /**
     * 全局返回码
     */
    private String errorCode = "";

    /**
     * 说明
     */
    private String errorMessage = "";

    public String getErrorCode() {
        return errorCode;
    }



    public String getErrorMessage() {
        return errorMessage;
    }

}
