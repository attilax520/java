package com.attilax.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.log4j.Logger;

import com.attilax.io.DirTraveService;
import com.attilax.io.PathService;
import com.attilax.ref.refService;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.base.Function;

@SuppressWarnings("all")
public class PackageUtil {
	public static Logger logger = Logger.getLogger(Cls1.class);

	public static void main(String[] args) {

		String packageName = "com.itkt.mtravel.hotel";
		// com.google.common.annotations.Beta
		String f = "C:\\Users\\attilax\\Desktop\\beked\\miniPrjjars\\guava-17.0.jar";
		// guava-17.0.jar

		new DirTraveService().traveV4Vs427("C:\\Users\\attilax\\Desktop\\beked\\miniPrjjars",
				new Function<File, Object>() {

					@Override
					public Object apply(File f) {
						logger.info("arg0:" + f.getAbsolutePath());

						try {
							List<String> classNames = refService.getClassName(f.getAbsolutePath());
							for (String className : classNames) {
								try {
									Class cls = refService.loadclass(f.getAbsolutePath(), className);
									String clstree = clsTree(cls);
									logger.info("className:" + className);
									logger.info("clstree:" + clstree);
								} catch (Throwable e) {
									logger.error(e);
								}

							}

						} catch (Throwable e) {

							e.printStackTrace();
							logger.error(e);
						}
						return null;
					}
				}, new Function<Integer, Object>() {

					@Override
					public Object apply(Integer cnt_index) {
						// Map m=Maps.newConcurrentMap();m.put("index",
						// cnt_index)
						logger.info("cnt_index:" + cnt_index);
						return null;
					}
				});

		// Class cls=

		// List<String> getClassName = getClassName(f);
		// for (String clsname : getClassName) {
		// Class cls = refService.loadclass(f, clsname);
		// System.out.println(cls);
		// }

	}

	private static String clsTree(Class cls ) {
		if(cls.toString().contains("ConcurrentRuntimeException"))
			System.out.println("dbg");
		Class parent = cls.getSuperclass();
		if (parent == Object.class)
			return parent.getName() + ">" + cls.getName();
		else if (parent == null) // interface
			return "null>" +   cls.getName();
		else {
		 
			return clsTree(parent)+">"+cls.getName();
		}
	}

	public static List<String> getClassName(String packageName) {
		String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
		List<String> fileNames = getClassName(filePath, null);
		return fileNames;
	}

	private static List<String> getClassName(String filePath, List<String> className) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (!childFile.getAbsolutePath().toLowerCase().endsWith(".jar"))
				continue;
			if (childFile.isDirectory()) {
				myClassName.addAll(getClassName(childFile.getPath(), myClassName));
			} else {
				String childFilePath = childFile.getPath();
				childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9,
						childFilePath.lastIndexOf("."));
				childFilePath = childFilePath.replace("\\", ".");
				myClassName.add(childFilePath);
			}
		}

		return myClassName;
	}
}