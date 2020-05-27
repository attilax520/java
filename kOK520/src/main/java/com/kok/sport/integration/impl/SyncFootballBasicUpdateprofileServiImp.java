package com.kok.sport.integration.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballBasicUpdateprofileServi;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;

@Service
@SuppressWarnings("all")
public class SyncFootballBasicUpdateprofileServiImp implements SyncFootballBasicUpdateprofileServi {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	public SqlSession session;

	@Override
	public void Football_Basic_Update_profile() throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Basic.Update_profile");
		MybatisMapper mpr = session.getMapper(MybatisMapper.class);
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
//		JsonArray competitons = json.getAsJsonObject("data").getAsJsonObject("delete").getAsJsonArray("competitons");
//		String sql = "insert  into Football.Basic.Update_profile(  id,data  )values( @id@,'@data@')";
		// sql = processVars(sql, json.getAsJsonObject("data") );

//		SqlSession session = MybatisUtil.getConn();
//
//		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);
//
//		System.out.println(mapper.updateV2(sql));
	}

}
