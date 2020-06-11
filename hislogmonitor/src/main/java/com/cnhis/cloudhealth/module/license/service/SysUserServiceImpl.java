package com.cnhis.cloudhealth.module.license.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnhis.cloudhealth.common.persistence.Page;
import com.cnhis.cloudhealth.common.result.InterfaceResult;
import com.cnhis.cloudhealth.common.result.business.LoginFailed;
import com.cnhis.cloudhealth.common.utils.GsonUtils;
import com.cnhis.cloudhealth.common.utils.Md5Utils;
import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.license.dao.SysUserDao;
import com.cnhis.cloudhealth.module.license.entity.SysUser;


@Service
public class SysUserServiceImpl implements SysUserService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserDao sysUserDao;
	
	public Page<SysUser> findAll(Page<SysUser> page,SysUser sysUser){
		sysUser.setPage(page);
		List<SysUser> list = sysUserDao.findAll();
		page.setList(list);
		return page;
	}

	/**
	 * 账户登录检测
	 * @Title: checkLogin   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: SysUser      
	 * @throws
	 */
	public InterfaceResult checkLogin(SysUser sysUser) {
		logger.info("登录参数:{}",GsonUtils.toJson(sysUser));
		
		if(sysUser == null){
			return InterfaceResult.failed(LoginFailed.SYSUSER_ERROR_FAIL);
		}
		
		String password = sysUser.getSysPassword();
		
		if(StringUtils.isEmpty(sysUser.getSysAccount())){
			return InterfaceResult.failed(LoginFailed.ACCOUNT_NULL_FAIL);
		}
		if(StringUtils.isEmpty(password)){
			return InterfaceResult.failed(LoginFailed.PASSWORD_NULL_FALL);
		}
		
		sysUser.setSysPassword(Md5Utils.encryptionMd5(password));
		SysUser user = sysUserDao.checkLogin(sysUser);
		if(user == null){
			return InterfaceResult.failed(LoginFailed.ACCOUNT_OR_PASSWORD_ERROR_FAIL);
		}
		return InterfaceResult.success(user);
	}

	/**
	 * 密码修改
	 * @Title: updatePwd   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: int      
	 * @throws
	 */
	public InterfaceResult updatePwd(SysUser sysUser) {
		
		if(sysUser == null){
			return InterfaceResult.failed(LoginFailed.UPDATE_ERROR_FAIL);
		}
		String oldPassword = sysUser.getOldPassword();
		
		if(StringUtils.isEmpty(oldPassword)){
			return InterfaceResult.failed(LoginFailed.OLDPASSWORD_NULL_FALL);
		}
		sysUser.setOldPassword(Md5Utils.encryptionMd5(sysUser.getOldPassword()));
		Integer num = sysUserDao.checkPwd(sysUser);
		if(num == null || num == 0){
			return InterfaceResult.failed(LoginFailed.OLDPASSWORD_NULL_FALL);
		}
		
		String password = sysUser.getSysPassword();
		
		if(StringUtils.isEmpty(sysUser.getId().toString())){
			return InterfaceResult.failed(LoginFailed.UPDATE_ERROR_FAIL);
		}
		
		if(StringUtils.isEmpty(password)){
			return InterfaceResult.failed(LoginFailed.UPDATE_ERROR_FAIL);
		}
		
		sysUser.setSysPassword(Md5Utils.encryptionMd5(password));
		
		int cn = sysUserDao.updatePwd(sysUser);
		if(cn > 0){
			return InterfaceResult.success();
		}
		return InterfaceResult.error();
	}
	
	
}
