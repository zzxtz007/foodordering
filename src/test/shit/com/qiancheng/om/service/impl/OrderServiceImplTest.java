package shit.com.qiancheng.om.service.impl;

import com.qiancheng.om.service.OrderService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * OrderServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class OrderServiceImplTest {

    private OrderService orderService;

    @Before
    public void before() throws Exception {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring" +
                "/applicationContext.xml");
        orderService = context.getBean(OrderService.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: insertOrder(String consumerId, String stallId, String phone, String
     * appointmentTimeStr, String remark, String isPackStr, String statusStr, Map<String,
     * Integer> foodCount, String isReAgreeStr)
     */
    @Test
    public void testInsertOrder() throws Exception {
//TODO: Test goes here...


//       Map<String ,String> map = new HashMap<>();
//       map.put("1", "2");
//       map.put("4", "1");
//       map.put("7", "asdsa");
//
//        System.out.println(orderService.insertOrder("openid15512312312", "s_1", "13645876734",
//                "2018-01-26 19:22:60", "垃圾", "0", "0", map, "true"));

    }

    /**
     * Method: updateOrderStatus(String statusStr, String id, String uid)
     */
    @Test
    public void testUpdateOrderStatus() throws Exception {
//TODO: Test goes here...

//        System.out.println(orderService.updateOrderStatus("4",
//                "8d9e3c86-43d9-44c2-8b24-bf3fe565be43",
//                "openid15512312312"));
    }

    /**
     * Method: updateOrderRefundTime(String statusStr, String typeStr, String orderId, String uid)
     */
    @Test
    public void testUpdateOrderRefundTime() throws Exception {
//TODO: Test goes here...
//        System.out.println(orderService.updateOrderRefundTime("6", "1",
//                "cebe6e6e-5637-4992-a515-caa781c80e68", "s_1"));
    }

    /**
     * Method: getById(String idStr)
     */
    @Test
    public void testGetById() throws Exception {
//TODO: Test goes here...
//        System.out.println(orderService.getById("e75ff38a-fa7e-418c-8307-629c211a21e1"));
    }

    /**
     * Method: listByStatus(String id, String statusStr, String startPageStr, String
     * rowSizeStr)
     */
    @Test
    public void testListByStatus() throws Exception {
//TODO: Test goes here...
//        System.out.println(orderService.listByStatus("s_1", "0", "1", "5"));
    }

    /**
     * Method: listById(String id, String startPageStr, String rowSizeStr)
     */
    @Test
    public void testListById() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: payOrder()
     */
    @Test
    public void testCheckPayType() throws Exception {
//TODO: Test goes here...
    }

}
