package com.attilax.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

 

public class timestampUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println(gettimeStamp_millsec());
		 System.out.println(gettimeStamp_millsec_filenameEncode());
		 System.out.println(gettimeStamp_sec());
	}

	public static String gettimeStamp_millsec_filenameEncode() {
	String ts=	gettimeStamp_millsec();
	ts=ts.replace(':', '_');
		return ts;
	}

	public static char[] gettimeStamp_sec() {
		   // 精确到秒
        // 获取当前时间戳
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(Calendar.getInstance().getTimeInMillis() / 1000);
        System.out.println(new Date().getTime() / 1000);

		return null;
	}

	public static String gettimeStamp_millsec() {
		  // 精确到毫秒
        // 获取当前时间戳
//        System.out.println(System.currentTimeMillis());
//        System.out.println(Calendar.getInstance().getTimeInMillis());
//        System.out.println(new Date().getTime());

     
        // 精确到毫秒
        // 获取指定格式的时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        // 输出字符串
        return df.format(new Date());
      //  System.out.println();
//        // 获取指定时间Date对象，参数是时间戳，只能精确到秒
//        System.out.println(new Date(1510369871));
//        df.getCalendar();
//        // 获取指定时间的时间戳
//        try {
//            System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").parse("2017/11/11 11:11:11:111").getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//		return null;
	}

	public static String gettimeStamp_millsec_filenameEncode_nospace() {
		// TODO Auto-generated method stub
		return null;
	}

}
