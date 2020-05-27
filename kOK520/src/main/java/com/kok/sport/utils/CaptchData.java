package com.kok.sport.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.google.common.collect.Maps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.UniqueEx;
import com.kok.sport.utils.ql.QlSpelUtil;
//import com.kok.sport.utils.ql.QlSpelUtil;
import com.kok.sport.utils.ql.sqlutil;

import cn.hutool.core.convert.Convert;
 
import cn.hutool.db.sql.SqlUtil;

@SuppressWarnings("all")
public class CaptchData {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);
	public static MybatisMapper MybatisMapper1;
	public static SqlSessionFactory sqlSessionFactory;

	public static void main(String[] args) throws Exception {
		System.out.println(SqlUtils.stripSqlInjection("20'"));
//	 if("1"=="1")
//		throw new RuntimeException("a");
		// TODO Auto-generated method stub
		// Football_Team_list();
		// Football_Basic_Update_profile();
//		Map m=Maps.newConcurrentMap();
//		m.put("k1",111);m.put("k2", 222);
//		String t= new Gson().toJson(m);
//		JsonObject JsonObject1= new JsonParser().parse(t).getAsJsonObject();
//		Set<Entry<String, JsonElement>>  setE=	JsonObject1.entrySet();
//		 for (Entry<String, JsonElement> entry : setE) {
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		}
		// Football_Live_Match_list();
		// Football_Europe_Odds_europe_live();
		Football_Live_Match_detail_live();
	}
	
	//  where match_time between unix_timestamp(date_sub(now(), interval 8 day)) and unix_timestamp( now() ) order by match_time desc
	public static List<String> matchids(String where) throws Exception {
		SqlSession session = MybatisUtil.getConn();
		List<Map> sList = MybatisUtil.executeSql(
				"select * from football_match_t "+where);
		List<String> li_rzt_matchids = sList.stream().map(s -> s.get("id").toString()).collect(Collectors.toList());
		return li_rzt_matchids;
	}
	
	
