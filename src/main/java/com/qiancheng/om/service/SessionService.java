package com.qiancheng.om.service;

import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.utils.Response;

import javax.servlet.http.HttpSession;

/**
 * 会话 Service
 *
 * @author Brendan Lee
 */
public interface SessionService {
    /**
     * 顾客登录
     *
     * @param code    微信小程序 login 接收到的 code
     * @param session 会话对象
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    Response consumerLogin(String code, HttpSession session) throws Throwable;

    /**
     * 商户登录
     *
     * @param username 用户输入的用户名 必选
     * @param password 用户输入的密码 必选
     * @param session  会话对象
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    Response stallLogin(String username, String password, HttpSession session) throws Throwable;

    /**
     * 运营方登录
     *
     * @param username 用户输入的用户名 必选
     * @param password 用户输入的密码 必选
     * @param session  会话对象
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    Response operatorLogin(String username, String password, HttpSession session) throws Throwable;

    /**
     * 登出
     *
     * @param session 会话对象
     * @param uid     当前用户 ID
     * @param role    当前用户角色
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    Response logout(HttpSession session, String uid, UserRoleEnum role) throws Throwable;
}
