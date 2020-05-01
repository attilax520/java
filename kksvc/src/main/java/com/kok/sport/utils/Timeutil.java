package com.kok.sport.utils;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

/**
 * java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
String s= "2011-07-09 "; 
Date date =  formatter.parse(s);
 * @author Administrator
 *
 */
public class Timeutil {
	 
	public static void main(String[] args) {
		String time="2'1''55";
		 time="61'";
		 
		System.out.println(toSecs(time));
		
		
	 long timeint_sec_10bit=1585407600;
	  // 获取指定时间Date对象，参数是时间戳，只能精确到秒
     System.out.println(new Date(timeint_sec_10bit*1000));  //Sat Mar 28 23:00:00 CST 2020
     
     for(int i=1;i<=15;i++)
     {
    	 Date d= DateUtils.addDays(new Date(), i);
    	 System.out.println(d);
    	 System.out.println(d.getTime()/1000);
     }
     
     
	}

	/**
	 *    // 精确到毫秒
        // 获取当前时间戳
        System.out.println(System.currentTimeMillis());
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(new Date().getTime());

        // 精确到秒
        // 获取当前时间戳
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(Calendar.getInstance().getTimeInMillis() / 1000);
        System.out.println(new Date().getTime() / 1000);

        // 精确到毫秒
        // 获取指定格式的时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        // 输出字符串
        System.out.println(df.format(new Date()));
        // 获取指定时间Date对象，参数是时间戳，只能精确到秒
        System.out.println(new Date(1510369871));
        df.getCalendar();
     
	 * @param time
	 * @return
	 */
	public static long toSecs(String time) {
		
		//convert hsm mode
		time=time.replaceAll("''", "S");
		time=time.replaceAll("'", "M");
		String t=time;
	    //
		Map m=new HashMap() {{ // parse time str fmt 
			int indexOfMill = t.indexOf("S");
			String timeNoMillsec=t.substring(0,indexOfMill+1);	
			if(indexOfMill==-1) //excpt
				timeNoMillsec=t;
			put("timeNoMillsec",timeNoMillsec);
			String mill=t.substring(indexOfMill+1);
			put("mill",timeNoMillsec);
			
		}};
	
		String timeNoMillsec="PT"+m.get("timeNoMillsec").toString();
		System.out.println(timeNoMillsec);
		//"P1DT1H10M10.5S"
		Duration fromChar1 = Duration.parse(timeNoMillsec);
		
		//process millsec
		try {
			
			fromChar1.plusMillis(Long.parseLong(m.get("mill").toString()));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	 
		return fromChar1.getSeconds();
	}

}
