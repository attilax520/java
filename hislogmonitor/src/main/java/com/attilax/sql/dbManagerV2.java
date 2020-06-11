package com.attilax.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.data.csv.csvService;
import com.attilax.db.ResultSetUtil;
import com.attilax.util.ExUtil;
import com.attilax.util.csvWzHeadService;
import com.attilax.web.respService;

public class dbManagerV2 {

	public static void main(String[] args) throws SQLException, IOException {
		Connection connection = null;
		String cfg = "url=jdbc:sqlserver://192.168.1.17;databaseName=HealthOne,username=sa,password=123456,driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver";
		connection = getConn(cfg);

		String sql = "select 1 ";
		sql=FileUtils.readFileToString(new File("C:\\Users\\attilax\\Desktop\\sql\\querys61.txt"));
		sql="select top 1 * from PAA1 where acf01=2";
		sql="select top 1 com.pkg.class1.meth(col1),* from PAA1 where acf01=2";
		String jsonString = query(connection, sql);
	//	jsonString=new csvService().getCSVString(jsonString);
		
		
		System.out.println(jsonString);
	}

	public static String query(Connection connection, String sql) throws SQLException {
		Statement preparedStatement = connection.createStatement();// (

		ResultSet rs = preparedStatement.executeQuery(sql);
		;
		List li = ResultSetUtil.toList(rs);
		String jsonString = JSON.toJSONString(li);
		return jsonString;
	}

	public static Connection getConn(String cfg) throws SQLException {
		Connection connection;
		Map m = csvWzHeadService.parseDbcfg(cfg);
		// csvWzHeadService.parseDbcfg(s)

		Statement statement = null;

		String url = (String) m.get("url");
		String user = (String) m.get("username");
		String password = (String) m.get("password");
		try {
			Class.forName(m.get("driverClass").toString());
		} catch (ClassNotFoundException e1) {
			ExUtil.throwExV2(e1);
		}
		connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

}
