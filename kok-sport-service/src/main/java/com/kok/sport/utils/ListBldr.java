package com.kok.sport.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ListBldr {
	   List m=Lists.newArrayList();
//		static MapBldr  MapBldr1=new MapBldr();

		public   ListBldr add(  Object v) {
			m.add(v);
			return this;
		}

		public List build() {
		 
			return this.m;
		}

		public static ListBldr newx() {
			ListBldr MapBldr1=new ListBldr();
			return MapBldr1;
		}
}
