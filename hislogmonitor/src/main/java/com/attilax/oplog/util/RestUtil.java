package com.attilax.oplog.util;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.validator.constraints.URL;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attilax.oplog.OperLogAction;
import com.attilax.util.Global;
import com.attilax.web.requestImp;
import com.attilax.web.sessionImp;

import cn.freeteam.util.MybatisSessionFactory;

@RestController
//@EnableAutoConfiguration
public class RestUtil {

	@RequestMapping("/")
	String home() {
		return "Hello World 22!";
	}

	@RequestMapping("/hello/{myName}")
	String index(@PathVariable String myName, HttpServletRequest request, HttpServletResponse response) {

		return "Hello " + myName + "!!!" + request.getParameter("t");
	}

	@RequestMapping("/restapi")
	public String index2(HttpServletRequest request, HttpServletResponse response) {
		Global.curReq.set(request);
		// 这里填写你允许进行跨域的主机ip
		response.setHeader("Access-Control-Allow-Origin", "*");
		String dsl = request.getParameter("t");
		System.out.println( "--get t param:"+dsl);

		String rzt;
		try {
			Object parsedsl_rzt = ApiUtil.parsedsl(dsl);
		//	if (parsedsl_rzt == null)
			//	throw new NullPointerException(" ret obj is null");
			if (parsedsl_rzt.getClass() == String.class)
				rzt = (String) parsedsl_rzt;
			else	if (parsedsl_rzt.getClass() == Integer.class)
				rzt = (String) parsedsl_rzt;
			else

				rzt = JSON.toJSONString(parsedsl_rzt, true);
		} catch (Exception e) {
			return JSON.toJSONString(e, true);
		}

		return rzt;
	}

}