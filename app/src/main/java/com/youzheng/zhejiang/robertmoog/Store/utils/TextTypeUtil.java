package com.youzheng.zhejiang.robertmoog.Store.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TextTypeUtil {


    public static void setMiddleText(TextView textView, Context context) {
         //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonnts/lantingzhongheie.ttf");
         //使用字体成仿宋体
        textView.setTypeface(typeface);

    }


    public static void setTextContent(TextView textView, Context context) {
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonnts/lantingheie.ttf");
        //使用字体成仿宋体
        textView.setTypeface(typeface);

    }

    public static void setXiText(TextView textView, Context context) {
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonnts/lantingxihei.ttf");
        //使用字体成仿宋体
        textView.setTypeface(typeface);

    }


    public static void setDinProBold(TextView textView, Context context) {
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonnts/dinpro_bold.ttf");
        //使用字体成仿宋体
        textView.setTypeface(typeface);

    }

    public static void setDinProLight(TextView textView, Context context) {
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonnts/dinpro_light.ttf");
        //使用字体成仿宋体
        textView.setTypeface(typeface);

    }

    public static void setDinProRegular(TextView textView, Context context) {
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonnts/dinpro_regular.ttf");
        //使用字体成仿宋体
        textView.setTypeface(typeface);

    }
}
