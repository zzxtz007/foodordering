package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Operator;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Operator Mapper
 *
 * @author Ice_Dog
 */
@MyBatisDao
public interface OperatorMapper {
    /**
     * 添加 运营用户 Operator
     *
     * @param operator Operator 模型
     * @return 影响行数
     */
    int insert(@Param("operator") Operator operator);

    /**
     * 查询运营用户 Operator
     *
     * @param username 用户名
     * @return 包含 Operator 信息的 map 的集合
     */
    Map<String, Object> get(@Param("username") String username);

    /**
     * 删除运营用户
     *
     * @param idArr 运营用户 ID 数组
     * @return 影响行数
     */
    int deleteById(@Param("idArr") String[] idArr, @Param("updateUser") String updateUser);

    /**
     * 根据运营用户更新
     *
     * @param operator 运营用户
     * @return 影响行数
     */
    int updateById(@Param("operator") Operator operator);
}
