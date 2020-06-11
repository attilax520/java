package com.cnhis.cloudhealth.module.license.service;

import java.util.List;

import com.cnhis.cloudhealth.module.license.entity.SysProvincial;


public interface SysProvincialService {

	/**
	 * @Title: findAll   
	 * @Description: 查询所有省
	 * @param: @return
	 * @return: List<SysProvincial>
	 * @throws
	 */
	public List<SysProvincial> findAll();
	
	
}
