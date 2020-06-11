
package com.attilax.ast;

import java.io.File;
 

public class InherTree {
	public static void main(String[] args) {
		File rootFile = new File(InherTree.class.getResource("/").getFile().replaceFirst("/", ""));

		setSonList(rootFile, rootFile.getPath() + "\\", ASTNode.class);
	}

	public static <T> void setSonList(File rootFile, String parentDirectory, Class<T> parentClass) {
		if (rootFile.isDirectory()) {
			File[] files = rootFile.listFiles();
			for (File file : files) {
				setSonList(file, parentDirectory, parentClass);
			}
		} else {  //file
			String className = null;
			try {
				if (rootFile.getPath().indexOf(".class") != -1) {
					className = rootFile.getPath().replace(parentDirectory, "").replace(".class", "").replace("\\", ".");
					Class<?> classObject = Class.forName(className);
					classObject.asSubclass(parentClass);

					System.out.println(className + " is subclass of " + parentClass + " ");
				}
			} catch (ClassNotFoundException e) {
				System.err.println("cant fine class " + className);
			} catch (ClassCastException e) {
				System.err.println(className + " not sub of  " + parentClass + " ");
			}
		}
	}
}

class Sub1 extends InherTree {

}

class Sub2 extends InherTree {

}
