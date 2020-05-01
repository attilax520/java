package com.kok.sport.config.annotations;

import java.lang.annotation.*;

/**
 * @Title: SameUrlData
 * @Description: 自定义注解防止表单重复提交
 * @Auther: haitian
 * @Version: 1.0
 * @create 2019/10/19 10:43
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SameUrlData {

	/**
	 * 如果上一个数据为null,表示还没有访问默认设置2秒
	 * @return
	 */
    long timeValue() default 2;
    
    /**
     * 如果上次 url+数据 和本次url加数据不同，则不是重复提交默认设置1秒
     * @return
     */
    long timeValues() default 1;
}