package org.chwin.firefighting.apiserver.net;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.chwin.firefighting.apiserver.data.Processor;
import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns="/**",filterName="UrlFilter")
public class UrlFilter implements Filter{

    //排除不拦截的url
    private static final String[] excludePathPatterns = { "/stuInfo/getAllStuInfoA"};
    static final String[] incPathPatterns = { "/startProcess"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        String host_webapp_preFix="http://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath();
        Processor.HostAppPrefix=host_webapp_preFix;
        System.out.println(host_webapp_preFix);


        // 获取请求url地址，不拦截excludePathPatterns中的url
        String url = req.getRequestURI();
        if (Arrays.asList(excludePathPatterns).contains(url)) {
            //放行，相当于第一种方法中LoginInterceptor返回值为true
            chain.doFilter(request, response);
            return;
        }


        System.out.println("开始拦截了................");
        //业务代码()
        if (Arrays.asList(incPathPatterns).contains(url)) {
            //resume other filter
        //    chain.doFilter(request, response);
            Processor pxc=new Processor();
            pxc.req=req;pxc.res=res;
            String r=  pxc.startProcess(req.getParameter("process_name"));

            res.getWriter().write(r);
            res.getWriter().flush();
            res.flushBuffer();
            return;
        }
//        if( url.equals("/device/get") && new File("d:\\iottestxx").exists())
//        {
//            String mqMsg= FileUtils.readFileToString(new File("D:\\0mq\\iot_part.json"));
//            res.getWriter().write(mqMsg);
//            res.getWriter().flush();
//            res.flushBuffer(); return;
//        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
