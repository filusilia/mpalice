package com.app;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alice.util.DateUtil;

public class App {

    public static void main(String[] args) {
        // System.err.println(DateUtil.getStrDate(1429777404 * 1000L,
        // "yyyy-MM-dd hh:mm:ss"));
        // System.err.println(fun("http://t.ppk365.com/236518870231"));
        // System.err.println(fun("http://315net.com/?t=000335459874"));
        // System.err.println(DateUtil.getStrDate(1430726183 * 1000L,
        // "yyyy-MM-dd HH:mm:ss"));
        System.err.println(System.currentTimeMillis() / 1000L);
        try {
            System.err.println(DateUtil.getCurrentDate().getTime() / 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fun(String resultText) {
        if (resultText.contains("http://t.ppk365.com/")
                || resultText.contains("http://315net.com/")) {
            return resultText.substring(resultText.length() - 12, resultText.length());
        }
        return "";
    }

    public static Map<String, String> parseXml() throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        /**
         * <xml> <ToUserName><![CDATA[gh_82479813ed64]]></ToUserName>
         * <FromUserName><![CDATA[ojpX_jig-gyi3_Q9fHXQ4rdHniQs]]></FromUserName>
         * <CreateTime>1412075435</CreateTime>
         * <MsgType><![CDATA[event]]></MsgType>
         * <Event><![CDATA[scancode_waitmsg]]></Event>
         * <EventKey><![CDATA[rselfmenu_0_0]]></EventKey> <ScanCodeInfo>
         * <ScanType><![CDATA[qrcode]]></ScanType>
         * <ScanResult><![CDATA[http://weixin
         * .qq.com/r/pUNnf4HEX9wgrcUc9xa3]]></ScanResult>
         * <EventKey><![CDATA[rselfmenu_0_0]]></EventKey> </ScanCodeInfo> </xml>
         */
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        xml.append("<ToUserName><![CDATA[gh_82479813ed64]]></ToUserName>");
        xml.append("<ScanCodeInfo>");
        xml.append("<ScanType><![CDATA[qrcode]]></ScanType>");
        xml.append("<ScanResult><![CDATA[http://weixin.qq.com/r/pUNnf4HEX9wgrcUc9xa3]]></ScanResult>");
        xml.append("<EventKey><![CDATA[rselfmenu_0_0]]></EventKey>");
        xml.append("</ScanCodeInfo>");
        xml.append("</xml>");

        InputStream inputStream = new ByteArrayInputStream(xml.toString().getBytes("UTF-8"));

        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            if ("ScanCodeInfo".equals(e.getName())) {
                List<Element> elements = e.elements();
                for (Element ee : elements) {
                    System.err.println("-----------------" + ee.getName() + " = " + ee.getText());
                }
            } else {
                map.put(e.getName(), e.getText());
                System.err.println("-----------------" + e.getName() + " = " + e.getText());
            }
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

}
