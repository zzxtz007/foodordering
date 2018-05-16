package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.OrderedFood;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * OrderedFood Mapper
 *
 * @author Ice_Dog
 */
@MyBatisDao
public interface OrderedFoodMapper {
    /**
     * 添加订单菜品 OrderedFood
     *
     * @param orderedFood 模型
     * @return 影响行数
     */
    int insert(@Param("orderedFood") OrderedFood orderedFood);

    /**
     * 更新订单菜品 OrderedFood
     *
     * @param orderedFood 模型
     * @return 影响行数
     */
    int updateById(@Param("orderedFood") OrderedFood orderedFood);

    /**
     * 删除订单菜品
     *
     * @param idArr      orderedFood ID 数组
     * @param updateUser 修改人 ID
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr, @Param("updateUser") String updateUser);

    /**
     * 查询多个 OrderedFood
     *
     * @param orderedFood 模型
     * @return 包含 OrderedFood 信息的 map 的集合
     */
    List<Map<String, Object>> list(@Param("orderedFood") OrderedFood orderedFood);

    /**
     * 按条件获取订单菜品的总条目数
     *
     * @param orderedFood 条件
     * @return 总条目数
     */
    int count(@Param("orderedFood") OrderedFood orderedFood);
}
