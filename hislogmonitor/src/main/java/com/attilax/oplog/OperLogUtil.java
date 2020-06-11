package com.attilax.oplog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

//package cn.freeteam.util;
// com.attilax.oplog.OperLogUtil

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.attilax.anno.LogAnno;
//import com.attilax.core;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.log.Logger;
import com.attilax.log.smpLogger;
import com.attilax.mq.PTPSend;
import com.attilax.util.Callback;
import com.attilax.util.Global;
import com.attilax.util.IdUtil;
import com.attilax.web.HttpClientUtil;
import com.attilax.web.requestImp;

import cn.freeteam.dao.OperlogsMapper;
import cn.freeteam.model.Operlogs;
import cn.freeteam.util.MybatisSessionFactory;
import m.global;


/**
 * 操作日志工具类
 * @author freeteam
 *2011-3-6
 *
 *vs315  add   log4postgre method ...jsonstr and normal str comaption
 */
public class OperLogUtil implements OperLogUtilInterface {
	
	public static void main(String[] args) {
		
	new OperLogUtil().log4postgre("ati", "{\"ddd\":123}");
		System.out.println("--f");
		
	}
	/* (non-Javadoc)
	 * @see com.attilax.oplog.OperLogUtilInterface#log4postgre(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@LogAnno(value = "afadfaf")
	public   String log4postgre(  String loginname,  String content )
	{
//		if("1"=="1")
//			throw new RuntimeException("---err log4postgre .............");
		 
		String timestamp=Global.timestamp();
		
		 
		try {
			JSONObject jo=JSONObject.parseObject(content);
			jo.put("timestamp", timestamp);
			content=jo.toJSONString();
		} catch (JSONException e) {
			if(e.getMessage().equals("error parse true"))
				content=content+",,,"+timestamp;
		}
		
		
		
		String logtag="currThread";
		log4postgre_core(loginname ,content,logtag);
		
		
		Map m=new HashMap<>();
		m.put("loginname", loginname);m.put("content", content);
		String jsonString = JSON.toJSONString(m,true);
		
		//----------local mq send
		  sendMqLocal(jsonString);
		
		
		//remote mq
		sendMqRemote_queueMode(jsonString);
		sendMqRemote_topicMode(jsonString);
		
		return "ok";
	}
	private void sendMqRemote_topicMode(String jsonString) {
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				String cfgfile = pathx.prjPath_webrootMode()+ "/cfg.txt";
				Properties prop=	Global.getPropFile(cfgfile);
				 String url=prop.getProperty("remote_mq_server_restapi_url_topicMode");
				 
				 
				 
				 
				 url=url.replaceAll("@v@",   Global.urlencode(jsonString));
				 System.out.println(new HttpClientUtil().httpget(url)); 
				
			}}, "threadName_sendMqRemote2");
		
	}
	private void sendMqRemote_queueMode(String jsonString) {
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				String cfgfile = pathx.prjPath_webrootMode()+ "/cfg.txt";
				Properties prop=	Global.getPropFile(cfgfile);
				 String url=prop.getProperty("remote_mq_server_restapi_url");
				 
				 
				 
				 
				 url=url.replaceAll("@v@",   Global.urlencode(jsonString));
				 System.out.println(new HttpClientUtil().httpget(url)); 
				
			}}, "threadName");
	}
	private String sendMqLocal(String jsonString) {
	
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				
			
				new PTPSend().sendSingle(jsonString);
				
			}}, "threadName");
		return jsonString;
	}
	
	private boolean isjsonObj(String content) {
		// TODO Auto-generated method stub
		return false;
	}
	public   String log4postgre_core(  String loginname,  String content, String logtag )
	{
		MybatisSessionFactory.CONFIG_FILE_LOCATION="/mybatis_postgresql.xml";
		HttpServletRequest request=new requestImp();
	 
		OperLogUtil.log_asyn(loginname ,content, request,logtag);
		return content;	
	}
		
	private static Logger logger=new smpLogger();
	
	
	
	/**
	 * async method log ,normal log
	 * @param loginname
	 * @param content
	 * @param request
	 * @param logtag
	 */
	public static void log_asyn(final String loginname,final String content,final HttpServletRequest request, String logtag){
		 log ( loginname,  content,  request, logtag);
		
	}
	/**
	 * async method log ,normal log
	 * @param loginname
	 * @param content
	 * @param request
	 * @param logtag
	 */
	public static void log(final String loginname,final String content,final HttpServletRequest request, String logtag){
		AsynUtil.execMeth_Ays(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					 log_sync( loginname,  content,  request, logtag);
					
				} catch (Exception e) {
					
					System.out.println(logtag+":---OperLogUtil  log err" );
					logger.log(e);
					try {
						MybatisSessionFactory.getSession().rollback();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				
			}
		}, "oplogerThread"+IdUtil.getUUid());
		 
		
	}
	public static void log_sync(String loginname, String content, HttpServletRequest request, String logtag) {
		
	//	com.attilax.oplog.util.JsonTypeHandler
		if (content!=null && content.trim().length()>0) {
			SqlSession session = MybatisSessionFactory.getSession();
			OperlogsMapper mapper=session.getMapper(OperlogsMapper.class);
			Operlogs log=new Operlogs();
			log.setId(UUID.randomUUID().toString());
			log.setContent(content);
			try {
				String remoteAddr = request.getRemoteAddr();
				if(remoteAddr==null)
					remoteAddr="null";
				log.setIp(remoteAddr);
			} catch (Exception e) {
				log.setIp("cant get ip");
			}
			
			log.setLoginname(loginname);
			log.setOpertime(new Date());
			
			mapper.insert(log);
			session.commit();
			try {
			
				session.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			try {
				session.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			MybatisSessionFactory.threadLocal_SqlSession.set(null);
			
			System.out.println( logtag+":---OperLogUtil  	MybatisSessionFactory.getSession().commit();" );
		}
		
	}
	@Override
	public String log4postgre(String loginname, String content, Callback Callback_hanler) {
		//log4postgre(loginname,content);
		//if()
		System.out.println("---log4postgre wizt Callback_hanler");
		Callback_hanler.process(" rat data...");
		return "--ok";
	}
}
