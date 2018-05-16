package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.RecommendedFoodService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 推荐菜品
 *
 * @author 郑孝蓉
 */
@Controller
@RequestMapping(value = "/recommended-food", produces = {"application/json;charset=utf-8"})
@ResponseBody
public class RecommendedFoodController {
    private static final Gson GSON = new Gson();
    @Resource
    private RecommendedFoodService recommendedFoodService;

    /**
     * 添加推荐菜品
     *
     * @param foodId 菜品 ID
     * @param uid    session 内的用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.POST)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String insert(Integer foodId, String uid) throws Throwable {
        return GSON.toJson(recommendedFoodService.insert(foodId, uid));
    }

    /**
     * 删除推荐菜品
     *
     * @param id  推荐菜品 ID
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
        return GSON.toJson(recommendedFoodService.deleteById(idList, uid));
    }

    /**
     * 批量删除推荐菜品
     *
     * @param idList 推荐菜品 ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String deleteById(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(recommendedFoodService.deleteById(idList, uid));
    }

    /**
     * 修改权重
     *
     * @param idList 按权重降序排序的 ID List
     * @param uid    session 内的用户 ID
     * @return 状态码status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/weight", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String updateWeight(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(recommendedFoodService.updateWeight(idList, uid));
    }

    /**
     * 获取推荐菜品列表
     *
     * @param count 获取的数量
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin
    public String list(Integer count) throws Throwable {
        return GSON.toJson(recommendedFoodService.list(count));
    }
}
