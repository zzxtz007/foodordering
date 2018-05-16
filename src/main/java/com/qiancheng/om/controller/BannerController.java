package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.BannerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

/**
 * 广告controller
 *
 * @author 毕立宇
 */
@Controller
@RequestMapping(value = "/banners", produces = "application/json;charset=utf-8")
@ResponseBody
public class BannerController {
    private static final Gson GSON = new Gson();
    @Resource
    private BannerService bannerService;

    /**
     * 添加Banner
     *
     * @param link 超链接
     * @param part 含有图片二进制数据的 Part 对象
     * @param uid  当前用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.POST)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String insertBanner(String link, @RequestParam("file") Part part, String uid) throws
            Throwable {
        return GSON.toJson(bannerService.insert(link, uid, part));
    }


    /**
     * 删除 Banner
     *
     * @param id  Banner ID
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
        return GSON.toJson(bannerService.deleteById(idList, uid));
    }

    /**
     * 批量删除 Banner
     *
     * @param idList Banner ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String deleteById(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(bannerService.deleteById(idList, uid));
    }


    /**
     * 修改Banner
     *
     * @param id   Banner ID
     * @param link 超链接
     * @param uid  当前用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String updateBanner(@PathVariable Integer id, String link, String uid) throws Throwable {
        return GSON.toJson(bannerService.updateById(id, uid, link));
    }

    /**
     * 修改图片
     *
     * @param part 含有图片二进制数据的 Part 对象
     * @param id   Banner ID
     * @param uid  当前用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String changeImage(@RequestParam("file") Part part, @PathVariable Integer id, String
            uid) throws Throwable {
        return GSON.toJson(bannerService.updateImage(part, uid, id));
    }

    /**
     * 获取所有Banner广告
     *
     * @return Banner集合
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN, UserRoleEnum.CONSUMER})
    public String listAll() throws Throwable {
        return GSON.toJson(bannerService.listAll());
    }

    /**
     * 修改Banner权重
     *
     * @param idList 按权重降序排序的 ID List
     * @param uid    当前用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/weight", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String changeWeight(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(bannerService.saveWeight(idList, uid));
    }
}
