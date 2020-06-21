package com.kok.sport.utils.tasks;

import java.util.LinkedHashMap;
import java.util.List;

import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;

public class DBQueryBrower {

	public static void main(String[] args) throws Exception {
		String sql=" select * from football_match_t order by create_time desc limit 10 ";
	//	sql="drop table foot_match_ex_saiguo";
	//	sql="select * from   foot_match_ex_saiguo limit 1";
	//	check table foot_match_ex_saiguo
	//	sql="repair table foot_match_ex_saiguo";
	//	
		 sql=" select * from foot_match_v_jinxinzhong  limit 10 ";
		 sql=" select * from football_match_t  where id=3391052 ";
		 
		 List<LinkedHashMap> li = null;
		 
			li = MybatisUtil.query(sql);
		 System.out.println(li);
//		 MybatisMapper MybatisMapper1=	 MybatisUtil.getMybatisMapper();
//		 for (LinkedHashMap linkedHashMap : li) {
//			 try {
//					String kill=(String) linkedHashMap.get("t");
//					   System.out.println(MybatisMapper1.update(kill));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		
//		}
		 System.out.println("f");

	}

}
