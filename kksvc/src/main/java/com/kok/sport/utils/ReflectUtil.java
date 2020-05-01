package com.kok.sport.utils;

import java.lang.reflect.Field;

public class ReflectUtil {

	public static Object getFld(Object obj1, String fldname) {
		try {
			Class<?> obj = obj1.getClass();

			Field f = obj.getDeclaredField(fldname);
			f.setAccessible(true);

			return f;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static Object getFldValRE(Object obj1, String fldname) {
		try {
			Class<?> obj = obj1.getClass();

			Field f = obj.getDeclaredField(fldname);
			f.setAccessible(true);

			return f.get(obj1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static Object getFldVal(Class Class1,Object obj1, String fldname) {
		try {
		//	Class<?> obj = obj1.getClass();
			Field f ;
			try {
				  f = Class1.getDeclaredField(fldname);
				f.setAccessible(true);
			} catch (Exception e) {
				  f = Class1.getField(fldname);
					f.setAccessible(true);
				
			}

			

			return f.get(obj1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getFldVal(Object obj1, String fldname) {
		try {
			Class<?> obj = obj1.getClass();
			Field f ;
			try {
				  f = obj.getDeclaredField(fldname);
				f.setAccessible(true);
			} catch (Exception e) {
				  f = obj.getField(fldname);
					f.setAccessible(true);
				
			}

			

			return f.get(obj1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
