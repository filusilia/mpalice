package com.alice.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alice.model.Sign;

/**
 * 微信消息验证工具类
 */
public class SignUtil {

    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /**
     * 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
     *
     * @param wxToken    token
     * @param checkModel CheckModel
     * @return String
     */
    public static String checkSignature(String wxToken, Sign checkModel) {
        String signature = checkModel.getSignature();
        Long timestamp = checkModel.getTimestamp();
        Long nonce = checkModel.getNonce();
        String echostr = checkModel.getEchostr();

        logger.debug("token = " + wxToken);
        logger.debug("signature = " + signature);
        logger.debug("timestamp = " + timestamp);
        logger.debug("nonce = " + nonce);
        logger.debug("echostr = " + echostr);

        if (signature != null) {
            String[] str = {wxToken, timestamp + "", nonce + ""};
            Arrays.sort(str); // 字典序排序
            String bigStr = str[0] + str[1] + str[2];
            logger.debug("bigStr = " + bigStr);
            // SHA1加密
            String digest = EncoderUtil.encode("SHA1", bigStr).toLowerCase();
            logger.debug("digest = " + digest);
            // 确认请求来至微信
            if (digest.equals(signature)) {
                // 最好此处将echostr存起来，以后每次校验消息来源都需要用到
                return echostr;
            }
        }
        return "error";
    }

}
