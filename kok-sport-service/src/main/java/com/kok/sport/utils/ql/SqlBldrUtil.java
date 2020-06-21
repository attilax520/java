package com.kok.sport.utils.ql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kok.sport.utils.ListBldr;
import com.kok.sport.utils.MapBldr;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.db.DatabaseMetaDataUtil;
import com.kok.sport.utils.db.MybatisUtil;

public class SqlBldrUtil {

	public static void main(String[] args) throws Exception {
		String tableName = "football_odds_t";
		// String sql = "insert into #{[tableName]}( #{[cols]} )values (#{[vals]})";

		// Map kv=MapBldr.newx().put("lock_flag","vvv").build();
		SqlSession session = MybatisUtil.getConn();
		Connection connection = session.getConnection();

		List<String> li_col_placeholder = ListBldr.newx().add("lock_flag").add("lock_flag2").build();
		System.out.println(getInsertSqlTmplt(tableName, li_col_placeholder, connection));

	}

	@SuppressWarnings("all")
	/**
	 * 生成insert 模板，默认只有not null col
	 * 
	 * @param tableName
	 * @param li_col_placeholder
	 * @return
	 * @throws Exception
	 */
	private static String getInsertSqlTmplt(String tableName, List<String> li_col_placeholder, Connection connection)
			throws Exception {
		String sql = "insert  into  #{[tableName]}( #{[cols]} )values (#{[vals]})";

		Map param = getColsValsMap(tableName, li_col_placeholder, connection);

		sql = QlSpelUtil.parse(sql, param);
		// System.out.println(li);
		// System.out.println(JSON.toJSONString(DatabaseMetaDataUtil.getColsList(connection,
		// tableName), true));

		return sql;
	}

