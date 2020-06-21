package com.kok.sport.utils.tasks;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.utils.Timeutil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.mockdata.SSLWebSocketClient;
import com.kok.sport.utils.mockdata.WssTest;

//com.kok.sport.utils.mockdata.wssSendTEst
public class wssSendTEst {

	public static void main(String[] args) throws Exception {

		while (true) {
			try {
				String msg = "xxxxxxxxxxxxxxx666xxxxx" + new Date();

				
				SSLWebSocketClient.send(msg,WssTest.hostip4jincyo);

				System.out.println("f");
				
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Runtime.getRuntime().exit(0);
		}
//		
//			
//		 
//			  SyncFootballLiveMatchdetailliveImp .sendTongzhiDebug(core,MybatisUtil.getConn());
//		//	select * from football_tlive_v  where   match_id=3380170
//			
//			//WssTest.sendMsgClose("11111111111", WssTest.hostip);
//			Thread.sleep(15*1000);
//		}

	}

}
