package com.gc.websocket.aspect;

/**
 * ========================
 *
 * @Author : shenhuaming
 * @Email :
 * @Date : 2019/8/12
 * ========================
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Aspect
@Configuration
@Order(1)
@Component
public class LogAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut(value = "execution(public * com.gc.websocket..controller.*.*(..))")
    public void devicesService() {
    }

    @Before("devicesService()")
    public void doBefore(JoinPoint joinPoint) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.info("=========================================================================================");
        LOGGER.info(request.getMethod() + " " + request.getRequestURI());
        Map<String, String[]> map = request.getParameterMap();
        if (map.size() > 0) {
            LOGGER.info("[ParameterMap] {}", new JSONObject(request.getParameterMap()).toString());
        }
        LOGGER.info("[Remote.IP] {}", request.getRemoteAddr());
        LOGGER.info("[CLass.Method] {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()");
        LOGGER.info("[Args] {}",Arrays.toString(joinPoint.getArgs()));
    }

    @After("devicesService()")
    public void doAfter() {

    }

    @AfterReturning(returning = "ret", pointcut = "devicesService()")
    public void doAfterReturning(Object ret) throws Throwable {
        LOGGER.info("[Response] {}", ret);
        LOGGER.info("=========================================================================================");
    }
}
