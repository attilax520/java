package com.attilax.oplog;

import com.attilax.anno.LogAnno;
import com.attilax.util.Callback;

public interface OperLogUtilInterface {

	/**
	 * for prj his 
	 * @param loginname
	 * @param content
	 * @return
	 */
	String log4postgre(String loginname, String content);
	String log4postgre(String loginname, String content,Callback Callback_hanler);
}