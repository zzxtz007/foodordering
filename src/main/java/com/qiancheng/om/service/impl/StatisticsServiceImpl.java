package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.common.utils.WeChatPayment;
import com.qiancheng.om.dao.DiningHallMapper;
import com.qiancheng.om.dao.OrderMapper;
import com.qiancheng.om.dao.OrderedFoodMapper;
import com.qiancheng.om.dao.StallMapper;
import com.qiancheng.om.model.DiningHall;
import com.qiancheng.om.model.OrderedFood;
import com.qiancheng.om.model.Stall;
import com.qiancheng.om.service.StatisticsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计业务实现类
 *
 * @author 彭凌飞
 */
@Transactional
@Service("StatisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(StatisticsServiceImpl.class);
    // 完成订单常量
    private static final int RECEIVED_ORDERS = 2;

    @Resource
    private OrderedFoodMapper orderedFoodMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StallMapper stallMapper;
    @Resource
    private DiningHallMapper diningHallMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Response getAllStallRevenueByDay(Date date, Integer pageNum, Integer pageSize) throws
            Throwable {
        LOGGER.debug("开始getAllStallRevenueByDay方法，参数为" + date);
        if (date == null) {
            LOGGER.debug("方法结束，状态码为3");
            return new Response(3);
        }
        //创建存储当天所有商户盈利信息的集合
        List<Map<String, Object>> stallRevenueList = new ArrayList<>();
        //获得查询开始时间和结束时间
        Timestamp startDate = new Timestamp(date.getTime());
        Timestamp endDate = new Timestamp(startDate.getTime() + 86400000);
        Map<String, Object> orderQueryMap = new HashMap<>();
        orderQueryMap.put("startDate", startDate);
        orderQueryMap.put("endDate", endDate);
        Integer [] status = new Integer[] {RECEIVED_ORDERS};
        orderQueryMap.put("status", status);
        BigDecimal totalRevenue = BigDecimal.ZERO;
        //查询一天的订单
        List<Map<String, Object>> orderList = orderMapper.list(orderQueryMap);

        for (Map map : orderList) {
            //判断是否是退款订单
            if (map.get("refundApplicationTime") != null) {
                continue;
            }
            //判断是否有商户订单信息已经保存
            boolean has = false;
            for (Map stallMap : stallRevenueList) {
                if (map.get("stallId").equals(stallMap.get("stallId"))) {
                    has = true;
                }

            }

            OrderedFood orderedFood = new OrderedFood();
            orderedFood.setOrderId(map.get("id") + "");
            List<Map<String, Object>> orderFoodList = orderedFoodMapper.list(orderedFood);
            //声明盈利数额对象
            BigDecimal money = new BigDecimal(0);
            //循环遍历查询到的当前商户的所有订单菜品并计算盈利+
            for (Map foodMap : orderFoodList) {
                //判断价格是否是标准价购买
                String priceStr = foodMap.get("standardPrice") != null ? "standardPrice" :
                        "memberPriceCost";
                money = money.add(new BigDecimal(Integer.parseInt(foodMap.get("count") + "")
                        * (Double.parseDouble(foodMap.get(priceStr) + "") + Double
                        .parseDouble(foodMap.get("packFee") + ""))));
            }

            // 计算营收总价
            totalRevenue = totalRevenue.add(money);
            // 将单笔营收减去微信收取费率
            money = money.subtract(WeChatPayment.deductionWeChatRates(money));

            //查询商户的名称并保存
            Stall stall = new Stall();
            try {
                stall.setId(map.get("stallId") + "");
                stall.setIsDeleted(false);
            } catch (NullPointerException e) {
                continue;
            }
            List<Map<String, Object>> stallList = stallMapper.list(stall);


            //查询商户对应的食堂名称并保存
            DiningHall diningHall = new DiningHall();
            diningHall.setId(Integer.parseInt(stallList.get(0).get("diningHallId") + ""));
            List<Map<String, Object>> diningHallList = diningHallMapper.list(diningHall);


            //有过数据则更新数据，没有数据则添加数据
            if (has) {
                for (Map<String, Object> stallMap : stallRevenueList) {
                    if (map.get("stallId").equals(stallMap.get("stallId"))) {
                        stallMap.put("money", BigDecimal.valueOf((Double.parseDouble(stallMap
                                .get("money").toString()))).add(money));
                    }
                }
            } else {
                //生成用来存储一个商户一天的盈利信息的Map
                Map<String, Object> stallRevenueMap = new HashMap<>();
                stallRevenueMap.put("stallId", map.get("stallId"));
                //保存商户一天所盈利钱数
                stallRevenueMap.put("money", money);
                stallRevenueMap.put("stallName", stallList.get(0).get("name"));
                stallRevenueMap.put("diningHallName", diningHallList.get(0).get("name"));
                //将商户信息保存到集合中
                stallRevenueList.add(stallRevenueMap);
            }
        }

        Map<String, Object> stallRevenueByDay = new HashMap<>(2);
        //将所有查询到的数据进行一个分页操作（未再数据库进行分页）
        List<Map<String, Object>> pageList = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            if ((pageNum - 1) * pageSize + i < stallRevenueList.size()) {
                pageList.add(stallRevenueList.get((pageNum - 1) * pageSize + i));
            } else {
                break;
            }
        }
        // 将营收降序排序
        Collections.sort(pageList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return ((BigDecimal) o2.get("money")).compareTo((BigDecimal)o1.get("money"));
            }
        });
        stallRevenueByDay.put("pageList", pageList);
        stallRevenueByDay.put("count", stallRevenueList.size());
        stallRevenueByDay.put("totalRevenue", totalRevenue);
        LOGGER.debug("方法结束，状态码为0");
        return new Response(0).add("message", stallRevenueByDay);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Response getSalesReport() throws Throwable {
        LOGGER.debug("getSalesReport方法开始");
        Map<String, Object> map = new HashMap<>();
        Calendar now = Calendar.getInstance();
        String todayStartStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1)
                + "-" + now.get(Calendar.DAY_OF_MONTH)
                + " 00:00:00";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long todayStart = sdf.parse(todayStartStr).getTime();
            long todayEnd = todayStart + 86400000;
            Map<String, Object> todayMess = getAllTurnoverByTime(todayStart, todayEnd);
            long yestardayStart = todayStart - 86400000;
            long yestardayEnd = todayStart - 1;
            Map<String, Object> yestardayMess = getAllTurnoverByTime(yestardayStart, yestardayEnd);
            map.put("todayAllMoney", todayMess.get("allMoney"));
            map.put("todayAllCount", todayMess.get("allCount"));
            map.put("todayRevenue", todayMess.get("revenue"));
            map.put("discrepancyMoney", ((BigDecimal) todayMess.get("allMoney")).subtract(
                    (BigDecimal) yestardayMess.get("allMoney")));
            map.put("discrepancyCount", (Integer) todayMess.get("allCount") - (Integer)
                    yestardayMess.get("allCount"));
            map.put("discrepancyRevenue", ((BigDecimal) todayMess.get("revenue")).subtract(
                    (BigDecimal) yestardayMess.get("revenue")));

        } catch (ParseException e) {
            LOGGER.error("出现错误，错误信息" + e.getMessage());
            e.printStackTrace();
            LOGGER.debug("方法结束，状态码为1");
            return new Response(1);
        }
        LOGGER.debug("方法结束，状态码为0");
        return new Response(0).add("message", map);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Response getRefundByTime(String stallId, Date startTime, Date endTime)
            throws Throwable {
        LOGGER.debug("开始getAllStallRevenueByDay方法，参数为" + stallId + "," + startTime + "," + endTime);
        if (stallId == null || startTime == null || endTime == null) {
            LOGGER.debug("方法结束，状态码为3");
            return new Response(3);
        }
        Map<String, Object> businessStatisticsMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sTime = new Timestamp(startTime.getTime());
        Timestamp eTime = new Timestamp(endTime.getTime());
        Map<String, Object> orderQueryMap = new HashMap<>();
        orderQueryMap.put("startDate", sTime);
        orderQueryMap.put("endDate", eTime);
        orderQueryMap.put("stallId", stallId);
        //根据时间 商户id查询订单
        List<Map<String, Object>> orderList = orderMapper.list(orderQueryMap);
        //判断营业订单数量（不包含退款订单），查看营业额
        BigDecimal refundMoney = BigDecimal.ZERO;
        int refundCount = 0;
        for (Map map : orderList) {
            OrderedFood orderedFood = new OrderedFood();
            orderedFood.setOrderId(map.get("id") + "");
            if ((Integer) map.get("status") == 5) {
                //退款订单+1
                refundCount++;
                List<Map<String, Object>> foodList = orderedFoodMapper.list(orderedFood);
                for (Map foodMap : foodList) {
                    String priceStr = foodMap.get("memberPrice") == null ? "standardPrice"
                            : "memberPrice";
                    //退款金额增加
                    refundMoney = refundMoney.add(((BigDecimal) foodMap.get(priceStr)).add(
                            (BigDecimal) foodMap.get("packFee")));

                    refundMoney = refundMoney.multiply(BigDecimal.valueOf((Integer) foodMap.get
                            ("count")));
                }
            }
        }

        businessStatisticsMap.put("refundMoney", refundMoney);
        businessStatisticsMap.put("refundCount", refundCount);
        LOGGER.debug("方法结束，状态码为0");
        return new Response(0).add("message", businessStatisticsMap);
    }

    @Override
    public Response getRevenueByTime(String stallId, Date startTime, Date endTime) throws
            Throwable {
        LOGGER.debug("开始getAllStallRevenueByDay方法，参数为" + stallId + "," + startTime + "," + endTime);
        if (stallId == null || startTime == null || endTime == null) {
            LOGGER.debug("方法结束，状态码为3");
            return new Response(3);
        }
        Map<String, Object> businessStatisticsMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sTime = new Timestamp(startTime.getTime());
        Timestamp eTime = new Timestamp(endTime.getTime());
        Map<String, Object> orderQueryMap = new HashMap<>();
        orderQueryMap.put("startDate", sTime);
        orderQueryMap.put("endDate", eTime);
        orderQueryMap.put("stallId", stallId);
        //根据时间 商户id查询订单
        List<Map<String, Object>> orderList = orderMapper.list(orderQueryMap);
        //判断营业订单数量（不包含退款订单），查看营业额
        BigDecimal sellMoney = BigDecimal.ZERO;
        int sellCount = 0;
        for (Map map : orderList) {
            OrderedFood orderedFood = new OrderedFood();
            orderedFood.setOrderId(map.get("id") + "");
            if ((Integer) map.get("status") == 2 || (Integer) map.get("status") == 6) {
                //已售订单+1
                sellCount++;
                List<Map<String, Object>> foodList = orderedFoodMapper.list(orderedFood);
                // 定义订单价格,用来减去微信手续费
                BigDecimal orderPrice = BigDecimal.ZERO;
                for (Map foodMap : foodList) {
                    String priceStr = foodMap.get("memberPrice") == null ? "standardPrice"
                            : "memberPrice";
                    // 计算订单价格
                    orderPrice = orderPrice.add(BigDecimal.valueOf(Integer.parseInt(foodMap.get
                            ("count") + "")).multiply(((BigDecimal) foodMap.get(priceStr)).add(
                            (BigDecimal) foodMap.get("packFee"))));
                }
                // 暂时不使用微信支付
                // 将单笔订单价格减去手续费添加进销售金额里
                // sellMoney = sellMoney.add(WeChatPayment.deductionWeChatRates(orderPrice));
            }
        }
        businessStatisticsMap.put("sellMoney", sellMoney);
        businessStatisticsMap.put("sellCount", sellCount);
        LOGGER.debug("方法结束，状态码为0");
        return new Response(0).add("message", businessStatisticsMap);
    }

    /**
     * 根据时间查询所有营业额,订单数，净收入
     *
     * @param startTime 开始时间毫秒数
     * @param endTime   结束时间毫秒数
     * @return 所有营业额, 订单数，净收入
     */
    private Map<String, Object> getAllTurnoverByTime(long startTime, long endTime) {
        Map<String, Object> messMap = new HashMap<>();
        try {
            Timestamp sTime = new Timestamp(startTime);
            Timestamp eTime = new Timestamp(endTime);
            Map<String, Object> orderQueryMap = new HashMap<>();
            orderQueryMap.put("startDate", sTime);
            orderQueryMap.put("endDate", eTime);
            //根据时间查找时间内订单
            List<Map<String, Object>> orderList = orderMapper.list(orderQueryMap);
            BigDecimal allMoney = BigDecimal.ZERO;
            int allCount = 0;
            BigDecimal revenue = BigDecimal.ZERO;
            //获得总销售额，订单数量，净收入
            for (Map map : orderList) {
                //判断是否是退款订单
                if (map.get("refundApplicationTime") != null) {
                    continue;
                }
                //订单数量+1
                allCount++;
                OrderedFood orderedFood = new OrderedFood();
                orderedFood.setOrderId((String) map.get("id"));
                List<Map<String, Object>> foodList = orderedFoodMapper.list(orderedFood);
                for (Map foodMap : foodList) {
                    if (foodMap.get("memberPrice") == null) {
                        //总销售额
                        allMoney = allMoney.add((BigDecimal) foodMap.get("standardPrice"));
                    } else {
                        //总销售额
                        allMoney = allMoney.add((BigDecimal) foodMap.get("memberPrice"));
                        //净收入
                        revenue = allMoney.add((BigDecimal) foodMap.get("memberPrice")).subtract(
                                (BigDecimal) foodMap.get("memberPriceCost"));
                    }
                }
            }
            messMap.put("allMoney", allMoney);
            messMap.put("allCount", allCount);
            messMap.put("revenue", revenue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messMap;

    }
}
