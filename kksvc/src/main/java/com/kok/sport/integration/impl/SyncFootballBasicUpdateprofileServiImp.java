package com.kok.sport.integration.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballBasicUpdateprofileServi;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.db.MybatisUtil;
@Service
public class SyncFootballBasicUpdateprofileServiImp implements SyncFootballBasicUpdateprofileServi {
	
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	SqlSession session;
	@Override
	public   void Football_Basic_Update_profile() throws Exception {
		JsonObject json = CaptchData.getJsonRzt("Football.Basic.Update_profile");

		JsonArray ja = json.getAsJsonObject("data").getAsJsonObject("delete").getAsJsonArray("teams");
		JsonArray competitons = json.getAsJsonObject("data").getAsJsonObject("delete").getAsJsonArray("competitons");
		String sql = "insert  into Football.Basic.Update_profile(  id,data  )values( @id@,'@data@')";
		// sql = processVars(sql, json.getAsJsonObject("data") );
		logger.info(sql);

		SqlSession session = MybatisUtil.getConn();

		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

		System.out.println(mapper.updateV2(sql));
	}

}
