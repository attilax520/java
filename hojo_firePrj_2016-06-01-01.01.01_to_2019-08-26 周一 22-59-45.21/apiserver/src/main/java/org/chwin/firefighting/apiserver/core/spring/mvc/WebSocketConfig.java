package org.chwin.firefighting.apiserver.core.spring.mvc;
import org.chwin.firefighting.apiserver.fire.listener.MyWebSocketInterceptor;
import org.chwin.firefighting.apiserver.fire.listener.WebSocketPushHandler;
import org.chwin.firefighting.apiserver.fire.listener.WisdomSocketPushHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Autowired
    private MyWebSocketInterceptor myWebSocketInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(WebSocketPushHandler(), "/webSocketServer.action")
                .addInterceptors(myWebSocketInterceptor)
                .setAllowedOrigins("*");
        registry.addHandler(WisdomSocketPushHandler(), "/wisdomSocketServer.action")
                .addInterceptors(myWebSocketInterceptor)
                .setAllowedOrigins("*");
        registry.addHandler(WebSocketPushHandler(), "/sockjs/webSocketServer.action")
                .addInterceptors(myWebSocketInterceptor)
                .withSockJS();
    }
    @Bean
    public WebSocketHandler WebSocketPushHandler() {
        return new WebSocketPushHandler();
    }

    @Bean
    public WebSocketHandler WisdomSocketPushHandler() {
        return new WisdomSocketPushHandler();
    }
}
