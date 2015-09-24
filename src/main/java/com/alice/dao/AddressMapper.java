package com.alice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.alice.entity.Address;

/**
 * 地址DAO
 */
@Repository
public interface AddressMapper {

	/**
	 * 保存地址
	 * 
	 * @param address
	 *            Address
	 * @return boolean
	 */
	@Insert("INSERT INTO sp_address(member_id,`name`,area,info,phone,create_time,update_time) "
			+ "VALUES(#{memberId},#{name},#{area},#{info},#{phone},#{createTime},#{updateTime})")
	public boolean save(Address address);

	/**
	 * 删除地址
	 * 
	 * @param id
	 *            ID
	 * @return boolean
	 */
	@Delete("DELETE FROM sp_address WHERE id = #{id}")
	public boolean delete(Integer id);

	/**
	 * 更新地址
	 * 
	 * @param address
	 *            Address
	 * @return boolean
	 */
	@Update("UPDATE sp_address sp SET sp.area=#{area},sp.`name`=#{name},sp.info=#{info},sp.phone=#{phone},sp.update_time=#{updateTime} WHERE sp.id=#{id}")
	public boolean update(Address address);

	/**
	 * 查询地址
	 * 
	 * @param id
	 *            ID
	 * @return Address
	 */
	@Select("SELECT sp.id,sp.member_id,sp.`name`,sp.area,sp.info,sp.phone,sp.create_time,sp.update_time FROM sp_address sp WHERE sp.id=#{id}")
	@Results(value = { @Result(column = "id", property = "id"),
			@Result(column = "member_id", property = "memberId"),
			@Result(column = "`name`", property = "name"),
			@Result(column = "area", property = "area"),
			@Result(column = "info", property = "info"),
			@Result(column = "phone", property = "phone"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime") })
	public Address findOne(Integer id);

	/**
	 * 查询地址数量
	 * 
	 * @param memberId
	 *            会员ID
	 * @return long
	 */
	@Select("SELECT COUNT(sp.id) count FROM sp_address sp WHERE sp.member_id = #{memberId}")
	@Results(value = { @Result(column = "count", property = "count") })
	public long count(Integer memberId);

	/**
	 * 查询所有地址
	 * 
	 * @param page
	 *            Page
	 * @param memberId
	 *            会员ID
	 * @return List<Address>
	 */
	@Select("SELECT sp.id,sp.member_id,sp.`name`,sp.area,sp.info,sp.phone,sp.create_time,sp.update_time "
			+ "FROM sp_address sp WHERE sp.member_id = #{memberId} order by sp.id desc")
	@Results(value = { @Result(column = "id", property = "id"),
			@Result(column = "member_id", property = "memberId"),
			@Result(column = "`name`", property = "name"),
			@Result(column = "area", property = "area"),
			@Result(column = "info", property = "info"),
			@Result(column = "phone", property = "phone"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime") })
	public List<Address> findAll(@Param(value = "memberId") Integer memberId);

	/**
	 * 通过条件查询地址信息
	 * 
	 * @param phone
	 *            电话
	 * @return List<Address>
	 */
	@SelectProvider(type = AddressProvider.class, method = "findAddress")
	@Results(value = { @Result(column = "id", property = "id"),
			@Result(column = "member_id", property = "memberId"),
			@Result(column = "`name`", property = "name"),
			@Result(column = "area", property = "area"),
			@Result(column = "info", property = "info"),
			@Result(column = "phone", property = "phone"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime") })
	public List<Address> findAddress(@Param(value = "phone") String phone);

}
