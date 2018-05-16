package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.FoodCategoryService;
import com.qiancheng.om.service.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/food-categories", produces = "application/json;charset=utf-8")
@ResponseBody
public class FoodCategoryController {
    private static final Gson GSON = new Gson();
    @Resource
    private FoodCategoryService foodCategoryService;
    @Resource
    private FoodService foodService;

    /**
     * 按ID修改菜品分类名称
     *
     * @param id   页面传出的菜品分类 ID
     * @param name 页面传出的菜品分类名
     * @param uid  session 内的用户 ID
     * @return 状态码+影响行数 status + rows
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String updateById(@PathVariable Integer id, String name, String uid) throws Throwable {
        return GSON.toJson(foodCategoryService.updateById(id, name, uid));
    }

    /**
     * 删除菜品分类
     *
     * @param id  菜品分类 ID
     * @param uid 当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String deleteById(@PathVariable Integer id, String uid) throws Throwable {
        List<Integer> idList = new ArrayList<>(1);
        idList.add(id);
        return GSON.toJson(foodCategoryService.deleteById(idList, uid));
    }

    /**
     * 批量删除菜品分类
     *
     * @param idList 菜品分类 ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String deleteById(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(foodCategoryService.deleteById(idList, uid));
    }

    /**
     * 获取指定菜品分类下的菜品
     *
     * @param id       页面传出的菜品分类 ID
     * @param pageNum  页码
     * @param pageSize 每页条目数
     * @param role     当前用户角色
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/food", method = RequestMethod.GET)
    @NeedLogin
    @GetUserInfo
    public String listByCategoryId(@PathVariable Integer id, Integer pageNum, Integer pageSize,
            UserRoleEnum role) throws Throwable {
        List<Integer> idList = new ArrayList<>(1);
        idList.add(id);
        return GSON.toJson(foodService.listByCategoryId(idList, pageNum, pageSize, role));
    }

    /**
     * 批量获取菜品分类下的菜品
     *
     * @param idList   菜品分类 ID List
     * @param pageNum  页码
     * @param pageSize 每页条目数
     * @param role     当前用户角色
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/food", method = RequestMethod.GET)
    @NeedLogin
    @GetUserInfo
    public String listByCategoryId(@RequestParam List<Integer> idList, Integer pageNum, Integer
            pageSize, UserRoleEnum role) throws Throwable {
        return GSON.toJson(foodService.listByCategoryId(idList, pageNum, pageSize, role));
    }

    /**
     * 添加菜品到指定菜品分类
     *
     * @param id            菜品分类 ID
     * @param part          菜品图片
     * @param name          菜品名称
     * @param introduction  菜品介绍
     * @param packFee       打包费用
     * @param standardPrice 菜品标准价格
     * @param uid           当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/food", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String insertFood(@PathVariable Integer id, String name, @RequestParam("file") Part
            part, String introduction, BigDecimal packFee, BigDecimal standardPrice, String uid)
            throws Throwable {
        return GSON.toJson(foodService.insert(part, name, introduction, id, packFee,
                standardPrice, uid));
    }

    /**
     * 修改指定菜品分类下的菜品权重
     *
     * @param id     菜品分类 ID
     * @param idList 按权重降序排序的 ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/food/weight", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String updateWeight(@PathVariable Integer id, @RequestParam List<Integer> idList,
            String uid) throws Throwable {
        return GSON.toJson(foodService.updateWeight(idList, id, uid));
    }
}
