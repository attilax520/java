package com.kok.sport.integration.impl;

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
	public	SqlSession session;
	
	@Override
	public   void Football_Live_Match_list() throws Exception {

		JsonObject json =CaptchData. getJsonRzt("Football.Live.Match_list");

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
								  //com.kok.sport.dao.FootballMatchDao.into_football_match_t
			int r=	session.insert("com.kok.sport.dao.FootballMatchDao.into_football_match_t", param);
			 	logger.info(r);

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

}
