package com.attilax.coreLuni.lang;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetX {

	public static Set<String> fromLi(List li, String string) {
		Set st=new HashSet ();
		for (Object object : li) {
			Map m=(Map) object;
			st.add(m.get(string));
		}
		return st;
	}

}
