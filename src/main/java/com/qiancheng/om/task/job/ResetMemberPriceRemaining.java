package com.qiancheng.om.task.job;

import com.qiancheng.om.dao.FoodMapper;
import com.qiancheng.om.model.Food;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author XLY
 */
public class ResetMemberPriceRemaining {

    @Resource
    private FoodMapper foodMapper;

    @Transactional(rollbackFor = Exception.class)
    public void execute() {

        Food selectFood = new Food();
        selectFood.setIsDeleted(false);

        List<Map<String, Object>> allFood = foodMapper.list(selectFood, null, null);

        for (Map<String, Object> singleFood : allFood) {
            Food food = new Food();
            food.setId((Integer) singleFood.get("id"));
            if(singleFood.get("memberPriceLimit") == null){
                continue;
            }
            food.setMemberPriceRemaining((Integer) singleFood.get("memberPriceLimit"));
            foodMapper.updateById(food);
        }
    }

}
