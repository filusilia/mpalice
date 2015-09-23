package com.alice.entity;

import java.io.Serializable;

/**
 * 会员实体
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1169843538460283228L;

	/**
	 * 会员ID
	 */
	private Integer id;

	/**
	 * 会员名
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 地区编码
	 */
	private Integer area;

	/**
	 * 微信号
	 */
	private String weixin;
	/**
	 * 验证码
	 */
	private String code;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
