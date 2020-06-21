package com.kok.sport.utils.constant;

import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.service.impl.FootballOddsServiceImpl;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.mockdata.UnitTestApi;

 

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//@WebAppConfiguration
@MapperScan({ "com.kok.sport.utils", "com.kok.sport.utils.constant", "com.kfit.user" })
public class springbootUnitV2 {
	
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);
	@Autowired
	SqlSessionFactory sqlSessionFactory ;
	@Autowired
	SyncFootballNumberOddsHistoryServiceImpl SyncFootballNumberOddsHistoryServiceImpl1;
	
	@Test
	public void test_SyncFootballTeamlistServiceImp() throws Exception {
//		List<String> matchids=UnitTestApi. matchids();
//		for (String mid : matchids) {
//			SyncFootballNumberOddsHistoryServiceImpl1.insertFootballNumberOddsData(Long.parseLong(mid));
//		}
//		SyncFootballNumberOddsHistoryServiceImpl c=new SyncFootballNumberOddsHistoryServiceImpl();
//		
//		FootballOddsServiceImpl fos=new FootballOddsServiceImpl();
//		c.footballOddsService=fos;
	
		 System.out.println(sqlSessionFactory);

	}
}
