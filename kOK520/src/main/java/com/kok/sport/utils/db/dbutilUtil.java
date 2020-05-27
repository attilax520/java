package com.kok.sport.utils.db;


//package com.attilax.db;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.kok.sport.utils.MapUtil;

public class dbutilUtil {

	public static Object execSqlQuery(String sql, DataSource datasouce) throws SQLException {
		QueryRunner qr4 = new QueryRunner(datasouce);
		
		List<Map<String, Object>> query = qr4.query(sql, new MapListHandler());
		return query;
	}

	
	public static void convertJsonStr2obj(List<LinkedHashMap> executeSql) {
		try {
			for (LinkedHashMap m : executeSql) {
				LinkedHashMap mCopy=(LinkedHashMap) MapUtil.cloneSafe(m);
				mCopy.forEach(new BiConsumer<Object, Object>() {

					@Override
					public void accept(Object t, Object u) {
						try {
							m.put(t,JsonUtil.tojsonIfjsonstr(u) );
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						 
						
					}
				});
			}
		} catch (Exception e) {

			e.printStackTrace();
			 
		}
		
	}
}
