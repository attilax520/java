package com.attilax.net.ftp;

import java.util.List;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.usermanager.impl.BaseUser;

public class BaseUserBldr {

	private BaseUser baseUser;

	public BaseUserBldr(BaseUser baseUser) {
		this.baseUser=baseUser;
	}

	public static BaseUserBldr $() {
		// TODO Auto-generated method stub
		return new BaseUserBldr(new BaseUser());
	}

	public BaseUserBldr setName(String string) {
		 this.baseUser.setName(string);
		return this;
	}

	public BaseUserBldr setHomeDirectory(String string) {
		 this.baseUser.setHomeDirectory(string);
		return this;
	}

	public BaseUserBldr setAuthorities(List<Authority> Authorities) {
		 this.baseUser.setAuthorities(Authorities);
		return this;
	}

	public BaseUser build() {
		// TODO Auto-generated method stub
		return this.baseUser;
	}

}
