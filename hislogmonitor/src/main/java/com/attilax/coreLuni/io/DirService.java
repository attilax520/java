package com.attilax.coreLuni.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.jfree.chart.ChartFactory;






import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.attilax.Closure;
import com.attilax.core;
// r626 import com.attilax.dataspider.TsaolyoNetDataSpider;
import com.attilax.ex.StopException;
import com.attilax.lang.YamlAtiX;
import com.attilax.lang.exCatchor;
import com.attilax.text.strUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import m.dirPkg.travDir;

public class DirService {
	
	public static void main(String[] args) {
		 long a=System.currentTimeMillis();
		 tt();
		// YamlAtiX.getList(f)
		
		 long a2=System.currentTimeMillis();
		 System.out.println(a2-a);
		 System.out.println("--f");
		// 138.396 s
		 //if dif  dirs  .only need  20s
		 
	}

	private static void tt() {
//		String d=",z:/鐖辨儏绫�,z:/鍔ㄧ敾绫�,z:/鍔ㄤ綔绫�,z:/娓彴鍥戒骇,z:/鍓ф儏绫�,z:/绉戝够绫�,z:/鎭愭�栫被,z:/鎮枒绫�,z:/鎴樹簤绫�,z:/鍠滃墽绫�";
//		DirService.traveDirPa1(d, new com.attilax.lang.Closure<String, String>() {
//
//			@Override
//			public String execute(String f) throws Exception {
//				if(f.contains("@Recycle") || f.contains("player"))
//						return null;
//				f = pathx.fixSlash(f);
//				System.out.println(f);
//				 
//				return null;
//			}
//		});
	}

//	public static List<String> getFiles(String strPath) {
//		travDir.refreshFileList(strPath);
//		List<String> li = travDir.filelist;
//		
//		return li;
//	}

	private String dirname;

	public DirService(String dirname) {
		this.dirname=dirname;
	}

	 
	public DirService() {
		// TODO Auto-generated constructor stub
	}

	public List<String> subDirs() {
		 
		 File[] files = 	new File(this.dirname).listFiles(); 
		 List<String> li=new ArrayList<String>();
		 for (File f : files) {
			 li.add(f.getName());
		}
		return li;
	}

	/**
	@author attilax 锟斤拷锟桔碉拷爪锟斤拷
		@since  2014-5-27 锟斤拷锟斤拷02:42:43$
	
	 * @param s
	 * @return
	 */
	public static String getParentPath(String s) {
		// attilax 锟斤拷锟桔碉拷爪锟斤拷  锟斤拷锟斤拷02:42:43   2014-5-27 
		File f=new File(s); 
		return f.getParent();
	}
	 
	 
	
//	public static void traveDirPa1(String strPath,
//			com.attilax.lang.Closure closure) {
//		strPath=pathx.fixSlash(strPath);
//		String[] a=strPath.split(",");
//		for (String dir_t : a) {
//			if(dir_t.trim().length()==0)
//				continue;
//			if (dir_t.contains("闆ㄦ灉"))
//				System.out.println("dbg");
//			File dir = new File(dir_t);
//			if(!dir.exists())
//				continue;
//			File[] files = dir.listFiles();
//			int length = files.length;
//
//			for (int i = 0; i < files.length; i++) {
//				if (files[i].isFile())
//					continue;
//				//dir is 
//				try {
//					closure.execute(files[i].getAbsolutePath());
//					traveDirPa1(files[i].getAbsolutePath(), closure);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		}
//		
//	}
	/*for (int i = 0; i < length; i++) { 
	        if (files[i].isDirectory()) { 
	        	trave(files[i].getAbsolutePath(),closure); 
	        } else { 
	            String strFileName = files[i].getAbsolutePath().toLowerCase();
	            
	            try {
					closure.execute(files[i].getAbsolutePath());
				} catch (Exception e) {
					throw  new RuntimeException("",e);
				}  
	            
	        } 
	    } 
	 * */
	private static boolean isLastLevDir(File dir) {
		//   File dir = new File(strPath); 
		    File[] files = dir.listFiles(); 
			for (int i = 0; i < files.length; i++) { 
		        if (files[i].isDirectory()) { 
		        	return false;
		        }  
		    } 
		return true;
	}

 /**
	 * traveFile_noIncDir  only file 
	 * @param strPath
	 * @param closure
	 */
 
