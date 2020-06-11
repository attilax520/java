package com.attilax.oplog;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attilax.oplog.util.filex;
import com.attilax.web.requestImp;
import com.attilax.web.sessionImp;

import cn.freeteam.util.MybatisSessionFactory;
 

public class mvcRest {

	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException, ClassNotFoundException {
		
		MybatisSessionFactory.CONFIG_FILE_LOCATION = "/mybatis_postgresql.xml";
		HttpSession HttpSession1=new sessionImp();
		requestImp requestImp1 = new requestImp();
		requestImp1.session=HttpSession1;
	//	ServletActionContext.setRequest(null);
		OperLogAction.request4test = requestImp1;
	//	org.apache.struts2.ServletActionContext.set
	
		String f = "C:\\0wkspc\\oploggerPrj\\src\\com\\attilax\\oplog\\t.json";
		String req_context = filex.read(f);
		JSONObject jo = (JSONObject) JSON.parse(req_context);
		String class_name = (String) jo.get("class");
		String me = jo.getString("method");
		JSONArray jarr = jo.getJSONArray("params");
		Object[] obj_arr = new Object[jarr.size()];
		for (int i = 0; i < jarr.size(); i++) {
			obj_arr[i] = jarr.get(i);
		}

		Object o = ConstructorUtils.invokeConstructor(Class.forName(class_name), null);
		String rzt = (String) MethodUtils.invokeMethod(o, me, obj_arr);
		System.out.println(rzt);

	}

}
