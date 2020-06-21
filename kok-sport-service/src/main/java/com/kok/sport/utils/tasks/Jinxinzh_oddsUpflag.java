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
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.mockdata.unitRecentlyV2;
import com.kok.sport.utils.ql.QlSpelUtil;

//  com.kok.sport.utils.tasks.JinxinzhScoreFresh
public class Jinxinzh_oddsUpflag {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(unitRecentlyV2.class);

	public static void main(String[] args) throws Exception {
		new Jinxinzh_oddsUpflag().jinxinzhon_Football_Live_Match_list_refreshScore();
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
		MybatisMapper MybatisMapper1=MybatisUtil.getMybatisMapper();
		for (List MatchItem : result1) {

			// param.put("home_id", ((List) MatchItem.get(5)).get(0));3381484
			try {

				LinkedHashMap param = Maps.newLinkedHashMap();
				Object mid =((Double) MatchItem.get(0)).intValue();
				param.put("id", mid);
				param.put("match_id", mid);
				param.put("match_event_id", MatchItem.get(1));
				Object stt2 = MatchItem.get(2);
				int stt = ((Double) stt2).intValue();
				if (stt >= 2 && stt <= 7) { // ing match
					n++;
					logger.info("==>n:" + n);
					//match_id, odds_type, change_time
					upYapan(MybatisMapper1, mid,1);	
					upYapan(MybatisMapper1, mid,2);	

					logger.info(param);
//					syncFootballLiveMatchlistServiceImp.socreInsert_Live_Match_list(param, MatchItem, 1);
//					syncFootballLiveMatchlistServiceImp.socreInsert_Live_Match_list(param, MatchItem, 2);
//					// logger.info("==>ret
//					// insert(\"com.kok.sport.dao.FootballMatchDao.into_football_match_t::" + r);
                    break;
				}

			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	//  select * from football_odds_t where    match_id=2694495 and odds_type=1 and change_time=1592234969
	private void upYapan(MybatisMapper MybatisMapper1, Object mid,int yapanDsPan) {
		try {
			String sql="select * from football_odds_t  where match_id= "+mid+" and odds_type="+yapanDsPan+" order by change_time desc limit 1";
			System.out.println(sql);
			List<LinkedHashMap> ypLi=MybatisMapper1.querySql(sql);
			LinkedHashMap yp=ypLi.get(0);
				  sql="update football_odds_t set upflag=1 where id="+yp.get("ip").toString();
				System.out.println(sql);
			System.out.println("==>sqlrzt:"+MybatisMapper1.update(sql));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
