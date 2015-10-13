package com.alice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alice.entity.User;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.common.Constant;
import com.alice.common.Logable;
import com.alice.service.AccountBoundService;
import com.alice.service.CoreService;
import com.alice.service.UserService;
import com.alice.util.BizUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 账号绑定Controller
 */
@RestController
@RequestMapping("accountBound")
public class AccountBoundController extends Logable {

    /**
     * 账号绑定Service
     */
    @Autowired
    private AccountBoundService accountBoundService;

    /**
     * 微信公众平台核心Service
     */
    @Autowired
    private CoreService coreService;

    /**
     * 会员Service
     */
    @Autowired
    private UserService userService;

    /**
     * 账号绑定用户页面
     *
     * @param request 请求对象
     */
    @RequestMapping("boundview.html")
    public ModelAndView boundview(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String code = request.getParameter("code");
            if (StringUtils.isBlank(code)) {
                logger.debug(" 尝试重定向 重新获取code——》start");
                modelAndView.setViewName(BizUtils.toRedirect(Constant.menu_left));
                return modelAndView;
            }
            logger.debug("get code success ——》" + code);
            String openid = this.coreService.getOpenId(code);
            logger.debug("——》code: " + code + ",——》 openid: " + openid + "}");

            if (StringUtils.isBlank(openid)) {
                openid = (String) request.getSession().getAttribute("openid");
                logger.debug("——》code: " + code + ",——》 openid: " + openid + "}");
            }

            if (StringUtils.isNotBlank(openid)) {
                HttpSession session = request.getSession();
                session.setAttribute("openid", openid);

                User user = this.userService.findOneByWeixin(openid);
                if (user != null) {
                    session.setAttribute("userId", user.getId() + "");
                    user.setWeixin("");
                    model.addAttribute("user", user);
                } else {
                    model.addAttribute("user", null);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        modelAndView.setViewName("/failure");
        return modelAndView;
    }

    /**
     * 提交绑定
     */
    @ResponseBody
    @RequestMapping("bound.html")
    public String bound(HttpServletRequest request, User user) {
        logger.debug("---> param: {memberPhone: " + user.getPhone() + "}");
        Map<String, Object> resultMap = new HashMap<>();
        Gson gson = new Gson();
        Boolean success = false;
        String msg = "some msg from bound";
        try {
            String openid = (String) request.getSession().getAttribute("openid");
            if (StringUtils.isNotBlank(openid)) {
                user.setWeixin(openid);
                success = this.accountBoundService.accountBound(user);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return gson.toJson(resultMap);
    }

    /**
     * 发送验证码
     *
     * @param user 用户信息
     */
    @ResponseBody
    @RequestMapping("sendCode.html")
    public void sendCode(User user) {
        logger.debug("*********** phone = " + user.getPhone() + "  *********  name = "
                + user.getName());
        this.accountBoundService.sendCode(user);
    }
}
