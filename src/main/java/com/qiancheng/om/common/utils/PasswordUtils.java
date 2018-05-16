package com.qiancheng.om.common.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 密码工具
 *
 * @author Brendan Lee
 */
public class PasswordUtils {
    /**
     * 盐值可以包含的字符数组
     */
    private static final char[] SALT_CHAR_ARR =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 对指定值进行SHA-256散列运算，获取以16进制表现的散列值
     *
     * @param plaintext 明文
     * @return 16进制表现的SHA-256散列值
     */
    private static String sha256Hex(String plaintext) {
        // 获取SHA-256散列实例
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        // 散列值存入byte数组
        byte[] digestByteArr = md.digest(plaintext.getBytes());

        // 对每个byte取低8位，转为hex String并补前导零
        StringBuilder sb = new StringBuilder(64);
        for (byte b : digestByteArr) {
            String hexVal = Integer.toHexString(b & 0xFF);

            if (hexVal.length() == 1) {
                sb.append('0');
            }
            sb.append(hexVal);
        }

        return sb.toString();
    }

    /**
     * 生成指定长度的盐值
     * 盐值的字符范围为 0-9、a-z 和 A-Z
     *
     * @param length 盐值长度
     * @return 盐值
     */
    private static String generateSalt(int length) {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = sr.nextInt(SALT_CHAR_ARR.length);
            sb.append(SALT_CHAR_ARR[index]);
        }

        return sb.toString();
    }

    /**
     * 生成随机盐值，并计算明文密码加盐后的散列值
     * 通常用于注册
     *
     * @param password 明文密码
     * @return 包含盐值和加盐密码散列值的密码对象
     */
    public static Password newHash(String password) {
        Password pwdObj = new Password();
        String salt = generateSalt(64);
        pwdObj.setSalt(salt);
        pwdObj.setHash(sha256Hex(password + salt));

        return pwdObj;
    }

    /**
     * 根据给定的明文密码和盐值，计算加盐密码的散列值
     * 通常用于登录校验
     *
     * @param password 明文密码
     * @param salt     盐值
     * @return 加盐密码的散列值
     */
    public static String hash(String password, String salt) {
        return sha256Hex(password + salt);
    }
}
