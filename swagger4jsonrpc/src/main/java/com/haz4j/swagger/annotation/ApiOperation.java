package com.haz4j.swagger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
@Documented
public @interface ApiOperation {
    String value() default "";
    String[] tags() default {""};
}
