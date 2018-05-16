package com.qiancheng.om.common.spring.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * String 到 Map 的转换器，支持泛型
 * <p>
 * 源数据为空或格式非法时返回 null
 *
 * @author Brendan Lee
 */
@Component
public class StringToMapConverter implements GenericConverter {
    private static final Gson GSON = new Gson();

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Map.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (!(source instanceof String) || ((String) source).isEmpty()) {
            return null;
        }
        String json = (String) source;

        Map<Object, Object> map;
        try {
            // 获取目标类型
            Type returnType = targetType.getResolvableType().getType();

            // 获取目标 Map 的键值类型，缺省为 Object
            Class keyClass;
            Class valueClass;
            if (returnType instanceof ParameterizedType) {
                Type[] kvTypeArr = ((ParameterizedType) returnType).getActualTypeArguments();
                keyClass = (Class) kvTypeArr[0];
                valueClass = (Class) kvTypeArr[1];
            } else {
                keyClass = Object.class;
                valueClass = Object.class;
            }

            // 先将原始字符串转为 Map<String, String>，转换失败时返回 null
            Map<String, String> rawKVMap;
            rawKVMap = GSON.fromJson(json, new TypeToken<HashMap<String, String>>() { }.getType());

            // 根据泛型类型，逐个解析出对应的键和值，并装入 Map
            map = new HashMap<>(rawKVMap.size(), 1);
            for (Map.Entry<String, String> entry : rawKVMap.entrySet()) {
                String rawKey = entry.getKey();
                String rawValue = entry.getValue();

                Object key = GSON.fromJson(rawKey, keyClass);
                Object value = GSON.fromJson(rawValue, valueClass);

                map.put(key, value);
            }
        } catch (Throwable t) {
            return null;
        }

        return map;
    }
}
