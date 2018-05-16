package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.DiningHall;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * dining hall mapper
 *
 * @author XLY
 */
@MyBatisDao
public interface DiningHallMapper {

    /**
     * 添加食堂
     *
     * @param diningHall 食堂模型
     * @return 影响行数 int
     */
    int insert(@Param("diningHall") DiningHall diningHall);

    /**
     * 批量根据 id 删除食堂
     *
     * @param idArr 食堂编号数组 updateUser 操作人
     * @return 影响行数 int
     */
    int deleteById(@Param("idArr") Integer[] idArr, @Param("updateUser") String updateUser);

    /**
     * 更新食堂
     * 通过主键ID，只能修改一个
     *
     * @param diningHall 食堂模型
     * @return 影响行数
     */
    int updateById(@Param("diningHall") DiningHall diningHall);

    /**
     * 获取所有食堂信息
     *
     * @param diningHall 食堂模型
     * @return 包含 dining hall 信息的 map 集合
     */
    List<Map<String, Object>> list(@Param("diningHall") DiningHall diningHall);

    /**
     * 按条件获取食堂的总条目数
     *
     * @param diningHall 条件
     * @return 总条目数
     */
    int count(@Param("diningHall") DiningHall diningHall);

    /**
     * 按id获取食堂
     *
     * @param  diningHall 食堂模型
     * @return map集合
     */
    Map<String, Object> selectById(@Param("diningHall") DiningHall diningHall);
}
