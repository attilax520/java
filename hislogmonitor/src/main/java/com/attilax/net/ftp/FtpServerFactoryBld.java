package com.attilax.net.ftp;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.usermanager.impl.BaseUser;

public class FtpServerFactoryBld {

	private FtpServerFactory ftpServerFactory;
	private UserManager getUserManager;

	public FtpServerFactoryBld(FtpServerFactory ftpServerFactory) {
		this.ftpServerFactory=ftpServerFactory;
	}

	public static FtpServerFactoryBld $() {
		 
		return new FtpServerFactoryBld( new FtpServerFactory());
	}

	public FtpServerFactoryBld getUserManager() {
		this.getUserManager=ftpServerFactory.getUserManager();
		return this;
	}

	public FtpServerFactoryBld save(BaseUser baseUser) {
		try {
			this.getUserManager.save(baseUser);
		} catch (FtpException e) {
			throw new RuntimeException(e);
		}
		return this;
		
	}

	public FtpServerFactory build() {
		// TODO Auto-generated method stub
		return this.ftpServerFactory;
	}

	public FtpServerFactoryBld addListener(String name, Listener listener) {
		 this.ftpServerFactory.addListener(name, listener);
		return this;
	}

}
