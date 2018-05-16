package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;


/**
 * 广告业务
 *
 * @author 毕立宇
 */
@Service("BannerService")
public interface BannerService {

    /**
     * @param link 获取到图片链接 必选
     * @param uid  用户 ID 必选
     * @param part 获取到图片信息 必选
     * @return 状态码+影响行数 rows
     * @throws Throwable 发生异常时抛出
     */
    Response insert(String link, String uid, Part part) throws Throwable;

    /**
     * 根据 ID 批量删除 Banner
     *
     * @param idArr Banner ID 数组
     * @return 状态码+影响行数 rows
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(List<Integer> idArr, String uid) throws Throwable;

    /**
     * 查询全部 Banner
     *
     * @return 包含 Banner 信息的 map 的集合
     * @throws Throwable 发生异常时抛出
     */
    Response listAll() throws Throwable;

    /**
     * 保存权重
     *
     * @param idList 按权重降序排序的 ID List
     * @param uid    用戶 ID 必选
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response saveWeight(List<Integer> idList, String uid) throws Throwable;

    /**
     * 修改图片信息
     *
     * @param part 获取到的图片信息 必选
     * @param uid  用戶 ID 必选
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response updateImage(Part part, String uid, Integer id) throws Throwable;


    /**
     * @param id   Banner ID 必选
     * @param uid  用戶 ID 必选
     * @param link
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response updateById(Integer id, String uid, String link) throws Throwable;
}
