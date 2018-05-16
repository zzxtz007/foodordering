import com.qiancheng.om.dao.FoodMapper;
import com.qiancheng.om.model.Food;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XLY
 */
public class Test {

    private List<Map<String, Object>> ListAll() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/applicationContext.xml");
        FoodMapper foodMapper = context.getBean(FoodMapper.class);
        Food food = new Food();
//        food.setCategoryId(12);
        food.setId(1);

        return foodMapper.list(food, null, null);
    }

    private  int insert(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/applicationContext.xml");
        FoodMapper foodMapper = context.getBean(FoodMapper.class);

        Food food = new Food();
        food.setCategoryId(12);
        food.setImageUrl("我他妈测试");
        food.setInsertUser("商家1");
        food.setUpdateUser("商家1");
        food.setName("蛋包饭");
        food.setIntroduction("介绍");
        food.setMemberPriceLimit(12);
        food.setMemberPriceRemaining(10);
        food.setSaleCount(123456);

        return foodMapper.insert(food);
    }

    private int ListAllCount(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/applicationContext.xml");
        FoodMapper foodMapper = context.getBean(FoodMapper.class);
        Food food = new Food();
        food.setCategoryId(4);
        return foodMapper.count(food);
    }

    private int delete(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/applicationContext.xml");
        FoodMapper foodMapper = context.getBean(FoodMapper.class);
        return foodMapper.deleteById(new int[]{2, 3, 5, 6}, "fuck");
    }

    private int update(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/applicationContext.xml");
        FoodMapper foodMapper = context.getBean(FoodMapper.class);

        Food food = new Food();
        food.setId(1);
        food.setCategoryId(16);
        food.setImageUrl("菜品图片");
        food.setInsertUser("商家3");
        food.setUpdateUser("商家3");
        food.setName("屎");
        food.setIntroduction("介绍阿三大苏打阿三");
        food.setIsOnSale(false);
        food.setMemberPriceLimit(133);
        food.setMemberPriceRemaining(102);
        food.setSaleCount(789456);

        return foodMapper.updateById(food);
    }


    public static void main(String[] args) {
        Test test = new Test();
        Map<String, Integer> map = new HashMap<>();

        map.put("test1", 12);
        map.put("test2", 124);
        System.out.println(map);
        System.out.println(test.ListAll());
    }

}
