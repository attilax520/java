package com.kok.sport.utils;

public class NumUtil {

	public static Object formatGetInt(Object stt2) {
		java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
		//设置数的小数部分所允许的最大位数，避免小数位被舍掉
						NF.setMaximumFractionDigits(0);
		//设置数的小数部分所允许的最小位数，避免小数位有多余的0
						NF.setMinimumFractionDigits(0);
		//去掉科学计数法显示，避免显示为111,111,111,111
						NF.setGroupingUsed(false);
		//System.out.println(NF.format(a));
		return NF.format(stt2);
	}

}
