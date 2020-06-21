package com.kok.sport.utils.db;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.ql.QlSpelUtil;
import com.kok.sport.utils.ql.SqlBldrUtil;
import com.kok.sport.utils.ql.sqlutil;

@SuppressWarnings("all")
public class MybatisAdvUtil {

	public static void main(String[] args) throws Exception {
		SqlSession conn = MybatisUtil.getConn();
		// Configuration Configuration1 = checkSqlValds(conn);
		String sttID = "insert_into_football_incident_tV2";
		sttID = "insert_into_tabs";

		Map kv_frmNet = Maps.newLinkedHashMap();
		kv_frmNet.put("id", 11);
		kv_frmNet.put("match_id", 12);
		kv_frmNet.put("real_time_score", "vv");
		
		Date dNow = new Date( );
	      SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
	 
	      System.out.println("当前时间为: " + ft.format(dNow));
		kv_frmNet.put("create_time", ft.format(dNow));
		kv_frmNet.put("delete_flag",0);
		
		String tabname = "football_odds_t";
		
		
		Map m_intoMybatis = getMap_intoMbts(conn, kv_frmNet, tabname);
		

//		System.out.println(JSON.toJSONString(m_intoMybatis, true));
//		System.out.println(conn.update(sttID, m_intoMybatis));
//		
		
	Map m2 = getMap_UpMbts(conn, kv_frmNet, tabname);	
	m2.put("where", "id=11");
		System.out.println(JSON.toJSONString(m2, true));
		System.out.println( conn.getMapper(MybatisMapper.class).updateByMap(m2)  );


//		Map m = Maps.newConcurrentMap();
//		m.put("vals", "testv");
//		String sqlBound = getSqlBound(conn, sttID, m);
//		System.out.println(sqlBound);
//		System.out.println(sqlutil.getTablename(sqlBound));
//		System.out.println(sqlutil.getColumns(sqlBound));
//
//	
//	
//		System.out.println(getXmlPath(conn, sttID));
//		
//		
//		String sqlBoundDefine = getSqlBoundDefine(conn, sttID);
//		System.out.println("----sqlBoundDefine:"+  sqlBoundDefine);
//		System.out.println(sqlutil.getColumns(sqlBoundDefine));
//		// System.out.println(statement);
	}

	private static Map getMap_UpMbts(SqlSession conn, Map kv_frmNet, String tabname) throws Exception {
		Map m_intoMybatis = Maps.newLinkedHashMap();
		
		m_intoMybatis.put("tab", tabname);
		
		List<String> li=Lists.newArrayList();
	 
		Set<String> ks = kv_frmNet.keySet();
		for (String k : ks) {
			String upitem=""+k+"=#{["+ k+"]}";
			li.add(upitem);
		}
		String setList_spel = "setList_spel";
		m_intoMybatis.put(setList_spel, ""+Joiner.on(",").join(li) );
		String val_warped_sqlValFmt = "val_warped_sqlValFmt";
		m_intoMybatis.put(val_warped_sqlValFmt, vals_warpedgene(tabname, kv_frmNet, conn));
		m_intoMybatis.put("setList", QlSpelUtil.parse(m_intoMybatis.get(setList_spel).toString(),
				(Map) m_intoMybatis.get(val_warped_sqlValFmt)) );
		return m_intoMybatis;
	}

	public static Map getMap_intoMbts(SqlSession conn, Map kv_frmNet, String tabname) throws Exception {
		Map m_intoMybatis = Maps.newLinkedHashMap();
	
		m_intoMybatis.put("tab", tabname);
		m_intoMybatis.put("objFrmNet", kv_frmNet);

		m_intoMybatis.put("cols", getColsFrom_objFrmNet(kv_frmNet));
		String vals_spel_placehodler = "vals_spel_placehodler";
		m_intoMybatis.put(vals_spel_placehodler, getvals_spel_placehodler(m_intoMybatis.get("cols")));
		String val_warped_sqlValFmt = "val_warped_sqlValFmt";
		m_intoMybatis.put(val_warped_sqlValFmt, vals_warpedgene(tabname, kv_frmNet, conn));
		m_intoMybatis.put("vals", QlSpelUtil.parse(m_intoMybatis.get(vals_spel_placehodler).toString(),
				(Map) m_intoMybatis.get(val_warped_sqlValFmt)));
		return m_intoMybatis;
	}

	private static Object ext_spel_placehodler(String tmplt, Map map) {

		return QlSpelUtil.parse(tmplt, map);
	}

