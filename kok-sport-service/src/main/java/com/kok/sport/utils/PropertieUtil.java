package com.kok.sport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import lombok.NonNull;

public class PropertieUtil {
	
	

	public static String getProperty(@NotNull @NonNull String propFilePath,@NotNull @NonNull String key) {
		Properties properties = new Properties();
	//	String propFilePath = "/cfg";
		InputStream inputStream = Object.class.getResourceAsStream(propFilePath);

		try {
			properties.load(inputStream);
		} catch (IOException e1) {
			ExUtilV2t33.throwExV2(e1);
		}

		String mybatisNamespace =  properties.getProperty(key);
		return mybatisNamespace;
	}

}
