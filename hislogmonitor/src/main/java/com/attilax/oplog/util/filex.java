package com.attilax.oplog.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.stream.ImageOutputStream;

//import m.FileService;


//import m.FileService;







import m.FileService;

import com.attilax.io.filexEx;
/*
import com.attilax.Closure;
import com.attilax.ClosureNoExcpt;
//import com.attilax.core;
import com.attilax.collection.Func;
import com.attilax.collection.listUtil;
import com.attilax.exception.ExUtil;
import com.attilax.io.filexEx;
import com.attilax.lang.core;
import com.attilax.text.strUtil;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Funcx;
import com.attilax.util.fileC0;
import com.attilax.util.god;
import com.attilax.util.travDir;

*/
import com.google.common.collect.Lists;
//import com.focustar.playRec.GvPlayRecord;
//import com.focustar.push.FileNotExist;

//import m.FileService;

public class filex  extends filexEx{

	public static void rename(String f, String target) {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  娑撳宕�04:15:25   2014-6-19 
		move(f, target);
	}


	// static 
	 public static final byte[] readImageData(String path){
		File file= new File(path);
		try{
			long length = file.length();
			byte tempData [] = new byte[(int)length];
			FileInputStream  fin = new FileInputStream(file);
			BufferedInputStream  bufIn=new BufferedInputStream(fin);
			bufIn.read(tempData);
			fin.close();
			return tempData;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("read image is  erro,please check path");
			return null;
		}
	}
	 
	 
	 /**
 *   xx.jpg >>> jpg
@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
\t@since  Aug 2, 2014 9:27:29 AM$

if no ext,ret emty str
 * @param fileName
 * @return
 */
	public static String getExtName(String fileName) {
		
		fileName=getFileName(fileName);  //o82  add can get empty extname.... no extname file can process..now .
		String name = "";
		String extention = "";
		if (fileName.length() > 0 && fileName != null) { // --閹搭亜褰囬弬鍥︽閸氾拷
			int i = fileName.lastIndexOf(".");
			if (i > -1 && i < fileName.length()) {
				name = fileName.substring(0, i); // --閺傚洣娆㈤崥锟�
				extention = fileName.substring(i + 1); // --閹碘晛鐫嶉崥锟�
			}
		}
		return extention;
	}
	
	/**
	 * if no ext ,ret null
	 * @param fileName
	 * @return
	 */
public static String getExtNameV2(String fileName) {
		
		fileName=getFileName(fileName);  //o82  add can get empty extname.... no extname file can process..now .
		String name = "";
		String extention =null;
		if (fileName.length() > 0 && fileName != null) { // --閹搭亜褰囬弬鍥︽閸氾拷
			int i = fileName.lastIndexOf(".");
			if (i > -1 && i < fileName.length()) {
				name = fileName.substring(0, i); // --閺傚洣娆㈤崥锟�
				extention = fileName.substring(i + 1); // --閹碘晛鐫嶉崥锟�
			}
		}
		//if(.equals(""))
		return extention;
	}
	
	public static String getFileName_noExtName(String fileName) {
		String name = "";
		String extention = "";
		int lastSpashIndex = fileName.lastIndexOf(File.separator);
		//qaf
		String fname = fileName.substring(lastSpashIndex + 1);

		int i = fname.lastIndexOf(".");
		
		try {
			name = fname.substring(0, i); // --閺傚洣娆㈤崥锟�
		} catch (Exception e) {
			if (i == -1) // no ext
				return fname;
		}
	
		// extention = fileName.substring(i + 1); // --閹碘晛鐫嶉崥锟�

		return name;

	}
	/**
	 * jeig haosyo you wenti l .qb25
	 * @param fileName
	 * @return
	 */
	public static String getFileName_mainname_noExtName_nopath(String fileName) {
		String f=getFileName_noExtName(fileName);
		String substring = f.substring(  f.lastIndexOf(".")+1,f.length());
		return substring;

	}
	/**
	 * _mainname_noExtName_nopath
	 * @param fileName
	 * @return
	 */
	public static String getFileName_mainname(String fileName) {
		String f=getFileName_noExtName(fileName);
		String substring = f.substring(  f.lastIndexOf("/")+1,f.length());
		return substring;

	}
	
	public static String getFileName(String fileName) {
		String name = "";
		String extention = "";
		int lastSpashIndex=fileName.lastIndexOf("\\");
		if(lastSpashIndex==-1)
			lastSpashIndex=fileName.lastIndexOf("/");
		if (fileName.length() > 0 && fileName != null) { // --閹搭亜褰囬弬鍥︽閸氾拷
		 
		 
				name = fileName.substring(lastSpashIndex+1); // --閺傚洣娆㈤崥锟�
			 
		}
		return name;

	}

