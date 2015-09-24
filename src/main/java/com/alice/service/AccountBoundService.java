package com.alice.service;

import java.util.HashMap;
import java.util.Map;

import com.alice.common.Logable;
import com.alice.entity.User;
import org.springframework.stereotype.Service;

/**
 * 账号绑定Service
 */
@Service
public class AccountBoundService extends Logable {

    /**
     * 账号绑定
     *
     * @param user 用户信息
     */

    public Boolean accountBound(User user) {
        Map<String, Object> map = new HashMap<>();
        Boolean success = false;
        String msg = "just accountbound method run success . now return some message.";
        map.put("success",success);
        map.put("msg",msg);
        return success;
    }

    /**
     * 向手机发送验证码
     */
    public void sendCode(User user) {
//        Pattern p = Pattern.compile("^1[3|5|6|7|8][0-9]\\d{8}$");
//        try {
//            Matcher m = p.matcher(user.getPhone());
//            if (m.matches()) {
//                String code = BizUtils.randomNum(4);
//                Cache cache = this.defaultMemcachedClient.getCache();
//                cache.set(user.getPhone().toString(), 300, code, SerializationType.JAVA);
//                SendSMSUtil.getSendMessage(user.getPhone(), code);
//            } else {
//                logger.error("手机号格式不符合");
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
    }
}
