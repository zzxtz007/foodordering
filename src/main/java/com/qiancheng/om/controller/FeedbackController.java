package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.impl.FeedBackServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/feedback", produces = "application/json;charset=utf-8")
@ResponseBody
public class FeedbackController {
    private static final Gson GSON = new Gson();
    @Resource
    private FeedBackServiceImpl feedbackService;

    /**
     * 添加 Feedback
     *
     * @param content 内容
     * @param uid     用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String insertFeedback(String content, String uid) throws Throwable {
        return GSON.toJson(feedbackService.insert(content, uid));
    }

    /**
     * 查询 Feedback
     *
     * @param pageNum  页码
     * @param pageSize 每页条目数
     * @return 状态码+Feedback集合
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    public String getFeedbackList(Integer pageNum, Integer pageSize) throws Throwable {
        return GSON.toJson(feedbackService.list(pageNum, pageSize));
    }
}
