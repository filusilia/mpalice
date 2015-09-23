package com.alice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.common.Logable;
import com.alice.dao.UserMapper;
import com.alice.entity.User;

/**
 * 会员Service
 * 
 * @author zhangyanhui
 * 
 */
@Service
public class MemberService extends Logable {
	
	/**
	 * 会员DAO
	 */
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 获取会员
	 * 
	 * @param weixin
	 *            会员微信号
	 * @return
	 */
	public User findOneByWeixin(String weixin) {
		return this.userMapper.findByWeixin(weixin);
	}
	
	/**
	 * 获取会员
	 * 
	 * @param phone
	 *            会员手机号
	 * @return
	 */
	public User getMemberByPhone(long phone) {
		return this.userMapper.findByPhone(phone);
	}

}
