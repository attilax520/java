package com.kok.sport.integration.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballLiveMatchlistService;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.QlSpelUtil;

@Service
public class SyncFootballLiveMatchlistServiceImp implements SyncFootballLiveMatchlistService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	public SqlSession session;
	
	
	public void Football_Live_Match_list_Pastdays(int Pastdays) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// past 30天
		for (int i = 0; i < Pastdays; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			// System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - i);// 让日期加1
			String dt = sdf.format(calendar.getTimeInMillis());
			System.out.println("new date:" + dt);// 加1之后的日期Top

			try {
				Football_Live_Match_list(dt);
			} catch (Exception e) {
				logger.error(e);
			}
		}

	}

	@Override
	public void Football_Live_Match_list() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < 30; i++) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			// System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + i);// 让日期加1
			String dt = sdf.format(calendar.getTimeInMillis());
			System.out.println("new date:" + dt);// 加1之后的日期Top
			try {
				Football_Live_Match_list(dt);
			} catch (Exception e) {
				logger.error(e);
			}

		}

		// past 30天
		for (int i = 0; i < 30; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			// System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - i);// 让日期加1
			String dt = sdf.format(calendar.getTimeInMillis());
			System.out.println("new date:" + dt);// 加1之后的日期Top

			try {
				Football_Live_Match_list(dt);
			} catch (Exception e) {
				logger.error(e);
			}
		}

	}
	
	@SuppressWarnings("all")
	public void Football_Live_Match_list_statue(String dt) throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		for (List MatchItem : result1) {
			
		//	param.put("home_id", ((List) MatchItem.get(5)).get(0));
			try {
				Map param = Maps.newConcurrentMap();
				param.put("id", MatchItem.get(0));	param.put("match_id", MatchItem.get(0));
				param.put("match_event_id", MatchItem.get(1));
				param.put("match_status", MatchItem.get(2));
				param.put("match_time", MatchItem.get(3));
				param.put("tee_time", MatchItem.get(4));
				param.put("home_id", ((List) MatchItem.get(5)).get(0));
				param.put("away_id", ((List) MatchItem.get(6)).get(0));
				List list_matchDetailObj_listmode = (List) MatchItem.get(7);
				param.put("match_detail", list_matchDetailObj_listmode.get(0));
				param.put("which_round", list_matchDetailObj_listmode.get(1));
				param.put("neutral_site", list_matchDetailObj_listmode.get(2));
				
 
				int r = session.insert("com.kok.sport.dao.FootballMatchDao.into_football_match_t", param);
				logger.info(r);
 
			} catch (Exception e) {
//				if("a".length()>0)
//				throw e;
				logger.error(e);
			}

			// Runtime.getRuntime().exit(0);
		}
		// List MatchItem = (List) result1.get(0);

	}

	private void Football_Live_Match_list(String dt) throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		for (List MatchItem : result1) {
			
		//	param.put("home_id", ((List) MatchItem.get(5)).get(0));
			try {
				Map param = Maps.newConcurrentMap();
				param.put("id", MatchItem.get(0));	param.put("match_id", MatchItem.get(0));
				param.put("match_event_id", MatchItem.get(1));
				param.put("match_status", MatchItem.get(2));
				param.put("match_time", MatchItem.get(3));
				param.put("tee_time", MatchItem.get(4));
				param.put("home_id", ((List) MatchItem.get(5)).get(0));
				param.put("away_id", ((List) MatchItem.get(6)).get(0));
				param.put("match_detail", ((List) MatchItem.get(7)).get(0));
				param.put("which_round", ((List) MatchItem.get(7)).get(1));
				param.put("neutral_site", ((List) MatchItem.get(7)).get(2));
				
				
			
				int teamtype=1;
				 socreInsert(  param ,  MatchItem,  teamtype ) ;
				 socreInsert(  param ,  MatchItem,  2 ) ;

				// JsonArray competitons =json.getAsJsonObject("data").
				// getAsJsonObject("delete").getAsJsonArray("competitons");
				// com.kok.sport.dao.FootballMatchDao.into_football_match_t
				int r = session.insert("com.kok.sport.dao.FootballMatchDao.into_football_match_t", param);
				logger.info(r);
//				 hometeamInsert(  param ,  MatchItem,  teamtype ) ;
//				 hometeamInsert(  param ,  MatchItem,  2 ) ;
				// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
				// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

//				MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);
//
//				System.out.println(mapper.updateV2(sql));
			} catch (Exception e) {
//				if("a".length()>0)
//				throw e;
				logger.error(e);
			}

			// Runtime.getRuntime().exit(0);
		}
		// List MatchItem = (List) result1.get(0);

	}
	
	@SuppressWarnings("all")
	public   void socreInsert(Map param2 ,List MatchItem,int teamtype ) {
		Map param=MapUtil.cloneSafe(param2);
		try {
			//home team socre
			//,#{regular_time_score},#{half_court_score},#{red_card},#{yellow_card},
		//	#{[corner_kick]},#{overtime_score},#{point_score},#{match_id},'0',#{team_type},
			param.put("team_type",   teamtype);
		
			List list_objTeam = (List) MatchItem.get(5);
			if(teamtype==2)
				list_objTeam = (List) MatchItem.get(6);
			param.put("team_id", list_objTeam.get(0));
			if(list_objTeam.get(0)==null)
				System.out.println("dbg");
			param.put("regular_time_score", list_objTeam.get(2));
			param.put("half_court_score", list_objTeam.get(3));
			
			param.put("red_card", list_objTeam.get(4));
			param.put("yellow_card", list_objTeam.get(5));
			param.put("corner_kick", list_objTeam.get(6));
			param.put("half_court_score", list_objTeam.get(3));
			
	int i=		SyncFootballLiveMatchdetailliveImp.score_insert(param, session);
	System.out.println(i);
		} catch (Exception e) {
		logger.error("",e);
		}
		
	}

}
