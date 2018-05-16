package com.qiancheng.om.common.exception;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;

/**
 * 非法访问异常
 * <p>
 * 用于区分合法访问时产生的异常，便于分别统一处理
 * <p>
 * 如：上传图片时文件扩展名不正确，由于前端已经做出限制，这种情况只会在非法访问时发生；而 {@link java.sql.SQLException} 在合法访问时也可能抛出
 *
 * @author Brendan Lee
 */
public class IllegalAccessException extends Exception {
    public IllegalAccessException() {}

    public IllegalAccessException(String message) {
        super(message);
    }

    public IllegalAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessException(Throwable cause) {
        super(cause);
    }

    public IllegalAccessException(IllegalAccessTypeEnum type) {
        this(type.getValue());
    }
}
