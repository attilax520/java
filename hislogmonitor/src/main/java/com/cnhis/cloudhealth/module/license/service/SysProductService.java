package com.cnhis.cloudhealth.module.license.service;

import java.util.List;

import com.cnhis.cloudhealth.module.license.entity.SysProduct;


public interface SysProductService {

	public String getZNodes(Integer corporationId);
	
	

	/**
	 * 获取当前医院已授权产品
	 * @Title: getLicenseProduct
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> getLicenseProduct(Integer corporationId);
	
	
	/**
	 * @Title: findLicenseProduct   
	 * @Description: 根据医院code以及医院名称查询授权模块API
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> findLicenseProduct(String code,String name);
	
}
