package com.kok.sport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mysql.cj.core.conf.url.ConnectionUrlParser;

public class Cfg {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/cfg");
        
            properties.load(inputStream);
       
        String mysqlConnUrl = properties.get("db").toString();
		ConnectionUrlParser connStringParser = ConnectionUrlParser.parseConnectionString(mysqlConnUrl);
		System.out.println(connStringParser);


	}

}
