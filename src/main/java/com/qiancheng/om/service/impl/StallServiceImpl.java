package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.*;
import com.qiancheng.om.dao.DiningHallMapper;
import com.qiancheng.om.dao.FoodCategoryMapper;
import com.qiancheng.om.dao.FoodMapper;
import com.qiancheng.om.dao.StallMapper;
import com.qiancheng.om.model.DiningHall;
import com.qiancheng.om.model.Food;
import com.qiancheng.om.model.FoodCategory;
import com.qiancheng.om.model.Stall;
import com.qiancheng.om.service.StallService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.util.*;

/**
 * 商店业务实现
 *
 * @author Ice_Dog
 */
@Service("StallService")
public class StallServiceImpl implements StallService {
    private static final Logger LOGGER = Logger.getLogger(StallServiceImpl.class);
    private static final String IMAGE_PATH = "upload/image/stall/";
    @Resource
    private StallMapper stallMapper;
    @Resource
    private DiningHallMapper diningHallMapper;
    @Resource
    private FoodCategoryMapper foodCategoryMapper;
    @Resource
    private FoodMapper foodMapper;

    @Override
    public Response list(Integer diningHallId) throws Throwable {
        //判断传入的参数非法 返回 3
        if (diningHallId == null) {
            return new Response(3);
        }
        Stall stall = new Stall();
        stall.setDiningHallId(diningHallId);
        List<Map<String, Object>> stalls = stallMapper.list(stall);
        int totalCount = stallMapper.count(stall);

        for (Map<String, Object> stallMap : stalls) {
            int count = 0;
            //获取该商店的所有菜品
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setStallId((String) stallMap.get("id"));
            List<Map<String, Object>> foodCategorys = foodCategoryMapper.listById(foodCategory);
            for (Map<String, Object> category : foodCategorys) {
                Food food = new Food();
                food.setCategoryId((Integer) category.get("id"));
                food.setIsDeleted(false);
                List<Map<String, Object>> foods = foodMapper.list(food, 0, 999);
                for (Map<String, Object> foodMap : foods) {
                    count += (int) foodMap.get("saleCount");
                }
            }
            stallMap.put("saleCount", count);
            stallMap.remove("salt");
            stallMap.remove("updateUser");
            stallMap.remove("pwdHash");
            stallMap.remove("insertTime");
            stallMap.remove("insertUser");

            // 格式化日期时间
            stallMap.put("updateTime", ((Timestamp) stallMap.get("updateTime")).getTime());
        }
        return new Response(0).add("info", stalls).add("totalCount", totalCount);
    }

    @Override
    public Response getById(String id) throws Throwable {
        //判断传入的参数非法 返回 3
        if (id == null) {
            return new Response(3);
        }
        Stall stall = new Stall();
        stall.setIsDeleted(false);
        stall.setId(id);
        List<Map<String, Object>> stalls = stallMapper.list(stall);
        Map<String, Object> s;
        //查询到的数据为空 返回 4
        if (!stalls.isEmpty()) {
            s = stalls.get(0);
        } else {
            return new Response(4);
        }
        s.remove("salt");
        s.remove("updateTime");
        s.remove("updateUser");
        s.remove("pwdHash");
        s.remove("insertTime");
        s.remove("insertUser");
        return new Response(0).add("info", s);
    }

    @Override
    public Response add(Part part, String name, String username, String password, Integer
            diningHallId, String uid) throws Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (part == null || name == null || username == null || password == null || diningHallId
                == null || uid == null) {
            return new Response(3);
        }

        //查看是否存在食堂 不存在 返回 4
        DiningHall diningHall = new DiningHall();
        diningHall.setId(diningHallId);
        diningHall.setIsDeleted(false);
        List<Map<String, Object>> diningHalls = diningHallMapper.list(diningHall);
        if (diningHalls.size() == 0) {
            return new Response(4);
        }
        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        Stall stall = new Stall();
        stall.setImageUrl(fileName);
        stall.setUsername(username);
        stall.setName(name);

