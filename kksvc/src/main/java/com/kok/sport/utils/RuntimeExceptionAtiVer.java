package com.kok.sport.utils;

import java.util.Map;

public class RuntimeExceptionAtiVer extends RuntimeException {

	public Object dbginfo;

	public RuntimeExceptionAtiVer() {
		// TODO Auto-generated constructor stub
	}

	public Object getDbginfo() {
		return dbginfo;
	}

	public void setDbginfo(Object dbginfo) {
		this.dbginfo = dbginfo;
	}

	public RuntimeExceptionAtiVer(String message) {
		super(message);
		 
	}

	public RuntimeExceptionAtiVer(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public RuntimeExceptionAtiVer(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RuntimeExceptionAtiVer(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RuntimeExceptionAtiVer(Object dbginfo2) {
		this.dbginfo=dbginfo2;
	}

	public RuntimeExceptionAtiVer(Exception e, Object dbginfo2) {
		super(e);
		this.dbginfo=dbginfo2;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
