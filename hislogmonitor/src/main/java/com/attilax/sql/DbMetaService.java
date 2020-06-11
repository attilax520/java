package com.attilax.sql;

 

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.attilax.collection.SetX;
import com.attilax.db.DBX;

//import aaaCfg.IocX4casher;
//import aaaCfg.IocX4nodb;
// r324 import aaaDbManager.SetX;
//r324 import aaaDbManager.dbManager;

//import com.attilax.collection.SetX;
//import com.attilax.db.DBX;
//import com.attilax.db.MysqlDMLSql;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.google.common.collect.Maps;
//import com.attilax.ioc.IocUtilV2;
//import com.attilax.json.AtiJson;
//import com.attilax.lang.MapX;
//import com.attilax.web.ReqX;
//import com.google.common.collect.Maps;
//import com.google.inject.Inject;
@SuppressWarnings("all")
//extends dbManager
public class DbMetaService  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("prj","jobus");
//		DbMetaService c =IocUtilV2.getBean(DbMetaService.class);
//				//IocX4nodb.getBean(DbMetaService.class);
//		
//	
//		Map req=Maps.newLinkedHashMap();
//		req.put("$tb", "sid0");
//		System.out.println( AtiJson.toJson(   c.getColumns(req))   );
//
//		//IocX4nodb.db.set("shopedb");
////		System.out.println(AtiJson.toJson(c.getPrimaryKeys(new HashMap() {
////			{
////				this.put("db", IocX4nodb.db.get());
////				this.put("table", "底单申请表");
////
////			}
////		})));

	}

//	protected List<Map> getColEx(Object reqx) {
//
//		List<Map> li = new ArrayList<Map>();
//		Map req = null;
//
//		if (reqx instanceof HttpServletRequest)
//			req = ReqX.toMap((HttpServletRequest) reqx);
//		else
//			req = (Map) reqx;
//		String db = (String) req.get("db");
//		String table = (String) req.get("table");
//		String dir = pathx.appPath() + "/info_schema\\columns\\" + db + "\\"
//				+ table + "";
//		File f = new File(dir);
//		File[] a = f.listFiles();
//		for (File file : a) {
//			String t = filex.read(file.getAbsolutePath());
//			Map m = MapX.from(t);
//			li.add(m);
//		}
//
//		return li;
//
//	}
//
//	@Inject
//	DBX dbx;

	/**
	 * { "KEY_SEQ": 1, "TABLE_NAME": "底单申请表", "COLUMN_NAME": "id", "PK_NAME":
	 * "PRIMARY", "TABLE_CAT": "shopedb" }
	 * 
	 * @param req
	 * @return
	 */
//	public List getPrimaryKeys(Map req) {
//		Object tab = getTableName(req);
//		return getPrimaryKeys(req, tab);
//	}

