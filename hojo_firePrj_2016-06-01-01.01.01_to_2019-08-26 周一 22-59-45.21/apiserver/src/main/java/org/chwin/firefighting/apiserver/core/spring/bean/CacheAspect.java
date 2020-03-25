package org.chwin.firefighting.apiserver.core.spring.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.CacheUtil;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Created by liming on 2017/10/10.
 */
@Aspect
@Configuration
public class CacheAspect {
    private LogUtil logger = new LogUtil(this.getClass());
    @Pointcut("execution(* org.chwin.firefighting.apiserver.core.service.CacheService.find*(..))")
    public void excudeService(){}

    @Around("excudeService()")
    public Object cacheAsOld(ProceedingJoinPoint point){
        Object thing=null;
        try {
            point.getArgs();
            MethodSignature signature = (MethodSignature)point.getSignature();
            Method method = signature.getMethod();
            String key = method.getName();
            for (Object value : point.getArgs()) {
                key +=  "_" + value;
            }

            if(SpringUtil.isDebug()){
                thing = point.proceed();
            }else {
                try {
                    thing = CacheUtil.instance().getKey(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (thing == null) {
                    thing = point.proceed();
                    CacheUtil.instance().putKey(key, thing);
                }
            }

            return thing;
        } catch (Throwable e) {
            e.printStackTrace ();
        }
        return thing;
    }

}
