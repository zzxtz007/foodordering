package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.FoodCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * FoodCategory Mapper
 *
 * @author Ice_Dog
 */
@MyBatisDao
public interface FoodCategoryMapper {
    /**
     * 添加食物分类 FoodCategory
     *
     * @param foodCategory 模型
     * @return 影响行数
     */
    int insert(@Param("FoodCategory") FoodCategory foodCategory);

    /**
     * 更新食物分类 FoodCategory
     *
     * @param foodCategory 模型
     * @return 影响行数
     */
    int updateById(@Param("FoodCategory") FoodCategory foodCategory);

    /**
     * 根据 ID 删除 FoodCategory
     *
     * @param idArr FoodCategory ID
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr, @Param("updateUser") String updateUser);

    /**
     * 查询所有菜品分类
     *
     * @param foodCategory 模型
     * @return 包含菜品信息的 map 集合
     */
    List<Map<String, Object>> listById(@Param("foodCategory") FoodCategory foodCategory);

    /**
     * 按条件获取菜品分类的总条目数
     *
     * @param foodCategory 条件
     * @return 总条目数
     */
    int count(@Param("foodCategory") FoodCategory foodCategory);
}
