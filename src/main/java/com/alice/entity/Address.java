package com.alice.entity;

import java.io.Serializable;

import com.alice.util.StringEditor;

/**
 * 地址实体类
 * 
 * @author hyl
 * 
 */
public class Address implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -2573409795790068180L;

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * egral_ID
	 */
	private Integer egralId;

	/**
	 * 会员ID
	 */
	private Integer memberId;

	/**
	 * 收货人姓名
	 */
	private String name;

	/**
	 * 地区编码(省市县标准三级联动编码)
	 */
	private Integer area;

	/**
	 * 详细地址
	 */
	private String info;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name = StringEditor.convertText(name, true, false, false);
	}

	public void setName(String name) {
		this.name = StringEditor.reverseText(name, true, false, false);
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getInfo() {
		return info = StringEditor.convertText(info, true, false, false);
	}

	public void setInfo(String info) {
		this.info = StringEditor.reverseText(info, true, false, false);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getEgralId() {
		return egralId;
	}

	public void setEgralId(Integer egralId) {
		this.egralId = egralId;
	}

}
