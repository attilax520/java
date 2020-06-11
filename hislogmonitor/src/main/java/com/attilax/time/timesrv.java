package com.attilax.time;

import org.apache.commons.lang3.StringUtils;

public class timesrv {

	public static String leftPad0(String stratday_mon) {
		// TODO Auto-generated method stub
		 String mon_mm = StringUtils.leftPad(stratday_mon, 2, '0');;
		 return mon_mm;
	}

}
