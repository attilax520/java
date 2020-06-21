package com.kok.sport.utils.mockdata;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.utils.UrlUtil;
import com.kok.sport.utils.db.JpaUtil;
import com.mysql.cj.core.conf.url.ConnectionUrlParser;

public class JpaTest {

	public static void main(String[] args) {

		EntityManagerFactory factory = JpaUtil.getFac();

		// Create a new EntityManager from the EntityManagerFactory. The
		// EntityManager is the main object in the persistence API, and is
		// used to create, delete, and query objects, as well as access
		// the current transaction
		EntityManager em = factory.createEntityManager();

		// Perform a simple query for all the Message entities
		//select * from sys_tab
		//mlt sql Map.class only ret first rs
		
	//	Query q = em.createNativeQuery("select 1;select 1;", Map.class);
		/*em.createNativeQuery("select 1 as age,'ati' name;select 2;" );
		 * [[1,"ati"]]
		 * */
		Query q = em.createNativeQuery("select 1 as age,'ati' name;select 2;",Map.class );
		List li = q.getResultList();
		System.out.println(JSON.toJSONString(li));

	}



}
