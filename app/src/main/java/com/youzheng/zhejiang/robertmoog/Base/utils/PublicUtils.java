package com.youzheng.zhejiang.robertmoog.Base.utils;

import android.content.res.Resources;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PublicUtils {

    public static int code = 200 ;
    public static int no_exist = 3002 ;
    public static String access_token = "access_token";
    public static String token_type = "token_type";
    public static String role = "role" ;
    public static String shop_title = "shop_title" ;
    public static String shop_phone = "shop_phone" ;
    public static String is_recognition ="is_recognition" ;
    public static String SHOP_SELLER ="SHOP_SELLER";
    public static String SHOP_LEADER = "SHOP_LEADER";//店主
    public static String EMPLOYEDID
            = "EMPLOYEDID";//店员ID

    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }


    public static String phoneNum(String phonenum){

        if(!TextUtils.isEmpty(phonenum) && phonenum.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phonenum.length(); i++) {
                char c = phonenum.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

            return sb.toString() ;
        }
        return "";
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
       String temp = null;
        for (int i = 0; i < bytes.length; i++) {
        temp = Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
       if (temp.length() == 1) {
       stringBuffer.append("0");
        }
        stringBuffer.append(temp);
       }
      return stringBuffer.toString();
        }

    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

}