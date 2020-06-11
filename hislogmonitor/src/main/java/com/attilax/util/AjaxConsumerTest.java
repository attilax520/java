package com.attilax.util;

import static org.junit.Assert.*;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.Charset.paramReqUtil;
import com.attilax.core.AsynUtil;
import com.attilax.web.UrlUtil;
import com.google.common.collect.Lists;

public class AjaxConsumerTest {

	@Test
	public void test_shell() {
		
		final String cmd = "ping -n 60 www.baidu.com";

		final String host = "192.168.1.77";
		final String webshellport = "8022";

		AjaxConsumer ac = new AjaxConsumer<>();
		final String containid = "10331314";
		Global.map.put("cli_finishstat_" + containid,false);		
		 Global.map.put(containid,Lists.newArrayList());
		
		
		ac.rztContainId = containid;
		ac.type = " com.attilax.util.AjaxConsumer";
		final String json_s = JSON.toJSONString(ac);
		
		

		// AsynUtil.execMeth_Ays(new Runnable() {
		//
		// @Override
		// public void run() {
		// String url =
		// "http://@host@:@port@/webshell/commServletV2?new=xx&class=com.attilax.util.CliService&method=exe&paramtypes=str,com.attilax.util.AjaxConsumer,str&args=@cmd@,@consum@,utf8";
		// url = url.replaceAll("@host@", host);
		// url = url.replaceAll("@port@", webshellport);
		// url = url.replaceAll("@cmd@", UrlUtil.encode(cmd));
		// String AjaxConsumer_paramencode =
		// UrlUtil.encode(paramutil.urlparamEncode(json_s));
		// url = url.replaceAll("@consum@", AjaxConsumer_paramencode);
		// System.out.println(url);
		// com.attilax.net.http.HttpClientUtil hcu = new
		// com.attilax.net.http.HttpClientUtil();
		// String r = hcu.httpget(url);
		// System.out.println("send cmd ret:" + r);
		//
		// }
		// }, "threadName");

		// get result
		Timer tmr = new Timer();
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {
				String url = "http://@host@:@port@/webshell/commServletV2?new=xx&class=com.attilax.net.http.webshellUtil&method=getRztByContainid&paramtypes=str&args=@Containid@";
				url = url.replaceAll("@host@", host);
				url = url.replaceAll("@port@", webshellport);
				url = url.replaceAll("@Containid@", containid);
				System.out.println(url);
				com.attilax.net.http.HttpClientUtil hcu = new com.attilax.net.http.HttpClientUtil();
				String r = hcu.httpget_autoEncode(url);
				System.out.println(r);

			}
		}, 3000, 1000);
		
		
		
		
	
		String url = "http://@host@:@port@/webshell/commServletV2?new=xx&class=com.attilax.util.CliService&method=exe&paramtypes=str,com.attilax.util.AjaxConsumer,str&args=@cmd@,@consum@,gbk";
		url = url.replaceAll("@host@", host);
		url = url.replaceAll("@port@", webshellport);
		url = url.replaceAll("@cmd@", UrlUtil.encode(cmd));
		String AjaxConsumer_paramencode = UrlUtil.encode(paramutil.urlparamEncode(json_s));
		url = url.replaceAll("@consum@", AjaxConsumer_paramencode);
		System.out.println(url);
		com.attilax.net.http.HttpClientUtil hcu = new com.attilax.net.http.HttpClientUtil();
		String r = hcu.httpget(url);
		System.out.println("send cmd ret:" + r);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
