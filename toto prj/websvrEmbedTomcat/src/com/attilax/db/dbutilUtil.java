package com.attilax.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class dbutilUtil {

	public static Object execSqlQuery(String sql, DataSource datasouce) throws SQLException {
		QueryRunner qr4 = new QueryRunner(datasouce);
		
		List<Map<String, Object>> query = qr4.query(sql, new MapListHandler());
		return query;
	}
}
