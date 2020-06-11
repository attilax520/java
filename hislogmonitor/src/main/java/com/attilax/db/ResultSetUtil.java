package com.attilax.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.attilax.util.ExUtil;

public class ResultSetUtil {

	public static List<Map > toList(ResultSet rs)   {
		List<Map> list = new ArrayList<Map >();  
		try {
		
		   ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据  
	        int columnCount;
			
				columnCount = md.getColumnCount();
			 //获得列数   
	        while (rs.next()) {  
	            Map<String,Object> rowData = new HashMap<String,Object>();  
	            for (int i = 1; i <= columnCount; i++) {  
	                rowData.put(md.getColumnName(i), rs.getObject(i));  
	            }  
	            list.add(rowData);  
	  
	        }  
			} catch (SQLException e) {
			ExUtil.throwExV2(e);
			}  
		return list;
	}

}
