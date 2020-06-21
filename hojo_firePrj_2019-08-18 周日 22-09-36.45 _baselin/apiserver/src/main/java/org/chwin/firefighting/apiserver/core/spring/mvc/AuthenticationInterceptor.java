package org.chwin.firefighting.apiserver.core.spring.mvc;

import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author GJW
 * @since 2017/10/13.
 * <p>验证用户是否有菜单访问权限，是否有按钮功能权限，是否有数据修改权限</p>
 */
@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final LogUtil logger = new LogUtil(this.getClass());
    @Autowired
    private CacheService service;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //logger.info("mvc:[request authentication] begin");
        //请求路径
 //       String moduleUrl = StringUtils.remove( httpServletRequest.getRequestURI(),httpServletRequest.getContextPath());
        //请求IP
 //       String requestIp = (String) ThreadVariable.getInstance().get(CONSTANTS.REQ_IP);
   /*     //登陆的用户信息
        Map uinfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        String SID = (String) uinfo.get(CONSTANTS.REQ_ID);
        logger.info("mvc:[request authentication] end");
        List permsList = (List)CacheUtil.instance().getKey(SID);
        Map inputData = (Map) ThreadVariable.getInstance().get("_INPUT_DATA");
        if(inputData  != null){

            String code = (String) inputData.get("CODE");
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}