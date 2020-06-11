package com.attilax.oplog.util;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attilax.oplog.OperLogAction;
import com.attilax.web.requestImp;
import com.attilax.web.sessionImp;

import cn.freeteam.util.MybatisSessionFactory;

public class ApiUtil {
	
	public static  Object parsedsl(String dsl) {
		MybatisSessionFactory.CONFIG_FILE_LOCATION = "/mybatis_postgresql.xml";
		HttpSession HttpSession1 = new sessionImp();
		requestImp requestImp1 = new requestImp();
		requestImp1.session = HttpSession1;
		// ServletActionContext.setRequest(null);
		OperLogAction.request4test = requestImp1;
		
		JSONObject jo = (JSONObject) JSON.parse(dsl);
		String class_name = (String) jo.get("class");
		String me = jo.getString("method");
		JSONArray jarr = jo.getJSONArray("params");
		Object[] obj_arr = new Object[jarr.size()];
		for (int i = 0; i < jarr.size(); i++) {
			obj_arr[i] = jarr.get(i);
		}

		Object o = null;
		try {
			o = ConstructorUtils.invokeConstructor(Class.forName(class_name), null);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException
				| ClassNotFoundException e) {
			//e.printStackTrace();
			//return JSON.toJSONString(e);
			throw	new RuntimeException(e);
		}
		Object rzt = null;
		try {
			rzt =   MethodUtils.invokeMethod(o, me, obj_arr);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			//return JSON.toJSONString(e);
		throw 	new RuntimeException(e);
		}
		return rzt;
	}

}
