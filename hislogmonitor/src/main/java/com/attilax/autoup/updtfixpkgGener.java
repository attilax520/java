package com.attilax.autoup;

import java.util.List;

import com.attilax.compress.ZipUtil;
import com.google.common.collect.Lists;

public class updtfixpkgGener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String oldver="c:\\d\\hislogv7.war";
		String newver="c:\\d\\hislogv82.war";
		
		List<String>  oldverList=ZipUtil.unzip_filelistV2(oldver);
		System.out.println(getJars(oldverList));
	//	System.out.println(  jar_ex(newver,oldver)   );
		

	}

	private static List<String>  jar_ex(String newver, String oldver) {
		List<String>  newverList=ZipUtil.unzip_filelistV2(newver);
		
		List<String>  oldverList=ZipUtil.unzip_filelistV2(oldver);
		
		List<String>  newverList_jars=getJars(newverList);
		List<String>  oldverList_jars=getJars(oldverList);
		 newverList_jars.removeAll(oldverList_jars);
		return newverList_jars;
	}
	
	private static List<String> getJars(String oldver) {
		List<String>  oldverList=ZipUtil.unzip_filelistV2(oldver);
		return getJars(oldverList);
	}

	private static List<String> getJars(List<String> newverList) {
		List<String>  newverList2=Lists.newArrayList();
		for (String string : newverList) {
			String lowerCase = string.trim().toLowerCase();
			if(!lowerCase.startsWith("web-inf/lib/"))
				continue;
			if(lowerCase.endsWith(".jar"))   ///webinfo/xxxx.jar
				newverList2.add(string);
		}
		
		return newverList2;
	}

}
