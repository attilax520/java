package com.kok.sport.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
 
/**
 * Fastjson配置，
 *
 * 把spring-boot默认的json解析器由Jenkins换为fastjson
 * 
 */
@Configuration
public class FastjsonConfiguration {
 
	
	/**SerializerFeature.PrettyFormat,
	 *  SerializerFeature.WriteNullStringAsEmpty, 
            SerializerFeature.WriteNullNumberAsZero, 
            SerializerFeature.WriteDateUseDateFormat,
	 * @return
	 */
  @Bean
  public HttpMessageConverters fastjsonConverter() {
//	  if("a".length()>0)
//		  throw new RuntimeException("fastjsonConverter");
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    //自定义格式化输出
    fastJsonConfig.setSerializerFeatures(
    		SerializerFeature.PrettyFormat,
            SerializerFeature.DisableCircularReferenceDetect);//禁止循环引用
    FastJsonHttpMessageConverter4 fastjson = new FastJsonHttpMessageConverter4();
    fastjson.setFastJsonConfig(fastJsonConfig);
    
    
    //json xml 
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);    //在convert中添加配置信息.   
    fastjson.setSupportedMediaTypes(fastMediaTypes);
 

 

    return new HttpMessageConverters(fastjson);
  }
 
}
 