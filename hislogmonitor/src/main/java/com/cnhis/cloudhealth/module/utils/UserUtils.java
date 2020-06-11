package com.cnhis.cloudhealth.module.utils;

import javax.servlet.http.HttpSession;

import com.cnhis.cloudhealth.module.license.entity.SysUser;

/**
 * 获取当前登录人信息
 * @Title:  UserUtils.java   
 * @Package com.cnhis.cloudhealth.module.utils   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年2月1日 下午12:58:27   
 * @version V1.0
 */
public class UserUtils {

	
	/**
	 * 获取用户信息实体
	 * @Title: getSysUser   
	 * @Description: TODO(方法功能描述)
	 * @param: @param session
	 * @param: @return
	 * @return: SysUser      
	 * @throws
	 */
	public static SysUser getSysUser(HttpSession session){
		SysUser user = (SysUser) session.getAttribute("user");
		if(user != null){
			return user;
		}
		return null;
	}
	
	/**
	 * 获取用户ID
	 * @Title: getSysUserId   
	 * @Description: TODO(方法功能描述)
	 * @param: @param session
	 * @param: @return
	 * @return: Integer      
	 * @throws
	 */
	public static Integer getSysUserId(HttpSession session){
		SysUser user = (SysUser) session.getAttribute("user");
		if(user != null){
			return user.getId();
		}
		return null;
	}
	
    /**
     * 校验当前用户是否已分配token授权
     * @param request
     * @return
     */
//    private boolean checkToken(HttpServletRequest request) {
//    	 String token = request.getHeader("X-Auth-Token");
//    	 if (StringUtils.isEmpty(token)) {
//    		 token = request.getParameter("X-Auth-Token");
//    	 }
//    	String sessionToken = (String) request.getSession().getAttribute("token");
//    	if(token.equals(sessionToken)){
//    		return true;
//    	}
//    	 return false;
//    }
}
