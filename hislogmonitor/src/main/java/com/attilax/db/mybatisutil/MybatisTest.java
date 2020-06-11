package com.attilax.db.mybatisutil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
 
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

 
import com.google.common.collect.Maps;

 

public class MybatisTest {

	public static void main(String[] args) throws SQLException {
		//t2();
		String cfg = "C:\\0wkspc\\移动医疗源码\\移动医护\\移动护士站\\honurse\\src\\com\\mbmed\\nurse\\vaf2\\mapper\\Vaf2Mapper.xml";
		String sql = getMybaticsCfgedSqlBysqlid(cfg, "getBinliFiletype");
	}

	private static void t2() throws SQLException {
		String sqlid = "adviceSousuo_kucui";
		String cfg = "C:\\0wkspc\\clinical\\src\\main\\java\\com\\cnhis\\cloudhealth\\clinical\\clidoctor\\clischemedefine\\mapper\\CliSchemeDefineMapper.xml";
		cfg = "C:\\0wkspc\\clinical\\src\\main\\java\\com\\cnhis\\cloudhealth\\clinical\\clidoctor\\lucence\\mapper\\lucenceMapper.xml";
		String s = getMybaticsCfgedSqlBysqlid(cfg, "queryDoctorAdviceList1");

		Map m = Maps.newHashMap();
		m.put("corporationid", -5);
		m.put("bck01", 1);
		m.put("yiyuanid", 1);

		String trueSql = getTrueSql(s, m);
		System.out.println(trueSql);

		Statement Statement1 = getStt();
		long startTime = System.currentTimeMillis();
		ResultSet rs1 = Statement1.executeQuery(trueSql);
		long endTime = System.currentTimeMillis();
		long excTime = (long) (endTime - startTime);
		System.out.println(excTime);
	}
	Connection conn;
	public   Connection get_conn() {

		SqlSession session = getSqlSession();
		  conn = session.getConnection();
return conn;
	 
	}
	
	protected static Statement getStt() {

		SqlSession session = getSqlSession();
		Connection conn = session.getConnection();

		Statement st = null;
		try {
			conn.setAutoCommit(true);
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return st;
	}

	private static SqlSession getSqlSession() {
		MybatisSessionFactory.CONFIG_FILE_LOCATION = "/com/attilax/db/mybatisutil/mybatis_postgresql.xml";
		SqlSession session = MybatisSessionFactory.getSession();
		return session;
	}

	private static String getTrueSql(String sql, Map<String, Object> m) {
		for (Map.Entry entry : m.entrySet()) {
			String key = (String) entry.getKey();
			key = key.trim();
			System.out.println(key + ":" + entry.getValue());

			sql = sql.replaceAll("\\#\\{" + key + "\\}", getValueByTypeinSql(entry.getValue()));
		}
		return sql;
	}

	private static String getValueByTypeinSql(Object value) {
		if (value.getClass() == String.class)
			return "'" + value.toString() + "'";
		if (value.getClass() == Integer.class)
			return value.toString();
		throw new RuntimeException("getValueByTypeinSql:: value not jude type");
	}

	private static String getMybaticsCfgedSqlBysqlid(String f, String sqlid) {
		// TODO Auto-generated method stub

		SAXBuilder builder = new SAXBuilder(false);

		Document doc;
		try {
			doc = builder.build(f);
		} catch (JDOMException | IOException e) {
			throw new RuntimeException(e);
		}

		Element books = doc.getRootElement();

		List<Element> definitions_eles = books.getChildren("select");
		for (Element e : definitions_eles) {
			System.out.println(e.getAttribute("id"));
			if (e.getAttribute("id").getValue().equals(sqlid))
				return e.getText();
		}

		// Element process_ele=books.getChild("process");

		// System.out.println(process_ele.getAttributeValue("deadlineLimit"));

		System.out.println("--f");
		return sqlid;

	}

}
