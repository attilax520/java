package com.attilax.love;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.TimerService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.attilax.time.timesrv;
import com.google.common.collect.Maps;

public class lovepubdayGener {

	public static void main(String[] args) throws IOException {
		// String
		// days="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\love\\fedays.txt";
	//	geneLovday();
		geneOtherday();
	}

	private static void geneOtherday() throws IOException {
		String days = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\love\\otherdays.txt";
		String tmpfile = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\love\\baiduechart_everiverTmplt.txt";
		String tmp = FileUtils.readFileToString(new File(tmpfile));
		List<String> days_li = FileUtils.readLines(new File(days));
		int n = 0;
		for (String dayname : days_li) {
			n++;
			if(n>200)
				break;
			if(dayname.trim().length()==0)
				continue;
			
			String dayname2=dayname.split(",")[0].trim();
			Map span=getspan(  dayname.split(",")[1].trim());
			String tmp2 = tmp;
			tmp2 = tmp2.replaceAll("@evtname@", dayname2.trim());
			String mon_mm = StringUtils.leftPad(String.valueOf(n), 2, '0');
			tmp2 = tmp2.replaceAll("@mon1@", (String) span.get("m1"));
			tmp2 = tmp2.replaceAll("@startday@", (String) span.get("d1"));
			tmp2 = tmp2.replaceAll("@mon2@", (String) span.get("m2"));
			tmp2 = tmp2.replaceAll("@endday@", (String) span.get("d2"));
			System.out.println(tmp2 + ",");
		}
		
	}

	private static Map getspan(String trim) {
		trim=trim.trim();
		int spanidx=trim.indexOf("to");
		String stratday=trim.trim().substring(0, spanidx);
		String endday=trim.substring(spanidx+2,trim.length());
		String stratday_mon=stratday.split("-")[0];
		String stratday_day=stratday.split("-")[1];
		String endday_mon=endday.split("-")[0];
		String endday_day=endday.split("-")[1];
		stratday_mon=timesrv.leftPad0(stratday_mon);
		stratday_day =timesrv.leftPad0(stratday_day);
		
		endday_mon=timesrv.leftPad0(endday_mon);
		endday_day =timesrv.leftPad0(endday_day);
		Map m=Maps.newConcurrentMap();
		m.put("m1", stratday_mon);m.put("d1", stratday_day);m.put("m2", endday_mon);m.put("d2", endday_day);
		return m;
	}

	private static void geneLovday() throws IOException {
		String days = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\love\\otherdays.txt";
		String tmpfile = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\love\\baiduechart_everiverTmplt.txt";
		String tmp = FileUtils.readFileToString(new File(tmpfile));
		List<String> days_li = FileUtils.readLines(new File(days));
		int n = 0;
		for (String dayname : days_li) {
			n++;
			String tmp2 = tmp;
			tmp2 = tmp2.replaceAll("@evtname@", dayname);
			String mon_mm = StringUtils.leftPad(String.valueOf(n), 2, '0');
			tmp2 = tmp2.replaceAll("@mon@", mon_mm);
			System.out.println(tmp2 + ",");
		}
	}

}
