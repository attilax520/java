package com.kok.sport.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.mock.web.MockHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.controller.ApiControllerForAdv;
import com.kok.sport.utils.db.MybatisUtil;

import cn.hutool.core.net.URLEncoder;

public class BziUtil {

	public static void main(String[] args) throws Exception {
		
	int hours = new Date().getHours();
	System.out.println(hours);	
//		long time2 = 1516197600 * 1000;
//		time2 = 1516125600 * 24 * 3600;
//		long time = (long) 1586591592 * (long) 1000;
//		// 1586591592045 //ms
//		System.out.println(new Date().getTime()); // gettime ms timestamp 13bit ms
//		// 毫秒级时间戳
//		long currentTimeMillis = System.currentTimeMillis();
//		System.out.println(currentTimeMillis);
//
//		String result2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
//		System.out.println(" " + result2);
//
//		SqlSession SqlSession1 = MybatisUtil.getConn();
//		MybatisMapper MybatisMapper = SqlSession1.getMapper(MybatisMapper.class);
//		Object bizSql = BziUtil.bizSql("spj", "{\"id\":33}", MybatisMapper);
//		//  '{"id":10000,"fav":"n"}'
	//	System.out.println(bizSql);
	
		
		
	

	}

	public static Object bizSql(String name, String json, MybatisMapper MybatisMapper1) {
		Map m = Maps.newConcurrentMap();
		m.put("call", name);
		m.put("jsonparam", json);

		return MybatisMapper1.call(m);
	}
	
	
	public static Object bizSqlV2(String name, String json, MybatisMapper MybatisMapper1) {
		Map m = Maps.newConcurrentMap();
		m.put("call", name);
		m.put("jsonparam", json);

		return MybatisMapper1.call(m);
	}

	public static Object bizScript(String type, String name, Map m) {
		String s = JSON.toJSONString(m);
		String urlencode = java.net.URLEncoder.encode(s);
		try {
			return Runtime.getRuntime().exec(type + " " + name + " " + urlencode);
		} catch (IOException e) {
			ExUtilV2t33.throwExV2(e);
		}
		return type + " " + name + m;
	}

}
