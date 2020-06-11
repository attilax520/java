package com.attilax.search;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.attilax.io.DirTraveService;
import com.attilax.io.PathService;
import com.attilax.util.timestampUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class FileIndexGEner {
	public static Logger logger = Logger.getLogger(Cls1.class);
	
	public static void main(String[] args) {
		DirTraveService dts=new DirTraveService();
		//final String dir = "C:\\Users\\attilax\\Documents\\s2018 s4 doc compc dtS44";
		final String   	dir="C:\\Users\\attilax\\Documents\\s2018 doc";
		final String dirdest="c:\\00allfilelist\\20171201-20180427";
		dts.traveV4Vs427(dir, new Function<File, Object>() {

			@Override
			public Object apply(File arg0) {
				logger.info("arg0:"+arg0.getAbsolutePath());
				String reltpath=PathService.relpath(arg0.getAbsolutePath(),dir);
				String newFileTxtmode4index=dirdest+"\\"+reltpath+".txt";
				try {
					String txtcontext = "data"+timestampUtil.gettimeStamp_millsec();
					logger.info("txtcontext:"+txtcontext);
					FileUtils.write(new File(newFileTxtmode4index), txtcontext);
				} catch (IOException e) {
					 
					e.printStackTrace();
				}
				return null;
			}
		}, new Function<Integer, Object>() {

			@Override
			public Object apply(Integer cnt_index) {
			//	Map m=Maps.newConcurrentMap();m.put("index", cnt_index)
				logger.info("cnt_index:"+cnt_index);
				return null;
			}});
	}
	
 
	 
}
