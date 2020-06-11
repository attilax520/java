package com.kok.sport.utils.tasks;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.Timeutil;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.mockdata.unitRecentlyV2;
import com.kok.sport.utils.ql.QlSpelUtil;


//  com.kok.sport.utils.tasks.JinxinzhonJincyoTonzhi 10000
@SuppressWarnings("all")
public class JinxinzhonJincyoTonzhi {

	public static boolean debug = false;
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(unitRecentlyV2.class);

	public static void main(String[] args) throws Exception {

		// jinxinzhon_Football_Live_Match_list_statue_cellList();
	//	String t = FileUtils.readFileToString(new File("D:\\cachex\\Football.Live.Match_detail_live.json"));
	//	midsJinxinzhon.add(2547914);

		
		
		//must exit bcz tonzhi wss not  gc
		int timetout = Integer.parseInt(args[0].trim());
		Util.timeOutExitRuntime(timetout*1000);

		// coll mids from  db
		Timer tmr = new Timer();
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {
				try {//from db
					jinxinzhon_Football_FrmDB();
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}, 0, 2000);
		// coll mids from net
		coll_midFrmNet();
			

		//send tonzhi 
		while (true) {
        logger.info("-->midsJinxinzh:");
        logger.info(midsJinxinzhon);
			try {
				Thread.sleep(1000);
				jincyoTonji_Football_Live_Match_detail_live();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	//	System.out.println("f");

	}

	protected static void jinxinzhon_Football_FrmDB() {
		String sql="select * from jinxinzh_today4tonzhi";
		 List<LinkedHashMap> rs=	MybatisUtil.getMybatisMapper(SqlSessionFactory1) .querySql(sql);
		// midsJinxinzhon.clear();
		for (LinkedHashMap linkedHashMap : rs) {
			try {
				Object id = linkedHashMap.get("id");
				if(id instanceof Long)
					id=((Long)id).intValue();
				midsJinxinzhon.add(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	 
		
	}

	private static void coll_midFrmNet() {
		Timer tmr = new Timer();
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {
				try {//from db
					jinxinzhon_Football_Live_Match_list_statue_cellList();
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}, 0, 1000);
		
	}

 
	public static SqlSessionFactory SqlSessionFactory1 = MybatisUtil.getSqlSessionFactoryRE();
	public static Set midsJinxinzhon = Sets.newConcurrentHashSet();

	public static void jinxinzhon_Football_Live_Match_list_statue_cellList() throws Exception {
		 
		
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
 

				LinkedHashMap param = Maps.newLinkedHashMap();
				param.put("id", MatchItem.get(0));
				param.put("match_id", MatchItem.get(0));
				int match_id = ((Double) MatchItem.get(0)).intValue();
				int stt = ((Double) MatchItem.get(2)).intValue();
				if (stt >= 2 && stt <= 7) {
					Double Double_teeTime = (Double) MatchItem.get(4);
					Double matTime = (Double) MatchItem.get(3);
					int timePass = (Double_teeTime.intValue() - matTime.intValue()) / 60;
				     midsJinxinzhon.add(match_id);
 
					n++;
					logger.info(param);
				}else
					midsJinxinzhon.remove(match_id);

			} catch (Exception e) {
				logger.error(e);
			}
		}
		System.out.println("----ing:" + n);
		
 
				

					 
	//	System.out.println("----ing:" + n);
	}

	public static void jincyoTonji_Football_Live_Match_detail_live() throws Exception {
		JsonObject json;
		if (debug) {
			String t = FileUtils.readFileToString(new File("D:\\cachex\\Football.Live.Match_detail_live.json"));

			json = JsonGsonUtil.toJsonObjectFromStr(t);

		} else
			json = CaptchData.getJsonRztFromUrl("Football.Live.Match_detail_live");

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
			Object mid = MatchItem_bisai.get("id");
			if (mid instanceof Double)
				mid = ((Double) mid).intValue();
			if (!midsJinxinzhon.contains(mid))
				continue;
			// updateMatchStateRealtime(session.getMapper(MybatisMapper.class),matchid)

			// updateMatchStateRealtime(session.getMapper(MybatisMapper.class),)

//			try {
//				List tliveList = (List) MatchItem_bisai.get("tlive");
//				tonzhi_processtliveList(tliveList, session, MatchItem_bisai);
//			} catch (Exception e) {
//				logger.warn(e);
//				// dbgExit(e);
//			}

			try {
				tonzhi_processtliveList((List<Map>) MatchItem_bisai.get("incidents"), session, MatchItem_bisai);
			} catch (Exception e) {
				logger.warn(e);
			}

		}
	}

	private static void tonzhi_processtliveList(List<Map> tlive_list, SqlSession session, Map matchItem_root)
			throws Exception {
		// List<Map> li=MatchItem.get("stats");
//replace(UNIX_TIMESTAMP(now(6)),'.','')

		// for (Map MatchItem : tlive_list) {
		try {
			Map MatchItem = tlive_list.get(tlive_list.size() - 1);
			logger.info("------processtliveList foreach");
			MatchItem.putAll(matchItem_root); // set bisai id
			MatchItem.put("data_typex", "jinqiuTonzhi");
			// MatchItem.put("time", Timeutil.toSecs(MatchItem.get("time").toString(), 0));
			SyncFootballLiveMatchdetailliveImp.sendTongzhi(MatchItem, session);

		}
		// continue;
		catch (Throwable e) {
			logger.error(e);
			// dbgExit(e);
		}
		// }
	}

}
