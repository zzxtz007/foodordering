package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.model.Feedback;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 反馈业务
 *
 * @author 秦鑫宇
 */
public interface FeedBackService {
    /**
     * 添加回馈信息 Feedback
     *
     * @param content    文本内容
     * @param insertUser 添加人
     * @return 影响行数
     * @throws Throwable 发生异常时抛出
     */
    Response insert(String content, String insertUser) throws Throwable;


    /**
     * 获取多个用户反馈
     *
     * @param startIndex 开始索引
     * @param rowCount   查询个数
     * @return 包含 Feedback 信息的 map 集合
     * @throws Throwable 发生异常时抛出
     */
    Response list(Integer startIndex, Integer rowCount) throws Throwable;

    /**
     * 根据 ID 批量删除 Feedback
     *
     * @param idArr      Feedback ID 数组
     * @param updateUser 修改人
     * @return 影响行数
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(int[] idArr, String updateUser) throws Throwable;

}

