package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.GlobalNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * global notice mapper
 *
 * @author XLY
 */
@MyBatisDao
public interface GlobalNoticeMapper {

    /**
     * 添加全局通知 globalNotice
     *
     * @param globalNotice GlobalNotice 模型
     * @return 影响行数
     */
    int insert(@Param("globalNotice") GlobalNotice globalNotice);

    /**
     * 获取第一条全局通知
     *
     * @return 包含 globalNotice 信息的 map 集合
     */
    List<Map<String, Object>> list();

    /**
     * 删除全局通知
     *
     * @param idArr      全局通知 ID 数组
     * @param updateUser 修改人姓名
     * @return 影响行数
     */
    int deleteById(@Param("idArr") int[] idArr, @Param("updateUser") String updateUser);

    /**
     * 根据全局通知更新
     *
     * @param globalNotice 全局通知
     * @return 影响行数
     */
    int updateById(@Param("globalNotice") GlobalNotice globalNotice);

}
