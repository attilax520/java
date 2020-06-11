package com.cnhis.cloudhealth.module.license.entity;

import com.cnhis.cloudhealth.common.persistence.DataEntity;

/**
 * 字典表 省
 * @Title:  SysProvincial.java   
 * @Package com.cnhis.cloudhealth.module.license.entity   
 * @Description:    字典表 省
 * @author: huchaojing     
 * @date:   2018年2月7日 下午3:05:43   
 * @version V1.0
 */
public class SysProvincial extends DataEntity<SysProvincial>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String provincial;

	public String getProvincial() {
		return provincial;
	}

	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}
	
	
}
