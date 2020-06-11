package com.attilax.coreLuni.net;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class respService {

	public static void out(HttpServletResponse response, byte[] bytes) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			out.write( bytes );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	public static void out_astoStr(HttpServletResponse response, Object o) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			out.write( o.toString().getBytes() );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	public static void out_asjson(HttpServletResponse response, List li) {
		out( response,	JSON.toJSONString(li).getBytes());
		
	}


	public static void out_asjson(HttpServletResponse response, Object o) {
		out( response,	JSON.toJSONString(o).getBytes());
		
	}

}
