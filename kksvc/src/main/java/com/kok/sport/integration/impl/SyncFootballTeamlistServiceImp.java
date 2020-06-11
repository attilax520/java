package com.kok.sport.integration.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

	public static void main(String[] args) throws Exception {
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
//		lsimp.session = MybatisUtil.getConn();
//		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
	//	teamSvr.Football_Team_list("D:\\cache\\Football.Basic.Team_list.json");
		teamSvr.Football_Team_list();
	}

	@Autowired
	public SqlSession session;

	public void Football_Team_list(String cachefile) throws Exception {

		// areas

		// 'Basketball.Basic.Matchevent_list'
		String readFileToString = FileUtils.readFileToString(new File(cachefile));
		foot_mat_list(readFileToString);

		// basket_match_event_t

		// console.log("f");
	}

	private void foot_mat_list(String readFileToString) {
		JsonObject json = new JsonParser().parse(readFileToString).getAsJsonObject();

foot_mat_list(json);
	}

	private void foot_mat_list(JsonObject json) {
		// url =
		// "http://www.skrsport.live/?service=Basketball.Basic.Match_deleted_ids&username=sport_api&secret=0gclkqzK";
				JsonArray ja = json.getAsJsonArray("data");
		
		// SqlSession session = MybatisUtil.getConn();
				for (JsonElement JsonElement1_item : ja) {
					logger.info(JsonElement1_item);
					// item = json.data.teams[fld]; String id =
					// JsonElement1_item.getAsJsonObject().get("id").getAsString();
					try {
						// logger.info(JsonElement1_item);
						Map jsonmap = JsonGsonUtil.toMap(JsonElement1_item.getAsJsonObject());
						// jsonmap.put("create_time",SqlBldrUtil.fun( "now()"));
						jsonmap.put("delete_flag", 0);
						// unique
		
						String tableName = "basketball_team_t";
						// MybatisUtil.uniqueIdx(tableName, "id", id, session);
		
//						String li_col_placeholder = "id,name_zh,short_name_zh,name_zht,short_name_zht,name_en,short_name_en,logo";
//		//				Map m = SqlBldrUtil.getColsValsMap4mybatis(tableName, li_col_placeholder, jsonmap,
//		//						session.getConnection());
		
						System.out.println(session.update("insert_into_football_team_t", jsonmap));
		
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
					// end for
					DbgUtil.tmpStp();
				}
	}

	public void Football_Team_list() throws Exception {

		// areas

		// 'Basketball.Basic.Matchevent_list'
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Basic.Team_list");
		foot_mat_list(json);
	}

}
