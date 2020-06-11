package com.attilax.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtil {

	public static String encode(String json_s) {
	 
		try {
			return URLEncoder.encode(json_s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json_s;
	}

}
