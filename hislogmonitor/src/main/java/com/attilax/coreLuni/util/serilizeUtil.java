package com.attilax.coreLuni.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.SerializationUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

public class serilizeUtil {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Map m=Maps.newHashMap();
		m.put("kk", "vv");
		
		System.out.println(m.toString());
	//	t2(m);

	}

	private static void t2(Map m) throws FileNotFoundException {
		SerializationUtils.serialize ((Serializable) m,new FileOutputStream( "c:\\logs\\mo2.txt"+UUID.randomUUID()));
	 
		Map m2=(Map) serizGetObjFromFile("c:\\logs\\mo2.txt");
		System.out.println( JSON.toJSONString(m2));
	}

	public static void seriz(Object o,String f) {
		 try {  
	            ObjectOutputStream os = new ObjectOutputStream(  
	                    new FileOutputStream(f));  
	            os.writeObject(o);// 将User对象写进文件  
	          
	            os.close();  
	        } catch (FileNotFoundException e) {  
	           throw new RuntimeException(e);
	        } catch (IOException e) {  
	        	  throw new RuntimeException(e);
	        }  
//	        try {  
//	            ObjectInputStream is = new ObjectInputStream(new FileInputStream(  
//	                    "C:/wxp.txt"));  
//	            User temp = (User) is.readObject();// 从流中读取User的数据  
//	            System.out.println(temp.getId());  
//	            System.out.println(temp.getName());  
//	            List tempList = (List) is.readObject();// 从流中读取List的数据  
//	            for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {  
//	                System.out.print(iterator.next());  
//	            }  
//	            is.close();  
//	        } catch (FileNotFoundException e) {  
//	            e.printStackTrace();  
//	        } catch (IOException e) {  
//	            e.printStackTrace();  
//	        } catch (ClassNotFoundException e) {  
//	            e.printStackTrace();  
//	        }  
		
	}
	
	
	public static Object serizGetObjFromFile( String f) {
		 
	        try {  
	            ObjectInputStream is = new ObjectInputStream(new FileInputStream(  
	                    f));  
	            Object temp = (Object) is.readObject();// 从流中读取User的数据  
	          
	            is.close();  
	            return temp;
	        } catch (FileNotFoundException e) {  
	        	 throw new RuntimeException(e);
	        } catch (IOException e) {  
	        	 throw new RuntimeException(e);
	        } catch (ClassNotFoundException e) {  
	        	 throw new RuntimeException(e);
	        }  
		
	}

}
