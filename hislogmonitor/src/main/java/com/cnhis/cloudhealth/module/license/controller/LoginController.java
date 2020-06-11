package com.cnhis.cloudhealth.module.license.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnhis.cloudhealth.common.result.ApiCode;
import com.cnhis.cloudhealth.common.result.ApiResult;
import com.cnhis.cloudhealth.common.result.InterfaceResult;
import com.cnhis.cloudhealth.module.license.entity.SysUser;
import com.cnhis.cloudhealth.module.license.service.SysUserService;

/**
 * 用户登录
 * @Title:  LoginController.java   
 * @Package com.cnhis.cloudhealth.module.license.controller   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年1月31日 上午10:07:00   
 * @version V1.0
 */
@Controller
@RequestMapping(value="/")
public class LoginController {

	@Autowired
	private SysUserService sysUserService;
	
	
	/**
	 * 项目入口
	 * @Title: index   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/")
	public String  index(){
		return "manage/login";
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
	@RequestMapping(value="/login",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult login(SysUser sysUser,HttpSession session){
		try {
			InterfaceResult result = sysUserService.checkLogin(sysUser);
			if(result.isSuccess()){
				sysUser = (SysUser) result.getData();
				session.setAttribute("user", sysUser);
				return ApiResult.success(sysUser);
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 进入修改密码页面
	 * @Title: updatepwd   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/gopwd")
	public String  gopwd(){
		return "manage/updatepwd";
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
	@RequestMapping(value="/updatePwd",method = RequestMethod.POST)
	public @ResponseBody ApiResult updatePwd(SysUser sysUser,HttpSession session){
		try {
			InterfaceResult result = sysUserService.updatePwd(sysUser);
			if(result.isSuccess()){
				session.removeAttribute("user");
				return ApiResult.success();
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 退出登录
	 * @Title: logout   
	 * @Description: TODO(方法功能描述)
	 * @param: @param session
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/logout")
	public String  logout(HttpSession session){
		session.removeAttribute("user");
		return "manage/login";
	}
	
}
