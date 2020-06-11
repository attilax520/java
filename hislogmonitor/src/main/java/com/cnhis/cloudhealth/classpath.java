

package com.cnhis.cloudhealth;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;

public class classpath {
	
	public static void main(String[] args) throws IOException {
		//+File.separator
		String path = org.apache.log4j.PropertyConfigurator.class.getResource("/").getPath()+"log4j.properties";
		org.apache.log4j.PropertyConfigurator.configureAndWatch( path  , 5000);
		System.out.println(path);
		String  t=FileUtils.readFileToString(new File(path));
		System.out.println(t);
		
	}

}
