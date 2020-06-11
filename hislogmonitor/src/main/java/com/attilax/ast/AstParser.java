/**
 * 
 */
package com.attilax.ast;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.attilax.exception.ExUtil;
import com.google.common.collect.Lists;

/**
 * @author attilax 2016骞�9鏈�19鏃� 涓嬪崍4:03:14
 */
public class AstParser {

	public static void main(String[] args) {

	}

	private Object rzt;

	// public Object parse;
	public Object parse(Expression ast) {

		MethodInvocation mi = (MethodInvocation) ast;
		 if (mi.Exp instanceof SimpleName) {
			Object rztttt = SimpleName_staticProcess(ast);
			return rztttt;
		} else if (mi.Exp instanceof ClassInstanceCreation) {
			Object rzt3 = ClassInstanceCreationProcess(ast);
			return rzt3;
		}else if (mi.Exp instanceof MethodInvocation) // for method chain
		{
			Object rzt_tmp_obj = parse(mi.Exp);  //recurse  circur
			List arguments = mi.arguments;
			TypeLiteraltypeParamProcess(arguments);
			
			Object[] params = arguments.toArray();

			try {
				Object rzt = MethodUtils.invokeMethod(rzt_tmp_obj, mi.Name,
						params);
				return rzt;
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException e) {
				ExUtil.throwExV2(e);
			}

		} 
		// return this.rzt;
		return " $null";
	}

	/**
	attilax    2016年10月24日  下午1:39:11
	 * @param arguments
	 */
	private void TypeLiteraltypeParamProcess(List arguments) {
		for (int i = 0; i < arguments.size(); i++) {
			Object object = arguments.get(0);
			if (object instanceof TypeLiteral)
				try {
					object = Class.forName(((TypeLiteral) object).type);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("ClassNotFoundException:"
							+ object);
				}
		}

	}

//	private Object parseDetail(Expression ast) {
//		MethodInvocation mi=(MethodInvocation) ast;
//		if (mi.Exp instanceof SimpleName) {
//			SimpleName_staticProcess(ast);
//		}else if(mi.Exp instanceof ClassInstanceCreation)
//		{
//			ClassInstanceCreationProcess(ast);
//		} 
//		return this.rzt;
//	}

	private Object ClassInstanceCreationProcess(Expression ast) {
		MethodInvocation mi = (MethodInvocation) ast;
		Object[] params = mi.arguments.toArray();
		
		Class<?> threadClazz = null;
		ClassInstanceCreation cic=(ClassInstanceCreation) mi.Exp;
		//MethodUtils.in
		Object o;
		try {
			o = ConstructorUtils.invokeConstructor(

					Class.forName(cic.name), null);
			rzt =	MethodUtils.invokeMethod(o, mi.Name, params);
		} catch (NoSuchMethodException | IllegalAccessException
				| InvocationTargetException | InstantiationException
				| ClassNotFoundException e) {
			ExUtil.throwExV2(e);
		}
		return rzt;
		
	}

	/**
	 * static
	 * @param ast
	 * @return
	 */
	private Object SimpleName_staticProcess(Expression ast) {
		MethodInvocation mi = (MethodInvocation) ast;
		Expression exp = mi.Exp;
		Class<?> threadClazz = null;
		if (exp instanceof SimpleName) {
			SimpleName sn = (SimpleName) exp;
			try {
				threadClazz = Class.forName(sn.IDENTIFIER);
			} catch (ClassNotFoundException e) {
				ExUtil.throwExV2(e);
			}

		}
		
		mi.arguments=	ParamProcess(mi.arguments);
		
		Object[] params = mi.arguments.toArray();
		 
			try {
				rzt = MethodUtils.invokeStaticMethod(threadClazz, mi.Name,
						params);
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException e) {
				ExUtil.throwExV2(e);
			}catch( Exception e)
			{
				String msg = "atiex:maybe method not exist,cls:"+threadClazz+",methName:"+ mi.Name;
				System.out.println(msg);
				ExUtil.throwExV2(e,msg );
			}
		  return rzt;
		// Method method = threadClazz.getMethod(, long.class);
		// System.out.println(method.invoke(null, -10000l));
	}

	private List ParamProcess(List arguments) {
		// TypeLiteraltypeParamProcess(mi.arguments);
		List li = Lists.newArrayList();
		for (Object object : arguments) {
			Object obj2 = null;
			if (object instanceof ArrayCreation) {
				obj2 = getArr((ArrayCreation) object);

			} else if (object instanceof TypeLiteral) {
				try {
					obj2 = Class.forName(((TypeLiteral) object).type);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("ClassNotFoundException:"
							+ object);
				}
			}else
				obj2=object;
			
			li.add(obj2);
		}

		return li;
	}

	private Object getArr(ArrayCreation ac) {
		 if(ac.arrayType.equals("string"))
		 {
			 
			 List<String> li=Lists.newArrayList();
			 List li_tmp=ac.optionalInitializer.expressions;
			 for (Object object : li_tmp) {
				li.add((String) object);
			 }
			 
			 return li.toArray(new String[ li.size()]);
			 
		 }
		return null;
	}

}
