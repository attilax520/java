package com.kok.sport.utils.db;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.common.collect.Maps;
import com.kok.sport.utils.UrlUtil;
import com.mysql.cj.core.conf.url.ConnectionUrlParser;

public class JpaUtil {

	public static EntityManagerFactory getFac() {
		 
		return JpaUtil_getFac();
	}
	
	private static EntityManagerFactory JpaUtil_getFac() {
		String url2;// = "jdbc:mysql://182.16.50.115:3306/dev_kok_sport?user=root&password=cjds1023123&allowMultiQueries=true";
	
		url2=MybatisUtil. getMysqlUrl();
		System.out.println(url2);
				String mysqlConnUrl =url2;
		ConnectionUrlParser connStringParser = ConnectionUrlParser.parseConnectionString(mysqlConnUrl);
		System.out.println(connStringParser);

		Object url = mysqlConnUrl;
		Object usr = UrlUtil.parse4Q(connStringParser.getQuery()).get("user");
		Object pwd = UrlUtil.parse4Q(connStringParser.getQuery()).get("password");
		Map properties = Maps.newLinkedHashMap();
		properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		properties.put("javax.persistence.jdbc.url",url);
		properties.put("javax.persistence.jdbc.user", usr.toString()); // if needed
		properties.put("javax.persistence.jdbc.password", pwd.toString()); // if needed
		System.out.println(properties);
		// Create a new EntityManagerFactory using the System properties.
		// The "hellojpa" name will be used to configure based on the
		// corresponding name in the META-INF/persistence.xml file
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(null,properties);
		return factory;
	}

}
