package com.attilax.net.ftp;

import java.util.concurrent.Callable;

public class lambdaRunner {

	public static <V> V run(Callable<V>  Callable1) {
		// TODO Auto-generated method stub
		try {
			return Callable1.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
