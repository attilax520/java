/**
 * @author attilax 老哇的爪子
	@since  2014-9-1 上午01:33:27$
 */
package com.attilax.db;
//import com.attilax.core;
//import com.attilax.retryO7;
// 
////r44 import com.attilax.biz.seo.getConnEx;
//import com.attilax.persistence.DBCfg;
//import com.attilax.util.HibernateSessionFactory;
//import com.foksda.mass.retryRzt;




//import static  com.attilax.core.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.net.*;
import java.io.*;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.transform.Transformers;
/**
 * @author  attilax 老哇的爪子
 *@since  2014-9-1 上午01:33:27$
 */
public class DBX   {
	//  attilax 老哇的爪子 上午01:33:27   2014-9-1   
	// public static Session sess;
	 public DBX(){}
	/**
	@author attilax 老哇的爪子
		@since  2014-9-2 上午02:06:23$
	
	 * @param session
	 */
//	public DBX(Session session) {
//		//  attilax 老哇的爪子 上午02:06:23   2014-9-2   
//		//this.session=session;
//	 
//	}
	public List<Map> execSql(String sql)
	{

 new RuntimeException(" no implt" );
return null;
	}
	public List executeQuery(String sql)
	{
	throw	 new RuntimeException(" no implt" );
	//	 return null;
	}
	public Integer execSql_retInt(String sql)
	{

 new RuntimeException(" no implt" );
return null;
	}
	
	public   List convertList(ResultSet rs) throws SQLException {
		if(rs ==null)
			throw new RuntimeException(" RS is "); 
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map rowData = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
}
	
	public Connection getConnection()  
	{
		throw new RuntimeException(" not imp");
	//	return null;
		
	}
	public List<Map> findBySql(Connection conn, String sql) {
		PreparedStatement prepareStatement;
		ResultSet resultSet;
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.execute() ;
			  resultSet =prepareStatement.getResultSet();
			
			List li = convertList(resultSet);
			return li;
		} catch (SQLException e) {
			 
			 throw new RuntimeException(e);
		}
			
			
//		PreparedSentence ps = new PreparedSentence(session,
//				" ");   //where name like '%name%'
		//try {
			
		//	System.out.println(core.toJsonStrO88(li));
//		} catch ( Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	//	System.out.println("---f");
		 
	}
	
	public List<Map> findBySql(String sql) {
		 new RuntimeException(" no implt" ); 
//		Query q =session.createSQLQuery(sql); 
//		 q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//	    List<Map> li=q.list();
//		return li;
		return null;
	}
		public int getCount(String sql) {
			 new RuntimeException(" no implt" ); 
//		String s = "	select count(*) from ( " + sql + ") as t ";
//		Query query = this.session.createSQLQuery(s.toString());
//		Number uniqueResult = (Number) query.uniqueResult();
//		return uniqueResult.intValue();
			return 0;

	}
		public int execSql(Connection conn, String string) {
			PreparedStatement prepareStatement;
			ResultSet resultSet;
			try {
				prepareStatement = conn.prepareStatement(string);
			return 	prepareStatement.executeUpdate() ;
				 
			} catch (SQLException e) {
				 
				 throw new RuntimeException(e);
			}
		}
		
		public int executeUpdate(Connection conn, String string) {
			PreparedStatement prepareStatement;
			ResultSet resultSet;
			try {
				prepareStatement = conn.prepareStatement(string);
			return 	prepareStatement.executeUpdate() ;
				 
			} catch (SQLException e) {
				 
				 throw new RuntimeException(e);
			}
		}
 
 
 
//	public DBCfg getDBCfg(String dbcfg_file) {
//
//		DBCfg cfg = new DBCfg();
//		try {
//			// propertyReader pro=new Properties();
//
//			FileInputStream fis = new FileInputStream(new File(dbcfg_file));
//			Properties properties = new Properties();
//			properties.load(fis);
//			// pro.
//			String url = properties.getProperty("jdbc.url");
//			String driver = properties.getProperty("jdbc.driverClassName");
//
//			String uname = properties.getProperty("jdbc.username");
//			String pwd = properties.getProperty("jdbc.password");
//			//
//			// configuration.configure(propertyFile);
//			// String url =
//			// configuration.getProperty("connection.url");
//			// String driver = configuration
//			// .getProperty("connection.driver_class");
//			//
//			// String uname =
//			// configuration.getProperty("connection.username");
//			// String pwd =
//			// configuration.getProperty("connection.password");
//			// // String url =
//			// "jdbc:mysql://@host/@db?zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useUnicode=true&characterEncoding=utf8";
//
//			cfg.setUrl(url);
//			cfg.setUser(uname);
//			cfg.setPassword(pwd);
//			return cfg;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
	public Map uniqueResult(String $sql) {
		// TODO Auto-generated method stub
		return null;
	}
	public int executeUpdate(String sql) {
		// TODO Auto-generated method stub
		return -1;
	}
	/**
	attilax    2016年4月25日  下午10:02:54
	 * @param sql
	 * @return
	 */
	public Map uniqueResult2row(String sql) {
	throw new RuntimeException("not imp");
	}

//	public   final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
//	  public   Session getSessionOri() throws HibernateException {
//	        Session session = (Session) threadLocal.get();
//
//			if (session == null || !session.isOpen()) {
//				if (sessionFactory == null) {
//					rebuildSessionFactory();
//				}
//				session = (sessionFactory != null) ? sessionFactory.openSession()
//						: null;
//				threadLocal.set(session);
//			}
//
//	        return session;
//	    }
	
	 
	 
}

//  attilax 老哇的爪子