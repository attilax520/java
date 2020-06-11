package com.cnhis.cloudhealth.module.license.service;

import com.cnhis.cloudhealth.common.result.InterfaceResult;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;



public interface SysLicenseService {
	
	/**
	 * 企业授权
	 * @Title: saveLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param productArr
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	public InterfaceResult saveLicense(SysCorporation sysCorporation,String productArr);
	
	/**
	 * 企业重新授权
	 * @Title: editLicense
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param productArr
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	public InterfaceResult editLicense(SysCorporation sysCorporation,String productArr);
	
	/**
	 * 添加集团
	 * @Title: saveBloc   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	public InterfaceResult saveBloc(SysCorporation sysCorporation);
	
	/**
	 * 更具集团id修改集团信息
	 * @Title: updateBloc   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	public InterfaceResult updateBloc(SysCorporation sysCorporation);
	
	/**
	 * 批量删除集团下的企业
	 * @Title: delBlocCorporation   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: InterfaceResult      
	 * @throws
	 */
	public InterfaceResult delBlocCorporation(SysCorporation sysCorporation);
	
}
