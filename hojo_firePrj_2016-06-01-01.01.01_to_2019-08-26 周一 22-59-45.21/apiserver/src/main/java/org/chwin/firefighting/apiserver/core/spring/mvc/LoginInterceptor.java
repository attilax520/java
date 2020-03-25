package org.chwin.firefighting.apiserver.core.spring.mvc;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.controller.BaseSupport;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.Encryption;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by liming on 2017/9/20.
 * <p>登录、权限拦截器</p>
 * 拦截所有请求的，验证当前请求用户是否处于登录状态； 根据{@code 'app_model'}是否是Debug模式，
 * 解析请求的参数，放入线程变量{@code "BaseSuport.putInputParam"}。
 */

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    private final LogUtil logger = new LogUtil(this.getClass());

    @Autowired
    private CacheService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        if (StringUtils.equals(request.getMethod(), "GET")) return true;

        Object bean = method.getBean();
        if (bean instanceof BaseSupport) {
            BaseSupport controller = (BaseSupport) bean;
            Map param = controller.getInputJSON(request);
            logger.debug("input data:" + param);
            if (SpringUtil.isDebug()) {
                debugHandle(controller, param);
            } else {
                releaseHandle(controller, param);
            }
        }
        return true;
    }

    private void debugHandle(BaseSupport controller, Map param) {
        controller.putInputParam(param);
    }

    private void releaseHandle(BaseSupport controller, Map param) {
        String sid = (String) param.get("sid");
        String data = (String) param.get("data");
        Number timstamp = (Number) param.get("timestamp");
        String sign = (String) param.get("sign");

        Map uinfo = service.findUInfo(sid);
        if (sid == null || uinfo == null)
            throw new BusinessException("RE.LOGIN", CONSTANTS.RE_LOGIN_CODE);

        Long now = (new Date()).getTime();
        if (timstamp == null || (Math.abs(now - timstamp.longValue()) > (CONSTANTS.OVERDUE_TIME)))
            throw new BusinessException("TIME_ERROR now value:" + now + "! tiemstamp value: " + timstamp.longValue());

        StringBuffer buffer = new StringBuffer();
        buffer.append(sid).append(timstamp).append(data);
        String svSign = Encryption.md5(buffer.toString());
        logger.debug(svSign);
        if (!StringUtils.equalsIgnoreCase(svSign, sign))
            throw new BusinessException("SIGN_ERROR");

        ThreadVariable.getInstance().put(CONSTANTS.REQ_UKEY, uinfo);

        String key = uinfo.get("ACCOUNT") + sid;
        String json = Encryption.des_decrypt(data, key);
        logger.debug(json);

        controller.putInputParam(JSONParser.json2Map(json));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
