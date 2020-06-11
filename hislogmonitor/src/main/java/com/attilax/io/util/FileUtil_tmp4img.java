package com.attilax.io.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.attilax.exception.ExUtil;
import com.attilax.io.filex;

public class FileUtil_tmp4img {

	public static void copy(String f, String targetDir, String oriDir) {
		f=f.trim();
		   String rltFilename=f.substring(oriDir.length()+1);
		   String newname=targetDir+File.separator+rltFilename;
		   copy(f,newname);
		
	}
	
	public static void  copy(String f, String target) {
		// attilax 鑰佸搰鐨勭埅瀛�  涓婂崍09:37:40   2014-5-8 
//		File f=new  File(target);
//		if(f.exists())
		if(!target.contains("."))
			throw new RuntimeException("target is not file path,maiby only dir:"+target);
			filex.createAllPath(target);
			
			File oldFile = new File(f);
			//filex.move(f,target);
//			
//			// 灏嗘枃浠剁Щ鍒版柊鏂囦欢閲�
//			
 		File fnew = new File(target);
 		try {
			FileUtils.copyFile(new File(f), fnew);
		} catch (IOException e) {
		 ExUtil.throwExV3(e,"FileUtil_tmp4img copy err");
		}
 		return;
 	//	return true;
 	//return	oldFile.renameTo(dest)(fnew);
		
		
	}

}