	/**
	 * Map param = Maps.newConcurrentMap(); param.put("tableName", tableName);
	 * param.put("cols", cols_s); param.put("vals", vstmp);
	 * 
	 * @param tableName
	 * @param li_col_placeholder
	 * @param connection
	 * @return
	 */
	public static Map getColsValsMap(String tableName, List<String> li_col_placeholder, Connection connection) {
		try {
			DatabaseMetaData meta = connection.getMetaData();
//防止重复
			Set<String> cols_liSet = Sets.newConcurrentHashSet();
			List<String> cols_li = Lists.newArrayList();
			List<String> vs_li = Lists.newArrayList();
			List<Map> cols = DatabaseMetaDataUtil.getColsList(connection, tableName);
			List<Map> li_NotNull = DatabaseMetaDataUtil.getColsListNotNull(connection, tableName);
			li_NotNull.forEach(new Consumer<Map>() {

				@Override
				public void accept(Map t) {
					cols_liSet.add(t.get("COLUMN_NAME").toString());
					cols_li.add(t.get("COLUMN_NAME").toString());
					vs_li.add(colv_spelHolder(t));
				}
			});
			li_col_placeholder.forEach(new Consumer<String>() {

				@Override
				public void accept(String t) {
					if (cols_liSet.contains(t))
						return;
					try {

						// search by COLUMN_NAME get colObj
						Map col = cols.stream().filter(map_item -> {

							return map_item.get("COLUMN_NAME").toString().equals(t);
							// return true;

						}).collect(Collectors.toList()).get(0);
						cols_li.add(t);
						vs_li.add(colv_spelHolder(col));
						if (t == "lock_flag")
							System.out.println("D");
					} catch (IndexOutOfBoundsException e) {
						System.out.println("   looks not find the colL" + t);
					}

				}
			});

			String cols_s = Joiner.on(",").join(cols_li);
			String vstmp = Joiner.on(",").join(vs_li);

			Map param = Maps.newConcurrentMap();
			param.put("tableName", tableName);
			param.put("cols", cols_s);
			param.put("valsTmp", vstmp);
			param.put("colsMeta", cols);

			System.out.println(JSON.toJSONString(cols, true));
			return param;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * val tmple by val,,if fun,,not need single quote... not shuld ext here
	 * 
	 * @param t
	 * @return
	 */
	protected static String colv_spelHolder(Map t) {
		return "#{[" + t.get("COLUMN_NAME").toString() + "]}";
		// "TYPE_NAME":"BIGINT"
//		if (t.get("TYPE_NAME").equals("BIGINT") || t.get("TYPE_NAME").equals("TINYINT")
//				|| t.get("TYPE_NAME").equals("INT"))
//			return "#{[" + t.get("COLUMN_NAME").toString() + "]}";
//		return "'#{[" + t.get("COLUMN_NAME").toString() + "]}'";
	}

	public static Map extVals(Map m, Map param) {
		String vals = m.get("valsTmp").toString();
		vals = QlSpelUtil.parse(vals, param);
		m.put("vals", vals);
		return m;
	}

	public static Map getColsValsMap4mybatis(String tableName, String li_col_placeholders, Map jsonmap,
			Connection connection) {
		List<String> li_col_placeholder = SqlBldrUtil.getCols4placeholder(li_col_placeholders);

		return getColsValsMap4mybatis(tableName, li_col_placeholder, jsonmap, connection);
	}

	@SuppressWarnings("all")
	public static Map getColsValsMap4mybatis(String tableName, List<String> li_col_placeholder, Map jsonmap,
			Connection connection) {
		Map m = SqlBldrUtil.getColsValsMap(tableName, li_col_placeholder, connection);
		jsonmap = SqlBldrUtil.geneBindedVals(m, jsonmap);

		m = SqlBldrUtil.extVals(m, jsonmap);
		return m;
	}

	/**
	 * param.put("cols", cols_s); param.put("valsTmp", vstmp); param.put("colsMeta",
	 * cols);
	 * 
	 * @param m
	 * @param jsonmap
	 * @return
	 */
	private static Map geneBindedVals(Map m, Map jsonmap) {
		List<Map> colsMeta = (List<Map>) m.get("colsMeta");
		String cols = m.get("cols").toString();
		String[] a = cols.split(",");
		for (String c : a) {
			String cType = colsMeta.stream().filter(map_item -> {

				return map_item.get("COLUMN_NAME").toString().equals(c);
				// return true;

			}).collect(Collectors.toList()).get(0).get("TYPE_NAME").toString();
			String v = getValWarpFmt(cType, jsonmap.get(c));
			jsonmap.put(c, v);
		}
		return jsonmap;
	}

	public static String getValWarpFmt(String cType, Object object) {
		if (cType.equals("BIGINT")) {
			return object.toString();
		}

		if (cType.equals("TINYINT") || cType.equals("INT"))
			return object.toString();
		if (cType.equals("VARCHAR"))
			return "'" + object.toString() + "'";
		if (cType.equals("DATETIME")) {
			if (object instanceof Map)
				return ((Map) object).get("exp").toString();
			else
				return "'" + object.toString() + "'";

		}

//				|| t.get("TYPE_NAME").equals("INT"))
//			return "#{[" + t.get("COLUMN_NAME").toString() + "]}";
//		return "'#{[" + t.get("COLUMN_NAME").toString() + "]}'";
		return "'" + object.toString() + "'";
	}

	public static List<String> getCols4placeholder(String cols) {
		String[] a = cols.split(",");
		List<String> li = Lists.newArrayList();
		for (String c : a) {
			li.add(c);
		}
		return li;
	}

	public static Map fun(String exp) {
		Map m = Maps.newConcurrentMap();
		m.put("exp", exp);
		return m;
	}

	public static String getValWarpFmt(String tableName, String col, Object v_col, Connection connection) {
		List<Map> ColsMetaList;
		try {
			ColsMetaList = DatabaseMetaDataUtil.getColsList(connection, tableName);

			String v_sqlFmt = getValWarpFmt(col, v_col, ColsMetaList);

			return v_sqlFmt;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getValWarpFmt(String col, Object v, List<Map> ColsMetaList) {
		String cType = ColsMetaList.stream().filter(map_item -> {

			return map_item.get("COLUMN_NAME").toString().equals(col);
			// return true;

		}).collect(Collectors.toList()).get(0).get("TYPE_NAME").toString();

		String v_sqlFmt = SqlBldrUtil.getValWarpFmt(cType, v);
		return v_sqlFmt;
	}

}
