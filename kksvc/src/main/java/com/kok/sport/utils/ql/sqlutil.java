package com.kok.sport.utils.ql;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Select;
import org.apache.logging.log4j.LogManager;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.db.MybatisUtil;

@SuppressWarnings("all")
public class sqlutil {

	public static void main(String[] args) {
		Map m = Maps.newConcurrentMap();
		m.put("k", "1");
		m.put("k2", "2'");
		m.put("from", "tab2"); // ,information_schema.tabls,mysql.priv
		m.put("select", "*");
		m.put("limit", 5555);
		mysql_real_escape_string(m);
		System.out.println(toStrArr("100,22,33"));
		//checkSafeForQuery(m);
		String sql="update xx set xx=2";
		checkSafe(null,sql);
	
	}

	public static Map toMap(String dataFrom, String condFld, String condFldVals, String orderFld, int page,
			int pagesize) {
		Map m = Maps.newLinkedHashMap();
		m.put("tab", dataFrom);
		try {
			if (condFld.length() > 0) {
				m.put("condFld", condFld);
				m.put("condFldVal", condFldVals);
				m.put("condt", " and " + condFld + " in(" + sqlutil.toStrArr(condFldVals) + ") ");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (orderFld.length() > 0)
				m.put("order", " order by " + orderFld);
		} catch (Exception e) {
			System.out.println(e);
		}

		pageProcess(page, pagesize, m);
		System.out.println(m);
		logger.info(m.toString());
		LogUtil.log.info(m.toString());
		return m;
	}

	public static void pageProcess(int page, int pagesize, Map m) {
		try {
			if (page > 0) {
				int limit = page;
				int offset = pagesize * (page - 1);
				String lmt = " limit " + pagesize + " offset  " + offset;
				m.put("lmt", lmt);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String lmtExpress(int page, int pagesize) {
		try {
			if (page > 0) {
				int limit = page;
				int offset = pagesize * (page - 1);
				String lmt = " limit " + pagesize + " offset  " + offset;
				return lmt;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public static String toStrArr(String condFldVals) {
		List li = Lists.newArrayList();
		String[] a = condFldVals.split(",");
		List<String> sList = Arrays.asList(a);
		List li_rzt = sList.stream().map(s -> "'" + s + "'").collect(Collectors.toList());
		return Joiner.on(",").join(li_rzt);
	}

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(sqlutil.class);

	public static Map mysql_real_escape_string(Map matchItem) {
		logger.info("mysql_real_escape_string start..");
		Map m = Maps.newConcurrentMap();

		Set keySet = matchItem.keySet();
		for (Object key : keySet) {
			try {
				Object object = matchItem.get(key);
				if (object instanceof Map || object instanceof List)
					continue;
				String string = object.toString();
				logger.info(string);
				m.put(key, string.replaceAll("'", "''"));
			} catch (Exception e) {
				logger.warn(e);
			}

			// System.out.println("Key = " + key);

		}
		logger.info("mysql_real_escape_string exit");
//		matchItem.forEach(new BiConsumer () {
//
//			@Override
//			public void accept(Object t, Object u) {
//				 
//				System.out.println(t.toString()+u.toString());
//			}
//		});
		return m;
	}

	public static Object lmtExpressSafe(int page, int pagesize) {

		return lmtExpress(page, pagesize);
	}

//	/**
//	 * 安全性检查
//	 * 
//	 * @param m
//	 */
//	public static void checkSafe(Map m) {
//
//	}
	
	/**
	 * 安全性检查 no limit lmt 
	 * 
	 * @param m
	 */
	public static void checkSafeForQueryRaw(Map m2) {
		Map m=MapUtil.clone(m2);
		String sql = MybatisUtil.getTrueSql(m);
		// sql = sql + "; select 1";
		System.out.println(sql);

		logger.info(sql);
		// 新建 MySQL Parser
		SQLStatementParser parser = new MySqlStatementParser(sql);

		// 使用Parser解析生成AST，这里SQLStatement就是AST
		// 安全设置2，只允许执行一条sql only one sql,,
		List<SQLStatement> statements = parser.parseStatementList();
		if (statements.size() > 1)
			throw new RuntimeException("multisql not alowded不许多条sql");
		SQLStatement statement = statements.get(0);
		//check only query 
		// SQLIntegerExpr 同时进行select语句检查，只允许执行select、语句
		SQLSelectStatement slctStat = (SQLSelectStatement) statement;
		MySqlSelectQueryBlock MySqlSelectQueryBlock1 = (MySqlSelectQueryBlock) slctStat.getSelect().getQuery();
		// parser.parseStatementList();
		// 使用visitor来访问AST
		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		statement.accept(visitor);
		// 系统库表黑名单
		Map<Name, TableStat> tables = dbTableBlacklistCheck(m, visitor); 
		// sp udf 黑名单
		// limit 检查，不能超出1000 防止性能卡慢			
		System.out.println(tables);
	}

	private static Map<Name, TableStat> dbTableBlacklistCheck(Map m, MySqlSchemaStatVisitor visitor) {
		List<String> tbs_li=Lists.newArrayList();
		try {
			if(m.get("$blacklistTbs")!=null) {
				String tbs=m.get("$blacklistTbs").toString().trim();
				String[] a=tbs.split(",");
				  tbs_li=Arrays.asList(a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	//	String[] dest = list.toArray(new String[0])
		Map<Name, TableStat> tables = visitor.getTables();
		Set<Name> tabs = tables.keySet();
		for (Name name : tabs) {
			String nameS = name.toString().toLowerCase();
			if (nameS.contains("mysql.") || nameS.contains("information_schema."))
				throw new RuntimeException("syslib not alowded不许查询系统库");
			if (tbs_li.contains(nameS))
				throw new RuntimeException("table not alowded不许查询此表");
		}
		return tables;
	}

	/**
	 * 安全性检查
	 * 
	 * @param m
	 */
	public static void checkSafeForQuery(Map m2) {
      Map m=Maps.newConcurrentMap();
      m.putAll(m2);
		if (m.get("limit") != null) {
			try {
				String lmt = m.get("limit").toString().trim().toLowerCase();
				if (lmt.contains(","))
					checkLimitDoubleIntFmt(lmt);
				else if (lmt.contains("offset")) {
					// nothing
				}else
				{//int mode
					Util.ifNotNullnEmptyConvertInt(m.get("limit"));
				}
			} catch (Exception e) {
				throw new RuntimeException("check limit apram请检查翻页参数limit格式", e);
			}
			
			
		}
		//page mode
		if(m.get("limit") == null &&(m.get("page")!=null))
		{
			 
			 
			try {
				Util.ifNotNullnEmptyConvertInt(m.get("page"));
				Util.ifNotNullnEmptyConvertInt(m.get("pagesize"));

			} catch (Exception e) {
				throw new RuntimeException("check page apram请检查翻页参数格式", e);
			}
		}
		
		//no lmt no page mode
		

		// 只允许执行query 语句 only query sql alowd

		String sql = MybatisUtil.getTrueSql(m);
		// sql = sql + "; select 1";
		System.out.println(sql);

		checkSafe(m, sql);

	}

	public static void checkSafe(Map m, String sql) {
		logger.info(sql);
		// 新建 MySQL Parser
		SQLStatementParser parser = new MySqlStatementParser(sql);

		// 使用Parser解析生成AST，这里SQLStatement就是AST
		// 安全设置2，只允许执行一条sql only one sql,,
		List<SQLStatement> statements = parser.parseStatementList();
		if (statements.size() > 1)
			throw new RuntimeException("multisql not alowded不许多条sql");
		SQLStatement statement = statements.get(0);

		// parser.parseStatementList();

		// 使用visitor来访问AST
		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		statement.accept(visitor);

		// 系统库表黑名单
		List<String> tbs_li=Lists.newArrayList();
		try {
			if(m.get("$blacklistTbs")!=null) {
				String tbs=m.get("$blacklistTbs").toString().trim();
				String[] a=tbs.split(",");
				  tbs_li=Arrays.asList(a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	//	String[] dest = list.toArray(new String[0])
		Map<Name, TableStat> tables = visitor.getTables();
		Set<Name> tabs = tables.keySet();
		for (Name name : tabs) {
			String nameS = name.toString().toLowerCase();
			if (nameS.contains("mysql.") || nameS.contains("information_schema."))
				throw new RuntimeException("syslib not alowded不许查询系统库");
			if (tbs_li.contains(nameS))
				throw new RuntimeException("table not alowded不许查询此表");
		}
//	        for (iterable_type iterable_element : iterable) {
//				
//			}
		// sp udf 黑名单

		// limit 检查，不能超出1000 防止性能卡慢
		// SQLIntegerExpr 同时进行select语句检查，只允许执行select、语句
		try {
			SQLSelectStatement slctStat = (SQLSelectStatement) statement;
			MySqlSelectQueryBlock MySqlSelectQueryBlock1 = (MySqlSelectQueryBlock) slctStat.getSelect().getQuery();
			SQLIntegerExpr SQLIntegerExpr1 = (SQLIntegerExpr) MySqlSelectQueryBlock1.getLimit().getRowCount();
			Number number = SQLIntegerExpr1.getNumber();
			if (number.intValue() > 1000)
				throw new RuntimeException("limit too big单次查询超过一千或者没有指定limit参数或翻页参数 " + sql);
		
		} catch (Exception e) {
			throw new RuntimeException("limit too big单次查询超过一千或者没有指定limit参数或翻页参数 " + sql);
			
		}
		
		System.out.println(tables);
	}

	private static void checkLimitDoubleIntFmt(String trim) {
		String[] a = trim.split(",");
		for (String i : a) {
			Util.ifNotNullnEmptyConvertInt(i);
		}

	}

	public static Object lmtExpressSafe(Object object, Object object2) {
		// TODO Auto-generated method stub
		return lmtExpressSafe(Integer.parseInt(object.toString()), Integer.parseInt(object2.toString()));
	}

	public static Object lmtExpressSafe(Object object, Object object2, int def) {
		try {
			return lmtExpressSafe(Integer.parseInt(object.toString()), Integer.parseInt(object2.toString()));
		} catch (Exception e) {
			return " limit " + def;
		}

	}

	public static String getTablename(String sqlBound) {
		// 新建 MySQL Parser
		SQLStatementParser parser = new MySqlStatementParser(sqlBound);

		// 使用Parser解析生成AST，这里SQLStatement就是AST
		SQLStatement statement = parser.parseStatement();

		if (statement instanceof MySqlInsertStatement) {
			List<SQLExpr> cols = ((MySqlInsertStatement) statement).getColumns();
			String tablename = ((MySqlInsertStatement) statement).getTableName().toString();
			return tablename;
		}
		return null;
	}

	public static List<String> getColumns(String sqlBound) {
		// 新建 MySQL Parser
		SQLStatementParser parser = new MySqlStatementParser(sqlBound);

		// 使用Parser解析生成AST，这里SQLStatement就是AST
		SQLStatement statement = parser.parseStatement();

		if (statement instanceof MySqlInsertStatement) {
			List<SQLExpr> cols = ((MySqlInsertStatement) statement).getColumns();
			List li_rzt = cols.stream().map(s -> s.toString()).collect(Collectors.toList());
			String tablename = ((MySqlInsertStatement) statement).getTableName().toString();
			return li_rzt;
		}
		return null;
	}

}
