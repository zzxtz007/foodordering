package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.FoodCategoryMapper;
import com.qiancheng.om.model.FoodCategory;
import com.qiancheng.om.service.FoodCategoryService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 菜品分类的业务实现类
 *
 * @author 宫昊男
 */
@Service("FoodCategoryService")
@Transactional
public class FoodCategoryServiceImpl implements FoodCategoryService {
    private static final Logger LOGGER = Logger.getLogger(FoodCategoryServiceImpl.class);
    @Resource
    private FoodCategoryMapper foodCategoryMapper;
    private int ret;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Response insert(String name, String stallId, String uid) throws Throwable {
        LOGGER.debug(uid + "调用添加类别方法，入参" + name + "," + uid);
        if (null != name && null != uid) {
            if (stallId.equals(uid)) {
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setName(name);
                foodCategory.setStallId(uid);
                foodCategory.setInsertUser(uid);
                foodCategory.setUpdateUser(uid);
                int rows = foodCategoryMapper.insert(foodCategory);
                LOGGER.info(uid + "调用添加类别方法成功，返回行数为" +
                        new Response(0).add("rows", rows).toString());
                return new Response(0).add("rows", rows);
            } else {
                LOGGER.debug("操作用户不是此商户");
                return new Response(3);
            }
        } else {
            LOGGER.debug("参数为空");
            return new Response(3);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Response deleteById(List<Integer> idArr, String uid) throws Throwable {
        LOGGER.debug("调用删除类别方法，入参" + idArr.toString() + "," + uid);
        int rows;
        if (!idArr.isEmpty() && null != uid) {
            int[] categoriesIdArr = new int[idArr.size()];
            for (int i = 0; i < idArr.size(); i++) {
                categoriesIdArr[i] = idArr.get(i);
            }
            rows = foodCategoryMapper.deleteById(categoriesIdArr, uid);
            LOGGER.info(uid + "调用删除类别方法成功，返回行数为" +
                    new Response(0).add("rows", rows).toString());
            return new Response(0).add("rows", rows);
        } else {
            LOGGER.debug("参数为空");
            return new Response(3);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response updateById(Integer id, String name, String uid) throws Throwable {
        if (null != id && null != uid) {
            if (null != name) {
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setId(id);
                foodCategory.setName(name);
                foodCategory.setUpdateUser(uid);
                foodCategoryMapper.updateById(foodCategory);
                return new Response(0);
            } else {
                LOGGER.debug("参数为空");
                return new Response(3);
            }
        } else {
            LOGGER.debug("参数为空");
            return new Response(3);
        }
    }

    @Override
    public Response listById(String stallId) throws Throwable {
        if (null != stallId) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setStallId(stallId);
            List<Map<String, Object>> map = foodCategoryMapper.listById(foodCategory);
            int totalCount = foodCategoryMapper.count(foodCategory);
            return new Response(0).add("info", map).add("totalCount", totalCount);
        } else {
            return new Response(3);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateWeight(List<Integer> idList, String stallId, String uid) throws
            Throwable {
        if (idList == null || stallId == null || uid == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        if (!Objects.equals(stallId, uid)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.NO_PERMISSION);
        }

        // 查询商户下所有菜品分类 ID
        int size = idList.size();
        Set<Object> foundIdSet = new HashSet<>(size, 1);
        FoodCategory condition = new FoodCategory();
        condition.setStallId(stallId);
        List<Map<String, Object>> foundFoodCategoryList = foodCategoryMapper.listById(condition);
        for (Map<String, Object> foundFoodCategory : foundFoodCategoryList) {
            foundIdSet.add(foundFoodCategory.get("id"));
        }

        // 遍历传入的 ID List，计算各 ID 对应的权重值
        List<FoodCategory> foodCategoryList = new ArrayList<>(size);
        for (int i = 0, weight = size - 1; i < size; i++, weight--) {
            // ID 为空或不属于该商户时返回
            Integer id = idList.get(i);
            if (id == null || !foundIdSet.contains(id)) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }

            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setId(id);
            foodCategory.setWeight(weight);
            foodCategory.setUpdateUser(uid);
            foodCategoryList.add(foodCategory);
        }

        // 更新数据库
        for (FoodCategory foodCategory : foodCategoryList) {
            foodCategoryMapper.updateById(foodCategory);
        }

        return new Response(0);
    }
}