	public static void write(String fileName, String str) {
		FileService.writeFile(fileName, str);

	}
	
		public   void writeToFile(String fileName, String str) {
		 byte[] data=str.getBytes();
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static String read(String path, String encode) {

		// if(path.equals(""))
		// {
		// System.out.println("--warnging :  path is empty cad");
		// return "";
		// }
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(path), encode);
		} catch (Exception e1) {

			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		BufferedReader reader;
		ArrayList li = new ArrayList<String>();
		try {
			reader = new BufferedReader(isr);

			// BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
			String line = reader.readLine();
			while (line != null) {
				// writer.write(line);
				sb.append(line + "\r\n");
				line = reader.readLine();

			}

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//o4e  cancel occu  handel
		try {
			isr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	/**
	 * o3i    
	 * @param fileFullPath 
	 */
	public static void createAllPath(String fileFullPath) {
		File file = new File(fileFullPath);
		if(file.getParentFile().exists())
			if(file.getParentFile().isFile())
				throw new RuntimeException("exist same name file: should be create dir for file:"+fileFullPath);
		 if (!file.getParentFile().exists()) {
		   //  System.out.println("鐩爣鏂囦欢鎵�鍦ㄨ矾寰勪笉瀛樺湪锛屽噯澶囧垱寤恒�傘�傘��"+fileFullPath);
		     if (!file.getParentFile().mkdirs()) {
		    	 throw new RuntimeException("create parent  dir fail:"+fileFullPath);
		   //   System.out.println("鍒涘缓鐩綍鏂囦欢鎵�鍦ㄧ殑鐩綍澶辫触锛�"+fileFullPath);
		   
		     }
		 }
	}
	
	/**
	 * o3i    
	 * @param fileFullPath 
	 */
	public static void createAllPath(String fileFullPath,boolean echo) {
		File file = new File(fileFullPath);
		if(file.getParentFile().exists())
			if(file.getParentFile().isFile())
				throw new RuntimeException("exist same name file: should be create dir for file:"+fileFullPath);
	 if (!file.getParentFile().exists() ) {
		 		if(echo)
		 				System.out.println("鐩爣鏂囦欢鎵�鍦ㄨ矾寰勪笉瀛樺湪锛屽噯澶囧垱寤恒�傘�傘��"+fileFullPath);
		     if (!file.getParentFile().mkdirs()  ) {
		    	 if(echo)
		      System.out.println("鍒涘缓鐩綍鏂囦欢鎵�鍦ㄧ殑鐩綍澶辫触锛�"+fileFullPath);
		   
		     }
		 }
	}

	public static String read(String string) {
		 try {
			 return read(string,"utf-8");
		} catch (Exception e) {
			//ExUtil.throwExV2(e);
			throw new RuntimeException(e);
		}
		
	}
	
	public static String read_NSF(String string) {
		 
			 return read(string,"utf-8");
		 
		
	}
	
	public static String read_SF(String file,String defVal) {
		 try {
			 return read(file,"utf-8");
		} catch (Exception e) {
			return defVal;
		}
		
	}

/**
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
		@since  2014-5-8 娑撳﹤宕�09:37:40$
	
	 * @param f
	 * @param string
	 */
	public static boolean move(String f, String target) {
		// attilax 鑰佸搰鐨勭埅瀛�  涓婂崍09:37:40   2014-5-8 
//		File f=new  File(target);
//		if(f.exists())
		if(!target.contains("."))
			throw new RuntimeException("target is not file path,maiby only dir:"+target);
			createAllPath(target);
			
			File oldFile = new File(f);
			//filex.move(f,target);
//			
//			// 灏嗘枃浠剁Щ鍒版柊鏂囦欢閲�
//			
 		File fnew = new File(target);
 	return	oldFile.renameTo(fnew);
		
		
	}

	/**
	 * 
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
	\t@since  Aug 23, 2014 4:01:15 PM$
	
	 * @param ba
	 * @param file
	 */
	public static void save(byte[] ba, String file) {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  hjm   o7l 
		try {
			createAllPath(file);	
			InputStream is = new ByteArrayInputStream(ba);
			byte[] bs = new byte[1024];
			// 鐠囪褰囬崚鎵畱閺佺増宓侀梹鍨
			int len;
			// 鏉堟挸鍤惃鍕瀮娴犺埖绁�
		 	OutputStream os = new FileOutputStream(file);
			// 瀵拷婵顕伴崣锟�
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 鐎瑰本鐦敍灞藉彠闂傤厽澧嶉張澶愭懠閹猴拷
			os.close();
			is.close();
			} catch (Exception e) {
			//	core.log(e);
			 
			}
		
	}
	
	


	/**
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
	\t@since  Aug 2, 2014 3:49:47 AM$
	
	 * @param string
	 * @throws IOException 
	 */
	public static void append(String fileName, String fileContent,String encode) throws IOException {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:49:47 AM   Aug 2, 2014 
	 
		   
		createAllPath(fileName);
		        File f = new File(fileName);      
		        if (!f.exists())   
		        {       
		            f.createNewFile();      
		        }      
		        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true),encode);      
		        BufferedWriter writer=new BufferedWriter(write);          
		        writer.write(fileContent);      
		       
		        writer.close();     
		     
		 
		
		
	}
	private File file;
	private BufferedWriter writer;
	
	public filex ( String fileName ) throws IOException 
	{
		createAllPath(fileName);
ini(fileName,"utf-8");
	}
	
	public filex ( String fileName,  String encode) throws IOException 
	{createAllPath(fileName);
		   ini(fileName, encode);   
	}

boolean genefile=true;
	public filex(String fileName, String encode, boolean b) {
		if(b)  //safe mode
		{
		createAllPath(fileName);
		   try {
			ini(fileName, encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();throw new RuntimeException(e);
		}   
		}
	}


	public filex() {
		// TODO Auto-generated constructor stub
	}

	private void ini(String fileName, String encode) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		File f = new File(fileName);      
	        if (!f.exists())   
	        {       
	            f.createNewFile();      
	        }   
		this.file=f;
		  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true),encode);      
	        this.writer=new BufferedWriter(write);
	}
	/**
	 * high perf
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
	\t@since  Aug 2, 2014 6:21:30 AM$
	
	 * @param fileName
	 * @param fileContent
	 * @param encode
	 * @throws IOException
	 */
	public   void append_HP(  String fileContent ) throws IOException   {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:49:47 AM   Aug 2, 2014 
	 
		   
		if (genefile == false)
			return;
		        
		             
		        writer.write(fileContent);      
		      
		     
		 
		
		
	}
	
	public   void append_HP_Safe(  String fileContent )    {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:49:47 AM   Aug 2, 2014 
	 
		   
		         
		        
		             
		        try {
					writer.write(fileContent);
					writer.flush();
				} catch (IOException e) {
					 
					e.printStackTrace();
				} 
	}
	
	public   void appendLine_flush_safe(  String line )    {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:49:47 AM   Aug 2, 2014 
	 
		   
		         
		        
		             
		        try {
					writer.write(line+"\r\n");
					writer.flush();
				} catch (IOException e) {
					 
					e.printStackTrace();
				} 
	}
	

	public void close() throws IOException
	{
		if (genefile == false)
			return;
		  writer.close();     
	}
	
	/**
	 * 
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
	\t@since  Aug 2, 2014 3:52:45 AM$
	
	 * @param fileName
	 * @param fileContent
	 * @param encode
	 * @throws IOException 
	 */
	public static void append(String fileName, String fileContent ) throws IOException {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:49:47 AM   Aug 2, 2014 
	 
		append(fileName,fileContent,"utf-8");
		
		
	}
	
	/**
	 * 
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
		@since  o8o 0_39_j   
	
	 * @param fileName
	 * @param fileContent
	 * @throws IOException
	 */
	public static void appendLine(String fileName, String fileContent ) throws IOException {
		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:49:47 AM   Aug 2, 2014 
	 
		append(fileName,fileContent+"\r\n","utf-8");
		
		
	}

	/**
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
	\t@since  Aug 2, 2014 3:55:24 AM$
	
	 * @param fileName
	 * @param string
	 */
