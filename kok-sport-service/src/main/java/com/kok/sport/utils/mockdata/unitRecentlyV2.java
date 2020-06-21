package com.kok.sport.utils.mockdata;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import com.kok.sport.controller.FootballMatchController;
import com.kok.sport.controller.FootballOddsController;
import com.kok.sport.integration.impl.SyncFootballBasicUpdateprofileServiImp;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.integration.impl.SyncFootballLiveMatchlistServiceImp;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.integration.impl.SyncFootballTeamlistServiceImp;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.QlSpelUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.kok.sport.entity.FootballScore;
import com.kok.sport.integration.SyncFootballMatchLiveService;
import com.kok.sport.service.FootballScoreService;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.NumUtil;
import com.kok.sport.utils.constant.HttpRequestUtil;
import com.kok.sport.vo.BasketballScoreVO;
import com.kok.sport.vo.FootballScoreVO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class unitRecentlyV2 {
	public static SqlSessionFactory SqlSessionFactory1 = MybatisUtil.getSqlSessionFactoryRE();

//	MybatisUtil.gets

	// // new
	// com.kok.sport.utils.mockdata.unitRecentlyV2().Live_Match_list_stat_score_today()
	// com.kok.sport.utils.mockdata.unitRecentlyV2().test__Football_tlive_Pastdays()
//  new com.kok.sport.utils.mockdata.unitRecentlyV2().test__Football_score_Pastdays()
//  new com.kok.sport.utils.mockdata.unitRecentlyV2().z_test_odds_past8()
	public static void main(String[] args) throws Exception {
		new unitRecentlyV2().test__Football_tlive_Pastdays();
		System.out.println(MybatisUtil.getSqlSessionFactory());
		// select * from foot_match_v_jinxinzhong
	}
	
	@Deprecated
	@Test 
	//  Football.Live.Match_detail_live
	// /sport/queryPageTrad/cmsdb/league
	public void Jinxinzhon_Score_tliveRealytimeFootballMatchController_Live_Match_detail_live() throws Exception {
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;
		// c.FootballMatchService("Football.Live.Match_list");
		//c.FootballMatchService("Football.Live.Match_detail_live");
		//SyncFootballLiveMatchdetailliveImp 
		dtimp.Football_Live_Match_detail_live_RealtimeScore();

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	// jinxinzhon_Football_Live_Match_list_refreshScore
	// add ing match

	@SuppressWarnings("unchecked")
	@Test

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

	@Test
	public void rank() throws Exception {

		String sql = "select  id as match_id from football_match_t ";
		SqlSessionFactory SqlSessionFactory1 = MybatisUtil.getSqlSessionFactory();
		SqlSession openSession = SqlSessionFactory1.openSession(true);
		MybatisMapper MybatisMapper1 = openSession.getMapper(MybatisMapper.class);
		List<LinkedHashMap> li = MybatisMapper1.querySql(sql);
		for (LinkedHashMap linkedHashMap : li) {
			try {
				String matchID = linkedHashMap.get("match_id").toString();
				TeamRankFlush.setRank(matchID);
			} catch (Exception e) {
				logger.error(e);
			}

		}
	}

//  Football_Live_Match_list_statue  statFlush,score flush
	@Test
	public void today_Live_Match_list_statFlush_today() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date()); // SqlSessionFactory1
		SyncFootballLiveMatchlistServiceImp syncFootballLiveMatchlistServiceImp = new SyncFootballLiveMatchlistServiceImp();
		syncFootballLiveMatchlistServiceImp.session = SqlSessionFactory1.openSession(true);
		syncFootballLiveMatchlistServiceImp.Football_Live_Match_list_statue(dt);
	}
