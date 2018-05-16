package com.qiancheng.om.common.utils;

/**
 * 封装用户权限
 * 为简单，只封装了权限的名称
 * @author Minhellic
 *
 */
public class FirmPrivilege {
    /**
     * 用户权限的名称
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FirmPrivilege(String value) {
        this.value = value;
    }

    public FirmPrivilege() {
    }

}
