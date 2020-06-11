package com.attilax.ref;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.http.client.utils.URLEncodedUtils;

import com.alibaba.fastjson.JSONObject;
import com.attilax.codec.codeUtil;
import com.attilax.compress.ZipUtil;
import com.attilax.core.ForeachFunction;
import com.attilax.io.DirTraveService;
//import com.attilax.parser.Token;
import com.attilax.util.ExUtil;
import com.attilax.util.josnutil;
import com.google.common.collect.Lists;

/**
 * v2 del zip dep
 * 
 * @author attilax
 *
 */
@SuppressWarnings("all")
public class refServiceV2s530 {

	public static void main(String[] args)

	throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String classfile = "C:\\0wkspc\\ftpserverati\\WebContent\\WEB-INF\\classes\\com\\attilax\\util\\clsNodep.class";
		classfile = "C:\\0wkspc\\ftpserverati\\WebContent\\WEB-INF\\classes";
		Class cls = refServiceV2s530.loadclassV2(classfile, "com.attilax.util.clsNodep");

		List<Object> paramsList = Lists.newArrayList();
		paramsList.add(null);
		MethodUtils.invokeStaticMethod(cls, "main", paramsList.toArray());
		System.out.println("--f");
	}

	public static Object getArg(String cur_token, int paramIdx2, String[] paramtypes) throws Exception {
		String ptype = paramtypes[paramIdx2];
		return getArg(cur_token, ptype);
	}

	private static Object getArg(String cur_token, String ptype) throws Exception {
		if (ptype.equals("int"))
			return Integer.parseInt(cur_token.toString());

		if (ptype.equals("map")) {

			String arg = codeUtil.paramDecode_comma(cur_token);
			JSONObject jo = JSONObject.parseObject(arg.toString());
			return (Map) jo;
		}
		// return Integer.parseInt(cur_token.toString());
		if (ptype.equals("str"))
			return cur_token;

		// def new create（）。bean corp copy
		Class c = Class.forName(ptype);
		Object obj = c.newInstance();
		cur_token = codeUtil.paramDecode_comma(cur_token);
		josnutil.copyMapJsonProp2ObjBean(cur_token, obj);

		return obj;
	}

	// public static Object getArg(Token cur_token, int paramIdx2, String[]
	// paramtypes) {
	// String ptype = paramtypes[paramIdx2];
	// if (ptype.equals("int"))
	// return Integer.parseInt(cur_token.Text.toString());
	//
	// return cur_token.Text;
	// }
	/**
	 * @my hah
	 * @author attilax
	 * @since s530
	 * @param f
	 *            jar file or class file dir,,cant be single classfile path
	 * @param clsname
	 * @return cls
	 */
	public static Class loadclassV2(String f, String clsname) {

		try {
			Class c = null;
			loadjar(f);
			// urlClassLoader_getSystemClassLoader.
			c = Class.forName(clsname);
			return c;
		} catch (Exception e) {
			ExUtil.throwExV2(e);
		}
		// 閹存牞锟�
		// Class c=urlClassLoader.loadClass("缁鎮�);
		throw new RuntimeException("class load ex:" + clsname + ",f:" + f);

	}

	public static Class loadclass(String f, String clsname) {

		try {
			Class c = null;
			loadjar(f);
			// urlClassLoader_getSystemClassLoader.
			c = Class.forName(clsname);
			return c;
		} catch (Exception e) {
			ExUtil.throwExV2(e);
		}
		// 閹存牞锟�
		// Class c=urlClassLoader.loadClass("缁鎮�);
		throw new RuntimeException("class load ex:" + clsname + ",f:" + f);

	}

	public static void loadclass(String classDir) {

		try {
			Class c = null;
			loadjar(classDir);
			// urlClassLoader_getSystemClassLoader.

		} catch (Exception e) {
			ExUtil.throwExV2(e);
		}

	}

	private static void loadjar(String f)
			throws MalformedURLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		File file = new File(f);
		if (!file.exists())
			throw new RuntimeException("file not exist:" + f);
		URL url = file.toURI().toURL();// 鐏忓挾ile缁鐎锋潪顑胯礋URL缁鐎烽敍瀹杋le娑撶皜ar閸栧懓鐭惧锟�
		// 瀵版鍩岀化鑽ょ埠缁濮炴潪钘夋珤
		URLClassLoader urlClassLoader_getSystemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		// 閸ョ姳璐烾RLClassLoader娑擃厾娈慳ddURL閺傝纭堕惃鍕綀闂勬劒璐焢rotected閹碉拷浜掗崣顏囧厴闁插洨鏁ら崣宥呯殸閻ㄥ嫭鏌熷▔鏇＄殶閻⑩暆ddURL閺傝纭�
		Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		add.setAccessible(true);
		add.invoke(urlClassLoader_getSystemClassLoader, new Object[] { url });
	}

	public static void loadJarFromDirtrave(String jardir) {
		new DirTraveService().traveV5_vS522(new File(jardir), new ForeachFunction() {

			@Override
			public void each(int count, File strFileName) throws Exception {
				loadjar(strFileName.getAbsolutePath());

			}
		});

	}

	public static Class loadclassFromCurClassloader(String clsname) {
		// TODO Auto-generated method stub
		try {
			return Class.forName(clsname);
		} catch (ClassNotFoundException e) {
			ExUtil.throwExV2(e);
		}
		return null;
	}

	// public static List<String> getClassName(String filePath) {
	// if (!filePath.toLowerCase().endsWith(".jar"))
	// if (!filePath.toLowerCase().endsWith(".zip"))
	// throw new RuntimeException("file fmt err:" + filePath);
	// List<String> clses = Lists.newArrayList();
	// List<String> myClassName = ZipUtil.unzip_filelistV2(filePath);
	// for (String f : myClassName) {
	// if (!f.endsWith(".class"))
	// continue;
	// f = f.replaceAll("/", ".");
	// int endDotpos = f.lastIndexOf(".");
	// f = f.substring(0, endDotpos);
	// clses.add(f);
	// }
	//
	// return clses;
	// }

}
