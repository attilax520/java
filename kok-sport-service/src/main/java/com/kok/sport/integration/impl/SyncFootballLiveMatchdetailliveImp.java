package com.kok.sport.integration.impl;

import java.net.URISyntaxException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.kok.sport.integration.SyncFootballLiveMatchdetaillive;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.Timeutil;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;
import com.kok.sport.utils.mockdata.WssTest;
import com.kok.sport.utils.ql.QlSpelUtil;
import com.kok.sport.utils.ql.sqlutil;

import cn.hutool.core.convert.Convert;

@Service
@SuppressWarnings("all")
public class SyncFootballLiveMatchdetailliveImp implements SyncFootballLiveMatchdetaillive {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	public SqlSession session;

	public void jincyoTonji_Football_Live_Match_detail_live() throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_detail_live");

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']";
		Object query = QlSpelUtil.query(m, mts_exp);
		if (query instanceof LinkedTreeMap) {
			logger.warn("root data mayhbe edmpty: " + json);
			return;
		}

		List<Map> result1_bisai_list = (List) query;

		SqlSession session = MybatisUtil.getConn();
		for (Map MatchItem_bisai : result1_bisai_list) {

			// updateMatchStateRealtime(session.getMapper(MybatisMapper.class),matchid)

			// updateMatchStateRealtime(session.getMapper(MybatisMapper.class),)

			try {
				List tliveList = (List) MatchItem_bisai.get("tlive");
				tonzhi_processtliveList(tliveList, session, MatchItem_bisai);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}

		}
	}
 
	public void Football_Live_Match_detail_live_RealtimeScore() throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_detail_live");

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']";
		Object query = QlSpelUtil.query(m, mts_exp);
		if (query instanceof LinkedTreeMap) {
			logger.warn("root data mayhbe edmpty: " + json);
			return;
		}

		List<Map> result1 = (List) query;

		SqlSession session = MybatisUtil.getConn();
		for (Map MatchItem : result1) {

		 

			try {
				// (List<Map>)
				ScoreProceeesList(MatchItem.get("score"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}

			 
		}
	}

	@Override
	public void Football_Live_Match_detail_live() throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_detail_live");

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']";
		Object query = QlSpelUtil.query(m, mts_exp);
		if (query instanceof LinkedTreeMap) {
			logger.warn("root data mayhbe edmpty: " + json);
			return;
		}

		List<Map> result1 = (List) query;

		SqlSession session = MybatisUtil.getConn();
		for (Map MatchItem : result1) {

			// updateMatchStateRealtime(session.getMapper(MybatisMapper.class),matchid)

			// updateMatchStateRealtime(session.getMapper(MybatisMapper.class),)

			try {
				List tliveList = (List) MatchItem.get("tlive");
				processtliveList(tliveList, session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}
//			if ("1" == "1")
//			continue;

			try {
				// (List<Map>)
				ScoreProceeesList(MatchItem.get("score"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}

			try {
				incidents_insert((List<Map>) MatchItem.get("incidents"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
			}

			// dbg

			// Runtime.getRuntime().exit(0);

			// dbg
//			if("1"=="1")
//			continue;
			try {
				CaptchData.statPrcsList((List<Map>) MatchItem.get("stats"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}

//			try {
//				Map param = Maps.newConcurrentMap();
//				param.put("id", MatchItem.get(0));
//				param.put("match_event_id", MatchItem.get(1));
//				param.put("match_status", MatchItem.get(2));
//				param.put("match_time", MatchItem.get(3));
//				param.put("tee_time", MatchItem.get(4));
//				param.put("home_id", ((List) MatchItem.get(5)).get(0));
//				param.put("away_id", ((List) MatchItem.get(6)).get(0));
//				param.put("match_detail", ((List) MatchItem.get(7)).get(0));
//				param.put("which_round", ((List) MatchItem.get(7)).get(1));
//				param.put("neutral_site", ((List) MatchItem.get(7)).get(2));
//
//				// JsonArray competitons =json.getAsJsonObject("data").
//				// getAsJsonObject("delete").getAsJsonArray("competitons");
//				String sql = "insert  into football_match_t(  id,match_event_id, match_status ,match_time,tee_time,"
//						+ "home_id,away_id,which_round,neutral_site,create_time,delete_flag,match_detail,"
//						+ "animation,intelligence,squad,video)values("
//						+ "#{[id]},'#{[match_event_id]}','#{[match_status]}','#{[match_time]}','#{[tee_time]}',"
//						+ "#{[home_id]},#{[away_id]},#{[which_round]},#{[neutral_site]},now(),0, '#{[match_time]}',"
//						+ "0,0,0,0)";
//				// sql = processVars(sql, json.getAsJsonObject("data") );
//				sql = QlSpelUtil.parse(sql, param);
//				logger.info(sql);
//
//				// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
//				// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]
//
//				MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);
//
//				System.out.println(mapper.update(sql));
//			} catch (Exception e) {
//				logger.error(e);
//			}

			// Runtime.getRuntime().exit(0);
		}
	}

	/**
	 * CREATE TABLE `football_incident_t` ( `id` bigint(19) NOT NULL COMMENT '主键id',
	 * `match_id` bigint(19) DEFAULT NULL COMMENT '比赛id', `type` tinyint(3) NOT NULL
	 * COMMENT '事件类型，详见状态码->足球技术统计', `position` int(10) NOT NULL COMMENT '事件发生方,0-中立
	 * 1,主队 2,客队', `time` bigint(19) DEFAULT NULL COMMENT '时间(分钟)', `player_id`
	 * bigint(19) DEFAULT NULL COMMENT '事件相关球员id,可能为空', `player_name` varchar(100)
	 * DEFAULT NULL COMMENT '事件相关球员名称,可能为空', `assist1_id` bigint(19) DEFAULT NULL
	 * COMMENT '进球时,助攻球员1 id,可能为空', `assist1_name` varchar(100) DEFAULT NULL COMMENT
	 * '进球时,助攻球员1名称,可能为空', `in_player_id` bigint(19) DEFAULT NULL COMMENT
	 * '换人时,换上球员id,可能为空', `in_player_name` varchar(100) DEFAULT NULL COMMENT
	 * '换人时,换上球员名称,可能为空', `out_player_id` bigint(19) DEFAULT NULL COMMENT
	 * '换人时,换下球员id ,可能为空', `out_player_name` varchar(100) DEFAULT NULL COMMENT
	 * '换人时,换下球员名称,可能为空', `home_score` int(10) DEFAULT NULL COMMENT '进球时,主队比分,可能没有',
	 * `away_score` int(10) DEFAULT NULL COMMENT '进球时,客队比分,可能没有', `delete_flag`
	 * char(1) NOT NULL COMMENT '是否删除(1.已删除0.未删除)', `create_time` datetime NOT NULL
	 * COMMENT '创建时间', PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
	 * COMMENT='比赛发生事件表';
	 * 
	 * @param list
	 * @param session
	 * @param matchItem_root
	 */
	private void incidents_insert(List<Map> list, SqlSession session, Map matchItem_root) {
		if (list == null) {
			logger.info("list==null");
			return;
		}
		for (Map MatchItem : list) {
			try {
				logger.info("------processtliveList foreach");
				MatchItem.putAll(matchItem_root);
//				MatchItem.put("time", Timeutil.toSecs(MatchItem.get("time").toString()));
//				Map sqlpa = sqlutil.mysql_real_escape_string(MatchItem);
				MatchItem.put("player_id", Convert.toInt(MatchItem.get("player_id"), 0));

				MapUtil.ConvertFldValToInt(MatchItem, "home_score", 0);
				MapUtil.ConvertFldValToInt(MatchItem, "away_score", 0);

				// System.out.println(session.update("insert_into_football_team_t", MatchItem));
			} catch (Exception e2) {
				logger.warn(JsonGsonUtil.toStrFromObj(e2));
				logger.error(e2);
				// continue;
			} catch (Throwable e) {
				logger.error(e);
				// dbgExit(e);
			}
		}

	}

	private static void parseScore(Map param, List scoreObj) {
		param.put("regular_time_score", scoreObj.get(0));
		param.put("half_court_score", scoreObj.get(1));

		param.put("red_card", scoreObj.get(2));
		param.put("yellow_card", scoreObj.get(3));

		param.put("corner_kick", scoreObj.get(4));
		param.put("overtime_score", scoreObj.get(5));
		param.put("point_score", scoreObj.get(6));
		// param.put("id", scoreObj.get(6));

	}

	public static int score_insert_safe(Map param, SqlSession session) {

		try {
			// System.out.println();
			// /kok-sport/kok-sport-service/src/main/resources/mapper/FootballLiveMatchdetailliveDao.xml
			return session.update("insert_into_football_score_t", param);
		} catch (Exception e) {

			logger.warn(e);

		}
		// m.id==teamid
		return 0;

	}

	public static void updateMatchStateRealtime(MybatisMapper MybatisMapper1, Long matchId, String match_status) {
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

//	public static int score_insert(Map param, SqlSession session) {
//
//		try {
//			// System.out.println();
//			// /kok-sport/kok-sport-service/src/main/resources/mapper/FootballLiveMatchdetailliveDao.xml
//			return session.update("insert_into_football_score_t", param);
//		} catch (Exception e) {
//			if ((e.getCause() instanceof SQLIntegrityConstraintViolationException))
//				logger.warn(e);
//			else
//				throw e;
//		}
//		// m.id==teamid
//		return 0;
//
//	}

	/**
	 * CREATE TABLE `football_tlive_t` ( `id` bigint(19) NOT NULL COMMENT '主键id',
	 * `match_id` bigint(19) NOT NULL COMMENT '比赛id', `main` tinyint(3) NOT NULL
	 * COMMENT '是否重要事件,1-是,0-否', `data` varchar(200) NOT NULL COMMENT '直播数据',
	 * `position` int(10) NOT NULL COMMENT '事件发生方,0-中立 1,主队 2,客队', `type` tinyint(3)
	 * NOT NULL COMMENT '事件类型', `time` bigint(19) NOT NULL COMMENT '事件时间',
	 * `create_time` datetime NOT NULL COMMENT '创建时间', `delete_flag` char(1) NOT
	 * NULL COMMENT '是否删除(1.已删除0.未删除)', PRIMARY KEY (`id`)
	 * 
	 * @param tlive_list
	 * @param session
	 * @param matchItem_root
	 * @throws Exception
	 */
	private static void processtliveList(List<Map> tlive_list, SqlSession session, Map matchItem_root)
			throws Exception {
		// List<Map> li=MatchItem.get("stats");
//replace(UNIX_TIMESTAMP(now(6)),'.','')
		for (Map MatchItem : tlive_list) {
			try {
				try {
					logger.info("------processtliveList foreach");
					MatchItem.putAll(matchItem_root);
					MatchItem.put("time", Timeutil.toSecs(MatchItem.get("time").toString(), 0));
					sendTongzhi(MatchItem, session);
					Map sqlpa = sqlutil.mysql_real_escape_string(MatchItem);
					System.out.println(session.update("insert_into_football_tlive_t", sqlpa));

				} catch (Exception e2) {
					if (e2.getCause() instanceof SQLIntegrityConstraintViolationException) {
						// 2723955

						Object matchID = MatchItem.get("id");
						insertMatchData(matchID);

						// retry
						MatchItem.putAll(matchItem_root);
						MatchItem.put("time", Timeutil.toSecs(MatchItem.get("time").toString(), 0));
						Map sqlpa = sqlutil.mysql_real_escape_string(MatchItem);
						System.out.println(session.update("insert_into_football_tlive_t", sqlpa));

					} else {
						JSON.toJSONString(e2, true);
						logger.warn(JsonGsonUtil.toStrFromObj(e2));
					}
				}
			}
			// continue;
			catch (Throwable e) {
				logger.error(e);
				// dbgExit(e);
			}
		}
	}

	private static void tonzhi_processtliveList(List<Map> tlive_list, SqlSession session, Map matchItem_root)
			throws Exception {
		// List<Map> li=MatchItem.get("stats");
//replace(UNIX_TIMESTAMP(now(6)),'.','')
		for (Map MatchItem : tlive_list) {
			try {

				logger.info("------processtliveList foreach");
				MatchItem.putAll(matchItem_root);  //set bisai id
				MatchItem.put("time", Timeutil.toSecs(MatchItem.get("time").toString(), 0));
				sendTongzhi(MatchItem, session);

			}
			// continue;
			catch (Throwable e) {
				logger.error(e);
				// dbgExit(e);
			}
		}
	}

	public static void sendTongzhi(Map matchItem, SqlSession session2) {
		LinkedHashMap mMsg = MapUtil.cloneSafe(matchItem);
		mMsg.remove("score");
		mMsg.remove("tlive");
		mMsg.remove("stats");
		mMsg.remove("incidents");
		mMsg.put("time_api", mMsg.get("time"));
		logger.info("sendTongzhi-->mMsg:");
		logger.info(mMsg);
		//
		// Maps.newLinkedHashMap();
		try {
			Double mid = Double.parseDouble(mMsg.get("id").toString());
			String recSign=JSON.toJSONString(mMsg);
					
//					mid.intValue()+mMsg.get("data").toString()+mMsg.get("time").toString()+ mMsg.get("position").toString()+
//					mMsg.get("type").toString();
			 noExistSetValChk(recSign,session2);
			 
			// String matchid= mid.intValue();o

				String sql = "select * from match_ex_team where  id=" + mid.intValue();
			logger.info(sql);
			LinkedHashMap rs = MybatisUtil.querySingleMap(sql,session2);
			// List<LinkedHashMap> li MybatisUtil.getMybatisMapper().querySqlSingle(sql);
			// CaptchData.sqlSessionFactory
		//	mMsg.putAll(rs);
			rs.putAll(mMsg);
			  if( rs.get("home_score")!=null)
			  {
				  rs.put("zhudiu_bifen",rs.get("home_score"));
				  rs.put("zhudui_bifen",rs.get("home_score"));
			  }
			  
			  if( rs.get("away_score")!=null)
				  rs.put("kedui_bifen",rs.get("away_score"));
			
			logger.info(JSON.toJSON(rs));
			Map m = Maps.newLinkedHashMap();
			m.put("method", "testsend");
			m.put("msg", rs);
			logger.info("==>WssTest.sendMsgClose:");
			logger.info(JSON.toJSON(m));
			InsertSet(recSign,session2);
			WssTest.sendMsgClose(JSON.toJSONString(m), WssTest.hostip4jincyo);
		} catch (Exception e) {
			logger.error(e);
		}

	}
	
	public static void sendTongzhiDebug(Map matchItem, SqlSession session2) {
		LinkedHashMap mMsg = MapUtil.cloneSafe(matchItem);
		mMsg.remove("score");
		mMsg.remove("tlive");
		mMsg.remove("stats");
		mMsg.remove("incidents");
		mMsg.put("time_api", mMsg.get("time"));
		logger.info("sendTongzhi-->mMsg:");
		logger.info(mMsg);
		//
		// Maps.newLinkedHashMap();
		try {
			Double mid = Double.parseDouble(mMsg.get("id").toString());
			String recSign=JSON.toJSONString(mMsg);
					
//					mid.intValue()+mMsg.get("data").toString()+mMsg.get("time").toString()+ mMsg.get("position").toString()+
//					mMsg.get("type").toString();
			// noExistSetValChk(recSign,session2);
			 
			// String matchid= mid.intValue();o

			String sql = "select * from match_ex_team where  id=" + mid.intValue();
			logger.info(sql);
			LinkedHashMap rs = MybatisUtil.querySingleMap(sql,session2);
			// List<LinkedHashMap> li MybatisUtil.getMybatisMapper().querySqlSingle(sql);
			// CaptchData.sqlSessionFactory
		//	mMsg.putAll(rs);
			rs.putAll(mMsg);
			  if( rs.get("home_score")!=null)
			  {
				  rs.put("zhudiu_bifen",rs.get("home_score"));
				  rs.put("zhudui_bifen",rs.get("home_score"));
			  }
				
			  
			  if( rs.get("away_score")!=null)
				  rs.put("kedui_bifen",rs.get("away_score"));
			  
			  rs.put("kedui", Math.random());
			logger.info(JSON.toJSON(rs));
			Map m = Maps.newLinkedHashMap();
			m.put("method", "testsend");
			m.put("msg", rs);
			logger.info("==>WssTest.sendMsgClose:");
		//	InsertSet(recSign,session2);
			logger.info(m);
			WssTest.sendMsgClose(JSON.toJSONString(m), WssTest.hostip4jincyo);
			logger.info("==>WssTest.sendMsgClose send finish:");
		} catch (Exception e) {
			logger.error(e);
		}

	}

	private static void InsertSet(String recSign, SqlSession session2) {
		recSign=recSign.replaceAll("'", "''");
		String sql = "insert into   sys_sets set val='" + recSign+"'";
		logger.info(sql );
		logger.info("==>sqlret:"+ MybatisUtil.execSql(sql, session2));
		
	}

	private static boolean noExistSetValChk(String recSign, SqlSession session2) {
		LinkedHashMap rs;
		try {
			String sql = "select * from sys_sets where val='" + recSign+"'";
			logger.info(sql );
			  rs = MybatisUtil.querySingleMap(sql,session2);
		    //chk
		} catch (Exception e) {
			//not exist
			return true;
		}
		
		if(rs!=null)
		{
			//extist ex 
			throw new RuntimeException("exist val");
			//return  false;
		}
		return true;
			
	
	//	return false;
	}

	public static void insertMatchData(Object matchID) throws Exception {
		String url = "http://www.skrsport.live/?service=Football.Analysis.Match_detail&username=sport_api&secret=0gclkqzK&id="
				+ matchID;
		logger.info(url);
		String t = Httpcliet.testGet(url);

		Map m = (Map) JSON.parse(t);

		Map newobj = Maps.newLinkedHashMap();
		newobj.put("id", matchID);
		newobj.put("match_event_id", QlSpelUtil.query(m, "#root['data']['matchevent']['id']"));
		newobj.put("match_status", QlSpelUtil.query(m, "#root['data']['info']['statusid']"));
		newobj.put("match_time", QlSpelUtil.query(m, "#root['data']['info']['matchtime']"));
		newobj.put("tee_time", QlSpelUtil.query(m, "#root['data']['info']['realtime']"));
		newobj.put("home_id", QlSpelUtil.query(m, "#root['data']['home_team']['id']"));
		newobj.put("away_id", QlSpelUtil.query(m, "#root['data']['away_team']['id']"));
		newobj.put("$insert", "football_match_t");

		System.out.println(newobj);
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		System.out.println(MysqlInsertUtil.insertV2_faster(sqlSessionFactory, newobj,
				MybatisUtil.getMybatisMapper(sqlSessionFactory)));
		;
	}

	
	@Deprecated
	/**Football.Analysis.Match_detail
	 * drop table if exists football_score_t; CREATE TABLE football_score_t( id
	 * BIGINT(19) NOT NULL COMMENT '主键id' , match_id BIGINT(19) NOT NULL COMMENT
	 * '比赛id' , team_id BIGINT(19) NOT NULL COMMENT '球队id' , team_type TINYINT(3)
	 * NOT NULL COMMENT '1:主队1, 2:客队' , league_ranking INT(10) NOT NULL COMMENT
	 * '联赛排名，无排名为空' , regular_time_score INT(10) NOT NULL COMMENT '常规时间比分(含补时)' ,
	 * half_court_score INT(10) NOT NULL COMMENT '半场比分(中场前为0)' , score INT(10) NOT
	 * NULL COMMENT '比分' , red_card INT(10) NOT NULL COMMENT '红牌' , yellow_card
	 * INT(10) NOT NULL COMMENT '黄牌' , corner_kick INT(10) NOT NULL COMMENT
	 * '角球，-1表示没有角球数据' , overtime_score INT(10) COMMENT '加时比分(含常规时间比分),加时赛才有' ,
	 * point_score INT(10) COMMENT '点球大战比分(不含常规时间比分),点球大战才有' , remark VARCHAR(500)
	 * NOT NULL COMMENT '备注信息' , create_time DATETIME NOT NULL COMMENT '创建时间' ,
	 * delete_flag CHAR(1) NOT NULL COMMENT '是否删除(1.已删除0.未删除)' , PRIMARY KEY (id) )
	 * COMMENT = '足球比赛得分表';
	 */
	public static void insertMatchScoreData(Object matchID, @NotNull SqlSession openSession) throws Exception {
		String url = "http://www.skrsport.live/?service=Football.Analysis.Match_detail&username=sport_api&secret=0gclkqzK&id="
				+ matchID;
		logger.info(url);
		String t = Httpcliet.testGet(url);

		Map m = (Map) JSON.parse(t);

		Map newobj = Maps.newLinkedHashMap();
		newobj.put("id", matchID);

		newobj.put("match_event_id", QlSpelUtil.query(m, "#root['data']['matchevent']['id']"));
		newobj.put("match_status", QlSpelUtil.query(m, "#root['data']['info']['statusid']"));
		newobj.put("match_time", QlSpelUtil.query(m, "#root['data']['info']['matchtime']"));
		newobj.put("tee_time", QlSpelUtil.query(m, "#root['data']['info']['realtime']"));
		newobj.put("home_id", QlSpelUtil.query(m, "#root['data']['home_team']['id']"));
		newobj.put("away_id", QlSpelUtil.query(m, "#root['data']['away_team']['id']"));
		newobj.put("$insert", "football_match_t");

		try {
			Map newobj_zhudui = Maps.newLinkedHashMap();
			newobj_zhudui.put("match_id", matchID);
			newobj_zhudui.put("team_id", QlSpelUtil.query(m, "#root['data']['home_team']['id']"));
			newobj_zhudui.put("team_type", 1);
			newobj_zhudui.put("score", QlSpelUtil.query(m, "#root['data']['home_team']['score']"));
			newobj_zhudui.put("half_court_score", QlSpelUtil.query(m, "#root['data']['home_team']['half_score']"));
			// newobj.put("$insert", "football_match_t");
		//	SyncFootballLiveMatchdetailliveImp.score_insert_safe(newobj_zhudui, openSession);
		} catch (Exception e) {
			logger.error(e);
		}

		try {
			Map newobj_kedui = Maps.newLinkedHashMap();
			newobj_kedui.put("match_id", matchID);
			newobj_kedui.put("team_id", QlSpelUtil.query(m, "#root['data']['away_team']['id']"));
			newobj_kedui.put("team_type", 1);
			newobj_kedui.put("score", QlSpelUtil.query(m, "#root['data']['away_team']['score']"));
			newobj_kedui.put("half_court_score", QlSpelUtil.query(m, "#root['data']['away_team']['half_score']"));
		//	SyncFootballLiveMatchdetailliveImp.score_insert_safe(newobj_kedui, openSession);

		} catch (Exception e) {
			logger.error(e);
		}

		System.out.println(newobj);
		// SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
//		System.out.println(MysqlInsertUtil.insertV2_faster(sqlSessionFactory, newobj,MybatisUtil.getMybatisMapper(sqlSessionFactory)));
//		;
	}

	/**
	 * `id` bigint(19) NOT NULL COMMENT '主键id', `match_id` bigint(19) NOT NULL
	 * COMMENT '比赛id', `team_id` bigint(19) NOT NULL COMMENT '球队id', `team_type`
	 * tinyint(3) NOT NULL COMMENT '1:主队1, 2:客队', `league_ranking` int(10) NOT NULL
	 * COMMENT '联赛排名，无排名为空', `regular_time_score` int(10) NOT NULL COMMENT
	 * '常规时间比分(含补时)', `half_court_score` int(10) NOT NULL COMMENT '半场比分(中场前为0)',
	 * `score` int(10) NOT NULL COMMENT '比分', `red_card` int(10) NOT NULL COMMENT
	 * '红牌', `yellow_card` int(10) NOT NULL COMMENT '黄牌', `corner_kick` int(10) NOT
	 * NULL COMMENT '角球，-1表示没有角球数据', `overtime_score` int(10) DEFAULT NULL COMMENT
	 * '加时比分(含常规时间比分),加时赛才有', `point_score` int(10) DEFAULT NULL COMMENT
	 * '点球大战比分(不含常规时间比分),点球大战才有', `remark` varchar(500) NOT NULL COMMENT '备注信息',
	 * `create_time` datetime NOT NULL COMMENT '创建时间', `delete_flag` char(1) NOT
	 * NULL COMMENT '是否删除(1.已删除0.未删除)',
	 * 
	 * @param list
	 * @param session
	 * @param matchItem
	 */
	private static void ScoreProceeesList(Object ScoreObj, SqlSession session, Map matchItem) {
		List ScoreObj_listmode = (List) ScoreObj;
		Map param = Maps.newConcurrentMap();
//			param.put("id", MatchItem.get(0));
		param.putAll(matchItem);
		param.put("match_id", ScoreObj_listmode.get(0));
		// Long matchid=Long.parseLong(ScoreObj_listmode.get(0).toString());

		// param.put("match_id", list.get(1)); stt
		List scoreObj_judwi = (List) ScoreObj_listmode.get(2);
		param.put("team_type", 1);
		parseScore(param, scoreObj_judwi);
		try {
			int r = score_insert_safe(param, session);
		} catch (Exception e) {
			logger.warn(e);
		}

		// proicess kadwi
		List scoeeKadwiObj = (List) ScoreObj_listmode.get(3);
		param.put("team_type", 2);
		parseScore(param, scoeeKadwiObj);

		try {
			int r = score_insert_safe(param, session);
		} catch (Exception e) {
			logger.warn(e);
		}

	}

}
