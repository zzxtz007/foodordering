package com.qiancheng.om.common.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识传入敏感信息的方法，通过 AOP 获取 openId
 *
 * @author XLY
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
@RequestMapping
public @interface CheckOpenId {
    String description() default "";
}
