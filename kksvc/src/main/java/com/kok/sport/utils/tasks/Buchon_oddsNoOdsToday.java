package com.kok.sport.utils.tasks;

import com.kok.sport.controller.FootballOddsController;
import com.kok.sport.utils.Util;


//  com.kok.sport.utils.tasks.Buchon_oddsNoOdsToday 600
public class Buchon_oddsNoOdsToday {

	public static void main(String[] args) throws Exception {
		
		//must exit bcz tonzhi wss not  gc
				int timetout = Integer.parseInt(args[0].trim());
				Util.timeOutExitRuntime(timetout*1000);
		String sql = "select * from odd_wait_lodi_24hr limit 30";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV4(sql);
		//return "ok";
		//3379322
		//select * from odd_wait_lodi_24hr limit  9

	}

}
