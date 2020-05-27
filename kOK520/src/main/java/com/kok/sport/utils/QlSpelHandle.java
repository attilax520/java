package com.kok.sport.utils;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * <pre>
 * </pre>
 *
 * @author saligia
 * @date 17-10-10
 */
public class QlSpelHandle extends AbstractHandler {

    /**
     * <pre>
     * </pre>
     *
     * @author
     * @param target              - 目标请求，可以是一个URI或者是一个转发到这的处理器的名字
     * @param request             - Jetty自己的没有被包装的请求，一个可变的Jetty请求对象
     * @param httpServletRequest  - 被filter或者servlet包装的请求，一个不可变的Jetty请求对象
     * @param httpServletResponse - 响应，可能被filter或者servlet包装过
     * @return
     */
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        System.out.println("target  :" + target);
        System.out.println("request : " + request.getRequestURI());
        System.out.println("requestServ : " + httpServletRequest.getRequestURI());

/**
 * target  :/
request : /
requestServ : /
 */
        /**
         * target  :/run
request : /run
requestServ : /run
         */
        
        ExpressionParser parser = new SpelExpressionParser();

        String e=httpServletRequest.getParameter("e");
		Expression exp = parser.parseExpression(e);
       
        Object value = exp.getValue();
		System.out.println(value);
		
		
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = httpServletResponse.getWriter();

        out.println(value);
        request.setHandled(true);
    }
}

 