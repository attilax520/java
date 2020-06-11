package com.attilax.coreLuni.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.attilax.str.strService;
import com.google.common.collect.Maps;
 

public class MybatisServiceNodepMybajar {

	public static void main(String[] args) throws SQLException, IOException {
		//t2();
		String cfg = "C:\\0wkspc\\移动医疗源码\\移动医护\\移动护士站\\honurse\\src\\com\\mbmed\\nurse\\vaf2\\mapper\\Vaf2Mapper.xml";
		
		
		List params=MybatisServiceNodepMybajar.getParams(cfg,"getBinliFiletype");
		
				
		System.out.println(params);
	}

	private static List getParams(String cfg, String string) {
		String t = null;
		try {
			  t=FileUtils.readFileToString(new File(cfg));
		} catch (IOException e) {
			ExUtil.throwExV2(e);
		}
		String sql = getMybaticsCfgedSqlBysqlid("file:///"+cfg, "getBinliFiletype");
		return (List) strService .newx(). findV2(strService.mybatisParamTmplt, sql).mybatsiParamsNoWarp().getRztLi();
	}

	private static void t2() throws SQLException {
//		String sqlid = "adviceSousuo_kucui";
//		String cfg = "C:\\0wkspc\\clinical\\src\\main\\java\\com\\cnhis\\cloudhealth\\clinical\\clidoctor\\clischemedefine\\mapper\\CliSchemeDefineMapper.xml";
//		cfg = "C:\\0wkspc\\clinical\\src\\main\\java\\com\\cnhis\\cloudhealth\\clinical\\clidoctor\\lucence\\mapper\\lucenceMapper.xml";
//		String s = getMybaticsCfgedSqlBysqlid(cfg, "queryDoctorAdviceList1");
//
//		Map m = Maps.newHashMap();
//		m.put("corporationid", -5);
//		m.put("bck01", 1);
//		m.put("yiyuanid", 1);
//
//		String trueSql = getTrueSql(s, m);
//		System.out.println(trueSql);
//
//		Statement Statement1 = getStt();
//		long startTime = System.currentTimeMillis();
//		ResultSet rs1 = Statement1.executeQuery(trueSql);
//		long endTime = System.currentTimeMillis();
//		long excTime = (long) (endTime - startTime);
//		System.out.println(excTime);
	}
	Connection conn;
 
	
 
 
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
