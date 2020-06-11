package com.attilax.util;

import com.attilax.web.UrlUtil;

public class paramutil {

	public static String urlparamEncode(String json_s) {
	 
		return  UrlUtil.encode(json_s);
	}

}
