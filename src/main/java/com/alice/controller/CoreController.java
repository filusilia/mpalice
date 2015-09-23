package com.alice.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alice.common.Logable;
import com.alice.model.Sign;
import com.alice.service.CoreService;
import com.alice.util.SignUtil;

/**
 * 微信公众平台核心Controller
 * 
 * @author hyl
 * 
 */
@Controller
@RequestMapping("wechat")
public class CoreController extends Logable {

    /**
     * 微信公众平台核心Service
     */
    @Autowired
    private CoreService coreService;

    /**
     * 校验信息是否是从微信服务器发过来的
     * 
     * @param request
     *            HttpServletRequest
     * @param out
     *            PrintWriter
     */
    @RequestMapping(method = { RequestMethod.GET })
    public void doGet(HttpServletRequest request, PrintWriter out) {
        logger.debug("-----------------------------doGet--------------------");
        String wxToken = request.getParameter("weixin");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 验证有效性
        out.print(SignUtil.checkSignature(wxToken, new Sign(signature, timestamp, nonce, echostr)));
        out.flush();
        out.close();
    }

    /**
     * 微信消息的处理
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     */
    @RequestMapping(method = { RequestMethod.POST })
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        logger.debug("-----------------------------doPost--------------------");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 调用核心业务类接收消息、处理消息
            String respMessage = (String) coreService.processRequest(request);
            logger.debug(respMessage);
            out = response.getWriter();
            out.print(respMessage);
            out.close();
        } catch (Exception e) {
            out.print("error");
            logger.error(e.getMessage(), e);
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    
    /**
     * 清除memcached
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("clearmemcached")
    public void clearMemcached(HttpSession session,String str) throws UnsupportedEncodingException, FileNotFoundException {
        logger.debug("************* clear start ");
        if(coreService.clearMemcached()){
        	 logger.debug("************* memcached clear success on ");
        }else{
        	logger.debug("************* memcached clear success failed ,please try again ! ");
        }
    }
    
    /**
     * 测试方法
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("testver")
    public void testver() throws UnsupportedEncodingException, FileNotFoundException {
        logger.debug("************* test start ");
        logger.debug("http://www.baidu.com/45fsdf45     getcode  -->"+coreService.getCode("http://baidu.com/45fsdf45"));
        logger.debug("http://www.baidu.com/gff/45fsdf45     getcode  -->"+coreService.getCode("http://t.baidu.com/45fsdf45"));
        logger.debug("http://www.baidu.com/45fsdf45     getcode  -->"+coreService.getCode("http://t.baidu.com/45fsdf45"));
    }
}
