package com.kok.sport.utils.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kok.sport.utils.ExUtilV2t33;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.RuntimeExceptionAtiVer;
import com.kok.sport.utils.ql.QlSpelUtil;
import com.kok.sport.utils.ql.SqlBldrUtil;

@SuppressWarnings("all")
public class MysqlInsertUtil {

	public static void main(String[] args) throws Exception {
		SqlSession conn = MybatisUtil.getConn();
		MybatisMapper mp = MybatisUtil.getMybatisMapper();

		Map kv_frmNet = Maps.newLinkedHashMap();
		kv_frmNet.put("id", 12);
		kv_frmNet.put("match_iD", 12);
		kv_frmNet.put("real_time_score", "vv");
		kv_frmNet.put("notexistFld", "notexistFldvv");

		Object insertBymapV2 = insert(MybatisUtil.getSqlSessionFactory(), kv_frmNet);
		System.out.println(insertBymapV2);

		// http://localhost:9601/add?into=sys_area_t&id=13&match_iD=13&real_time_score=nnn
	}

	public static SqlSessionFactory sqlSessionFactory;

	public static Object insertMulti(String ql, SqlSessionFactory sqlSessionFactory) {
		SqlSession session = sqlSessionFactory.openSession(true);
		List<Map> statments = JsonGsonUtil.toList(ql, Map.class);
		List r = statments.stream().map(m_stt -> {
			MybatisMapper MybatisMapper1 = session.getMapper(MybatisMapper.class);
			Object query = insert(sqlSessionFactory, m_stt);
			return query;
		}).collect(Collectors.toList());
		return r;
	}
	
	/**
	 * sqlSessionFactory just dep null
	 * @param sqlSessionFactory
	 * @param kv_frmNet
	 * @param mybatisMapper1
	 * @return
	 */
	public static Object insertV2_faster(SqlSessionFactory sqlSessionFactory, Map kv_frmNet, MybatisMapper mybatisMapper1 ) {
		Map m_intoMybatis = null;
		try {
			// defvalue
		//	CfgDb.fldDefValFun(kv_frmNet);

			String tabname = kv_frmNet.get("$insert").toString();
			kv_frmNet.remove("$insert");
			
		//	SqlSession session = sqlSessionFactory.openSession(true);
			m_intoMybatis = getMap_InsertMbtsSetMode_FastNocheckColExist(null, kv_frmNet, tabname);

			if(m_intoMybatis.get("set").toString().length()==0)
				throw new RuntimeException("into tab is err表名可能不正确请检查");
			System.out.println(JSON.toJSONString(m_intoMybatis, true));
			MybatisMapper mp =mybatisMapper1;

			int insertBymapV2 = mp.insertBymapV2(m_intoMybatis);
			
			//def ret curser obj
			Map m=Maps.newLinkedHashMap();
			m.put("effectRowsNums", insertBymapV2);
			m.put("m_into", m_intoMybatis);
		//	m.put("statement", m_intoMybatis);
		//	m.put("last_inset_id", m_intoMybatis);
//			
//			lastrowid 
//			column_names
//			description 
//			rowcount 
//			statement 
//			with_rows
			
			return m;
		} catch (Exception e) {
//			Map dbginfo = Maps.newLinkedHashMap();
//		 
//			dbginfo.put("m_intoMybatis", m_intoMybatis);
//			ExUtilV2t33.throwExV2(new RuntimeExceptionAtiVer(e,m_intoMybatis));
 	 	ExUtilV2t33.throwExWzDbginfo(e,m_intoMybatis);
		}
		return 0;

	}
	
