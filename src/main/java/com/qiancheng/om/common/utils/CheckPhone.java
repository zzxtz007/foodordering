package com.qiancheng.om.common.utils;

import java.util.regex.Pattern;

/**
 * @author XLY
 */
public class CheckPhone {

    public static Boolean checkPhone(String phoneNum) {

        if (phoneNum.length() != 11) {
            return false;
        }

        String rule = "^[1][358]\\d{9}$";

        return Pattern.matches(rule, phoneNum);
    }

}