        stall.setDiningHallId(diningHallId);
        Password realPassword = PasswordUtils.newHash(password);
        stall.setSalt(realPassword.getSalt());
        stall.setPwdHash(realPassword.getHash());
        stall.setInsertUser(uid);
        stall.setUpdateUser(uid);
        int rows = stallMapper.insert(stall);
        if (rows == 0) {
            return new Response(1);
        }
        //添加商户成功
        LOGGER.info(uid + "添加商户" + stall);
        return new Response(0).add("info", fileName);
    }

    @Override
    public Response deleteById(List<String> idArr, String uid) throws Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (idArr == null || uid == null) {
            return new Response(3);
        }
        String[] arr = new String[idArr.size()];
        for (int i = 0; i < idArr.size(); i++) {
            arr[i] = idArr.get(i);
        }
        stallMapper.deleteById(arr, uid);
        LOGGER.info(uid + "刪除商戶");
        return new Response(0);
    }

    @Override
    public Response edit(String id, String name, String username, String password, String phone,
            String notify, String uid, UserRoleEnum role) throws Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (name == null || id == null || uid == null || role == null) {
            return new Response(3);
        }

        Stall stall = new Stall();
        switch (role) {
            case OPERATOR:
            case OPERATOR_ADMIN:
                if (username == null) {
                    return new Response(3);
                }
                stall.setUsername(username);
                if (password != null && !"".equals(password)) {
                    Password realPassword = PasswordUtils.newHash(password);
                    stall.setSalt(realPassword.getSalt());
                    stall.setPwdHash(realPassword.getHash());
                }
                break;
            case STALL:
                if (!id.equals(uid)) {
                    return new Response(3);
                }
                if (phone == null || notify == null) {
                    return new Response(3);
                }
                stall.setPhone(phone);
                stall.setIntroduction(notify);
                break;
            default:
                return new Response(3);
        }

        stall.setId(id);
        stall.setName(name);
        stall.setUpdateUser(uid);
        stall.setIsDeleted(false);
        stallMapper.update(stall);
        LOGGER.info(uid + "修改商戶" + stall);
        return new Response(0);
    }

    @Override
    public Response changeImage(String id, Part part, String uid) throws Throwable {
        // 判断传入的参数是否为空？若为空 返回 3
        if (part == null || id == null || uid == null) {
            return new Response(3);
        }
        //权限验证 权限验证不通过返回 3
        Stall stall = new Stall();
        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        //把文件名传入数据库
        stall.setId(id);
        stall.setImageUrl(fileName);
        stall.setUpdateUser(uid);
        int rows = stallMapper.update(stall);
        if (rows == 0) {
            return new Response(1);
        }
        LOGGER.info(uid + "修改了图片");
        return new Response(0).add("info", fileName);
    }

    @Override
    public Response saveWeight(List<String> idList, Integer diningHallId, String uid) throws
            Throwable {
        if (idList == null || diningHallId == null || uid == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        // 获取食堂下的全部商户 ID
        int size = idList.size();
        Set<Object> foundIdSet = new HashSet<>(size, 1);
        Stall condition = new Stall();
        condition.setDiningHallId(diningHallId);
        List<Map<String, Object>> foundStallList = stallMapper.list(condition);
        for (Map<String, Object> foundStall : foundStallList) {
            foundIdSet.add(foundStall.get("id"));
        }

        // 遍历 ID List，计算各 ID 对应的权重值
        List<Stall> stallList = new ArrayList<>(size);
        for (int i = 0, weight = size - 1; i < idList.size(); i++, weight--) {
            // 商户 ID 为空或不属于该食堂时返回
            String id = idList.get(i);
            if (id == null || !foundIdSet.contains(id)) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }

            Stall stall = new Stall();
            stall.setId(id);
            stall.setWeight(weight);
            stall.setUpdateUser(uid);
            stallList.add(stall);
        }

        // 更新数据库
        for (Stall stall : stallList) {
            stallMapper.update(stall);
        }

        return new Response(0);
    }

    @Override
    public Response changeStatus(String id, Boolean isOpen, String uid) throws Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (id == null || isOpen == null || uid == null) {
            LOGGER.error("参数为空");
            return new Response(3);
        }
        //权限验证 权限验证不通过返回 3
        if (!id.equals(uid)) {
            return new Response(3);
        }
        Stall stall = new Stall();
        stall.setId(id);
        stall.setIsOpen(isOpen);
        stall.setUpdateUser(uid);
        stallMapper.update(stall);
        return new Response(0);
    }

    @Override
    public int batchOpenStall() throws Throwable {
        return batchToggleOpenState(true);
    }

    @Override
    public int batchCloseStall() throws Throwable {
        return batchToggleOpenState(false);
    }

    /**
     * 批量切换开店状态
     *
     * @param isOpen true=开店，false=关店
     * @return 影响的商户数
     * @throws Throwable 发生异常时抛出
     */
    private int batchToggleOpenState(boolean isOpen) throws Throwable {
        Stall condition = new Stall();
        condition.setIsOpen(isOpen);
        return stallMapper.update(condition);
    }
}
