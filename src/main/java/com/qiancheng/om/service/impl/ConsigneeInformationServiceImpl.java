package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.ConsigneeInformationMapper;
import com.qiancheng.om.model.ConsigneeInformation;
import com.qiancheng.om.service.ConsigneeInformationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("ConsigneeInformationServiceImpl")
public class ConsigneeInformationServiceImpl implements ConsigneeInformationService {

    private static Logger LOGGER = Logger.getLogger(ConsigneeInformationServiceImpl.class);

    @Resource
    private ConsigneeInformationMapper consigneeInformationMapper;

    @Override
    public Response insertInformation(String consumerId, String address) throws Throwable {
        if (consumerId == null || address == null) {
            LOGGER.debug("consumerId:" + consumerId + "address:" + address);
            return new Response(3);
        }

        ConsigneeInformation consigneeInformation = new ConsigneeInformation();
        consigneeInformation.setConsumerId(consumerId);
        consigneeInformation.setAddress(address);
        consigneeInformationMapper.insert(consigneeInformation);

        return new Response(0);
    }

    @Override
    public Response listInformationById(String consumerId) throws Throwable {
        if (consumerId == null) {
            return new Response(3);
        }

        ConsigneeInformation consigneeInformation = new ConsigneeInformation();
        consigneeInformation.setConsumerId(consumerId);

        List<Map<String, Object>> consigneeInformationList = consigneeInformationMapper.select
                (consigneeInformation);

        return new Response(0).add("info", consigneeInformationList);
    }

    @Override
    public Response changeInformationById(Integer id, String consumerId, String address) throws
            Throwable {
        if (consumerId == null || address == null || id == null) {
            LOGGER.debug("consumerId:" + consumerId + "address:" + address + "id:" + id);
            return new Response(3);
        }

        ConsigneeInformation consigneeInformation = new ConsigneeInformation();
        consigneeInformation.setConsumerId(consumerId);
        consigneeInformation.setAddress(address);
        consigneeInformation.setId(id);
        consigneeInformationMapper.updateById(consigneeInformation);

        return new Response(0);
    }

    @Override
    public Response deleteInformation(Integer id) throws Throwable {
        if (id == null) {
            return new Response(3);
        }

        ConsigneeInformation consigneeInformation = new ConsigneeInformation();
        consigneeInformation.setId(id);
        consigneeInformationMapper.delete(consigneeInformation);

        return new Response(0);
    }
}
