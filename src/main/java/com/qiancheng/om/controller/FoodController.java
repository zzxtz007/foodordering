package com.qiancheng.om.controller;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.GetUserInfo;
import com.qiancheng.om.common.annotation.NeedLogin;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.service.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/food", produces = "application/json;charset=utf-8")
@ResponseBody
public class FoodController {
    private static final Gson GSON = new Gson();
    @Resource
    private FoodService foodService;

    /**
     * 获取菜品信息
     *
     * @param id 菜品 ID 必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    public String getById(@PathVariable Integer id) throws Throwable {
        return GSON.toJson(foodService.getById(id));
    }

    /**
     * 获取菜品好评率
     *
     * @param id foodId 菜品 ID 必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/ratings", method = RequestMethod.GET)
    @NeedLogin
    public String mark(@PathVariable Integer id) throws Throwable {
        return GSON.toJson(foodService.mark(id));
    }

    /**
     * 批量提交菜品评分
     *
     * @param markMap 菜品 ID-评分 Map 必选
     * @param uid     session 内用户 ID   必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.CONSUMER)
    @GetUserInfo
    public String updateMark(@RequestParam(value = "markMap") Map<Integer, Integer> markMap,
            String uid) throws
            Throwable {
        return GSON.toJson(foodService.updateMark(markMap, uid));
    }

    /**
     * 修改菜品信息
     *
     * @param id               菜品 ID 必选
     * @param name             菜品名称 可选
     * @param introduction     菜品介绍 可选
     * @param categoryId       菜品分类 ID  可选
     * @param standardPrice    标准价格，即非会员价格 可选
     * @param memberPriceCost  会员价格成本，即商户提供给运营的会员价菜品的价格  可选
     * @param memberPrice      会员价格 可选
     * @param packFee          打包费 可选
     * @param memberPriceLimit 会员价菜品每日限量 可选
     * @param saleCount        总销量 可选
     * @param uid              session 内用户 ID 必选
     * @param role             操作用户角色：stall(商户)、operator（运营端用户）必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @NeedLogin
    @NeedRole({UserRoleEnum.STALL, UserRoleEnum.OPERATOR, UserRoleEnum.OPERATOR_ADMIN})
    @GetUserInfo
    public String updateFood(@PathVariable Integer id, String name, Integer categoryId,
            BigDecimal packFee, BigDecimal memberPriceCost, String introduction, BigDecimal
            standardPrice, BigDecimal memberPrice, Integer memberPriceLimit, Integer saleCount,
            String uid, UserRoleEnum role) throws Throwable {
        switch (role) {
            case STALL:
                return GSON.toJson(foodService.updateByStallById(id, uid, name, categoryId,
                        packFee, standardPrice, introduction));
            case OPERATOR:
            case OPERATOR_ADMIN:
                return GSON.toJson(foodService.updateByOperatorById(id, uid, memberPrice,
                        memberPriceCost, memberPriceLimit, saleCount));
            default:
                return null;
        }
    }

    /**
     * 修改菜品图片
     *
     * @param id   菜品 ID 必选
     * @param part 图片路径
     * @param uid  session 内用户 ID 必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String updateImageUrl(@PathVariable Integer id, @RequestParam("file") Part part,
            String uid) throws Throwable {
        return GSON.toJson(foodService.changeImage(id, part, uid));
    }

    /**
     * 菜品上架
     *
     * @param id  菜品 ID 必选
     * @param uid session 内用户 ID 必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/on-sale", method = RequestMethod.POST)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String upOnSaleById(@PathVariable Integer id, String uid) throws Throwable {
        return GSON.toJson(foodService.updateOnSaleById(id, uid, true));
    }

    /**
     * 菜品下架
     *
     * @param id  菜品 ID 必选
     * @param uid session 内用户 ID 必选
     * @return Json 格式的 Response
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(value = "/{id}/on-sale", method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String downOnSaleById(@PathVariable Integer id, String uid) throws Throwable {
        return GSON.toJson(foodService.updateOnSaleById(id, uid, false));
    }

    /**
     * 删除菜品
     *
     * @param id  菜品 ID
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
        return GSON.toJson(foodService.deleteById(idList, uid));
    }

    /**
     * 批量删除菜品
     *
     * @param idList 菜品 ID List
     * @param uid    当前用户 ID
     * @return 响应值
     * @throws Throwable 发生异常时抛出
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @NeedLogin
    @NeedRole(UserRoleEnum.STALL)
    @GetUserInfo
    public String deleteById(@RequestParam List<Integer> idList, String uid) throws Throwable {
        return GSON.toJson(foodService.deleteById(idList, uid));
    }
}











