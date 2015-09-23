package com.alice.service;

import com.alice.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.common.Logable;


/**
 * 签到Service
 * 
 * @author FanMing
 * 
 */
@Service
public class SignService extends Logable {

    /**
     * 会员DAO
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 签到
     * @param openid
     *            微信号(weixin)
     */
	public String addEgral(String openid) {
    	return "service";
    }
    
}
