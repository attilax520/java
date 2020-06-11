package com.attilax.util;


import org.apache.log4j.Logger;

//import com.mijie.homi.search.service.index.MoodUserIndexService;
//import com.mijie.homi.search.service.index.moodUserIndexserviceTest;
//import com.mijie.query.fulltxtControllor;


public class securyInt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Logger logger = Logger
	.getLogger(securyInt.class);
	public static int getInt(String string, int i) {
		try {
			int n = Integer.parseInt(string);
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 
	@author attilax 老哇的爪子
		@since  o85 1_54_b$
	
	 * @param object
	 * @param f
	 * @return
	 */
	public static Float getFloat(Object object, float f) {
		if(object==null)return f;
		String s=object.toString().trim();
		s=s.replace(" ", "");
		if( s.length()==0)return f;
		if(object instanceof Integer)
			return Float.parseFloat(s);
	 	if(object instanceof Float)
	 		return Float.parseFloat(s);
	 	if(object instanceof String)
		{
	 	
	 		return Float.parseFloat(s);
		}	
	 	logger.warn(" s" );
		return f;
	}

	public static String getString(Object object) {
		if (object == null)
			return "";
		

		try {
			if(object instanceof String)
			{
				return object.toString();
			}
		} catch (Exception e) {
			logger.warn(god.getTrace(e));

		}

		try {
			return String.valueOf(object);
		} catch (Exception e) {
			logger.warn(god.getTrace(e));

		}

		try {
			return object.toString();
		} catch (Exception e) {
			logger.warn(god.getTrace(e));

		}

		return "";
	}
	
	public static String getString(Object object,String defaultValue) {
		if (object == null)
			return defaultValue;

		try {
			return String.valueOf(object);
		} catch (Exception e) {
			logger.warn(god.getTrace(e));

		}

		try {
			return object.toString();
		} catch (Exception e) {
			logger.warn(god.getTrace(e));

		}

		return defaultValue;
	}

	public static String getString(String[] args, int i) {
		String s = "";
		try {
			s = args[i];
		} catch (Exception e) {
			logger.warn(god.getTrace(e));

		}
		s = s.trim();

		return getString(s);
	}

	public static int getInt(Object object, int i) {
		if(object==null)return i;
		 
		try {
			int n = Integer.parseInt(object.toString());
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			int n = (int) Float.parseFloat(object.toString());
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			
//			int n =  ).intValue();
//			return n;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		
		
		
//		.intValue());
		return i;
	}

	public static boolean getbool(boolean closed, boolean b) {
		// TODO Auto-generated method stub
		return false;
	}

	public static Double getDouble(Object object, int i) {
		try {
			Double d = Double.parseDouble(object.toString());
			d=d/1000;
			return tLog4j.convert(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
return new Double(i);
		// return null;
	}

	public static String getString(String[] sxArr, int i, String string) {
		 try{
			 return sxArr[i];
		 }catch (Exception e) {
			// MoodUserIndexService.logger.warn(god.getTrace(e));
			 return   string;
		}
		  
		
	}

}
