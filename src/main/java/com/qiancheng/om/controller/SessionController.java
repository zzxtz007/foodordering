package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 会话 Controller
 *
 * @author Brendan Lee
 */
@Controller
@RequestMapping(value = "/session", produces = "application/json;charset=utf-8")
@ResponseBody
public class SessionController {
    private static final Gson GSON = new Gson();
    @Resource
    private SessionService sessionService;

    /**
     * 登录
     *
     * @param role     角色
     * @param username 用户名（For operators and stalls）
     * @param password 密码（For operators and stalls）
     * @param code     微信小程序 login 接收到的 code（For consumers only）
     * @param session  会话对象
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.POST)
    public String login(String role, String username, String password, String code, HttpSession
            session) throws Throwable {
        switch (role) {
            case "consumer": {
                return GSON.toJson(sessionService.consumerLogin(code, session));
            }
            case "stall": {
                return GSON.toJson(sessionService.stallLogin(username, password, session));
            }
            case "operator": {
                return GSON.toJson(sessionService.operatorLogin(username, password, session));
            }
            default: {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }
        }
    }

    /**
     * 登出
     *
     * @param session 会话对象
     * @param uid     当前用户 ID
     * @param role    当前用户角色
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @GetUserInfo
    public String logout(HttpSession session, String uid, UserRoleEnum role) throws Throwable {
        return GSON.toJson(sessionService.logout(session, uid, role));
    }

    /**
     * 检查会话状态
     *
     * @param uid  当前用户 ID
     * @param role 当前用户角色
     * @param session
     * @return 响应值
     */
    @RequestMapping(method = RequestMethod.GET)
    @GetUserInfo
    public String checkSession(String uid, UserRoleEnum role, HttpSession session) {
        if (uid == null) {
            return GSON.toJson(new Response(2));
        } else {
            Response response = new Response(0).add("uid", uid).add("role", role.getValue());
            Object username = session.getAttribute("username");
            if (username != null) {
                response.add("username", username);
            }
            return GSON.toJson(response);
        }
    }
}
