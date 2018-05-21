package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 用户业务
 *
 * @author 邹运
 * 状态码：0:正常  1:空指针异常 2：值为空 3：类型转换异常
 */
@Service("ConsumerService")
public interface ConsumerService {
    /**
     * 更新用户信息(升级成会员)
     *
     * @param uid       用户ID
     * @param name      用户姓名
     * @param studentId 用户学生证号
     * @param phone     用户手机
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response updateById(String uid, String name, String studentId, String phone) throws Throwable;

    /**
     * 根据id获取用户信息
     *
     * @param uid 用户ID
     * @return 状态码+包含 Consumer 信息的 map 集合
     * @throws Throwable 发生异常时抛出
     */
    Response list(String uid) throws Throwable;

    /**
     * 根据id获取用户常购菜品信息
     *
     * @param uid 用户ID
     * @return 状态码+包含 FavoriteFood 信息的 map 集合
     * @throws Throwable 发生异常时抛出
     */
    Response listFavoriteFood(String uid) throws Throwable;

    /**
     * 通过解密加密数据获取用户手机号码
     *
     * @param iv 加密初始向量
     * @param encryptedData 加密数据
     * @return 状态码 + 包含 PhoneNumber 的信息
     * @throws Throwable 发生异常时抛出
     */
    Response getPhone(String iv, String encryptedData)throws Throwable;
}
