package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Stall;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Stall Mapper
 *
 * @author Ice_Dog
 */
@MyBatisDao
public interface StallMapper {
    /**
     * 添加商户 stall
     *
     * @param stall 模型
     * @return 影响行数
     */
    int insert(@Param("stall") Stall stall);

    /**
     * 根据 ID 批量删除 Stall
     *
     * @param idArr      Stall ID 数组
     * @param updateUser 修改人
     * @return 影响行数
     */
    int deleteById(@Param("idArr") String[] idArr, @Param("updateUser") String updateUser);

    /**
     * 修改 Stall
     *
     * @param stall Stall 模型
     * @return 影响行数
     */
    int update(@Param("stall") Stall stall);

    /**
     * 查询多个 Stall
     *
     * @param stall Stall 模型
     * @return 包含 Stall 信息的 map 的集合
     */
    List<Map<String, Object>> list(@Param("stall") Stall stall);

    /**
     * 按条件获取商户的总条目数
     *
     * @param stall 条件
     * @return 总条目数
     */
    int count(@Param("stall") Stall stall);
}
