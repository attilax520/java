package com.cnhis.cloudhealth.module.license.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnhis.cloudhealth.common.result.InterfaceResult;
import com.cnhis.cloudhealth.common.utils.GsonUtils;
import com.cnhis.cloudhealth.module.license.dao.SysCorporationDao;
import com.cnhis.cloudhealth.module.license.dao.SysLicenseDao;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;
import com.cnhis.cloudhealth.module.license.entity.SysLicense;


@Service
@Transactional(readOnly = true)
public class SysLicenseServiceImpl implements SysLicenseService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysCorporationDao sysCorporationDao;
	
	@Autowired
	private SysLicenseDao sysLicenseDao;

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
	@Transactional(readOnly = false)
	public InterfaceResult saveLicense(SysCorporation sysCorporation,String productArr) {
		logger.info("授权开始：基础数据：参数：{},授权模块：参数：{}",GsonUtils.toJson(sysCorporation),productArr);
		sysCorporation.setCreateDate(new Date());
		sysCorporation.setDelFlag("0");
		sysCorporationDao.save(sysCorporation);
		logger.info("基础数据添加成功，返回ID：{}",sysCorporation.getId());
		
		saveCorporationLicense(productArr,sysCorporation.getId());
		
		return InterfaceResult.success();
	}
	
	/**
	 * 添加集团
	 * @Title: saveBloc   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param productArr
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	@Transactional(readOnly = false)
	public InterfaceResult saveBloc(SysCorporation sysCorporation) {
		logger.info("授权开始：基础数据：参数：{},授权模块：参数：{}",GsonUtils.toJson(sysCorporation));
		if(null == sysCorporation.getId()){
			sysCorporation.setCreateDate(new Date());
			sysCorporation.setDelFlag("0");
			sysCorporationDao.save(sysCorporation);
			logger.info("基础数据添加成功，返回ID：{}",sysCorporation.getId());
		}else{
			sysCorporation.setUpdateDate(new Date());
			sysCorporation.setUpdateBy(sysCorporation.getUpdateBy());
			sysCorporationDao.update(sysCorporation);
			logger.info("基础数据修改成功，返回ID：{}",sysCorporation.getId());
		}
		return InterfaceResult.success();
	}
	
	/**
	 * 企业授权模块添加
	 * @Title: saveCorporationLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @param productArr
	 * @param: @param userId
	 * @return: void      
	 * @throws
	 */
	@Transactional(readOnly = false)
	private void saveCorporationLicense(String productArr,Integer corporationId) {
		if(!StringUtils.isEmpty(productArr)){
			String [] productId = productArr.split(",");
			for (int i = 0; i < productId.length; i++) {
				SysLicense sysLicense = new SysLicense();
				sysLicense.setCorporationId(corporationId);
				sysLicense.setProductId(Integer.parseInt(productId[i]));
				sysLicenseDao.save(sysLicense);
			}
		}
	}

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
	@Transactional(readOnly = false)
	public InterfaceResult editLicense(SysCorporation sysCorporation,String productArr) {
		
		logger.info("重新授权开始：基础数据：参数：{},授权模块：参数：{}",GsonUtils.toJson(sysCorporation),productArr);
		
		sysCorporation.setUpdateDate(new Date());
		sysCorporationDao.update(sysCorporation);
		logger.info("基础数据编辑成功，返回ID：{}",sysCorporation.getId());
		
		//先删除已授权的产品
		sysLicenseDao.delProduct(sysCorporation.getId());
		//重新添加授权产品
		saveCorporationLicense(productArr,sysCorporation.getId());
		
		return InterfaceResult.success();
	}
	
	
	/**
	 * 根据集团id修改集团信息
	 * @Title: updateBloc   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	@Transactional(readOnly = false)
	public InterfaceResult updateBloc(SysCorporation sysCorporation){
		sysCorporationDao.update(sysCorporation);
		return InterfaceResult.success();
	}

	/**
	 * 批量删除集团下的企业
	 * @Title: delBlocCorporation   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void
	 * @throws
	 */
	@Transactional(readOnly = false)
	public InterfaceResult delBlocCorporation(SysCorporation sysCorporation) {
		sysCorporationDao.delBlocCorporation(sysCorporation);
		return InterfaceResult.success();
	}
	
}
