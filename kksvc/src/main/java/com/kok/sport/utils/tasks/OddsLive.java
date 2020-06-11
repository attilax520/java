package com.kok.sport.utils.tasks;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;

// T(com.kok.sport.utils.tasks.OddsLive).oddsLive
public class OddsLive {
	static Logger logger2 = LoggerFactory.getLogger(SyncFootballNumberOddsHistoryServiceImpl.class);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String f="D:\\cache\\Odds_live_history.json";
	//	String f = "D:\\prj\\sport-service\\kok-sport-service\\doc\\Football.Number.Odds_history.json";
		String t = FileUtils.readFileToString(new File(f.trim()));
	//	insertTable(t);
		int timetout = Integer.parseInt(args[0].trim());
		Util.timeOutExitRuntime(timetout*1000);
			while(true) {
				try {
					
					oddsLive();
					logger2.info("f");
					if("a"=="a")
						return;
					//Thread.sleep(1000);
				} catch (Exception e) {
				e.printStackTrace();
				}
			}
			
			
		
	}
	
	public static  SqlSessionFactory sqlSessionFactory;// = MybatisUtil.getSqlSessionFactoryRE();
	private static SqlSession session;

	public static boolean oddsLive() {
		
		if(sqlSessionFactory==null)
			 sqlSessionFactory = MybatisUtil.getSqlSessionFactoryRE();
			  session = sqlSessionFactory.openSession(true);
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
				FileUtils.write(new File("d:\\cache\\Odds_live_history2.json"), sendGet);
			} catch (Exception e) {
				// TODO: handle exception
			}
			logger2.info(sendGet);
			return insertTable(  sendGet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				//HttpUtil.sendGet(map);maosi asyn error
		return false;
	 
	
	}
	
	
	 
	private static boolean insertTable(  String sendGet) {
		
	
 
		 	  MybatisMapper1 = session.getMapper(MybatisMapper.class);
	//	 MybatisMapper1 = session.getMapper(MybatisMapper.class);
		
		JSONObject getData = JSONObject.parseObject(sendGet).getJSONObject("data");
		Set<String> keySet_comID = getData.keySet();
		// 因为这边我无法确定有多少家公司，所以只能每次请求的时候将所有的 key 取出来，以其作为循环添加的基础
		List<FootballOdds> oddsList = new ArrayList<>();
		for (String comId : keySet_comID) {
			 
			JSONArray panko_data = (JSONArray) getData.get(comId);
			 
			 for (Object odd : panko_data) {
				 try {
					 Runnable runnable = new Runnable() {
						
						@Override
						public void run() {
							  addOdds(odd,comId);
							
						}
					};
					runnable.run();
					//new Thread(runnable).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			   
			}
		}
		
		try {
		 //   session.close();
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return false;

		// return footballOddsService.saveBatch(oddsList);
	}

	 static MybatisMapper	  MybatisMapper1 ;
		

	private static void addOdds(Object odd, String comId) {
		System.out.println(odd);	
		JSONArray array=(JSONArray) odd;
		 JSONArray arrayData = array.getJSONArray(2);
         // "6.0,4.5,0.11,0",//大球,盘口,小球,是否封盘：1-封盘,0-未封盘
         String[] str = arrayData.getString(2).split(",");
         FootballOdds footballOdds = new FootballOdds() {{
//             setId(1L);              //主键Id
             setCompanyId(Long.parseLong(comId));         //公司Id
             setMatchId(array.getLong(0));       //比赛Id
             // 指数类型 1,asia-亚盘
             if ("asia".equals(array.getString(1))) {
                 setOddsType(1);
                 // 2,bs-大小球
             } else if ("bs".equals(array.getString(1))) {
                 setOddsType(2);
                 //3,eu-欧赔
             } else if ("eu".equals(array.getString(1))) {
                 //setOddsType(3);
            	  throw new RuntimeException();
             } else {
                 //出错
                 setOddsType(0);
             }
             setChangeTime(arrayData.getLong(0));        //变化时间
             setHappenTime(arrayData.getString(1));        //比赛进行时间 未开始时为空
             setMatchStatus(arrayData.getInteger(3));       //比赛状态
             setHomeOdds(new BigDecimal(str[0]));          //主队赔率(大球)
             setTieOdds(new BigDecimal(str[1]));           //和局赔率(盘口)
             setAwayOdds(new BigDecimal(str[2]));          //客队赔率(小球)
             setLockFlag(Integer.parseInt(str[3]));          //是否封盘 0-否 1-是
             setRealTimeScore(array.getString(3));     //实时比分
             setCreateTime(LocalDateTime.now());     //创建时间
             setDeleteFlag("0");     //是否删除 0-未删除 1-已删除
         }};
         System.out.println(footballOdds);
         //obj end
         
         Map m = MapUtil.fromBean(footballOdds);
			m.put("$insert", "football_odds_t");
			Map m2 = MapUtil.camel2lowerUnderline(m);
			MysqlInsertUtil.insertV2_faster(null, m2,MybatisMapper1);
		//	logger2.info(curindex);
	
	}


}
