package com.kok.sport.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.kok.sport.entity.FootballOdds;

import cn.hutool.core.convert.Convert;

public class MapUtil {
	
	public static void main(String[] args) {
		
		
		Map m=Maps.newLinkedHashMap();m.put("aaBB", 11);
		
		
		Map m2 = camel2lowerUnderline(m);
		System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));//test_data
		System.out.println(m2);

	}

	public static Map camel2lowerUnderline(Map m) {
		Map m2=Maps.newLinkedHashMap();
		m.forEach(new BiConsumer<String, Object>() {

			@Override
			public void accept(String t, Object u) {
			 
				try {
					String k2=	CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, t);
					m2.put(k2, u);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
			}
		});
		return m2;
	}

	public static void ConvertFldValToInt(Map MatchItem, String k, int def) {
		MatchItem.put(k, Convert.toInt(MatchItem.get(k), def));
		
	}
	
	public static void putAllNotoveride(Map rec, LinkedHashMap m) {
	//	LinkedHashMap m=(LinkedHashMap) rs.get(0);
		m.forEach(new BiConsumer<String, Object>() {

			@Override
			public void accept(String t, Object u) {
				if(!rec.containsKey(t))
					rec.put(t,u);
				
			}
		} );
	}
	@SuppressWarnings("all")
	public static String execTmplt(String sqltmplt,  Map m) {
		 
		Map<String,String> m2=Maps.newConcurrentMap();
		m2.put("s", sqltmplt);
		m.forEach(new BiConsumer<String, Object>() {

			@Override
			public void accept(String k, Object v) {
				String t=m2.get("s");
				t=t.replaceAll("\\$\\{"+k+"}", v.toString());
				m2.put("s", t);
			}
		});
		
		//remove other numm  siubstr
		String t=m2.get("s");
		String[] keys="join,where,group,HAVIN,order,limit".split(",");
		for (String k : keys) {
			t=t.replaceAll("\\$\\{"+k+"}", "");
		}
	
		return t;
	}
	
	
	//not null safe
	@Deprecated
	public static Map clone(Map reqM) {
		 ConcurrentMap<Object, Object> newConcurrentMap = Maps.newConcurrentMap();
		newConcurrentMap.putAll(reqM);
		return newConcurrentMap;
	}
	
	
	public static LinkedHashMap cloneSafe(Map reqM) {
		LinkedHashMap<Object, Object> newConcurrentMap = Maps.newLinkedHashMap();
		reqM.forEach(new BiConsumer<Object, Object>() {

			@Override
			public void accept(Object t, Object u) {
				//if(u!=null)
				newConcurrentMap.put(t, u);
				
			}
		});
		//newConcurrentMap.putAll(reqM);
		return newConcurrentMap;
	}

	public static Map fromBean(Object footballOdd) {
		String json_str=JSON.toJSONString(footballOdd);
		
		return (Map) JSON.parse(json_str);
	}

}
