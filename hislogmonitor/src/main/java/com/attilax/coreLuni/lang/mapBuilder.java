package com.attilax.coreLuni.lang;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class mapBuilder<k,v> {
	
	public static void main(String[] args) {
		//<String,Object>
		 
		Map<String,Object> m=	mapBuilder.<String,Object>$().put("k1","v").put("k2","v2").build();
	 	//List<String> li=	listBuilder.$().add("k1" ).add("k2" ).build();
	 	List  li=	listBuilder.<String>$().add("k1" ).add("k2" ).build();
		List<String> li2=	listBuilder.<String>$().addNoType("k1" ).addNoType("k2" ).build();
		List  li3=	listBuilder.$().addNoType("k1" ).addNoType("k2" ).build();
		System.out.println(m);System.out.println(li2);
	}

	public static  <k,v> mapBuilder<k,v>  $() {
		// TODO Auto-generated method stub
		return new  mapBuilder<k,v>();
	}

	Map<k, v> mapObj;
	public Map<k, v> build() {
		// TODO Auto-generated method stub
		return mapObj;
	}

	public   mapBuilder<k,v> put(k k1, v v1) {
		 if(mapObj==null)
			 mapObj=Maps.newLinkedHashMap();
		 mapObj.put(k1, v1);
		return this;
	}
	


}
