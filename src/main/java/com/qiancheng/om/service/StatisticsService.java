package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;

import java.util.Date;

/**
 * 统计业务
 *
 * @author 彭凌飞
 */
public interface StatisticsService {

    /**
     * 根据日期查询所有商户日收益
     * 0:成功
     * 1:字符串转换时间异常
     * 3:参数为空
     *
     * @param date 日期
     * @param pageNum 查询页数
     * @param pageSize 每页显示条目数
     * @return 商户日收益及状态码集合 商户收益（message） 状态码（status）
     * @throws Throwable 发生异常时抛出
     */
     Response getAllStallRevenueByDay(Date date, Integer pageNum, Integer pageSize) throws Throwable;

    /**
     * 查询销售报表
     * 所需信息（营业总额，与昨日相比营业差，订单数量，与昨日相比订单数差额，运营收入，于昨日相比运营收入差额）
     * 0:成功
     * 1:字符串转换时间异常
     *
     * @return 销售报表信息及状态码集合
     * 营业总额，与昨日相比营业差，订单数量，与昨日相比订单数差额，运营收入，于昨日相比运营收入差（message）
     * 状态码（status）
     * @throws Throwable 发生异常时抛出
     */
     Response getSalesReport() throws Throwable;


    /**
     * 根据时间商户id获取退款情况
     * 0:成功
     * 1:字符串转换时间异常
     * 3:参数为空
     *
     * @param stallId   商户id
     * @param startTime 查询的营业详情开始时间
     * @param endTime   查询的营业详情结束时间
     * @return 营业详情及状态码集合 营业详情（message） 状态码（status）
     * @throws Throwable 发生异常时抛出
     */
     Response getRefundByTime(String stallId, Date startTime, Date endTime)
            throws Throwable;

    /**
     * 根据时间商户id获取应收情况
     * 0:成功
     * 1:字符串转换时间异常
     * 3:参数为空
     *
     * @param stallId   商户id
     * @param startTime 查询的营业详情开始时间
     * @param endTime   查询的营业详情结束时间
     * @return 营业详情及状态码集合 营业详情（message） 状态码（status）
     * @throws Throwable 发生异常时抛出
     */
     Response getRevenueByTime(String stallId, Date startTime, Date endTime) throws Throwable;
}
