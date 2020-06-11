package com.attilax.fulltxt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.attilax.Charset.EncodingDetect;
import com.attilax.collection.mapBuilder;
import com.attilax.core.ForeachFunction;
import com.attilax.io.DirTraveService;
import com.attilax.text.strUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.collect.Lists;
@SuppressWarnings("all")
public class fulltxtServ {
	
	public static void main(String[] args) throws IOException {
		String f="C:\\Users\\attilax\\Desktop\\快速搭建ELK日志分析系统 - Json_20180522_084943.txt";
		String code=EncodingDetect.getJavaEncode(f );
		String t=FileUtils.readFileToString(new File( f),code);
		String kw="elasticsearch";
		System.out.println(getSometxt(kw,t));
	 
	}
	static final Logger logger = Logger.getLogger(Cls1.class);
	public static Object getSometxt(String kw, String t) {
		
		List li=Lists.newArrayList();
		int curstartIndex = 0;
	 
		while(true)
		{
		//	logger.info(curstartIndex);
			if(curstartIndex==1724)
				System.out.println("dbg");
			if(curstartIndex>=t.length())
				break;
			int stt2=t.indexOf(kw,curstartIndex);
			if(stt2==-1 )// cant find
				break;
		//	logger.info(" kw index"+ stt2);
			int rang_beginIndex_safe = fulltxtServ. getBeginIndex( stt2-50);
			int rabg_endIndex_safe = fulltxtServ.getEndIndex(stt2+50,t);
			String some2=t.substring( rang_beginIndex_safe,  rabg_endIndex_safe);
			
		//	logger.info(some2);
			li.add(some2);
			curstartIndex=rabg_endIndex_safe;
			
		}
		return li;
	}
	
	
 

	private static int getEndIndex(int i, String t) {
		if(i>t.length())
		return t.length();
		return i;
	}

	private static int getBeginIndex(int i) {
		if(i<0)
		return 0;
		return i;
	}




	public static List kwList(String kw) {
		// TODO Auto-generated method stub
		return strUtil.toList$(kw, " ").trimElement().build();
	}




	public static boolean contains(String t, List li_kw) {
	   for (Object kw : li_kw) {
		if(!t.contains(kw.toString().trim()))
			return false;
	}
		return true;
	}




	public static List search(String kw, String t, File f) {
		final List li = Lists.newArrayList();
		List li_kw=fulltxtServ.kwList(kw); 
		if(fulltxtServ.contains(t,li_kw))				
		  {
			for (Object kw1 : li_kw) {
				Map m = mapBuilder.$().put("kw", kw).put("sometxt", fulltxtServ.getSometxt(kw1.toString(), t)).put("f", f)
						.build();
				//put("index", count).
				li.add(m);
				logger.info(JSON.toJSONString(m, true));
			}
			
		}
		return li;
	}




	public static List searchFromDir(final String kw, String dir) {
		final List li = Lists.newArrayList();
		new DirTraveService().traveV5_vS522(new File(dir), new ForeachFunction() {

			@Override
			public void each(int count, File f) throws Exception {
				String code = EncodingDetect.getJavaEncode(f.getAbsolutePath());
				String t = FileUtils.readFileToString(f, code);
				logger.info( "cn:"+count+f.getAbsolutePath());
			//	kw="成年 后世";
				
				List rzt_singlefile=fulltxtServ.search(kw,t,f);
				if(rzt_singlefile.size()>0)
				{
					logger.info("*********************************************************************************");
				logger.info(rzt_singlefile);
				li.addAll(rzt_singlefile);
				logger.info("***************end******************************************************************");
				}
			

			}

		});
		return li;
		
	}

  

}
