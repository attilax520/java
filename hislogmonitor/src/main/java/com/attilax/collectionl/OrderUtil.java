package com.attilax.collectionl;

import java.util.Map;

public class OrderUtil {

	public static int asc(Object object, Object object2) {
		 
		return   object.toString().compareTo(object2.toString());
	}

	public static int desc(Object object, Object object2) {
		 
		return  0-   asc(object,object2);
	}

	public static int asc(String col, Map o1, Map o2) {
		return   o1.get(col).toString().compareTo( o2.get(col).toString());
	}
	
	public static int desc(String col, Map o1, Map o2) {
		return  0-   asc(col,o1,o2);
	}

}
