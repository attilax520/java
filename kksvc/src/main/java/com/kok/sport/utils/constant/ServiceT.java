package com.kok.sport.utils.constant;

import org.junit.Test;

import com.kok.sport.integration.impl.SyncFootballTeamlistServiceImp;
import com.kok.sport.utils.db.MybatisUtil;

public class ServiceT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Test
	public void test_SyncFootballTeamlistServiceImp() throws Exception {
		// SyncFootballEuropeOddseuropeliveImp
		SyncFootballTeamlistServiceImp	SyncFootballTeamlistServiceImp1=new SyncFootballTeamlistServiceImp();
		SyncFootballTeamlistServiceImp1.session=MybatisUtil.getConn();
		SyncFootballTeamlistServiceImp1.Football_Team_list();

	}
	//Football_Team_list

}
