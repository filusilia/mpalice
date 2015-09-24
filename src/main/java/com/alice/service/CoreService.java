package com.alice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import com.google.gson.Gson;
import com.alice.common.Constant;
import com.alice.common.Logable;
import com.alice.dao.UserMapper;
import com.alice.model.AccessToken;
import com.alice.model.PageAccessToken;
import com.alice.model.SendTextMessage;
import com.alice.model.Text;
import com.alice.model.TextMessage;
import com.alice.util.HttpClientUtil;
import com.alice.util.MessageUtil;

/**
 * 微信公众平台核心Service
 */
@Service
public class CoreService extends Logable {

    /**
     * 签到Service
     */
    @Autowired
    private SignService signService;

    /**
     * 缓存工厂
     */
    @Autowired
    private CacheFactory defaultMemcachedClient;

    /**
     * 会员DAO
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpSession session;

    /**
     * 处理微信发来的请求
     *
     * @param request HttpServletRequest
     * @return Object
     */
    public Object processRequest(HttpServletRequest request) {
        try {
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            String msgType = ""; // 获取消息类型
            if (StringUtils.isNotBlank(requestMap.get(Constant.MSG_TYPE))){
                msgType = requestMap.get(Constant.MSG_TYPE).toLowerCase();
            }

            // 创建消息实体
            TextMessage message = new TextMessage();
            message.setToUserName(requestMap.get(Constant.FROM_USER_NAME));
            message.setFromUserName(requestMap.get(Constant.TO_USER_NAMR));
            logger.debug("requestMap.get(Constant.FROM_USER_NAME) = " + requestMap.get(Constant.FROM_USER_NAME));
            logger.debug("requestMap.get(Constant.TO_USER_NAME) = " + requestMap.get(Constant.TO_USER_NAMR));

            if (StringUtils.isNotBlank(requestMap.get(Constant.CREATE_TIME)))
                message.setCreateTime(Long.parseLong(requestMap.get(Constant.CREATE_TIME)));
            message.setMsgType(Constant.MESSAGE_TYPE_TEXT);

            switch (msgType) {
                case Constant.MESSAGE_TYPE_TEXT:
                    break;
                default:
                    break;
            }
            if (Constant.MESSAGE_TYPE_TEXT.equals(msgType)) {
                //FIXME  文字类型
                message.setContent("hello world");

            } else if (Constant.MESSAGE_TYPE_EVENT.equals(msgType)) {
                // event 推送 类型
                String event = requestMap.get(Constant.EVENT).toLowerCase();
                String eventKey = requestMap.get(Constant.EVENT_KEY);

                // 订阅-用户第一次关注
                if (Constant.EVENT_TYPE_SUBSCRIBE.equals(event)) {
                    message.setContent("Master！初次见面请多关照！\n" +
                            "这里是您的小助手晓晓！\n" +
                            "由于我还在开发中，可能会遇到很多奇怪的问题，但我一定会努力修正的！\n" +
                            "您可以发送“开发进度（version）”查看一下我现在拥有什么能力！\n" +
                            "那么，以后的日子就请多关照啦！（鞠躬）\n" +
                            "O(∩_∩)O");
                }

                if (Constant.EVENT_TYPE_CLICK.equals(event)){
                    // 签到点击
                    message.setContent(this.signService.addEgral(message.getToUserName()));

                }else if (Constant.EVENT_KEY_SCAN.equals(eventKey)) {
                    // 扫描结果处理
                    String code = getCode(requestMap.get("ScanResult"));
                    logger.debug("+++++++*** 扫描 code = " + code + " **** User = "
                            + message.getToUserName());
                    if ("".equals(code)) {
                        message.setContent("非常抱歉！\n您扫描的二维码无效，请扫描有效的二维码！");
                        return "";
                    }

                }
            }
            return MessageUtil.textMessageToXml(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 向客户端发送消息
     *
     * @param opendid 普通用户openid
     * @param content 消息内容
     */
    public void sendMsg(String opendid, String content) {
        StringBuilder url = new StringBuilder(
                "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=")
                .append(getAccessToken());
        SendTextMessage msg = new SendTextMessage();
        msg.setTouser(opendid);
        msg.setText(new Text(content));
        String rs = HttpClientUtil.sendPostSSLRequest(url.toString(), new Gson().toJson(msg));
        logger.debug(rs);
    }

    /**
     * 获取 wechat_access_token
     *
     * @return String
     */
    public String getAccessToken() {
        try {
            Cache cache = defaultMemcachedClient.getCache();
            String access_token = cache.get("wechat_access_token", SerializationType.JAVA);
            if (StringUtils.isNotBlank(access_token))
                return access_token;
            else {
                StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?");
                url.append("grant_type=client_credential&appid=").append(Constant.appid);
                url.append("&secret=").append(Constant.appsecret);
                String rs = HttpClientUtil.sendGetSSLRequest(url.toString(), null);
                access_token = new Gson().fromJson(rs, AccessToken.class).getAccess_token();
                cache.set("wechat_access_token", 7100, access_token, SerializationType.JAVA);
                return access_token;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 通过code获取openId
     *
     * @param code
     * @return String
     */
    public String getOpenId(String code) {
        try {
            StringBuilder url = new StringBuilder(
                    "https://api.weixin.qq.com/sns/oauth2/access_token?");
            url.append("appid=").append(Constant.appid.trim());
            url.append("&secret=").append(Constant.appsecret.trim());
            url.append("&code=").append(code);
            url.append("&grant_type=").append(Constant.grant_type.trim());
            long start = System.currentTimeMillis();
            String rs = HttpClientUtil.sendGetSSLRequest(url.toString(), null);
            logger.debug(">>>>>>>>>>>>>> getOpenId >>>>costTime : "
                    + (System.currentTimeMillis() - start));
            Gson gson = new Gson();
            PageAccessToken pageAccessToken = gson.fromJson(rs, PageAccessToken.class);
            if (null != pageAccessToken)
                return pageAccessToken.getOpenid();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 截取URL中的code参数
     *
     * @param url 二维码链接
     * @return String
     */
    public String getCode(String url) {

        if (StringUtils.isNotBlank(url)) {

            try {
                ArrayList<String> arr = getVerfi();

                for (String string : arr) {
                    Pattern pattern = Pattern.compile(string);
                    if (pattern.matcher(url).matches()) {
                        logger.debug("-----------getCode regix success ------------------");
                        Matcher matcher = pattern.matcher(url);
                        if (matcher.matches()) {
                            String group = matcher.group(2);
                            return group;
                        } else {
                            logger.debug("-----------getCode-->getGroup regix fail-------------------");
                            return "";
                        }
                    }
                }

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

//        if (StringUtils.isNotBlank(url)
//                && (url.contains("http://t.ppk365.com/") || url.contains("http://315net.com/"))) {
//            return url.substring(url.length() - 12, url.length());
//        }
        logger.debug("-----------getCode regix fail-------------------");
        return "";
    }


    /**
     * 获取正则数组
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public ArrayList<String> getVerfi() throws UnsupportedEncodingException, FileNotFoundException {

        ArrayList<String> arr = new ArrayList<String>();

        Cache cache = this.defaultMemcachedClient.getCache();
        try {
            if (null == cache.get("ver", SerializationType.JAVA)) {
                //memcached取值空
                logger.debug("-----------sorry ,now try to get memcached------------------");

                String path = session.getServletContext().getRealPath("/");//得到当前应用在服务器的绝对路径

                File file = new File(path + "WEB-INF/classes/verfi.properties");
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader reader = null;
                try {
//                    System.out.println("以行为单位读取文件内容，一次读一整行：");  
                    reader = new BufferedReader(read);

                    String tempString = null;
                    // 一次读入一行，直到读入null为文件结束  
                    while ((tempString = reader.readLine()) != null) {
                        arr.add(tempString.trim().toString());
                    }
                    reader.close();

                } catch (IOException e) {
                    file.toPath();
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }

                }

                try {
                    cache.set("ver", 64800, arr, SerializationType.JAVA);
                } catch (TimeoutException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (CacheException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                //正确获取memcached
                arr = cache.get("ver", SerializationType.JAVA);
            }
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CacheException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 清除memcached存储的正则数组
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public boolean clearMemcached() throws UnsupportedEncodingException, FileNotFoundException {

        Cache cache = this.defaultMemcachedClient.getCache();
        try {
            cache.delete("ver");
            return true;
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CacheException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}