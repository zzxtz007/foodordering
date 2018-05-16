package com.qiancheng.om.common.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注了该注解的方法，会自动获取用户信息、并赋值给相应的参数
 * <p>
 * 匹配是通过参数变量名进行的，支持下列参数：
 * <tr>
 * <td>uid - 用户/商户/运营 ID
 * <tr>
 * <td>role - 用户角色
 * <p>
 * 用于 Controller 方法上
 * <p>
 * {@link javax.servlet.http.HttpSession}
 *
 * @author XLY
 */

@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequestMapping
public @interface GetUserInfo {
}
