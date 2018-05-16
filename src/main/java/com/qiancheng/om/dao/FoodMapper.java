package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Food;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * food mapper
 *
 * @author XLY
 */
@MyBatisDao
public interface FoodMapper {

    /**
     * 添加菜品信息
     *
     * @param food 菜品模型
     * @return 影响行数
     */
    int insert(@Param("food") Food food);

    /**
     * 删除菜品
     *
     * @param idArr      菜品 ID 数组
     * @param updateUser 修改人 ID
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr, @Param("updateUser") String updateUser);

    /**
     * 根据菜品编号更新
     *
     * @param food 菜品模型
     * @return 影响行数
     */
    int updateById(@Param("food") Food food);

    /**
     * 根据各类信息查询菜品
     *
     * @param food       菜品模型
     * @param startIndex 开始索引
     * @param rowCount   查询条数
     * @return 包含菜品信息的 map 集合
     */
    List<Map<String, Object>> list(@Param("food") Food food, @Param("startIndex") Integer
            startIndex, @Param("rowCount") Integer rowCount);

    /**
     * 按条件获取菜品的总条目数
     *
     * @param food 条件
     * @return 总条目数
     */
    int count(@Param("food") Food food);
}

