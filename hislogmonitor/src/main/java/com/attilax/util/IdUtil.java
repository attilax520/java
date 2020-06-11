package com.attilax.util;

import java.text.SimpleDateFormat;

public class IdUtil {
	 public static String getUUid( ) {
		 java.util.Date   dt   =   new   java.util.Date(System.currentTimeMillis());  
	     SimpleDateFormat   fmt   =   new   SimpleDateFormat("MMdd_HHmmss_SSS");  
	     String   fileName=   fmt.format(dt);  
	     return fileName;
	 }
}
