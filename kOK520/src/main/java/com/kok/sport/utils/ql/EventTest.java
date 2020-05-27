package com.kok.sport.utils.ql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;

public class EventTest {

	//T(com.kok.sport.utils.ql.EventTest).m1()
	public static void main(String[] args) throws Exception {
		Map m = Maps.newLinkedHashMap();
		m.put("call", "sp_ivkj");
		m.put("jsonparam", "{}");
//		SqlSessionFactory SqlSessionFactory1=MybatisUtil.getSqlSessionFactory();
		
	System.out.println(processBizSql(m));	;
 
		System.out.println("f");

	}

	private static List<LinkedHashMap> processBizSql(Map m3) {
		List<LinkedHashMap> li=  MybatisUtil.getMybatisMapper().call(m3);
		if(li.size()>0) {
			//nomal fmt
			if(li.get(0).get("at_rztType")==null )
				return (li);
			if(li.get(0).get("at_rztType")!=null && li.get(0).get("at_rztType").equals("exprs"))
			{
				String at_rztType=li.get(0).get("at_rztType").toString();
				 
					//exprs  if java php js etc
					String javaStts=li.get(0).get("host_stts").toString();
					String[] a=javaStts.split(";");
					for (String stt : a) {
						  ExpressionParser parser = new SpelExpressionParser();

					        Expression exp = parser.parseExpression(stt);
					       //exet stt
					        System.out.println("host lan:"+stt);
					        System.out.println("host lang exe rzt:"+exp.getValue());
					}
					String nextSttScript=li.get(0).get("nextSttScript").toString();
					Map m = Maps.newLinkedHashMap();
					m.put("call", nextSttScript);
					m.put("jsonparam", "{}");
					return	processBizSql(m);
				//	List<LinkedHashMap> li2=MybatisMapper1.querySql(sql);
				//	System.out.println("not impt");
				 
			}else 
				//not impt
				return  Lists.newLinkedList();
		 
		}else
		return li;
		 
	}
	
	public static  Object m1( ) throws Exception
	{
	//	update sys_event  set rzt='rztttt' where id=9
		return "m1rzt";
	}
	
	public static  Object m2( ) throws Exception
	{
	//	update sys_event  set rzt='rztttt' where id=9
		return "m2rzt";
	}
}
