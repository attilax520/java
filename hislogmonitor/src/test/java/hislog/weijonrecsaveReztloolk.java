package hislog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.json.CDL;
import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.attilax.collection.listBuilder;
import com.attilax.data.csv.csvService;
import com.attilax.util.ExUtil;
import com.attilax.util.jsonuti4fc;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings("all")
public class weijonrecsaveReztloolk {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new weijonrecsaveReztloolk().lookrzt(new testdbService().getConnection());
	}

	

	
	public void lookrzt(Connection conn) {
		String sql = "select top 100 * from vcf1 order by vcf10 desc";
		QueryRunner queryRunner = new QueryRunner(true);
		List<Map<String, Object>> list = null;

		try {
			list = queryRunner.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString = JSON.toJSONString(list, true);
		System.out.println( new csvService(). getCSVString(jsonString));
		// return list;

	}

}
