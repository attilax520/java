package com.cnhis.cloudhealth.module.license.entity;

import com.cnhis.cloudhealth.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class SysUser extends DataEntity<SysUser>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sysAccount;
	private String sysPassword;
	private String oldPassword;
	
	private String authToken;
	
	public String getSysAccount() {
		return sysAccount;
	}
	public void setSysAccount(String sysAccount) {
		this.sysAccount = sysAccount;
	}
	
	public String getSysPassword() {
		return sysPassword;
	}
	public void setSysPassword(String sysPassword) {
		this.sysPassword = sysPassword;
	}
	
	@JsonIgnore
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
