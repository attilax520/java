package com.attilax.core;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

public class MapUtil {

	public static Map parse(String map_strfmt) {
		map_strfmt=map_strfmt.replaceAll(" ，", ",");	
		map_strfmt=map_strfmt.replaceAll("，", ",");	
	 
		map_strfmt=map_strfmt.replaceAll("：", ":");
		String[] a=map_strfmt.split(",");
		Map m=Maps.newConcurrentMap();
		for (String s : a) {
			String[] a2=s.split(":");
		
			m.put(a2[0].trim(), a2[1].trim());
		}
		return m;
	}

	public static void foreachKey(Map m, Consumer<Object> consumer) throws Exception {
		   Set st=m.keySet();
		   for (Object k : st) {
			   consumer.accept(k);
		}
		
	}

}
