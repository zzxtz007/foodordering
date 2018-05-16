package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.RecommendedFood;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * Recommended food mapper
 *
 * @author XLY
 */
@MyBatisDao
public interface RecommendedFoodMapper {

    /**
     * 添加推荐菜品
     *
     * @param recommendedFood 实体
     * @return 影响行数
     */
    int insert(@Param("recommendedFood") RecommendedFood recommendedFood);

    /**
     * 批量删除
     *
     * @param idArr      推荐菜品编号(一个或多个)
     * @param updateUser 修改人姓名
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr, @Param("updateUser") String updateUser);

    /**
     * 根据id更新推荐菜品
     *
     * @param recommendedFoodArr 模型
     * @return 影响行数
     */
    int updateById(@Param("recommendedFood") RecommendedFood recommendedFoodArr);

    /**
     * 获取指定条数的推荐菜品
     *
     * @param rowsNum 获取条数
     * @return 包含 recommendedFood 信息的 map 集合
     */
    List<Map<String, Object>> list(@Param("rowsNum") Integer rowsNum);

    /**
     * 获取推荐菜品的总条目数
     *
     * @return 总条目数
     */
    int count();
}
