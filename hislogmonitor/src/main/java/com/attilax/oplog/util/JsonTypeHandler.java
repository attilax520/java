package com.attilax.oplog.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSON;

//import com.kylin.test.util.json.JsonUtil;

// 继承自BaseTypeHandler<Object> 使用Object是为了让JsonUtil可以处理任意类型
//  com.attilax.oplog.util.JsonTypeHandler
public class JsonTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter,
            JdbcType jdbcType) throws SQLException {
        
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        
        return JSON.parseObject(rs.getString(columnName), Object.class);//();
    }

    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        
    	return JSON.parseObject(rs.getString(columnIndex), Object.class);
     //   return JsonUtil.parse(rs.getString(columnIndex), Object.class);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
    	return JSON.parseObject(cs.getString(columnIndex), Object.class);
    //    return JsonUtil.parse(cs.getString(columnIndex), Object.class);
    }

}