package com.attilax.util;

import java.util.Map;

import com.attilax.collection.mapBuilder;
import com.google.common.collect.Maps;



//{driverClass=driverClass, url=url, username=username, password=password}
// {driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver, url=jdbc:sqlserver://192.168.1.17;databaseName, username=sa, password=123456}
//{driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver, url=jdbc:sqlserver://192.168.1.17;databaseName=HealthOne, username=sa, password=123456}


public class csvWzHeadService {
	
	public static void main(String[] args) {
		String s="url=jdbc:sqlserver://192.168.1.17;databaseName=HealthOne,username=sa,password=123456,driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver";
	System.out.println(csvWzHeadService.parseDbcfg(s));
	}

	public static Map parseDbcfg(String cmd) {
		Map m=Maps.newConcurrentMap();
		String[] pair_arr=cmd.split(",");
		for (String pair : pair_arr) {
			pair=pair.trim();
			String[] kva=pair.split("=");
			String k = kva[0].trim();
			if(k.equals("url"))				
				m.put(k, pair.substring(4));
			else
				m.put(k, kva[1].trim());
		}
		return m;
	}

	public static Map parse(String cmd) {
		Map m=Maps.newConcurrentMap();
		String[] pair_arr=cmd.split(",");
		for (String pair : pair_arr) {
			pair=pair.trim();
			String[] kva=pair.split("=");
			m.put(kva[0].trim(), kva[1].trim());
		}
		 
		return m;
	}

}
