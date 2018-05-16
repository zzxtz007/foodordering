package com.qiancheng.om.common.enumeration;

/**
 * 非法访问的类型
 *
 * @author Brendan Lee
 */
public enum IllegalAccessTypeEnum {
    /**
     * 必要参数缺失
     */
    MISS_PARAM("必要参数缺失"),

    /**
     * 参数非法
     */
    ILLEGAL_PARAM("参数非法"),

    /**
     * 无权访问
     */
    NO_PERMISSION("无权访问"),

    /**
     * 文件过大
     */
    FILE_OVERSIZE("文件过大"),

    /**
     * 文件扩展名有误
     */
    INCORRECT_FILE_EXTENSION("文件扩展名有误");

    /**
     * 非法访问的类型说明
     */
    private String value;

    /**
     * 枚举构造器
     *
     * @param value 枚举的值
     */
    IllegalAccessTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 获取非法访问的类型说明
     *
     * @return 非法访问的类型说明
     */
    public String getValue() {
        return value;
    }
}