//	public static List<String> matchids(String where) throws Exception {
//		SqlSession session = MybatisUtil.getConn();
//		List<Map> sList = MybatisUtil.executeSql(
//				"select * from football_match_t "+where);
//		List<String> li_rzt_matchids = sList.stream().map(s -> s.get("id").toString()).collect(Collectors.toList());
//		return li_rzt_matchids;
//	}

	/**
	 * Football.Live.Match_detail_live
	 * 
	 * @throws Exception
	 */
	@Deprecated
	private static void Football_Live_Match_detail_live() throws Exception {
		JsonObject json = getJsonRzt("Football.Live.Match_detail_live");
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
				statPrcsList((List<Map>) MatchItem.get("stats"), session, MatchItem);
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
	private static void incidents_insert(List<Map> list, SqlSession session, Map matchItem_root) {
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
			    
				
				String sql = "insert  into football_incident_t(  id,time, type ,position," + "match_id,"
						+ "player_id,player_name,home_score,away_score," + " create_time,delete_flag)values("
						+ "replace(UNIX_TIMESTAMP(now(6)),'.',''),'#{[time]}','#{[type]}','#{[position]}',"
						+ "#{[id]}," + "'#{[player_id]}','#{[player_name]}','#{[home_score]}','#{[away_score]}',"
						+ "now(),0 )";
				// sql = processVars(sql, json.getAsJsonObject("data") );
				sql = QlSpelUtil.parse(sql, MatchItem);
				logger.info(sql);

				// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
				// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

				MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

				System.out.println(mapper.updateV2(sql));
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

	private static void dbgExit(Exception e) throws IOException {
		Runtime.getRuntime().exit(1);
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
//		param.put("id", MatchItem.get(0));
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
			String sql = "insert  into football_score_t(  id,regular_time_score, half_court_score ,red_card,yellow_card,"
					+ "corner_kick,overtime_score,point_score,match_id,team_id,team_type,"
					+ "remark,league_ranking, score,create_time,delete_flag)values("
					+ " replace(UNIX_TIMESTAMP(now(6)),'.','') ,'#{[regular_time_score]}','#{[half_court_score]}','#{[red_card]}','#{[yellow_card]}',"
					+ "#{[corner_kick]},'#{[overtime_score]}','#{[point_score]}','#{[match_id]}','0','#{[team_type]}',"
					+ "'',0,0,now(),0 )";
			// sql = processVars(sql, json.getAsJsonObject("data") );
			sql = QlSpelUtil.parse(sql, param);
			logger.info(sql);
			MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

			int update = mapper.updateV2(sql);
			System.out.println(update);
			return update;
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

				String sql = "insert  into football_tlive_t(  id,time, type ,data,position," + "match_id,main,"
						+ " create_time,delete_flag)values("
						+ "replace(UNIX_TIMESTAMP(now(6)),'.',''),'#{[time]}','#{[type]}','#{[data]}','#{[position]}',"
						+ "#{[id]},0," + "now(),0 )";
				// sql = processVars(sql, json.getAsJsonObject("data") );
				sql = QlSpelUtil.parse(sql, sqlpa);
				logger.info(sql);

				// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
				// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

				MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

				System.out.println(mapper.updateV2(sql));
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

	public static void statPrcsList(List<Map> list, SqlSession session, Map matchItem_root) throws Exception {

		// List<Map> li=MatchItem.get("stats");
		for (Map MatchItem : list) {
			MatchItem.putAll(matchItem_root);
			MatchItem.put("match_id", matchItem_root.get("id"));
			try {
//				Map param = Maps.newConcurrentMap();
//				param.put("id", MatchItem.get(0));
//				param.put("match_id", MatchItem.get(1));
//				param.put("type", MatchItem.get(""));
//				param.put("home", MatchItem.get(3));		  
//				param.put("away", ((List) MatchItem.get(6)).get(0));

				// JsonArray competitons =json.getAsJsonObject("data").
				// getAsJsonObject("delete").getAsJsonArray("competitons");
				String sql = "insert  into football_stats_t(  id,match_id, type ,home,away,"
						+ " create_time,delete_flag)values("
						+ "  replace(UNIX_TIMESTAMP(now(6)),'.','') ,'#{[match_id]}','#{[type]}','#{[home]}','#{[away]}',"
						+ "now(),0 )";
				// sql = processVars(sql, json.getAsJsonObject("data") );
				sql = QlSpelUtil.parse(sql, MatchItem);
				logger.info(sql);

				// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
				// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

				MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

				System.out.println(mapper.updateV2(sql));
			} catch (Exception e) {
				if ((e.getCause() instanceof SQLIntegrityConstraintViolationException))
					logger.warn(e);
				else
					throw e;
			}
		}

	}
@Deprecated
	/**
	 * Football.Europe.Odds_europe_live
	 * 
	 * @throws Exception
	 * 
	 *                   CREATE TABLE `football_odds_t` ( `id` bigint(19) NOT NULL
	 *                   COMMENT '主键id', `company_id` bigint(19) NOT NULL COMMENT
	 *                   '公司id', `match_id` bigint(19) NOT NULL COMMENT '比赛id',
	 *                   `odds_type` tinyint(3) NOT NULL COMMENT '指数类型:1,asia-亚盘;
	 *                   2,bs-大小球;3,eu-欧赔', `change_time` bigint(19) NOT NULL
	 *                   COMMENT '变化时间', `happen_time` varchar(50) DEFAULT NULL
	 *                   COMMENT '比赛进行时间', `match_status` tinyint(3) NOT NULL
	 *                   COMMENT '比赛状态', `home_odds` decimal(32,10) NOT NULL COMMENT
	 *                   '主赔率', `tie_odds` decimal(32,10) NOT NULL COMMENT
	 *                   '盘口/和局赔率', `away_odds` decimal(32,10) NOT NULL COMMENT
	 *                   '客赔率', `lock_flag` tinyint(3) DEFAULT NULL COMMENT '是否封盘
	 *                   0-否,1-是', `real_time_score` varchar(20) NOT NULL COMMENT
	 *                   '实时比分', `create_time` datetime NOT NULL COMMENT '创建时间',
	 *                   `delete_flag` char(1) NOT NULL COMMENT '是否删除(1.已删除0.未删除)',
	 *                   PRIMARY KEY (`id`) ) ENGINE=Inno
	 */
	private static void Football_Europe_Odds_europe_live() throws Exception {

		SqlSession session = MybatisUtil.getConn();
		List<Map> li = MybatisUtil.executeSql("select * from football_match_t");
		for (Map map : li) {
			try {
				String matchid = map.get("id").toString();
				// "2644547";
				logger.info(map);
				JsonObject json = getJsonRzt("Football.Europe.Odds_europe_history", "&id=" + matchid,
						"Football.Europe.Odds_europe_history_" + matchid);

				Map m = JsonGsonUtil.toMap(json);
				m.forEach(new BiConsumer<String, List>() {

					@Override
					public void accept(String k, List v) {
						for (Object item1 : v) {

							try {

								List it = (List) item1;
								Map param = Maps.newConcurrentMap();
								param.put("company_id", k);
								param.put("match_id", matchid);
								param.put("change_time", it.get(0));

								param.put("home_odds", it.get(1));
								param.put("tie_odds", it.get(2));
								param.put("away_odds", it.get(3));
								param.put("lock_flag", it.get(4));

								String sql = "insert  into football_odds_t( " + " id,company_id, match_id ,change_time,"
										+ "home_odds,tie_odds,away_odds,lock_flag,create_time,delete_flag" + ")values("
										+ "#{[id]},'#{[company_id]}','#{[match_id]}','#{[change_time]}',"
										+ "#{[home_odds]},#{[tie_odds]},#{[away_odds]},#{[lock_flag]},now(),0 " + ")";
								// sql = processVars(sql, json.getAsJsonObject("data") );
								sql = QlSpelUtil.parse(sql, param);
								logger.info(sql);
								MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

								System.out.println(mapper.updateV2(sql));
							} catch (Exception e) {
								logger.error(e);
							}

						}

					}

				});
			} catch (Exception e) {
				logger.info(e);
			}

		}

	}

	public static JsonObject getJsonRzt(String svr, String param, String cachename) throws Exception {
		String fname = "d:\\cache\\" + cachename + ".json";

		if (new File(fname).exists()) {
			logger.info("file exist " + fname);

			String readFileToString = FileUtils.readFileToString(new File(fname));
			return new JsonParser().parse(readFileToString).getAsJsonObject();

		} else {
			String url = "http://www.skrsport.live/?service=" + svr + "&username=sport_api&secret=0gclkqzK" + param;
			logger.info(url);
//	        const request = require("request");
//	        const util = require('util')
//	        const requestPromise = util.promisify(request);
//	        const response = await requestPromise(url);
//	        console.log('response', response.body);
			String t = Httpcliet.testGet(url);
			// fs.writeFileSync(fname, response.body);
			FileUtils.write(new File(fname), t);
			return new JsonParser().parse(t).getAsJsonObject();
		}
	}

	/*
	 * `id` bigint(19) NOT NULL COMMENT '比赛id', `match_event_id` bigint(19) NOT NULL
	 * COMMENT '赛事id', `match_status` tinyint(3) NOT NULL COMMENT '足球状态码',
	 * `match_time` bigint(19) NOT NULL COMMENT '比赛时间', `tee_time` bigint(19) NOT
	 * NULL COMMENT '开球时间，可能是上/下半场开球时间', `home_id` bigint(19) NOT NULL COMMENT
	 * '主队id', `away_id` bigint(19) NOT NULL COMMENT '客队id', `match_detail`
	 * varchar(100) NOT NULL COMMENT '比赛详细说明，包括加时、点球、中立场、首回合、40分钟赛事 等',
	 * `which_round` int(10) DEFAULT NULL COMMENT '第几轮', `neutral_site` int(10) NOT
	 * NULL COMMENT '是否中立场，1-是 0-否', `animation` int(10) NOT NULL COMMENT
	 * '是否有动画，未购买客户请忽略（1有，0,没有)', `intelligence` int(10) NOT NULL COMMENT
	 * '是否有情报，未购买客户请忽略（1有，0,没有)', `squad` int(10) NOT NULL COMMENT
	 * '是否有阵容，未购买客户请忽略（1有，0,没有)', `video` int(10) NOT NULL COMMENT
	 * '是否有视频，未购买客户请忽略（1有，0,没有)', `create_time` datetime NOT NULL COMMENT '创建时间',
	 * `delete_flag` char(1) NOT NULL COMMENT '是否删除(1.已删除0.未删除)',
	 * 
	 */
	@Deprecated
	private static void Football_Live_Match_list() throws Exception {

		JsonObject json = getJsonRzt("Football.Live.Match_list");

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		for (List MatchItem : result1) {
			try {
				Map param = Maps.newConcurrentMap();
				param.put("id", MatchItem.get(0));
				param.put("match_event_id", MatchItem.get(1));
				param.put("match_status", MatchItem.get(2));
				param.put("match_time", MatchItem.get(3));
				param.put("tee_time", MatchItem.get(4));
				param.put("home_id", ((List) MatchItem.get(5)).get(0));
				param.put("away_id", ((List) MatchItem.get(6)).get(0));
				param.put("match_detail", ((List) MatchItem.get(7)).get(0));
				param.put("which_round", ((List) MatchItem.get(7)).get(1));
				param.put("neutral_site", ((List) MatchItem.get(7)).get(2));

				// JsonArray competitons =json.getAsJsonObject("data").
				// getAsJsonObject("delete").getAsJsonArray("competitons");
				String sql = "insert  into football_match_t(  id,match_event_id, match_status ,match_time,tee_time,"
						+ "home_id,away_id,which_round,neutral_site,create_time,delete_flag,match_detail,"
						+ "animation,intelligence,squad,video)values("
						+ "#{[id]},'#{[match_event_id]}','#{[match_status]}','#{[match_time]}','#{[tee_time]}',"
						+ "#{[home_id]},#{[away_id]},#{[which_round]},#{[neutral_site]},now(),0, '#{[match_time]}',"
						+ "0,0,0,0)";
				// sql = processVars(sql, json.getAsJsonObject("data") );
				sql = QlSpelUtil.parse(sql, param);
				logger.info(sql);

				// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
				// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

				MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

				System.out.println(mapper.updateV2(sql));
			} catch (Exception e) {
				logger.error(e);
			}

			// Runtime.getRuntime().exit(0);
		}
		// List MatchItem = (List) result1.get(0);

	}

	public static String m1() {
		System.out.println(" m1:"+sqlSessionFactory);
		return "000";
	}

	@Deprecated
	// CaptchData.Football_Basic_Update_profile()
	public static void Football_Basic_Update_profile() throws Exception {
		JsonObject json = getJsonRzt("Football.Basic.Update_profile");

		JsonArray ja = json.getAsJsonObject("data").getAsJsonObject("delete").getAsJsonArray("teams");
		JsonArray competitons = json.getAsJsonObject("data").getAsJsonObject("delete").getAsJsonArray("competitons");
		String sql = "insert  into Football.Basic.Update_profile(  id,data  )values( @id@,'@data@')";
		// sql = processVars(sql, json.getAsJsonObject("data") );
		logger.info(sql);

		SqlSession session = MybatisUtil.getConn();

		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

		System.out.println(mapper.updateV2(sql));
	}
@Deprecated
	public static void Football_Team_list() throws Exception {

		// areas

		// 'Basketball.Basic.Matchevent_list'
		JsonObject json = getJsonRzt("Football.Basic.Team_list");

		// url =
		// "http://www.skrsport.live/?service=Basketball.Basic.Match_deleted_ids&username=sport_api&secret=0gclkqzK";
		JsonArray ja = json.getAsJsonArray("data");

		SqlSession session = MybatisUtil.getConn();
		for (JsonElement JsonElement1_item : ja) {

			// item = json.data.teams[fld];
			String id = JsonElement1_item.getAsJsonObject().get("id").getAsString();
			try {
				// logger.info(JsonElement1_item);

				// unique

				MybatisUtil.uniqueIdx("basketball_team_t", "id", id, session);

				String sql = "insert  into football_team_t(  id,name_zh,short_name_zh,name_zht,short_name_zht,name_en,short_name_en,logo ,create_time ,delete_flag )values("
						+ " @id@,@name_zh@,@short_name_zh@,@name_zht@,@short_name_zht@,@name_en@,@short_name_en@,@logo@,now(),0)";
				sql = JsonGsonUtil.processVars(sql, JsonElement1_item.getAsJsonObject());
				logger.info(sql);
				MybatisUtil.execSql(sql, session);
				// var rzt = await query(connection, sql)
				// logger.info(rzt);
				// throw new RuntimeException("d");
				// Runtime.getRuntime().exit(0);

			} catch (UniqueEx e) {
				try {
					String sql = "update football_team_t set name_en='@name_en@',short_name_en='@short_name_en@',logo='@logo@'  where id="
							+ id;
//							+ "  )values("
//							+ " @id@,@name_zh@,@short_name_zh@,@name_zht@,@short_name_zht@,,@short_name_en@,)";
					sql = JsonGsonUtil.processVars(sql, JsonElement1_item.getAsJsonObject());
					logger.info(sql);
					MybatisUtil.execSql(sql, session);
				} catch (Exception e2) {
					logger.error(e2);
				}

			} catch (Throwable e) {
				logger.error(e);

			}
			// break;
			// break;
			// process.exit();
		}

		// basket_match_event_t

		// console.log("f");
	}

	public static JsonObject getJsonRzt(String svr, String param) throws Exception {

		return getJsonRzt(svr, param, svr);

	}
	public static JsonObject getJsonRztFromUrl(String svr,String param) throws Exception {

	
			//http://www.skrsport.live/?service=Football.Analysis.Match_detail&username=sport_api&secret=0gclkqzK&id=2723955
			String url = "http://www.skrsport.live/?service=" + svr + "&username=sport_api&secret=0gclkqzK"	 + param;

//	        const request = require("request");
//	        const util = require('util')
//	        const requestPromise = util.promisify(request);
//	        const response = await requestPromise(url);
//	        console.log('response', response.body);
			String t = Httpcliet.testGet(url);
			// fs.writeFileSync(fname, response.body);
		 
			return new JsonParser().parse(t).getAsJsonObject();
	 

	}

	public static JsonObject getJsonRztFromUrl(String svr) throws Exception {

	//	 + param
			//http://www.skrsport.live/?service=Football.Analysis.Match_detail&username=sport_api&secret=0gclkqzK&id=2723955
			String url = "http://www.skrsport.live/?service=" + svr + "&username=sport_api&secret=0gclkqzK";

//	        const request = require("request");
//	        const util = require('util')
//	        const requestPromise = util.promisify(request);
//	        const response = await requestPromise(url);
//	        console.log('response', response.body);
			String t = Httpcliet.testGet(url);
			// fs.writeFileSync(fname, response.body);
		 
			try {
				String fname = "d:\\cache\\" + svr + ".json";

				FileUtils.write(new File(fname), t);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return new JsonParser().parse(t).getAsJsonObject();
	 

	}

	public static JsonObject getJsonRzt(String svr) throws Exception {

		String fname = "d:\\cache\\" + svr + ".json";

		if (new File(fname).exists()) {
			logger.info("file exist " + fname);

			String readFileToString = FileUtils.readFileToString(new File(fname));
			return new JsonParser().parse(readFileToString).getAsJsonObject();

		} else {
			//http://www.skrsport.live/?service=Football.Analysis.Match_detail&username=sport_api&secret=0gclkqzK&id=2723955
			String url = "http://www.skrsport.live/?service=" + svr + "&username=sport_api&secret=0gclkqzK";

//	        const request = require("request");
//	        const util = require('util')
//	        const requestPromise = util.promisify(request);
//	        const response = await requestPromise(url);
//	        console.log('response', response.body);
			String t = Httpcliet.testGet(url);
			// fs.writeFileSync(fname, response.body);
			FileUtils.write(new File(fname), t);
			return new JsonParser().parse(t).getAsJsonObject();
		}

	}

}
