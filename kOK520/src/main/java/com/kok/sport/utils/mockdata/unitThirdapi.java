package com.kok.sport.utils.mockdata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kok.sport.controller.FootballMatchController;
import com.kok.sport.controller.FootballOddsController;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.integration.impl.SyncFootballLiveMatchlistServiceImp;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.integration.impl.SyncFootballTeamlistServiceImp;
import com.kok.sport.service.impl.FootballOddsServiceImpl;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisUtil;

//   new com.kok.sport.utils.mockdata.unitThirdapi().test_Football_Live_Match_list_SaichenSaiguoAll()
public class unitThirdapi {
	
	
	// all 
	@Test
	public void test_odds_future30() throws Exception {
		//all(today-tomory) ing... saichen  saiguo    all
		String where="where match_time between unix_timestamp(date_sub(now(), interval 1 day)) and unix_timestamp( date_add(now(), interval 2 day) ) order by match_time desc";
		FootballOddsController c=new FootballOddsController();
		c.insertFootballNumberOddsDataV2(where);
	}
	
	@Test
	public void test_odds_saichen() throws Exception {
		// saichen ////all(today-tomory) ing... saichen  saiguo    all
		String where="where match_time between unix_timestamp(date_sub(now(), interval 0 day)) and unix_timestamp( date_add(now(), interval 8 day) ) order by match_time desc";
		FootballOddsController c=new FootballOddsController();
		c.insertFootballNumberOddsDataV2(where);
	}
	

	@Test // /sport/queryPageTrad/cmsdb/league
	public void test_Football_Live_Match_list_SaichenSaiguoAll() throws Exception {
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
	 	c.FootballMatchService("Football.Live.Match_list");
	 

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}
	
	@Test // /sport/queryPageTrad/cmsdb/league
	public void test_Football_Live_Match_list_Pastdays() throws Exception {
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
	//	c.FootballMatchService("Football.Live.Match_list");
		lsimp.Football_Live_Match_list_Pastdays(3);

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void oddsFxi() throws Exception {
		List<String> matchids = matchids();
		for (String mid : matchids) {
			try {
				new SyncFootballNumberOddsHistoryServiceImpl().insertFootballNumberOddsData(Long.parseLong(mid));
			} catch (Exception e) {
				logger2.error("", e);
			}
			
		}
	}
	static Logger logger2 = LoggerFactory.getLogger(LogUtil.class);
	@Test
	public void testOdds() throws Exception {
		List<String> matchids = matchids();
		for (String mid : matchids) {
			try {
				String url = "http://112.121.163.125:9601/sport/footballodds/insertFootballNumberOddsData?Id=" + mid;
				logger2.info(url);
				String t = Httpcliet.testGet(url);
				logger2.info(t);
			} catch (Exception e) {
				logger2.error("", e);
			}

		}
	}

	public static List<String> matchids() throws Exception {
		SqlSession session = MybatisUtil.getConn();
		List<Map> sList = MybatisUtil.executeSql(
				"select * from football_match_t where match_time between unix_timestamp(date_sub(now(), interval 8 day)) and unix_timestamp( now() ) order by match_time desc");
		List<String> li_rzt_matchids = sList.stream().map(s -> s.get("id").toString()).collect(Collectors.toList());
		return li_rzt_matchids;
	}

	@Test
	public void testSyncFootballNumberOddsHistoryServiceImpl() throws Exception {
		List<String> matchids = matchids();
		for (String string : matchids) {

		}
		SyncFootballNumberOddsHistoryServiceImpl c = new SyncFootballNumberOddsHistoryServiceImpl();

		FootballOddsServiceImpl fos = new FootballOddsServiceImpl();
		c.footballOddsService = fos;
		c.insertFootballNumberOddsData(3381595L);
	}
	
	@Test // /sport/queryPageTrad/cmsdb/league
	public void TeamFlush() throws Exception {
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
		c.FootballMatchService("Football.Basic.Team_list");
		//c.FootballMatchService("Football.Live.Match_detail_live");

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	// Football.Live.Match_list
	// Football.Basic.Team_list
	// Football.Basic.Update_profile
	// Football.Europe.Odds_europe_history
	@Test // /sport/queryPageTrad/cmsdb/league
	public void testFootballMatchController() throws Exception {
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
		c.FootballMatchService("Football.Live.Match_list");
		c.FootballMatchService("Football.Live.Match_detail_live");

		System.out.println("f");
		// System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

}
