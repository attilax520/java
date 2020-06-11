package com.cnhis.cloudhealth.module.license.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cnhis.cloudhealth.common.persistence.annotation.MyBatisDao;
import com.cnhis.cloudhealth.module.license.entity.SysProduct;

@MyBatisDao
public interface SysProductDao {
	
	/**
	 * 查询所有产品
	 * @Title: findAll   
	 * @Description: TODO(方法功能描述)
	 * @param: @param corporationId
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> findAll(@Param("corporationId") Integer corporationId);
	
	/**
	 * 查询所有父类
	 * @Title: findParent   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> findParent();
	
	
	
	/**
	 * 获取当前医院已授权产品
	 * @Title: getLicenseProduct
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> getLicenseProduct(@Param("corporationId") Integer corporationId,@Param("parentid") Integer parentid);
	
	/**
	 * @Title: findLicenseProduct   
	 * @Description: 根据医院code以及医院名称查询授权模块API
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> findLicenseProduct(@Param("code") String code,@Param("name") String name);
	
}
