package com.qiancheng.om.service;

import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.utils.Response;

import javax.servlet.http.Part;
import java.util.List;

/**
 * 商店业务
 *
 * @author 周智鑫
 */
public interface StallService {
    /**
     * 获取指定食堂的所有商户
     *
     * @param diningHallId 食堂 ID 必选
     * @return 状态码+集合  status + stalls 包含 stall 信息的 list 集合
     * @throws Throwable 发生异常时抛出
     */
    Response list(Integer diningHallId) throws Throwable;

    /**
     * 获取指定 id 商户信息
     *
     * @param id 食堂 ID 必选
     * @return 状态码+集合 status + stall 包含 stall 信息的 map 集合
     * @throws Throwable 发生异常时抛出
     */
    Response getById(String id) throws Throwable;

    /**
     * 添加商户信息 需要输入用户名和密码
     *
     * @param part         获取到的图片信息 必选
     * @param name         获取到的商户名 必选
     * @param username     获取到的用户名 必选
     * @param password     获取到的密码 必选
     * @param diningHallId 食堂 ID 必选
     * @param uid          用戶 ID 必选
     * @return 状态码+文件名 status + fileName 返回文件名用于回显
     * @throws Throwable 发生异常时抛出
     */
    Response add(Part part, String name, String username, String password, Integer diningHallId,
            String uid) throws Throwable;

    /**
     * 删除商户信息 需要输入id
     *
     * @param idArr 必选
     * @param uid   用戶 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(List<String> idArr, String uid) throws Throwable;

    /**
     * 修改商戶信息 需要输入用户名，商户名和密码
     *
     * @param id       获取到的商户 ID 必选
     * @param name     获取到的商户名 必选
     * @param username 获取到的用户名 可选
     * @param password 获取到的密码 可选
     * @param phone    获取到的手机号码 可选
     * @param notify   获取到的通知信息 可选
     * @param uid      用户 ID 必选
     * @param role     角色 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response edit(String id, String name, String username, String password, String phone, String
            notify, String uid, UserRoleEnum role) throws Throwable;

    /**
     * 修改图片信息
     *
     * @param id   获取到的商户 ID 必选
     * @param part 获取到的图片信息 必选
     * @param uid  用戶 ID 必选
     * @return 状态码+文件名 status + fileName 返回文件名用于回显
     * @throws Throwable 发生异常时抛出
     */
    Response changeImage(String id, Part part, String uid) throws Throwable;

    /**
     * 保存食堂下的权重
     *
     * @param idList       含有权重的商户列表<商户 ID , 权重>  必选
     * @param diningHallId 食堂 ID  必选
     * @param uid          用戶 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response saveWeight(List<String> idList, Integer diningHallId, String uid) throws Throwable;

    /**
     * 调整商户开关店
     *
     * @param id     获取到的商户 ID 必选
     * @param isOpen 获取到的开店状态 必选
     * @param uid    用戶 ID 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    Response changeStatus(String id, Boolean isOpen, String uid) throws Throwable;

    /**
     * 批量开店
     *
     * @throws Throwable 发生异常时抛出
     * @return 影响的商户数
     */
    int batchOpenStall() throws Throwable;

    /**
     * 批量关店
     *
     * @throws Throwable 发生异常时抛出
     * @return 影响的商户数
     */
    int batchCloseStall() throws Throwable;
}
