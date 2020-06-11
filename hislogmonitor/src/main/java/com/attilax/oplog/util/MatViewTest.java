package com.attilax.oplog.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;

import com.attilax.oplog.OperLogUtil;

import cn.freeteam.util.MybatisSessionFactory;

public class MatViewTest {
	public static void main(String[] args) {

		Timer tmr = new Timer();
	 
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {
				MybatisSessionFactory.CONFIG_FILE_LOCATION="/mybatis_postgresql.xml";
				SqlSession session = MybatisSessionFactory.getSession();
				Connection conn = session.getConnection();
				Statement st;
				String sql = "refresh materialized view v1;";
				try {
					int i;
					conn.setAutoCommit(true);
					st = conn.createStatement();
					i = st.executeUpdate(sql);
					conn.commit();
					System.out.println(i);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		},50, 3000);
		
		// if("1"=="1")
		// return;

		ExecutorService ExecutorService1_theardpool = Executors.newFixedThreadPool(50);

		for (int i = 0; i < 200000; i++) {
			
			

			ExecutorService1_theardpool.submit(new Runnable() {

				@Override

				public void run() {

					new OperLogUtil().log4postgre_core("ati1", " {\"txt\":123} ", "tag");

				}

			}); // end sumbit
			if(i%101==0)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

		// ExecutorService1_theardpool.shutdown();

	}

}
