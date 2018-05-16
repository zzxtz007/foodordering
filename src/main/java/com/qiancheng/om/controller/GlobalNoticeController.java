package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.service.GlobalNoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 全局通知controller
 *
 * @author 王道铭
 */
@Controller
@RequestMapping(value = "/global-notices", produces = "application/json;charset=utf-8")
@ResponseBody
public class GlobalNoticeController {
    private static final Gson GSON = new Gson();
    @Resource
    private GlobalNoticeService globalNoticeService;

    /**
     * 获取通知
     *
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    public String list() throws Throwable {
        Response response = globalNoticeService.list();
        return GSON.toJson(response);
    }

    /**
     * 插入通知
     *
     * @param content 插入通知
     * @param uid     登录者的ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String insert(String content, String uid) throws Throwable {
        Response response = globalNoticeService.insert(content, uid);
        return GSON.toJson(response);
    }
}