	public static void traveV3(String strPath ,Function   closure) { 
	    File dir = new File(strPath); 
	    File[] files = dir.listFiles(); 
	    
	    if (files == null) 
	        return; 
	    int length = files.length;
		for (int i = 0; i < length; i++) { 
	        if (files[i].isDirectory()) { 
	        	traveV3(files[i].getAbsolutePath(),closure); 
	        } else { 
	            String strFileName = files[i].getAbsolutePath().toLowerCase();
	            
	            try {
					closure.apply(files[i].getAbsolutePath());
				} catch (Exception e) {
					throw  new RuntimeException("traveV3 ex,f:"+strFileName,e);
				}  
	            
	        } 
	    } 
	}
	
	
	
	
		public String traveMode="all";  //dir,file   
	public boolean traveCanBreakThrowEx=false;
 

	
	/**  
	 *  not safe ver
	@author attilax 鑰佸搰鐨勭埅瀛�
	\t@since  Aug 2, 2014 3:40:56 AM$
	
	 * @param strPath
	 */
	public static void trave_NS(String strPath ,Closure closure) { 
	    File dir = new File(strPath); 
	    File[] files = dir.listFiles(); 
	    
	    if (files == null) 
	        return; 
	    for (int i = 0; i < files.length; i++) { 
	        if (files[i].isDirectory()) { 
	        	trave(files[i].getAbsolutePath(),closure); 
	        } else { 
	            String strFileName = files[i].getAbsolutePath().toLowerCase();
	            
	            try {
					closure.execute(files[i].getAbsolutePath());
				} catch (Exception e) {
					throw  new RuntimeException("",e);
				}  
	            
	        } 
	    } 
	}

	public DirService setTraveMode(String traveMode2) {
		this. traveMode= traveMode2;
		return this;
	}

	public void trave(String strPath, com.attilax.lang.Closure closure) {
		 File dir = new File(strPath); 
		    File[] files = dir.listFiles(); 
		    
		    if (files == null) 
		        return; 
		    int length = files.length;
			for (int i = 0; i < length; i++) { 
		        if (files[i].isDirectory()) { 
		        	trave(files[i].getAbsolutePath(),closure); 
		        } else { 
		            String strFileName = files[i].getAbsolutePath().toLowerCase();
		            
		            try {
						closure.execute(files[i].getAbsolutePath());
					} catch (Exception e) {
						throw  new RuntimeException("",e);
					}  
		            
		        } 
		    } 
	}
	
	/**
	 * q0f  save no throw ex
	attilax    2016骞�9鏈�28鏃�  涓嬪崍5:33:28
	 * @param strPath
	 * @param fun
	 */
	public void traveV2(String strPath, com.attilax.lang.Function  fun) {
		 File dir = new File(strPath); 
		 
		// Function<T, R>
		    File[] files = dir.listFiles(); 
		    
		    if (files == null) 
		        return; 
		    int length = files.length;
			for (int i = 0; i < length; i++) { 
		        if (files[i].isDirectory()) { 
		        	traveV2(files[i].getAbsolutePath(),fun); 
		        } else { 
		            String strFileName = files[i].getAbsolutePath().toLowerCase();
 
						fun.apply(files[i].getAbsolutePath());
 
		            
		        } 
		    } 
	}
	public static final Logger logger = LoggerFactory
			.getLogger(DirService.class);
	List<Map> err_li=Lists.newArrayList();
	int count;
	/**
	 * ingo ex ,and log ,and save ex in to li..
	 * * 
	 * 
	 * 
	 * @param strPath
	 * @param fun
	 */
	public void traveV3(String strPath, com.attilax.lang.Function  fun) {
		 File dir = new File(strPath); 
		 
		// Function<T, R>
		    File[] files = dir.listFiles(); 
		    
		    if (files == null) 
		        return; 
		    int length = files.length;
			for (int i = 0; i < length; i++) { 
		        if (files[i].isDirectory()) { 
		        	traveV2(files[i].getAbsolutePath(),fun); 
		        } else { 
		            String strFileName = files[i].getAbsolutePath();
		            try {
		            	fun.apply(files[i].getAbsolutePath());
					} catch (Exception e) {
						Map m=Maps.newConcurrentMap();
						m.put("file", strFileName);
						m.put("ex", e);
					}
		            count++;
		        	logger.info("--now process count:"+String.valueOf(count));
		        } 
		    } 
	}

}
