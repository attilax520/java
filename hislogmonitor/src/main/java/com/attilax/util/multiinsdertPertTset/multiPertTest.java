package com.attilax.util.multiinsdertPertTset;

 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang3.RandomStringUtils;

import com.cnhis.cloudhealth.commons.util.oplogutil.AsynUtil;
import com.google.common.collect.Lists;

public class multiPertTest {
	private static final int pool_count = 20;
	public static	List conn_li=Lists.newArrayList();;
	
	public synchronized static void ini_pool_lev1(int lev_pool_count) {
		 		 
			 for(int i=0;i<pool_count;i++)
				{
					 FutureTask<Object> FutureTask_updateCviBdj=new FutureTask( new Callable<Object>() {

							@Override
							public Object call()   {				 
							 
								return null;
							}
						}) ;
					 pool_ExecutorService_hiprid_lev1.submit(FutureTask_updateCviBdj, "threadName");
				}
			
		}
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, ExecutionException {
		ini_pool_lev1(100);
//Thread.sleep(10000);
		String url = "jdbc:postgresql://192.168.1.18:5432/cloudhealth";
		String user = "postgres";
		String password = "cloudhealth";
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		Connection connection2 = DriverManager.getConnection(url, user, password);
		Connection connection3 = DriverManager.getConnection(url, user, password);
		
		for(int i=0;i<1000;i++)
		{
			//Connection connection5 = DriverManager.getConnection(url, user, password);
			 
		//	conn_li.add(connection5);
		}

		long l1 = System.currentTimeMillis();

	 //	join(connection);//  523--423ms
	//	splt(connection);  // 900ms
	  	multi(connection,connection2,connection3);
		long l2 = System.currentTimeMillis();
		System.out.println(l2 - l1 + "ms");
		Thread.sleep(30000);
		 pool_ExecutorService_hiprid_lev1.shutdown();

	}
	static ExecutorService pool_ExecutorService_hiprid_lev1=  Executors.newFixedThreadPool(pool_count);
	private static void multi(Connection connection, Connection connection2, Connection connection3) throws SQLException, InterruptedException, ExecutionException {
		for (int i = 0; i < 1000; i++) {

			String pk_s = RandomStringUtils.randomNumeric(9);
			String sql11 = "INSERT INTO VAF1(VAF01,vaa01,bda01)values(@pk@,1,1)"; // vaf01	 	// pk
			sql11 = sql11.replaceAll("@pk@", pk_s);
			String sql_final=sql11;
			
			
			String sdql2 = " insert into veh1 (vaf01)values(@pk@)";
			sdql2 = sdql2.replaceAll("@pk@", pk_s);
			String sql2_final=sdql2;
			
			
			String sql3 = "  insert into vfa1 (vaf01)values(@pk@) ";
			sql3 = sql3.replaceAll("@pk@", pk_s);
			
			
			String Sql_join = sql11 + ";" + sdql2 + ";" + sql3;

			FutureTask ft1=new FutureTask<>(new Callable() {

				@Override
				public Object call() throws Exception {
					Statement stt = null;
					try {
						//Connection connection2=conn_li.get(index)
						stt = connection2.createStatement();
						stt.executeUpdate(sql_final);
						 stt.close();
					//	connection2.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return stt;
				}
			});
			pool_ExecutorService_hiprid_lev1.submit(ft1);
			
			FutureTask ft2=new FutureTask<>(new Callable() {

				@Override
				public Object call() throws Exception {
					 
					Statement stt = null;
					try {
						Statement stt2 = connection3.createStatement();
						stt2.executeUpdate(sql2_final);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return stt;
				}
			});
			pool_ExecutorService_hiprid_lev1.submit(ft2);
		
 

			
			Statement stt3 = connection.createStatement();
			stt3.executeUpdate(sql3);
			ft1.get();
			ft2.get();
		}
	}

	private static void splt(Connection connection) throws SQLException {
		for (int i = 0; i < 1000; i++) {

			String pk_s = RandomStringUtils.randomNumeric(9);
			String sql11 = "INSERT INTO VAF1(VAF01,vaa01,bda01)values(@pk@,1,1)"; // vaf01
																					// pk
			sql11 = sql11.replaceAll("@pk@", pk_s);
			String sdql2 = " insert into veh1 (vaf01)values(@pk@)";
			sdql2 = sdql2.replaceAll("@pk@", pk_s);

			String sql3 = "  insert into vfa1 (vaf01)values(@pk@) ";
			sql3 = sql3.replaceAll("@pk@", pk_s);

			String Sql_join = sql11 + ";" + sdql2 + ";" + sql3;

			Statement stt = connection.createStatement();
			stt.executeUpdate(sql11);
			Statement stt2 = connection.createStatement();
			stt2.executeUpdate(sdql2);
			Statement stt3 = connection.createStatement();
			stt3.executeUpdate(sql3);
		}
	}
	
	
	private static void join(Connection connection) throws SQLException {
		for (int i = 0; i < 1000; i++) {

			String pk_s = RandomStringUtils.randomNumeric(9);
			String sql11 = "INSERT INTO VAF1(VAF01,vaa01,bda01)values(@pk@,1,1)"; // vaf01
																					// pk
			sql11 = sql11.replaceAll("@pk@", pk_s);
			String sdql2 = " insert into veh1 (vaf01)values(@pk@)";
			sdql2 = sdql2.replaceAll("@pk@", pk_s);

			String sql3 = "  insert into vfa1 (vaf01)values(@pk@) ";
			sql3 = sql3.replaceAll("@pk@", pk_s);

			String Sql_join = sql11 + ";" + sdql2 + ";" + sql3;

			Statement stt = connection.createStatement();
			stt.executeUpdate(Sql_join);
		}
	}

}
