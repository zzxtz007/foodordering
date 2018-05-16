package com.qiancheng.om.dao;

import com.qiancheng.om.common.annotation.MyBatisDao;
import com.qiancheng.om.model.ConsigneeInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ConsigneeInformationMapper {

    int insert(@Param("consigneeInformation") ConsigneeInformation consigneeInformation);

    int delete(@Param("consigneeInformation") ConsigneeInformation consigneeInformation);

    int updateById(@Param("consigneeInformation") ConsigneeInformation consigneeInformation);

    List<Map<String, Object>>select(@Param("consigneeInformation") ConsigneeInformation
            consigneeInformation);

}
