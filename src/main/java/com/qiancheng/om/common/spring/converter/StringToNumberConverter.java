package com.qiancheng.om.common.spring.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

/**
 * String 到 Number 的转换器
 * <p>
 * 包括以下类型
 * <table><tr><td>{@link java.lang.Byte}
 * <tr><td>{@link java.lang.Short}
 * <tr><td>{@link java.lang.Integer}
 * <tr><td>{@link java.lang.Long}
 * <tr><td>{@link java.lang.Float}
 * <tr><td>{@link java.lang.Double}
 * <tr><td>{@link java.math.BigInteger}
 * <tr><td>{@link java.math.BigDecimal}
 * <p>
 * 源数据为空或格式非法时返回 null
 *
 * @author Brendan Lee
 */
@Component
public class StringToNumberConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Number.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (!(source instanceof String) || ((String) source).isEmpty()) {
            return null;
        }
        String rawValue = (String) source;

        Object value;
        try {
            switch (targetType.getName()) {
                case "java.lang.Byte": {
                    value = Byte.valueOf(rawValue);
                    break;
                }
                case "java.lang.Short": {
                    value = Short.valueOf(rawValue);
                    break;
                }
                case "java.lang.Integer": {
                    value = Integer.valueOf(rawValue);
                    break;
                }
                case "java.lang.Long": {
                    value = Long.valueOf(rawValue);
                    break;
                }
                case "java.lang.Float": {
                    value = Float.valueOf(rawValue);
                    break;
                }
                case "java.lang.Double": {
                    value = Double.valueOf(rawValue);
                    break;
                }
                case "java.math.BigInteger": {
                    value = new BigInteger(rawValue);
                    break;
                }
                case "java.math.BigDecimal": {
                    value = new BigDecimal(rawValue);
                    break;
                }
                default: {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }

        return value;
    }
}
