package com.qiancheng.om.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Properties 处理工具
 *
 * @author Brendan Lee
 */
public class PropertiesHandler {
    private String FILE_NAME;
    private String ENCODING;

    /**
     * 构造 Properties 处理工具
     *
     * @param filePath Properties 文件的相对路径，相对于应用根目录
     * @param encoding 文件的字符编码
     */
    public PropertiesHandler(String filePath, String encoding) {
        FILE_NAME = filePath;
        ENCODING = encoding;
        PropertiesUtils.load(filePath, encoding);
    }

    /**
     * 构造 Properties 处理工具
     *
     * @param filePath Properties 文件的相对路径，相对于应用根目录
     */
    public PropertiesHandler(String filePath) {
        this(filePath, "UTF-8");
    }

    /**
     * 获取指定键对应的值
     *
     * @param key 键名
     * @return 字符串类型的值
     */
    public String getValue(String key) {
        return PropertiesUtils.getValue(FILE_NAME, ENCODING, key);
    }

    /**
     * 获取 byte 类型的值
     *
     * @param key 键名
     * @return byte 形式的值
     */
    public byte getByte(String key) {
        return Byte.parseByte(getValue(key));
    }

    /**
     * 获取 short 类型的值
     *
     * @param key 键名
     * @return short 形式的值
     */
    public short getShort(String key) {
        return Short.parseShort(getValue(key));
    }

    /**
     * 获取 int 类型的值
     *
     * @param key 键名
     * @return int 形式的值
     */
    public int getInt(String key) {
        return Integer.parseInt(getValue(key));
    }

    /**
     * 获取 long 类型的值
     *
     * @param key 键名
     * @return int 形式的值
     */
    public long getLong(String key) {
        return Long.parseLong(getValue(key));
    }

    /**
     * 获取 float 类型的值
     *
     * @param key 键名
     * @return int 形式的值
     */
    public float getFloat(String key) {
        return Float.parseFloat(getValue(key));
    }

    /**
     * 获取 double 类型的值
     *
     * @param key 键名
     * @return int 形式的值
     */
    public double getDouble(String key) {
        return Double.parseDouble(getValue(key));
    }

    /**
     * 获取 boolean 类型的值
     *
     * @param key 键名
     * @return boolean 形式的值
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getValue(key));
    }

    /**
     * 获取 BigInteger 类型的值
     *
     * @param key 键名
     * @return BigInteger 形式的值
     */
    public BigInteger getBigInteger(String key) {
        return new BigInteger(getValue(key));
    }

    /**
     * 获取 BigDecimal 类型的值
     *
     * @param key 键名
     * @return BigDecimal 形式的值
     */
    public BigDecimal getBigDecimal(String key) {
        return new BigDecimal(getValue(key));
    }
}
