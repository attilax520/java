package com.attilax.coreLuni.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class jsonuti4fc {
	
	public static void main(String[] args) {
		String f="C:\\0log\\0c90e39f74a1f0fe36d087d558fde429.txt";	String t;
		try {
		 t=FileUtils.readFileToString(new File(f));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		JSONObject jo=JSON.parseObject(t );
		JSONArray ja=jo.getJSONArray("faces");
		for ( Object o : ja) {
			JSONObject jo1=(JSONObject) o;
			JSONObject jsonObject_attr = jo1.getJSONObject("attributes");
			String gender=jsonObject_attr.getJSONObject("gender").getString("value");
			String age=jsonObject_attr.getJSONObject("age").getString("value");
			String beauty_male_score=jsonObject_attr.getJSONObject("beauty").getString("male_score");
			System.out.println( gender + "  "+age +"  "+ beauty_male_score +  "  ");
		//	if()
		}
	}

}
