package com.alice.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.alice.common.Constant;

/**
 * 对于字符传的生成处理
 * 
 */
public class BizUtils {

    /**
     * 获取重定向url
     * 
     * @param url
     *            需要重定向的URL
     * @return String
     */
    public static String toRedirect(String url) {
        StringBuilder rs = new StringBuilder();
        rs.append("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
        rs.append(Constant.appid);
        rs.append("&redirect_uri=");
        rs.append(url);
        rs.append("?response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        return rs.toString();
    }

    /**
     * 随机数字验证码
     * 
     * @param length
     *            长度
     * @return String
     */
    public static String randomNum(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(ThreadLocalRandom.current().nextInt(9));
        }
        return builder.toString();
    }
}