//	public static void append(String fileName, String string) {
//		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  3:55:24 AM   Aug 2, 2014 
//		
//		{ 
//		 } 
//		
//		
//	}
	
	
	/**
	@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
	@since   oaa c_54_43
	 
	 */
public static long getSize(String file) {
	 
	return new File(file).length();
}


		/**
		@author attilax 閼颁礁鎼伴惃鍕焻鐎涳拷
		@since   oad g_s_a
		 
		 */
	public static void file2url(String fileurl) {
		  File file = new File(fileurl);  
      //    Player player = Manager.createPlayer(file.toURI().toURL());  
		
	}


			public void closeSF() {
							try {
								close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								System.out.println("---cant ok close...by force close");
								e.printStackTrace();
							}
							
						}


						public List<String> subDirs() {
					 
							 File[] files = 	this.file.listFiles(); 
							 List<String> li=new ArrayList<String>();
							 for (File f : files) {
								 li.add(f.getName());
							}
							return li;
						}


						public static boolean isEmptyDir(Object object) {
							File file = new File(object.toString());
							if(file.isDirectory())
							{
								  File[] files = file.listFiles(); 
								  if(files.length==0)
									  return true;
							}
							return false;
						}


						public Set toSet(String f, String splitor) {
		Set<String> st = new HashSet();
		String t = filex.read(f);
		String[] a = t.split("\r\n");
		for (String line : a) {
			String[] a2 = line.split(splitor);
			for (String item : a2) {
				st.add(item);
			}
		}
		return st;
	}

	public Set<String> toSetByLine(String appPath) {
		// TODO Auto-generated method stub
		return toSet(appPath,"\r\n");
	}


	public static String getDirName(String f2) {
		try {
			String[] a=f2.split("/");
			return a[a.length-2];
		} catch (Exception e) {
			return "";
		}
		
	}


	public static String getDirName(String f2, int i) {
		try {
			String[] a=f2.split("/");
			return a[i];
		} catch (Exception e) {
			return "";
		}
	}


	public String replaceExtname(String jpg, String string) {
		String baseName=filex.getFileName_mainname( jpg);
		File f=new File(jpg);
		String parentPath=f.getParentFile().getAbsolutePath();
		String newFile=parentPath+"\\"+baseName+"."+string;
		return newFile;
	}


	public String getFilePathNFileMainName(String jpg) {
		String baseName=filex.getFileName_mainname( jpg);
		File f=new File(jpg);
		String parentPath=f.getParentFile().getAbsolutePath();
		String newFile=parentPath+"\\"+baseName;
		return newFile;
	}


	public static File getFile(String string) {
		createAllPath(string);
		
		return  new File(string);
	}

