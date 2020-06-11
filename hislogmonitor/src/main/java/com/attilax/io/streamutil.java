package com.attilax.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.attilax.core.Consumer;
import com.attilax.util.cli.SSHHelperCoreV2;

public class streamutil {
	   private final static Log logger =LogFactory.getLog(SSHHelperCoreV2.class);
	public static ThreadLocal<String>  stream=new ThreadLocal<>();
	public static void process(InputStream stdStream, Consumer consumer1) throws     Exception {
		String str = null;
		String charsetName = "utf-8";
		if (stdStream.available() > 0) {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(stdStream, charsetName));

			while ((str = br.readLine()) != null) {
				logger.info( stream.get()+" stream outptu:");
				consumer1.accept(str);
			}
		}
		
	}

}
