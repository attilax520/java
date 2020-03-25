package org.chwin.firefighting.apiserver.core.spring.mvc;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liming on 2017/9/20.
 */

@Configuration
public class ParamsIntercpetor implements HandlerInterceptor {

    @Autowired
    private CacheService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //添加每个线程会话ID
        //ThreadVariable.getInstance().put(CONSTANTS.CURREN_SID, UUID.randomUUID().toString());
        //获取请求IP地址
        String ip = getIpAddr(request);
        ThreadVariable.getInstance().put(CONSTANTS.REQ_IP,ip);
        Cookie[] cookies = request.getCookies();
        String ssid = "";
        if(cookies != null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                String key = cookie.getName();
                String value = cookie.getValue();
                if (StringUtils.equals(CONSTANTS.COOKIE_ID, key)) {
                    ThreadVariable.getInstance().put(CONSTANTS.REQ_ID, value);
                    if (SpringUtil.isDebug() || true) {
                        ssid=value;
                    }
                }
            }
        }else{
            ssid = request.getParameter(CONSTANTS.COOKIE_ID);
        }
        if(StringUtils.isNotBlank(ssid)){
            Map uinfo = service.findUInfo(ssid);
            ThreadVariable.getInstance().put(CONSTANTS.REQ_UKEY, uinfo);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }


}
