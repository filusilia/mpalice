package com.alice.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * 发短信工具类
 * 
 * 1.在相应项目的resources文件夹中加入SMS.properties配置文件
 * 2.在相应项目的applicationContext.xml文件中加入以下内容 <context:property-placeholder
 * location="classpath:*.properties" /> <!-- 发短信工具类 --> <bean id="sendSMSUtil"
 * class="com.ppk.common.util.SendSMSUtil"> <property name="URL"
 * value="${SMS.URL}" /> <property name="ECECCID" value="${SMS.ECECCID}" />
 * <property name="Password" value="${SMS.Password}" /> </bean>
 * 
 * @author zhangyanhui
 * 
 */
public class SendSMSUtil {

    /**
     * 创建日志文件
     */
    static Logger logger = Logger.getLogger(SendSMSUtil.class);

    /**
     * 地址
     */
    private static String URL;

    /**
     * 标识
     */
    private static String ECECCID;

    /**
     * 密码
     */
    private static String Password;

    /**
     * 短信模板 【拍拍看】
     */
    private static String template;

    /**
     * 获得消息
     * 
     * @param mobile
     *            手机号
     * @param SMSContent
     *            发送的验证码
     * @return
     * @throws Exception
     *             抛出异常
     */
    public static boolean getSendMessage(String mobile, String SMSContent) throws Exception {
        String md5 = DigestUtils.md5Hex((SMSContent + template + Password).getBytes("utf-8"));
        SMSContent = URLEncoder.encode(SMSContent + template, "utf-8");
        String sendURL = String.format(URL, ECECCID, md5, mobile, SMSContent);
        getHttp(sendURL);
        return true;
    }

    public static void getHttp(String myurl) {
        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL(myurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("user-agent",
                    "mozilla/4.0 (compatible; msie 6.0; windows 2000)");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);
            connection.connect();
            logger.debug("-------------->debug myurl:" + myurl + ",length:"
                    + connection.getContentLength() + ",status:" + connection.getResponseCode());
            StringBuffer sb = new StringBuffer();
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(in, "gbk"));
                String str = breader.readLine();
                while (str != null) {
                    sb.append(str);
                    str = breader.readLine();
                }
                in.close();
            }
            logger.debug(sb.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            url = null;
            if (null != connection) {
                connection.disconnect();
                connection = null;
            }
        }
    }

    /**
     * 获得消息
     * 
     * @param mobile
     *            手机号
     * @param SMSContent
     *            发送的验证码
     * @return
     * @throws Exception
     *             抛出异常
     */
    public static boolean getSendMessage_backup(String mobile, String SMSContent) throws Exception {
        HttpClient httpClient = new HttpClient();
        logger.info("------URL---" + URL);
        String sendURL = String.format(URL, mobile, SMSContent, ECECCID, Password);
        logger.debug("----------------> sms request url = " + sendURL);
        GetMethod method = new GetMethod(sendURL);
        httpClient.executeMethod(method);
        String result = new String(method.getResponseBodyAsString().getBytes("utf-8"));
        if (!result.contains("操作成功")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置url
     * 
     * @param URL
     *            传入的url
     */
    public void setURL(String URL) {
        if (null == SendSMSUtil.URL)
            SendSMSUtil.URL = URL;
    }

    /**
     * 标识
     * 
     * @param ECECCID
     *            传入的标识
     */
    public void setECECCID(String ECECCID) {
        if (null == SendSMSUtil.ECECCID)
            SendSMSUtil.ECECCID = ECECCID;
    }

    /**
     * 设置密码
     * 
     * @param Password
     *            设置密码
     */
    public void setPassword(String Password) {
        if (null == SendSMSUtil.Password)
            SendSMSUtil.Password = Password;
    }

    /**
     * 设置模板
     * 
     * @param template
     */
    public void setTemplate(String template) {
        if (null == SendSMSUtil.template)
            SendSMSUtil.template = template;
    }

}
