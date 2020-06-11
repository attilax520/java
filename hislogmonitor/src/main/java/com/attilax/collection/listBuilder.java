package com.attilax.collection;

import java.util.List;

import com.attilax.text.strService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
 

public class listBuilder<v> {
List<v> liObj=Lists.newArrayList();
private String strObj;
	public listBuilder(List<String> li) {
		liObj=(List<v>) li;
}
	public listBuilder() {
		// TODO Auto-generated constructor stub
	}
	public listBuilder(String strOri) {
		// TODO Auto-generated constructor stub
	}
	public static <v> listBuilder<v> $() {
		 
		return new listBuilder<v>();
	}
	public listBuilder<v> add(v string) {
		liObj.add(string);
		return this;
	}
	public List<v> build() {
		// TODO Auto-generated method stub
		return liObj;
	}
	public listBuilder addNoType(Object string) {
		liObj.add((v) string);
		return this;
	}
	 
	
	
	public String[]  toStrArr() {
		 

		String[] strings = new String[liObj.size()];

		liObj.toArray(strings);
		return strings;
	}
	public strService join(String ch) {
		String result = Joiner.on(ch).join(liObj);
		this.strObj=result;
		return new strService(result);
	}
	public listBuilder trimElement() {
		@SuppressWarnings("rawtypes")
		List lis=Lists.newArrayList();
		for (v val : liObj) {
			String vals=val.toString().trim();
			lis.add(vals);
		}
		this.liObj=lis;
		return this;
	}
	public static listBuilder $(String[] a) {
		// TODO Auto-generated method stub
		return null;
	}

}
