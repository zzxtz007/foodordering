package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;

public interface ConsigneeInformationService {

    /**
     * 添加用户信息
     *
     * @param consumerId 用户 id
     * @param address 地址
     * @return 响应码
     * @throws Throwable 有异常时抛出
     */
    Response insertInformation(String consumerId, String address) throws Throwable;

    /**
     * 获取指定用户下的所有信息
     *
     * @param consumerId 用户 id
     * @return 包含用户信息的响应码
     * @throws Throwable 有异常时抛出
     */
    Response listInformationById(String consumerId) throws Throwable;

    /**
     * 修改指定信息
     *
     * @param id 信息 id
     * @param consumerId 用户 id
     * @param address 地址
     * @return 响应码
     * @throws Throwable 有异常时抛出
     */
    Response changeInformationById(Integer id, String consumerId, String address) throws Throwable;

    /**
     * 删除指定信息
     *
     * @param id 信息编号
     * @return 响应码
     * @throws Throwable 有异常时抛出
     */
    Response deleteInformation(Integer id) throws Throwable;
}
