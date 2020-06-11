package com.attilax.ref;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.collection.listBuilder;
import com.attilax.collection.mapBuilder;


/**
 * 
 * {
	"Exp":{
		"arguments":[
			"contstuParamVal"
		],
		"jsonname":"ClassInstanceCreation",
		"name":"com.attilax.core.methodRunner",
		"paramtypes":["\"string\""]
	},
	"Name":"methDync",
	"arguments":[
		"haha"
	],
	"exp":{"$ref":"$.Exp"},
	"jsonname":"MethodInvocation",
	"paramtypes":["\"string\""]
}
 * @author attilax
 *
 */
public class refServiceV2s530Test {

	@Test
	public void test() {
		  Map staticSimplenameExp=mapBuilder.$().put("beanname", "Simplename").put("name", "EmbeddedTomcat").build();
		  List arguments=listBuilder.$().add("c:\\xxxdir").add(1315).build();  //add("")
		  Map m=mapBuilder.$().put("beanname", "MethodInvocation").put("exp", staticSimplenameExp).put("name", "openWebdav").put("paramtypes", "str,int").put("arguments", arguments).build();
		  System.out.println(JSON.toJSONString(m,true));
	}

}
