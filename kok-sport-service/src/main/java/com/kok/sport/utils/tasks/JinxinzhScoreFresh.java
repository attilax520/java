package com.kok.sport.utils.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.kok.sport.integration.impl.SyncFootballLiveMatchlistServiceImp;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.mockdata.unitRecentlyV2;
import com.kok.sport.utils.ql.QlSpelUtil;

//  com.kok.sport.utils.tasks.JinxinzhScoreFresh
public class JinxinzhScoreFresh {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(unitRecentlyV2.class);

	public static void main(String[] args) throws Exception {
		new JinxinzhScoreFresh().jinxinzhon_Football_Live_Match_list_refreshScore();
		System.out.println("f");
	}
	
	public void jinxinzhon_Football_Live_Match_list_refreshScore() throws Exception {
		// String dt = "20200523";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		SyncFootballLiveMatchlistServiceImp syncFootballLiveMatchlistServiceImp = new SyncFootballLiveMatchlistServiceImp();
		syncFootballLiveMatchlistServiceImp.session = MybatisUtil.getConn();
		int n = 0;
		for (List MatchItem : result1) {

			// param.put("home_id", ((List) MatchItem.get(5)).get(0));3381484
			try {

				LinkedHashMap param = Maps.newLinkedHashMap();
				param.put("id", MatchItem.get(0));
				param.put("match_id", MatchItem.get(0));
				param.put("match_event_id", MatchItem.get(1));
				Object stt2 = MatchItem.get(2);
				int stt = ((Double) stt2).intValue();
				if (stt >= 2 && stt <= 7) { // ing match
					n++;
					logger.info("==>n:" + n);

					param.put("match_status", stt);
					param.put("match_time", ((Double) MatchItem.get(3)).intValue());
					param.put("tee_time", ((Double) MatchItem.get(4)).intValue());

					param.put("tee_time sub match_time",
							((Double) MatchItem.get(4)).intValue() - ((Double) MatchItem.get(3)).intValue());
					param.put("home_id", ((List) MatchItem.get(5)).get(0));
					param.put("away_id", ((List) MatchItem.get(6)).get(0));
					param.put("match_detail", ((List) MatchItem.get(7)).get(0));
					param.put("which_round", ((List) MatchItem.get(7)).get(1));
					param.put("neutral_site", ((List) MatchItem.get(7)).get(2));

					logger.info(param);
					syncFootballLiveMatchlistServiceImp.socreInsert_Live_Match_list(param, MatchItem, 1);
					syncFootballLiveMatchlistServiceImp.socreInsert_Live_Match_list(param, MatchItem, 2);
					// logger.info("==>ret
					// insert(\"com.kok.sport.dao.FootballMatchDao.into_football_match_t::" + r);

				}

			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

}
