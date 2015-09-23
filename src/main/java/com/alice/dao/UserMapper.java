package com.alice.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.alice.entity.User;

/**
 * 会员Mapper
 */
@Repository
public interface UserMapper {
	
	/**
	 * 会员查询 通过手机号
	 * 
	 * @param phone
	 * 			手机号
	 * @return
	 */
	@Select("SELECT * FROM sp_member WHERE phone=#{phone}")
	User findByPhone(long phone);
	
	/**
	 * 会员查询 通过微信号
	 * 
	 * @param weixin
	 * @return
	 */
	@Select("SELECT * FROM sp_member WHERE weixin=#{weixin}")
	User findByWeixin(String weixin);

	/**
	 * 会员查询 通过ID
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM sp_member WHERE.id=#{id}")
	User findById(String id);

	/**
	 * 修改-会员信息
	 * 
	 * @param user
	 * 			会员实体
	 * @return
	 */
	@Update("UPDATE sp_member mem SET mem.weixin=#{weixin} WHERE mem.id=#{id}")
	boolean update(User user);

	/**
	 * 保存
	 * 
	 * @param user
	 * @return boolean
	 */
	@Insert("INSERT INTO sp_member(name,phone) VALUES(#{name},#{phone})")
	boolean save(User user);
	
	/**
	 * 保存
	 * @param name
	 * @return boolean
	 */
	@Insert("INSERT INTO sp_member(name,phone) VALUES(#{name},#{phone})")
	boolean saveNameAndPhone(@Param(value = "name") String name, @Param(value = "phone") long phone);
}