	public static Object insertV2(SqlSessionFactory sqlSessionFactory, Map kv_frmNet) {
		Map m_intoMybatis = null;
		try {
			// defvalue
			CfgDb.fldDefValFun(kv_frmNet);

			String tabname = kv_frmNet.get("$insert").toString();

			SqlSession session = sqlSessionFactory.openSession(true);
			m_intoMybatis = getMap_InsertMbtsSetMode(session, kv_frmNet, tabname);

			if(m_intoMybatis.get("set").toString().length()==0)
				throw new RuntimeException("into tab is err表名可能不正确请检查");
			System.out.println(JSON.toJSONString(m_intoMybatis, true));
			MybatisMapper mp = session.getMapper(MybatisMapper.class);

			int insertBymapV2 = mp.insertBymapV2(m_intoMybatis);
			
			//def ret curser obj
			Map m=Maps.newLinkedHashMap();
			m.put("effectRowsNums", insertBymapV2);
			m.put("m_into", m_intoMybatis);
		//	m.put("statement", m_intoMybatis);
		//	m.put("last_inset_id", m_intoMybatis);
//			
//			lastrowid 
//			column_names
//			description 
//			rowcount 
//			statement 
//			with_rows
			
			return m;
		} catch (Exception e) {
//			Map dbginfo = Maps.newLinkedHashMap();
//		 
//			dbginfo.put("m_intoMybatis", m_intoMybatis);
//			ExUtilV2t33.throwExV2(new RuntimeExceptionAtiVer(e,m_intoMybatis));
 	 	ExUtilV2t33.throwExWzDbginfo(e,m_intoMybatis);
		}
		return 0;

	}

	public static Object insert(SqlSessionFactory sqlSessionFactory, Map kv_frmNet) {
		Map m_intoMybatis = null;
		try {
			// defvalue
			CfgDb.fldDefValFun(kv_frmNet);

			String tabname = kv_frmNet.get("@into").toString();

			SqlSession session = sqlSessionFactory.openSession(true);
			m_intoMybatis = getMap_InsertMbtsSetMode(session, kv_frmNet, tabname);

			if(m_intoMybatis.get("set").toString().length()==0)
				throw new RuntimeException("into tab is err表名可能不正确请检查");
			System.out.println(JSON.toJSONString(m_intoMybatis, true));
			MybatisMapper mp = session.getMapper(MybatisMapper.class);

			int insertBymapV2 = mp.insertBymapV2(m_intoMybatis);
			
			//def ret curser obj
			Map m=Maps.newLinkedHashMap();
			m.put("effectRowsNums", insertBymapV2);
			m.put("m_into", m_intoMybatis);
//			last_inset_id
//			lastrowid 
//			column_names
//			description 
//			rowcount 
//			statement 
//			with_rows
			
			return m;
		} catch (Exception e) {
//			Map dbginfo = Maps.newLinkedHashMap();
//		 
//			dbginfo.put("m_intoMybatis", m_intoMybatis);
//			ExUtilV2t33.throwExV2(new RuntimeExceptionAtiVer(e,m_intoMybatis));
 	 	ExUtilV2t33.throwExWzDbginfo(e,m_intoMybatis);
		}
		return 0;

	}
	
	private static Map getMap_InsertMbtsSetMode_FastNocheckColExist(SqlSession conn, Map kv_frmNet, String tabname) throws Exception {
		Map m_intoMybatis = Maps.newLinkedHashMap();

		m_intoMybatis.put("tab", tabname);

		List<String> li_cols = Lists.newArrayList();

		// Sets.newLinkedHashSet()
		Set<String> ks = kv_frmNet.keySet();
		//
	//	List<String> ks_filtedNoexistFld = ks_filtedNoexistFld(ks, conn, tabname);
		for (String k : ks) {
		//	if(k.equals("$insert"))
			String upitem = "" + k + "=#{[" + k + "]}";
			li_cols.add(upitem);
		}
		String setList_spel = "setList_spel";
		m_intoMybatis.put(setList_spel, "" + Joiner.on(",").join(li_cols));
		String val_warped_sqlValFmt = "val_warped_sqlValFmt";
		m_intoMybatis.put(val_warped_sqlValFmt, vals_warpedgene_fater(tabname, kv_frmNet, conn));
		m_intoMybatis.put("set", QlSpelUtil.parse(m_intoMybatis.get(setList_spel).toString(),
				(Map) m_intoMybatis.get(val_warped_sqlValFmt)));
		return m_intoMybatis;
	}

