package org.chwin.firefighting.apiserver.core.spring.mvc;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.service.LogService;
import org.chwin.firefighting.apiserver.core.util.Encryption;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志请求拦截
 * <p>根据配置表，记录用户请求日志</p>
 *
 * @author wangjunma
 * @version $Id: LogInterceptor,v 0.1 2017/10/23 16:14 Exp $$
 */
@Configuration
public class LogInterceptor implements HandlerInterceptor {
    private final LogUtil logger = new LogUtil(this.getClass());
    // 日志配置记录读取缓存Service
    @Autowired
    private CacheService cacheService;
    // 日志记录Service
    @Autowired
    private LogService logService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        //请求路径
        String moduleUrl = StringUtils.remove( httpServletRequest.getRequestURI(),httpServletRequest.getContextPath());
        Map<String, String> paramMap =new HashMap<>(4);
        paramMap.put("ACTION_URL",moduleUrl);
        Map<String, Object> resultMap = cacheService.findLogCfg(paramMap);
        //创建日志对象
        if (null != resultMap && !resultMap.isEmpty()) {
            Map contentMap = (Map) ThreadVariable.getInstance().get("_INPUT_DATA");
            boolean isLogin = CONSTANTS.LOGIN.equals(((HandlerMethod)handler).getMethod().getName());
            if(isLogin){
                if(MapUtils.isEmpty(contentMap)){
                    contentMap = new HashMap(4);
                }
                String userName = httpServletRequest.getParameter("uname");
                String un = Encryption.des_decrypt(userName,"12345678910");
                contentMap.put("ACCOUNT",un);
            }
            logService.addLog(resultMap,contentMap);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        if(e != null){
            String ip = httpServletRequest.getRemoteAddr();
            String url = httpServletRequest.getRequestURI();
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("REQUEST_IP",ip);
            resultMap.put("DESCRIPTION",url);
            resultMap.put("EXCEPTION",e.getMessage());
            resultMap.put("STATUS","1");
            logService.addLogWithoutRule(resultMap);
        }
    }
}
