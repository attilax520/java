package com.kok.sport.utils.mockdata;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.alibaba.fastjson.JSON;

public class JpaTest {

	public static void main(String[] args) {
		// Create a new EntityManagerFactory using the System properties.
		// The "hellojpa" name will be used to configure based on the
		// corresponding name in the META-INF/persistence.xml file
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("wmsPersisteUnitName");

		// Create a new EntityManager from the EntityManagerFactory. The
		// EntityManager is the main object in the persistence API, and is
		// used to create, delete, and query objects, as well as access
		// the current transaction
		EntityManager em = factory.createEntityManager();

		// Perform a simple query for all the Message entities
		Query q = em.createNativeQuery("select * from sys_tab",Map.class);
		List li = q.getResultList();
		System.out.println(JSON.toJSONString(li));

	}

}