//	private List getPrimaryKeys(Map req, Object tab) {
//		// DBX dbx = IocX4casher.getBean(DBX.class);
//		java.sql.DatabaseMetaData dbmd = null;
//	
//		try {
//			try {
//				dbmd = dbx.getConnection().getMetaData();
//			} catch (com.attilax.db.getConnEx e) {//r44 fix
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new RuntimeException(e);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		// databaseMetaData.getColumns(localCatalog, localSchema,
//		// localTableName, null);
//		String catalog = (String) req.get("db");
//		ResultSet rs = null;
//		try {
//		
//			
//			rs = dbmd.getPrimaryKeys(catalog, "%", tab.toString());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		List li = null;
//		try {
//			li = dbx.convertList(rs);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			throw new RuntimeException(e1);
//		}
//		return li;
//	}
	public String getPrimaryKeys(Connection conn,Object tab)
	{
		List<Map> li=getPrimaryKeys__all(conn,tab);
		String COLUMN_NAME = (String) li.get(0).get("COLUMN_NAME");
		if(COLUMN_NAME==null)
			COLUMN_NAME = (String) li.get(0).get("column_name"); //pgsql
		return COLUMN_NAME;
	}
	
	
	public List getPrimaryKeys__all(Connection conn,Object tab) {

		// DBX dbx = IocX4casher.getBean(DBX.class);
		java.sql.DatabaseMetaData dbmd = null;

		try {
			dbmd = conn.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// databaseMetaData.getColumns(localCatalog, localSchema,
		// localTableName, null);
	//	String catalog = (String) req.get("db");
		ResultSet rs = null;
		try {
			// = getTableName(req);
			// rs = dbmd.getPrimaryKeys(null, "%", tab.toString());  mysql
		//	rs = dbmd.getPrimaryKeys("%", "%", tab.toString());  //pgsqql
			rs = dbmd.getPrimaryKeys(null, "public", tab.toString());  //pgsqql   pgsql should add schmael
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		List li = null;
		try {
			li = new DBX().convertList(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		return li;
	}

	private String getTable(Map req) {
		String table = (String) req.get("$table");
		if(table==null)
			 table = (String) req.get("table");
		if(table==null)
			 table = (String) req.get("$tb");
		if(table==null)
			throw new RuntimeException("cant get table var");
		return table;
	}
	private Object getTableName(Map req) {
		 
		return getTable(req);
	}
	
//	private Object getTableName(Map req) {
//		 
//		return getTable(req);
//	}

	/**
	 * cate log yasi null binsyo use... eque % yash..
	 * @param req
	 * @return
	 */
//	public List getColumns(Map req) {
//		
//		String catalog = "";
//		if (req.get("catalog") == null)
//			catalog = (String) req.get("db");
//		else
//			catalog = (String) req.get("catalog");
//		
//		Object tab = getTableName(req);
//		
//		String  line="db meata serv getColumns: tab is :"+tab;
//		filex.saveLog(line, "e:\\logQ4_fltMap");
//
//		return getColumns(catalog, tab);
//	}
//	
//	public List getColumns(String   tab) {
//		return getColumns(null,tab);
//	}

//	public List getColumns(String catalog, Object tab) {
//		DatabaseMetaData dbmd = null;
//		Connection conn = null;
//		try {
//			  conn=	 dbx.getConnection();
//			dbmd = conn.getMetaData();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			close(conn);
//			throw new RuntimeException(e);
//		} catch (com.attilax.db.getConnEx e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();	close(conn);
//			throw new RuntimeException(e);
//
//		}
//		// databaseMetaData.getColumns(localCatalog, localSchema,
//		// localTableName, null);
//		
//		ResultSet rs = null;
//		try {
//		
//			rs = dbmd.getColumns(catalog, "%",tab.toString(),
//					"%");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();	close(conn);
//			throw new RuntimeException(e);
//		}finally{
//		
//		}
//		List li = null;
//		try {
//			li = dbx.convertList(rs);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			throw new RuntimeException(e1);
//		}finally{
//			close(conn);
//		}
//		
//		return li;
//	}

	private void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public String getPrimaryKey(String dbName, String tablename) {
//		Map m = Maps.newLinkedHashMap();
//		m.put("db", dbName);
//		m.put("$table", tablename);
//		List<Map> li = getPrimaryKeys(m);
//
//		return (String) li.get(0).get("COLUMN_NAME");
//	}
	
	public String getPrimaryKey(Connection conn, String dbName, String tablename) {
		Map m = Maps.newLinkedHashMap();
		m.put("db", dbName);
		m.put("$table", tablename);
		List<Map> li = getPrimaryKeys(conn,dbName,tablename);
		String COLUMN_NAME = (String) li.get(0).get("COLUMN_NAME");
		if(COLUMN_NAME==null)
			COLUMN_NAME = (String) li.get(0).get("column_name"); //pgsql
		return COLUMN_NAME;
	//	return (String) li.get(0).get("COLUMN_NAME");
	}
	
	private List<Map> getPrimaryKeys(Connection conn, String dbName, String tablename) {
		// TODO Auto-generated method stub
		return getPrimaryKeys__all(conn,tablename);
	}

//	public String getPrimaryKey( String tablename) {
//		Map m = Maps.newLinkedHashMap();
//	//	m.put("db", dbName);
//	//	m.put("$table", tablename);
//		List<Map> li = getPrimaryKeys(m,tablename);
//
//		return (String) li.get(0).get("COLUMN_NAME");
//	}
//	public String getPrimaryKey(Map req) {
//	List<Map> li=getPrimaryKeys(req);
//	 
//		return (String) li.get(0).get("COLUMN_NAME");
//	}

//	public Set<String> getColumns_set(Map req) {
//		
//		List li=getColumns(req);
//		
//		return SetX.fromLi(li,"COLUMN_NAME");
//	}
//	
//	public Set<String> getColumns_set(String table) {
//		
//		List li=getColumns(table);
//		
//		return SetX.fromLi(li,"COLUMN_NAME");
//	}
//	
	public Set<String> getColumns_set_fromLi(List li) {
		
	//	List li=getColumns(table);
		
		return SetX.fromLi(li,"COLUMN_NAME");
	}
	//  @Inject
	//		public		 SqlService sqlSrv;
	private Map reqMap;
//	public void createNewCol(Map req) {
//		this.reqMap=req;
//		Set<String> cols = getColumns_set(req);
//		Map ret_minus =new MapX().minus(req, cols).removeDollerKey().rzt;
//		addCols(ret_minus);
//		
//	}
//	
//	private void addCols(Map ret_minus) {
//		String table=getTable(this.reqMap);
//		Set<String> cols=ret_minus.keySet();
//		for (String col : cols) {
//			String sql=	MysqlDMLSql.addCol.replace("$tab$", table).replace("$col$", col);
//			int r=sqlSrv.executeUpdate(sql);
//			System.out.println("--addcol "+sql+ " rzt:"+r);
//		
//		}
//	
		
	//}

}
