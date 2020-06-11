package com.attilax.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attilax.core.Consumer;
import com.attilax.core.MapUtil;
@SuppressWarnings("all")
public class josnutil {

	public static void main(String[] args) throws Exception {
		AjaxConsumer ac = new AjaxConsumer<>();
		final String containid = "10331314";
		ac.rztContainId = containid;
		ac.type = " com.attilax.util.AjaxConsumer";
		String json_s = JSON.toJSONString(ac);
		
	 
		Class  c=Class.forName(	ac.type.trim() );
		Object obj=	c.newInstance();
	 
		josnutil.copyMapJsonProp2ObjBean(json_s,obj);
		System.out.println( obj );
		
		
	}

	public static void copyMapJsonProp2ObjBean(String cur_token, final Object obj) throws Exception {

		JSONObject jo = JSONObject.parseObject(cur_token);
		
		final Map m = jo;

		MapUtil.foreachKey(m, new Consumer<Object>() {

			@Override
			public void accept(Object k) throws Exception {
				try {
					Object v = m.get(k);
					BeanUtils.copyProperty(obj, k.toString(), v);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

}
