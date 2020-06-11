package com.attilax.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Strutil {

	public static Set toSet(String compressableMimeType, String spltor) {
		String[] a=compressableMimeType.split(spltor);
		Set st=new HashSet<>();
		for (String string2 : a) {
			st.add(string2);
		}
	
		 
		return st;
	}

	
	//maybe throw nullpoint ex
	public static boolean containsAny(String contentType, Set<String> set) {
		 
//		if( contentType==null)
//			return false;
//		if( set==null)
//			return false;
		for (String object : set) {
			if(contentType.contains(object) )
				 
				return true;
		}
		return  false;
	}
	
	
	public static boolean containsAny_sf(String contentType, Set<String> set) {
		 
		if( contentType==null)
			return false;
		if( set==null)
			return false;
		for (String object : set) {
			if(contentType.contains(object) )
				 
				return true;
		}
		return  false;
	}


	public static    String[]  toStrArray(String command) {
		   StringTokenizer st = new StringTokenizer(command);
	       String[] cmdarray = new String[st.countTokens()];
	       for (int i = 0; st.hasMoreTokens(); i++)
	           cmdarray[i] = st.nextToken();
	       return cmdarray;
	}


	public static boolean containsAny(String string, String stStr) {
		Set st=toSet(stStr, " ");
		return st.contains(string);
	}

}
