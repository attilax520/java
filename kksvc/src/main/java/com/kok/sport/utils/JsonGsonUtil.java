package com.kok.sport.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kok.sport.utils.ql.QlSpelUtil;

public class JsonGsonUtil {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JsonObject json = getJsonRzt("Football.Basic.Team_list");
		Map m = toMap(json);
		//// spel

		String expressionString = "#root['data'][0]['id']";

		expressionString = "#root['data'].?[['id']<10003]";
		// "#myMap.?[key.length()<5&&value>90]"
		Object result1 = QlSpelUtil.query(m, expressionString);
		System.out.println(result1);
		System.out.println("ff");
		//JsonGsonUtil.toMap(jsonElement.getAsJsonObject());
	}

	public static Map toMap(JsonObject json) {

		return toMap(toStrFromJsonObj(json));
	}
	
	public static String processVars(String sql, JsonObject asJsonObject) {

		JsonObject JsonObject1 = asJsonObject;
		// new JsonParser().parse(t).getAsJsonObject();
		Set<Entry<String, JsonElement>> setE = JsonObject1.entrySet();
		if(setE==null)
			return sql;
		for (Entry<String, JsonElement> entry : setE) {
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
			JsonElement JsonElement1=entry.getValue();
			try {
				sql = sql.replace("@" + entry.getKey() + "@", "'" + JsonElement1.getAsString() + "'");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		return sql;

//        sql = sql.replace("@name_zh@", "'" + item.name_zh + "'")
//
//		        sql = sql.replace("@short_name_zh@", "'" + item.short_name_zh + "'")
//
//				        sql = sql.replace("@name_zht@", "'" + item.name_zht + "'")
//
//						        sql = sql.replace("@short_name_zht@", "'" + item.short_name_zht + "'")
//
//								        sql = sql.replace("@name_en@", "'" + item.name_en + "'")
//
//										        sql = sql.replace("@short_name_en@", "'" + item.short_name_en + "'")
//
//												         sql = sql.replace("@logo@", "'" + item.logo + "'")

		//return null;
	}

	public static JsonObject getJsonRzt(String svr) throws Exception {

		String fname = "d:\\cache\\" + svr + ".json";

		logger.info("file exist " + fname);

		String readFileToString = FileUtils.readFileToString(new File(fname));
		JsonParser jsonParser = new JsonParser();

//	        Map  map = new Gson().fromJson(readFileToString, HashMap.class);
//	        return map;
		// new Gson().fromJson()
		return jsonParser.parse(readFileToString).getAsJsonObject();
	}

	/**
	 * 将对象转换成Json字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String toStrFromObj(Object src) {
		return GSON.toJson(src);
	}

	/**
	 * 将Json转换成对象
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T toEntity(String json, Class<T> classOfT) {
		return GSON.fromJson(json, classOfT);
	}

	/**
	 * 将Json转化成Map
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, ?> toMap(String json) {
		return GSON.fromJson(json, MAP_TYPE);
	}

	/**
	 * 将Json字符串转化成List
	 * 
	 * @param json
	 * @param typeOfT
	 * @return
	 */
	public static <T> List<T> toList(String json, Class<T> typeOfT) {
		List<JsonObject> jsonObjectList = GSON.fromJson(json, JSON_OBJECT_TYPE);
		List<T> list = new ArrayList<>();
		for (JsonObject jsonObject : jsonObjectList) {
			list.add(toEntity(jsonObject.toString(), typeOfT));
		}
		return list;
	}

	/**
	 * Json字符串转JsonObject
	 * 
	 * @param json
	 * @return
	 */
	public static JsonObject toJsonObjectFromStr(String json) {
		return JSON_PARSER.parse(json).getAsJsonObject();
	}

	/**
	 * 将JsonObject转换成Json字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String toStrFromJsonObj(JsonObject jsonObject) {
		return jsonObject.toString();
	}

	/**
	 * 禁止调用无参构造
	 * 
	 * @throws Exception
	 */
//	private JsonUtils() throws Exception {
//		throw new Exception("Error...");
//	}

	private static final Gson GSON = new Gson();

	private static final JsonParser JSON_PARSER = new JsonParser();

	private static final Type MAP_TYPE = new TypeToken<Map<String, ?>>() {
	}.getType();

	private static final Type JSON_OBJECT_TYPE = new TypeToken<List<JsonObject>>() {
	}.getType();

	/**
	 * 将Json转化成Map
	 *
	 * @param json
	 * @param typeOfMap
	 * @param typeOfKey
	 * @param typeOfValue
	 * @param <M>
	 * @param <K>
	 * @param <V>
	 * @return
	 */
//	public static <M extends Map<K, V>, K, V> M toMap(String json, Class<M> typeOfMap, Class<K> typeOfKey, Class<V> typeOfValue) {
//	    Type[] argumentTypes = new Type[]{typeOfKey, typeOfValue};
//	    Type parameterizedType = ParameterizedTypeImpl.make(typeOfMap, argumentTypes, null);
//	    return GSON.get().fromJson(json, parameterizedType);
//	}
//
//	/**
//	 * 将json转化成Collection
//	 *
//	 * @param json
//	 * @param typeOfCollection
//	 * @param typeOfT
//	 * @param <C>
//	 * @param <T>
//	 * @return
//	 */
//	public static <C extends Collection<T>, T> C toCollection(String json, Class<C> typeOfCollection, Class<T> typeOfT) {
//	    Type[] argumentTypes = new Type[]{typeOfT};
//	    Type parameterizedType = ParameterizedTypeImpl.make(typeOfCollection, argumentTypes, null);
//	    return GSON.get().fromJson(json, parameterizedType);
//	}
}
