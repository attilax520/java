package com.kok.sport.utils.mockdata;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.utils.Timeutil;
import com.kok.sport.utils.db.MybatisUtil;


//com.kok.sport.utils.mockdata.wssSendTEst
public class wssSendTEst {

	public static void main(String[] args) throws  Exception {
		
		while(true) {
			
			Map m = Maps.newLinkedHashMap();
			m.put("method", "testsend");
	 		m.put("msg", new Date().toLocaleString());
	 		WssTest.sendMsgClose(JSON.toJSONString(m), WssTest.hostip);
			System.out.println(m);
		//	logger.info("==>WssTest.sendMsgClose:");
		//	InsertSet(recSign,session2);
			//     "home_score": 2,
          //  "away_score": 0, 
			LinkedHashMap core=Maps.newLinkedHashMap();
			core.put("id", 2547914);
			core.put("time", 99);
//			 "": 1,//类型，详见状态码->足球技术统计   jinqiu
//			  "": 2,//事件发生方,0-中立 1,主队 2,客队
			  core.put("type", 1);
			  core.put("position",1);
			  core.put("home_score",2);
			  core.put("away_score",3);
			  core.put("data_typex", "jinqiuTonzhi");
			  
			  SyncFootballLiveMatchdetailliveImp .sendTongzhiDebug(core,MybatisUtil.getConn());
		//	select * from football_tlive_v  where   match_id=3380170
			
			//WssTest.sendMsgClose("11111111111", WssTest.hostip);
			Thread.sleep(15*1000);
		}
		

	}

}
