package com.qiancheng.om.common.enumeration;

/**
 * 用户角色枚举
 *
 * @author Ice_Dog
 */

public enum UserRoleEnum {
    /**
     * 顾客
     */
    CONSUMER(1),

    /**
     * 商户
     */
    STALL(2),

    /**
     * 运营方管理员
     */
    OPERATOR(3),

    /**
     * 运营方超级管理员
     */
    OPERATOR_ADMIN(4);

    private int value;

    /**
     * 私有构造
     *
     * @param value 枚举值
     */
    UserRoleEnum(int value) {
        this.value = value;
    }

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    public int getValue() {
        return value;
    }
}
