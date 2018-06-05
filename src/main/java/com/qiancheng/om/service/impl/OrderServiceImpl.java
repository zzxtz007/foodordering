package com.qiancheng.om.service.impl;

import com.google.gson.Gson;
import com.qiancheng.om.common.utils.*;
import com.qiancheng.om.dao.*;
import com.qiancheng.om.model.*;
import com.qiancheng.om.service.OrderService;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author XLY
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    private static final String NEW_REFUND_APPLICATION = "newRefundApplication";
    private static final String NEW_ORDER = "newOrder";
    private static final int ONE_MINUTE = 60 * 1000;
    private static final int TWO_MINUTES = 2 * ONE_MINUTE;
    private static final int FIVE_MINUTES = 5 * ONE_MINUTE;
    private static final int UNPAID = 0;
    private static final int WAITING_FOR_ORDERS = 1;
    private static final int RECEIVED_ORDERS = 2;
    private static final int REFUSE_ORDER = 3;
    private static final int REQUEST_A_REFUND = 4;
    private static final int REFUNDED = 5;
    private static final int REFUSE_TO_REFUND = 6;
    private static final int ORDER_CANCELED = 7;
    private static final int REFUND_FAILED = 8;
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
    public static final Gson GSON = new Gson();

    private static ExecutorService poll = Executors.newFixedThreadPool(2000);

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderedFoodMapper orderedFoodMapper;
    @Resource
    private ConsumerMapper consumerMapper;
    @Resource
    private FoodMapper foodMapper;
    @Resource
    private StallMapper stallMapper;
    @Resource
    private DiningHallMapper diningHallMapper;
    @Resource
    private FastDateFormat timeFormat;
    @Resource
    private FastDateFormat dateFormat;
    @Resource
    private FastDateFormat dateTimeFormat;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response insertOrder(final String consumerId, String stallId, String phone, Date
            appointmentTime, String remark, Boolean isPack, Map<Integer, Integer> foodCount)
            throws Throwable {
        // 假设有空值，返回状态码 3
        if (stallId == null || phone == null || remark == null || isPack == null || foodCount ==
                null || consumerId == null) {
            return new Response(3);
        }

        // 初始化总价
        BigDecimal totalPrice = BigDecimal.ZERO;


        Stall stall = new Stall();
        stall.setId(stallId);

        List<Map<String, Object>> stallList = stallMapper.list(stall);

        // 判断商户是否存在
        if (stallList.size() != 1) {
            return new Response(3);
        }

        Map<String, Object> stallMap = stallList.get(0);
        if (!(Boolean) stallMap.get("isOpen")) {
            return new Response(4);
        }

        DiningHall diningHall = new DiningHall();
        diningHall.setId((Integer) stallList.get(0).get("diningHallId"));


        // 取餐时间是否是当前时间后或食堂关店前
        Long startTime = ((Time) diningHallMapper.list(diningHall).get(0).get("startTime"))
                .getTime();
        Long endTime = ((Time) diningHallMapper.list(diningHall).get(0).get("endTime")).getTime();
        Long time;
        Timestamp setTime = null;

        if (null != appointmentTime) {
            // 获取时间
            time = Time.valueOf(timeFormat.format(appointmentTime)).getTime();
            if (time < startTime || time > endTime) {
                return new Response(6);
            }
            setTime = new Timestamp(appointmentTime.getTime());
        }

        // 是否会员标记
        Boolean isMember = checkIsMember(consumerId);

        // 通过 ID 匹配菜品 假设有已下架菜品，则返回
        List<Map<String, Object>> foodList = new ArrayList<>();

        Food food = new Food();

        Set<Integer> keys = foodCount.keySet();

        // 判断 map 内是否存在空键、空值
        for (Integer foodId : keys) {
            if (foodCount.get(foodId) == null) {
                return new Response(3);
            }

            food.setId(foodId);
            food.setIsDeleted(false);

            if (foodMapper.list(food, null, null).size() != 1) {
                return new Response(3);
            }

            Map<String, Object> singleFood = foodMapper.list(food, null, null).get(0);
            if (singleFood.get("isOnSale").equals(false)) {
                return new Response(5);
            }
            foodList.add(singleFood);
        }

        // 获取在表内不存在的订单编号
        final String orderId = getOrderId();

        // 生成 order 对象
        Order order = new Order();
        order.setId(orderId);
        order.setConsumerId(consumerId);
        order.setStallId(stallId);
        order.setPhone(phone);

        // 取餐号
        int appointmentId = AppointmentIdControl.getAppointmentId();

        order.setAppointmentId(appointmentId);
        order.setRemark(remark);
        order.setAppointmentTime(setTime);
        order.setIsPack(isPack);
        order.setStatus(UNPAID);
        order.setUpdateUser(consumerId);
        order.setInsertUser(consumerId);

        Consumer consumer = new Consumer();
        consumer.setId(consumerId);
        List<Map<String, Object>> consumerList = consumerMapper.list(consumer);

        if (consumerList.isEmpty()) {
            return new Response(3);
        }

        Map<String, Object> consumerMap = consumerList.get(0);

        if (consumerMap.get("phone") == null) {
            consumer.setPhone(phone);
            consumerMapper.updateById(consumer);
        }

        // 添加订单
        orderMapper.insert(order);

        // 循环生成 ordered food 对象 并且添加
        for (Map<String, Object> singleFood : foodList) {
            OrderedFood orderedFood = new OrderedFood();
            orderedFood.setIsMember(isMember);
            orderedFood.setOrderId(orderId);
            orderedFood.setFoodId((Integer) singleFood.get("id"));
            orderedFood.setInsertUser(consumerId);
            orderedFood.setUpdateUser(consumerId);

            Integer id = (Integer) singleFood.get("id");

            Integer count = foodCount.get(id);

            Integer remaining = (Integer) singleFood.get("memberPriceRemaining");
            Boolean isMemberFood = true;
            if (remaining == null || remaining == 0 || !isMember) {
                isMemberFood = false;
            }

            orderedFood.setIsMember(isMemberFood);

            // 订单主题逻辑业务
            BigDecimal packFee = BigDecimal.ZERO;
            if (isPack) {
                totalPrice = totalPrice.add(((BigDecimal) singleFood.get("packFee")).multiply
                        (BigDecimal.valueOf(count)));
                packFee = (BigDecimal) singleFood.get("packFee");
            }
            orderedFood.setPackFee(packFee);
            if (isMemberFood) {

                // 数量不足
                if (remaining < count) {
                    orderedFood.setMemberPrice((BigDecimal) singleFood.get("memberPrice"));
                    orderedFood.setMemberPriceCost((BigDecimal) singleFood.get("memberPriceCost"));
                    orderedFood.setCount(remaining);
                    // 将最高价格添加进 BigDecimal 内
                    totalPrice = totalPrice.add(((BigDecimal) singleFood.get("memberPrice"))
                            .multiply(BigDecimal.valueOf(remaining)));

                    // 剩余不是会员订单数量
                    OrderedFood otherOrderedFood = new OrderedFood();
                    otherOrderedFood.setOrderId(orderId);
                    otherOrderedFood.setFoodId(orderedFood.getFoodId());
                    otherOrderedFood.setIsMember(false);
                    otherOrderedFood.setStandardPrice((BigDecimal) singleFood.get("standardPrice"));
                    otherOrderedFood.setCount(count - remaining);
                    otherOrderedFood.setInsertUser(consumerId);
                    otherOrderedFood.setUpdateUser(consumerId);
                    otherOrderedFood.setPackFee(packFee);

                    // 将最高价格添加进 BigDecimal 内
                    totalPrice = totalPrice.add(((BigDecimal) singleFood.get("standardPrice"))
                            .multiply(BigDecimal.valueOf(count - remaining)));

                    // 添加菜品
                    orderedFoodMapper.insert(otherOrderedFood);
                }
                // 数量充足
                else {
                    orderedFood.setMemberPrice((BigDecimal) singleFood.get("memberPrice"));
                    orderedFood.setMemberPriceCost((BigDecimal) singleFood.get("memberPriceCost"));
                    orderedFood.setCount(count);
                    // 将最高价格添加进 BigDecimal 内
                    totalPrice = totalPrice.add(((BigDecimal) singleFood.get("memberPrice"))
                            .multiply(BigDecimal.valueOf(count)));
                }
            }
            // 不是会员菜品价格和菜品数量
            else {
                orderedFood.setStandardPrice((BigDecimal) singleFood.get("standardPrice"));
                orderedFood.setCount(count);
                totalPrice = totalPrice.add(((BigDecimal) singleFood.get("standardPrice"))
                        .multiply(BigDecimal.valueOf(count)));
            }

            // 添加菜品
            orderedFoodMapper.insert(orderedFood);
            food = foodMapToFoodModel(singleFood, consumerId, count);

            foodMapper.updateById(food);
        }


        totalPrice = totalPrice.multiply(BigDecimal.valueOf(100));

        Map<String, String> message = new HashMap<>();
        message.put("orderId", orderId);
        message.put("spbillCreateIp", "0.0.0.0");
        message.put("openId", consumerId);
        message.put("totalFee", String.valueOf(totalPrice.intValue()));

        Map<String, String> map = WeChatPayment.outgoingPaymentData(message);
        if (map == null || map.get("package") == null) {
            throw new IllegalArgumentException();
        }
        map.remove("appId");
        map.put("orderId", orderId);
        poll.execute(new CheckOrderStatus(orderId, consumerId, orderMapper, this,
                UNPAID));


        return new Response(0).add("paymentInfo", map);
    }

    @Override
    public Response getById(String id) throws Throwable {
        Map<String, Object> order = new TreeMap<>();
        order.put("id", id);
        List<Map<String, Object>> orderArr = orderMapper.list(order);
        if (orderArr.isEmpty()) {
            return new Response(0);
        }
        Map<String, Object> singleOrder = orderArr.get(0);

        Map<String, Object> singleOrderFood = getOrderInfo(id);
        singleOrder = changeDataType(singleOrder);

        if ((Integer) singleOrderFood.get("errCode") == 3) {
            return new Response(3);
        }
        Stall stall = new Stall();
        stall.setId(String.valueOf(singleOrder.get("stallId")));
        singleOrder.put("orderFood", singleOrderFood);
        singleOrder.put("stall", getStallInfo(stall));
        return new Response(0).add("info", singleOrder);
    }

    @Override
    public Response listByStatus(String id, String uid, List<Integer> statusArr, Integer
            startPage, Integer rowSize, Date startDate, Date endDate) throws Throwable {
        if (id == null || (startPage == null ^ rowSize == null) || statusArr == null ||
                (startDate == null ^ endDate == null)) {
            return new Response(3);
        }

        if (!id.equals(uid)) {
            return new Response(3);
        }

        Integer startIndex = startPage == null ? null : (startPage - 1) * rowSize;

        // 获取指定范围内的指定订单状态的订单
        Map<String, Object> condition = new HashMap<>();
        condition.put("stallId", id);
        condition.put("startIndex", startIndex);
        condition.put("status", statusArr);
        condition.put("count", rowSize);
        condition.put("startDate", startDate);
        condition.put("endDate", endDate);
        List<Map<String, Object>> orderList = orderMapper.list(condition);
        if (orderList.size() == 0) {
            return new Response(0).add("info", "[]").add("totalCount", 0);
        }
        // 获取指定订单状态的数量
        Integer totalCount = orderMapper.count(condition);


        for (Map<String, Object> singleOrder : orderList) {
            String orderId = String.valueOf(singleOrder.get("id"));
            singleOrder = changeDataType(singleOrder);
            Map<String, Object> singleOrderInfo = getOrderInfo(orderId);

            if ((Integer) singleOrderInfo.get("errCode") == 3) {
                return new Response(3);
            }

            singleOrder.put("orderFood", singleOrderInfo);
        }

        return new Response(0).add("info", orderList).add("totalCount", totalCount);
    }

    @Override
    public Response listById(String id, Integer startPage, Integer rowSize) throws Throwable {
	        if (startPage == null ^ rowSize == null) {
            return new Response(3);
        }

        Integer startIndex = startPage == null ? null : (startPage - 1) * rowSize;

        Map<String, Object> condition = new HashMap<>();
        condition.put("consumerId", id);
        condition.put("startIndex", startIndex);
        condition.put("count", rowSize);

        List<Map<String, Object>> orderList = orderMapper.list(condition);


        if (orderList.isEmpty()) {
            return new Response(0).add("info", "[]").add("totalCount", "0");
        }

        Map<String, Object> getTotalCountCondition = new HashMap<>();
        getTotalCountCondition.put("consumerId", id);
        Integer totalCount = orderMapper.count(getTotalCountCondition);

        for (Map<String, Object> order : orderList) {
            String orderId = String.valueOf(order.get("id"));
            order = changeDataType(order);

            Map<String, Object> singleOrderInfo = getOrderInfo(orderId);

            if ((Integer) singleOrderInfo.get("errCode") == 3) {
                return new Response(3);
            }
            Stall stall = new Stall();
            stall.setId(String.valueOf(order.get("stallId")));

            order.put("stall", getStallInfo(stall));
            order.put("orderFood", singleOrderInfo);
        }

        return new Response(0).add("info", orderList).add("totalCount", totalCount);
    }

    @Override
    public Response payOrder(String orderId, String consumerId) throws Throwable {
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            LOGGER.debug("订单不存在");
            return new Response(3);aaa
        }
        Map<String, Object> order = orderArr.get(0);

        Map<String, String> resultMap = WeChatPayment.getPaymentStatus(orderId);

        if ("SUCCESS".equals(resultMap.get("trade_state"))) {

            if (!order.get("status").equals(UNPAID)) {
                // 订单已经支付
                return new Response(4);
            }

            // 1 待接单
            Order updateOrder = new Order();
            updateOrder.setId(orderId);
            updateOrder.setStatus(WAITING_FOR_ORDERS);
            updateOrder.setUpdateUser(consumerId);
            updateOrder.setPayTime(new Timestamp(System.currentTimeMillis()));
            orderMapper.updateById(updateOrder);

            push(NEW_ORDER, orderId, (String) order.get("stallId"));
            poll.execute(new CheckOrderStatus(orderId, consumerId, orderMapper, this,
                    WAITING_FOR_ORDERS));

            return new Response(0);
        }
        LOGGER.debug("订单未支付成功或已支付");
        return new Response(3);
    }

    @Override
    public Response payOrderAgain(String orderId, String prepayId, String consumerId) throws
            Throwable {
        if (prepayId == null || orderId == null || consumerId == null) {
            return new Response(3);
        }

        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            LOGGER.debug("订单不存在");
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);

        if (orderMap.get("status").equals(ORDER_CANCELED)) {
            return new Response(5);
        }

        Map<String, String> resultMap = WeChatPayment.getPaymentStatus(orderId);

        if (resultMap.get("trade_state").equals("SUCCESS")) {
            // 调用方法
            updateOrderToWaiting(orderId, consumerId, orderMap);
            return new Response(4);
        }

        Map<String, String> map = WeChatPayment.getPayment(prepayId);
        if (map == null || map.get("package") == null) {
            throw new IllegalArgumentException();
        }

        return new Response(0).add("info", map);
    }

    /**
     * 将订单状态修改成待接单
     *
     * @param orderId    订单 id
     * @param consumerId 用户 id
     * @param orderMap   包含订单信息的 map 集合
     */
    private void updateOrderToWaiting(String orderId, String consumerId, Map<String, Object>
            orderMap) {
        // 将订单状态
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(WAITING_FOR_ORDERS);
        order.setUpdateUser(consumerId);
        order.setPayTime(new Timestamp(System.currentTimeMillis()));
        orderMapper.updateById(order);

        push(NEW_ORDER, orderId, (String) orderMap.get("stallId"));
    }

    /**
     * 极光推送
     *
     * @param type    推送的类型
     * @param orderId 订单 id
     * @param stallId 商户 id
     */
    private void push(String type, String orderId, String stallId) {
        try {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("type", type);
            dataMap.put("orderId", orderId);
            JPush.push(stallId, dataMap);
        } catch (Exception e) {
            LOGGER.error("极光推送:" + e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response consumerApplicationRefund(String orderId, String consumerId) throws Throwable {
        if (orderId == null) {
            LOGGER.debug("订单编号为空");
            return new Response(3);
        }
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);
        Timestamp now = new Timestamp(System.currentTimeMillis() - TWO_MINUTES);

        // 订单被拒后返回状态码 5
        if (orderMap.get("status").equals(REFUSE_ORDER)) {
            return new Response(5);
        }

        if (!(orderMap.get("insertUser").equals(consumerId) || !orderMap.get("status").equals
                (RECEIVED_ORDERS))) {
            return new Response(3);
        }

        if (((Timestamp) orderMap.get("payTime")).before(now)) {
            return new Response(4);
        }
        // 4 申请退款
        Order order = new Order();
        order.setStatus(REQUEST_A_REFUND);
        order.setId(orderId);
        order.setUpdateUser(consumerId);
        order.setRefundApplicationTime(new Timestamp(System.currentTimeMillis()));

        if (updateOrder(order) == 1) {
            push(NEW_REFUND_APPLICATION, orderId, (String) orderMap.get("stallId"));
            return new Response(0);
        }
        return new Response(3);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response confirmRefund(String orderId, String consumerId) throws Throwable {
        if (orderId == null) {
            LOGGER.debug("订单编号为空");
            return new Response(3);
        }

        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        Map<String, Object> orderMap = orderArr.get(0);
        if (!(orderMap.get("insertUser").equals(consumerId) || !orderMap.get("status").equals
                (REFUND_FAILED))) {
            return new Response(3);
        }
        // 确认退款
        Order order = new Order();
        order.setStatus(REFUNDED);
        order.setId(orderId);
        order.setUpdateUser(consumerId);
        order.setRefundApplicationTime(new Timestamp(System.currentTimeMillis()));
        if (updateOrder(order) == 1) {
            returnDiscountFood(orderId);
            return new Response(0);
        }
        return new Response(3);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response refund(String orderId, String stallId, String uid) throws Throwable {
        if (!stallId.equals(uid)) {
            return new Response(3);
        }
        if (orderId == null) {
            LOGGER.debug("订单编号为空");
            return new Response(3);
        }
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);
        if (!(orderMap.get("stallId").equals(uid) || orderMap.get("status").equals
                (RECEIVED_ORDERS) || orderMap.get("status").equals(REQUEST_A_REFUND) || orderMap
                .get("status").equals(REQUEST_A_REFUND))) {
            return new Response(3);
        }

        // 5 同意退款
        Order order = new Order();
        order.setStatus(REFUNDED);
        order.setId(orderId);
        order.setUpdateUser(uid);
        order.setRefundEndTime(new Timestamp(System.currentTimeMillis()));

        if (updateOrder(order) == 1) {
            OrderedFood orderedFood = new OrderedFood();
            orderedFood.setOrderId(orderId);

            Map<String, String> resultXml = WeChatPayment.refundOrder(orderId, getTotalFee
                    (orderedFood));
            if (!(resultXml.get("result_code").equals("SUCCESS"))) {
                throw new IllegalArgumentException();
            }

            if ("基本账户余额不足，请充值后重新发起".equals(resultXml.get("err_code_des"))) {
                return new Response(4);
            }
            return new Response(0);
        }
        return new Response(3);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response refuseToRefund(String orderId, String stallId, String uid) throws Throwable {
        if (!stallId.equals(uid)) {
            return new Response(3);
        }
        if (orderId == null) {
            LOGGER.debug("订单编号为空");
            return new Response(3);
        }
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);
        if (!orderMap.get("stallId").equals(uid) || !orderMap.get("status").equals
                (REQUEST_A_REFUND)) {
            return new Response(3);
        }
        // 6 拒绝退款
        Order order = new Order();
        order.setStatus(REFUSE_TO_REFUND);
        order.setId(orderId);
        order.setUpdateUser(uid);
        order.setRefundEndTime(new Timestamp(System.currentTimeMillis()));

        if (updateOrder(order) == 1) {
            return new Response(0);
        }
        return new Response(3);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response cancelOrder(String orderId, String consumerId) throws Throwable {
        if (orderId == null) {
            LOGGER.debug("订单编号为空");
            return new Response(3);
        }
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);


        if ((Integer) orderMap.get("status") == ORDER_CANCELED) {

            // 4 表示订单已取消
            return new Response(4);
        }

        Map<String, String> resultMap = WeChatPayment.getPaymentStatus(orderId);

        //  假设订单已经支付
        if (resultMap.get("trade_state").equals("SUCCESS")) {
            // 调用方法 并且返回 5 代表订单已经支付
            updateOrderToWaiting(orderId, consumerId, orderMap);
            return new Response(5);
        }
        if (!orderMap.get("insertUser").equals(consumerId) || !orderMap.get("status").equals
                (UNPAID)) {
            return new Response(3);
        }

        // 7 取消订单
        Order order = new Order();
        order.setStatus(ORDER_CANCELED);
        order.setId(orderId);
        order.setUpdateUser(consumerId);

        if (updateOrder(order) == 1) {
            returnDiscountFood(orderId);
            return new Response(0);
        }
        return new Response(3);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response acceptOrder(String orderId, String stallId, String uid) throws Throwable {
        if (orderId == null || !stallId.equals(uid)) {
            return new Response(3);
        }
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);
        if (orderMap.get("status").equals(REFUSE_ORDER)) {
            return new Response(4);
        }
        if (orderMap.get("status").equals(REQUEST_A_REFUND)) {
            return new Response(5);
        }
        if (!orderMap.get("stallId").equals(uid) || !orderMap.get("status").equals
                (WAITING_FOR_ORDERS)) {
            return new Response(3);
        }

        // 接受订单
        Order order = new Order();
        order.setStatus(RECEIVED_ORDERS);
        order.setId(orderId);
        order.setUpdateUser(uid);

        if (updateOrder(order) == 1) {
            return new Response(0);
        }
        return new Response(3);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response refuseOrder(String orderId, String stallId, String uid) throws Throwable {
        if (orderId == null) {
            LOGGER.debug("订单编号为空");
            return new Response(3);
        }
        Map<String, Object> selectOrder = new HashMap<>();
        selectOrder.put("id", orderId);
        List<Map<String, Object>> orderArr = orderMapper.list(selectOrder);
        if (orderArr.size() == 0) {
            return new Response(3);
        }
        Map<String, Object> orderMap = orderArr.get(0);
        if (orderMap.get("status").equals(REFUSE_ORDER)) {
            return new Response(4);
        }
        if (orderMap.get("status").equals(REQUEST_A_REFUND)) {
            return new Response(5);
        }
        if (!orderMap.get("stallId").equals(uid) || !orderMap.get("status").equals
                (WAITING_FOR_ORDERS)) {
            return new Response(3);
        }
        OrderedFood orderedFood = new OrderedFood();
        orderedFood.setOrderId(orderId);

        Map<String, String> resultXml = WeChatPayment.refundOrder(orderId, String.valueOf
                (getTotalFee(orderedFood)));
        if (!(resultXml.get("result_code").equals("SUCCESS"))) {
            return new Response(3);
        }
        // 3 拒绝订单
        Order order = new Order();
        order.setStatus(REFUSE_ORDER);
        order.setId(orderId);
        order.setUpdateUser(uid);

        if (updateOrder(order) == 1) {
            returnDiscountFood(orderId);
            return new Response(0);
        }
        return new Response(3);
    }

    /**
     * 退回优惠菜品数量
     *
     * @param orderId 订单id
     */
    private void returnDiscountFood(String orderId) {
        OrderedFood orderedFood = new OrderedFood();
        orderedFood.setOrderId(orderId);
        orderedFood.setIsIsDeleted(false);
        List<Map<String, Object>> orderFoodArr = orderedFoodMapper.list(orderedFood);
        // 当就获取的订单菜品不为空时
        if (!orderFoodArr.isEmpty()) {
            // 遍历订单菜品
            for (Map<String, Object> orderFoodMap : orderFoodArr) {
                Food food = new Food();
                food.setId((Integer) orderFoodMap.get("id"));
                List<Map<String, Object>> foodArr = foodMapper.list(food, null, null);
                for (Map<String, Object> foodMap : foodArr) {
                    food.setSaleCount((Integer) foodMap.get("saleCount") - (Integer) orderFoodMap
                            .get("count"));
                    // 当订单菜品为优惠菜品时
                    if ((Boolean) orderFoodMap.get("isMember")) {
                        food.setMemberPriceRemaining((Integer) foodMap.get
                                ("memberPriceRemaining") + (Integer) orderFoodMap.get("count"));
                    }
                    foodMapper.updateById(food);
                }
            }
        }


    }

    /**
     * 将 food 信息的集合的值赋给 food 实体类
     *
     * @param foodMap    包含 food 信息的集合
     * @param consumerId 用户编号
     * @param count      购买数量
     * @return 包含 food 信息的实体类
     */
    private Food foodMapToFoodModel(Map<String, Object> foodMap, String consumerId, Integer count) {
        Food food = new Food();
        food.setId((Integer) foodMap.get("id"));
        food.setSaleCount((Integer) foodMap.get("saleCount") + count);
        if (foodMap.get("memberPriceRemaining") != null) {
            Integer memberPriceRemaining = (Integer) foodMap.get("memberPriceRemaining") - count;
            if (memberPriceRemaining < 0) {
                memberPriceRemaining = 0;
            }
            food.setMemberPriceRemaining(memberPriceRemaining);
        }

        food.setUpdateUser(consumerId);
        return food;
    }

    /**
     * 转换时间类型
     *
     * @param order 包含 order 信息的 map 集合
     * @return 转换后的 map 集合
     */
    private Map<String, Object> changeDataType(Map<String, Object> order) {
        // 假如有订单时间,将 Long 类型转化成 Date 类型
        if (order.get("orderTime") != null) {
            order.put("orderTime", ((Date) order.get("orderTime")).getTime());
        }
        // 假如有支付时间,将 Long 类型转化成 Date 类型
        if (order.get("payTime") != null) {
            order.put("payTime", ((Date) order.get("payTime")).getTime());
            // 判断是否在顾客允许的退款时间内,如果是 那么将剩余时间出入 Map 集合内
            Long payTime = (Long) order.get("payTime");
            Long now = new Date().getTime();
            Long leftTime = now - payTime;
            // 假如时间在2分钟内,则将剩余时间传入 map 内
            if (leftTime < TWO_MINUTES) {
                order.put("leftTime", (TWO_MINUTES - leftTime) / 1000);
            }
            // 假如时间在5分钟内,则将剩余刷新时间传入 map 内
            if (leftTime < FIVE_MINUTES) {
                order.put("refreshTime", (FIVE_MINUTES - leftTime) / 1000);
            }
        }
        // 假如有取餐时间,将 Long 类型转化成 Date 类型
        if (order.get("appointmentTime") != null) {
            order.put("appointmentTime", ((Date) order.get("appointmentTime")).getTime());
        }
        // 假如有退款时间,将 Long 类型转化成 Date 类型
        if (order.get("refundApplicationTime") != null) {
            order.put("refundApplicationTime", ((Date) order.get
                    ("refundApplicationTime")).getTime());
        }
        // 假如有退款结束时间,将 Long 类型转化成 Date 类型
        if (order.get("refundEndTime") != null) {
            order.put("refundEndTime", ((Date) order.get("refundEndTime")).getTime());
        }
        return order;
    }

    /**
     * 获取商户信息
     *
     * @return 包含商户信息的 map 集合
     */
    private Map<String, Object> getStallInfo(Stall stall) {
        Map<String, Object> stallInfo = stallMapper.list(stall).get(0);
        stallInfo.remove("pwdHash");
        stallInfo.remove("salt");
        stallInfo.remove("diningHallId");
        stallInfo.remove("weight");
        stallInfo.remove("updateUser");
        stallInfo.remove("updateTime");
        stallInfo.remove("insertUser");
        stallInfo.remove("insertTime");
        return stallInfo;
    }

    /**
     * 获取订单 ID
     *
     * @return 字符串
     */
    private String getOrderId() {
        String orderId = UUIDToString();
        while (true) {
            Map<String, Object> selectOrder = new HashMap<>();
            selectOrder.put("id", orderId);
            if (orderMapper.list(selectOrder).size() == 1) {
                orderId = UUIDToString();
                continue;
            }
            return orderId;
        }
    }

    /**
     * 将 UUID 剔除 - 拼成字符串
     *
     * @return 修改后的 UUID 的字符串
     */
    private String UUIDToString() {
        // 生成订单 UUID
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();

        char[] idArr = (uuid.toString()).toCharArray();
        for (char c : idArr) {
            if (c != '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取总计费用
     *
     * @param orderedFood 订单菜品编号
     * @return 字符串类型的总计费用
     */
    private String getTotalFee(OrderedFood orderedFood) {
        List<Map<String, Object>> foodArr = orderedFoodMapper.list(orderedFood);
        BigDecimal total = BigDecimal.ZERO;

        for (Map<String, Object> food : foodArr) {
            if (food.get("isMember").equals(true)) {
                total = total.add(((BigDecimal) food.get("memberPrice")).multiply(
                        new BigDecimal((Integer) food.get("count"))));
                total = total.add(((BigDecimal) food.get("packFee")).multiply(new
                        BigDecimal((Integer) food.get("count"))));
            } else {
                total = total.add(((BigDecimal) food.get("standardPrice")).multiply
                        (new BigDecimal((Integer) food.get("count"))));
                total = total.add(((BigDecimal) food.get("packFee")).multiply(new
                        BigDecimal((Integer) food.get("count"))));
            }
        }

        total = total.multiply(BigDecimal.valueOf(100));
        return String.valueOf(total.intValue());
    }

    /**
     * 用户会员检测
     *
     * @param id 用户 ID
     * @return 是否为会员
     */
    private Boolean checkIsMember(String id) {
        Consumer consumer = new Consumer();
        consumer.setId(id);
        List<Map<String, Object>> list = consumerMapper.list(consumer);
        return (Boolean) list.get(0).get("isMember");
    }

    /**
     * 将获取单个订单的复用代码提取出来
     *
     * @param id 订单 ID
     * @return 包含信息的Map
     */
    private Map<String, Object> getOrderInfo(String id) {

        Map<String, Object> singleOrder = new HashMap<>();

        if (id == null) {
            singleOrder.put("errCode", 3);
            return singleOrder;
        }

        OrderedFood orderedFood = new OrderedFood();
        orderedFood.setOrderId(id);
        List<Map<String, Object>> orderFoodList = orderedFoodMapper.list(orderedFood);

        for (Map<String, Object> singleOrderFood : orderFoodList) {
            Food food = new Food();
            food.setId((Integer) singleOrderFood.get("foodId"));
            Map<String, Object> foodInfo = foodMapper.list(food, null, null).get(0);
            singleOrderFood.put("name", foodInfo.get("name"));
        }

        singleOrder.put("food", orderFoodList);
        singleOrder.put("errCode", 0);

        return singleOrder;
    }

    private int updateOrder(Order order) {
        return orderMapper.updateById(order);
    }


}
