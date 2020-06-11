package com.cnhis.cloudhealth.common.utils;

import com.google.gson.Gson;

/**
 * Gson工具类
 * 
 * @author xiaohui.pu
 *
 */
public class GsonUtils {

	/**
	 * 将对象转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * 将字符串转换为类
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T toClass(String jsonStr, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clazz);
	}
}
