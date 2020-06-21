package com.kok.sport.utils.tasks;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;

public class DBexePerRow2 {

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
				String sql="select   * from football_odds_t where  odds_type=1 and  match_id="+linkedHashMap.get("id");
				System.out.println(sql);
				LinkedHashMap yapanRs=MybatisMapper1.querySql(sql).get(0);
				sql = "update football_match_t  set odd_yapan_home="+yapanRs.get("home_odds")+",odd_yapan_tie="+yapanRs.get("tie_odds")+",odd_yapan_away="+yapanRs.get("away_odds")+" where   id="
						+ linkedHashMap.get("id");
				System.out.println(sql);
				
				System.out.println(MybatisMapper1.update(sql));
				
				  sql="select   * from football_odds_t where  odds_type=2 and  match_id="+linkedHashMap.get("id");
				System.out.println(sql);
				LinkedHashMap dasyaopeRs=MybatisMapper1.querySql(sql).get(0);
			
				sql = "update football_match_t  set odd_ds_home="+dasyaopeRs.get("home_odds")+",odd_ds_tie="+dasyaopeRs.get("tie_odds")+",odd_ds_away="+dasyaopeRs.get("away_odds")+" where   id="
						+ linkedHashMap.get("id");
				System.out.println(sql);
				
				System.out.println(MybatisMapper1.update(sql));
				
				System.out.println(n+"/"+li.size());
				//break;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		 System.out.println("f");

	}

	private static void chMat(MybatisMapper MybatisMapper1, LinkedHashMap linkedHashMap,int oddtype) {
		String sql="select   * from football_odds_t where  odds_type=1 and  match_id="+linkedHashMap.get("id");
		LinkedHashMap yapanRs=MybatisMapper1.querySql(sql).get(0);
		String s2 = "update football_match_t  set odd_yapan_home="+linkedHashMap.get("home_odds")+",odd_yapan_tie="+linkedHashMap.get("tie_odds")+",odd_yapan_away="+linkedHashMap.get("away_odds")+" where   id="
				+ linkedHashMap.get("id");
		System.out.println(s2);
		
		System.out.println(MybatisMapper1.update(s2));
	}

}
