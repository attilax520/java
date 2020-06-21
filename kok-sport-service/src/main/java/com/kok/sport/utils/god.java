package com.kok.sport.utils;

import com.alibaba.fastjson.JSON;

public class god {

	public static Object getTrace(Exception e) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(e);
	}

}
