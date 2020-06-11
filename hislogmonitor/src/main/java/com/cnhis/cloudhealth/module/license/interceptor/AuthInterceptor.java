package com.cnhis.cloudhealth.module.license.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cnhis.cloudhealth.common.result.ApiCode;
import com.cnhis.cloudhealth.common.result.ApiResult;
import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.utils.JsonMapper;


/**
 * @Title:  AuthInterceptor.java   
 * @Package com.cnhis.cloudhealth.module.license.interceptor   
 * @Description:    认证授权拦截器
 * @author: huchaojing     
 * @date:   2018年2月13日 下午3:26:52
 * @version V1.0
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
    	// 从请求中得到token
        String token = getToken(request);
        
        if (!checkToken(request, token)) {
        	// 验证token失败，则返回直接返回用户未登录错误
        	errorResponse(response, ApiResult.fail(ApiCode.USER_NOT_LOGIN));
        	return false;
        }

        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

    }
    
    /**
     * 获取token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
    	 String token = request.getHeader("X-Auth-Token");
    	 if (StringUtils.isEmpty(token)) {
    		 token = request.getParameter("X-Auth-Token");
    	 }
    	 return token;
    }
    
    /**
     * 错误输出
     * @param response
     * @param apiResult
     */
    public void errorResponse(HttpServletResponse response, ApiResult apiResult) throws IOException {
        response.setContentType("application/json; charset=utf-8");  
        PrintWriter out = response.getWriter();
        out.println(JsonMapper.nonDefaultMapper().toJson(apiResult));
        out.flush();
        out.close();
    }
    
    private boolean checkToken(HttpServletRequest request,String token) {
    	HttpSession session = request.getSession();
    	String sessionToken = (String)session.getAttribute("X-Auth-Token");
   	 	if (StringUtils.isEmpty(sessionToken) && StringUtils.isEmpty(token)) {
   	 		return false;
   	 	}
   	 	if(!sessionToken.equals(token)){
   	 		return false;
   	 	}
   	 	return true;
   }
}
