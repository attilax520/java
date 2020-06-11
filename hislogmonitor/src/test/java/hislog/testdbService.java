package hislog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class testdbService {
	
	public static void main(String[] args) {
		for(int i=71;i<100;i++)
		{
			String tmp="  VCF@n@=\"${VCF@n@}\" ";
			tmp=tmp.replaceAll("@n@", String.valueOf(i));
			System.out.print(" "+tmp);
		}
	}
	

	public Connection getConnection() {

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class.forName driver err,drive class name is :  ", e);
		}

		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.17;databaseName=HealthOne", "sa", "123456");
			// System.out.println("get conn: "+ conn+" at:"+timeUtil.Now_CST());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	public List findBySql(String sql) {
		Connection conn;
		try {
			conn = getConnection();

			// 鍒涘缓涓�涓猀ueryRunner
			QueryRunner queryRunner = new QueryRunner(true);
			List<Map<String, Object>> list;

			list = queryRunner.query(conn, sql, new MapListHandler());

			return list;

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
