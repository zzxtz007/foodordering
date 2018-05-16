package com.qiancheng.om.common.spring.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * String 到 Boolean 的转换器
 * <p>
 * 源数据为空或格式非法时返回 null
 *
 * @author Brendan Lee
 */
@Component
public class StringToBooleanConverter implements Converter<String, Boolean> {
    @Override
    public Boolean convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            return Boolean.valueOf(source);
        } catch (Exception e) {
            return null;
        }
    }
}
