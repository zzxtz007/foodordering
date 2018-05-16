package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;
import org.springframework.stereotype.Service;


/**
 * 全局通知业务
 *
 * @author 王道铭
 */
@Service("GlobalNoticeService")
public interface GlobalNoticeService {
    /**
     * 获取全局通知
     *
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response list() throws Throwable;

    /**
     * 更新全局通知
     *
     * @param content 全局通知内容
     * @param uid     全局通知添加人
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response insert(String content, String uid) throws Throwable;
}
