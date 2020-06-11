package com.attilax.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.jfree.chart.ChartFactory;






import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
 
// r626 import com.attilax.dataspider.TsaolyoNetDataSpider;
 
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

 

public class DirService4monitor {
	
	public static void main(String[] args) throws IOException {
		 long a=System.currentTimeMillis();
		
		// tt();
		// YamlAtiX.getList(f)
		 String startdir = "c:\\";
		//List li = subfiles(startdir);
		
		 
		// System.out.println(JSON.toJSONString(sub_files) );
		 //["c:\\.classpath","c:\\.git","c:\\.gitignore","c:\\.project","c:\\.settings","c:\\libs","c:\\pom.xml","c:\\README.md","c:\\src","c:\\target","c:\\WebContent","c:\\work"]
		 
		 
		// System.out.println(JSON.toJSONString(li,true) );
		 System.out.println(new DirService4monitor().subfiles_retJsonArray(startdir) );
		
		 long a2=System.currentTimeMillis();
		 System.out.println(a2-a);
		 System.out.println("--d");
		// 138.396 s
		 //if dif  dirs  .only need  20s
		 
	}
	
	String subfiles_retJsonArray(String startdir)
	{
		List li = subfiles(startdir);
	return	JSON.toJSONString(li,true);
	}

	private   List subfiles(String startdir)   {
		File f=new File(startdir);
		 File[] sub_files=f.listFiles();
		 List li=Lists.newArrayList();
		 for (File object : sub_files) {
			 Map m=Maps.newConcurrentMap();
			 m.put("isDirectory",object.isDirectory());
			 m.put("getAbsolutePath",object.getAbsolutePath());
			 m.put("isFile",object.isFile());
			 m.put("getName",object.getName());
			 try {
				m.put("getCanonicalPath",object.getCanonicalPath());
			} catch (IOException e) {
				logger.error("getCanonicalPath ex",e);
				e.printStackTrace();
			}
			 m.put("getParent",object.getParent());
			 m.put("getPath",object.getPath());
		 
			 li.add(m);
		}
		return li;
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

	public DirService4monitor(String dirname) {
		this.dirname=dirname;
	}

	 
	public DirService4monitor() {
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
 
 
	
	
	
	
		public String traveMode="all";  //dir,file   
	public boolean traveCanBreakThrowEx=false;
	public boolean traveCanBreakThrowEx2=false;

	
	/**  
	 *  not safe ver
	@author attilax 鑰佸搰鐨勭埅瀛�
	\t@since  Aug 2, 2014 3:40:56 AM$
	
	 * @param strPath
	 */
 
	public DirService4monitor setTraveMode(String traveMode2) {
		this. traveMode= traveMode2;
		return this;
	}

 
	
	/**
	 * q0f  save no throw ex
	attilax    2016骞�9鏈�28鏃�  涓嬪崍5:33:28
	 * @param strPath
	 * @param fun
	 */
	 
	public static final Logger logger = LoggerFactory
			.getLogger(DirService4monitorV3.class);
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
	 

}
