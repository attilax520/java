package com.kok.sport.utils.mockdata;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.db.JsonUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.dbutilUtil;

public class zhenronDAta {

	/**
	 * 4231 shirt_number 球衣号 数字int logo logo
	 * 前缀：https://cdn.sportnanoapi.com/basketball/player/字符串varchar position
	 * 球员位置,F前锋 M中锋 D后卫 G守门员,其他为未知字符串varchar
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// String zhudui="select * from football_team_t where "
		String sql = "select * from football_formation_t  ";
		List<LinkedHashMap> executeSql = MybatisUtil.executeSql(sql);
		dbutilUtil.convertJsonStr2obj(executeSql);
		System.out.println(JSON.toJSONString(executeSql, true));
		;
		// addMems();
	}

	private static void addMems() throws Exception {
		List li2 = jenronIndex();
		LinkedHashMap member = Maps.newLinkedHashMap();
		List li = Lists.newArrayList();
		for (int i = 0; i <= 10; i++) {
			LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
			m.put("name_zh", "名字" + i);
			m.put("shirt_number", i);
			m.put("logo", "https://cdn.sportnanoapi.com/basketball/player");

			Map positon = getPositon(i, li2);
			m.put("position", positon.get("type"));
			m.put("x", positon.get("x"));
			m.put("y", positon.get("y"));
			li.add(m);
		}
		member.put("zhudui", li);
		List li_kedui = Lists.newArrayList();
		for (int i = 11; i < 21; i++) {
			LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
			m.put("name_zh", "名字" + i);
			m.put("shirt_number", i);
			m.put("logo", "https://cdn.sportnanoapi.com/basketball/player");
			Map positon = getPositon(i - 11, li2);
			m.put("position", positon.get("type"));
			m.put("x", positon.get("x"));
			m.put("y", positon.get("y"));
			li_kedui.add(m);
		}
		member.put("kedui", li_kedui);
		String jsonString = JSON.toJSONString(member, true);
		System.out.println(jsonString);

		String sql = "Update football_formation_t set  members='" + jsonString + "'";
		System.out.println(MybatisUtil.executeSqlRetObj(sql));
		;
	}

	@SuppressWarnings("all")
	private static Map getPositon(int i, List<LinkedHashMap> li2) {
		int index = 0;
		for (LinkedHashMap object : li2) {

			if (i == index) {
				return object;
			}
			index++;

		}
		return null;
	}

	private static List jenronIndex() {

		int membs = 1;
		List li = Lists.newArrayList();
		LinkedHashMap m1 = Maps.newLinkedHashMap();
		int allLayers = 6;
		m1.put("y", getY(1, allLayers));
		m1.put("x", 50);
		m1.put("type", "守门员");

		/// houwei 4
		LinkedHashMap m2 = Maps.newLinkedHashMap();
		int layer_cur = 2;
		membs = 3;
		for (int i = 1; i <= membs; i++) {
			LinkedHashMap m6 = getMapLayer(layer_cur, allLayers, "后卫", i, membs);
 
			li.add(m6);
		}


		////////
		layer_cur = 3;
		membs = 2;

		for (int i = 1; i <= membs; i++) {
			LinkedHashMap m6 = getMapLayer(layer_cur, allLayers, "后卫", i, membs);

			 
		}

		////// 42 33333----------------
		layer_cur = 4;
		membs = 3;

		for (int i = 1; i <= membs; i++) {
			LinkedHashMap m6 = getMapLayer(layer_cur, allLayers, "中锋", i, membs);

		 
			li.add(m6);
		}

		//// lat 1
		layer_cur = 5;
		membs = 1;

		for (int i = 1; i <= membs; i++) {
			LinkedHashMap m6 = getMapLayer(layer_cur, allLayers, "前锋", i, membs);

		 
			li.add(m6);
		}

		 

		 
		return li;
	}

	private static LinkedHashMap getMapLayer(int layer_cur, int allLayers, String string, int xIdx, int allMems) {
		LinkedHashMap m9 = Maps.newLinkedHashMap();
		m9.put("y", getY(layer_cur, allLayers));
		m9.put("x", getX(xIdx, allMems));
		m9.put("type", string);
		return m9;
	}

	private static Object getX(int xIdx, int allMems) {
		int span = 100 / allMems;
		return xIdx * 17;

	}

	private static int getY(int layer, int allLayers) {
		int span = 100 / allLayers;
		return layer * 17;
	}

}
