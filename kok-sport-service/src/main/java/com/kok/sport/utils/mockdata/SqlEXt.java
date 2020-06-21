package com.kok.sport.utils.mockdata;

import com.kok.sport.utils.db.MybatisUtil;

public class SqlEXt {

	public static void main(String[] args) throws Exception {
	String sql="ALTER TABLE `football_odds_t`\r\n" + 
			"ADD INDEX `paichon` (`company_id`, `match_id`, `odds_type`, `change_time`) ;";
	sql="insert football_odds_t set id=5";
	sql=" check table football_odds_t;";
	sql=" check table football_score_t;";  //if ok ret 1
//	sql=" drop table football_odds_t;";
	sql="select * from football_odds_t limit 1";
//	System.out.println( MybatisUtil.execSqlUpdateNInsert(sql));
	sql="select * from  foot_match_ex_saiguo";
	System.out.println( MybatisUtil.query(sql) );
System.out.println("f");
	}

}
