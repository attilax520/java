package com.attilax.oplog.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	 @Override  
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
	        return builder.sources(Application.class);  
	    }  
	      
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("-8-");
	//	org.springframework.jms.config.DefaultJmsListenerContainerFactory
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8081);  
		
	}
}