package com.kok.sport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haitian
 * @Desc Swagger 配置
 * @date 2020-02-18
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder ticketPars = new ParameterBuilder();
        ticketPars.name("Authorization")
                .description("登录token,注意空格(Bearer tokenId)")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build(); //header中的ticket参数非必填，传空也可以    	pars.add(ticketPar.build())
        pars.add(ticketPars.build());

/*        ParameterBuilder ticketPars1 = new ParameterBuilder();
        ticketPars1.name("sessionId")
                .description("设备号,sessionId")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build(); //header中的ticket参数非必填，传空也可以    	pars.add(ticketPar.build())
        pars.add(ticketPars1.build());
        pars.add(new ParameterBuilder().name("service-origin").description("service-origin").modelRef(new ModelRef("string")).parameterType("header").required(false).build());*/
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kok.sport.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("KOK平台用户服务")
/*                .contact(new Contact("3a", "http://www.3a.com", "y3-oo@ik8s.com"))*/
                .version("0.0.1")
                .build();
    }
}
