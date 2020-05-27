package com.kok.sport.utils.db;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.regFind;

public class SubqueryProcessorExtmode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}
	
	public static List processSubQ_sqlToObjli(String sqls) {
		sqls=sqls.trim();
		String[] a = sqls.split(";");
		List<Map> li_subquery = Lists.newArrayList();
		for (String sql : a) {
			LinkedHashMap m = Maps.newLinkedHashMap();
			 
			List<String> outCols = regFind.SqlParam(sql);
		 
			m.put("sql", sql);
			m.put("outCols", outCols);
//				// 新建 MySQL Parser
//				SQLStatementParser parser = new MySqlStatementParser(sql);
			//
//				// 使用Parser解析生成AST，这里SQLStatement就是AST
//				// 安全设置2，只允许执行一条sql only one sql,,
//				List<SQLStatement> statements = parser.parseStatementList();
//		 		SQLStatement statement = statements.get(0);

			li_subquery.add(m);

		}
		return li_subquery;
	}

	public static List<LinkedHashMap> getSubqObjsMode(String sqls) {
		sqls = sqls.trim();
		String[] a = sqls.split(";");
		List<LinkedHashMap> li_subquery = Lists.newArrayList();
		for (String sql : a) {
			sql=sql.trim();
			LinkedHashMap m = Maps.newLinkedHashMap();
			int lastAs = sql.lastIndexOf("as");
			String asObj = sql.substring(lastAs + 2).trim();
			int lastBrack = sql.lastIndexOf(")");
			sql = sql.substring(1, lastBrack);
			// sql="select * from "+sql;
			List<String> outCols = regFind.SqlParam(sql);
			m.put("asObj", asObj.toString().trim());
			m.put("sql", sql);
			m.put("outCols", outCols);
//				// 新建 MySQL Parser
//				SQLStatementParser parser = new MySqlStatementParser(sql);
			//
//				// 使用Parser解析生成AST，这里SQLStatement就是AST
//				// 安全设置2，只允许执行一条sql only one sql,,
//				List<SQLStatement> statements = parser.parseStatementList();
//		 		SQLStatement statement = statements.get(0);

			li_subquery.add(m);

		}
		return li_subquery;
	}
	

}
