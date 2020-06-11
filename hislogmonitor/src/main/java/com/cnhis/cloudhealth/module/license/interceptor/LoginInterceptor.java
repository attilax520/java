package com.cnhis.cloudhealth.module.license.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cnhis.cloudhealth.module.license.entity.SysUser;


/**
 * 
 * @Title:  LoginInterceptor.java   
 * @Package com.cnhis.cloudhealth.module.license.interceptor   
 * @Description:   登录拦截
 * @author: huchaojing     
 * @date:   2018年2月13日 下午3:33:19   
 * @version V1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if(user == null){
				modelAndView.setViewName("manage/login");
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
	}


}
