package com.qiancheng.om.common.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties 工具
 *
 * @author Brendan Lee
 */
class PropertiesUtils {
    private static final Map<String, Properties> PROPERTIES_MAP = new HashMap<>(16, .75F);
    private static final Logger LOGGER = Logger.getLogger(PropertiesUtils.class);

    /**
     * 载入 Properties 文件
     *
     * @param filePath Properties 文件的相对路径，相对于应用根目录
     * @param encoding 文件的字符编码
     */
    static void load(String filePath, String encoding) {
        String propId = getPropId(filePath, encoding);

        // 文件已加载过时返回
        if (PROPERTIES_MAP.containsKey(propId)) {
            return;
        }

        // 载入文件
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(AppContextUtils.getClasspath(filePath));
            InputStreamReader isr = new InputStreamReader(fis, encoding);
            properties.load(isr);
        } catch (IOException e) {
            LOGGER.error("读取 Properties 文件失败", e);
        }

        // 置入 Map
        PROPERTIES_MAP.put(propId, properties);
    }

    /**
     * 读取指定 Properties 文件的指定值
     * <p>
     * 在读取之前，Properties 文件必须通过 {@link #load(String, String)} 载入过
     *
     * @param filePath Properties 文件的相对路径，相对于应用根目录
     * @param encoding 文件的字符编码
     * @param key      键名
     * @return 字符串形式的值
     * @throws NullPointerException Properties 文件未通过 {@link #load(String, String)} 载入过时抛出
     */
    static String getValue(String filePath, String encoding, String key) {
        String propId = getPropId(filePath, encoding);
        return PROPERTIES_MAP.get(propId).getProperty(key);
    }

    /**
     * 生成 Properties 文件唯一标识
     *
     * @param filePath 文件路径
     * @param encoding 文件的字符编码
     * @return 文件唯一标识
     */
    private static String getPropId(String filePath, String encoding) {
        return filePath + '_' + encoding;
    }
}