	private static Map getMap_InsertMbtsSetMode(SqlSession conn, Map kv_frmNet, String tabname) throws Exception {
		Map m_intoMybatis = Maps.newLinkedHashMap();

		m_intoMybatis.put("tab", tabname);

		List<String> li_cols = Lists.newArrayList();

		// Sets.newLinkedHashSet()
		Set<String> ks = kv_frmNet.keySet();
		//
		List<String> ks_filtedNoexistFld = ks_filtedNoexistFld(ks, conn, tabname);
		for (String k : ks_filtedNoexistFld) {
			String upitem = "" + k + "=#{[" + k + "]}";
			li_cols.add(upitem);
		}
		String setList_spel = "setList_spel";
		m_intoMybatis.put(setList_spel, "" + Joiner.on(",").join(li_cols));
		String val_warped_sqlValFmt = "val_warped_sqlValFmt";
		m_intoMybatis.put(val_warped_sqlValFmt, vals_warpedgene(tabname, kv_frmNet, conn));
		m_intoMybatis.put("set", QlSpelUtil.parse(m_intoMybatis.get(setList_spel).toString(),
				(Map) m_intoMybatis.get(val_warped_sqlValFmt)));
		return m_intoMybatis;
	}

	private static List<String> ks_filtedNoexistFld(Set<String> ks, SqlSession conn, String tableName) {
		Connection connection;
		if (sqlSessionFactory != null) {
			SqlSession session = sqlSessionFactory.openSession(true);
			connection = conn.getConnection();
		} else
			connection = conn.getConnection();
		try {

			List<Map> ColsMetaList = DatabaseMetaDataUtil.getColsList(connection, tableName);
			List<String> cols_frmTabDef = ColsMetaList.stream().map(map_item -> {
				return map_item.get("COLUMN_NAME").toString().toLowerCase();
			}).collect(Collectors.toList());
	//		String cols_inFmt = Joiner.on(",").join(cols);

			List<String> cols_existIndbOnly = ks.stream().filter(item -> {

				return cols_frmTabDef.contains(item.toLowerCase());
				// return true;

			}).collect(Collectors.toList());
			return cols_existIndbOnly;
		} catch (Exception e) {
			ExUtilV2t33.throwExV2(e);
		}
		return null;

	}
	
	
	private static Map vals_warpedgene_fater(String tableName, Map kv_frmNet, SqlSession conn) throws Exception {
		String Cols = (String) getColsFrom_objFrmNet(kv_frmNet);
	//	List<Map> ColsMetaList = DatabaseMetaDataUtil.getColsList(conn.getConnection(), tableName);

		Map m = MapUtil.cloneSafe(kv_frmNet);
		Set<String> ks = m.keySet();
		for (String k_col : ks) {
			try {
				Object v_col = m.get(k_col);
				 
				m.put(k_col, "'"+v_col.toString()+"'");
			} catch (Exception e) {
				System.out.println("---waring ");
				e.printStackTrace();
			}

		}

		return m;
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

				String v_sqlFmt = SqlBldrUtil.getValWarpFmt(tableName, k_col, v_col, conn.getConnection());
				m.put(k_col, v_sqlFmt);
			} catch (Exception e) {
				System.out.println("---waring ");
				e.printStackTrace();
			}

		}

		return m;
	}

	private static Object getColsFrom_objFrmNet(Map kv_frmNet) {
		List li = Lists.newArrayList();
		Set ks = kv_frmNet.keySet();
		li.addAll(ks);
		return Joiner.on(",").join(li);
	}


}
