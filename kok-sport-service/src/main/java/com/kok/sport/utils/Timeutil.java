package com.kok.sport.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.time.DateUtils;

import lombok.NonNull;

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
		
		System.out.println(toSecs(time));
		 time="61'";
		 
		System.out.println(toSecs(time));
		
		
//	 long timeint_sec_10bit=1585407600;
//	  // 获取指定时间Date对象，参数是时间戳，只能精确到秒
//     System.out.println(new Date(timeint_sec_10bit*1000));  //Sat Mar 28 23:00:00 CST 2020
//     
//     for(int i=1;i<=15;i++)
//     {
//    	 Date d= DateUtils.addDays(new Date(), i);
//    	 System.out.println(d);
//    	 System.out.println(d.getTime()/1000);
//     }
		//20200612
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
//        long times = System.currentTimeMillis();  
//        System.out.println(times);  
//        Date date = new Date(times);  
        String tim = sdf.format(new Date());  
        System.out.println(tim);  
        
//       　DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//       Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GTM+8")); //获取当前时间
//       String date = dateFormat.format(calendar.getTimeInMillis());
        
//        Calendar calendar=Calendar.getInstance();   
//	     calendar.setTime(new Date()); 
//	     System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期 
//	     
	     
        for(int i=0;i<30;i++) {
        	 Calendar calendar=Calendar.getInstance();   
    	     calendar.setTime(new Date()); 
    	//     System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期 
        	    calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-i);//让日期加1  
        	    System.out.println("new date:"+sdf.format(calendar.getTimeInMillis()));//加1之后的日期Top 
        }
 
     
	}

	public static long toSecs(@NotNull @NonNull String time,long def)
	{
		if(time.trim().length()==0)
			return def;
		else
			return toSecs(time);
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
	public static long toSecs(@NotNull @NonNull String time) {
		if(time.trim().length()==0)
			throw new RuntimeException("time str is empty");
		
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
		System.out.println("timeNoMillsec:"+timeNoMillsec);
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
	public static String now( ) {
		//时间格式,HH是24小时制，hh是AM PM12小时制
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//比如timestamp=1449210225945；
	//	long date_temp = Long.valueOf(timestamp1);  
		String date_string = sdf.format(new Date( ));  
	 
		return date_string;
	}
	public static String toStr(long timestamp1) {
		//时间格式,HH是24小时制，hh是AM PM12小时制
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//比如timestamp=1449210225945；
		long date_temp = Long.valueOf(timestamp1);  
		String date_string = sdf.format(new Date(date_temp));  
	 
		return date_string;
	}

}
