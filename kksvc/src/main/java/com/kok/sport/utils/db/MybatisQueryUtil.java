package com.kok.sport.utils.db;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.jaxrs.FastJsonAutoDiscoverable;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.controller.ApiController;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.RequestUtil;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.regFind;
import com.kok.sport.utils.ql.sqlutil;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import lombok.NonNull;

@SuppressWarnings("all")
public class MybatisQueryUtil {
	public static String orderby = "orderby"; // map key

	public static void main(String[] args) throws Exception {

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "football_match_t");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		String sbq = " (  from football_score_t where match_id={id} limit 1 ) as zhudui_scoreObj;( from football_score_t where match_id={id} and team_type=2 limit 1 ) as kedui_scoreObj";
		reqt.addParameter("subquery", sbq);
		System.out.println(URLEncoder.encode(sbq, "utf8"));

		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ApiControllerForAdv.req = reqt;
		Object bizSql = ApiControllerForAdv.queryPage();
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		System.out.println(JSON.toJSONString(li, true));

//		Map m = Maps.newConcurrentMap();
//		m.put("select", "id,999 as ttt,data");
//		m.put("from", "football_tlive_t");
//		// m.remove("limitt");
//		// m.put("select", " * ");
//		m.put("limit", 3);
//		m.put("limit", "3,10");
//		MybatisMapper MybatisMapper1 = MybatisUtil.getConn().getMapper(MybatisMapper.class);
//		Object queryPage = queryPage(m, MybatisMapper1);
//		System.out.println(JsonUtil.prety(queryPage));
//
//		System.out.println(JSON.toJSONString(queryPage, true));
	}

	@Test
	public void test_pagemode() throws Exception {
		Map m = Maps.newConcurrentMap();

		m.put("from", "football_tlive_t");
		// m.remove("limitt");
		m.put("select", "id,data");
//		m.put("limit", 3);
//		m.put("limit", "3,10");
		m.put("page", 3);
		m.put("pagesize", 10);
		MybatisMapper MybatisMapper1 = MybatisUtil.getConn().getMapper(MybatisMapper.class);
		System.out.println(queryPage(m, MybatisMapper1));

	}

	static Logger logger = LoggerFactory.getLogger(MybatisQueryUtil.class);

	//only query data ,no count query
	public static Object query(Map m2, MybatisMapper mybatisMapper1) {
		Map m = com.kok.sport.utils.MapUtil.clone(m2);
		// 简化使用性ux，增加select和limit参数的默认模式，翻页转换page等
		uxEnhance(m);
		logger.info(m.toString());

		// 安全性检查过滤
		m.put("$blacklistTbs", "sys_user_t");
		sqlutil.checkSafeForQuery(m);
		// convert mybatis fmt fragment
		paddParam(m);
		List  r = mybatisMapper1.query(m);
		dbutilUtil.	convertJsonStr2obj(r);
		processSubquery(m, r, mybatisMapper1);

		//from db view subq
		if (m2.get("fromOriSubquerys") != null) {
			String fromOriSubquerys_sqls = m2.get("fromOriSubquerys").toString();
			List li_subquery = SubqueryProcessorExtmode.getSubqObjsMode(fromOriSubquerys_sqls);
			procsss_subq(r, li_subquery, mybatisMapper1, subobj_mode);
		}
		
		//from sysview_subq
		sysview_subqProcess(m2,mybatisMapper1,r);

		return r;
	}
	
	//only query data ,no count query
	public static Object queryNoLmt(Map m2,@NotNull @NonNull MybatisMapper mybatisMapper1) {
		Map m = com.kok.sport.utils.MapUtil.clone(m2);
		// 简化使用性ux，增加select和limit参数的默认模式，翻页转换page等
		uxEnhance(m);
		logger.info(m.toString());

		// 安全性检查过滤
		m.put("$blacklistTbs", "sys_user_t");
		sqlutil.checkSafeForQueryRaw(m);
		// convert mybatis fmt fragment
		paddParam(m);
		List  r = mybatisMapper1.query(m);
		dbutilUtil.	convertJsonStr2obj(r);
		processSubquery(m, r, mybatisMapper1);

		//from db view subq
		if (m2.get("fromOriSubquerys") != null) {
			String fromOriSubquerys_sqls = m2.get("fromOriSubquerys").toString();
			List li_subquery = SubqueryProcessorExtmode.getSubqObjsMode(fromOriSubquerys_sqls);
			procsss_subq(r, li_subquery, mybatisMapper1, subobj_mode);
		}
		
		//from sysview_subq
		sysview_subqProcess(m2,mybatisMapper1,r);

		return r;
	}

	private static void sysview_subqProcess(Map m2, MybatisMapper mybatisMapper1, List rzt) {
		
		try {
			String view=m2.get("from").toString();
			 String sql="select * from sys_views where name='"+view+"'";
			 List<LinkedHashMap> rzts=mybatisMapper1.querySql(sql);
			 if(rzts.size()>0)
			 {
				 LinkedHashMap view_ex=rzts.get(0);
				 String outsqls=view_ex.get("subquerys").toString();
				 List<LinkedHashMap> li_subquery = SubqueryProcessorExtmode.getSubqObjsMode(outsqls);
					 
				 procsss_subq(rzt, li_subquery, mybatisMapper1, subobj_mode);
			 }
		} catch (Exception e) {
			System.out.println(e);
		}
	
		
	}

	private static LinkedHashMap<String, Object> OutterSubqueryProcess(Map m,  MybatisMapper mybatisMapper1) {
		LinkedHashMap<String, Object> extMap=Maps.newLinkedHashMap();
		 if (m.get("OutterSubquery") != null)
		 {
			 String fromOriSubquerys_sqls = m.get("OutterSubquery").toString();
				List<LinkedHashMap> li_subquery = SubqueryProcessorExtmode.getSubqObjsMode(fromOriSubquerys_sqls);
				for (LinkedHashMap subq : li_subquery) {
					String sql=subq.get("sql").toString();
					List li=mybatisMapper1.querySql(sql);
					String asObj=subq.get("asObj").toString();
					extMap.put(asObj, li);
				}
		 }
		 
		 String view=m.get("from").toString();
		 String sql="select * from sys_views where name='"+view+"'";
		 List<LinkedHashMap> rzts=mybatisMapper1.querySql(sql);
		 if(rzts.size()>0)
		 {
			 LinkedHashMap view_ex=rzts.get(0);
			 String outsqls=view_ex.get("outterSubq").toString();
			 List<LinkedHashMap> li_subquery = SubqueryProcessorExtmode.getSubqObjsMode(outsqls);
				for (LinkedHashMap subq : li_subquery) {
					String sql2=subq.get("sql").toString();
					List li=mybatisMapper1.querySql(sql2);
					String asObj=subq.get("asObj").toString();
					extMap.put(asObj, li);
				}
			 
		 }
		return extMap;
	}

	/**
	 * 处理子查询 problm leave::: 1. fldval maybe int 2.mult fld onsu..
	 * 
	 * @param m
	 * @param rzt
	 * @param mybatisMapper1
	 */
	private static void processSubquery(Map m5, List<Map> rzt, MybatisMapper mybatisMapper1) {

		if (m5.get("subquery") != null) {
			String sqls = m5.get("subquery").toString().trim();
			processSubQ(rzt, sqls, mybatisMapper1);
		}
		if (m5.get("subqueryID") != null) {
			String subqueryID = m5.get("subqueryID").toString().trim();
			String sqls = processSubQ_getSqlBySbqID(subqueryID, mybatisMapper1);
			processSubQ(rzt, sqls, mybatisMapper1);
		}

	}

	private static String processSubQ_getSqlBySbqID(String subqueryID, MybatisMapper mybatisMapper1) {
		String sql = "select * from subquerys where id='" + subqueryID.trim() + "'";
		List<LinkedHashMap> li = mybatisMapper1.querySql(sql);
		return li.get(0).get("sql").toString();
	}

	private static void processSubQ(List<Map> rzt, String sqls, MybatisMapper mybatisMapper1) {
		List<LinkedHashMap> li_subquery = SubqueryProcessorExtmode. getSubqObjsMode(sqls);

		procsss_subq(rzt, li_subquery, mybatisMapper1, subobj_mode);
	}

	

	static String subobj_mode = "subobj_mode";
	static String extCol_mode = "extCol_mode";

	private static void procsss_subq(List<Map> rzt, List<LinkedHashMap> li_subquery, MybatisMapper mybatisMapper1,
			String subQJoinMode) {
		for (Map rec : rzt) {
			for (Map subqM : li_subquery) {
				List<String> outCols = (List<String>) subqM.get("outCols");
				String sql = subqM.get("sql").toString().trim();
				for (String outCol : outCols) {
					String onfldVal = "'" + rec.get(outCol).toString() + "'";
					sql = sql.replaceAll("\\{" + outCol + "\\}", onfldVal);
					// 简化使用性ux，增加select和limit参数的默认模式，翻页转换page等

				}
				sql = sql.trim();
				if (!sql.startsWith("select"))
					sql = "select * " + sql;

				HashMap m = Maps.newHashMap();
				// 安全性检查过滤
				m.put("$blacklistTbs", "sys_user_t");
				sqlutil.checkSafe(m, sql);
				// process where

				// convert mybatis fmt fragment
				// paddParam(subqM);
				List rs = mybatisMapper1.querySql(sql);

				if (subQJoinMode.contentEquals(subobj_mode))
					process_subq_subobjMod(rec, subqM, rs);
				else {
					// extCol_mode
					process_subq_extCol_modeMod(rec, subqM, rs);

				}
			}

		}
	}

	private static void process_subq_extCol_modeMod(Map rec, Map subqM, List rs) {
		if(rs.size()>0)
		{
			LinkedHashMap m = (LinkedHashMap) rs.get(0);
			com.kok.sport.utils.MapUtil.putAllNotoveride(rec, m);
		}
		

		// rec.put(asObj.toString().trim(), rs);

	}

	private static void process_subq_subobjMod(Map rec, Map subqM, List rs) {
		Object asObj = subqM.get("asObj");
		rec.put(asObj.toString().trim(), rs);
	}

	static Object having = "having";
	static Object groupby = "groupby";

	public static void paddParam(Map m) {

		if (m.get(orderby) != null)
			m.put(orderby, " order by  " + m.get(orderby));
		if (m.get("limit") != null)
			m.put("limit", " limit  " + m.get("limit"));
		// 兼容page模式，也可以直接使用limit模式
		if (m.get("limit") == null)
			m.put("limit", sqlutil.lmtExpressSafe(m.get("page"), m.get("pagesize"), 500));

		if (m.get("where") != null && m.get("where").toString().trim().length() > 0)
			m.put("where", " where  " + m.get("where"));

		if (m.get(groupby) != null)
			m.put(groupby, " group by  " + m.get(groupby));

		if (m.get(having) != null)
			m.put(having, " having  " + m.get(having));
	}

	private static void uxEnhance(Map m) {
		if (m.get("select") == null || (m.get("select").toString().trim().length() == 0))
			m.put("select", "*");

	}

	public static int queryCount(Map m2, MybatisMapper MybatisMapper1) {
		if(MybatisMapper1==null)
			throw new RuntimeException("MybatisMapper1==nullMybatisMapper1==null");
		Map m = com.kok.sport.utils.MapUtil.clone(m2);

		m.remove("limit");
		m.remove("page");
		m.remove("pagesize");
		m.put("select", " count(*) as cnt");

		// 安全性检查过滤
		sqlutil.checkSafeForQuery(m);
		// convert mybatis fmt fragment paddParam
		//
		if (m.get("where") != null && m.get("where").toString().trim().length() > 0)
			m.put("where", " where  " + m.get("where"));
		List<LinkedHashMap> cntList = MybatisMapper1.query(m);
		 
		int cnt = Integer.parseInt(cntList.get(0).get("cnt").toString());
		return cnt;
	}

	public static Object queryPage(Map reqM, MybatisMapper mybatisMapper1) {
		if(mybatisMapper1==null)
			throw new RuntimeException("mybatisMapper1==null");
		try {
			Map m = Maps.newLinkedHashMap();

			m.put("count", queryCount(com.kok.sport.utils.MapUtil.clone(reqM), mybatisMapper1));
			m.put("data", query(com.kok.sport.utils.MapUtil.clone(reqM), mybatisMapper1));
			if (reqM.get("limit") != null) {
				m.put("limit", reqM.get("limit"));
			} else if (reqM.get("page") != null) {
				m.put("pageCount", Util.getPageCount(m.get("count"), reqM.get("pagesize")));
				m.put("page", reqM.get("page"));
				m.put("pagesize", reqM.get("pagesize"));
			}
			try {
				   LinkedHashMap extM=	OutterSubqueryProcess(com.kok.sport.utils.MapUtil.clone(reqM),  mybatisMapper1);
					m.put("extData", extM);
			
			} catch (Exception e) {
				System.out.println(e);
			}
		   return m;
		} catch (Exception e) {
			// Table 'dev_kok_sport.match_ex' doesn't exist
			String table = reqM.get("from").toString();
			if (e.getMessage().contains("Table") && e.getMessage().contains(table)
					&& e.getMessage().contains("doesn't exist")) {
				String sql = "select * from sys_views where name='" + table + "'";
				List<LinkedHashMap> li = mybatisMapper1.querySql(sql);
				LinkedHashMap linkedHashMap_view = li.get(0);
				String view_context = linkedHashMap_view.get("context").toString();
				reqM.put("fromOri", reqM.get("from"));
				if(linkedHashMap_view.get("subquerys")!=null)
				reqM.put("fromOriSubquerys", linkedHashMap_view.get("subquerys"));
				reqM.put("from", "(" + view_context + ") as tab_subqueryV1123");

				return queryPage(reqM, mybatisMapper1);

			} else
				throw e;

		}

	}

	public static Object queryMulti(String ql, MybatisMapper MybatisMapper1) {
		List<Map> statments = JsonGsonUtil.toList(ql, Map.class);
		List r = statments.stream().map(m_stt -> {
			Object query = query(m_stt, MybatisMapper1);
			return query;
		}).collect(Collectors.toList());
		return r;
	}
	
	public static Object queryMultiBySp(String ql, MybatisMapper MybatisMapper1) {
		 
		return 	MybatisMapper1.querySqlMultiRs("call sp_multitab" );
	}

	public static void processWhere(LinkedHashMap m, Map reqM) {
		String where = getWhere(reqM);
		if (reqM.get("@where") != null) {
			String whereAt = reqM.get("@where").toString();
			if (where.trim().length() > 0) {
				String where_true = whereAt + " and " + where;
				m.put("where", where_true);
			}

		} else {
			if (where.trim().length() > 0)
				m.put("where", where);
		}
	}

	
	

	public static String getWhere(Map reqM) {
		// LinkedHashMap<String , String> m=Maps.newLinkedHashMap();
		List<String> li = Lists.newArrayList();
		reqM.forEach(new BiConsumer<String, String>() {

			@Override
			public void accept(String t, String u) {
				String trim = t.trim();

				if (trim.endsWith("_start@")) // fake col support for between
				{
					String col = (trim.substring(0, trim.length() - 7));
					li.add(col + ">='" + u + "'");
				} else if (trim.endsWith("_end@")) // fake col support for between
				{
					String col = (trim.substring(0, trim.length() - 5));
					li.add(col + "<='" + u + "'");
				}

				else if (!trim.startsWith("@") && trim.contains("@") && !trim.endsWith("@")) // udf
				{

					int at = trim.indexOf("@");
					String col = trim.substring(0, at);
					String fun = trim.substring(at + 1);
					//// BETWEEN in exist like is _null
					if (fun.trim().toLowerCase().equals("in")) {
						String string = col + " " + fun + "(" + u.trim() + ")";
						li.add(string);
					} else if (fun.trim().toLowerCase().equals("date_from_unixtime")) {
						String string =fun +"("+col + ") ="    + " '" + u.trim() + "' ";
						li.add(string);
					}else {
						String string = col + " =" + fun + "('" + u.trim() + "')";
						li.add(string);
					}

				} else if (!trim.startsWith("@") && !trim.endsWith("@")) {
					// BETWEEN in exist like is _null
					// m.put(t, u);
					if (u.trim().startsWith("between"))
						li.add(t + "  " + u);
					else
						li.add(t + "='" + u + "'");
				}

			}
		});

//		m.forEach((new BiConsumer<String, String>(){
//
//			@Override
//			public void accept(String t, String u) {
//				 
//				
//			}});
		return Joiner.on(" and ").join(li);
	}


}
