package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.integration.SyncFootballNumberOddsHistoryService;
import com.kok.sport.service.FootballOddsService;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.constant.HttpUtil;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 单场指数历史 获取单场比赛所有公司的指数变化，从初盘到请求接口的时刻
 */
@Service
public class SyncFootballNumberOddsHistoryServiceImpl implements SyncFootballNumberOddsHistoryService {
	static Logger logger2 = LoggerFactory.getLogger(SyncFootballNumberOddsHistoryServiceImpl.class);
	@Autowired
	public   SqlSessionFactory sqlSessionFactory;// = MybatisUtil.getSqlSessionFactoryRE();

	public static void main(String[] args) throws Exception {
		
	
		String f = "D:\\prj\\sport-service\\kok-sport-service\\doc\\Football.Number.Odds_history.json";
		String t = FileUtils.readFileToString(new File(f.trim()));

		System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));// test_data

		SyncFootballNumberOddsHistoryServiceImpl syncFootballNumberOddsHistoryServiceImpl = new SyncFootballNumberOddsHistoryServiceImpl();
	
		syncFootballNumberOddsHistoryServiceImpl.sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		
		syncFootballNumberOddsHistoryServiceImpl.	oddsLive();
		syncFootballNumberOddsHistoryServiceImpl.insertTable(3380170L, t);
		}

	@Autowired
	public FootballOddsService footballOddsService;

	/**
	 * 拉取单场指数历史
	 * 
	 * @param Id
	 * @return
	 */
	@Override
	public boolean insertFootballNumberOddsData(Long Id) {
		Map<String, String> map = new HashMap<String, String>() {
			{
				put("service", "Football.Number.Odds_history");
				put("id", Id.toString());
			}
		};
		  String url = "http://www.skrsport.live/?service=Football.Number.Odds_history&username=sport_api&secret=0gclkqzK&id="+Id;
	System.out.println(url);
		  String sendGet;
		try {
			sendGet = Httpcliet.testGet(url);
			logger2.info(sendGet);
			return insertTable(Id, sendGet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				//HttpUtil.sendGet(map);maosi asyn error
		return false;
	
	}
	public boolean oddsLive() {
//		Map<String, String> map = new HashMap<String, String>() {
//			{
//				put("service", "Football.Number.Odds_history");
//				put("id", Id.toString());
//			}
//		};
		  String url = "http://www.skrsport.live/?service=Football.Number.Odds_live_history&username=sport_api&secret=0gclkqzK";
		String sendGet;
		try {
			sendGet = Httpcliet.testGet(url);
			try {
				FileUtils.write(new File("d:\\cache\\Odds_live_history.json"), sendGet);
			} catch (Exception e) {
				// TODO: handle exception
			}
			logger2.info(sendGet);
			return insertTable(0L, sendGet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				//HttpUtil.sendGet(map);maosi asyn error
		return false;
	
	}
	
	MybatisMapper MybatisMapper1;
	public String curindex;
	private boolean insertTable(Long match_Id, String sendGet) {
		
		if(sqlSessionFactory==null)
		 sqlSessionFactory = MybatisUtil.getSqlSessionFactoryRE();
		SqlSession session = sqlSessionFactory.openSession(true);
 
		  MybatisMapper1 = session.getMapper(MybatisMapper.class);

		
		JSONObject getData = JSONObject.parseObject(sendGet).getJSONObject("data");
		Set<String> keySet_comID = getData.keySet();
		// 因为这边我无法确定有多少家公司，所以只能每次请求的时候将所有的 key 取出来，以其作为循环添加的基础
		List<FootballOdds> oddsList = new ArrayList<>();
		int n=0;
		for (String comId : keySet_comID) {
			
			
			// for (Map.Entry<String, Object> entry : getData.entrySet()) {
			// String x = entry.getKey();
			JSONObject panko_data = (JSONObject) getData.get(comId);
			// if (comId.equals(x)) {
			// JSONObject yData=

			try {
				dasyhaoqiu(match_Id, comId, panko_data);
			} catch (Exception e) {
				logger2.error("", e);
			}

			try {
				processYapan(match_Id, comId, panko_data);
			} catch (Exception e) {
				logger2.error("", e);
			}
			 
			//fast speed
			n++;
			if(n>3)
			break;

		}
		
		try {
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return false;

		// return footballOddsService.saveBatch(oddsList);
	}

	private void dasyhaoqiu(Long match_Id, String comId, JSONObject panko_data) {
		// bs 大小球
		JSONArray bsData = panko_data.getJSONArray("bs");

		for (int i = 0; i < bsData.size(); i++) {
			try {
				JSONArray forData = bsData.getJSONArray(i);
				FootballOdds footballOdd = new FootballOdds() {
					{
						setId(1L); // 主键Id
						setCompanyId(Long.parseLong(comId)); // 公司Id
						setMatchId(match_Id); // 比赛Id
						setOddsType(2); // 指数类型:1,asia-亚盘; 2,bs-大小球;3,eu-欧赔
						setChangeTime(forData.getLong(0)); // 变化时间
						setHappenTime(forData.getString(1)); // 比赛进行时间
						setMatchStatus(forData.getInteger(5)); // 比赛状态
						setHomeOdds(forData.getBigDecimal(2)); // 主队赔率
						setTieOdds(forData.getBigDecimal(3)); // 和局赔率
						setAwayOdds(forData.getBigDecimal(4)); // 客队赔率
						setLockFlag(forData.getInteger(6)); // 是否封盘 0-否 1-是
						setRealTimeScore(forData.getString(7)); // 实时比分
						setCreateTime(LocalDateTime.now()); // 创建时间
						setDeleteFlag("0"); // 是否删除 0-未删除 1-已删除
					}
				};
				Map m = MapUtil.fromBean(footballOdd);
				m.put("$insert", "football_odds_t");
				Map m2 = MapUtil.camel2lowerUnderline(m);
				logger2.info("--dasyaocyo foreach  MysqlInsertUtil.insertV2");
				logger2.info(JSON.toJSONString(m2) );
				MysqlInsertUtil.insertV2_faster(sqlSessionFactory, m2,MybatisMapper1);
				logger2.info("--finish one" );
				 
					logger2.info(curindex);
			} catch (Exception e) {
				logger2.error("", e);
			}

			// MybatisUtil.getMybatisMapper().insertBymapV2(m);

		}
	}

	private void processYapan(Long match_Id, String comId, JSONObject panko_data) {
		JSONArray asiaData = panko_data.getJSONArray("asia");
		// asia 亚盘

		for (int i = 0; i < asiaData.size(); i++) {
			try {
				JSONArray forData = asiaData.getJSONArray(i);
				FootballOdds footballOdd = new FootballOdds() {
					{
						setId(1L); // 主键Id
						setCompanyId(Long.parseLong(comId)); // 公司Id
						setMatchId(match_Id); // 比赛Id
						setOddsType(1); // 指数类型:1,asia-亚盘; 2,bs-大小球;3,eu-欧赔
						setChangeTime(forData.getLong(0)); // 变化时间
						setHappenTime(forData.getString(1)); // 比赛进行时间
						setMatchStatus(forData.getInteger(5)); // 比赛状态
						setHomeOdds(forData.getBigDecimal(2)); // 主队赔率
						setTieOdds(forData.getBigDecimal(3)); // 和局赔率
						setAwayOdds(forData.getBigDecimal(4)); // 客队赔率
						setLockFlag(forData.getInteger(6)); // 是否封盘 0-否 1-是
						setRealTimeScore(forData.getString(7)); // 实时比分
						setCreateTime(LocalDateTime.now()); // 创建时间
						setDeleteFlag("0"); // 是否删除 0-未删除 1-已删除
						// add yhapan
						// footballOdd
					}
				};

				Map m = MapUtil.fromBean(footballOdd);
				m.put("$insert", "football_odds_t");
				Map m2 = MapUtil.camel2lowerUnderline(m);
				MysqlInsertUtil.insertV2_faster(sqlSessionFactory, m2,MybatisMapper1);
				logger2.info(curindex);
			} catch (Exception e) {
				logger2.error("", e);
			}

		}
	}

}
