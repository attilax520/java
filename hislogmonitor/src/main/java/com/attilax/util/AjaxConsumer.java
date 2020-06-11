package com.attilax.util;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.core.Consumer;
import com.attilax.web.HttpClientUtil;
import com.attilax.web.UrlUtil;
import com.attilax.web.httpclientTest;
import com.google.common.collect.Lists;

// com.attilax.util.AjaxConsumer
public class AjaxConsumer<T> implements Consumer<T> {

	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	

	public String getRztContainId() {
		return rztContainId;
	}

	public void setRztContainId(String rztContainId) {
		this.rztContainId = rztContainId;
	}

	String rztContainId;

	public AjaxConsumer() {
		this.rztContainId = String.valueOf(new Random().nextInt());
		Global.map.put(rztContainId, Lists.newArrayList());
	}

	@Override
	public void accept(T line) throws Exception {
	
//		if(Global.map.get(rztContainId)==null)
//		{
//			Global.map.put(rztContainId, Lists.newArrayList());
//			 
//		}
		List<String> ret_cmd_rzt_li = (List<java.lang.String>) Global.map.get(rztContainId);
		ret_cmd_rzt_li.add((String) line);
	}

}
