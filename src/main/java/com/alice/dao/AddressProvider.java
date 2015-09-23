package com.alice.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alice.common.Logable;

public class AddressProvider extends Logable {

    public String findAddress(Map<String, Object> params) {
        BEGIN();
        SELECT("sp.id,sp.member_id,sp.`name`,sp.area,sp.info,sp.phone,sp.create_time,sp.update_time");
        FROM("sp_address sp");
        if (null != params.get("phone") && StringUtils.isNotBlank((String) (params.get("phone"))))
            WHERE("phone = #{phone}");
        StringBuilder sqlBuilder = new StringBuilder(SQL());
        logger.debug(sqlBuilder.toString());
        String sql = "SELECT sp.id,sp.member_id,sp.`name`,sp.area,sp.info,sp.phone,sp.create_time,sp.update_time FROM sp_address sp ";
        if (null != params.get("phone") && StringUtils.isNotBlank((String) (params.get("phone"))))
            sql = sql + " WHERE phone = " + (String) (params.get("phone"));
        return sqlBuilder.toString();
    }
}
