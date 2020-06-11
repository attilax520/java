package com.attilax.net;

import javax.servlet.http.HttpServletRequest;

public class reqUtil {
	
	public static void main(String[] args) {
		System.out.println("aa");
	}
//
	
	@Deprecated
	public static boolean hasOption(String string) {
		 if(string==null)
		return false;
		 else
			 return false;
	}

	public static String getOptionValue(String string, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(string).trim();
	}

	public static boolean hasOption(String string, HttpServletRequest request) {
		try {
			string=request.getParameter(string).trim();
		} catch (Exception e) {
			string=null;
		}
	
		 if(string==null)
				return false;
				 else
					 return true;
	 
	}

}
