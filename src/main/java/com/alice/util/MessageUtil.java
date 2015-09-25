package com.alice.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alice.model.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类
 */
public class MessageUtil {

    public static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            List<Element> elements = e.elements();
            if (null != elements) {
                for (Element ee : elements) {
                    map.put(ee.getName(), ee.getText());
                    logger.debug("-----------------" + ee.getName() + " = " + ee.getText());
                }
            }
            map.put(e.getName(), e.getText());
            logger.debug("-----------------" + e.getName() + " = " + e.getText());
        }
        // 释放资源
        inputStream.close();
        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        try {
            if (null == textMessage)
                logger.debug("--------------------> textMessage is null");
            if (null == xstream)
                logger.debug("--------------------> xstream is null");
            xstream.alias("xml", textMessage.getClass());
            logger.debug("-----------------> textMessage params start :");
            logger.debug("----------------->textMessage.getContent() = " + textMessage.getContent());
            logger.debug("----------------->textMessage.getCreateTime() = "
                    + textMessage.getCreateTime());
            logger.debug("----------------->textMessage.getErrorCode() = " + textMessage.getErrorCode());
            logger.debug("----------------->textMessage.getErrorMessage() = " + textMessage.getErrorMessage());
            logger.debug("----------------->textMessage.getEvent() = " + textMessage.getEvent());
            logger.debug("----------------->textMessage.getEventKey() = "
                    + textMessage.getEventKey());
            logger.debug("----------------->textMessage.getFromUserName() = "
                    + textMessage.getFromUserName());
            logger.debug("----------------->textMessage.getMsgType() = " + textMessage.getMsgType());
            logger.debug("----------------->textMessage.getToUserName() = "
                    + textMessage.getToUserName());
            logger.debug("-----------------> textMessage params end");
            return xstream.toXML(textMessage);
        } catch (Exception e) {
            logger.error(DateUtil.getStrDate(new Date(), "yyyy-MM-dd HH:mm")
                    + " textMessageToXml error");
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}
