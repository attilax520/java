package com.kok.sport.utils;



import java.util.List;

//package com.attilax.text;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class regFind {

	public static void main(String[] args) {

		String str = " select  * from tab1 where col1=${col1}  and c2= {c2out}";
//		Map m = Maps.newConcurrentMap();
//		m.put("col1", 3333);
//		System.out.println(new regFind().parse(str, m));
		System.out.println(SqlParam(str));
		// \$\{.*\}
		// t(str);
	}

	@SuppressWarnings("unchecked")
	private String parse(String str, Map m) {
		Map m2 = Maps.newConcurrentMap();

		m.forEach(new BiConsumer<String, Object>() {

			@Override
			public void accept(String k, Object v) {

				String s = str.replaceAll("\\$\\{" + k + "\\}", v.toString());
				m2.put("rzt", s);
			}
		});
		return (String) m2.get("rzt");
	}

	public static List<String> SqlParam(String str) {
		List<String> li=Lists.newArrayList();
		String regEx = "\\{" + "(.*?)" + "\\}";
		Pattern Pattern1 = Pattern.compile(regEx);
		Matcher Matcher1 = Pattern1.matcher(str);
		// boolean bool = Pattern.matches("\\w+","hello abc");

		while (Matcher1.find()) {
			li.add(Matcher1.group(1));
			System.out.println(JSON.toJSONString(Matcher1));
			// group(0)或group()将会返回整个匹配的字符串（完全匹配）；group(i)则会返回与分组i匹配的字符
//			String group1_str = Matcher1.group(); // defalt is 0
//			String group2_str = Matcher1.group(1);
//			System.out.println(group1_str);
//			System.out.println(group2_str);
//
//			System.out.println(str.replace(group2_str, ""));
//
//			System.out.println("start:" + Matcher1.start() + " end:" + Matcher1.end());

		}
		return li;
	}
	
	//		String str = " select  * from tab1 where col1=${col1} ";
	private static void t(String str) {
		String regEx = "\\$\\{" + "(.*?)" + "\\}";
		Pattern Pattern1 = Pattern.compile(regEx);
		Matcher Matcher1 = Pattern1.matcher(str);
		// boolean bool = Pattern.matches("\\w+","hello abc");

		while (Matcher1.find()) {
			System.out.println(JSON.toJSONString(Matcher1));
			// group(0)或group()将会返回整个匹配的字符串（完全匹配）；group(i)则会返回与分组i匹配的字符
			String group1_str = Matcher1.group(); // defalt is 0
			String group2_str = Matcher1.group(1);
			System.out.println(group1_str);
			System.out.println(group2_str);

			System.out.println(str.replace(group2_str, ""));

			System.out.println("start:" + Matcher1.start() + " end:" + Matcher1.end());

		}
	}

}
