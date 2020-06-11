package com.cnhis.cloudhealth.module.license.service;

import java.util.List;

import com.cnhis.cloudhealth.common.persistence.Page;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;
import com.cnhis.cloudhealth.module.license.vo.LicenseJSON;
import com.cnhis.cloudhealth.module.license.vo.SysBlocVo;


public interface SysCorporationService {
	
	/**
	 * 查询最大code值
	 * @Title: findMaxCode   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	public String findMaxCode();

	/**
	 * 查询所有企业医院
	 * @Title: findAll   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: Page<SysCorporation>      
	 * @throws
	 */
	public Page<SysCorporation> findAll(Page<SysCorporation> page,SysCorporation sysCorporation);
	
	/**
	 * 添加企业或集团
	 * @Title: save   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void      
	 * @throws
	 */
	public void save(SysCorporation sysCorporation);
	
	/**
	 * 根据ID查询企业或集团
	 * @Title: getCorporation   
	 * @Description: TODO(方法功能描述)
	 * @param: @param id
	 * @param: @return
	 * @return: SysCorporation
	 * @throws
	 */
	public SysCorporation getCorporation(Integer id);
	
	/**
	 * 校验子模块是否授权
	 * @Title: checkLicenseToken   
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: boolean      
	 * @throws
	 */
	public boolean checkLicenseToken(String code,String token);
	
	/**
	 * 添加授权证书名称
	 * @Title: addCredentialName   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void      
	 * @throws
	 */
	public void addCredentialName(SysCorporation sysCorporation);
	
	
	/**
	 * 查询所有集团医院
	 * @Title: findBlocAll   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysBlocVo
	 * @param: @return
	 * @return: Page<SysBlocVo>      
	 * @throws
	 */
	public Page<SysBlocVo> findBlocAll(Page<SysBlocVo> page,SysBlocVo sysBlocVo);
	
	/**
	 * 查询所有集团医院
	 * @Title: findBlocAll   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysBlocVo
	 * @param: @return
	 * @return: Page<SysBlocVo>      
	 * @throws
	 */
	public List<SysBlocVo> findBlocAllName(SysBlocVo sysBlocVo);
	
	
	/**
	 * 查询某个集团企业医院
	 * @Title: findByBlocCorPage   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: Page<SysCorporation>      
	 * @throws
	 */
	public Page<SysCorporation> findByBlocCorPage(Page<SysCorporation> page, SysCorporation sysCorporation);
	
	/**
	 * 查询需要授权提醒的个数
	 * @Title: remindLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: Integer      
	 * @throws
	 */
	public Integer remindLicense();
	
	
	/**
	 * 
	 * @Title: findByCorporation   
	 * @Description: 根据医院编码以及名称查询医院信息 并验证授权成功与否
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: SysCorporation      
	 * @throws
	 */
	public LicenseJSON checkLicenseApi(String code,String name);
	
}
