package com.attilax.core;

import java.util.List;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.common.collect.Lists;

public class methodRunner {
	
//	public methodRunner() {
//		// TODO Auto-generated constructor stub
//	}
	
	public methodRunner(String p) {
		System.out.println("constu");
	}
	
	public methodRunner() {
		// TODO Auto-generated constructor stub
	}

	//C:\0wkspc\ftpserverati\WebContent\WEB-INF\classes\com\attilax\core\methodRunner.class
//java -classpath C:\0wkspc\ftpserverati\WebContent\WEB-INF\classes -Djava.ext.dirs=C:\0wkspc\ftpserverati\WebContent\WEB-INF\lib  com.attilax.core.methodRunner static  com.attilax.core.methodRunner  methStatic "string"   haha
// invokeStaticMethod  com.attilax.core.methodRunner  methStatic "string"   haha
//	invokeMethod  com.attilax.core.methodRunner   methDync "string"   hahax
//	invokeMethod  com.attilax.core.methodRunner  "string" contstuParamVal .  methDync "string"   haha
	public static void main(String[] args) throws Exception {
		//System.out.println(new  methodRunner());
		String static_new = args[0].trim();
		String classname = args[1].trim();
		String me = args[2].trim();
		String argtype = args[3].trim();
		List<Object> params = getaparams(args);
		Object[] prams_objs=params.toArray();
		Class cls = Class.forName(classname);
		if (static_new.equals("invokeStaticMethod")) {
			MethodUtils.invokeStaticMethod(cls, me, prams_objs);
		} else {
			Object obj = cls.newInstance();
			MethodUtils.invokeMethod(obj, me, prams_objs);
		}

	}

	public static List<Object> getaparams(String[] args) {
		String argtype = args[3].trim();
		String[] argtype_a=argtype.split(",");
		List<Object> params = Lists.newArrayList();
		for (int i = 4; i < 20; i++) {
			try {
				String string = args[i];
				params.add(string);
			} catch (ArrayIndexOutOfBoundsException e) {
			    break;
			}
		
		}
		List<Object> params_rzt = Lists.newArrayList();
		//===============param type convert
		for ( int i = 0; i < params.size(); i++ ) {
			Object p = params.get(i);
			String ptype=argtype_a[i];
			if(ptype.equals("int"))
				p=Integer.parseInt(p.toString());
			
			params_rzt.add(p);
		}
		return params_rzt;
	}

	public static void String(String p) {
		System.out.println(p);
	}
	public static void methStatic(String p) {
		System.out.println(p);
	}

	public Object methDync(String p) {
		System.out.println("methDync"+p);
		return "methDyncrzt";
	}

}