	private static Map vals_warpedgene(String tableName, Map kv_frmNet, SqlSession conn) throws Exception {
		String Cols = (String) getColsFrom_objFrmNet(kv_frmNet);
		List<Map> ColsMetaList = DatabaseMetaDataUtil.getColsList(conn.getConnection(), tableName);

		Map m = MapUtil.clone(kv_frmNet);
		Set<String> ks = m.keySet();
		for (String k_col : ks) {
			try {
				Object v_col = m.get(k_col);
				String cType = ColsMetaList.stream().filter(map_item -> {

					return map_item.get("COLUMN_NAME").toString().equals(k_col);
					// return true;

				}).collect(Collectors.toList()).get(0).get("TYPE_NAME").toString();

				String v_sqlFmt = SqlBldrUtil.getValWarpFmt(tableName,k_col, v_col,conn.getConnection());
				m.put(k_col, v_sqlFmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return m;
	}

	private static Object getvals_spel_placehodler(Object object) {
		List li = Lists.newArrayList();
		String[] a = object.toString().split(",");
		for (String c : a) {
			li.add("#{[" + c + "]}");
		}
		return Joiner.on(",").join(li);
	}

	private static Object getColsFrom_objFrmNet(Map kv_frmNet) {
		List li = Lists.newArrayList();
		Set ks = kv_frmNet.keySet();
		li.addAll(ks);
		return Joiner.on(",").join(li);
	}

	private static String getSqlBoundDefine(SqlSession conn, String sttID) {

		Configuration Configuration1 = conn.getConfiguration();
		// Configuration1.addLoadedResource(resource);

		// Configuration1.
		MappedStatement MappedStaement1 = Configuration1.getMappedStatement(sttID);
		DynamicSqlSource DynamicSqlSource1 = (DynamicSqlSource) MappedStaement1.getSqlSource();
		// DynamicSqlSource1.
		String fldname = "rootSqlNode";
		SqlNode SqlNode1 = (SqlNode) getFldValRE(DynamicSqlSource1, "rootSqlNode");
		MixedSqlNode MixedSqlNode1 = (MixedSqlNode) SqlNode1;
		List li = (List) getFldValRE(MixedSqlNode1, "contents");
		// StaticTextSqlNode <String,TextSqlNode> <TextSqlNode>

		Object r = li.stream().reduce("", (s, o) -> {

			String string = "";
			try {
				string = getFldVal(o, "text").toString();
			} catch (NoSuchFieldException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return s + string;
		});
		return r.toString();
	}

	// mapper/FootballLiveMatchdetailliveDao.xml
	private static String getXmlPath(SqlSession conn, String sttID) throws Exception {
		Configuration Configuration1 = conn.getConfiguration();
		// Configuration1.addLoadedResource(resource);

		// Configuration1.
		MappedStatement MappedStaement1 = Configuration1.getMappedStatement(sttID);

		// DynamicSqlSource1
		return MappedStaement1.getResource();
//		String sqlBound = MappedStaement1.getSqlSource().getBoundSql(null).getSql();
//		return sqlBound;
	}

	private static Object getFldValRE(Object obj1, String fldname) {
		try {
			Class<?> obj = obj1.getClass();

			Field f = obj.getDeclaredField(fldname);
			f.setAccessible(true);

			return f.get(obj1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static Object getFldVal(Object obj1, String fldname) throws NoSuchFieldException, IllegalAccessException {
		Class<?> obj = obj1.getClass();

		Field f = obj.getDeclaredField(fldname);
		f.setAccessible(true);

		return f.get(obj1);
	}

//	@Deprecated
//	private static String getSqlBound(SqlSession conn,String sttID) {
//		
//		
//		return getSqlBound(conn, sttID, m);
//	}

	private static String getSqlBound(SqlSession conn, String sttID, Map m) {
		Configuration Configuration1 = conn.getConfiguration();

		MappedStatement MappedStaement1 = Configuration1.getMappedStatement(sttID);
		String sqlBound = MappedStaement1.getSqlSource().getBoundSql(m).getSql();
		return sqlBound;
	}

	private static Configuration checkSqlValds(SqlSession conn) {
		Configuration Configuration1 = conn.getConfiguration();
		Collection<MappedStatement> stts = Configuration1.getMappedStatements();
		stts.forEach(new Consumer<MappedStatement>() {

			@Override
			public void accept(MappedStatement s) {
				try {
					checkSqlValid(s.getSqlSource().getBoundSql(null).getSql(), s);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		return Configuration1;
	}

	protected static void checkSqlValid(String sql, MappedStatement s) {
		if (sql == "")
			return;
		try {
			SQLStatementParser parser = new MySqlStatementParser(sql);

			// 使用Parser解析生成AST，这里SQLStatement就是AST
			SQLStatement statement = parser.parseStatement();
			System.out.println("---check ok");
			System.out.println(statement);
			System.out.println(s.getResource());
			System.out.println(sql);
			System.out.println("---check ok end");
		} catch (Exception e) {
			System.out.println("---check err");
			System.out.println(s.getResource());
			System.out.println(sql);
			System.out.println("---check err end");
			throw e;
		}

		;

	}

}
