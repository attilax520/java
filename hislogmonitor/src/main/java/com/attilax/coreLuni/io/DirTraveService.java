package com.attilax.coreLuni.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.attilax.util.timestampUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class DirTraveService {
	public static Logger logger = Logger.getLogger(Cls1.class);
	
	public static void main(String[] args) {
		DirTraveService dts=new DirTraveService();
		final String dir = "C:\\Users\\attilax\\Documents\\s2018 s4 doc compc dtS44";
		dts.traveV4Vs427(dir, new Function<File, Object>() {

			@Override
			public Object apply(File arg0) {
				logger.info("arg0:"+arg0.getAbsolutePath());
				String reltpath=PathService.relpath(arg0.getAbsolutePath(),dir);
				String newFileTxtmode4index="c:\\00txtmodeIndexdir\\"+reltpath+".txt";
				try {
					String txtcontext = "data"+timestampUtil.gettimeStamp_millsec();
					logger.info("txtcontext:"+txtcontext);
					FileUtils.write(new File(newFileTxtmode4index), txtcontext);
				} catch (IOException e) {
					 
					e.printStackTrace();
					logger.error(e);
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
	
	int count=0;
	/**
	 * ingo ex ,and log ,and save ex in to li..
	 * * 
	 * 
	 * 
	 * @param strPath
	 * @param fun
	 */
	public void traveV4Vs427(String strPath, Function  fun,Function  fun_log) {
		 File dir = new File(strPath); 
		 
		// Function<T, R>
		    File[] files = dir.listFiles(); 
		    
		    if (files == null) 
		        return; 
		    int length = files.length;
			for (int i = 0; i < length; i++) { 
		        if (files[i].isDirectory()) { 
		        	traveV4Vs427(files[i].getAbsolutePath(),fun,fun_log); 
		        } else { 
		            File strFileName = files[i];
		            try {
		            	fun.apply(strFileName);
					} catch (Exception e) {
						Map m=Maps.newConcurrentMap();
						m.put("file", strFileName);
						m.put("ex", e);
					}
		            count++;
		            fun_log.apply(count);
		          //	logger.info("--now process count:"+String.valueOf(count));
		        } 
		    } 
	}
	
	
	/**
	 * ingo ex ,and log ,and save ex in to li..
	 * * 
	 * 
	 * 
	 * @param strPath
	 * @param fun
	 */
	public void trave_throwEx(String strPath, Function  fun,Function  fun_log) {
		 File dir = new File(strPath); 
		 
		// Function<T, R>
		    File[] files = dir.listFiles(); 
		    
		    if (files == null) 
		        return; 
		    int length = files.length;
			for (int i = 0; i < length; i++) { 
		        if (files[i].isDirectory()) { 
		        	trave_throwEx(files[i].getAbsolutePath(),fun,fun_log); 
		        } else { 
		            File strFileName = files[i];
		           
		            	fun.apply(strFileName);
					 
		            count++;
		            fun_log.apply(count);
		          //	logger.info("--now process count:"+String.valueOf(count));
		        } 
		    } 
	}
	public static Function nullFunc() {
		Function fc=	new Function<Integer, Object>() {

			@Override
			public Object apply(Integer cnt_index) {
			//	Map m=Maps.newConcurrentMap();m.put("index", cnt_index)
			//	logger.info("cnt_index:"+cnt_index);
				return null;
			}};
		return fc;
	}

}
