package com.attilax.net;

import javax.servlet.http.HttpServletRequest;

public class reqUtilV2t33 {
	
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

	public static String getParameter(String string, HttpServletRequest req) {
	   if(req.getParameter(string)==null)
		   return "";
		return req.getParameter(string);
	}

	public static String getParameter(String string, HttpServletRequest req, String defV) {
		  if(req.getParameter(string)==null)
			   return defV;
			return req.getParameter(string);
	}

}
