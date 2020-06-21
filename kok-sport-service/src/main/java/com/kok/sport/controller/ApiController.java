package com.kok.sport.controller;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
import com.kok.sport.utils.ql.sqlutil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//  
//@Api(value = "KOK看球比赛查询",tags = "KOK看球比赛查询")
//it looks only tags can show 
@Api( value = "数据查询接口api v",tags = "数据查询接口api tag")
@RestController
@SuppressWarnings("all")
public class ApiController {
//	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ApiController.class);
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws Exception {
		
		
		ApiController ApiController1=new ApiController();
		ApiController1.sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
	System.out.println(ApiController1.queryDataStruts("sys_area_t"));	;
		

	}
/**
 *   @ApiImplicitParam(name = "having", value = "分组统计过滤条件", dataType = "string", paramType = "query")
 *    @ApiImplicitParam(name = "limit ", value = "limit模式，限制返回数据条数", dataType = "string", paramType = "query")
 *       @ApiImplicitParam(name = "groupby ", value = "分组统计类参数", dataType = "string", paramType = "query"),
 * @return
 * @throws Exception
 */
	@ApiOperation(value = "翻页查询", notes = "翻页查询的说明note")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "select", value = "要返回的字段集合", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "from", value = "查询数据源，一般是表或视图view,basketball_event_t	篮球赛事表,basketball_match_t	篮球比赛信息表,basketball_odds_t	篮球盘口指数表,basketball_player_t	篮球比赛阵容表 ,basketball_score_t	篮球比赛得分表,basketball_stage_t	篮球比赛阶段表,basketball_stats_t	篮球比赛统计表,basketball_team_player_t	篮球球员表,basketball_team_t	篮球球队表,basketball_tlive_t	篮球比赛文字直播表,football_distribution_t	足球进球分布表,football_environment_t	足球比赛环境表,football_event_t	足球赛事表,football_formation_t	足球比赛阵型表,football_incident_t	比赛发生事件表,football_injury_t	足球比赛伤停情况表,football_league_score_t	足球联赛积分表,football_match_t	足球比赛表,football_odds_t	足球盘口指数表,football_player_incident_t	足球比赛球员事件表,football_score_t	足球比赛得分表,football_stage_t	足球比赛阶段表,football_stats_t	足球比赛统计表,football_team_player_t	足球球队球员表,football_team_t	足球球队表,football_tlive_t	足球比赛文字直播表,kok_match_stream_t	比赛直播数据源表,kok_match_t	比赛信息基础表,match_season_t	赛季信息表,match_stream_t	比赛视频源表,sys_area_t	区域表,sys_country_t	国家表, ", dataType = "string", paramType = "query",required=true,defaultValue = "sys_area_t"),
        @ApiImplicitParam(name = "where", value = "条件过滤", dataType = "string", paramType = "query"),     
        @ApiImplicitParam(name = "orderby ", value = "排序字段", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pagesize", value = "每页条数", dataType = "int", paramType = "query")
       
      
	})
	@GetMapping("/queryPage")
	public Object queryPage() throws Exception {

	//	HttpServletRequest req = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
			Map reqM = RequestUtil.getMap(req);
		return MybatisQueryUtil.queryPage(reqM, MybatisMapper1);

	}
	
	/**
	 * @RequestParam value is paramName,,descrp??
	 * 查询数据源表结构 query table struts
	 * @param datasource
	 * @return
	 * @throws Exception
	 * 
	 * 从报错信息上来看name应该是value的别名，它们两个中只有一个能被允许存在。name其实就是我们在前台页面的表单中input中的name，用来解决前后台传入参数不一致的问题。比如前台通过ajax传入后台的参数名是phone，然而后台想接受的phoneNum并且后台代码都已经写好了（全部改的话比较麻烦），那么我们就可以设置name="phone"，后台代码不需要改动。不过，name的功能是和alue一样的，name完全等价于value，使用哪一个，只是个人习惯问题（切记不可同时使用，推荐使用value）
 
	 */
	@GetMapping("/queryDataStruts")
	 
	@ApiOperation(value = "查询数据源表 字段结构 query table struts", notes = "翻页查询的说明note")
	@ApiImplicitParams({
         @ApiImplicitParam(name = "from", dataType = "string", paramType = "query",required=true,defaultValue = "sys_area_t", value = "查询数据源，一般是表或视图view,basketball_event_t	篮球赛事表,basketball_match_t	篮球比赛信息表,basketball_odds_t	篮球盘口指数表,basketball_player_t	篮球比赛阵容表 ,basketball_score_t	篮球比赛得分表,basketball_stage_t	篮球比赛阶段表,basketball_stats_t	篮球比赛统计表,basketball_team_player_t	篮球球员表,basketball_team_t	篮球球队表,basketball_tlive_t	篮球比赛文字直播表,football_distribution_t	足球进球分布表,football_environment_t	足球比赛环境表,football_event_t	足球赛事表,football_formation_t	足球比赛阵型表,football_incident_t	比赛发生事件表,football_injury_t	足球比赛伤停情况表,football_league_score_t	足球联赛积分表,football_match_t	足球比赛表,football_odds_t	足球盘口指数表,football_player_incident_t	足球比赛球员事件表,football_score_t	足球比赛得分表,football_stage_t	足球比赛阶段表,football_stats_t	足球比赛统计表,football_team_player_t	足球球队球员表,football_team_t	足球球队表,football_tlive_t	足球比赛文字直播表,kok_match_stream_t	比赛直播数据源表,kok_match_t	比赛信息基础表,match_season_t	赛季信息表,match_stream_t	比赛视频源表,sys_area_t	区域表,sys_country_t	国家表, ")
   
      
	})
	public Object queryDataStruts(@RequestParam(  name="from",defaultValue = "sys_area_t")String from) throws Exception {

		String sql="SELECT distinct	c.TABLE_NAME 数据结构名, 	table_comment 含义,  	COLUMN_NAME 字段名, 	COLUMN_COMMENT 备注含义 ,DATA_TYPE 数据类型 	FROM 	information_schema.`COLUMNS` c left join information_schema.`TABLES` t on c.TABLE_NAME=t.TABLE_NAME  and      t.table_schema='dev_kok_sport' WHERE 	c.TABLE_NAME = '{0}' and   c.table_schema='dev_kok_sport'  ";
	 	 sql=sql.replaceAll("\\{0\\}", from);
		//sql=	MessageFormat.format(sql, from);
		logger.info(sql);
		List<LinkedHashMap> querySql = sqlSessionFactory.openSession(true).getMapper(MybatisMapper.class).querySql(sql);
		return querySql;

	}
	
	@ApiOperation(value = "翻页查询，JSon参数", notes = " 说明note")
	@GetMapping("/queryPageJson")
	public Object queryPageJson(String json) throws Exception {
       
		Map reqM = JSON.parseObject(json);
		return MybatisQueryUtil.queryPage(reqM, MybatisMapper1);

	}
	
	
	@ApiOperation(value = "查询条数")
	/**
	 * sum 聚合查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/queryCount")
	public int queryCount() throws Exception {

	//	HttpServletRequest req = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
				Map m = RequestUtil.getMap(req);

		return MybatisQueryUtil.queryCount(m, MybatisMapper1);

	}

	@Autowired
	public HttpServletRequest req;

	@Autowired

 
	public SqlSessionFactory sqlSessionFactory;
	@Autowired
	public
	MybatisMapper MybatisMapper1;

	public Object reqMock;

	// 核心接口
	@ApiOperation(value = "查询，返回数据json列表，不带翻页数据")
	@GetMapping("/query")
	public Object query() throws Exception {
	//	HttpServletRequest req = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		 		Map m = RequestUtil.getMap(req);
		return MybatisQueryUtil.query(m, MybatisMapper1);

	}
	
	public Object queryPage(MockHttpServletRequest reqt) {
		Map reqM = RequestUtil.getMap(reqt);
		return MybatisQueryUtil.queryPage(reqM, MybatisMapper1);
	}
	
	// 核心接口
		@ApiOperation(value = "查询，参数为json模式")
		@GetMapping("/queryJson")
		public Object queryJson(String json) throws Exception {
			Map m = JSON.parseObject(json);
			return MybatisQueryUtil.query(m, MybatisMapper1);

		}

	// 多语句查询 高级接口
	@ApiOperation(value = "多数据集返回查询 高级接口 后端组合")
	@GetMapping("/queryMulti")
	public Object queryMulti(String jsonArray) throws Exception {
	//	String ql = req.getParameter("ql");
		return MybatisQueryUtil.queryMulti(jsonArray,MybatisMapper1);

	}
	
	
	// 多语句查询 高级接口
	@ApiOperation(value = "多数据集返回查询 高级接口 数据库端组合")
	@GetMapping("/sport/queryMultiBySp")
	public Object queryMultiBySp(String name) throws Exception {
	//	String ql = req.getParameter("ql");
		return  	 	MybatisMapper1.querySqlMultiRs("call "+name );

	}





}
