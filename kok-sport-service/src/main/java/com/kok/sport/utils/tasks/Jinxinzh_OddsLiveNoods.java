package com.kok.sport.utils.tasks;

import com.kok.sport.controller.FootballOddsController;


//  com.kok.sport.utils.tasks.Jinxinzh_OddsLiveNoods
public class Jinxinzh_OddsLiveNoods {

	public static void main(String[] args) throws Exception {
		
		
		String sql = "select * from odd_live_wait_lodi";
		FootballOddsController c = new FootballOddsController();
		c.insertFootballNumberOddsDataV4(sql);
		//return "ok";
		//3379322
		//select * from odd_wait_lodi_24hr limit  9

	}

}
