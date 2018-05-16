package com.qiancheng.om.common.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注该注解的方法，会自动检测登录状态，未登录返回 statusCode 2
 * <p>
 * 用于 Controller 方法上
 * <p>
 * {@link javax.servlet.http.HttpSession}
 *
 * @author XLY
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
@RequestMapping
public @interface NeedLogin {
    String description() default "";
}
