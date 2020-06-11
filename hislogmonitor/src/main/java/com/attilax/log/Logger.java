package com.attilax.log;

public interface Logger {

	void log(String string);

	void err(Exception e);

	void log(Exception e);

}
