package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;

import java.util.List;

/**
 * 菜品分类业务
 *
 * @author 宫昊男
 */
public interface FoodCategoryService {
    /**
     * 添加食物分类 FoodCategory
     *
     * @param name    类别名
     * @param stallId 商户ID
     * @param uid     修改用户ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response insert(String name, String stallId, String uid) throws Throwable;

    /**
     * 更新食物分类 FoodCategory
     *
     * @param idArr 菜品id数组
     * @param uid   修改人id
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(List<Integer> idArr, String uid) throws Throwable;

    /**
     * 根据 ID 修改 FoodCategory
     *
     * @param id   类别ID
     * @param name 类别名
     * @param uid  修改人id
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response updateById(Integer id, String name, String uid) throws Throwable;

    /**
     * 查询所有菜品分类
     *
     * @param stallId 商户ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response listById(String stallId) throws Throwable;

    /**
     * 修改权重
     *
     * @param stallId 商户ID
     * @param idList  按权重降序排序的菜品分类 ID List
     * @param uid     修改人id
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response updateWeight(List<Integer> idList, String stallId, String uid) throws Throwable;
}
