package com.attilax.io;

public class filexEx {

	public static String addTimestampNSuffix(String f, String suffix) {
		// TODO Auto-generated method stub
		return filex.addSuffix(f, "" + filex.getUUidName() + suffix);
	}
	
	public static void mkdir(String f) {
		filex.createAllPath(f);
		
	}

}
