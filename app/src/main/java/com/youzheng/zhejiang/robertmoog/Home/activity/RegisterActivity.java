package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.login.RegisterBean;
import com.youzheng.zhejiang.robertmoog.Model.login.ShopQRCodeBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.PeopleMangerActivity;
import com.youzheng.zhejiang.robertmoog.Store.bean.Code;
import com.youzheng.zhejiang.robertmoog.Store.bean.UnqualifiedContent;
import com.youzheng.zhejiang.robertmoog.utils.View.MyCountDownTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class RegisterActivity  extends BaseActivity {

    EditText edt_phone ,edt_code;
    Button btn_send_code ;
    private MyCountDownTimer timer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("客户注册");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
        ((ImageView)findViewById(R.id.iv_next)).setImageResource(R.mipmap.group_30_1);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_code = (EditText) findViewById(R.id.edt_code);
        btn_send_code = (Button) findViewById(R.id.btn_send_code);
        timer = new MyCountDownTimer(btn_send_code,60000,1000);
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_phone.getText().toString().equals("")){
                    showToast(getString(R.string.phone_not_null));
                    return;
                }
                if (edt_code.getText().toString().equals("")){
                    showToast(getString(R.string.code_not_null));
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("phone",edt_phone.getText().toString());
                map.put("checkCode",edt_code.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.REGISTER_USER+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            showToast(getString(R.string.register_success));
                            RegisterBean registerBean = gson.fromJson(gson.toJson(baseModel.getDatas()),RegisterBean.class);
                            Intent intent = new Intent(mContext,RegisterSuccessActivity.class);
                            intent.putExtra("register",registerBean);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });

        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone.getText().toString().equals("")){
                    showToast(getString(R.string.phone_not_null));
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("phone",edt_phone.getText().toString());
                timer.start();
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEND_CODE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()== PublicUtils.code){
                            Code code = gson.fromJson(gson.toJson(baseModel.getDatas()),Code.class);
                            showStopDialog(code.getCheckCode());
                        }
                    }
                });
            }
        });


        ((ImageView)findViewById(R.id.iv_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SHOP_SCVAN+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            ShopQRCodeBean qrCodeBean = gson.fromJson(gson.toJson(baseModel.getDatas()),ShopQRCodeBean.class);

                        }
                    }
                });
            }
        });

    }



    public void showStopDialog(final String code) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(RegisterActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_get_code, null);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.show();
        dialogBuilder.setContentView(dialogView);

        TextView tv_no=dialogView.findViewById(R.id.tv_no);
        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);
        TextView version=dialogView.findViewById(R.id.version);
        version.setText(getString(R.string.code_is)+code);
//        tv_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogBuilder.dismiss();
//            }
//        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_code.setText(code);
                dialogBuilder.dismiss();
            }
        });

        Window window = dialogBuilder.getWindow();
        //这句设置我们dialog在底部
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        DisplayMetrics d = this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.74); // 高度设置为屏幕的0.6
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }
}
