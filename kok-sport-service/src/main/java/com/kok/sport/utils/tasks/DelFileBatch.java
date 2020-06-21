package com.kok.sport.utils.tasks;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;


//  T(com.kok.sport.utils.tasks.DelFileBatch).main(null)
public class DelFileBatch {

	public static void main(String[] args) {
	//	String dir=args[0];String extname=args[1].trim();
		String dir="/home/tomati/";String extname=".htm";
	File[] li=	new File(dir).listFiles();
	System.out.println("file len:"+li.length);
	for (File file : li) {
		
		if(!file.getAbsolutePath().endsWith(extname))
			continue;
		try {
		//	String f = dir+File.separator+ stri;
			System.out.println(file);
			FileUtils.forceDelete(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	for (String stri : li) {
//		try {
//			String f = dir+File.separator+ stri;
//			System.out.println(f);
//			FileUtils.forceDelete(new File(f));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//	}

	}

}
