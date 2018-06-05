package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.utils.AES;
import com.qiancheng.om.common.utils.AppContextUtils;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.*;
import com.qiancheng.om.model.Consumer;
import com.qiancheng.om.model.Food;
import com.qiancheng.om.model.FoodCategory;
import com.qiancheng.om.model.OrderedFood;
import com.qiancheng.om.service.ConsumerService;
import com.qiancheng.om.service.FoodService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 用户业务
 *
 * @author 邹运
 */
@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {
	private static final Logger LOGGER = Logger.getLogger(ConsumerServiceImpl.class);
	private static final int PHONE_NUMBER_INDEX = 0;
	private static final int PHONE_NUMBER_DIGITAL_INDEX = 1;
	private static final String COMMA = ",";
	private static final String COLON = ":";
	private static final String QUOTATION_MARKS = "\"";
	@Resource

	private ConsumerMapper consumerMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private OrderedFoodMapper orderedFoodMapper;
	@Resource
	private FoodMapper foodMapper;
	@Resource
	private FoodCategoryMapper foodCategoryMapper;
	@Resource
	private FoodService foodService;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Response updateById(String uid, String name, String studentId, String phone) throws
			Throwable {
		int rows;
		if (uid == null || name == null || studentId == null || "".equals(name) || ""
				.equals(studentId) || phone == null || "".equals(phone)) {
			LOGGER.debug("方法结束，状态码为3");
			return new Response(3);
		}
		try {
			uid = createUid();
			Consumer consumer = new Consumer();
			consumer.setPhone(phone);
			consumer.setId(uid);
			consumer.setName(name);
			consumer.setStudentId(studentId);
			consumer.setMember(true);
			rows = consumerMapper.insert(consumer);
			if (!(rows > 0)) {
				LOGGER.debug("方法结束，状态码为3");
				return new Response(3);
			}
		} catch (Exception e) {
			LOGGER.error("出现错误，错误信息" + e.getMessage());
			e.printStackTrace();
			LOGGER.debug("方法结束，状态码为1");
			return new Response(1);
		}
		LOGGER.debug("方法结束，状态码为0");
		return new Response(0).add("rows", rows);
	}

	private String createUid() {
		StringBuilder sb = new StringBuilder();
		sb.append("Openid_");
		for (int i = 0; i < 15; i++) {
			sb.append((int) (0 + Math.random() * 9 - 0 + 1));
		}
		return sb.toString();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Response list(String uid) throws Throwable {
		if (uid == null) {
			LOGGER.debug("方法结束，状态码为3");
			return new Response(3);
		}
		List<Map<String, Object>> consumerList;
		try {
			Consumer consumer = new Consumer();
			consumer.setId(uid);
			consumerList = consumerMapper.list(consumer);
			if (consumerList.isEmpty()) {
				LOGGER.debug("方法结束，状态码为3");
				return new Response(3);
			}
		} catch (Exception e) {
			LOGGER.error("出现错误，错误信息" + e.getMessage());
			e.printStackTrace();
			LOGGER.debug("方法结束，状态码为1");
			return new Response(1);
		}
		LOGGER.debug("方法结束，状态码为0");
		return new Response(0).add("consumerList", consumerList).add("consumer",consumerList.get(0));
	}

	@Override
	public Response listFavoriteFood(String uid) throws Throwable {
		if (uid == null) {
			LOGGER.debug("方法结束，状态码为3");
			return new Response(3);
		}

		String firstId = null;
		String secondId = null;
		String thirdId = null;
		try {
			Map<String, Object> consumerMap = new HashMap<>();
			Map<String, Integer> foods = new HashMap<>();
			consumerMap.put("consumerId", uid);
			List<Map<String, Object>> orderList = orderMapper.list(consumerMap);

			if (orderList.size() == 0) {
				return new Response(0).add("info", "[]");
			}

			for (Map map : orderList) {
				OrderedFood orderedFood = new OrderedFood();
				orderedFood.setOrderId(map.get("id") + "");
				List<Map<String, Object>> orderFoodList = orderedFoodMapper.list(orderedFood);
				for (Map orderFoodMap : orderFoodList) {
					Food food = new Food();
					food.setId((Integer) orderFoodMap.get("foodId"));
					food.setIsDeleted(false);
					if (foodMapper.list(food, null, null).isEmpty()) {
						continue;
					}
					String foodId = orderFoodMap.get("foodId") + "";
					if (foods.get(foodId) == null) {
						foods.put(orderFoodMap.get("foodId") + "", Integer.parseInt(orderFoodMap
								.get("count") + ""));
					} else {
						foods.put(orderFoodMap.get("foodId") + "", foods.get(foodId) + Integer
								.parseInt(orderFoodMap.get("count") + ""));
					}
				}
			}

			Set<String> sets = foods.keySet();
			//排序
			for (String foodId : sets) {
				if (thirdId != null) {
					if (foods.get(foodId) > foods.get(thirdId)) {
						thirdId = foodId;
					}
				} else {
					thirdId = foodId;
				}
				if (secondId != null) {
					if (foods.get(thirdId) > foods.get(secondId)) {
						String fid = secondId;
						secondId = thirdId;
						thirdId = fid;
					}
				} else {
					secondId = thirdId;
					thirdId = null;
				}
				if (firstId != null) {
					if (foods.get(secondId) > foods.get(firstId)) {
						String fid = firstId;
						firstId = secondId;
						secondId = fid;
					}
				} else {
					firstId = secondId;
					secondId = null;
				}
			}
		} catch (Exception e) {
			LOGGER.error("出现错误，错误信息" + e.getMessage());
			e.printStackTrace();
			LOGGER.debug("方法结束，状态码为1");
			return new Response(1);
		}
		Food food = new Food();
		Map<String, Object> firstFood = null;
		Map<String, Object> secondFood = null;
		Map<String, Object> thirdFood = null;


		List<Map<String, Object>> favoriteFood = new ArrayList<>();
		if (firstId != null) {
			food.setId(Integer.parseInt(firstId));
			firstFood = foodMapper.list(food, null, null).get(0);
			addStallId(Integer.valueOf(firstId), firstFood);
		}
		if (secondId != null) {
			food.setId(Integer.parseInt(secondId));
			secondFood = foodMapper.list(food, null, null).get(0);
			addStallId(Integer.valueOf(secondId), secondFood);
		}
		if (thirdId != null) {
			food.setId(Integer.parseInt(thirdId));
			thirdFood = foodMapper.list(food, null, null).get(0);
			addStallId(Integer.valueOf(thirdId), thirdFood);
		}
		LOGGER.debug("方法结束，状态码为0");
		favoriteFood.add(firstFood);
		favoriteFood.add(secondFood);
		favoriteFood.add(thirdFood);
		return new Response(0).add("info", favoriteFood);
	}

	@Override
	public Response getPhone(String iv, String encryptedData) throws Throwable {
		if (iv == null || encryptedData == null) {
			return new Response(3);
		}
		HttpSession session = AppContextUtils.getSession();
		String sessionKey = (String) session.getAttribute("sessionKey");
		if (sessionKey == null) {
			return new Response(3);
		}

		byte[] resultByte = AES.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64
				(sessionKey), Base64.decodeBase64(iv));
		if (null == resultByte || resultByte.length <= 0) {
			return new Response(3);
		}
		String data = new String(resultByte, "UTF-8");
		String phoneNumber = data.split(COMMA)[PHONE_NUMBER_INDEX].split(COLON)
				[PHONE_NUMBER_DIGITAL_INDEX];
		phoneNumber = phoneNumber.split(QUOTATION_MARKS)[PHONE_NUMBER_DIGITAL_INDEX];
		Consumer consumer = new Consumer();
		consumer.setId((String) session.getAttribute("uid"));
		consumer.setPhone(phoneNumber);
		consumerMapper.updateById(consumer);
		return new Response(0).add("info", phoneNumber);
	}

	/**
	 * 根据菜品类型获取商户id
	 *
	 * @param categoryId 菜品分类id
	 * @return 商户的id
	 */
	private String getStallIdByCateGoryId(int categoryId) {
		String stallId = null;
		FoodCategory foodCategory = new FoodCategory();
		foodCategory.setId(categoryId);
		List<Map<String, Object>> foodCategoryList = foodCategoryMapper.listById(foodCategory);
		if (!foodCategoryList.isEmpty()) {
			stallId = foodCategoryList.get(0).get("stallId").toString();
		}
		return stallId;
	}

	/**
	 * 将商户id添加到集合中
	 *
	 * @param map 需要添加进的集合
	 */
	private void addStallId(Integer id, Map<String, Object> map) throws Throwable {
		map.put("stallId", getStallIdByCateGoryId((Integer) map.get("categoryId")));
		map.put("positiveRate", foodService.mark(id).get("info"));
	}
}
