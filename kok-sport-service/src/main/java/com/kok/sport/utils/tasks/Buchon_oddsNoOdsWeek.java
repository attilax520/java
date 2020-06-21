package com.kok.sport.utils.tasks;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;

import com.kok.sport.controller.FootballOddsController;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.utils.Util;

//
//  com.kok.sport.utils.tasks.Buchon_oddsNoOdsToday 600
public class Buchon_oddsNoOdsWeek {
	
	public static void main(String[] args) throws Exception {
		
		String f="D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\tasks\\oneweekMatch.sql";
		String t=FileUtils.readFileToString(new File(f));
		
		
		SyncFootballNumberOddsHistoryServiceImpl.isFast=true;
		//must exit bcz tonzhi wss not  gc
				int timetout = Integer.parseInt(args[0].trim());
				Util.timeOutExitRuntime(timetout*1000);
		String sql = "select * from odd_wait_lodi_1week limit 500";
		sql=t;
		System.out.println(sql);
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV4(sql);
		//return "ok";
		//3379322
		//select * from odd_wait_lodi_24hr limit  9
		System.out.println("f");

	}

}
