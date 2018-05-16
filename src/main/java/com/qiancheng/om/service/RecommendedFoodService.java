package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;

import java.util.List;

/**
 * 推荐菜品业务
 *
 * @author 郑孝蓉
 * 状态码：0:正常  1:空指针异常 2：值为空 3：类型转换异常
 */
public interface RecommendedFoodService {
    /**
     * 添加推荐菜品
     *
     * @param foodId 菜品 ID 必选
     * @param uid    用户 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response insert(Integer foodId, String uid) throws Throwable;

    /**
     * 批量删除
     *
     * @param recommendFoodId 推荐菜品ID数组 必选
     * @param uid             用户 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(List<Integer> recommendFoodId, String uid) throws Throwable;

    /**
     * 根据id更新推荐菜品的权重
     *
     * @param idList 按权重降序排序的 ID List
     * @param uid    当前用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response updateWeight(List<Integer> idList, String uid) throws Throwable;

    /**
     * 获取指定条数推荐菜品
     *
     * @return 状态码+status+recommendFoods查询到的推荐菜品结果集（推荐菜品对象+商户id+食堂名称diningHallName+菜品名称name
     * +菜品图片imageUrl+菜品）
     * @throws Throwable 发生异常时抛出
     */
    Response list(Integer rowsNum) throws Throwable;
}
