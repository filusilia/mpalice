package com.alice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alice.dao.AddressMapper;
import com.alice.dao.UserMapper;
import com.alice.entity.Address;
import com.alice.entity.User;

/**
 * 地址服务
 */
@Service
public class AddressService {

	/**
	 * 地址dao
	 */
	@Autowired
	private AddressMapper addressMapper;
	
	
	/**
	 * 地址dao
	 */
	@Autowired
	private UserMapper userMapper;
	
	
	public User findMemberByOpenId(String openId){
		return userMapper.findByWeixin(openId);
	}
	
	
	
	
	
	/**
	 * 获取地址列表
	 * 
	 * @param memberId
	 * 成员id
	 * @return
	 */
	public List<Address> getAddressList(Integer memberId) {
		if (memberId != null) {
			return addressMapper.findAll(memberId);
		} else {
			return null;
		}
	}

	/**
	 * 获取地址详情
	 * 
	 * @param addressId
	 *            地址id
	 * @return
	 */
	public Address getAddressOne(Integer addressId) {
		if (addressId != null) {
			return addressMapper.findOne(addressId);
		} else {
			return null;
		}
	}

	/**
	 * 添加地址
	 * 
	 * @param address
	 *            地址实体
	 * @return
	 */
	public boolean addAddress(Address address) {
		if (address != null) {
			return addressMapper.save(address);
		} else {
			return false;
		}
	}

	/**
	 * 更新地址
	 * 
	 * @param address
	 *            地址实体
	 * @return
	 */
	public boolean updateAddress(Address address) {
		if (address != null) {
			return addressMapper.update(address);
		} else {
			return false;
		}
	}

	/**
	 * 删除地址
	 * 
	 * @param addressId
	 *            地址id
	 * @return
	 */
	public boolean deleteAddress(Integer addressId) {
		if (addressId != null) {
			return addressMapper.delete(addressId);
		} else {
			return false;
		}
	}
}
