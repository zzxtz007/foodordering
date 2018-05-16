package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.service.ConsigneeInformationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "consignee-information")
@ResponseBody
public class ConsigneeInformationController {

    @Resource
    private ConsigneeInformationService consigneeInformationService;

    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    @GetUserInfo
    public String listById(String uid) throws Throwable {
        return new Gson().toJson(consigneeInformationService.listInformationById(uid));
    }



}
