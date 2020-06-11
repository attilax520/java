package com.kok.sport.utils.tasks;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;
import com.kok.sport.utils.ql.QlSpelUtil;
 

//  com.kok.sport.utils.tasks.TeamTmp
public class TeamTmp {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String f="D:\\cache2\\teamtmp.json";
		 String t=FileUtils.readFileToString(new File(f));
		// =new JsonParser().parse(t).getAsJsonObject();
		 
		 Map m2=JSON.parseObject(t);
		// String mts_exp = "#root['data'][update']['teams']";
	//		Object query = QlSpelUtil.query(m2, mts_exp);
			MybatisMapper mpr = MybatisUtil.getConn().getMapper(MybatisMapper.class);
			JsonObject json;
			json=CaptchData.	getJsonRztFromUrl("Football.Basic.Update_profile");
			JsonArray ja = json.getAsJsonObject("data").getAsJsonObject("update").getAsJsonArray("teams");
		
			
			for (JsonElement jsonElement : ja) {
				try {

					Map m = JsonGsonUtil.toMap(jsonElement.getAsJsonObject());
					m.put("$insert", "football_team_t");
					logger.info(m);
					logger.info(MysqlInsertUtil.insertV2_faster(null, m, mpr));
				} catch (Exception e) {
					logger.error(e);
				}

			}

	}

}
