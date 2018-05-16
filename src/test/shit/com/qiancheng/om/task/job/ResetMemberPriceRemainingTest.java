package shit.com.qiancheng.om.task.job;

import com.qiancheng.om.dao.FoodMapper;
import com.qiancheng.om.model.Food;
import com.qiancheng.om.service.OrderService;
import com.qiancheng.om.task.job.ResetMemberPriceRemaining;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * ResetMemberPriceRemaining Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class ResetMemberPriceRemainingTest {

    private FoodMapper foodMapper;

    @Before
    public void before() throws Exception {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring" +
                "/applicationContext.xml");
        foodMapper = (FoodMapper) context.getBean("foodMapper");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: execute()
     */
    @Test
    public void testExecute() throws Exception {
        List<Map<String, Object>> allFood = foodMapper.listAll(new Food(), null, null);
        System.out.println(allFood);

        for (Map<String, Object> singleFood : allFood) {
            Food food = new Food();
            food.setId((Integer) singleFood.get("id"));
            food.setMemberPriceRemaining((Integer) singleFood.get("memberPriceLimit"));
            foodMapper.updateById(food);
        }
    }


}
