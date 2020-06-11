package com.cnhis.cloudhealth.module.license.dao;

import org.apache.ibatis.annotations.Param;

import com.cnhis.cloudhealth.common.persistence.annotation.MyBatisDao;
import com.cnhis.cloudhealth.module.license.entity.SysLicense;

@MyBatisDao
public interface SysLicenseDao {
	
	/**
	 * 添加医院和产品授权
	 * @Title: save   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysLicense
	 * @return: void      
	 * @throws
	 */
	public void save(SysLicense sysLicense);
	
	/**
	 * 根据企业ID删除已授权模块
	 * @Title: delProduct   
	 * @Description: TODO(方法功能描述)
	 * @param: @param corporationId
	 * @return: void      
	 * @throws
	 */
	public void delProduct(@Param("corporationId") Integer corporationId);
	
}
