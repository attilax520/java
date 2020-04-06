package com.attilax.db;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.naming.java.javaURLContextFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import ognl.Ognl;
import tomcatxpkg.DBPoolC3p0Util;
import tomcatxpkg.sonsyefen;

public class DatabaseMetaDataUtil {

	public static void main(String[] args) throws Exception {
		String ymlString = "H:\\gitCode\\tt-api\\com-tt-yxt\\src\\main\\resources\\application.yml";

		Map m = sonsyefen.getTestCfg(ymlString);

		// 非根节点取值需要#开头
		Object expression = Ognl.parseExpression("spring.datasource");

		Object value = Ognl.getValue(expression, m); // Ognl.getValue(expression);
		System.out.println(value);
		String url = (String) Ognl.getValue(Ognl.parseExpression("spring.datasource.url"), m);

		String u = (String) Ognl.getValue(Ognl.parseExpression("spring.datasource.username"), m);
		String p = Ognl.getValue(Ognl.parseExpression("spring.datasource.password"), m).toString();
		DataSource datasouce = DBPoolC3p0Util.getDatasouce("com.mysql.jdbc.Driver", url, u, p);

		Connection con = datasouce.getConnection();
		String addItemSqlString = getInsertSqlStr(con, "i_user_credit_item");
		System.out.println(addItemSqlString);

		// insert
		// i_user_credit_item(id,code,user_code,credit_way_code,credit_value,create_on,create_by,last_update_on,last_update_by,version,is_delete,abstractCode)values('@id@','@code@','@user_code@','@credit_way_code@','@credit_value@','@create_on@','@create_by@','@last_update_on@','@last_update_by@',@version@,@is_delete@,'@abstractCode@')

	}

	private static String getInsertSqlStr(Connection con,String tableName) throws SQLException {
		java.sql.DatabaseMetaData dbmd = con.getMetaData();
	//	String tableName = "i_user_credit_item";
		ResultSet rs = dbmd.getColumns(con.getCatalog(), "%", tableName, null);
		List list = getColumnsRs2list(rs);
		System.out.println(JSON.toJSONString(list, true));

		String colNamesString = getColNams(list);
		String colVals = getcolVals(list);
		String addItemSqlString = "insert {0}({1})values({2})";
		addItemSqlString = MessageFormat.format(addItemSqlString, tableName,colNamesString, colVals);
		return addItemSqlString;
	}

	private static List getColumnsRs2list(ResultSet rs) throws SQLException {
		List list = Lists.newArrayList();
		List list_colname = Lists.newArrayList();
		while (rs.next()) {
			Map map = Maps.newConcurrentMap();
			String columnName = rs.getString("COLUMN_NAME");
			String columnType = rs.getString("TYPE_NAME");
			int datasize = rs.getInt("COLUMN_SIZE");
			int digits = rs.getInt("DECIMAL_DIGITS");
			int nullable = rs.getInt("NULLABLE");
			map.put("COLUMN_NAME", columnName);
			map.put("TYPE_NAME", columnType);
			list.add(map);
		 

			String COLUMN_COMMENT = rs.getString("REMARKS");
		}
		return list;
	}

	private static String getcolVals(List list) {
		List Str_a = Lists.newArrayList();
		for (Object object : list) {
			Map map = (Map) object;
			if (map.get("TYPE_NAME").equals("INT") || map.get("TYPE_NAME").equals("BIGINT") || map.get("TYPE_NAME").equals("FLOAT")  )
				Str_a.add("@" + map.get("COLUMN_NAME") + "@");
			else {
				Str_a.add("'@" + map.get("COLUMN_NAME") + "@'");
			}

		}
		return Joiner.on(",").join(Str_a);
	}

	private static String getColNams(List list_colname) {

		List Str_a = Lists.newArrayList();
		for (Object object : list_colname) {
			Map map = (Map) object;
			Str_a.add("" + map.get("COLUMN_NAME") + "");
		}
		return Joiner.on(",").join(Str_a);
	}

}
