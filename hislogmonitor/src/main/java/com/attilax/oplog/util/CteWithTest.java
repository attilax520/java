package com.attilax.oplog.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.session.SqlSession;

import com.attilax.oplog.OperLogUtil;

import cn.freeteam.util.MybatisSessionFactory;

public class CteWithTest {



	public static void main(String[] args) {

		ExecutorService ExecutorService1_theardpool = Executors.newFixedThreadPool(50);
		for (int i = 1; i < 20000; i++) {
			ExecutorService1_theardpool.submit(new Runnable() {
				@Override
				public void run() {
					Statement st = getStt_iniEnvi(); // ini envi
					long startTime = System.currentTimeMillis();
					exec(st);
					CurrTestUtil.calcNout_ex(startTime);
				}
			}); // end sumbit
		 CurrTestUtil.	sleep4reduceCpu(i);

		}
		// ExecutorService1_theardpool.shutdown();

	}
	
	
	static int exec(Statement st ) {
		String sql = "update     dpt1 set var1='sssx26647'  where id='901982439071686659'     ";
		int i = 0;
		try {
			i = st.executeUpdate(sql);
			//conn.commit();
			System.out.println(i);
		} catch (SQLException e) {
			 throw new RuntimeException(e);
		}
		return i;
	}


	

	protected static Statement getStt_iniEnvi() {
		
		MybatisSessionFactory.CONFIG_FILE_LOCATION = "/mybatis_postgresql.xml";
		SqlSession session = MybatisSessionFactory.getSession();
		Connection conn = session.getConnection();
		
		Statement st = null;
		try {
			conn.setAutoCommit(true);
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return st;
	}

}
