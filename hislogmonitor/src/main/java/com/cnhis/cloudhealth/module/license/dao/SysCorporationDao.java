package com.cnhis.cloudhealth.module.license.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cnhis.cloudhealth.common.persistence.annotation.MyBatisDao;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;
import com.cnhis.cloudhealth.module.license.vo.SysBlocVo;

@MyBatisDao
public interface SysCorporationDao {
	
	
	
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
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: List<SysCorporation>      
	 * @throws
	 */
	public List<SysCorporation> findAll(SysCorporation sysCorporation);
	
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
	 * 修改企业或集团
	 * @Title: save   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void      
	 * @throws
	 */
	public void update(SysCorporation sysCorporation);
	
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
	 * 根据ID查询企业或集团
	 * @Title: getCorporation   
	 * @Description: TODO(方法功能描述)
	 * @param: @param id
	 * @param: @return
	 * @return: SysCorporation
	 * @throws
	 */
	public SysCorporation getCorporation(@Param("id") Integer id);
	
	/**
	 * 查询所有集团医院
	 * @Title: findAll   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysBlocVo
	 * @param: @return
	 * @return: List<SysBlocVo>      
	 * @throws
	 */
	public List<SysBlocVo> findBlocAll(SysBlocVo sysBlocVo);
	
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
	 * @Description: 根据医院编码以及名称查询医院信息 
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: SysCorporation      
	 * @throws
	 */
	public SysCorporation findByCorporation(@Param("code") String code,@Param("name") String name);
	
	
	/**
	 * 批量删除集团下的企业
	 * @Title: delBlocCorporation   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void
	 * @throws
	 */
	public void delBlocCorporation(SysCorporation sysCorporation);
	
	
}
