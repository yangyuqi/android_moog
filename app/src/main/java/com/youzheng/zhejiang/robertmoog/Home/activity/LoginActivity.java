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
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.MainActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.login.LoginBean;
import com.youzheng.zhejiang.robertmoog.Model.login.UserConfigDataBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.Code;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.MyCountDownLoginTimer;
import com.youzheng.zhejiang.robertmoog.utils.View.MyCountDownTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginActivity extends BaseActivity {

    private EditText edt_phone ,edt_password ,edt_code ;
    Button btn_send_code ;
    private MyCountDownLoginTimer timer ;
    String type ="2" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_login_by_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = null;
                changeLayout(null);
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="1";
                changeLayout(type);
            }
        });

        findViewById(R.id.tv_login_by_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="2";
                changeLayout(type);
            }
        });

        findViewById(R.id.tv_login_for_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,ForgetPwdActivity.class));
            }
        });

        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_send_code = findViewById(R.id.btn_send_code);
        timer = new MyCountDownLoginTimer(btn_send_code,60000,1000);
        edt_code = findViewById(R.id.edt_code);

        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone.getText().toString().equals("")){
                    showToast(getString(R.string.phone_not_null));
                    return;
                }
                if (type!=null) {
                    if (edt_password.getText().toString().equals("")) {
                        showToast(getString(R.string.pwd_not_null));
                        return;
                    }
                }else {
                    if (edt_code.getText().toString().equals("")){
                        showToast("验证码不能为空");
                        return;
                    }
                }
                if (type!=null) {
                    initLogin(edt_password.getText().toString(),type); //code 密码 验证码
                }else {
                    initLogin(edt_code.getText().toString(),type);
                }
            }
        });

        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone.getText().toString().equals("")){
                    showToast(getString(R.string.phone_not_null));
                    return;
                }
                timer.start();
                Map<String,Object> map = new HashMap<>();
                map.put("phone",edt_phone.getText().toString());

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
    }


    public void showStopDialog(final String code) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(LoginActivity.this,R.style.mydialog).create();
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
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
       // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    private void initLogin(String code ,String type) {
        String name , password ;
        if (type!=null){
            name = edt_phone.getText().toString();
            password =  PublicUtils.getSHA256StrJava(code);
        }else {
            name = edt_phone.getText().toString()+"__"+code;
            password = code ;
        }

       OkHttpClientManager.getAsyn(UrlUtils.LOGIN+"?grant_type=password&username="+name+"&password="+ password+"&client_id=app&client_secret=appSecret", new OkHttpClientManager.StringCallback() {
           @Override
           public void onFailure(Request request, IOException e) {

           }

           @Override
           public void onResponse(String response) {
               BaseModel model = gson.fromJson(response,BaseModel.class);
               if (model.getCode()==PublicUtils.code){
                   LoginBean loginBean = gson.fromJson(gson.toJson(model.getDatas()), LoginBean.class);
                   SharedPreferencesUtils.setParam(mContext,PublicUtils.access_token,loginBean.getAccess_token());
                   OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GET_USER_ONFO + "?access_token=" + loginBean.getAccess_token(), new OkHttpClientManager.StringCallback() {
                       @Override
                       public void onFailure(Request request, IOException e) {

                       }

                       @Override
                       public void onResponse(String response) {
                           BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                           if (baseModel.getCode()==PublicUtils.code){
                               UserConfigDataBean dataBean = gson.fromJson(gson.toJson(baseModel.getDatas()),UserConfigDataBean.class);
                               SharedPreferencesUtils.setParam(mContext,PublicUtils.role,dataBean.getUserConfigData().getUserRole());
                               SharedPreferencesUtils.setParam(mContext,PublicUtils.shop_title,dataBean.getUserConfigData().getShopName());
                               SharedPreferencesUtils.setParam(mContext,PublicUtils.shop_phone,edt_phone.getText().toString());
                               startActivity(new Intent(mContext, MainActivity.class));
                               finish();
                           }
                       }
                   });

               }else {
                   showToast(model.getMsg());
               }
           }
       });
    }

    void changeLayout(String type){
        if (type!=null) {
            findViewById(R.id.tv_login_for_pwd).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_login_by_code).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_show_pwd).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_show_code).setVisibility(View.GONE);
            findViewById(R.id.tv_login_by_pwd).setVisibility(View.GONE);
            findViewById(R.id.iv_back).setVisibility(View.GONE);
        }else {
            findViewById(R.id.tv_login_for_pwd).setVisibility(View.GONE);
            findViewById(R.id.tv_login_by_code).setVisibility(View.GONE);
            findViewById(R.id.ll_show_pwd).setVisibility(View.GONE);
            findViewById(R.id.rl_show_code).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_login_by_pwd).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        }
    }
}
