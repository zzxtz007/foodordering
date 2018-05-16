package com.qiancheng.om.common.spring.converter;

import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * String 到 Date 的转换器
 * <p>
 * 源数据为空或格式非法时返回 null
 * <p>
 * 接受以下 Pattern，Pattern 含义与 {@link java.text.SimpleDateFormat SimpleDateFormat} 相同
 * <table><tr><td>yyyy-MM-dd HH:mm:ss:SSS
 * <tr><td>yyyy-MM-dd HH:mm:ss
 * <tr><td>yyyy-MM-dd
 * <tr><td>HH:mm:ss:SSS
 * <tr><td>HH:mm:ss
 *
 * @author Brendan Lee
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {
    /**
     * Date parser for date and time with milliseconds
     */
    private static final DateParser DATE_TIME_MILLIS_PARSER = FastDateFormat.getInstance
            ("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * Date parser for date and time
     */
    private static final DateParser DATE_TIME_PARSER = FastDateFormat.getInstance("yyyy-MM-dd "
            + "HH:mm:ss");

    /**
     * Date parser for date
     */
    private static final DateParser DATE_PARSER = FastDateFormat.getInstance("yyyy-MM-dd");

    /**
     * Date parser for time with milliseconds
     */
    private static final DateParser TIME_MILLIS_PARSER = FastDateFormat.getInstance("HH:mm:ss:SSS");

    /**
     * Date parser for time
     */
    private static final DateParser TIME_PARSER = FastDateFormat.getInstance("HH:mm:ss");

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            // 统计各分隔符的数量
            int hyphenCount = 0;
            int spaceCount = 0;
            int colonCount = 0;
            for (byte b : source.getBytes("UTF-8")) {
                switch (b) {
                    // 日期分隔符
                    case '-': {
                        hyphenCount++;
                        break;
                    }
                    // 日期与时间之间的空格
                    case ' ': {
                        spaceCount++;
                        break;
                    }
                    // 时间分隔符
                    case ':': {
                        colonCount++;
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }

            // 根据各分隔符的数量匹配对应的解析器
            switch (hyphenCount | spaceCount << 2 | colonCount << 4) {
                // date & time with milliseconds
                case 54: {
                    return DATE_TIME_MILLIS_PARSER.parse(source);
                }
                // date & time
                case 38: {
                    return DATE_TIME_PARSER.parse(source);
                }
                // date
                case 2: {
                    return DATE_PARSER.parse(source);
                }
                // time width milliseconds
                case 48: {
                    return TIME_MILLIS_PARSER.parse(source);
                }
                // time
                case 32: {
                    return TIME_PARSER.parse(source);
                }
                // 非法数据
                default: {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
}
