package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Banner mapper
 *
 * @author Brendan Lee
 */
@MyBatisDao
public interface BannerMapper {
    /**
     * 添加 Banner
     *
     * @param banner Banner 模型
     * @return 影响行数
     */
    int insert(@Param("banner") Banner banner);

    /**
     * 根据 ID 批量删除 Banner
     *
     * @param idArr      Banner ID 数组
     * @param updateUser 修改人 ID
     * @return 影响行数
     */
    int deleteById(@Param("idArr") Integer[] idArr, @Param("updateUser") String updateUser);

    /**
     * 根据 ID 修改 Banner
     *
     * @param banner Banner 模型
     * @return 影响行数
     */
    int updateById(@Param("banner") Banner banner);

    /**
     * 查询全部 Banner
     *
     * @return 包含 Banner 信息的 map 的集合
     */
    List<Map<String, Object>> listAll();
}
