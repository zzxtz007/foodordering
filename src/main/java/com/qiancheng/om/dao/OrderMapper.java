package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Order Mapper
 *
 * @author Ice_Dog
 */
@MyBatisDao
public interface OrderMapper {
    /**
     * 添加 Order 模型
     *
     * @param order 模型
     * @return 影响行数
     */
    int insert(@Param("order") Order order);

    /**
     * 修改 Order 模型
     *
     * @param order 模型
     * @return 影响行数
     */
    int updateById(@Param("order") Order order);

    /**
     * 删除 Order
     *
     * @param idArr Order ID 数组
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr);

    /**
     * 查询全部 Order
     *
     * @param order 查询条件 Map，支持下列参数
     *              <table><tr><td>startTime<td>查询起始时间
     *              <tr><td>endTime<td>查询结束时间
     *              <tr><td>consumerId<td>顾客 ID
     *              <tr><td>stallId<td>商户 ID
     *              <tr><td>id<td>订单 ID
     *              <tr><td>status<td>订单状态
     *              <tr><td>isDeleted<td>删除标记
     *              <tr><td>startIndex<td>查询起始索引
     *              <tr><td>count<td>查询条数
     * @return 包含 Order 信息的 map 的集合
     */
    List<Map<String, Object>> list(Map<String, Object> order);

    /**
     * 按条件获取订单总条目数
     *
     * @param order 条件
     * @return 总条目数
     * @see #list(Map)
     */
    int count(Map<String, Object> order);
}
