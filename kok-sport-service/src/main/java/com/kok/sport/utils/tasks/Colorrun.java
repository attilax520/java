package com.kok.sport.utils.tasks;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.alibaba.fastjson.JSON;
import com.kok.sport.utils.db.JpaUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;

import cn.hutool.core.util.RandomUtil;

public class Colorrun {
	public static ExecutorService pool_esvr = Executors.newFixedThreadPool(30);
	public static void main(String[] args) {
		EntityManagerFactory factory = JpaUtil.getFac();

		EntityManager em = factory.createEntityManager();

		String sql = "select   * from football_odds_t where odds_up_down_flag is null order by change_time desc limit 10000;";
		System.out.println(sql);
		Query q = em.createNativeQuery(sql, LinkedHashMap.class);

		List<LinkedHashMap> li = q.getResultList();
		System.out.println(JSON.toJSONString(li));
int n=0;
		for (LinkedHashMap r : li) {
		
		n++;
		System.out.println( n+"/"+ li.size());
			  Future  Future1=	pool_esvr.submit(new Runnable() {
					
					@Override
					public void run() {
						EntityManager em = factory.createEntityManager();
						EntityTransaction transaction = em.getTransaction();
						transaction.begin();
						/////sql
						
						String	sql = "update football_odds_t set  odds_up_down_flag=  {0}  ,away_odds_up_down_flag= {1},tie_odds_updown_flag= {2} where id="
								+ r.get("id");

						sql = MessageFormat.format(sql, "'" + getUpflag() + "'", "'" + getUpflag() + "'", "'" + getUpflag() + "'");
						System.out.println(sql);
						Query createNativeQuery = em.createNativeQuery(sql);
						System.out.println(createNativeQuery.executeUpdate());

						transaction.commit();
					}
				});
			 //   Future1.
			//    Future1.get(5, TimeUnit.SECONDS);
		
		//	break;
		}
		try {
			pool_esvr.awaitTermination(600, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("f");

	}

	private static String getUpflag() {
		String[] cls = new String[] { "up", "down" };
		double m = Math.random();
		if (m > 0.5)
			return cls[0];

		return cls[1];
	}

}
