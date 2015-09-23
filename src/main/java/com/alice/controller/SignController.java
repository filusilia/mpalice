package com.alice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alice.service.SignService;

/**
 * 签到Controller
 * 
 * @author FanMing
 * 
 */
@Controller
@RequestMapping("sign")
public class SignController {

	/**
	 * 签到Service
	 */
	@Autowired
	private SignService signService;

	/**
	 * 每日签到
	 * 使用CoreController中的方法
	 */
	@RequestMapping("everyDaySign")
	public void everyDaySign(String weixin) {
		this.signService.addEgral(weixin);
	}
}
