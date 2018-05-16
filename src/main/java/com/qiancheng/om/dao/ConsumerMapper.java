package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Consumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * consumer mapper
 *
 * @author XLY
 */
@MyBatisDao
public interface ConsumerMapper {

    /**
     * 添加用户信息
     *
     * @param consumer 用户模型
     * @return 影响行数
     */
    int insert(@Param("consumer") Consumer consumer);

    /**
     * 更新用户信息(升级成会员)
     *
     * @param consumer 用户模型
     * @return 影响行数
     */
    int updateById(@Param("consumer") Consumer consumer);

    /**
     * 根据 ID 批量删除 Consumer
     *
     * @param idArr Consumer ID 数组
     * @return 影响行数
     */
    int deleteById(@Param("idArr") String[] idArr);

    /**
     * 根据id获取用户信息
     *
     * @param consumer 用户模型
     * @return 包含 Consumer 信息的 map 集合
     */
    List<Map<String, Object>> list(@Param("consumer") Consumer consumer);
}
