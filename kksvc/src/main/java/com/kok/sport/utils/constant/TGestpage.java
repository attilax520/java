package com.kok.sport.utils.constant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kok.sport.controller.ApiController;
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
@MapperScan({ "com.kok.sport.utils", "com.kok.sport.utils.constant", "com.kfit.user" })
public class TGestpage {
	@Autowired
	public ApiController ApiController1;
	@Test
	public void test_queryWzCount() throws Exception {

		MockHttpServletRequest reqt = new MockHttpServletRequest();
//		reqt.addParameter("select", "*");
		reqt.addParameter("from", "football_team_txx");
//		reqt.addParameter("where", "id=10008");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "10");
		ApiController1.reqMock=reqt;
		ApiController1.req = reqt;
		// "football_team_t", "id", "10008,10009", "name_zh", 1, 3
		System.out.println(ApiController1.queryPage(reqt));
		System.out.println("f");

	}

}
