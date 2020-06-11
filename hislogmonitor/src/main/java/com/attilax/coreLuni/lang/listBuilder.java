package com.attilax.coreLuni.lang;

import java.util.List;

import com.google.common.collect.Lists;

public class listBuilder<v> {
List<v> liObj=Lists.newArrayList();
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

}
