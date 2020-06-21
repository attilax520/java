package com.kok.sport.controller;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
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
import lombok.NonNull;

//  
//@Api(value = "KOK看球比赛查询",tags = "KOK看球比赛查询")
//it looks only tags can show 
@Api(value = "数据查询接口api v", tags = "数据查询接口api 条件参数ux提升版本 简化where参数")
@RestController
@SuppressWarnings("all")
public class ApiControllerSmp {
//	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ApiController.class);
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws Exception {

		LinkedHashMap m = Maps.newLinkedHashMap();
		m.put("timex_start@", "2008");
		m.put("timex_end@", "20010");
		// System.out.println(new ApiControllerTrad().getWhere(m));

//		ApiControllerTrad ApiController1=new ApiControllerTrad();
//		ApiController1.sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
//	System.out.println(ApiController1.queryDataStruts("sys_area_t"));	;

	}

	/// sport/queryPageTrad/
	@ApiOperation(value = "查询无翻页", notes = "查询无翻页")
	@GetMapping("/sport/ApiControllerSmpQuery/{fromParam}")
	public Object query(@PathVariable(name = "fromParam") String from) throws Exception {
		LinkedHashMap m = Maps.newLinkedHashMap();

		Map reqM = RequestUtil.getMap(req);
		String where = getWhere(reqM);
		String sql = "select * from " + from + "  where 1=1 " + where;
		logger.info(sql);
		return MybatisMapper1.querySql(sql);
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

	})

	// http://localhost:9601/sport/ApiControllerSmpQueryPage/football_odds_t_ex/5/1
	// http://112.121.163.125:9601/queryPageSmp/football_odds_t_ex/5/1
	@GetMapping("/sport/ApiControllerSmpQueryPage/{fromParam}/{pagesizePrm}/{pagePrm}")
	public Object queryPage(@PathVariable(name = "fromParam") String from,
			@PathVariable(name = "pagesizePrm") int pagesize, @PathVariable(name = "pagePrm") int page)
			throws Exception {
		setCrossdoainRes();
		 
		String where = getWhere(RequestUtil.getMap(req));
		String sqlCount = "select count(*) as cnt from " + from + " where 1=1 " + where;

		String sql = "select * from " + from + " where 1=1 " + where + "   limit " + pagesize + " offset  "
				+ pagesize * (page - 1);
		logger.info(sql);

		LinkedHashMap rztMap = Maps.newLinkedHashMap();
		rztMap.put("count", MybatisUtil.query(sqlCount).get(0).get("cnt"));
		rztMap.put("pageCount", Util.getPageCount(rztMap.get("count"), pagesize));
		rztMap.put("pagesize", pagesize);
		rztMap.put("page", page);
		rztMap.put("data", MybatisUtil.query(sql));

		return rztMap;

	}

	private void setCrossdoainRes() {
		try {
			res.addHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Content-Type", "application/json;charset=UTF-8");

		} catch (Exception e) {
			// logger.info("",e);
		}
	}

	public static String getWhere(Map reqM) {
		// LinkedHashMap<String , String> m=Maps.newLinkedHashMap();
		List<String> li = Lists.newArrayList();
		reqM.forEach(new BiConsumer<String, String>() {

			@Override
			public void accept(String t, String u) {
				String colTrim = t.trim();

				li.add(t + "='" + u + "'");
			}

		});

		String join = Joiner.on(" and ").join(li);
		if (join.trim().length() > 0)
			return " and " + join;
		else
			return join;// empty
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
