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

public class CurrTestUtil {

	public static AtomicLong all_int = new AtomicLong(0);
	public static AtomicLong cnt = new AtomicLong(0);
	public static AtomicLong avg = new AtomicLong(0);

	
 

	public static void main(String[] args) {

		ExecutorService ExecutorService1_theardpool = Executors.newFixedThreadPool(50);
		for (int i = 1; i < 20000; i++) {
			ExecutorService1_theardpool.submit(new Runnable() {
				@Override
				public void run() {
					Statement st = getStt(); // ini envi
					long startTime = System.currentTimeMillis();
					exec(st);
					calcNout_ex(startTime);
				}
			}); // end sumbit
			 sleep4reduceCpu(i);

		}
		// ExecutorService1_theardpool.shutdown();

	}
	
	
	public  static int exec(Statement st ) {
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

//	public static AtomicLong all_int = new AtomicLong(0);
//	public static AtomicLong cnt = new AtomicLong(0);
//	public static AtomicLong avg = new AtomicLong(0);

	
	synchronized public static void calcNout(long excTime) {
		
		all_int.addAndGet(excTime);
		cnt.incrementAndGet();
		avg.set(all_int.get() / cnt.get());
		System.out.println("---------执行时间：" + excTime + "ms,avgtime: " + avg + "ms,cnt:" + cnt + ",alltime:"
				+ all_int);
	}
//	public static void sleep4reduceCpu(int i) {
//		if (i % 101 == 0)
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	}
	
	
//	public static void calcNout_ex(long startTime) {
//		long endTime = System.currentTimeMillis();
//		long excTime = (long) (endTime - startTime);
//		calcNout(excTime);
//	}
//	
	static void sleep4reduceCpu(int i) {
		if (i % 101 == 0)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public static void calcNout_ex(long startTime) {
		long endTime = System.currentTimeMillis();
		long excTime = (long) (endTime - startTime);
		calcNout(excTime);
	}

	protected static Statement getStt() {
		
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
