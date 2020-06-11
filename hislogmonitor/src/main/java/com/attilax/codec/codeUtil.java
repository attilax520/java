package com.attilax.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class codeUtil {

	public static String paramDecode_comma(String cur_token) {
		 
		try {
			return URLDecoder.decode(cur_token, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cur_token;
	}

}