@Deprecated
	@Test
	// Live.Match_detail_live jincyo tonji
	public void tonji_Live_Match_detail_live() throws Exception {
	if("a"=="a")
		return;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date()); // SqlSessionFactory1
		// SyncFootballLiveMatchlistServiceImp syncFootballLiveMatchlistServiceImp = new
		// SyncFootballLiveMatchlistServiceImp();

		SyncFootballLiveMatchdetailliveImp SyncFootballLiveMatchdetailliveImp1 = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballLiveMatchdetailliveImp1.session = SqlSessionFactory1.openSession(true);

		SyncFootballLiveMatchdetailliveImp1.jincyoTonji_Football_Live_Match_detail_live();
	}

	@Deprecated
	@Test // /sport/queryPageTrad/cmsdb/league
	public void tliveRealytimeFootballMatchController() throws Exception {
		if("a"=="a")
			return;
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;
		// c.FootballMatchService("Football.Live.Match_list");
		c.FootballMatchService("Football.Live.Match_detail_live");

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	// jinxinzhon_Football_Live_Match_list_statue
	// add ing match

	@SuppressWarnings("unchecked")
	@Test

	public void jinxinzhon_Football_Live_Match_list_statue() throws Exception {
		// String dt = "20200523";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		int n = 0;
		for (List MatchItem : result1) {

			// param.put("home_id", ((List) MatchItem.get(5)).get(0));3381484
			try {

				java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
//设置数的小数部分所允许的最大位数，避免小数位被舍掉
				NF.setMaximumFractionDigits(0);
//设置数的小数部分所允许的最小位数，避免小数位有多余的0
				NF.setMinimumFractionDigits(0);
//去掉科学计数法显示，避免显示为111,111,111,111
				NF.setGroupingUsed(false);
//System.out.println(NF.format(a));

				LinkedHashMap param = Maps.newLinkedHashMap();
				param.put("id", MatchItem.get(0));
				param.put("match_id", MatchItem.get(0));
				param.put("match_event_id", MatchItem.get(1));
				int stt = ((Double) MatchItem.get(2)).intValue();
				if (stt >= 2 && stt <= 7) { // ing match
					n++;
					logger.info("==>n:" + n);
					param.put("match_status", NF.format(MatchItem.get(2)));
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
					// SqlSession session=SqlSessionFactory1.openSession(true);
					int r = session.insert("com.kok.sport.dao.FootballMatchDao.into_football_match_t", param);
					logger.info("==>ret insert(\"com.kok.sport.dao.FootballMatchDao.into_football_match_t::" + r);

				}

			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test

	public void jinxinzhon_Football_Live_Match_list_statue_cellList() throws Exception {
		// String dt = "20200528";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);
		int n = 0;
		SqlSession session = MybatisUtil.getConn();
		for (List MatchItem : result1) {

			// param.put("home_id", ((List) MatchItem.get(5)).get(0));
			try {

				java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
//设置数的小数部分所允许的最大位数，避免小数位被舍掉
				NF.setMaximumFractionDigits(0);
//设置数的小数部分所允许的最小位数，避免小数位有多余的0
				NF.setMinimumFractionDigits(0);
//去掉科学计数法显示，避免显示为111,111,111,111
				NF.setGroupingUsed(false);
//System.out.println(NF.format(a));

				LinkedHashMap param = Maps.newLinkedHashMap();
				param.put("id", MatchItem.get(0));
				param.put("match_id", MatchItem.get(0));
				int stt = ((Double) MatchItem.get(2)).intValue();
				if (stt >= 2 && stt <= 7) {
					Double Double_teeTime = (Double) MatchItem.get(4);
					Double matTime = (Double) MatchItem.get(3);
					int timePass = (Double_teeTime.intValue() - matTime.intValue()) / 60;
					param.put("timePass", timePass);
					param.put("match_status", NF.format(MatchItem.get(2)));

					param.put("match_time", NF.format(matTime));

					param.put("tee_time", NF.format(Double_teeTime));
//				param.put("home_id", ((List) MatchItem.get(5)).get(0));
//				param.put("away_id", ((List) MatchItem.get(6)).get(0));
//				param.put("match_detail", ((List) MatchItem.get(7)).get(0));
//				param.put("which_round", ((List) MatchItem.get(7)).get(1));
//				param.put("neutral_site", ((List) MatchItem.get(7)).get(2));
					n++;
					logger.info(param);
				}

			} catch (Exception e) {
				logger.error(e);
			}
		}
		System.out.println("----ing:" + n);
	}

	@Test // /sport/queryPageTrad/cmsdb/league
	public void scoreStatImdtlyRealytimeFootballMatchController() throws Exception {
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;
		// c.FootballMatchService("Football.Live.Match_list");
		c.FootballMatchService("Football.Live.Match_detail_live");

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Deprecated
	// by Football.Analysis.Match_detail
	@Test
	public void jinxinzhong_score_fresh() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		String sql = "select * from foot_match_exV3 where match_status between 2 and 7 and match_time between unix_timestamp(date_sub(now(), interval 1 day)) and unix_timestamp(now() )  order by match_time desc";
		SqlSession openSession = SqlSessionFactory1.openSession(true);
		MybatisMapper MybatisMapper1 = openSession.getMapper(MybatisMapper.class);
		List<LinkedHashMap> li = MybatisMapper1.querySql(sql);
		for (LinkedHashMap linkedHashMap : li) {
			try {
				String matchID = linkedHashMap.get("match_id").toString();
				// should use lmdlt itfs
				// SyncFootballLiveMatchdetailliveImp.insertMatchScoreData(matchID,
				// openSession);
			} catch (Exception e) {
				logger.error(e);
			}

		}

	}

	@Test
	public void jinxinzhong_odds_fresh() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		String where = "where match_status between 2 and 7 and match_time between unix_timestamp(date_sub(now(), interval 1 day)) and unix_timestamp(now() )  order by match_time desc";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV2(where);
	}

	@Test
	public void test__Football_lostMatchFlush() throws Exception {
		String sql = "select * from foot_match_needadd_v";
		MybatisMapper MybatisMapper1 = SqlSessionFactory1.openSession(true).getMapper(MybatisMapper.class);
		List<LinkedHashMap> li = MybatisMapper1.querySql(sql);
		for (LinkedHashMap linkedHashMap : li) {
			try {
				String matchID = linkedHashMap.get("match_id").toString();
				SyncFootballLiveMatchdetailliveImp.insertMatchData(matchID);
			} catch (Exception e) {
				logger.error(e);
			}

		}

		System.out.println("f");

	}

	@Test
	public void test__Football_teamFlush() throws Exception {
		SyncFootballBasicUpdateprofileServiImp syncFootballBasicUpdateprofileServiImp = new SyncFootballBasicUpdateprofileServiImp();
		syncFootballBasicUpdateprofileServiImp.session = SqlSessionFactory1.openSession(true);
		syncFootballBasicUpdateprofileServiImp.Football_Basic_Update_profile();
		// footballMatchLive();
		System.out.println("f");

	}

	// Football.Live.Match_live jinxinzhong
	@Test
	public void matchStatScoreFlush_LiveMatch_live() throws Exception {
		footballMatchLive();
		System.out.println("f");

	}
	// Football.Live.Match_live
	@Test
	public void jinxz__Football_matchStatScoreFlush() throws Exception {
		footballMatchLive();
		System.out.println("f");

	}
	// Football.Live.Match_live
	@Test
	public void test__Football_matchStatScoreFlush() throws Exception {
		footballMatchLive();
		System.out.println("f");

	}

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(unitRecentlyV2.class);

	/**
	 * 返回实时变动的即时数据信息,无即时数据变动的比赛不返回
	 * 
	 * @return
	 */

	@SuppressWarnings("all")
	public boolean footballMatchLive() {

		SqlSession openSession = SqlSessionFactory1.openSession(true);
		MybatisMapper MybatisMapper1 = openSession.getMapper(MybatisMapper.class);
		String url = "http://www.skrsport.live/?service=Football.Live.Match_live&username=sport_api&secret=0gclkqzK";
		String result = HttpRequestUtil.doGet(url);
		// 解析返回结果
		JSONObject resultObj = JSONObject.parseObject(result);
		if (resultObj.getLong("ret") == (200)) {
			JSONArray dataArr = resultObj.getJSONArray("data");
			List<FootballScoreVO> scoreList = new ArrayList();
			dataArr.forEach(x -> {

				try {
					JSONArray data = JSONObject.parseArray(x.toString());
					List<Integer> homeList = JSONObject.parseArray(data.getString(2), Integer.class);
					List<Integer> awayList = JSONObject.parseArray(data.getString(3), Integer.class);
					// 获取球队ID
					Long matchId = data.getLong(0);
					String match_status = data.get(1).toString();

					updateMatchStateRealtime(MybatisMapper1, matchId, match_status);

					// 主队比分
					FootballScoreVO homeScoreVO = new FootballScoreVO();
					homeScoreVO.setMatchId(matchId);
					homeScoreVO.setTeamId(null);
					homeScoreVO.setTeamType(1);
					homeScoreVO.setRegularTimeScore(homeList.get(0));
					homeScoreVO.setHalfCourtScore(homeList.get(1));
					homeScoreVO.setRedCard(homeList.get(2));
					homeScoreVO.setYellowCard(homeList.get(3));
					homeScoreVO.setCornerKick(homeList.get(4));
					homeScoreVO.setOvertimeScore(homeList.get(5));
					homeScoreVO.setPointScore(homeList.get(6));
					homeScoreVO.setRemark(data.getString(5));

					// 客队比分
					FootballScoreVO awayScoreVO = new FootballScoreVO();
					awayScoreVO.setMatchId(data.getLong(0));
					awayScoreVO.setTeamId(null);
					awayScoreVO.setTeamType(2);
					awayScoreVO.setRegularTimeScore(awayList.get(0));
					awayScoreVO.setHalfCourtScore(awayList.get(1));
					awayScoreVO.setRedCard(awayList.get(2));
					awayScoreVO.setYellowCard(awayList.get(3));
					awayScoreVO.setCornerKick(awayList.get(4));
					awayScoreVO.setOvertimeScore(awayList.get(5));
					awayScoreVO.setPointScore(awayList.get(6));

					// 获取球队ID
					String sql = "select * from football_match_t where id=" + matchId.toString();
					LinkedHashMap mat = MybatisMapper1.querySql(sql).get(0);

					homeScoreVO.setTeamId(Long.parseLong(mat.get("home_id").toString()));

					awayScoreVO.setTeamId(Long.parseLong(mat.get("away_id").toString()));

					try {
						updateScoreVo(openSession, homeScoreVO);
					} catch (Exception e) {
						logger.error(e);
					}

					updateScoreVo(openSession, awayScoreVO);

				} catch (Exception e) {
					logger.error(e);
				}

			});
			// footballScoreService.updateFootballScore(scoreList);
			// return true;
		}
		return false;

	}

	private void updateMatchStateRealtime(MybatisMapper MybatisMapper1, Long matchId, String match_status) {
		try {
			String s = "insert foot_match_needadd set match_id=" + matchId;
			logger.info(s);
			System.out.println("==>update football_match_t:" + MybatisMapper1.update(s));
			;
		} catch (Exception e) {
			logger.error(e);
		}
		try {
			String upStat = "update football_match_t set match_status=" + match_status + " where id=" + matchId;
			logger.info(upStat);
			System.out.println("==>update football_match_t:" + MybatisMapper1.update(upStat));
			;
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void updateScoreVo(SqlSession openSession, FootballScoreVO homeScoreVO) {

		Map m = MapUtil.fromBean(homeScoreVO);
		Map m2HomeScore = MapUtil.camel2lowerUnderline(m);
		SyncFootballLiveMatchdetailliveImp.score_insert_safe(m2HomeScore, openSession);
	}

	/**
	 * realtime api tlive
	 * 
	 * @throws Exception
	 */
	@Test
	public void test__Football_tlive_Pastdays() throws Exception {
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;

		dtimp.Football_Live_Match_detail_live();
		System.out.println("f");

	}

	/**
	 * select * from foot_match_exV3 where id not in (select match_id from
	 * football_odds_t) and match_time between unix_timestamp(date_sub(now(),
	 * interval 8 day)) and unix_timestamp(now() ) order by match_time desc
	 * 
	 * @throws Exception
	 */
	@Test
	public void z_test_odds_past8_notInOdds() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		String sql = "select * from mat_today_notOdds";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV4(sql);
	}

	@Test
	public void odds_mat_today_notOdds_in8hr() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		String sql = "select * from mat_today_notOdds_in8hr";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV4(sql);
	}

	@Test
	public void z_test_odds_today_notInOdds() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		String sql = "select * from mat_today_notOdds";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV4(sql);
	}

	@SuppressWarnings("unchecked")
	@Test

	public void Football_Live_Match_list_statue_cellList() throws Exception {
		String dt = "20200523";
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		for (List MatchItem : result1) {

			// param.put("home_id", ((List) MatchItem.get(5)).get(0));
			try {

				java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
//设置数的小数部分所允许的最大位数，避免小数位被舍掉
				NF.setMaximumFractionDigits(0);
//设置数的小数部分所允许的最小位数，避免小数位有多余的0
				NF.setMinimumFractionDigits(0);
//去掉科学计数法显示，避免显示为111,111,111,111
				NF.setGroupingUsed(false);
//System.out.println(NF.format(a));

				LinkedHashMap param = Maps.newLinkedHashMap();
				param.put("id", MatchItem.get(0));
				param.put("match_id", MatchItem.get(0));
				int stt = ((Double) MatchItem.get(2)).intValue();
				if (stt >= 2 && stt <= 7) {
					param.put("match_status", NF.format(MatchItem.get(2)));
					param.put("match_time", NF.format(MatchItem.get(3)));
					param.put("tee_time", NF.format(MatchItem.get(4)));
//				param.put("home_id", ((List) MatchItem.get(5)).get(0));
//				param.put("away_id", ((List) MatchItem.get(6)).get(0));
//				param.put("match_detail", ((List) MatchItem.get(7)).get(0));
//				param.put("which_round", ((List) MatchItem.get(7)).get(1));
//				param.put("neutral_site", ((List) MatchItem.get(7)).get(2));

					logger.info(param);
				}

			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	// Football_Live_Match_list_statue
	@Test
	public void Live_Match_list_statFlush_today() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date()); // SqlSessionFactory1
		SyncFootballLiveMatchlistServiceImp syncFootballLiveMatchlistServiceImp = new SyncFootballLiveMatchlistServiceImp();
		syncFootballLiveMatchlistServiceImp.session = SqlSessionFactory1.openSession(true);
		syncFootballLiveMatchlistServiceImp.Football_Live_Match_list_statue(dt);
	}

	@Test // /sport/queryPageTrad/cmsdb/league 25secs sotimer is 2min
	public void Live_Match_list_stat_score_today() throws Exception {
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;
		// c.FootballMatchService("Football.Live.Match_list");
		lsimp.Football_Live_Match_list_Pastdays(1);
		// dtimp.Football_Live_Match_detail_live();

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test // /sport/queryPageTrad/cmsdb/league
	public void test__Football_score_today() throws Exception {
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;
		// c.FootballMatchService("Football.Live.Match_list");
		lsimp.Football_Live_Match_list_Pastdays(2);
		// dtimp.Football_Live_Match_detail_live();
		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	/**
	 * socre and match list
	 * 
	 * @throws Exception
	 */
	@Test // /sport/queryPageTrad/cmsdb/league
	public void test__Football_score_Pastdays() throws Exception {
		FootballMatchController c = new FootballMatchController();
		// ApiControllerTrad c = new ApiControllerTrad();
		SyncFootballLiveMatchlistServiceImp lsimp = new SyncFootballLiveMatchlistServiceImp();
		SyncFootballLiveMatchdetailliveImp dtimp = new SyncFootballLiveMatchdetailliveImp();
		SyncFootballTeamlistServiceImp teamSvr = new SyncFootballTeamlistServiceImp();
		lsimp.session = MybatisUtil.getConn();
		dtimp.session = MybatisUtil.getConn();
		teamSvr.session = MybatisUtil.getConn();
		c.SyncFootballLiveMatchlistServiceImp1 = lsimp;
		c.SyncFootballLiveMatchdetailliveImp1 = dtimp;
		c.SyncFootballTeamlistServiceImp1 = teamSvr;
		// c.FootballMatchService("Football.Live.Match_list");
		lsimp.Football_Live_Match_list_Pastdays(8);
		// dtimp.Football_Live_Match_detail_live();
		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void odds_today() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		// select * from foot_match_exV3 where match_time between
		// unix_timestamp(date_sub(now(), interval 1 day)) and unix_timestamp(now() )
		// order by match_time desc
		String where = "where match_time between unix_timestamp(date_sub(now(), interval 1 day)) and unix_timestamp(now() ) order by match_time desc";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV2(where);
	}

	// all
	@Test
	public void z_test_odds_past8() throws Exception {
		// all(today-tomory) ing... saichen saiguo all
		String where = "where match_time between unix_timestamp(date_sub(now(), interval 8 day)) and unix_timestamp(now() ) order by match_time desc";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV2(where);
	}

	@Test
	public void z_test_insertFootballNumberOddsData_bymatchId() throws Exception {
		SyncFootballNumberOddsHistoryServiceImpl syncFootballNumberOddsHistoryServiceImpl = new SyncFootballNumberOddsHistoryServiceImpl();
		syncFootballNumberOddsHistoryServiceImpl.sqlSessionFactory = MybatisUtil.getSqlSessionFactoryRE();
		syncFootballNumberOddsHistoryServiceImpl.insertFootballNumberOddsData(3383969L);
		System.out.println("f");
	}

}
