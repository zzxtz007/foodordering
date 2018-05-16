package com.qiancheng.om.common.spring.converter;

import com.google.gson.Gson;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * String 到 List 的转换器，支持泛型
 * <p>
 * 源数据为空或格式非法时返回 null
 *
 * @author Brendan Lee
 */
@Component
public class StringToListConverter implements GenericConverter {
    private static final Gson GSON = new Gson();

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, List.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (!(source instanceof String) || ((String) source).isEmpty()) {
            return null;
        }
        String json = (String) source;

        List<Object> list;
        try {
            // 获取目标类型
            Type returnType = targetType.getResolvableType().getType();

            // 获取目标 List 的元素类型，缺省为 Object
            Class elemClass = returnType instanceof ParameterizedType ? (Class) ((ParameterizedType)
                    returnType).getActualTypeArguments()[0] : Object.class;

            // 先将原始字符串转为 String 数组，转换失败时返回 null
            String[] rawValueArr;
            rawValueArr = GSON.fromJson(json, String[].class);

            // 根据泛型类型，逐个解析出对应的值，并装入 List
            list = new ArrayList<>(rawValueArr.length);
            for (String rawValue : rawValueArr) {
                list.add(GSON.fromJson(rawValue, elemClass));
            }
        } catch (Throwable t) {
            return null;
        }

        return list;
    }
}
