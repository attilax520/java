package com.attilax.io;
    import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;  
    import java.io.File;  
    import java.io.FileInputStream;  
    import java.io.FileNotFoundException;  
    import java.io.IOException;  
    import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;  
    import java.nio.MappedByteBuffer;  
    import java.nio.channels.FileChannel;  
import java.nio.channels.FileChannel.MapMode;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import com.attilax.Charset.EncodingDetect;
import com.attilax.util.ExUtil;

 
      
      
    public class FileUtilsAti {  
      
    	
    	/**
    	attilax    2016妤犵儑鎷�闁哄牞鎷�8闁哄喛鎷� 濞戞挸顑呭畷锟�:38:58
    	 * @param f
    	 * @param targetDir
    	 * @param oriDir
    	 */
    	public static void move(String f, String targetDir, String oriDir) {
    		//   int splt=f.indexOf(oriDir);
    		f=f.trim();
    		   String rltFilename=rltpath(f, oriDir);
    		   String newname=targetDir+File.separator+rltFilename;
    		   move(f,newname);
    		
    	}

    	public static String rltpath(String f, String oriDir) {
			return f.substring(oriDir.length()+1);
		}
    	
    	/**
    	 * s61  dek  contains jude..maybe file not hav ext name
    	@author attilax 闁奸绀侀幖浼存儍閸曨厼鐒婚悗娑虫嫹
    		@since  2014-5-8 濞戞挸锕ゅ畷锟�9:37:40$
    	
    	 * @param f
    	 * @param string
    	 */
    	public static boolean move(String f, String target) {
    		// attilax 閼颁礁鎼伴惃鍕焻鐎涳拷  娑撳﹤宕�9:37:40   2014-5-8 
//    		File f=new  File(target);
//    		if(f.exists())
    		//if(!target.contains("."))
    		//	throw new RuntimeException("target is not file path,maiby only dir:"+target);
    		//	createAllPath(target);
    			try {
					FileUtils.forceMkdir(new File(target).getParentFile());
				} catch (IOException e) {
					ExUtil.throwExV2(e);
				}
    			
    			File oldFile = new File(f);
    			//filex.move(f,target);
//    			
//    			// 鐏忓棙鏋冩禒鍓佇╅崚鐗堟煀閺傚洣娆㈤柌锟�
//    			
     		File fnew = new File(target);
     	return	oldFile.renameTo(fnew);
    		
    		
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
    		   //  System.out.println("閻╊喗鐖ｉ弬鍥︽閹碉拷閸︺劏鐭惧鍕瑝鐎涙ê婀敍灞藉櫙婢跺洤鍨卞鎭掞拷鍌橈拷鍌橈拷锟�+fileFullPath);
    		     if (!file.getParentFile().mkdirs()) {
    		    	 throw new RuntimeException("create parent  dir fail:"+fileFullPath);
    		   //   System.out.println("閸掓稑缂撻惄顔肩秿閺傚洣娆㈤幍锟介崷銊ф畱閻╊喖缍嶆径杈Е閿涳拷"+fileFullPath);
    		   
    		     }
    		 }
    	}
        /** 
         * the traditional io way  
         * @param filename 
         * @return 
         * @throws IOException 
         */  
        public static byte[] toByteArray(String filename) throws IOException{  
              
            File f = new File(filename);  
            if(!f.exists()){  
                throw new FileNotFoundException(filename);  
            }  
      
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());  
            BufferedInputStream in = null;  
            try{  
                in = new BufferedInputStream(new FileInputStream(f));  
                int buf_size = 1024;  
                byte[] buffer = new byte[buf_size];  
                int len = 0;  
                while(-1 != (len = in.read(buffer,0,buf_size))){  
                    bos.write(buffer,0,len);  
                }  
                return bos.toByteArray();  
            }catch (IOException e) {  
                e.printStackTrace();  
                throw e;  
            }finally{  
                try{  
                    in.close();  
                }catch (IOException e) {  
                    e.printStackTrace();  
                }  
                bos.close();  
            }  
        }  
          
          
        /** 
         * NIO way 
         * @param filename 
         * @return 
         * @throws IOException 
         */  
        public static byte[] toByteArray2(String filename)throws IOException{  
              
            File f = new File(filename);  
            if(!f.exists()){  
                throw new FileNotFoundException(filename);  
            }  
              
            FileChannel channel = null;  
            FileInputStream fs = null;  
            try{  
                fs = new FileInputStream(f);  
                channel = fs.getChannel();  
                ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());  
                while((channel.read(byteBuffer)) > 0){  
                    // do nothing  
    //              System.out.println("reading");  
                }  
                return byteBuffer.array();  
            }catch (IOException e) {  
                e.printStackTrace();  
                throw e;  
            }finally{  
                try{  
                    channel.close();  
                }catch (IOException e) {  
                    e.printStackTrace();  
                }  
                try{  
                    fs.close();  
                }catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
          
          
        /**  todox  perform
         * Mapped File  way 
         * MappedByteBuffer 鍙互鍦ㄥ鐞嗗ぇ鏂囦欢鏃讹紝鎻愬崌鎬ц兘 
         * @param filename 
         * @return 
         * @throws IOException 
         */  
        public static byte[] toByteArray3(String filename)throws IOException{  
              
            FileChannel fc = null;  
            try{  
                fc = new RandomAccessFile(filename,"r").getChannel();  
                MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();  
                System.out.println(byteBuffer.isLoaded());  
                byte[] result = new byte[(int)fc.size()];  
                if (byteBuffer.remaining() > 0) {  
    //              System.out.println("remain");  
                    byteBuffer.get(result, 0, byteBuffer.remaining());  
                }  
                return result;  
            }catch (IOException e) {  
                e.printStackTrace();  
                throw e;  
            }finally{  
                try{  
                    fc.close();  
                }catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }


        /**

         * 鐢熶骇鏂囦欢 path 濡傛灉鏂囦欢鎵�湪璺緞涓嶅瓨鍦ㄥ垯鐢熸垚璺緞

         * 

         * @param fileName

         *            鏂囦欢鍚�甯﹁矾寰�

         * @param isDirectory 鏄惁涓鸿矾寰�

         * @return

         * @author yayagepei

         * @date 2008-8-27

         */

        public static File buildFile(String fileName, boolean isDirectory) {

            File target = new File(fileName);

            if (isDirectory) {

                target.mkdirs();

            } else {

                if (!target.getParentFile().exists()) {

                    target.getParentFile().mkdirs();

                    target = new File(target.getAbsolutePath());

                }

            }

            return target;

        }


		public static String readFileToStringAutoDetectEncode(String f) {
			String code = EncodingDetect.getJavaEncode(f);
			String t = null;
			try {
				t = org.apache.commons.io.FileUtils.readFileToString(new File( f), code);
			} catch (IOException e) {
				ExUtil.throwExV2(e);
			}
			return t;
		}


		public static Reader readFileToReaderAutoDetectEncode(String f) {
			String t=readFileToStringAutoDetectEncode(f);
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(t.getBytes("utf8"));
				return ByteArrayInputStreamUtil.toReader(bais, "utf8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;  
			
			
			
		}


		public static boolean isImgType(String lowerCase) {
				if(lowerCase.endsWith(".gif")|| lowerCase.endsWith(".jpg")||lowerCase.endsWith(".jpeg")|| lowerCase.endsWith(".png")||  lowerCase.endsWith(".webp") ||  lowerCase.endsWith(".psd"))
			return true;else
			return false;
		}


		public static boolean isCompressFileType(String f) {
			if(f.endsWith(".rar") || f.endsWith(".zip"))
				return true;
			 
			return false;
		}


		public static boolean isExcelfile(String f) {
			if(f.endsWith(".xls") || f.endsWith(".xlsx"))
				return true;
			 
			return false;
			 
		}

		String fltExt="exe dll mp3 mp4 pdf chm wps";
/**
 * for doc reader
 * @param f
 * @return
 */
		public static boolean isBinfileType(String f) {
			if(f.endsWith(".exe") || f.endsWith(".dll") || f.endsWith(".重命名") || f.endsWith(".mp3") || f.endsWith(".mp4")|| f.endsWith(".pdf") || f.endsWith(".chm") || f.endsWith(".wps"))   
				return true;
			 if( isImgType(f))
				 return true;
			 if(isCompressFileType(f)) return true;
			return false;
		}


public static String readFileToStringAutoDetectEncode(File strFileName) {
	// TODO Auto-generated method stub
	return readFileToStringAutoDetectEncode(strFileName.getAbsolutePath());
}


public static void moveFileToDirectory(File strFileName, File destDirRoot, boolean b) {
	try {
		FileUtils.moveFileToDirectory(strFileName, destDirRoot, true);
	} catch (  IOException e) {
		ExUtil.throwExV2(e);
		// FileUtils.forceDelete(strFileName);
	}
	
	
}

public static boolean isNotDocFmt(String lowerCase) {
	if(FileUtilsAti.isImgType(lowerCase))
		return true;
	if(FileUtilsAti.isCompressFileType(lowerCase))
		return true;
	if(FileUtilsAti.isBinfileType(lowerCase))
		return true;
	if(lowerCase.endsWith(".lnk")|| lowerCase.endsWith(".wps")||lowerCase.endsWith(".jpeg")|| lowerCase.endsWith(".png")||  lowerCase.endsWith(".webp"))
		return true;
	return false;
}
    }  