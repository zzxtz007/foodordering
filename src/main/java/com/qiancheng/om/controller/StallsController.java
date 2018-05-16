package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.FoodCategoryService;
import com.qiancheng.om.service.OrderService;
import com.qiancheng.om.service.StallService;
import com.qiancheng.om.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商户 Controller
 *
 * @author Ice_Dog
 */
@Controller
@RequestMapping(value = "/stalls", produces = "application/json;charset=utf-8")
@ResponseBody
public class StallsController {
    private static final Gson GSON = new Gson();
    @Resource
    private StallService stallService;
    @Resource
    private OrderService orderService;
    @Resource
    private FoodCategoryService foodCategoryService;
    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取指定 id 商户信息
     *
     * @param id 商户 ID
     * @return 状态码+集合 status + info 包含 stall 信息的 map 集合
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @NeedLogin
    public String getByStallId(@PathVariable String id) throws Throwable {
        return GSON.toJson(stallService.getById(id));
    }

    /**
     * 修改商户图片
     *
     * @param part 上传的文件
     * @param id   获取到的商户 ID 必选
     * @param uid  用戶 ID 必选
     * @return 状态码+文件名 status + info
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN, UserRoleEnum.STALL})
    @GetUserInfo
    public String editStallImageByStallId(@RequestParam("file") Part part, @PathVariable String id,
            String uid) throws Throwable {
        return GSON.toJson(stallService.changeImage(id, part, uid));
    }

    /**
     * 修改商户信息
     *
     * @param id           获取到的商户 ID 必选
     * @param name         获取到的商户名 必选
     * @param phone        获取到的手机号码 可选
     * @param introduction 获取到的通知信息 可选
     * @param username     获取到的用户名 可选
     * @param password     获取到的密码 可选
     * @param uid          用户 ID 必选
     * @param role         用户的角色信息 Role 必选
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN, UserRoleEnum.STALL})
    @GetUserInfo
    public String editStallByStallId(@PathVariable String id, String name, String username,
            String password, String phone, String introduction, String uid, UserRoleEnum role)
            throws Throwable {
        return GSON.toJson(stallService.edit(id, name, username, password, phone, introduction,
                uid, role));
    }

    /**
     * 设置开店
     *
     * @param id  商户 ID
     * @param uid session 内的用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/opening", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String stallOpen(@PathVariable String id, String uid) throws Throwable {
        return GSON.toJson(stallService.changeStatus(id, true, uid));
    }

    /**
     * 设置关店
     *
     * @param id  商户 ID
     * @param uid session 内的用户 ID
     * @return 状态码
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/opening", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String stallClose(@PathVariable String id, String uid) throws Throwable {
        return GSON.toJson(stallService.changeStatus(id, false, uid));
    }

    /**
     * 删除商户
     *
     * @param id  商户 ID
     * @param uid 当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String delete(@PathVariable String id, String uid) throws Throwable {
        List<String> idList = new ArrayList<>(1);
        idList.add(id);
        return GSON.toJson(stallService.deleteById(idList, uid));
    }

    /**
     * 批量删除商户
     *
     * @param idList 商户 ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole({UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String removeStall(@RequestParam List<String> idList, String uid) throws Throwable {
        return GSON.toJson(stallService.deleteById(idList, uid));
    }

    /**
     * 获取商户订单列表
     *
     * @param id         页面传出的用户 ID
     * @param statusList 订单状态
     * @param pageNum    页码
     * @param pageSize   每页条目数
     * @param uid        session 内的用户 ID
     * @param startDate 查询起始日期
     * @param endDate   查询结束日期
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String listStallOrdersByStatus(@PathVariable String id, @RequestParam List<Integer>
            statusList, Integer pageNum, Integer pageSize, String uid, Date startDate, Date
            endDate) throws Throwable {
        return GSON.toJson(orderService.listByStatus(id, uid, statusList, pageNum, pageSize,
                startDate, endDate));
    }

    /**
     * 根据商户id查询商品分类
     *
     * @param id 页面传出的用户 ID
     * @return Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/food-categories", method = RequestMethod.GET)
    @NeedLogin
    public String listById(@PathVariable String id) throws Throwable {
        return GSON.toJson(foodCategoryService.listById(id));
    }

    /**
     * 添加菜品分类
     *
     * @param id   页面传出的商户 ID
     * @param name 页面传出的菜品分类名
     * @param uid  session 内的用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/food-categories", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String insertFoodCategory(String name, @PathVariable String id, String uid) throws
            Throwable {
        return GSON.toJson(foodCategoryService.insert(name, id, uid));
    }

    /**
     * 修改菜品分类权重
     *
     * @param id     商户 ID
     * @param idList 按权重降序排序的菜品 ID List
     * @param uid    session 内的用户 ID
     * @return 状态码 status
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/food-categories/weight", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String updateFoodCategoryWeight(@PathVariable String id, @RequestParam List<Integer>
            idList, String uid) throws Throwable {
        return GSON.toJson(foodCategoryService.updateWeight(idList, id, uid));
    }

    /**
     * 运营方获取商户营收情况列表
     *
     * @param date     查询日期
     * @param pageNum  页码
     * @param pageSize 每页条目数
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/statistics/revenue", method = RequestMethod.GET)
    @NeedLogin
    @NeedRole(UserRoleEnum.OPERATOR_ADMIN)
    public String listRevenueStatistics(Date date, Integer pageNum, Integer pageSize) throws
            Throwable {
        return GSON.toJson(statisticsService.getAllStallRevenueByDay(date, pageNum, pageSize));
    }

    /**
     * 获取指定商户的营收情况
     *
     * @param id        商户 ID
     * @param startDate 查询起始日期
     * @param endDate   查询结束日期
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/statistics/revenue", method = RequestMethod.GET)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    public String getRevenueStatistics(@PathVariable String id, Date startDate, Date endDate)
            throws Throwable {
        return GSON.toJson(statisticsService.getRevenueByTime(id, startDate, endDate));
    }

    /**
     * 获取指定商户的退款情况
     *
     * @param id        商户 ID
     * @param startDate 查询起始日期
     * @param endDate   查询结束日期
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/statistics/refund", method = RequestMethod.GET)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    public String getRefundStatistics(@PathVariable String id, Date startDate, Date endDate)
            throws Throwable {
        return GSON.toJson(statisticsService.getRefundByTime(id, startDate, endDate));
    }
}
