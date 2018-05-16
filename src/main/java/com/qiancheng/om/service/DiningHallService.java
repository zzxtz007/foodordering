package com.qiancheng.om.service;

import com.qiancheng.om.common.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.util.Date;
import java.util.List;


/**
 * 食堂业务
 *
 * @author 李登帅
 */
@Service("DiningHallService")
public interface DiningHallService {

    /**
     * 对食堂进行增加
     * 状态码 0 成功 1 空指针 2 字段必填未填写
     *
     * @param name         食堂名称 必填
     * @param file         获取到的图片信息 必选
     * @param startTime    开始时间 必填
     * @param endTime      结束时间 必填
     * @param introduction 介绍
     * @param uid          增加人，修改人
     * @return Response 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response insert(String name, Date startTime, Date endTime, String introduction, String uid,
            MultipartFile file) throws Throwable;

    /**
     * 对食堂表进行逻辑删除（状态is_deleted由0转变1）
     * 状态码 0 成功 1 空指针 2 字段必填未填写
     *
     * @param diningHallIds 删除的主键所放的集合 不为空
     * @param uid           删除操作人 必填
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response deleteById(List<Integer> diningHallIds, String uid) throws
            Throwable;

    /**
     * 对食堂表进行修改
     * 状态码 0 成功 1 空指针 2 字段必填未填写
     *
     * @param id        主键 必填
     * @param name      食堂名称 非必填
     * @param startTime 开始时间 非必填
     * @param endTime   结束时间 非必填
     * @param uid       修改人 必填
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response updateById(Integer id, String name, Date startTime, Date endTime, String
            introduction, String uid) throws Throwable;

    /**
     * 查看所有食堂的信息
     * 状态码 0 成功 1 空指针 2 字段必填未填写
     *
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response list() throws Throwable;

    /**
     * 根据id查看所有食堂的信息
     * 状态码 0 成功 1 空指针 2 字段必填未填写
     *
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    Response getById(Integer id) throws Throwable;

    /**
     * 修改权重
     * 状态码 0 成功 1 空指针 2 字段必填未填写
     *
     * @param idList 按权重降序排序的 ID List
     * @param uid    修改人 不为空
     * @return Response 状态码
     * @throws Throwable 发生异常时抛出
     */
    Response updateWeight(List<Integer> idList, String uid) throws Throwable;

    /**
     * 修改图片
     *
     * @param id   获取到食堂的ID 必选
     * @param part 获取到的图片信息 必选
     * @param uid  用戶 ID 必选
     * @return 状态码+文件名 status + fileName 返回文件名用于回显
     * @throws Throwable 发生异常时抛出
     */
    Response updateImageUrl(Integer id, Part part, String uid) throws Throwable;
}
