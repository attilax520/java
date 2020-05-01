package com.kok.sport.controller;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.kok.SportApplication;
import com.kok.base.exception.ApplicationException;
import com.kok.base.utils.Result;
import com.kok.base.vo.PageVo;
import com.kok.sport.utils.BziUtil;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.RequestUtil;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.WsSrv;
import com.kok.sport.utils.db.JsonUtil;
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
@Api(value = "数据查询接口api", tags = "复杂业务逻辑api")
@RestController
@SuppressWarnings("all")
public class ApiControllerForAdv {
//	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ApiController.class);
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {

	}

	@Autowired
	public HttpServletRequest req;

	@Autowired

	public SqlSessionFactory sqlSessionFactory;
	@Autowired
	public	MybatisMapper MybatisMapper1;

	public Object reqMock;

	// 核心接口
	@ApiOperation(value = "调用服务端sql脚本，参数为json模式")
	@GetMapping("/bizSqlJson")
	public Object bizSqlJson(@ApiParam(name = "name", value = "业务脚本名称") String name,
			@ApiParam(name = "json", value = "参数，json格式") String json) throws Exception {
		//Map m = JSON.parseObject(json);
		return BziUtil.bizSql(name, json,MybatisMapper1);

	}
	
	@ApiOperation(value = "调用notifyWebsocket通知")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "@name", value = "脚本名称", dataType = "string", paramType = "query"),      
      
	})
	@GetMapping("/sport/notifyWebsocket")
	public Object notifyWebsocket( 
			  String notifyMsg) throws Exception {
		
		LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
		m.put("WsSrv.conns_li.size()", WsSrv.conns_li.size());
		m.put("notifyMsg", JsonUtil.tojsonIfjsonstr(notifyMsg) );
		WsSrv.conns_li.forEach(new Consumer<WebSocket>() {

			@Override
			public void accept(WebSocket t) {
				try {
//					LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
//					m.put("from", "foot_tlive_notify");
//				 
//					MybatisMapper mybatisMapper1;
//					if (SportApplication.context != null) {
//						mybatisMapper1 = SportApplication.context.getBean(MybatisMapper.class);
//						
//					} else {
//						mybatisMapper1=MybatisUtil.getMybatisMapper();
//						
//					}
//					Object r = MybatisQueryUtil.queryPage(m, mybatisMapper1);
//
//					String jsonString = JSON.toJSONString(r, true);
					t.send(notifyMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			}
		});return m;
	//	return JSON.toJSONString(m, true);

	}


	@ApiOperation(value = "调用服务端sql脚本")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "@name", value = "脚本名称", dataType = "string", paramType = "query"),      
      
	})
	@GetMapping("/bizSql")
	public Object bizSql( 
			@ApiParam(name = "req", value = "参数") HttpServletRequest req) throws Exception {
		Map m = RequestUtil.getMap(req);
		 String json=JSON.toJSONString(m);
		return BziUtil.bizSql(m.get("@name").toString(), json,MybatisMapper1);

	}

	@ApiOperation(value = "调用mybatis业务模式")
	@GetMapping("/bizMybatis")
	public Object bizMybatis(@ApiParam(name = "name", value = "mybatis业务语句名称id") String name,
			@ApiParam(name = "req", value = "参数") HttpServletRequest req) throws Exception {
		Map m = RequestUtil.getMap(req);
		return  sqlSessionFactory.openSession(true).selectList(name);

	}
	
	
	@ApiOperation(value = "调用其他脚本")
	@GetMapping("/bizScript")
	public Object bizScript(@ApiParam(name = "type", value = "脚本类型，shell,python,java,nodejs,php,sql") String type,
			@ApiParam(name = "name", value = "脚本路径") String name,			
			@ApiParam(name = "req", value = "参数") HttpServletRequest req) throws Exception {
		Map m = RequestUtil.getMap(req);
		return BziUtil.bizScript(type,name,m);

	}

	// 多语句查询 高级接口
//	@ApiOperation(value = "多数据集返回查询 高级接口")
//	@GetMapping("/queryMulti")
//	public Object queryMulti(String jsonArray) throws Exception {
//	//	String ql = req.getParameter("ql");
//		return MybatisQueryUtil.queryMulti(jsonArray,MybatisMapper1);
//
//	}

}
