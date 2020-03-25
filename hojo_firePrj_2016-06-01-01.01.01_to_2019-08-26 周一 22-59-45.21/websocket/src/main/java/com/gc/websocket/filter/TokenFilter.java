package com.gc.websocket.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        LOGGER.info("=========================================================================================");
//        LOGGER.info(httpRequest.getMethod() + " " + httpRequest.getRequestURI());
//        Cookie[] cookies = httpRequest.getCookies();
//        if (null != cookies) {
//            for (Cookie cookie : cookies) {
//                if ("accessToken".equals(cookie.getName())) {
//                    String accessToken = cookie.getValue();
//                    if (RedisPool.client.connect().sync().exists(accessToken)) {
//                        //通过
                        chain.doFilter(request, response);
//                        return;
//                    }
//                }
//            }
//        }
//        //不通过
//        httpServletResponse.setStatus(401);
//        LOGGER.info("[Response] {}", 401);
//        LOGGER.info("=========================================================================================");
    }

    @Override
    public void destroy() {

    }
}
