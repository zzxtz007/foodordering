package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Feedback;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Feedback Mapper
 *
 * @author XLY
 */
@MyBatisDao
public interface FeedbackMapper {

    /**
     * 添加回馈信息 Feedback
     *
     * @param feedback Feedback 模型
     * @return 影响行数
     */
    int insert(@Param("feedback") Feedback feedback);


    /**
     * 获取多个用户反馈
     *
     * @param startIndex 开始索引
     * @param rowCount   查询个数
     * @return 包含 Feedback 信息的 map 集合
     */
    List<Map<String, Object>> list(@Param("startIndex") Integer startIndex, @Param("rowCount")
            Integer rowCount);

    /**
     * 获取反馈的总条目数
     *
     * @return 总条目数
     */
    int count();

    /**
     * 根据 ID 批量删除 Feedback
     *
     * @param idArr      Feedback ID 数组
     * @param updateUser 修改人
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr, @Param("updateUser") String updateUser);

    /**
     * 根据 ID 修改 Feedback
     *
     * @param feedback Feedback 模型
     * @return 影响行数
     */
    int updateById(@Param("feedback") Feedback feedback);
}
