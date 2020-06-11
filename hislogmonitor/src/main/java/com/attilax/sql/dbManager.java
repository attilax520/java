package com.attilax.sql;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.attilax.biz.seo.getConnEx;
import com.attilax.db.DBX;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.json.AtiJson;
import com.attilax.json.JSONArray;
import com.attilax.lang.MapX;
import com.attilax.office.excelUtil;
import com.attilax.web.ReqX;

import aaaCfg.IocX4casher;

/**
 * aaaDbManager.dbManager
 * 
 * @author Administrator
 *
 */
public class dbManager {

	public static void main(String[] args) throws SQLException, getConnEx {
	//	syso"---";
// 	tt2_forTab();
		
//	String s=	new dbManager().exportDefDoc(new HashMap(){{
//			this.put("tableName", "people");this.put("catalog", "atiposdb");
//		}});
//	
//	String s=	new dbManager().exe(new HashMap(){{
//		this.put("tableName", "people");this.put("catalog", "atiposdb");
//		this.put("db", "shopedb");
//	}});
//	System.out.println(s+"=");
		
		
	}
	
	private static void tt2_forTab() throws SQLException, getConnEx {
		DBX dbx = IocX4casher.getBean(DBX.class);
		DatabaseMetaData dbmd = dbx.getConnection().getMetaData();
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
		ResultSet rs =dbmd.getTables("atiposdb", "%", "%", new String[]{"TABLE"});
		  // rs = dbmd.getSchemas();
				//dbmd.getColumns(null, "%", "ecs_users", "%");
		// DatabaseMetaData.getColumns
//		while (rs.next()) {
//			// System.out.println("\t<p code=\""+rs.getString("COLUMN_NAME").toUpperCase()+"\" name=\""+rs.getString("REMARKS")+"\"/>\n");
//			System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释："
//					+ rs.getString("REMARKS") + "\t字段数据类型："
//					+ rs.getString("TYPE_NAME"));
//		}
		
	List li=	dbx.convertList(rs);
	System.out.println(JSONArray.fromObject(li).toString(2)); ;
	}
	
	private static void tt2() throws SQLException, getConnEx {
		DBX dbx = IocX4casher.getBean(DBX.class);
		DatabaseMetaData dbmd = dbx.getConnection().getMetaData();
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
		ResultSet rs = dbmd.getCatalogs();
		  // rs = dbmd.getSchemas();
				//dbmd.getColumns(null, "%", "ecs_users", "%");
		// DatabaseMetaData.getColumns
//		while (rs.next()) {
//			// System.out.println("\t<p code=\""+rs.getString("COLUMN_NAME").toUpperCase()+"\" name=\""+rs.getString("REMARKS")+"\"/>\n");
//			System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释："
//					+ rs.getString("REMARKS") + "\t字段数据类型："
//					+ rs.getString("TYPE_NAME"));
//		}
		
	List li=	dbx.convertList(rs);
	System.out.println(JSONArray.fromObject(li).toString(2)); ;
	}

	private static void tt() throws SQLException, getConnEx {
		DBX dbx = IocX4casher.getBean(DBX.class);
		DatabaseMetaData dbmd = dbx.getConnection().getMetaData();
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
		ResultSet rs = dbmd.getColumns(null, "%", "ecs_users", "%");
		// DatabaseMetaData.getColumns
//		while (rs.next()) {
//			// System.out.println("\t<p code=\""+rs.getString("COLUMN_NAME").toUpperCase()+"\" name=\""+rs.getString("REMARKS")+"\"/>\n");
//			System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释："
//					+ rs.getString("REMARKS") + "\t字段数据类型："
//					+ rs.getString("TYPE_NAME"));
//		}
		
	List li=	dbx.convertList(rs);
	System.out.println(JSONArray.fromObject(li).toString(2)); ;
	}
	public String exportDefDoc(Map req)
	{
		String tab=(String) req.get("table");
		if(req.get("tableName")==null)
		req.put("tableName", tab);
List li=		getColumns_retLi(req);
String outputFilePath = "c:\\"+filex.getUUidName()+".xls";
new excelUtil().toExcel(li, outputFilePath);
	return 	outputFilePath;
	
	}
	
	
	
	public String getTables(Map req) {
		DBX dbx = IocX4casher.getBean(DBX.class);
		DatabaseMetaData dbmd = null;
		try {
			dbmd = dbx.getConnection().getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (getConnEx e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
		ResultSet rs = null;
		try {
			  String string =(String) req.get("schema");
			rs =dbmd.getTables(string, "%", "%", new String[]{"TABLE"});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List li = null;
		try {
			li = dbx.convertList(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (JSONArray.fromObject(li).toString(2));
	}

	public String getCatalogs(Map req) {
		DBX dbx = IocX4casher.getBean(DBX.class);
		DatabaseMetaData dbmd = null;
		try {
			dbmd = dbx.getConnection().getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (getConnEx e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
		ResultSet rs = null;
		try {
			rs = dbmd.getCatalogs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List li = null;
		try {
			li = dbx.convertList(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (JSONArray.fromObject(li).toString(2));
	}

	@SuppressWarnings("all")
	public String getColumns(Object reqx) {
		
		try {
		
			Map req = null;

			if (reqx instanceof HttpServletRequest)
				req = ReqX.toMap((HttpServletRequest) reqx);
			else
				req=(Map) reqx;
			List li = getColumns_retLi(req);
			// //DatabaseMetaData.getColumns
			// try {
			// while(rs.next()){
			// //
			// System.out.println("\t<p code=\""+rs.getString("COLUMN_NAME").toUpperCase()+"\" name=\""+rs.getString("REMARKS")+"\"/>\n");
			// System.out.println("字段名："+rs.getString("COLUMN_NAME")+"\t字段注释："+rs.getString("REMARKS")+"\t字段数据类型："+rs.getString("TYPE_NAME"));
			// }
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			return JSONArray.fromObject(li).toString(2);
		} catch (Exception e) {
			e.printStackTrace();
			return  AtiJson.toJson(e);
		}
		

	}
	private List getColEx(Map reqx)
	{
		
		List<Map> li=new ArrayList<Map>();
		Map req = null;

		if (reqx instanceof HttpServletRequest)
			req = ReqX.toMap((HttpServletRequest) reqx);
		else
			req=(Map) reqx;
		String  db=(String) reqx.get("db");
		String table=(String) reqx.get("table");
		String dir=pathx.appPath()+"/info_schema\\columns\\"+db+"\\"+table+"";
		File f=new File(dir);
		File[] a=f.listFiles();
		for (File file : a) {
			String t=filex.read(file.getAbsolutePath());
			Map m=MapX.from(t);
			li.add(m);
		}
		
		return li;
		
	}
	

	
	
	
	
	


	
	 //  rst = con.getMetaData().getPrimaryKeys(null,null,"USER");

	private List getColumns_retLi(Map req) {
		DBX dbx = IocX4casher.getBean(DBX.class);
		DatabaseMetaData dbmd = null;
	 
		try {
			dbmd = dbx.getConnection().getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (getConnEx e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
String catalog="";
if( req.get("catalog")==null)
	catalog=(String) req.get("db");
else
	catalog =(String) req.get("catalog");
		ResultSet rs = null;
		try {
			rs = dbmd.getColumns(catalog, "%", req.get("tableName").toString(), "%");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List li = null;
		try {
			li = dbx.convertList(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return li;
	}

}
