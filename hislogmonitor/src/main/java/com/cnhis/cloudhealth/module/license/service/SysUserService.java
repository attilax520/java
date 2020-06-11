package com.cnhis.cloudhealth.module.license.service;

import com.cnhis.cloudhealth.common.persistence.Page;
import com.cnhis.cloudhealth.common.result.InterfaceResult;
import com.cnhis.cloudhealth.module.license.entity.SysUser;


public interface SysUserService {

	public Page<SysUser> findAll(Page<SysUser> page,SysUser sysUser);
	
	
	/**
	 * 账户登录检测
	 * @Title: checkLogin   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: SysUser      
	 * @throws
	 */
	public InterfaceResult checkLogin(SysUser sysUser);
	
	/**
	 * 密码修改
	 * @Title: updatePwd   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: int      
	 * @throws
	 */
	public InterfaceResult updatePwd(SysUser sysUser);
	
}
