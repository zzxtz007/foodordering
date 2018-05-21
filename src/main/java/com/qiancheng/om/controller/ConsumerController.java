package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.ConsumerService;
import com.qiancheng.om.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户业务
 *
 * @author 邹运
 */
@Controller
@RequestMapping(value = "/consumers", produces = "application/json;charset=UTF-8")
@ResponseBody
public class ConsumerController {
    private static final Gson GSON = new Gson();
    @Resource
    private ConsumerService consumerService;
    @Resource
    private OrderService orderService;

    /**
     * 根据id获取用户信息
     *
     * @param uid 用户ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    @GetUserInfo
    public String list(String uid) throws Throwable {
        return GSON.toJson(consumerService.list(uid));
    }

    /**
     * 注册会员
     *
     * @param name      用户姓名
     * @param studentId 用户学生证号
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/membership", method = RequestMethod.POST)
    public String updateById(String name, String studentId) throws Throwable {
        return GSON.toJson(consumerService.updateById("软件注册", name, studentId));
    }

    /**
     * 根据id获取用户常购菜品信息
     *
     * @param uid 用户ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/favorite-food", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String listFavoriteFood(String uid) throws Throwable {
        return GSON.toJson(consumerService.listFavoriteFood(uid));
    }

    /**
     * 获取用户订单列表
     *
     * @param pageNum  页码，从 1 起
     * @param pageSize 每页条目数
     * @param uid      session 内的用户 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @NeedLogin
    @GetUserInfo
    public String listUserOrders(Integer pageNum, Integer pageSize, String uid) throws Throwable {
        return new Gson().toJson(orderService.listById(uid, pageNum, pageSize));
    }


    /**
     * 获取用户绑定的手机号码
     *
     * @param iv            加密初始向量
     * @param encryptedData 加密数据
     * @return 手机号码以及其余加密数据
     */
    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    @NeedRole(UserRoleEnum.CONSUMER)
    @NeedLogin
    public String getConsumerPhone(String iv, String encryptedData) throws Throwable {
        return new Gson().toJson(consumerService.getPhone(iv, encryptedData));
    }

}
