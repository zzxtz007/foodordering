package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.ImageUtils;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.FoodCategoryMapper;
import com.qiancheng.om.dao.FoodMapper;
import com.qiancheng.om.dao.OrderedFoodMapper;
import com.qiancheng.om.model.Food;
import com.qiancheng.om.model.FoodCategory;
import com.qiancheng.om.model.OrderedFood;
import com.qiancheng.om.service.FoodService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * 菜品业务的实现类
 *
 * @author 谢治波
 */
@Transactional
@Service("FoodService")
public class FoodServiceImpl implements FoodService {
    /**
     * 菜品图片存放路径下的包名
     */
    private final static String IMAGE_PATH = "upload/image/food/";
    private static final Logger LOGGER = Logger.getLogger(FoodServiceImpl.class);
    /**
     * 具体查询信息
     */
    private final String INFO = "info";
    @Resource
    private FoodMapper foodMapper;
    @Resource
    private OrderedFoodMapper orderedFoodMapper;
    @Resource
    private FoodCategoryMapper foodCategoryMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response insert(Part part, String name, String introduction, Integer categoryId,
            BigDecimal packFee, BigDecimal standardPrice, String uid) throws Throwable {
        if (part == null || name == null || categoryId == null || standardPrice == null || uid
                == null || introduction == null || packFee == null) {
            return new Response(3);
        }
        Food food = new Food();
        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        food.setName(name);
        food.setImageUrl(fileName);
        food.setCategoryId(categoryId);
        food.setStandardPrice(standardPrice);
        food.setInsertUser(uid);
        food.setUpdateUser(uid);
        food.setSaleCount(0);
        food.setIntroduction(introduction);
        food.setPackFee(packFee);
        List<Map<String, Object>> list = listCategoryById(food.getCategoryId());
        //判断菜品分类是否存在
        if (list == null) {
            LOGGER.debug("没有此菜品分类");
            return new Response(3);
        }
        //判断插入菜品权限
        if (!uid.equals(list.get(0).get("stallId"))) {
            LOGGER.debug("您没有权限添加此菜品信息！");
            return new Response(3);
        }
        int rows = foodMapper.insert(food);
        if (rows > 0) {
            return new Response(0).add("info", fileName);
        } else {
            return new Response(1);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response deleteById(List<Integer> idArr, String uid) throws Throwable {
        Response response = null;
        if (!idArr.isEmpty() && uid != null) {
            int[] foodIdArr = new int[idArr.size()];
            for (int i = 0; i < idArr.size(); i++) {
                foodIdArr[i] = idArr.get(i);
                //判断删除权限
                Response res = getById(idArr.get(i));
                if (0 == (Integer) res.get("status")) {
                    Integer categoryId = (Integer) ((List<Map<String, Object>>) res.get(INFO))
                            .get(0).get("categoryId");
                    List<Map<String, Object>> list = listCategoryById(categoryId);
                    if (!uid.equals(list.get(0).get("stallId"))) {
                        LOGGER.debug("您没有权限修改此菜品信息！");
                        return new Response(3);
                    }
                }
            }
            int rows = foodMapper.deleteById(foodIdArr, uid);
            if (rows > 0) {
                response = new Response(0);
            } else {
                throw new RuntimeException();
            }
        } else {
            response = new Response(1);
        }
        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response updateByStallById(Integer id, String uid, String name, Integer categoryId,
            BigDecimal packFee, BigDecimal standardPrice, String introduction) throws Throwable {
        if (id == null || uid == null || name == null || categoryId == null || packFee == null
                || standardPrice == null || introduction == null) {
            return new Response(3);
        }
        Food food = new Food();
        food.setId(id);
        food.setUpdateUser(uid);
        food.setName(name);
        food.setCategoryId(categoryId);
        food.setPackFee(packFee);
        food.setStandardPrice(standardPrice);
        food.setIntroduction(introduction);
        food.setUpdateUser(uid);
        List<Map<String, Object>> list = listCategoryById(food.getCategoryId());
        //判断菜品分类是否存在
        if (list == null) {
            LOGGER.debug("没有此菜品分类");
            return new Response(3);
        }
        //判断修改权限
        if (!uid.equals(list.get(0).get("stallId"))) {
            LOGGER.debug("您没有权限修改此菜品信息！");
            return new Response(3);
        }
        int rows = foodMapper.updateById(food);
        if (rows > 0) {
            return new Response(0);
        } else {
            return new Response(3);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public Response changeImage(Integer foodId, Part part, String uid) throws Throwable {
        // 判断传入的参数是否为空？若为空 返回 3
        if (part == null || foodId == null || uid == null) {
            return new Response(3);
        }
        //确认修改权限
        Response res = getById(foodId);
        if ((Integer) res.get("status") == 0) {
            Integer categoryId = (Integer) ((List<Map<String, Object>>) res.get(INFO)).get(0).get
                    ("categoryId");
            List<Map<String, Object>> list = listCategoryById(categoryId);
            if (!uid.equals(list.get(0).get("stallId"))) {
                LOGGER.debug("您没有权限修改此菜品图片！");
                return new Response(3);
            }
        }
        //前端传入图片文件转成字符串
        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        //把文件名传入数据库
        Food food = new Food();
        food.setId(foodId);
        food.setImageUrl(fileName);
        food.setUpdateUser(uid);
        int rows = foodMapper.updateById(food);
        if (rows == 0) {
            return new Response(1);
        }
        return new Response(0).add("info", fileName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response updateByOperatorById(Integer id, String uid, BigDecimal memberPrice, BigDecimal
            memberPriceCost, Integer memberPriceLimit, Integer saleCount) throws Throwable {
        // 缺失必要参数时抛出非法访问异常
        if (id == null || uid == null || saleCount == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        // mp, mpc, mpl 必须同时为空或同时不为空，否则抛出非法访问异常
        if ((memberPrice == null ^ memberPriceCost == null) || (memberPrice == null ^
                memberPriceLimit == null)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
        }

        // 价格四舍五入
        if (memberPrice != null) {
            memberPrice = memberPrice.setScale(2, RoundingMode.HALF_UP);
            memberPriceCost = memberPriceCost.setScale(2, RoundingMode.HALF_UP);
        }

        Food food = new Food();
        food.setId(id);

        List<Map<String, Object>> foodList = foodMapper.list(food, null, null);

        if (foodList.isEmpty()) {
            return new Response(3);
        }

        Map<String, Object> foodMap = foodList.get(0);

        // 如果取消会员优惠，则将会员相关数值设为 -1，由 Mapper 将对应字段设为 null
        if (memberPrice == null) {
            memberPrice = new BigDecimal(-1);
            memberPriceCost = new BigDecimal(-1);
            memberPriceLimit = -1;
        }

        // 如果取消会员优惠，则将会员价剩余数量设为 -1，由 Mapper 将对应字段设为 null
        // 如果设置会员优惠，则当会员价剩余数量为 null 时，将剩余数量改为输入的每日限量，否则不改变剩余数量
        Integer memberPriceRemaining = memberPrice.equals(new BigDecimal(-1)) ? -1 : foodMap.get
                ("memberPriceRemaining") != null ? (Integer) foodMap.get("memberPriceRemaining")
                : memberPriceLimit;

        food.setMemberPrice(memberPrice);
        food.setMemberPriceCost(memberPriceCost);
        food.setMemberPriceLimit(memberPriceLimit);
        food.setMemberPriceRemaining(memberPriceRemaining);
        food.setSaleCount(saleCount);
        food.setUpdateUser(uid);

        // 菜品 ID 不存在时，影响行数不为 1，抛出非法访问异常
        if (foodMapper.updateById(food) != 1) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
        }

        return new Response(0);
    }

    @Override
    public Response listByCategoryId(List<Integer> categoryIds, Integer pageNum, Integer pageSize,
            UserRoleEnum role) throws Throwable {
        // 参数丢失时抛出异常
        if (categoryIds == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        // 参数非法时抛出异常
        for (Integer categoryId : categoryIds) {
            if (categoryId == null) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }
        }

        List<Map<String, Object>> resultList = new ArrayList<>(10);
        int totalCount = 0;

        switch (role) {
            case CONSUMER:
            case STALL: {
                // 遍历条件进行查询，汇总结果
                for (Integer categoryId : categoryIds) {
                    Food condition = new Food();
                    condition.setCategoryId(categoryId);
                    condition.setIsDeleted(false);
                    List<Map<String, Object>> tempResultList = foodMapper.list(condition,
                            null, null);

                    // 写入菜品分类 ID 和评分
                    for (Map<String, Object> result : tempResultList) {
                        result.put("categoryId", categoryId);
                        Integer foodId = (Integer) result.get("id");
                        result.put("positiveRate", mark(foodId).get("info"));
                    }

                    resultList.addAll(tempResultList);
                    totalCount += foodMapper.count(condition);
                }
                break;
            }
            case OPERATOR:
            case OPERATOR_ADMIN: {
                // 参数不完整时抛出异常
                if (pageNum == null ^ pageSize == null) {
                    throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
                }

                // 参数非法时抛出异常
                if (pageNum != null && (pageNum < 1 || pageSize < 1)) {
                    throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
                }

                Food condition = new Food();
                int categoryId = categoryIds.get(0);
                condition.setCategoryId(categoryId);
                condition.setIsDeleted(false);
                Integer startIndex = null;
                if (pageNum != null) {
                    startIndex = (pageNum - 1) * pageSize;
                }

                List<Map<String, Object>> tempResultList = foodMapper.list(condition,
                        startIndex, pageSize);

                resultList.addAll(tempResultList);
                totalCount = foodMapper.count(condition);
                break;
            }
            default: {
                break;
            }
        }

        return new Response(0).add(INFO, resultList).add("totalCount", totalCount);
    }

    @Override
    public Response getById(Integer id) throws Throwable {
        if (id == null) {
            return new Response(3);
        }
        Food food = new Food();
        food.setId(id);
        food.setIsDeleted(false);
        List<Map<String, Object>> list = foodMapper.list(food, null, null);
        if (list.isEmpty()) {
            return new Response(3);
        }
        return new Response(0).add(INFO, list);
    }

    @Override
    public Response mark(Integer id) throws Throwable {
        if (id == null) {
            return new Response(3);
        }
        OrderedFood orderedFood = new OrderedFood();
        orderedFood.setFoodId(id);
        List<Map<String, Object>> list = orderedFoodMapper.list(orderedFood);
        //总分
        int positiveScore = 0;
        if (list.size() == 0) {
            return new Response(0).add(INFO, 0);
        }
        //按照评价等级计算的好评订单总分
        for (Map map : list) {
            switch ((int) map.get("mark")) {
                case -2:
                    positiveScore += 80;
                    break;
                case -1:
                    positiveScore += 85;
                    break;
                case 0:
                    positiveScore += 90;
                    break;
                case 1:
                    positiveScore += 95;
                    break;
                case 2:
                    positiveScore += 100;
                    break;
            }
        }
        try {
            //计算positiveRate好评率
            //转换成double型
            double positiveRate = new BigDecimal(positiveScore / (100 * list.size()))
                    .setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            return new Response(0).add(INFO, positiveRate);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("算数异常，异常信息：", e);
            return new Response(4);
        } catch (Exception e) {
            LOGGER.debug("其他异常，异常信息：", e);
            return new Response(1);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response updateMark(Map<Integer, Integer> markMap, String uid) throws Throwable {
        if (markMap == null || uid == null) {
            return new Response(3);
        }
        for (Map.Entry<Integer, Integer> entry : markMap.entrySet()) {
            Integer id = entry.getKey();
            Integer mark = entry.getValue();

            OrderedFood orderedFood = new OrderedFood();
            orderedFood.setId(id);
            orderedFood.setMark(mark);
            orderedFood.setUpdateUser(uid);
            if (orderedFood.getMark() > -3 && orderedFood.getMark() < 3) {
                int row = orderedFoodMapper.updateById(orderedFood);
            } else {
                return new Response(3);
            }
        }
        return new Response(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response updateWeight(List<Integer> idList, Integer categoryId, String uid) throws
            Throwable {
        if (idList == null || categoryId == null || uid == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        // 获取菜品分类信息
        List<Map<String, Object>> foundFoodCategoryList = listCategoryById(categoryId);
        if (foundFoodCategoryList == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
        }
        Map<String, Object> foundFoodCategory = foundFoodCategoryList.get(0);

        // 检查菜品分类是否属于当前用户
        String foundStallId = (String) foundFoodCategory.get("stallId");
        if (!Objects.equals(uid, foundStallId)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.NO_PERMISSION);
        }

        // 获取菜品分类下所有菜品 ID
        int size = idList.size();
        Set<Object> foundIdSet = new HashSet<>(size, 1);
        Food condition = new Food();
        condition.setCategoryId(categoryId);
        condition.setIsDeleted(false);
        List<Map<String, Object>> foundFoodList = foodMapper.list(condition, null, null);
        for (Map<String, Object> foundFood : foundFoodList) {
            foundIdSet.add(foundFood.get("id"));
        }

        // 遍历 idList，计算各 ID 对应的权重值
        List<Food> foodList = new ArrayList<>(size);
        for (int i = 0, weight = size; i < size; i++, weight--) {
            // ID 为空或不属于该菜品分类时返回
            Integer id = idList.get(i);
            if (id == null || !foundIdSet.contains(id)) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }

            Food food = new Food();
            food.setId(id);
            food.setWeight(weight);
            food.setUpdateUser(uid);
            foodList.add(food);
        }

        // 更新数据库
        for (Food food : foodList) {
            foodMapper.updateById(food);
        }

        return new Response(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response updateOnSaleById(Integer foodId, String uid, Boolean foodIsOnSale) throws
            Throwable {
        if (foodId == null || uid == null || foodIsOnSale == null) {
            return new Response(3);
        }
        //判断修改权限
        Response res = getById(foodId);
        if (0 == (Integer) res.get("status")) {
            Integer categoryId = (Integer) ((List<Map<String, Object>>) res.get(INFO)).get(0).get
                    ("categoryId");
            List<Map<String, Object>> list = listCategoryById(categoryId);
            if (!uid.equals(list.get(0).get("stallId"))) {
                LOGGER.debug("您没有权限修改此菜品图片！");
                return new Response(3);
            }
        }
        Food food = new Food();
        food.setId(foodId);
        food.setUpdateUser(uid);
        food.setIsOnSale(foodIsOnSale);
        int rows = foodMapper.updateById(food);
        return new Response(0);
    }

    /**
     * 判断分类是否存在
     *
     * @param categoryId 菜品分类ID 必须
     * @return true/false  真/假
     */
    private List<Map<String, Object>> listCategoryById(Integer categoryId) {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setId(categoryId);
        List<Map<String, Object>> list = foodCategoryMapper.listById(foodCategory);
        return list;
    }
}
