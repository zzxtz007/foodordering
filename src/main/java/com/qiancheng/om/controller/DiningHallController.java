package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.DiningHallService;
import com.qiancheng.om.service.StallService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 食堂 controller
 *
 * @author 郑孝蓉
 */
@Controller
@RequestMapping(value = "/dining-halls", produces = "application/json;charset=utf-8")
@ResponseBody
public class DiningHallController {
    private static final Gson GSON = new Gson();
    @Resource
    private StallService stallService;
    @Resource
    private DiningHallService diningHallService;

    /**
     * 获取所有食堂
     *
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    public String list() throws Throwable {
        return GSON.toJson(diningHallService.list());
    }

    /**
     * 添加食堂
     *
     * @param name         食堂名称
     * @param startTime    开始营业时间
     * @param endTime      结束营业时间
     * @param introduction 食堂介绍
     * @param file         食堂图片
     * @param uid          用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.POST)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String insert(String name, Date startTime, Date endTime, String introduction,
            MultipartFile file, String uid) throws Throwable {
        return GSON.toJson(diningHallService.insert(name, startTime, endTime, introduction, uid,
                file));
    }

    /**
     * 根据id获取食堂
     *
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @NeedLogin
    public String getById(@PathVariable Integer id) throws Throwable {
        return GSON.toJson(diningHallService.getById(id));
    }

    /**
     * 修改食堂
     *
     * @param id           食堂 ID
     * @param name         食堂名称
     * @param startTime    开始营业时间
     * @param endTime      结束营业时间
     * @param introduction 食堂介绍
     * @param uid          用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String updateById(@PathVariable Integer id, String name, Date startTime, Date endTime,
            String introduction, String uid) throws Throwable {
        return GSON.toJson(diningHallService.updateById(id, name, startTime, endTime,
                introduction, uid));
    }

    /**
     * 修改图片
     *
     * @param id   食堂 ID
     * @param part 获取到的图片信息
     * @param uid  用户 ID
     * @return 状态码+文件名 status + fileName
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String updateImageUrl(@RequestParam("file") Part part, @PathVariable Integer id,
            String uid) throws Throwable {
        return GSON.toJson(diningHallService.updateImageUrl(id, part, uid));
    }

    /**
     * 修改权重
     *
     * @param idList 按权重降序排序的 ID List
     * @param uid    session 内的用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/weight", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String updateWeight(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(diningHallService.updateWeight(idList, uid));
    }

    /**
     * 删除食堂
     *
     * @param id  食堂 ID
     * @param uid 当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String deleteById(@PathVariable Integer id, String uid) throws Throwable {
        List<Integer> idList = new ArrayList<>(1);
        idList.add(id);
        return GSON.toJson(diningHallService.deleteById(idList, uid));
    }

    /**
     * 批量删除食堂
     *
     * @param idList 食堂 ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String deleteById(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(diningHallService.deleteById(idList, uid));
    }

    /**
     * 添加商户信息
     *
     * @param part     上传的文件
     * @param id       食堂 ID 必选
     * @param name     获取到的商户名 必选
     * @param username 获取到的用户名 必选
     * @param password 获取到的密码 必选
     * @param uid      session 内的用户 ID 必选
     * @return 状态码+文件名 status + fileName 返回文件名用于回显
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/stalls", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String addStalls(@RequestParam("file") Part part, @PathVariable Integer id, String
            name, String username, String password, String uid) throws Throwable {
        return GSON.toJson(stallService.add(part, name, username, password, id, uid));
    }

    /**
     * 保存权重
     *
     * @param id     获取到的食堂 ID
     * @param idList 按权重降序排序的 ID List
     * @param uid    session 内的用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/stalls/weight", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String changeWeight(@PathVariable Integer id, @RequestParam List<String> idList,
            String uid) throws Throwable {
        return GSON.toJson(stallService.saveWeight(idList, id, uid));
    }

    /**
     * 获取指定食堂的所有商户
     *
     * @param id 食堂 ID 必选
     * @return 状态码+集合 status + stalls 包含 stall 信息的 list 集合
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/stalls", method = RequestMethod.GET)
    @NeedLogin
    public String listStallsByDiningHellId(@PathVariable Integer id) throws Throwable {
        return GSON.toJson(stallService.list(id));
    }
}
