package com.kok.sport.config.annotations;

import java.lang.annotation.*;

/**
 * @author haitian
 * @date 2019/10/25
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  UserPermission {
    String value() default "Permission";
}
