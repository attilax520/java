package com.cnhis.cloudhealth.module.license.dao;

import java.util.List;

import com.cnhis.cloudhealth.common.persistence.annotation.MyBatisDao;
import com.cnhis.cloudhealth.module.license.entity.SysProvincial;

@MyBatisDao
public interface SysProvincialDao {
	
	/**
	 * @Title: findAll   
	 * @Description: 查询所有省
	 * @param: @return
	 * @return: List<SysProvincial>
	 * @throws
	 */
	public List<SysProvincial> findAll();
	
	
}
