package org.chwin.firefighting.apiserver.core.service;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.exception.ExceptionEncoder;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 代理服务，用来解耦服务与服务网之间的直接依赖关系
 * <p>具体说明</p>
 *
 * @author cailm
 * @version $Id: ProxyService,v 0.1 2017/12/8 9:11 Exp $$
 */

@Component
public class ProxyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object invoke(String func, Object... args) {
        String[] path = StringUtils.split(func, ".");

        String beanName = path[0];
        String methodName = path[1];
        if (path.length < 2) return null;

        try {
/*            int len=args.length;
            Class[] clz=new Class[len];
            for(int i=0;i<len;i++){
                Object arg=args[i];
                if(arg==null)
                    throw new BusinessException("PARAM_IS_NULL");
                 clz[i]=arg.getClass();
            }
*/
            Object bean = SpringUtil.getBean(beanName);

            Method fun = null;// ReflectionUtils.findMethod(bean.getClass(), methodName, clz);
            Class searchType = bean.getClass();
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                Class[] ptypes = method.getParameterTypes();
                String modifiers= Modifier.toString(method.getModifiers());
                if (StringUtils.containsIgnoreCase(modifiers,"public") &&  methodName.equals(method.getName())) {
                    fun = method;
                    break;
                }
            }
            if (fun == null)
                throw new BusinessException("FUN NOT EXISTS "+func , func);

            Object rtn = ReflectionUtils.invokeMethod(fun, bean, args);
            return rtn;
        } catch (Exception e) {
            logger.error("proxy error:",e);
            BusinessException be = ExceptionEncoder.findTypeException(e, BusinessException.class);
            if (be != null)
                throw be;
            else
                throw new BusinessException("PROXY_CALL_FAIL", func);
        }
    }
}
