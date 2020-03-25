package org.chwin.firefighting.apiserver.core.spring.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by liming on 2017/9/20.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private ParamsIntercpetor paramsIntercpetor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    //日志拦截请求
    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 设置session区域解析器，默认为中文
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paramsIntercpetor).addPathPatterns("/**").excludePathPatterns("/pos/**", "/app/**","/iot/**","/file/**","/ceshi/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/sys/**", "/file/**", "/pos/**", "/app/**","/iot/**","/file/**","/ceshi/**"
        );
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");

        /*registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/sys/**", "/file/**", "/logout", "/pos/**", "/app/**","/iot/**","/file/**","/ceshi/**"
        );*/
        super.addInterceptors(registry);
    }
}
