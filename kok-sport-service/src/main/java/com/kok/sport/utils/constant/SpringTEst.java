package com.kok.sport.utils.constant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.kok.SportApplication;

//@SpringBootApplication 
//@SpringBootApplication
@ComponentScan(basePackages = {"com.kok.sport.utils.constant"})
public class SpringTEst {

	public static void main(String[] args) {
//		com.google.common.collect.ImmutableSortedSet.toImmutableSortedSet
//		org.openqa.selenium.WebDriver
//		com.google.common.collect.ImmutableSortedSet
		 ConfigurableApplicationContext  context=new SpringApplicationBuilder(). 
				 sources(SpringTEst.class).web(WebApplicationType.NONE).run(args);
		System.out.println(context.getBean(ServiceT.class));

	}

}
