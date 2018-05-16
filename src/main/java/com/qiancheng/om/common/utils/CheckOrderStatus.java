package com.qiancheng.om.common.utils;

import com.qiancheng.om.dao.OrderMapper;
import com.qiancheng.om.model.Order;
import com.qiancheng.om.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XLY
 */
public class CheckOrderStatus extends Thread {

    private static final Logger LOGGER = Logger.getLogger(CheckOrderStatus.class);
    private static final int ONE_MINUTE = 60 * 1000;
    private static final int FIVE_MINUTES = 5 * ONE_MINUTE;
    private static final int UNPAID = 0;
    private static final int WAITING_FOR_ORDERS = 1;
    private static final int ORDER_CANCELED = 7;
    private String orderId;
    private String consumerId;
    private OrderMapper orderMapper;
    private OrderServiceImpl orderService;
    private Integer orderStatus;

    public CheckOrderStatus(String orderId, String consumerId, OrderMapper orderMapper,
            OrderServiceImpl orderService, Integer orderStatus) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.orderStatus = orderStatus;
    }


    public void run() {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", orderId);
        try {
            sleep(FIVE_MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 当查询出对应订单时
        if (orderMapper.list(condition).size() == 1) {
            Map<String, Object> map = orderMapper.list(condition).get(0);
            String stallId = (String) map.get("stallId");

            // 根据添加线程时的订单的状态，进行不同的操作
            switch (orderStatus) {
                case UNPAID:
                    // 当订单状态为未支付

                    if ((Integer) map.get("status") == UNPAID) {
                        try {
                            LOGGER.debug("开始修改订单状态");
                            orderService.cancelOrder(orderId, consumerId);
                        } catch (Throwable throwable) {
                            LOGGER.error("5分钟后取消订单错误", throwable);
                        }
                    }
                    break;
                case WAITING_FOR_ORDERS:
                    // 当订单状态为未接单时
                    if ((Integer) map.get("status") == WAITING_FOR_ORDERS) {
                        try {
                            LOGGER.debug("开始拒绝接单");
                            orderService.refuseOrder(orderId, stallId, stallId);
                        } catch (Throwable throwable) {
                            LOGGER.error("5分钟后拒绝订单错误", throwable);
                        }
                    }
                    break;
            }
        }
    }
}

