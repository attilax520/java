package com.kok.sport.utils.constant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.WsSrv;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**com.kok.SportApplication
 * 服务启动类
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan({ "com.kok.sport.utils", "com.kok.sport.utils.constant", "com.kfit.user","" ,"mapper" })
////pringBootApplication(exclude ,DruidDataSourceAutoConfigure.class
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
//@SpringBootApplication
@PropertySource(value= {"classpath:/conf/errorCode.properties"})
@ComponentScan({"com.kok","com.kok.*"})
public class SportApplication
{
	  public static ConfigurableApplicationContext context;
    public static void main(String[] args) {
    	
    //	javax.servlet.http.HttpServletRequest
    	  context= new SpringApplicationBuilder(). sources(SportApplication.class).web(WebApplicationType.SERVLET) .run ( args);
    	           // .profiles("client")
    	          
    	MybatisMapper MybatisMapper1 = context.getBean(MybatisMapper.class);
    	System.out.println(MybatisMapper1.querySql("select 'frm appmain'"));
    	
    	SyncFootballNumberOddsHistoryServiceImpl cc=context.getBean(SyncFootballNumberOddsHistoryServiceImpl.class);
cc.insertFootballNumberOddsData(3382750L);
    	//    	try {
//    		int wsport=Integer.parseInt(args[0].trim()) ;
//        	WsSrv.startWebsocketService(wsport);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		 
//			 WsSrv.startWebsocketService(5202);
//			 
//			
//		}
    	System.out.println("f");
    	
    	
    	
    }
}
