package com.qiancheng.om.service;

import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.utils.Response;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 菜品业务
 *
 * @author 谢治波
 */
public interface FoodService {
    /**
     * 商户菜品添加
     *
     * @param name            菜品名称  必选
     * @param introduction    菜品介绍  必选
     * @param categoryId      菜品分类 ID 必选
     * @param packFee         打包费  必选
     * @param memberPriceCost 会员价格成本，即商户提供给运营的会员价菜品的价格  必选
     * @param uid             最后修改数据的用户 ID 必选
     * @return 状态码+图片路径status+fileName
     * @throws Throwable 发生异常时抛出
     */
    Response insert(Part part, String name, String introduction, Integer categoryId, BigDecimal
            packFee, BigDecimal memberPriceCost, String uid) throws Throwable;

    /**
     * 菜品下架（逻辑删除）
     *
     * @param idArr 菜品 ID 数组
     * @param uid   最后修改数据的用户 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(List<Integer> idArr, String uid) throws Throwable;

    /**
     * 商户修改菜品
     *
     * @param id              菜品 ID 必选
     * @param uid             最后修改数据的用户 ID 必选
     * @param name            菜品名称  必选
     * @param categoryId      菜品分类 ID 必选
     * @param packFee         打包费用 必选
     * @param standard_price 会员价格成本，即商户提供给运营的会员价菜品的价格  必选
     * @param introduction    菜品介绍 必选
     * @return 状态码+影响行数status+rows
     * @throws Throwable 发生异常时抛出
     */
    Response updateByStallById(Integer id, String uid, String name, Integer categoryId, BigDecimal
            packFee, BigDecimal standardPrice, String introduction) throws Throwable;
    /**
     * 修改菜品图片
     *
     * @param id   获取菜品ID  必选
     * @param part 获取到的图片信息 必选
     * @param uid  商户ID  必选
     * @return 状态码+文件名 status + fileName 返回文件名用于回显
     * @throws Throwable 发生异常时抛出
     */
    Response changeImage(Integer id, Part part, String uid) throws Throwable;

    /**
     * 运营端修改菜品
     *
     * @param id               菜品 ID 必选
     * @param uid              最后修改数据的用户 ID 必选
     * @param memberPrice      会员价格 必选
     * @param memberPrice   会员价格成本，即商户提供给运营的会员价菜品的价格 必选
     * @param memberPriceLimit 会员价菜品每日限量 必选
     * @param saleCount        总销量 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response updateByOperatorById(Integer id, String uid, BigDecimal memberPrice,BigDecimal
            memberPriceCost, Integer memberPriceLimit, Integer saleCount) throws Throwable;

    /**
     * 根据菜品分类ID查询菜品
     *
     * @param categoryId 菜品分类 ID 必选
     * @param startIndex 起始标记 必选
     * @param rowCount   查询条数 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response listByCategoryId(List<Integer> categoryId, Integer startIndex, Integer rowCount,
            UserRoleEnum role) throws Throwable;

    /**
     * 根据菜品ID查询菜品详情
     *
     * @param id 菜品 ID 必选
     * @return 状态码+集合 status+list
     * @throws Throwable 发生异常时抛出
     */
    Response getById(Integer id) throws Throwable;

    /**
     * 根据历史订单菜品id查询菜品综合评分
     *
     * @param id 菜品 ID 必选
     * @return 状态码+好评率 status+positiveRate
     * @throws Throwable 发生异常时抛出
     */
    Response mark(Integer id) throws Throwable;

    /**
     * 用户根据订单提交评价
     *
     * @param markMap 订单菜品 ID 与订单评分集合  必选
     * @param uid     最后修改数据的用户 ID 必选
     * @return 状态码  status
     * @throws Throwable 发生异常时抛出
     */
    Response updateMark(Map<Integer, Integer> markMap, String uid) throws Throwable;

    /**
     * 修改菜品权重
     *
     * @param idList      菜品 ID集合 必选
     * @param categoryId 菜品分类 ID 必选
     * @param uid        最后修改数据的用户 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response updateWeight(List<Integer> idList, Integer categoryId, String uid) throws
            Throwable;

    /**
     * 菜品上下架
     *
     * @param id       菜品 ID 必选
     * @param uid      用户 ID 必选
     * @param isOnSale 在售标记 true 在售 false 下架
     * @return 状态码  status
     * @throws Throwable 发生异常时抛出
     */
    Response updateOnSaleById(Integer id, String uid, Boolean isOnSale) throws Throwable;
}
