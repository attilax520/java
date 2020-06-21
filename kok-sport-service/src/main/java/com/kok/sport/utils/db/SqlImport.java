package com.kok.sport.utils.db;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kok.sport.utils.MybatisMapper;

public class SqlImport {
	public static void main(String[] args) throws Exception {
		  changesP();
		
		String sql="select  * from  information_schema.VIEWS where table_schema='eladmin'   and DEFINER='root@%'";
		List<LinkedHashMap> li = MybatisUtil.getMybatisMapper().querySql(sql);

		SqlSession sess = MybatisUtil.getConn();
		for (LinkedHashMap r : li) {
			try {
				String s = "ALTER DEFINER=`admin`@`%` SQL SECURITY INVOKER view " + r.get("TABLE_NAME") + " as  " + r.get("VIEW_DEFINITION");
				System.out.println(s);
				// 	exeSql(sess, s);
				System.out.println("ff");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		//  ALTER   SQL SECURITY INVOKER view   ttt as select 1
	}

	private static void changesP() throws Exception {
		String sql = "select * from  information_schema.ROUTINES where ROUTINE_schema='eladmin'";
		List<LinkedHashMap> li = MybatisUtil.getMybatisMapper().querySql(sql);

		SqlSession sess = MybatisUtil.getConn();
		for (LinkedHashMap r : li) {
			
			// ALTER function datesaisi SQL SECURITY INVOKER READS SQL DATA
			String s = "ALTER " + r.get("ROUTINE_TYPE") + " " + r.get("ROUTINE_NAME")
					+ " DEFINER =`admin`@`%`  SQL SECURITY INVOKER  READS SQL DATA";
			exeSql(sess, s);
			;
		}
	}

	private static void exeSql(SqlSession sess, String s) {
		System.out.println(s);
		MybatisMapper MybatisMapper1=	sess.getMapper(MybatisMapper.class);
		System.out.println("-->r:" + MybatisMapper1.update(s));
	}

}
