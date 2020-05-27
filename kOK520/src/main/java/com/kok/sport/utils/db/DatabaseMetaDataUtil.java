package com.kok.sport.utils.db;

//package com.attilax.db;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.naming.java.javaURLContextFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.utils.JsonGsonUtil;

import ognl.Ognl;
//import tomcatxpkg.DBPoolC3p0Util;
//import tomcatxpkg.sonsyefen;
@SuppressWarnings("all")
public class DatabaseMetaDataUtil {

	public static void main(String[] args) throws Exception {
//		String ymlString = "H:\\gitCode\\tt-api\\com-tt-yxt\\src\\main\\resources\\application.yml";
//
//		Map m = null;// = sonsyefen.getTestCfg(ymlString);
//
//		// 非根节点取值需要#开头
//		Object expression = Ognl.parseExpression("spring.datasource");
//
//		Object value = Ognl.getValue(expression, m); // Ognl.getValue(expression);
//		System.out.println(value);
//		String url = (String) Ognl.getValue(Ognl.parseExpression("spring.datasource.url"), m);
//
//		String u = (String) Ognl.getValue(Ognl.parseExpression("spring.datasource.username"), m);
//		String p = Ognl.getValue(Ognl.parseExpression("spring.datasource.password"), m).toString();
//		DataSource datasouce = null;// = DBPoolC3p0Util.getDatasouce("com.mysql.jdbc.Driver", url, u, p);
//
//		Connection con = datasouce.getConnection();
//		String addItemSqlString = getInsertSqlStr(con, "i_user_credit_item");
//		System.out.println(addItemSqlString);

		// insert
		// i_user_credit_item(id,code,user_code,credit_way_code,credit_value,create_on,create_by,last_update_on,last_update_by,version,is_delete,abstractCode)values('@id@','@code@','@user_code@','@credit_way_code@','@credit_value@','@create_on@','@create_by@','@last_update_on@','@last_update_by@',@version@,@is_delete@,'@abstractCode@')

	SqlSession sesss=	MybatisUtil.getConn();
	Configuration configuration = sesss.getConfiguration();
	//liag json all err lib
//	System.out.println( JSON.toJSON(configuration));
//	System.out.println( JsonGsonUtil.toStrFromObj(configuration));
	Connection connection = sesss.getConnection();  //can  get db url
//	 System.out.println( JsonGsonUtil.toStrFromObj(connection));  err
	java.sql.DatabaseMetaData dbmd = connection.getMetaData();
	}

	private static String getInsertSqlStr(Connection con, String tableName) throws SQLException {
	 	List list = getColsList(con, tableName);
		System.out.println(JSON.toJSONString(list, true));

		String colNamesString = getColNams(list);
		String colVals = getcolVals(list);
		String addItemSqlString = "insert {0}({1})values({2})";
		addItemSqlString = MessageFormat.format(addItemSqlString, tableName, colNamesString, colVals);
		return addItemSqlString;
	}

	public static List getColsList(Connection con, String tableName )
			  {
		try {
		java.sql.DatabaseMetaData dbmd = con.getMetaData();
		ResultSet rs;
		
			rs = dbmd.getColumns(con.getCatalog(), "%", tableName, "%");
	
		List list = toList(rs);
		if(list.size()==0)
			throw new RuntimeException("table not exist maybe表名可能不存在 :"+tableName);
		return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

//	private static List getColumnsRs2list(ResultSet rs) throws SQLException {
//		List list = Lists.newArrayList();
//		List list_colname = Lists.newArrayList();
//		while (rs.next()) {
//			Map map = Maps.newConcurrentMap();
//			String columnName = rs.getString("COLUMN_NAME");
//			String columnType = rs.getString("TYPE_NAME");
//			int datasize = rs.getInt("COLUMN_SIZE");
//			int digits = rs.getInt("DECIMAL_DIGITS");
//			int nullable = rs.getInt("NULLABLE");
//			map.put("COLUMN_NAME", columnName);
//			map.put("TYPE_NAME", columnType);
//			list.add(map);
//
//			String COLUMN_COMMENT = rs.getString("REMARKS");
//		}
//		return list;
//	}

	// ResultSet转换为List的方法

	private static List toList(ResultSet rs) throws SQLException {

		List list = new ArrayList();

		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount(); // Map rowData;

		while (rs.next()) { // rowData = new HashMap(columnCount);

			Map rowData = new HashMap();

			for (int i = 1; i <= columnCount; i++) {

				rowData.put(md.getColumnName(i), rs.getObject(i));

			}

			list.add(rowData);

		}
		return list;

	}

	private static String getcolVals(List list) {
		List Str_a = Lists.newArrayList();
		for (Object object : list) {
			Map map = (Map) object;
			if (map.get("TYPE_NAME").equals("INT") || map.get("TYPE_NAME").equals("BIGINT")
					|| map.get("TYPE_NAME").equals("FLOAT"))
				Str_a.add("@" + map.get("COLUMN_NAME") + "@");
			else {
				Str_a.add("'@" + map.get("COLUMN_NAME") + "@'");
			}

		}
		return Joiner.on(",").join(Str_a);
	}

	public static String getColNams(List list_colname) {

		List Str_a = Lists.newArrayList();
		for (Object object : list_colname) {
			Map map = (Map) object;
			Str_a.add("" + map.get("COLUMN_NAME") + "");
		}
		return Joiner.on(",").join(Str_a);
	}

	public static List<Map> getColsListNotNull(Connection connection, String tableName) throws Exception {
		 List<Map>  cols=getColsList(connection, tableName);
		
		List<Map> result = cols.stream().filter(map_item -> {

			return  map_item.get("NULLABLE").toString().equals("0")  ;
			// return true;

		}).collect(Collectors.toList());
		 
		return result;
	}

}
