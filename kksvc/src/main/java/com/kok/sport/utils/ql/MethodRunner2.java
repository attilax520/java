package com.kok.sport.utils.ql;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.SportApplication;
import com.kok.sport.integration.impl.SyncFootballBasicUpdateprofileServiImp;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.constant.ServiceT;
import com.kok.sport.utils.db.MybatisQueryUtil;
import com.kok.sport.utils.db.MybatisUtil;

 
 //双引号转义，就是俩个双引号
//  "T(com.kok.sport.utils.ql.MethodRunner).sql2notifyWebSocket(""select * from football_tlive_v limit 2 ;select 2"",""http://localhost:9601"")"
public class MethodRunner2 {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(MethodRunner2.class);
	//T(com.kok.sport.utils.CaptchData).m1()  invoke sttatic method
	// T(com.kok.sport.utils.CaptchData).Football_Basic_Update_profile()
	public static void main(String[] args) throws Throwable {
//		System.out.println(args[0]);
//		System.out.println("d");
		
//		 ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(); 
//		 ctx.
//		    ctx.refresh(); 
		AnnotationConfigApplicationContext   ctx = new AnnotationConfigApplicationContext();
		String basePackages="com.kok.sport.utils.constant";
		// ctx.scan(basePackages);
		ctx.register(ServiceT.class);
		
	//	ctx.register(org.apache.ibatis.session.defaults.DefaultSqlSession.class);
	//	ctx.register(org.apache.ibatis.session.defaults.DefaultSqlSession.class)
		ctx.register(SyncFootballBasicUpdateprofileServiImp.class);
	 
		 ctx.refresh(); 
		 
		 SyncFootballBasicUpdateprofileServiImp t=    ctx.getBean(SyncFootballBasicUpdateprofileServiImp.class);
		    t.Football_Basic_Update_profile();
//		    ServiceT t=    ctx.getBean(ServiceT.class);
//		    t.m1();
 		String e="1";
 		
 		logger.info(">>MethodRunner.main:"+e);
 	//	e="T(com.kok.sport.utils.ql.MethodRunner).sql2notifyWebSocket(\"select * from football_tlive_v limit 2;select 2\",\"http://localhost:9601\")";
//		Interpreter i = new Interpreter(); // Construct an interpreter
//		System.out.println(i.eval(" com.kok.sport.utils.CaptchData.m1()  "));
		   ExpressionParser parser = new SpelExpressionParser();

	        Expression exp = parser.parseExpression(e);
	       
	        Object value = exp.getValue();
			System.out.println(value);
	        logger.info(">>MethodRunner.exe:");
	        logger.info(value);

	}
//	
//	private Object resolveExpression(String value) {
//	    return this.resolver.evaluate(resolve(value), this.expressionContext);
//	}
//
//	private String resolve(String value) {
//	    if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
//	        return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
//	    }
//	    return value;
//	}

	//T(com.kok.sport.utils.ql.MethodRunner).sql2notifyWebSocket("select * from football_tlive_v limit 2\;select 2","http://localhost:9601")
	public static  Object sql2notifyWebSocket(String sqls,String nofifyurl) throws Exception
	{
		
		nofifyurl=nofifyurl.trim()+"/sport/notifyWebsocket?notifyMsg=";
//		LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
//		m.put("from", "foot_tlive_notify");
	 
		MybatisMapper mybatisMapper1;
		if (SportApplication.context != null) {
			mybatisMapper1 = SportApplication.context.getBean(MybatisMapper.class);
			
		} else {
			mybatisMapper1=MybatisUtil.getMybatisMapper();
			
		}
		MybatisMapper MybatisMapper1=MybatisUtil.getMybatisMapper();
		List r_li =Lists.newArrayList();
		String[] a=sqls.split(";");
		for (String sql : a) {
			sql=sql.trim();
			Object r =MybatisMapper1.querySql( sql);
			r_li.add(r);
		}
		
//		List r = statments.stream().map(m_stt -> {
//			Object query = query(m_stt, MybatisMapper1);
//			return query;
//		}).collect(Collectors.toList());
//		//Object r = MybatisQueryUtil.queryPage(m, mybatisMapper1);
		
		System.out.println(TestObj(r_li));
		String jsonString = JSON.toJSONString(r_li);
		System.out.println(jsonString);
		String	jsonString_encode=URLEncoder.encode(jsonString, "utf8");
		nofifyurl=nofifyurl+jsonString_encode;
		System.out.println(nofifyurl);
		String t = Httpcliet.testGet(nofifyurl);
		return t;
	}

	private static String TestObj(List r_li) throws UnsupportedEncodingException {
		List li=(List) r_li.get(0);
		Map m=(Map) li.get(0);
		m.put("shijian_shijian", 15);
		m.put("teamId", 10000);
		m.put("team_type", "主队");
		System.out.println(m);		
		
		String jsonString = JSON.toJSONString(m);
	
			//{method:"testsend",msg:{"aa":3333}}
				LinkedHashMap wssendTest_M=Maps.newLinkedHashMap();
				wssendTest_M.put("method","testsend");
				wssendTest_M.put("msg",m);
				String wtssendTest=JSON.toJSONString(wssendTest_M);
				System.out.println(wtssendTest);
		String	jsonString_encode=URLEncoder.encode(jsonString, "utf8");
		String url="http://112.121.163.125:9601/sport/notifyWebsocket?notifyMsg="+jsonString_encode;
		return url;
	}
}
