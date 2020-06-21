package com.kok.sport.utils.mockdata;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.constant.HttpRequestUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.QlSpelUtil;

/**
 * D:\prj\sport-service\kok-sport-service\target\classes\com\kok\sport/utils\mockdata\TeamRankFlush.class
 * @param args
 */
/// /
public class TeamRankFlush {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(TeamRankFlush.class);

	public static void main(String[] args) throws Exception {
	//	int matID = 3374157;
		
 	forJinxinzh();
	//	setRank(matID);
		
		
		
		System.out.println("F");

	}

	private static void forJinxinzh() throws Exception {
		String sql = "select * from foot_match_v_jinxinzhong ";
		SqlSessionFactory SqlSessionFactory1=MybatisUtil.getSqlSessionFactory();
		SqlSession openSession = SqlSessionFactory1.openSession(true);
		MybatisMapper MybatisMapper1 = openSession.getMapper(MybatisMapper.class);
		List<LinkedHashMap> li = MybatisMapper1.querySql(sql);
		for (LinkedHashMap linkedHashMap : li) {
			try {
				String matchID = linkedHashMap.get("match_id").toString();
				setRank(matchID);
			} catch (Exception e) {
				logger.error(e);
			}

		}
	}

	static void setRank(String matID) {
		String url = "http://www.skrsport.live/?service=Football.Analysis.Match_analysis&username=sport_api&secret=0gclkqzK&id="
				+ matID;
		String result = HttpRequestUtil.doGet(url);

		Map m = (Map) JSON.parse(result);
		String mts_exp = "#root['data']['info']";
		List result1 = (List) QlSpelUtil.query(m, mts_exp);
		List zhuduiObj = (List) result1.get(5);
		String zhudui_id = ((Integer) QlSpelUtil.query(m, "#root['data']['info'][5][0]")).toString();
		String zhuduiPaimin = ((String) zhuduiObj.get(1)).toString();
		List keduiObj = (List) result1.get(6);
		String kedui_id = ((Integer) QlSpelUtil.query(m, "#root['data']['info'][6][0]")).toString();
		String keduiPaimin = ((String) keduiObj.get(1)).toString();
		Map newobj = Maps.newLinkedHashMap();
		newobj.put("id", matID);
		newobj.put("match_id", matID);

		newobj.put("league_ranking_judui", zhuduiPaimin);
		newobj.put("league_ranking_kadui", keduiPaimin);
		newobj.put("team_id_zhudui", zhudui_id);
		newobj.put("team_id_kedui", kedui_id);
		// newobj.put("tee_time", QlSpelUtil.query(m,
		// "#root['data']['info']['realtime']"));
		String Sql = "update football_score_t set league_ranking='#{[league_ranking_judui]}' where match_id=#{[match_id]} and team_id=#{[team_id_zhudui]} and team_type=1 ";
		extracted(Sql, newobj);
		String Sql2 = "update football_score_t set league_ranking='#{[league_ranking_kadui]}'  where match_id=#{[match_id]} and team_id=#{[team_id_kedui]} and team_type=2";
		extracted(Sql2, newobj);
	}

	private static void extracted(String sql, Map param) {
		try {
			sql = QlSpelUtil.parse(sql, param);
			logger.info(sql);
			 logger.info( MybatisUtil.getMybatisMapper().update(sql));
		} catch (Exception e) {
			logger.info(e);
		}
		
	}
}
