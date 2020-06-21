package com.kok.sport.utils.mockdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.sport.utils.db.MybatisUtil;

import ognl.Ognl;
@SuppressWarnings("all")
public class typeTest {

	public static void main(String[] args) throws IOException, Exception {
		List li = jenronIndex();

		System.out.println(JSON.toJSONString(li, true));
		System.out.println(li);
		
		    
		String s="select type,data from football_tlive_t_ex group by type";
		
	//	FileUtils.write(new File("D:\\type.json"), JSON.toJSONString(MybatisUtil.executeSql(s), true));
	 	


org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
Iterable<Object> result  =yaml.loadAll(new FileInputStream(new File("C:\\Users\\Administrator\\OneDrive\\Documents\\4231.yml")));
for (Object obj : result) {
	LinkedHashMap lm=Maps.newLinkedHashMap();
	  
 
  
Object x = Ognl.getValue("playr.x", obj);
 // lm.putAll("x",x);
System.out.println(x);
}
	System.out.println("f");
	
	
	LinkedHashMap lm1=Maps.newLinkedHashMap();
	LinkedHashMap lm2=Maps.newLinkedHashMap();
	lm1.put("x", 1);
	lm2.put("x", 2);
	li.add(lm1);li.add(lm2);
System.out.println(yaml.dump(li) );	
//	Object expression = Ognl.parseExpression(prpt);
////  Ognl.get
//  Object url = Ognl.getValue(prpt, mObject);
//  return url.toString();
	}

	private static List jenronIndex() {
		List li=Lists.newArrayList();
		LinkedHashMap m1=Maps.newLinkedHashMap();
		m1.put("y",17);
		m1.put("x",50);
		m1.put("type","守门员");
		///houwei 4
		LinkedHashMap m2=Maps.newLinkedHashMap();
		m2.put("y",34);
		m2.put("x",20);
		m2.put("type","后卫");
		
		LinkedHashMap m3=Maps.newLinkedHashMap();
		m3.put("y",34);
		m3.put("x",40);
		m3.put("type","后卫");
		
		LinkedHashMap m4=Maps.newLinkedHashMap();
		m4.put("y",34);
		m4.put("x",60);
		m4.put("type","后卫");
		
		LinkedHashMap m5=Maps.newLinkedHashMap();
		m5.put("y",34);
		m5.put("x",80);
		m5.put("type","后卫");
		
		////////
		
		LinkedHashMap m6=Maps.newLinkedHashMap();
		m6.put("y",50);
		m6.put("x",33);
		m6.put("type","后卫");
		LinkedHashMap m7=Maps.newLinkedHashMap();
		m7.put("y",50);
		m7.put("x",66);
		m7.put("type","后卫");
		
		//////42   33333----------------
		LinkedHashMap m8=Maps.newLinkedHashMap();
		m8.put("y",67);
		m8.put("x",25);
		m8.put("type","中锋");
		LinkedHashMap m9=Maps.newLinkedHashMap();
		m9.put("y",67);
		m9.put("x",50);
		m9.put("type","中锋");
		LinkedHashMap m10=Maps.newLinkedHashMap();
		m10.put("y",67);
		m10.put("x",75);
		m10.put("type","中锋");
		
		////lat 1
		LinkedHashMap m11=Maps.newLinkedHashMap();
		m11.put("y",83);
		m11.put("x",50);
		m11.put("type","前锋");
		
		li.add(m1);
		li.add(m2);
		li.add(m3);
		li.add(m4);
		li.add(m5);
		li.add(m6);
		li.add(m7);
		li.add(m8);
		li.add(m9);
		li.add(m10);li.add(m11);
		return li;
	}

}
