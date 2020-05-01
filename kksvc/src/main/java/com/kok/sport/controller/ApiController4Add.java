package com.kok.sport.controller;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import com.kok.sport.utils.db.MysqlInsertUtil;
import com.kok.sport.utils.ql.sqlutil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//  
//@Api(value = "KOK看球比赛查询",tags = "KOK看球比赛查询")
//it looks only tags can show 
@Api(value = "数据添加接口api", tags = "数据添加 接口api")
@RestController
@SuppressWarnings("all")
public class ApiController4Add {
//	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ApiController.class);
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {

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

	@Autowired
	public SqlSessionFactory sqlSessionFactory;
	@Autowired
	public SqlSession conn;

	@Autowired
	public HttpServletRequest req;

	@Autowired
	MybatisMapper MybatisMapper1;

	public Object reqMock;

	// @ApiImplicitParam(name = "select", value = "要返回的字段集合", dataType = "string",
	// paramType = "query"),
	/**
	 * @ApiImplicitParam(name = "where", value = "条件过滤", dataType = "string",
	 *                        paramType = "query"),
	 * @ApiImplicitParam(name = "orderby ", value = "排序字段", dataType = "string",
	 *                        paramType = "query"),
	 * @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType =
	 *                        "query"),
	 * @ApiImplicitParam(name = "pagesize", value = "每页条数", dataType = "int",
	 *                        paramType = "query")
	 * @return
	 * @throws Exception
	 */

	@ApiImplicitParams({

			@ApiImplicitParam(name = "@into", value = "要添加数据源，一般是表或视图view,basketball_event_t	篮球赛事表,basketball_match_t	篮球比赛信息表,basketball_odds_t	篮球盘口指数表,basketball_player_t	篮球比赛阵容表 ,basketball_score_t	篮球比赛得分表,basketball_stage_t	篮球比赛阶段表,basketball_stats_t	篮球比赛统计表,basketball_team_player_t	篮球球员表,basketball_team_t	篮球球队表,basketball_tlive_t	篮球比赛文字直播表,football_distribution_t	足球进球分布表,football_environment_t	足球比赛环境表,football_event_t	足球赛事表,football_formation_t	足球比赛阵型表,football_incident_t	比赛发生事件表,football_injury_t	足球比赛伤停情况表,football_league_score_t	足球联赛积分表,football_match_t	足球比赛表,football_odds_t	足球盘口指数表,football_player_incident_t	足球比赛球员事件表,football_score_t	足球比赛得分表,football_stage_t	足球比赛阶段表,football_stats_t	足球比赛统计表,football_team_player_t	足球球队球员表,football_team_t	足球球队表,football_tlive_t	足球比赛文字直播表,kok_match_stream_t	比赛直播数据源表,kok_match_t	比赛信息基础表,match_season_t	赛季信息表,match_stream_t	比赛视频源表,sys_area_t	区域表,sys_country_t	国家表, ", dataType = "string", paramType = "query", required = true, defaultValue = "sys_area_t"),

	})
	// 核心接口
	@ApiOperation(value = "添加数据")
	@GetMapping("/addData")
	public Object add() throws Exception {
		MysqlInsertUtil.sqlSessionFactory=sqlSessionFactory;
		Map m = RequestUtil.getMap(req);
		return MysqlInsertUtil.insert(sqlSessionFactory, m);

	}

	// 核心接口
	@ApiOperation(value = "参数为json模式")
	@GetMapping("/addDataJson")
	public Object addJson(String json) throws Exception {
		Map m = JSON.parseObject(json);
		return MysqlInsertUtil.insert(sqlSessionFactory, m);

	}

	// 多语句 高级接口
	@ApiOperation(value = "批量添加")
	@GetMapping("/addDataMulti")
	public Object addMulti(String jsonArray) throws Exception {
		// String ql = req.getParameter("ql");
		return MysqlInsertUtil.insertMulti(jsonArray, sqlSessionFactory);

	}

}
