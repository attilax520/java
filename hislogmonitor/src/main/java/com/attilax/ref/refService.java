package com.attilax.ref;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.attilax.compress.ZipUtil;
import com.attilax.util.ExUtil;
import com.google.common.collect.Lists;

public class refService {

	public static Class loadclass(String f, String clsname) {
		
		try {
			Class c = null;
			File file = new File(f);
			URL url = file.toURI().toURL();// 将File类型转为URL类型，file为jar包路径
			// 得到系统类加载器
			URLClassLoader urlClassLoader_getSystemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			// 因为URLClassLoader中的addURL方法的权限为protected所以只能采用反射的方法调用addURL方法
			Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			add.setAccessible(true);
			add.invoke(urlClassLoader_getSystemClassLoader, new Object[] { url });

			c = Class.forName(clsname);
			return c;
		} catch (Exception e) {
			ExUtil.throwExV2(e);
		}
		// 或者
		// Class c=urlClassLoader.loadClass("类名");
	    throw new RuntimeException("class load ex:"+clsname +",f:"+f);
		
	}

		

	public static List<String> getClassName(String filePath) {
		if(!filePath.toLowerCase().endsWith(".jar"))
			if(!filePath.toLowerCase().endsWith(".zip"))
				throw new RuntimeException("file fmt err:"+filePath);
		List<String> clses=Lists.newArrayList();
		List<String> myClassName = ZipUtil.unzip_filelistV2(filePath);
		for (String f : myClassName) {
			if(!f.endsWith(".class"))
				continue;
			f=f.replaceAll("/", ".");
			int endDotpos=f.lastIndexOf(".");
			f=f.substring(0, endDotpos);
			clses.add(f);
		}

		return clses;
	}

}
