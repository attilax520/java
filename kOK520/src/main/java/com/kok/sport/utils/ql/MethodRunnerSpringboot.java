package com.kok.sport.utils.ql;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.alibaba.fastjson.JSON;
import com.kok.SportApplication;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.db.DbUtil;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.zaxxer.hikari.pool.HikariProxyConnection;
import com.zaxxer.hikari.pool.ProxyConnection;

import cn.hutool.core.util.ReflectUtil;

public class MethodRunnerSpringboot {

	// T(com.kok.sport.utils.CaptchData).m1() invoke sttatic method
	// T(com.kok.sport.utils.CaptchData).Football_Basic_Update_profile()
	public static void main(String[] args) throws Throwable {
		System.out.println(args[0]);
		System.out.println("d");
		String e = args[0];

//		Interpreter i = new Interpreter(); // Construct an interpreter
//		System.out.println(i.eval(" com.kok.sport.utils.CaptchData.m1()  "));
		ExpressionParser parser = new SpelExpressionParser();

		Expression exp = parser.parseExpression(e);

		//lauch sprinboot main
		SportApplication.main(args);
		
		//inject to the class 
		MybatisMapper MybatisMapper1 = SportApplication.context.getBean(MybatisMapper.class);
		SqlSessionFactory sqlSessionFactory = SportApplication.context.getBean(SqlSessionFactory.class);
		try {
			 Util.toFile(SportApplication.context, "SportApplication.context");
		//	Util.toFile(sqlSessionFactory, "sqlSessionFactory");
		//	Util.toFile(sqlSessionFactory.getConfiguration(), "sqlSessionFactory");
			Util.toFile(sqlSessionFactory.openSession(), "openSession");
		
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
	 		Connection connection = sqlSessionFactory.openSession().getConnection();
	 		DbUtil.getUrl(connection);
	 		Util.toFile(connection, "getConnection");
		
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		System.out.println(MybatisMapper1.querySql("select 'frm MethodRunnerSpringboot'"));
		CaptchData.MybatisMapper1 = MybatisMapper1;
		CaptchData.sqlSessionFactory = sqlSessionFactory;
		

		System.out.println(exp.getValue());
		Runtime.getRuntime().exit(0);

	}

//	private static void DbUtil(Connection connection) {
//		
//	}

}
