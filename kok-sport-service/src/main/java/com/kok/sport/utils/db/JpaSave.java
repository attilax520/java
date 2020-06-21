package com.kok.sport.utils.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class JpaSave {
public static void main(String[] args) {
	JpaEntityAti d = new JpaEntityAti();
     d.setName("Angel");
     
     EntityManagerFactory factory = JpaUtil.getFac();
 
		EntityManager em = factory.createEntityManager();
 	EntityTransaction transaction = em.getTransaction();
 		transaction.begin();
      
       em.persist(d);
    transaction.commit();
       System.out.println("f");
}
}
