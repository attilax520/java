package com.cnhis.cloudhealth.module.license.dao;

import java.util.List;

import com.cnhis.cloudhealth.common.persistence.annotation.MyBatisDao;
import com.cnhis.cloudhealth.module.license.entity.SysUser;

@MyBatisDao
public interface SysUserDao {
	
	public List<SysUser> findAll();
	
	/**
	 * 账户登录检测
	 * @Title: checkLogin   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: SysUser      
	 * @throws
	 */
	public SysUser checkLogin(SysUser sysUser);
	
	/**
	 * 检测原始密码是否正确
	 * @Title: checkPwd   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: Integer      
	 * @throws
	 */
	public Integer checkPwd(SysUser sysUser);
	
	
	
	/**
	 * 密码修改
	 * @Title: updatePwd   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysUser
	 * @param: @return
	 * @return: int      
	 * @throws
	 */
	public int updatePwd(SysUser sysUser);
}
