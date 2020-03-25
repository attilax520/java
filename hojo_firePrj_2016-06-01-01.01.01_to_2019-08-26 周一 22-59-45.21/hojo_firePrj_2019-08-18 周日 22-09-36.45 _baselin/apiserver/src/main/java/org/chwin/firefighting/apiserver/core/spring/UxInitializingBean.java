package org.chwin.firefighting.apiserver.core.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by liming on 2017/9/15.
 */
public class UxInitializingBean implements ApplicationContextInitializer<ConfigurableApplicationContext>,ApplicationListener {

    protected ConfigurableApplicationContext app;
    private static boolean inited=false;

    public  void initialize(ConfigurableApplicationContext context){
        app=context;
        SpringUtil.setAPP(app);
    }

    public void onApplicationEvent(ApplicationEvent applicationEvent){

    }
}
