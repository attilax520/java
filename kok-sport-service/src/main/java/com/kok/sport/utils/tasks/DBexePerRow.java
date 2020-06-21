package com.kok.sport.utils.tasks;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;

public class DBexePerRow {

	public static void main(String[] args) throws Exception {
		String f="D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\tasks\\oneweekMatch.sql";
		String t=FileUtils.readFileToString(new File(f));
	//	String sql=" select * from football_match_t order by create_time desc limit 10 ";
	//	sql="drop table foot_match_ex_saiguo";
	//	sql="select * from   foot_match_ex_saiguo limit 1";
	//	check table foot_match_ex_saiguo
	//	sql="repair table foot_match_ex_saiguo";
	//	
	//	String sql = "select * from football_match_t where  odd_yapan_home is   null order by match_time desc limit 2000  ";
	 
		//update football_match_t  set odd_yapan_home=1,odd_yapan_tie=1,odd_yapan_away=1 where  id='3384848'

		System.out.println(t);
		List<LinkedHashMap> li =MybatisUtil.getMybatisMapper().querySql(t);
		System.out.println(li);
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		int n=0;
		for (LinkedHashMap linkedHashMap : li) {
			try {
				n++;
				String s2 = "update football_match_t m set upchk=0, odd_yapan_home=oddsOf_home_odds(`m`.`id`,0,1)  where id="
						+ linkedHashMap.get("id");
				System.out.println(s2);
				System.out.println(n+"/"+li.size());
				System.out.println(MybatisMapper1.update(s2));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		 System.out.println("f");

	}

}
