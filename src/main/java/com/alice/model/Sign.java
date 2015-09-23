package com.alice.model;

import org.apache.commons.lang.StringUtils;

/**
 * 用于接收验证URL有效性的参数
 * 
 * @author hyl
 * 
 */
public class Sign extends ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = -8345611197885069954L;

    /**
     * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     */
    private String signature = "";

    /**
     * 时间戳
     */
    private long timestamp;

    /**
     * 随机数
     */
    private long nonce;

    /**
     * 随机字符串
     */
    private String echostr = "";

    public Sign() {
        super();
    }

    public Sign(String signature, String timestamp, String nonce, String echostr) {
        this.signature = signature;
        if (StringUtils.isNotBlank(timestamp))
            this.timestamp = Long.valueOf(timestamp);
        if (StringUtils.isNotBlank(nonce))
            this.nonce = Long.valueOf(nonce);
        this.echostr = echostr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

}
