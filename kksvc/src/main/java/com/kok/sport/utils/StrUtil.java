package com.kok.sport.utils;

import org.apache.commons.lang3.StringUtils;

public class StrUtil {
	
	public static void main(String[] args) {
		System.out.println( String_length("赛区id"));
		System.out.println( String_length("中文"));
		System.out.println( rightPad("赛区id",20)+"ff");
		System.out.println( rightPad("中文",20)+"ff");
	}
	
	public static int String_length(String value) {
		  int valueLength = 0;
		  String chinese = "[\u4e00-\u9fa5]";
		  for (int i = 0; i < value.length(); i++) {
		   String temp = value.substring(i, i + 1);
		   if (temp.matches(chinese)) {
		    valueLength += 2;
		   } else {
		    valueLength += 1;
		   }
		  }
		  return valueLength;
		 }

	public static String rightPad(String s, int cellwidth) {
int len=String_length(s);
int padlen=cellwidth-len;
String pad=StringUtils.leftPad("", padlen);
		return s+pad;
	}
		 

}
