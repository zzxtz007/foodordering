package com.qiancheng.om.common.annotation;

import com.qiancheng.om.common.enumeration.UserRoleEnum;

import java.lang.annotation.*;

/**
 * 该注解用于标示业务所需的用户角色，适用于 Controller 方法
 * <p>
 * 当已登录用户的角色符合要求时，正常执行该方法；不符合需求时，该请求为非法请求，返回状态码 3
 *
 * @author Brendan Lee
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedRole {
    /**
     * 业务所需的用户角色数组
     * <p>
     * 已登录用户的角色不在该数组中时，判定为非法请求，返回状态码 3
     *
     * @see UserRoleEnum
     */
    UserRoleEnum[] value();
}
