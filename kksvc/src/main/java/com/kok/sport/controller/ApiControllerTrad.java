package com.kok.sport.controller;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.base.exception.ApplicationException;
import com.kok.base.utils.Result;
import com.kok.base.vo.PageVo;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.RequestUtil;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.db.MybatisQueryUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.sqlutil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//  
//@Api(value = "KOK看球比赛查询",tags = "KOK看球比赛查询")
//it looks only tags can show 
@Api(value = "数据查询接口api v", tags = "数据查询接口api 条件参数ux提升版本 简化where参数")
@RestController
@SuppressWarnings("all")
public class ApiControllerTrad {
//	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ApiController.class);
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws Exception {

		LinkedHashMap m = Maps.newLinkedHashMap();
		m.put("timex_start@", "2008");
		m.put("timex_end@", "20010");
	//	System.out.println(new ApiControllerTrad().getWhere(m));

//		ApiControllerTrad ApiController1=new ApiControllerTrad();
//		ApiController1.sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
//	System.out.println(ApiController1.queryDataStruts("sys_area_t"));	;

	}

	/// sport/queryPageTrad/
	@ApiOperation(value = "查询", notes = "查询无翻页")
	@GetMapping("/sport/queryPageTrad/{fromParam}")
	public Object queryNoLmt(@PathVariable(name = "fromParam") String from) throws Exception {
		LinkedHashMap m = Maps.newLinkedHashMap();

		Map reqM = RequestUtil.getMap(req);

		m.put("from", from);
		if (reqM.get("@orderby") != null)
			m.put("orderby", reqM.get("@orderby"));
//		 if(reqM.get("@page")!=null)
// 		 m.put("page", page);
//		 if(reqM.get("@pagesize")!=null)
// 		 m.put("pagesize", pagesize);
		if (reqM.get("@select") != null)
			m.put("select", reqM.get("@select"));
		if (reqM.get("@limit") != null)
			m.put("limit", reqM.get("@limit"));

		MybatisQueryUtil.processWhere(m, reqM);
		return MybatisQueryUtil.queryNoLmt(m, MybatisMapper1);
	}


	/**
	 * @ApiImplicitParam(name = "having", value = "分组统计过滤条件", dataType = "string",
	 *                        paramType = "query")
	 * @ApiImplicitParam(name = "limit ", value = "limit模式，限制返回数据条数", dataType =
	 *                        "string", paramType = "query")
	 * @ApiImplicitParam(name = "groupby ", value = "分组统计类参数", dataType = "string",
	 *                        paramType = "query"),
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "翻页查询", notes = "翻页查询的说明note")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "@select", value = "要返回的字段集合", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "from", value = "查询数据源，一般是表或视图view,basketball_event_t	篮球赛事表,basketball_match_t	篮球比赛信息表,basketball_odds_t	篮球盘口指数表,basketball_player_t	篮球比赛阵容表 ,basketball_score_t	篮球比赛得分表,basketball_stage_t	篮球比赛阶段表,basketball_stats_t	篮球比赛统计表,basketball_team_player_t	篮球球员表,basketball_team_t	篮球球队表,basketball_tlive_t	篮球比赛文字直播表,football_distribution_t	足球进球分布表,football_environment_t	足球比赛环境表,football_event_t	足球赛事表,football_formation_t	足球比赛阵型表,football_incident_t	比赛发生事件表,football_injury_t	足球比赛伤停情况表,football_league_score_t	足球联赛积分表,football_match_t	足球比赛表,football_odds_t	足球盘口指数表,football_player_incident_t	足球比赛球员事件表,football_score_t	足球比赛得分表,football_stage_t	足球比赛阶段表,football_stats_t	足球比赛统计表,football_team_player_t	足球球队球员表,football_team_t	足球球队表,football_tlive_t	足球比赛文字直播表,kok_match_stream_t	比赛直播数据源表,kok_match_t	比赛信息基础表,match_season_t	赛季信息表,match_stream_t	比赛视频源表,sys_area_t	区域表,sys_country_t	国家表, ", dataType = "string", paramType = "query", required = true, defaultValue = "sys_area_t"),
			@ApiImplicitParam(name = "where", value = "条件过滤", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "@orderby", value = "排序字段", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "@page", value = "页数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "@pagesize", value = "每页条数", dataType = "int", paramType = "query")

	})

	// http://112.121.163.125:9601/queryPageTrad/football_odds_t_ex?@page=1&@pagesize=10&odds_type=1
	@GetMapping("/sport/queryPageTrad/{fromParam}/{pagesizePrm}/{pagePrm}")
	public Object queryPage(@PathVariable(name = "fromParam") String from,
			@PathVariable(name = "pagesizePrm") int pagesize, @PathVariable(name = "pagePrm") int page)
			throws Exception {

		try {
			res.addHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Content-Type", "application/json;charset=UTF-8");

		} catch (Exception e) {
			// logger.info("",e);
		}
		LinkedHashMap m = Maps.newLinkedHashMap();

		Map reqM = RequestUtil.getMap(req);
	 
		m.put("from", from);
		if (reqM.get("@orderby") != null)
			m.put("orderby", reqM.get("@orderby"));
//		 if(reqM.get("@page")!=null)
		m.put("page", page);
//		 if(reqM.get("@pagesize")!=null)
		m.put("pagesize", pagesize);
		if (reqM.get("@select") != null)
			m.put("select", reqM.get("@select"));

		MybatisQueryUtil.processWhere(m, reqM);

		// reqM.remove("@select"); reqM.remove("@orderby"); reqM.remove("@page");
		// reqM.remove("@pagesize");

		return MybatisQueryUtil.queryPage(m, MybatisMapper1);

	}
	
	
	
	@Autowired
	public HttpServletResponse res;

	@Autowired
	public HttpServletRequest req;

	@Autowired

	public SqlSessionFactory sqlSessionFactory;
	@Autowired
	public MybatisMapper MybatisMapper1;

	public Object reqMock;

}
