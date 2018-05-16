package com.qiancheng.om.common.utils;

import java.util.HashMap;

/**
 * 响应值类
 *
 * @author Brendan Lee
 */
public class Response extends HashMap<String, Object> {
	/**
	 * 构造一个含有状态码的响应值对象
	 *
	 * @param statusCode 状态码
	 */
	public Response(int statusCode) {
		put("status", statusCode);
	}

	/**
	 * 添加键值对
	 *
	 * @param key   键名
	 * @param value 值
	 * @return 响应值对象自身，便于连续调用
	 */
	public Response add(String key, Object value) {
		put(key, value);
		return this;
	}
}
