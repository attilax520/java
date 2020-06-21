package com.kok.sport.utils.constant;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.controller.ApiController;
import com.kok.sport.controller.ApiController4Add;
import com.kok.sport.controller.ApiControllerForAdv;
import com.kok.sport.integration.impl.SyncFootballEuropeOddseuropeliveImp;
import com.kok.sport.integration.impl.SyncFootballTeamlistServiceImp;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.db.MybatisUtil;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
@MapperScan({ "com.kok.sport.utils", "com.kok.sport.utils.constant", "com.kfit.user" })
public class springbootUnit {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	public static void main(String[] args) throws Exception {
		// 测试，可能要加个join测试
		Class.forName("com.mysql.cj.jdbc.Driver");
		Object conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test_demo?useSSL=false&serverTimezone=UTC", "root", "password");

	}

	@Autowired
	public ApiController ApiController1;
	
	@Autowired
	public ApiController4Add ApiController4Add1;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory ;

	@Autowired
	SyncFootballEuropeOddseuropeliveImp SyncFootballEuropeOddseuropeliveImp1;
	@Autowired
	SyncFootballTeamlistServiceImp SyncFootballTeamlistServiceImp1;

//	@Test
//	public void save_sqlSessionFactory() throws Exception {
//		Util.toFile(sqlSessionFactory, "sqlSessionFactoryFile");
//		System.out.println("f");
//	
//	}
	@Test
	public void test_SyncFootballTeamlistServiceImp() throws Exception {
		
		Connection connection = SyncFootballTeamlistServiceImp1.session.getConnection();
		// SyncFootballEuropeOddseuropeliveImp
		SyncFootballTeamlistServiceImp1.Football_Team_list();

	}

	@Test
	public void test_SyncFootballEuropeOddseuropeliveImp() throws Exception {
		// SyncFootballEuropeOddseuropeliveImp
		SyncFootballEuropeOddseuropeliveImp1.Football_Europe_Odds_europe_live();

	}

 
	public void testGetEntFileById() {

		// com.mysql.cj.jdbc.Driver
		try {

//			System.out.println(ApiController1.query("football_team_t", null, null, null, 1, 3));
//			System.out.println(ApiController1.query("football_team_t", "id", "10008", "name_zh", 1, 3));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Assert.assertSame("企业数量有误",500,entFileService.getCount());
	}

 
//	public void test_queryPage() throws Exception {
//
//		MockHttpServletRequest reqt = new MockHttpServletRequest();
////		reqt.addParameter("select", "*");
//		reqt.addParameter("from", "football_team_txx");
////		reqt.addParameter("where", "id=10008");
//		reqt.addParameter("page", "1");
//		reqt.addParameter("pagesize", "10");
//		ApiController1.req = reqt;
//		// "football_team_t", "id", "10008,10009", "name_zh", 1, 3
//		System.out.println(ApiController1.queryPage());
//
//	}
	@Test
	public void test_ApiController4Add1() throws Exception {

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("into", "football_odds_t");
		reqt.addParameter("id", "16");
		reqt.addParameter("match_iD", "12");
		reqt.addParameter("real_time_score", "vv");
		
     	reqt.addParameter("select", "*");
		
		reqt.addParameter("where", "id=10008");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "10");
		ApiController1.req = reqt;
		System.out.println(ApiController4Add1.add());

	}
	@Test
	public void test_queryDataStruts() throws Exception {

		MockHttpServletRequest reqt = new MockHttpServletRequest();
     	reqt.addParameter("select", "*");
		reqt.addParameter("from", "sys_area_t");
		reqt.addParameter("where", "id=10008");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "queryDataStruts");
		ApiController1.req = reqt;
		Object queryDataStruts = ApiController1.queryDataStruts("sys_area_t");
		
		List li=(List) queryDataStruts;
//		jsonstr=JSON.toJSONString(queryDataStruts);
//		JSONObject jo=JSONObject.parseObject(queryDataStruts.toString());
		assertNotEquals(0, li.size());
		System.out.println(li.size());

	}
	
	
	
	
	public void test_queryPage() throws Exception {

		MockHttpServletRequest reqt = new MockHttpServletRequest();
     	reqt.addParameter("select", "*");
		reqt.addParameter("from", "football_team_t");
		reqt.addParameter("where", "id=10008");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "10");
		ApiController1.req = reqt;
		Object queryPage = ApiController1.queryPage(reqt);
		LinkedHashMap m=(LinkedHashMap) queryPage;
		System.out.println(m.get("count"));
		System.out.println(queryPage);

	}
 
	public void test_queryApi() throws Exception {

		MockHttpServletRequest reqt = new MockHttpServletRequest();
//		reqt.addParameter("select", "*");
		reqt.addParameter("from", "football_team_t");
		reqt.addParameter("where", "id=10008");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "10");
		ApiController1.req = reqt;
		System.out.println(ApiController1.query());

	}

	@Before
	public void init() {
		System.out.println("开始测试-----------------");
	}

	@After
	public void after() {
		System.out.println("测试结束-----------------");
	}

}
