package com.attilax.ref;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.attilax.parser.Token;
import com.google.common.collect.Lists;


//com.attilax.ref.methodRunnerByclassfile
@SuppressWarnings("all")
public class methodRunnerByclassfile {
	// ¡¢¡¢C:\0wkspc\webdavroot\bin\EmbeddedTomcat.class
	// -cpx "C:\\0wkspc\\webdavroot\\bin" jardir
	// "C:\\0wkspc\\webdavroot\\libtomcart" -class EmbeddedTomcat -method
	// openWebdav -paramtypes 'str,int' -arguments "C:\\0wkspc" 1315
	// -cpx "C:\\0wkspc\\webdavroot\\bin" -jardir
	// "C:\\0wkspc\\webdavroot\\libtomcart" -class EmbeddedTomcat -method
	// openWebdav -paramtypes 'str,int' -arguments "C:\\0wkspc" 1315
	public static void main(String[] args)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {

		Options options = new Options();
		options.addOption("cpx", true, "class folder path.");
		options.addOption("jardirx", true, "jardir.");
		options.addOption("class", true, "class need.");
		options.addOption("method", true, "method need.");
		options.addOption("paramtypes", true, "paramtypes need.");
		options.addOption("arguments", true, "arguments need.");
		options.addOption("none", false, "class need.");

		CommandLineParser parser = new DefaultParser(); // new GnuParser();
		CommandLine cmd = parser.parse(options, args);
		String cpx = cmd.getOptionValue("cpx");
		// if (cmd.hasOption("dir"))

		// String
		// classfile="C:\\0wkspc\\ftpserverati\\WebContent\\WEB-INF\\classes\\com\\attilax\\util\\clsNodep.class";

		String clsname = cmd.getOptionValue("class");
		String meth = cmd.getOptionValue("method");
		String jardir = cmd.getOptionValue("jardirx");
		refServiceV2s530.loadJarFromDirtrave(jardir);
		Class cls = refServiceV2s530.loadclass(cpx, clsname);
		List<Object> paramsList = Lists.newArrayList();
		String arguments = cmd.getOptionValue("arguments");
		String paramtypes = cmd.getOptionValue("paramtypes");
		String[] a=arguments.split(",");
		int n=0;
		for (String string : a) {
			Object p=refServiceV2s530.getArg(string, n, paramtypes.split(","));
			paramsList.add(p);
			n++;
		}
		

		MethodUtils.invokeStaticMethod(cls, meth, paramsList.toArray());

		System.out.println("--f");
	}
	
	


}
