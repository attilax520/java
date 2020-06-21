package com.kok.sport.utils.mockdata;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;
import com.kok.sport.utils.ql.QlSpelUtil;

public class matchdetailParse {
	
	public static void main(String[] args) throws Exception {
		String f="D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\mockdata\\matchDetail.json";
		String t=FileUtils.readFileToString(new File(f));
		
		Map m = (Map) JSON.parse(t);
	 
		Map newobj=Maps.newLinkedHashMap();
		newobj.put("id", 2723955);
		newobj.put("match_event_id", QlSpelUtil.query(m, "#root['data']['matchevent']['id']") );
		newobj.put("match_status", QlSpelUtil.query(m, "#root['data']['info']['statusid']") );		
		newobj.put("match_time", QlSpelUtil.query(m, "#root['data']['info']['matchtime']") );
	//	newobj.put("tee_time", QlSpelUtil.query(m, "#root['data']['info']['id']") );
		newobj.put("home_id", QlSpelUtil.query(m, "#root['data']['home_team']['id']") );
		newobj.put("away_id", QlSpelUtil.query(m, "#root['data']['away_team']['id']") );
		newobj.put("$insert", "football_match_t");
		
		System.out.println(newobj);
	 	SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
	 System.out.println(MysqlInsertUtil.insertV2(sqlSessionFactory, newobj));	;
	//	     
		
	//	List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);
	}

}
