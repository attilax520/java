package com.kok.sport.utils.tasks;

import java.util.LinkedHashMap;
import java.util.List;

import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;

public class DeadlockKill {

	public static void main(String[] args) throws Exception {
		String sql=" select concat('KILL ',id,';') as t from information_schema.processlist ";
	//	sql="drop table foot_match_ex_saiguo";
	//	sql="select * from   foot_match_ex_saiguo limit 1";
	//	check table foot_match_ex_saiguo
	//	sql="repair table foot_match_ex_saiguo";
	//	
		 List<LinkedHashMap> li = null;
		 
			li = MybatisUtil.query(sql);
		 System.out.println(li);
		 MybatisMapper MybatisMapper1=	 MybatisUtil.getMybatisMapper();
		 for (LinkedHashMap linkedHashMap : li) {
			 try {
					String kill=(String) linkedHashMap.get("t");
					   System.out.println(MybatisMapper1.update(kill));
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		 System.out.println("f");

	}

}
