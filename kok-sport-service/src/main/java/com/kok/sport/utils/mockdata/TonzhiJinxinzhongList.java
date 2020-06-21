package com.kok.sport.utils.mockdata;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.controller.ApiControllerTrad;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.db.MybatisUtil;

//  com.kok.sport.utils.mockdata.TonzhiJinxinzhongList
public final class TonzhiJinxinzhongList {

	public static void main(String[] args) throws Exception {
	//	int tim=Integer.p args[0];
		Util.timeOutExitRuntime(600*1000);
		queryPageTrad_jinxinzhong();
	

	}
	
static org.apache.logging.log4j.Logger logger = LogManager.getLogger(UnitTestApi.class);
	
	
 
	public static void queryPageTrad_jinxinzhong() throws Exception {
		// try {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		// reqt.addParameter("from", "match_ex"); //3380166
		// reqt.addParameter("$page", "1");
		// reqt.addParameter("$pagesize", "2");
	//	reqt.addParameter("odds_type", "between 1 and 3");
//			reqt.addParameter("subquery", " ( select * from football_score_t where match_id={match_id} limit 1 ) as zhudui_scoreObj");
//			reqt.addParameter("subqueryID","keduiSq_matchid");
		ApiControllerTrad ctrl = new ApiControllerTrad();
		ctrl.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ctrl.req = reqt;
		Object bizSql = ctrl.queryPage("foot_match_v_jinxinzhong", 30, 1);
		LinkedHashMap m=(LinkedHashMap) bizSql;
		List  li=(List) m.get("data");
	 //	List li =  (List) bizSql;
	//	System.out.println(li.get("count").toString());
		String jsonString = JSON.toJSONString(li, true);
		System.out.println(jsonString);
		try {
			FileUtils.write(new File("D:\\cache\\jinxinzh.json"), jsonString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		sendTongzhi(li,MybatisUtil.getSqlSessionFactory());
//		} catch (Exception e) {
//			System.out.println(e);
//		}

	}
	private static void sendTongzhi(Object bizSql, SqlSessionFactory sqlSessionFactory) {
	 
		logger.info("sendTongzhi-->mMsg:");
	//	logger.info(mMsg);
		//
		// Maps.newLinkedHashMap();
		try {
		 	// noExistSetValChk(recSign,sqlSessionFactory);
			 
 
			Map mapTonzhi = Maps.newLinkedHashMap();
			mapTonzhi.put("method", "testsend");
			mapTonzhi.put("msg", bizSql);
			logger.info("==>WssTest.sendMsgClose:");
		//	InsertSet(recSign,sqlSessionFactory);
			logger.info(mapTonzhi);
			//WssTest.sendMsgClose("teeeeeeeeeeeeeeeeeeeee", WssTest.hostip);
		 	WssTest.sendMsgClose(JSON.toJSONString(mapTonzhi), WssTest.hostip);
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
