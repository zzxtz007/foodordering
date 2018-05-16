package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 运营方 Controller
 *
 * @author Brendan Lee
 */
@Controller
@RequestMapping(value = "/operators", produces = "application/json;charset=utf-8")
@ResponseBody
public class OperatorController {
    private static final Gson GSON = new Gson();
    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取运营方的营收情况
     *
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/statistics/revenue", method = RequestMethod.GET)
    @NeedLogin
    @NeedRole(UserRoleEnum.OPERATOR_ADMIN)
    public String getRevenueStatistics() throws Throwable {
        return GSON.toJson(statisticsService.getSalesReport());
    }
}
