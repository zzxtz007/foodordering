package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.*;
import com.qiancheng.om.model.*;
import com.qiancheng.om.service.RecommendedFoodService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 推荐菜品 service
 *
 * @author 郑孝蓉
 */
@Transactional
@Service("recommendFoodService")
public class RecommendedFoodServiceImpl implements RecommendedFoodService {
    private static final Logger LOGGER = Logger.getLogger(RecommendedFoodServiceImpl.class);
    @Autowired
    private RecommendedFoodMapper recommendedFoodMapper;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private FoodCategoryMapper foodCategoryMapper;
    @Autowired
    private StallMapper stallMapper;
    @Autowired
    private DiningHallMapper diningHallMapper;

    /**
     * 状态码：0正常 1：出错 2：登录异常 3：参数为空 类型转换异常
     */

    @Override
    public Response insert(Integer foodId, String uid) throws Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (foodId == null || uid == null) {
            return new Response(3);
        }
        boolean flag = checkoutFoodId(foodId);
        if (flag) {
            RecommendedFood recommendedFood = new RecommendedFood();
            recommendedFood.setFoodId(foodId);
            recommendedFood.setInsertUser(uid);
            recommendedFood.setUpdateUser(uid);
            recommendedFoodMapper.insert(recommendedFood);
            LOGGER.info(uid + "插入数据");
            return new Response(0);
        } else {
            return new Response(1);
        }
    }

    @Override
    public Response deleteById(List<Integer> recommendFoodId, String uid) throws Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (recommendFoodId == null || uid == null) {
            return new Response(3);
        }
        int[] idArr = new int[recommendFoodId.size()];
        for (int i = 0; i < recommendFoodId.size(); i++) {
            idArr[i] = recommendFoodId.get(i);
        }
        recommendedFoodMapper.deleteById(idArr, uid);
        LOGGER.info(uid + "删除了数据");
        return new Response(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response updateWeight(List<Integer> idList, String uid) throws Throwable {
        if (idList == null || uid == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        // 遍历 ID List，计算各 ID 对应的权重值
        int size = idList.size();
        List<RecommendedFood> recommendedFoodList = new ArrayList<>(size);
        for (int i = 0, weight = size - 1; i < size; i++, weight--) {
            Integer id = idList.get(i);
            if (id == null) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }

            RecommendedFood recommendedFood = new RecommendedFood();
            recommendedFood.setId(id);
            recommendedFood.setWeight(weight);
            recommendedFood.setUpdateUser(uid);
            recommendedFoodList.add(recommendedFood);
        }

        // 更新数据库
        for (RecommendedFood recommendedFood : recommendedFoodList) {
            recommendedFoodMapper.updateById(recommendedFood);
        }

        return new Response(0);
    }

    @Override
    public Response list(Integer rowsNum) throws Throwable {
        if (rowsNum == null) {
            return new Response(3);
        }
        List<Map<String, Object>> listAll = recommendedFoodMapper.list(rowsNum);
        List<Map<String, Object>> info = new ArrayList<>();
        int totalCount = 0;
        if (!listAll.isEmpty()) {
            for (Map<String, Object> map : listAll) {
                Object foodId = map.get("foodId");

                Food food = getFoodById(foodId);
                //判断获得的 food 是否为空 为空返回 3
                if (food == null) {
                    return new Response(3);
                }

                // 日期时间格式化
                map.put("insertTime", ((Timestamp) map.get("insertTime")).getTime());

                String stallId = getStallIdByFoodId(foodId);
                map.put("stallId", stallId);
                String stallName = getStallNameById(stallId);
                map.put("stallName", stallName);
                String diningHallName = getDiningHallNameByStallId(stallId);
                map.put("diningHallName", diningHallName);

                map.put("name", food.getName());
                map.put("imageUrl", food.getImageUrl());
                map.put("standardPrice", food.getStandardPrice());
                map.put("memberPrice", food.getMemberPrice());
                if (food.getName() != null) {
                    info.add(map);
                    totalCount++;
                }
            }
        }
        return new Response(0).add("info", info).add("totalCount", totalCount);
    }

    /**
     * 根据菜品id查询商户id
     *
     * @param foodId 菜品ID
     * @return stallId 商户ID
     */
    private String getStallIdByFoodId(Object foodId) {
        //判断传入的参数是否为空？若为空 返回 null
        if ((Integer) foodId == 0) {
            return null;
        }
        String stallId = null;
        Object categoryId = 0;
        Food food = new Food();
        food.setId((Integer) foodId);
        food.setIsDeleted(false);
        List<Map<String, Object>> foodArr = foodMapper.list(food, null, null);
        for (Map<String, Object> map : foodArr) {
            categoryId = map.get("categoryId");
        }
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setId((Integer) categoryId);
        List<Map<String, Object>> foodCategoryArr = foodCategoryMapper.listById(foodCategory);
        for (Map<String, Object> stringObjectMap : foodCategoryArr) {
            stallId = (String) stringObjectMap.get("stallId");
        }
        return stallId;
    }

    /**
     * 根据商户id查询食堂名称
     *
     * @param stallId 商户ID
     * @return diningHallName 食堂名称
     */
    private String getDiningHallNameByStallId(String stallId) {
        //判断传入的参数是否为空？若为空 返回 null
        if (stallId == null) {
            return null;
        }
        //根据商户id查询食堂id
        Stall stall = new Stall();
        stall.setId(stallId);
        stall.setIsDeleted(false);
        Integer diningHallId = 0;
        List<Map<String, Object>> stallArr = stallMapper.list(stall);
        for (Map<String, Object> map : stallArr) {
            diningHallId = (Integer) map.get("diningHallId");
        }
        //根据食堂id查询食堂名称
        DiningHall diningHall = new DiningHall();
        diningHall.setId(diningHallId);
        String diningHallName = null;
        List<Map<String, Object>> diningHallArr = diningHallMapper.list(diningHall);
        for (Map<String, Object> stringObjectMap : diningHallArr) {
            diningHallName = (String) stringObjectMap.get("name");
        }
        return diningHallName;
    }

    /**
     * 根据商户ID查询商户名称
     *
     * @param stallId 商户
     * @return stallName 商户名称
     */
    private String getStallNameById(String stallId) {
        //判断传入的参数是否为空？若为空 返回 null
        if (stallId == null) {
            return null;
        }
        //根据商户id查询商户名称
        Stall stall = new Stall();
        stall.setId(stallId);
        stall.setIsDeleted(false);
        String stallName = null;
        List<Map<String, Object>> stallArr = stallMapper.list(stall);
        for (Map<String, Object> map : stallArr) {
            stallName = (String) map.get("name");
        }
        return stallName;
    }

    /**
     * 根据foodId查询food对象
     *
     * @param foodId 菜品 ID
     * @return food 对象
     */
    private Food getFoodById(Object foodId) {
        //判断传入的参数是否为空？若为空 返回 null
        if ((Integer) foodId == 0) {
            return null;
        }
        Food food = new Food();
        food.setId((Integer) foodId);
        food.setIsDeleted(false);
        Food food1 = new Food();
        List<Map<String, Object>> foodArr = foodMapper.list(food, null, null);
        for (Map<String, Object> map : foodArr) {
            food1.setImageUrl((String) map.get("imageUrl"));
            food1.setName((String) map.get("name"));
            food1.setStandardPrice((BigDecimal) map.get("standardPrice"));
            food1.setMemberPrice((BigDecimal) map.get("memberPrice"));
        }
        return food1;
    }

    /**
     * 验证菜品是否存在
     *
     * @param foodId 菜品 ID
     * @return true 菜品存在 false 菜品不存在
     */
    private boolean checkoutFoodId(Integer foodId) {
        Food food = new Food();
        food.setId(foodId);
        food.setIsDeleted(false);
        List<Map<String, Object>> foodArr = foodMapper.list(food, null, null);
        for (Map<String, Object> map : foodArr) {
            if (foodId.equals(map.get("id"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证推荐菜品是否存在
     *
     * @param id 推荐菜品 ID
     * @return true 存在 false 不存在
     */
    private boolean checkoutRecommendedFood(Integer id) {
        RecommendedFood recommendedFood = new RecommendedFood();
        recommendedFood.setId(id);
        List<Map<String, Object>> listAll = recommendedFoodMapper.list(null);
        for (Map<String, Object> map : listAll) {
            if (id.equals(map.get("id"))) {
                return true;
            }
        }
        return false;
    }
}
