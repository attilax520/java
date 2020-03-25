package org.chwin.firefighting.apiserver.core.spring;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by liming on 2017/9/18.
 */
@Component
public class SpringUtil {
    private static ApplicationContext app = null;

    public static ApplicationContext getApp() {
        return app;
    }

    public static void setAPP(ApplicationContext context){
        app=context;
    }

    public static Object getBean(String name) {
        return getApp().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApp().getBean(clazz);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApp().getBean(name, clazz);
    }

    public static String getProp(String prop){
        PropVarb pv=(PropVarb)SpringUtil.getBean("propvarb");
        return pv.getProp(prop);
    }

    public static boolean isDebug(){
        String mode=getProp("spring.application.app_mode");
        if(StringUtils.equalsIgnoreCase(mode,"DEBUG"))
            return true;
        return false;
    }

}

