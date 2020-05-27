package com.kok;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
 
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * Fastjson配置，
 *
 * 把spring-boot默认的json解析器由Jenkins换为fastjson
 * 
 */
//@//Configuration
public class FastjsonConfiguration {
 
	
	/**
	 *  SerializerFeature.WriteNullStringAsEmpty, 
            SerializerFeature.WriteNullNumberAsZero, 
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.PrettyFormat,
	 * @return
	 */
  //@//Bean
  public HttpMessageConverters fastjsonConverter() {
	  if("a".length()>0)
		  throw new RuntimeException("fastjsonConverter");
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    //自定义格式化输出
    fastJsonConfig.setSerializerFeatures(
           
            SerializerFeature.DisableCircularReferenceDetect);//禁止循环引用
    FastJsonHttpMessageConverter4 fastjson = new FastJsonHttpMessageConverter4();
    fastjson.setFastJsonConfig(fastJsonConfig);
    return new HttpMessageConverters(fastjson);
  }
 
}
 