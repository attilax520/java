package com.kok.sport.integration.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballLiveMatchdetaillive;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.Timeutil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.QlSpelUtil;
import com.kok.sport.utils.ql.sqlutil;

import cn.hutool.core.convert.Convert;
@Service
public class SyncFootballLiveMatchdetailliveImp implements SyncFootballLiveMatchdetaillive {
	
	
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	SqlSession session;
	
	@Override
	public   void Football_Live_Match_detail_live() throws Exception {
		JsonObject json =CaptchData. getJsonRzt("Football.Live.Match_detail_live");
		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']";
		List<Map> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		for (Map MatchItem : result1) {

			try {
				incidents_insert((List<Map>) MatchItem.get("incidents"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
			}

			if ("1" == "1")
				continue;
			try {
				processtliveList((List<Map>) MatchItem.get("tlive"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}

			// dbg

			// Runtime.getRuntime().exit(0);

			try {
				ScoreProceeesList((List<Map>) MatchItem.get("score"), session, MatchItem);
			} catch (Exception e) {
				logger.warn(e);
				// dbgExit(e);
			}
			// dbg
//			if("1"=="1")
//			continue;
			try {
			CaptchData.	statPrcsList((List<Map>) MatchItem.get("stats"), session, MatchItem);
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
	private   void incidents_insert(List<Map> list, SqlSession session, Map matchItem_root) {
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
				
			    MapUtil.ConvertFldValToInt(MatchItem,"home_score",0);
			    MapUtil.ConvertFldValToInt(MatchItem,"away_score",0);
			    
				
			    System.out.println(session.update("insert_into_football_team_t", MatchItem));
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

	private static int score_insert(Map param, SqlSession session) {

		try {
		//	 System.out.println();
			
			return session.update("insert_into_football_score_t", param);
		} catch (Exception e) {
			if ((e.getCause() instanceof SQLIntegrityConstraintViolationException))
				logger.warn(e);
			else
				throw e;
		}
		// m.id==teamid
		return 0;

	}
	
	
	/**
	 * CREATE TABLE `football_tlive_t` ( `id` bigint(19) NOT NULL COMMENT '主键id',
	 * `match_id` bigint(19) NOT NULL COMMENT '比赛id', `main` tinyint(3) NOT NULL
	 * COMMENT '是否重要事件,1-是,0-否', `data` varchar(200) NOT NULL COMMENT '直播数据',
	 * `position` int(10) NOT NULL COMMENT '事件发生方,0-中立 1,主队 2,客队', `type` tinyint(3)
	 * NOT NULL COMMENT '事件类型', `time` bigint(19) NOT NULL COMMENT '事件时间',
	 * `create_time` datetime NOT NULL COMMENT '创建时间', `delete_flag` char(1) NOT
	 * NULL COMMENT '是否删除(1.已删除0.未删除)', PRIMARY KEY (`id`)
	 * 
	 * @param list
	 * @param session
	 * @param matchItem_root
	 * @throws Exception
	 */
	private static void processtliveList(List<Map> list, SqlSession session, Map matchItem_root) throws Exception {
		// List<Map> li=MatchItem.get("stats");
//replace(UNIX_TIMESTAMP(now(6)),'.','')
		for (Map MatchItem : list) {
			try {
				logger.info("------processtliveList foreach");
				MatchItem.putAll(matchItem_root);
				MatchItem.put("time", Timeutil.toSecs(MatchItem.get("time").toString()));
				Map sqlpa = sqlutil.mysql_real_escape_string(MatchItem);
				System.out.println(session.update("insert_into_football_tlive_t", sqlpa));
				
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
		private static void ScoreProceeesList(List list, SqlSession session, Map matchItem) {

			Map param = Maps.newConcurrentMap();
//			param.put("id", MatchItem.get(0));
			param.putAll(matchItem);
			param.put("match_id", list.get(0));
			// param.put("match_id", list.get(1)); stt
			List scoreObj_judwi = (List) list.get(2);
			param.put("team_type", 1);
			parseScore(param, scoreObj_judwi);
			try {
				int r = score_insert(param, session);
			} catch (Exception e) {
				logger.warn(e);
			}

			// proicess kadwi
			List scoeeKadwiObj = (List) list.get(3);
			param.put("team_type", 2);
			parseScore(param, scoeeKadwiObj);

			try {
				int r = score_insert(param, session);
			} catch (Exception e) {
				logger.warn(e);
			}

		}

}
