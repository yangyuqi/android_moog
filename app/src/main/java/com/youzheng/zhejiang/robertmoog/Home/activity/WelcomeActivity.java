package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.MainActivity;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.SPUtils;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        boolean isfirst= (boolean) SPUtils.getParam(WelcomeActivity.this,"IsFir",true);
        if (isfirst){
            startActivity(new Intent(WelcomeActivity.this,LeadActivity.class));
            return;
        }else {
            final String token = (String) SharedPreferencesUtils.getParam(WelcomeActivity.this, PublicUtils.access_token, "");
            Integer time = 2000;    //设置等待时间，单位为毫秒
            Handler handler = new Handler();
            //当计时结束时，跳转至主界面
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (token.equals("")){
                        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    }else {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }
                    finish();
                }
            }, time);
        }
    }
}
