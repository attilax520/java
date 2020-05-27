package com.kok.sport.utils;

import java.io.File;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kok.sport.utils.db.MybatisUtil;

public class DataStrutsGene {

	public static void main(String[] args) throws Exception {
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		String tabs="basketball_event_t,basketball_match_t,basketball_odds_t,basketball_player_t,basketball_score_t,basketball_stage_t,basketball_stats_t,basketball_team_player_t,basketball_team_t,basketball_tlive_t,football_distribution_t,football_environment_t,football_event_t,football_formation_t,football_incident_t,football_injury_t,football_league_score_t,football_match_t,football_odds_t,football_player_incident_t,football_score_t,football_stage_t,football_stats_t,football_team_player_t,football_team_t,football_tlive_t,kok_match_stream_t,kok_match_t,match_season_t,match_stream_t,sys_area_t,sys_country_t,";
	String[] tbsa=tabs.split(",");
	File file = new File("d:\\datastrust.txt");
	FileUtils.deleteQuietly(file);
	for (String table : tbsa) {
		String sql="SELECT 	c.TABLE_NAME 数据来源名称英文, 	table_comment 含义,  	COLUMN_NAME 字段名, 	COLUMN_COMMENT 备注字段含义,	DATA_TYPE 字段数据类型   FROM 	information_schema.`COLUMNS` c left join information_schema.`TABLES` t on c.TABLE_NAME=t.TABLE_NAME WHERE 	c.TABLE_NAME = '{0}'  ";
		sql=sql.replaceAll("\\{0\\}", table);
		//sql=	MessageFormat.format(sql, table);
		
		List<LinkedHashMap> li= sqlSessionFactory.openSession(true).getMapper(MybatisMapper.class).querySql(sql);
		LinkedHashMap r1=li.get(0);
		System.out.println(r1.get("数据来源名称英文") +"  "+r1.get("含义")  );
	
		FileUtils.write(file, "\r\n\r\n", true);
		FileUtils.write(file, "== "+ r1.get("数据来源名称英文") +"  "+r1.get("含义")+"\r\n", true);
		for (LinkedHashMap r : li) {
        	
        	int cellwidth2=15;
			String linepart1=PrintCellUtill.printCell(r.get("字段名"), "left", cellwidth2);
			String linepart2=PrintCellUtill.printCell(r.get("备注字段含义"), "left", cellwidth2);
			String linepart3=PrintCellUtill.printCell( showdatatye( r.get("字段数据类型")), "left", cellwidth2);
        	
			FileUtils.write(file, linepart1+linepart2+linepart3+"\r\n", true);
        	System.out.print("\r\n");
		}
		
	}
		

	}

	private static Object showdatatye(Object object) {
		if(object.toString().toLowerCase().contains("int"))
		return "数字"+object;
		if (object.toString().toLowerCase().contains("time"))
			return "时间字符串"+object;
		else
			return "字符串"+object;
	}

}
