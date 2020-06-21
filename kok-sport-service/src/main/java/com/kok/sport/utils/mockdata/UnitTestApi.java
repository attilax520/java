package com.kok.sport.utils.mockdata;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.SportApplication;
import com.kok.sport.controller.ApiController;
import com.kok.sport.controller.ApiControllerForAdv;
import com.kok.sport.controller.ApiControllerSmp;
import com.kok.sport.controller.ApiControllerTrad;
import com.kok.sport.controller.FootballMatchController;
import com.kok.sport.controller.FootballOddsController;
import com.kok.sport.integration.impl.SyncFootballLiveMatchdetailliveImp;
import com.kok.sport.integration.impl.SyncFootballLiveMatchlistServiceImp;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.integration.impl.SyncFootballTeamlistServiceImp;
import com.kok.sport.service.impl.FootballOddsServiceImpl;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisQueryUtil;
import com.kok.sport.utils.db.MybatisUtil;

import cn.hutool.core.net.URLEncoder;

public class UnitTestApi {
	
	
	@Test // /sport/queryPageTrad/cmsdb/league
	public void queryApiControllerSmp() throws Exception {

		ApiControllerSmp c = new ApiControllerSmp();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
	//	reqt.addParameter("dbid", "cmsdb"); // 3380166
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		c.req = reqt;
		c.MybatisMapper1 = MybatisMapper1;
		List<LinkedHashMap> li = (List<LinkedHashMap>) c.query(  "foot_match_v_all" );

		System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	
	@Test // /sport/queryPageTrad/cmsdb/league
	public void queryApiControllerSmp_queryPage() throws Exception {

		ApiControllerSmp c = new ApiControllerSmp();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
	//	reqt.addParameter("dbid", "cmsdb"); // 3380166
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		c.req = reqt;
		c.MybatisMapper1 = MybatisMapper1;
 

		System.out.println(JSON.toJSONString( c.queryPage(   "foot_match_v_all",5,2 )));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}
	
	// /cmsdb/foot_match_v_all_admin/10/1
	@Test // /sport/queryPageTrad/cmsdb/league
	public void queryPageBydbid() throws Exception {

		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
	//	reqt.addParameter("dbid", "cmsdb"); // 3380166
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		c.req = reqt;
		c.MybatisMapper1 = MybatisMapper1;
		List<LinkedHashMap> li = (List<LinkedHashMap>) c.queryPage("cmsdb", "foot_match_v_all_admin", 10, 1 );

		System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}




	@Test // /sport/queryPageTrad/cmsdb/league
	public void crossPcqueryMultiBySp() throws Exception {

		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("dbid", "noveldb"); // 3380166
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		c.req = reqt;
		c.MybatisMapper1 = MybatisMapper1;
		List<LinkedHashMap> li = (List<LinkedHashMap>) c.queryMultiBySp("sportdb", "sp_multitab");

		System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test // /sport/queryPageTrad/cmsdb/league
	public void crossPcReadtab() throws Exception {
//	"2020-05-07"

		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		// reqt.addParameter("from", ); // 3380166
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		c.req = reqt;
		c.MybatisMapper1 = MybatisMapper1;
		List<LinkedHashMap> li = (List<LinkedHashMap>) c.queryNoLmt("cmsdb", "league");

		System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test //
	public void tabRefTest() throws Exception {
//	"2020-05-07"

		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		// reqt.addParameter("from", ); // 3380166
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		c.req = reqt;
		c.MybatisMapper1 = MybatisMapper1;
		List<LinkedHashMap> li = (List<LinkedHashMap>) c.queryNoLmt("sys_tab");

		System.out.println(JSON.toJSONString(li, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test //
	public void jsonQry() throws Exception {
//	"2020-05-07"
		// querySqlMultiRs select 2;
		String sql = "select  data->>'$.dbid' as dbid ,data->>'$.ref_table' ref_table from sys_data";

		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();

		System.out.println(JSON.toJSONString(MybatisMapper1.querySql(sql), true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test //
	public void foot_match_ex_saichen() throws Exception {
//	"2020-05-07"
		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("match_time_date", "2020-05-07"); // 3380166
		// reqt.addParameter("match_time_date$end", ""); // 3380166

		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_ex_saichen", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test //
	public void queryTabExtViewLanjyecyi() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		// reqt.addParameter("match_time_date", "2020-05%$like"); // 3380166
		// reqt.addParameter("match_time_date$end", ""); // 3380166
		reqt.addParameter(MybatisQueryUtil.$subquery, "(select 11 limit 1)as subq");
		reqt.addParameter(MybatisQueryUtil.$OutterSubquery, "(select 22 limit 1)as outq");

		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("sys_tab", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test //
	public void queryPageLike() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("match_time_date", "2020-05%$like"); // 3380166
		// reqt.addParameter("match_time_date$end", ""); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_ex_saichen", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test // start end mode is ok also
	public void queryPageTimeBtweenMode() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("match_time_date", "2020-05-01,2020-05-03$between"); // 3380166
		// reqt.addParameter("match_time_date$end", ""); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_ex_saichen", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void queryPageTimeStartEnd() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("match_time_date$start", "2020-05-01"); // 3380166
		reqt.addParameter("match_time_date$end", "2020-05-03"); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_ex_saichen", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test // and col$in mode also ok
	public void queryPageInAtmde() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("id", "3380166,3379907$in"); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_v_all", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void dbIdTeste() throws Exception {
		ApiControllerTrad c = new ApiControllerTrad();
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("$nocheckSubq", "1"); // 3380166
		reqt.addParameter("$tablequeryInterceptor_disable", "1"); // 3380166

		// reqt.addParameter("$limit", "5"); // 3380166
//		reqt.addParameter("match_time$date_from_unixtime", "2020-05-01");
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("noveldb", "novel_category", 5, 1);
		// Map m=(Map) r;
		System.out.println(JSON.toJSONString(r, true));
	}

	@Test
	public void querypageAtFun() throws Exception {
		ApiControllerTrad c = new ApiControllerTrad();
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("$limit", "5"); // 3380166
		reqt.addParameter("match_time$date_from_unixtime", "2020-05-01");
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_ex_saichen", 5, 1);
		// Map m=(Map) r;
		System.out.println(JSON.toJSONString(r, true));
	}

	@Test
	public void SportApplication_main() throws Exception {
		SportApplication.main(null);
	}

	// queryNoLmt

	@Test
	public void queryNoLmt() throws Exception {
		ApiControllerTrad c = new ApiControllerTrad();
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("$limit", "5"); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryNoLmt("football_event_t");
		// Map m=(Map) r;
		System.out.println(JSON.toJSONString(r, true));
	}

	@Test
	public void queryPageFunIn3() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("id", "in(3380166,3379907)"); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_v_all", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void queryPageFunIn2() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("id$in", "3380166,3379907"); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_v_all", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void queryPageFunIn() throws Exception {

		// querySqlMultiRs select 2;
		ApiControllerTrad c = new ApiControllerTrad();

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("id$in", "3380166,3379907"); // 3380166
		c.req = reqt;
		c.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object r = c.queryPage("foot_match_v_all", 10, 1);
		Map m = (Map) r;
		System.out.println(JSON.toJSONString(m, true));

//	List li=	MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab" );
//	System.out.println(li);
	}

	@Test
	public void querySqlMultiRs2() throws Exception {

		// querySqlMultiRs select 2;

		List li = MybatisUtil.getMybatisMapper().querySqlMultiRs("call sp_multitab('{}')");
		System.out.println(li);
	}

	@Test
	public void querySqlMultiRs() throws Exception {

		// querySqlMultiRs select 2;

		List li = MybatisUtil.getMybatisMapper().querySqlMultiRs("select 1;select 2;");
		System.out.println(li);
	}

	@Test
	public void test_view_sys() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "foot_match_v_ing"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		ApiController ApiController1 = new ApiController();
		ApiController1.req = reqt;
		ApiController1.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object bizSql = ApiController1.queryPage();
		Map li = (Map) bizSql;

		System.out.println(JSON.toJSONString(li, true));
	}

	@Test
	public void test_zhenron() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "foot_zhenron"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.req = reqt;
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object bizSql = ApiControllerForAdv.query();
		List li = (List) bizSql;

		System.out.println(JSON.toJSONString(li, true));
	}

	@Test
	public void foot_detail_data_v() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "foot_detail_data_recent"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.req = reqt;
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object bizSql = ApiControllerForAdv.query();
		List li = (List) bizSql;

		System.out.println(JSON.toJSONString(li, true));
	}

	@Test
	public void queryTest() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "football_odds_t_ex"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.req = reqt;
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		Object bizSql = ApiControllerForAdv.query();
		List li = (List) bizSql;

		System.out.println(JSON.toJSONString(li, true));
	}

	@Test
	public void queryPageOutterSubquery() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "football_odds_t_ex"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		// reqt.addParameter("subquery", " ( select * from football_score_t where
		// match_id={match_id} limit 1 ) as zhudui_scoreObj");
		// reqt.addParameter("subqueryID","keduiSq_matchid");
		// reqt.addParameter("OutterSubquery", " ( select 55 as id ) as 外部子查询");

		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ApiControllerForAdv.req = reqt;
		Object bizSql = ApiControllerForAdv.queryPage();
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		System.out.println(JSON.toJSONString(li, true));
	}

	@Test
	public void queryMulti() throws Exception {
		List li = Lists.newArrayList();
		Map m = Maps.newLinkedHashMap();
		m.put("from", "football_odds_t_ex");
		m.put("page", "1");
		m.put("pagesize", "5");
		Map m2 = Maps.newLinkedHashMap();
		m2.put("from", "foot_odds_tongji");
		li.add(m);
		li.add(m2);
		String jsonString = JSON.toJSONString(li);
		System.out.println(jsonString);
		System.out.println(java.net.URLEncoder.encode(jsonString));
		ApiController ctrl = new ApiController();
		ctrl.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		System.out.println(ctrl.queryMulti(jsonString));
		;

	}

	@Test
	public void queryPageTrad2() throws Exception {
		// try {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		// reqt.addParameter("from", "match_ex"); //3380166
		// reqt.addParameter("$page", "1");
		// reqt.addParameter("$pagesize", "2");
		reqt.addParameter("odds_type", "2");
//			reqt.addParameter("subquery", " ( select * from football_score_t where match_id={match_id} limit 1 ) as zhudui_scoreObj");
//			reqt.addParameter("subqueryID","keduiSq_matchid");
		ApiControllerTrad ctrl = new ApiControllerTrad();
		ctrl.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ctrl.req = reqt;
		Object bizSql = ctrl.queryPage("football_odds_t_ex", 10, 1);
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		System.out.println(JSON.toJSONString(li, true));
//		} catch (Exception e) {
//			System.out.println(e);
//		}

	}

	@Test
	public void queryPageTrad() throws Exception {
		// try {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		// reqt.addParameter("from", "match_ex"); //3380166
		// reqt.addParameter("$page", "1");
		// reqt.addParameter("$pagesize", "2");
		reqt.addParameter("odds_type", "between 1 and 3");
//			reqt.addParameter("subquery", " ( select * from football_score_t where match_id={match_id} limit 1 ) as zhudui_scoreObj");
//			reqt.addParameter("subqueryID","keduiSq_matchid");
		ApiControllerTrad ctrl = new ApiControllerTrad();
		ctrl.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ctrl.req = reqt;
		Object bizSql = ctrl.queryPage("football_odds_t_ex", 10, 1);
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		System.out.println(JSON.toJSONString(li, true));
//		} catch (Exception e) {
//			System.out.println(e);
//		}

	}

	// select 1 ,JSON_OBJECT('id',99,'name','ati') as o;

	@Test
	public void jsonstr2json_fld() throws Exception {
		String sql = "select 1 ,JSON_OBJECT('id',99,'name','ati') as o;";
		List<LinkedHashMap> querySql = MybatisUtil.getMybatisMapper().querySql(sql);
		System.out.println(querySql);

	}

	@Test
	public void queryPageView() throws Exception {
		// try {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "match_ex"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "2");
		reqt.addParameter("where", "id=3380166");
//			reqt.addParameter("subquery", " ( select * from football_score_t where match_id={match_id} limit 1 ) as zhudui_scoreObj");
//			reqt.addParameter("subqueryID","keduiSq_matchid");
		ApiController ctrl = new ApiController();
		ctrl.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ctrl.req = reqt;
		Object bizSql = ctrl.queryPage();
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		System.out.println(JSON.toJSONString(li, true));
//		} catch (Exception e) {
//			System.out.println(e);
//		}

	}

	@Test
	public void queryBizsql() throws Exception {
		System.out.println("where=" + java.net.URLEncoder.encode("match_id=3380166"));

		// http://112.121.163.125:9601/queryPage?from=foot_match_ex&page=1&pagesize=1&where=match_id%20=3380166

		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("$name", "fav_foot_match");
		reqt.addParameter("id", "2644516");
		reqt.addParameter("fav", "y");

		ApiControllerForAdv ApiControllerForAdv1 = new ApiControllerForAdv();
		ApiControllerForAdv1.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		System.out.println(ApiControllerForAdv1.bizSql(reqt));
		;
	}

	@Test
	public void queryPage() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "football_match_t");
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "10");

		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ApiControllerForAdv.req = reqt;
		Object bizSql = ApiControllerForAdv.queryPage();
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		;
	}

	static Logger logger2 = LoggerFactory.getLogger(LogUtil.class);

	@Test
	public void queryPageSubquery() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("from", "foot_bifen"); // 3380166
		reqt.addParameter("page", "1");
		reqt.addParameter("pagesize", "3");
		reqt.addParameter("subquery",
				" ( select * from football_score_t where match_id={match_id} limit 1 ) as zhudui_scoreObj");
		reqt.addParameter("subqueryID", "keduiSq_matchid");
		ApiController ApiControllerForAdv = new ApiController();
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		ApiControllerForAdv.req = reqt;
		Object bizSql = ApiControllerForAdv.queryPage();
		LinkedHashMap li = (LinkedHashMap) bizSql;
		System.out.println(li.get("count").toString());
		System.out.println(JSON.toJSONString(li, true));
	}

	//
//	public String subquery() throws UnsupportedEncodingException {
//		List<Map> li = Lists.newArrayList();
//		LinkedHashMap q = Maps.newLinkedHashMap();
//		q.put("from", "football_score_t");
//		q.put("where", "match_id={0}");
//		q.put("on", "id");
//		q.put("limit", 1);
//		//q.compute("", remappingFunction)
//		q.put("as", "scoreObj");
//	//	q.put("", value)
//		li.add(q);
//		String jsonString = JSON.toJSONString(li);
//		System.out.println(jsonString);
//		logger2.info(jsonString);
//		String encode = java.net.URLEncoder.encode(jsonString,"utf8");
//		System.out.println(encode );
//		LogUtil.log.info(encode);
//		logger2.info(encode);
//		return jsonString;
//	}

	@Test
	public void bizSql() throws Exception {
		MockHttpServletRequest reqt = new MockHttpServletRequest();
		reqt.addParameter("$name", "fav_foot_match");
		reqt.addParameter("id", "3380166");
		reqt.addParameter("fav", "n");

		ApiControllerForAdv ApiControllerForAdv = new ApiControllerForAdv();
		ApiControllerForAdv.MybatisMapper1 = MybatisUtil.getMybatisMapper();
		// assertNotNull(object);
		Object bizSql = ApiControllerForAdv.bizSql(reqt);
		List li = (List) bizSql;
		System.out.println(bizSql);
		;
	}

}
