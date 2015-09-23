package com.alice.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * md5加密
 * 
 * @author hyl
 * 
 */
public class Md5Util {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 加密验证
     * 
     * @param params
     *            加密参数
     * @param skey
     *            秘钥
     * @return boolean
     */
    public static boolean MD5verification(Map<String, String[]> params, String skey) {
        StringBuilder rs = new StringBuilder(skey);
        String securit = "";
        long timestamp = 0;
        if (null != params.get("securit") && params.get("securit").length > 0)
            securit = params.get("securit")[0];
        if (null != params.get("time") && params.get("time").length > 0)
            timestamp = Long.parseLong(params.get("time")[0]);
        if (StringUtils.isNotBlank(securit)) {
            if ((System.currentTimeMillis() / 1000L - timestamp) > 600)
                return false;
            params.remove("securit");
            List<Map.Entry<String, String[]>> info = new ArrayList<Map.Entry<String, String[]>>(
                    params.entrySet());
            // 排序
            Collections.sort(info, new Comparator<Map.Entry<String, String[]>>() {
                public int compare(Map.Entry<String, String[]> o1, Map.Entry<String, String[]> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            for (int i = 0; i < info.size(); i++) {
                Entry<String, String[]> entry = info.get(i);
                String[] values = entry.getValue();
                if (null != values && values.length > 0) {
                    rs.append(values[0]);
                }
            }
            logger.debug("param.securit = " + securit + " || this.securit = "
                    + md5(md5(rs.toString()) + skey));
            if (securit.equals(md5(md5(rs.toString()) + skey)))
                return true;
        }
        return false;
    }

    /**
     * 进行md5加密
     * 
     * @param params
     *            加密参数
     * @param skey
     *            秘钥
     * @return String
     */
    public static String md5(Map<String, String> params, String skey) {
        List<Map.Entry<String, String>> info = new ArrayList<Map.Entry<String, String>>(
                params.entrySet());
        StringBuilder rs = new StringBuilder(skey);
        Collections.sort(info, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        for (int i = 0; i < info.size(); i++) {
            rs.append(info.get(i).getValue());
        }
        return md5(md5(rs.toString()) + skey);
    }

    /**
     * 进行md5加密
     * 
     * @param data
     *            加密参数
     * @return String
     */
    public static String md5(String data) {
        try {
            byte[] md5 = md5(data.getBytes("utf-8"));
            return toHexString(md5);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[] {};

    }

    public static String toHexString(byte[] md5) {
        StringBuilder buf = new StringBuilder();
        for (byte b : md5) {
            buf.append(leftPad(Integer.toHexString(b & 0xff), '0', 2));
        }
        return buf.toString();
    }

    public static String leftPad(String hex, char c, int size) {
        char[] cs = new char[size];
        Arrays.fill(cs, c);
        System.arraycopy(hex.toCharArray(), 0, cs, cs.length - hex.length(), hex.length());
        return new String(cs);
    }
    
    /**
     * 请求参数打印
     * 
     * @param request
     *            HttpServletRequest
     */
    @SuppressWarnings("unchecked")
	public static void loggerPrint(HttpServletRequest request) {
        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        logger.debug("=================mpalice debug print start=================");
        logger.debug("IP = " + request.getRemoteAddr());
        logger.debug("URL = " + request.getServletPath().toString());
        Set<Map.Entry<String, String[]>> set = params.entrySet();
        for (Iterator<Map.Entry<String, String[]>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String[]> entry = it.next();
            logger.debug("params : " + "key = " + entry.getKey() + "--->");
            String[] vals = entry.getValue();
            if (null != vals)
                for (int i = 0; i < vals.length; i++) {
                    logger.debug("params values " + i + " = " + vals[i]);
                }
        }
        logger.debug("=================mpalice debug print end================");
    }
}
