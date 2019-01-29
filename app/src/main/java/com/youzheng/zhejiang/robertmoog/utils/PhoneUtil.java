package com.youzheng.zhejiang.robertmoog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


}
