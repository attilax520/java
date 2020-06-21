package com.kok.sport.utils.tasks;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;

public class DBexe {

	public static void main(String[] args) throws Exception {
		String f = "D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\tasks\\u.sql";
		String t = FileUtils.readFileToString(new File(f));
		// String sql=" select * from football_match_t order by create_time desc limit
		// 10 ";
		// sql="drop table foot_match_ex_saiguo";
		// sql="select * from foot_match_ex_saiguo limit 1";
		// check table foot_match_ex_saiguo
		// sql="repair table foot_match_ex_saiguo";
		//

		String sql = "select * from football_match_t where  odd_yapan_home is not null order by match_time desc limit 2000  ";
		List<LinkedHashMap> li = null;

		System.out.println(t);
		System.out.println(MybatisUtil.getMybatisMapper().update(t));
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		for (LinkedHashMap linkedHashMap : li) {
			try {
				String s2 = "update football_match_t m set odd_yapan_home=oddsOf_home_odds(`m`.`id`,0,1)  where id="
						+ linkedHashMap.get("id");
				System.out.println(s2);
				System.out.println(MybatisMapper1.update(s2));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("f");

	}

}