String dir;
	public filex In_the_pc_machine_directory(String string) {
		dir=string;
		return this;
	}


	public filex written_to_the_file() {
		// TODO Auto-generated method stub
		return this;
	}


	public filex comma() {
		// TODO Auto-generated method stub
		return this;
	}
						public static String fileNameEncode(Object startWith) {
							 
							return fileNameEncode(startWith.toString());
						}


						public static byte[] readFileBlob(File file2) {
							// TODO Auto-generated method stub
							return readImageData(file2.getAbsolutePath());
						}
String context;
/**
 * only main name
 */
private String filename;
private String extname;
private int threadCount;
	public filex the_content_is(String string) {
		context=string;
		return this;
	}


	public filex the_file_name_is(String string) {
		this.filename=string;
		return this;
	}


	public filex the_extension_is(String string) {
		this.extname=string;
		return this;
	}


	public  	ExecutorService fixedThreadPool;

	public filex OpenTasksCount(int i) {
		// TODO Auto-generated method stub
		this.threadCount=i;
		return this;
	}


	public filex at_the_same_time() {
		// TODO Auto-generated method stub
		return this;
	}


	/**
	attilax    2016楠烇拷9閺堬拷28閺冿拷  娑撳宕�5:38:58
	 * @param f
	 * @param targetDir
	 * @param oriDir
	 */
	public static void move(String f, String targetDir, String oriDir) {
		//   int splt=f.indexOf(oriDir);
		f=f.trim();
		   String rltFilename=f.substring(oriDir.length()+1);
		   String newname=targetDir+File.separator+rltFilename;
		   move(f,newname);
		
	}
	
	/**
 * append mode
 * @param okli_file
 * @return
 */
	public static filex newFilex(String okli_file) {
		 
		return new filex(okli_file, "utf8",true);
	}

	public static String addSuffix(String f, String suf) {
		String path=new File(f).getParent();
		String mainname=getFileName_noExtName(f);
		String mainame_new=mainname+"_"+suf;
		String ex=getExtName(f);
		String f2=path+File.separator+mainame_new+"."+ex;
		return f2;
	}


	/**
	attilax    2016骞�11鏈�9鏃�  涓嬪崍11:04:26
	 * @param string
	 * @param jpg
	 * @return
	 */
	public static boolean existSameFileButExtIs(String ext, String jpg) {
		String path=new File(jpg).getParent();
		String name=getFileName_noExtName(jpg);
		String fullname = path+"/"+name+"."+ext;
		if(new File(fullname).exists())
			return true;
		return false;
	}


	public static String convertSseparatorToLocal(String f, String sprtr) {
	 
		return f.replace(sprtr, File.separator);
		 
	}



					
				   
 
		

}
