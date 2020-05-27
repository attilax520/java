package com.kok.sport.config.security;

import com.kok.auth.config.CustomTokenExtractor;
import com.kok.auth.exception.BusinessAccessDeniedHandler;
import com.kok.auth.exception.BusinessAuthExceptionEntryPoint;
import com.kok.sport.utils.constant.AntUrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import java.util.Iterator;

/**
 * 资源服务配置 @ EnableResourceServer 启用资源服务 @ EnableWebSecurity 启用web安全 @
 * EnableGlobalMethodSecurity 启用全局方法安全注解，就可以在方法上使用注解来对请求进行过滤
 *
 * @author haitian
 * @version 0.1
 * @date 2020年02月22日下午3:51:55
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${spring.profiles.active}")
    private String active = "dev";
    @Autowired
    private BusinessAccessDeniedHandler accessDeniedHandler;

    /**
         * 令牌存储
     *
     * @return redis令牌存储对象
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new BusinessAuthExceptionEntryPoint())
                .accessDeniedHandler(accessDeniedHandler)
                .tokenExtractor(new CustomTokenExtractor(AntUrlConstants.URL));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry ExpressionInterceptUrlRegistry= http.authorizeRequests();
        ExpressionInterceptUrlRegistry
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/sport/**").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/csrf").permitAll()
                .antMatchers("/query**").permitAll()
                .antMatchers("/addData**").permitAll() 
                .antMatchers("/editData**").permitAll()
                .antMatchers("/biz**").permitAll()
                .antMatchers("/queryPage**").permitAll()
                .antMatchers("/queryPage**/**").permitAll()
                .antMatchers("/queryPageTrad/**").permitAll()
                .antMatchers("/queryPageTrad/**/**").permitAll()
                .antMatchers("/queryPageTrad/*").permitAll()
                .antMatchers("/queryPageTrad/football_odds_t_ex").permitAll()
                
                .anyRequest().permitAll()//
               
                
                .antMatchers("/").permitAll()
                .and().anonymous() //对于没有配置权限的其他请求允许匿名访问
                ;
        Iterator<String> url = AntUrlConstants.URL.iterator();
        while (url.hasNext())
            ExpressionInterceptUrlRegistry.antMatchers(url.next()).permitAll();
        ExpressionInterceptUrlRegistry.anyRequest().authenticated();
        // 无需认证的接口 start
        http.csrf().disable().httpBasic();
    }
}
