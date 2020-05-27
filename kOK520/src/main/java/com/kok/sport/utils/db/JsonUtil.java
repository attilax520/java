package com.kok.sport.utils.db;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class JsonUtil {

	public static String prety(Object queryPage) {
		String s = "";
		Map m = (Map) queryPage;
		s = s + "{\r\n";
		Set<String> ks = m.keySet();

		List li = Lists.newArrayList();
		for (String k : ks) {
			li.add("    \"" + k + "\":" + pretyVal(m.get(k)));

		}
		s = s + Joiner.on(",\r\n").join(li);

		s = s + "\r\n}";
		return s;
	}

	private static String pretyVal(Object object) {

		if (object instanceof List)
		{
			//return "";
			 return pretyLi((List<Map>) object);
		}
			

		if (object instanceof Map)
			return JSON.toJSONString(object);

		else if (object instanceof Integer)
			return object.toString();
		else
			return "\"" + object + "\"";
	}

	private static String pretyLi(List<Map> li) {
		String s = "";
		s = s + "[\r\n";
		List li2 = Lists.newArrayList();
		for (Map data_item : li) {
			li2.add("      "+ JSON.toJSONString(data_item) );
			 

		}
		s = s + Joiner.on(",\r\n").join(li2);
		s = s + "\r\n      ]";
		return s;

	}

	public static Object tojsonIfjsonstr(Object u) {
		try {
			 String js=u.toString();
			 return JSON.parse(js);
		} catch (Exception e) {
			return u;
		}
		
		 
	}

//	private static String pretyLiMap(Map data_item) {
//		String s = "";		 
//		s = s + "{\r\n";
//		s = s +JSON.toJSONString(data_item) ;
//		s = s + "}\r\n";
//		return s;
//		
//	}

}
