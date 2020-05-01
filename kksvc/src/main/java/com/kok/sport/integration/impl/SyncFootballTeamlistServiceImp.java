package com.kok.sport.integration.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballTeamlistServic;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.DbgUtil;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.ListBldr;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.UniqueEx;
import com.kok.sport.utils.ql.SqlBldrUtil;

@Service
@SuppressWarnings("all")
public class SyncFootballTeamlistServiceImp implements SyncFootballTeamlistServic {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Autowired
	public SqlSession session;

	public void Football_Team_list() throws Exception {

		// areas

		// 'Basketball.Basic.Matchevent_list'
		JsonObject json = CaptchData.getJsonRzt("Football.Basic.Team_list");

		// url =
		// "http://www.skrsport.live/?service=Basketball.Basic.Match_deleted_ids&username=sport_api&secret=0gclkqzK";
		JsonArray ja = json.getAsJsonArray("data");

		// SqlSession session = MybatisUtil.getConn();
		for (JsonElement JsonElement1_item : ja) {

			// item = json.data.teams[fld]; String id =
			// JsonElement1_item.getAsJsonObject().get("id").getAsString();
			try {
				// logger.info(JsonElement1_item);
				Map jsonmap = JsonGsonUtil.toMap(JsonElement1_item.getAsJsonObject());
				jsonmap.put("create_time",SqlBldrUtil.fun( "now()"));
				jsonmap.put("delete_flag", 0);
				// unique

				String tableName = "basketball_team_t";
				// MybatisUtil.uniqueIdx(tableName, "id", id, session);

				String li_col_placeholder = "id,name_zh,short_name_zh,name_zht,short_name_zht,name_en,short_name_en,logo";
				Map m = SqlBldrUtil.getColsValsMap4mybatis(tableName, li_col_placeholder, jsonmap,
						session.getConnection());

				System.out.println(session.update("insert_into_football_team_t", m));

//				String sql = "insert  into football_team_t(  id,name_zh,short_name_zh,name_zht,short_name_zht,name_en,short_name_en,logo ,create_time ,delete_flag )values("
//						+ " @id@,@name_zh@,@short_name_zh@,@name_zht@,@short_name_zht@,@name_en@,@short_name_en@,@logo@,now(),0)";
//				sql = JsonGsonUtil.processVars(sql, JsonElement1_item.getAsJsonObject());
//				logger.info(sql);
//				MybatisUtil.execSql(sql, session);
//				// var rzt = await query(connection, sql)
				// logger.info(rzt);
				// throw new RuntimeException("d");
				DbgUtil.tmpStp();

			} catch (UniqueEx e) {
//				try {
//					String sql = "update football_team_t set name_en='@name_en@',short_name_en='@short_name_en@',logo='@logo@'  where id="
//							+ id;
////							+ "  )values("
////							+ " @id@,@name_zh@,@short_name_zh@,@name_zht@,@short_name_zht@,,@short_name_en@,)";
//					sql = JsonGsonUtil.processVars(sql, JsonElement1_item.getAsJsonObject());
//					logger.info(sql);
//					MybatisUtil.execSql(sql, session);
				DbgUtil.tmpStp();
			} catch (Exception e2) {
				logger.error(e2);
				DbgUtil.tmpStp();
			} catch (Throwable e) {
				logger.error(e);
				DbgUtil.tmpStp();
			}
			// break;
			// break;
			// process.exit();
			//end for
			DbgUtil.tmpStp();
		}

		// basket_match_event_t

		// console.log("f");
	}

}
