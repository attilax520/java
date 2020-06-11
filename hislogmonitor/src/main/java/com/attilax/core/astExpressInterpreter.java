package com.attilax.core;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.ast.ClassInstanceCreation;
import com.attilax.ast.Expression;
import com.attilax.ast.MethodInvocation;
import com.attilax.ast.SimpleName;
import com.attilax.collection.listBuilder;
import com.attilax.parser.Token;
import com.attilax.util.ExUtil;

public class astExpressInterpreter {

	public class interpret {

	}

	public static void main(String[] args) {
		
		System.out.println("ff");

//		String s = "new  com.attilax.core.methodRunner  \"string\" contstuParamVal .  methDync \"string\"   haha";
//		String[] a = s.split(" ");
//		List li = listBuilder.$(a).trimElement().delEmptyElement().build();
//		
//		List<Token> li_tokens = (List<Token>) new TokenizeV8s528().TokenizeProcess(li);
//
//		com.attilax.ast.Expression astExp = new AstBuilderV4s529().buildAstV9s528(li_tokens);
//
//		System.out.println(JSON.toJSONString(astExp, true));
//
//		Object interpret_rzt = new astExpressInterpreter().interpret(astExp);
//		System.out.println("rzt:" + interpret_rzt);
//		;

	}

	public Object interpret(Expression astExp) {
		try {
			return interpret_throwDefEx(astExp);
		} catch (Exception e) {
			ExUtil.throwExV2(e);
		}
		return null;

	}

	private Object interpret_throwDefEx(Expression astExp) throws Exception {
		if (astExp instanceof MethodInvocation) {
			return MethodInvocationInterpreter_itprt(astExp);
		} else if (astExp instanceof ClassInstanceCreation) {
			return ClassInstanceCreationIntrepreter_itprt(astExp);
		} else if (astExp instanceof SimpleName) {

			return SimpleNameInterpreter_itprt(astExp);
		}
		return astExp;
	}

	private Object SimpleNameInterpreter_itprt(Expression astExp) throws ClassNotFoundException {
		SimpleName sn = (SimpleName) astExp;
		Class cls = Class.forName(sn.IDENTIFIER);

		return cls;
	}

	@SuppressWarnings("all")
	private Object ClassInstanceCreationIntrepreter_itprt(Expression astExp) throws Exception {
		ClassInstanceCreation cic = (ClassInstanceCreation) astExp;
		Class cls = Class.forName(cic.name);
		 
		Object o = ConstructorUtils.invokeConstructor(cls, cic.arguments.toArray());
		return o;
	}

	private Object MethodInvocationInterpreter_itprt(Expression astExp) throws Exception {
		
		
		MethodInvocation mi = (MethodInvocation) astExp;
		if(mi.Name.equals("spaceinfo"))
			System.out.println("dbg");
		
		if (mi.Exp instanceof ClassInstanceCreation) {
			Object obj = interpret(mi.Exp); // obj installed
			return MethodUtils.invokeMethod(obj, mi.Name, mi.arguments.toArray());

		} else if (mi.Exp instanceof SimpleName) {
			Class cls = (Class) interpret(mi.Exp); // obj installed
			return MethodUtils.invokeStaticMethod(cls, mi.Name, mi.arguments.toArray());
		}
		return null;
	}

}
