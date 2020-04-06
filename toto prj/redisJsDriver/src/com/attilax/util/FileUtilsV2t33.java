package com.attilax.util;

public class FileUtilsV2t33 {

	public static boolean isPath(String cmdString) {
		if(cmdString.trim().startsWith("/"))
			return true;
		if(cmdString.trim().toCharArray()[1]==':')
			return true;
		return false;
	}

}
