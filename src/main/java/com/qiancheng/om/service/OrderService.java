package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单业务
 *
 * @author XLY
 */
public interface OrderService {

    /**
     * 添加订单
     *
     * @param uid             session 内用户 ID
     * @param stallId         商户 ID
     * @param phone           手机号码
     * @param appointmentTime 取餐时间
     * @param remark          备注
     * @param isPack          是否打包
     * @param foodCount       购买菜品数量的 map
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response insertOrder(String uid, String stallId, String phone, Date appointmentTime, String
            remark, Boolean isPack, Map<Integer, Integer> foodCount) throws
            Throwable;

    /**
     * 获取详细订单
     *
     * @param id 订单编号
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response getById(String id) throws Throwable;

    /**
     * 根据订单状态查找单个商户的订单
     *
     * @param id        商户 ID
     * @param uid       session 内商户 ID
     * @param startPage 起始页
     * @param rowSize   每页行数
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response listByStatus(String id, String uid, List<Integer> statusArr, Integer startPage,
            Integer rowSize, Date startDate, Date endDate) throws Throwable;

    /**
     * 获取单个用户的所有订单
     *
     * @param id        session 内用户 ID
     * @param startPage 起始页
     * @param rowSize   每页行数
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response listById(String id, Integer startPage, Integer rowSize) throws Throwable;

    /**
     * 将订单号传入，查询是否支付成功，同时修改订单状态
     *
     * @param orderId    订单 ID
     * @param consumerId 用户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response payOrder(String orderId, String consumerId) throws Throwable;

    /**
     * 将预支付 id 传入，返回调起微信支付所需的信息
     *
     * @param prepayId 预支付 id
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response payOrderAgain(String orderId, String prepayId, String uid) throws Throwable;


    /**
     * 用户申请退款
     *
     * @param orderId    订单 ID
     * @param consumerId 用户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response consumerApplicationRefund(String orderId, String consumerId) throws Throwable;

    /**
     * 用户确认退款
     *
     * @param orderId    订单 ID
     * @param consumerId 用户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response confirmRefund(String orderId, String consumerId) throws Throwable;

    /**
     * 商家退款
     *
     * @param orderId 订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内存的商户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response refund(String orderId, String stallId, String uid) throws Throwable;

    /**
     * 拒绝退款
     *
     * @param orderId 订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内存的商户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response refuseToRefund(String orderId, String stallId, String uid) throws Throwable;

    /**
     * 取消订单
     *
     * @param orderId    订单 ID
     * @param consumerId 用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    Response cancelOrder(String orderId, String consumerId) throws Throwable;

    /**
     * 同意接单
     *
     * @param orderId 订单 ID
     * @param stallId 商户 ID
     * @param uid     session 内存的商户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response acceptOrder(String orderId, String stallId, String uid) throws Throwable;

    /**
     * 拒绝接单
     *
     * @param orderId 订单 ID
     * @param stallId 用户 ID
     * @param uid     session 内存的商户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response refuseOrder(String orderId, String stallId, String uid) throws Throwable;

}
