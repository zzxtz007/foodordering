package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 订单的 Controller
 *
 * @author XLY
 */
@Controller
@RequestMapping(value = "/orders", produces = "application/json;charset=utf-8")
@ResponseBody
public class OrderController {
    private static final Gson GSON = new Gson();
    @Resource
    private OrderService orderService;

    /**
     * 获取订单详情
     *
     * @param id 订单 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @NeedLogin
    public String getByOrderId(@PathVariable String id) throws Throwable {
        return GSON.toJson(orderService.getById(id));
    }

    /**
     * 用户提交订单
     *
     * @param stallId         商户 ID
     * @param phone           电话
     * @param appointmentTime 取餐时间
     * @param remark          备注
     * @param isPack          是否打包
     * @param foodCountMap    菜品数量
     * @param uid             session 内用户 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String insertOrder(String stallId, String phone, Date appointmentTime, String remark,
            Boolean isPack, @RequestParam("foodCountMap") Map<Integer, Integer> foodCountMap,
            String uid) throws Throwable {
        return GSON.toJson(orderService.insertOrder(uid, stallId, phone, appointmentTime, remark,
                isPack, foodCountMap));
    }

    /**
     * 取消订单
     *
     * @param id  订单 ID
     * @param uid session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String cancelOrder(@PathVariable String id, String uid) throws Throwable {
        return GSON.toJson(orderService.cancelOrder(id, uid));
    }

    /**
     * 接受订单
     *
     * @param id      订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/deal", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String acceptOrder(@PathVariable String id, String stallId, String uid) throws
            Throwable {
        return GSON.toJson(orderService.acceptOrder(id, stallId, uid));
    }

    /**
     * 支付订单接口
     *
     * @param id  订单 ID
     * @param uid session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/payment", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String payOrder(@PathVariable String id, String uid) throws Throwable {
        return GSON.toJson(orderService.payOrder(id, uid));
    }

    /**
     * 再次支付
     *
     * @param orderId  订单 id
     * @param prepayId 预支付 id
     * @param uid      session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/payment-again", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String payOrderAgain(@PathVariable("id") String orderId, String prepayId, String uid)
            throws Throwable {
        return GSON.toJson(orderService.payOrderAgain(orderId, prepayId, uid));
    }

    /**
     * 拒绝接单
     *
     * @param id      订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/deal", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String refuseOrder(@PathVariable String id, String stallId, String uid) throws
            Throwable {
        return GSON.toJson(orderService.refuseOrder(id, stallId, uid));
    }

    /**
     * 申请退款
     *
     * @param id  订单 ID
     * @param uid session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/refund", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String applicationRefund(@PathVariable String id, String uid) throws Throwable {
        return GSON.toJson(orderService.consumerApplicationRefund(id, uid));
    }

    /**
     * 确认退款
     *
     * @param id  订单 ID
     * @param uid session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/confirmRefund", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String confirmRefund(@PathVariable String id, String uid) throws Throwable {
        return GSON.toJson(orderService.confirmRefund(id, uid));
    }

    /**
     * 同意退款
     *
     * @param id      订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/refund", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String acceptRefund(@PathVariable String id, String stallId, String uid) throws
            Throwable {
        return GSON.toJson(orderService.refund(id, stallId, uid));
    }

    /**
     * 拒绝退款
     *
     * @param id      订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内的 ID
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/refund", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String refuseRefund(@PathVariable String id, String stallId, String uid) throws
            Throwable {
        return GSON.toJson(orderService.refuseToRefund(id, stallId, uid));
    }
}
