package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.youzheng.zhejiang.robertmoog.Store.utils.PermissionUtils;
import com.youzheng.zhejiang.robertmoog.utils.ClickUtils;
import com.youzheng.zhejiang.robertmoog.utils.PhoneUtil;
import com.youzheng.zhejiang.robertmoog.utils.View.MyCountDownTimer;
import com.youzheng.zhejiang.robertmoog.utils.View.NoteInfoDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class RegisterActivity  extends BaseActivity implements TextWatcher {

    EditText edt_phone ,edt_code;
    Button btn_send_code ;
    private MyCountDownTimer timer ;
    private String phone;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        phone=getIntent().getStringExtra("no_phone");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCode();

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
        edt_phone.addTextChangedListener(this);

        if (!TextUtils.isEmpty(phone)){
            edt_phone.setText(phone);
        }
        timer = new MyCountDownTimer(btn_send_code,60000,1000);
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_phone.getText().toString().equals("")){
                    showToasts(getString(R.string.login_input_phone));
                    return;
                }
                if (edt_code.getText().toString().equals("")){
                    showToasts(getString(R.string.code_not_null));
                    return;
                }

                if (ClickUtils.isFastDoubleClick()){
                    return;
                }else {
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
                                // showToast(getString(R.string.register_success));
                                RegisterBean registerBean = gson.fromJson(gson.toJson(baseModel.getDatas()),RegisterBean.class);
                                Intent intent = new Intent(mContext,RegisterSuccessActivity.class);
                                intent.putExtra("register",registerBean);
                                Log.e("customerid","注册"+registerBean.getCustomerId());
                                startActivity(intent);
                                finish();
                            }else {
                                showToasts(baseModel.getMsg());
                            }
                        }
                    });
                }


            }
        });

        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone.getText().toString().equals("")){
                    showToasts(getString(R.string.login_input_phone));
                    return;

                }else if (PhoneUtil.isCellphone(edt_phone.getText().toString())==false) {
                    showToasts("手机号码格式不正确");
                }else {
                    Map<String,Object> map = new HashMap<>();
                    map.put("phone",edt_phone.getText().toString());

                    edt_code.requestFocus();
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CUSTOMER_REGESTER, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            timer.onFinish();
                            btn_send_code.setText("获取验证码");
                        }

                        @Override
                        public void onResponse(String response) {
                            BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                            if (baseModel.getCode()== PublicUtils.code){
                                Code code = gson.fromJson(gson.toJson(baseModel.getDatas()),Code.class);
                                timer.start();
                                //showStopDialog(code.getCheckCode());
                            }else {
                                if (!TextUtils.isEmpty(baseModel.getMsg())){
                                    showToasts(baseModel.getMsg());
                                    timer.onFinish();
                                    btn_send_code.setText("获取验证码");
                                }
                            }
                        }
                    });
                }
                }
        });

        ((ImageView)findViewById(R.id.iv_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (PermissionUtils.permissionIsOpen(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            ){


                        NoteInfoDialog infoDialog = new NoteInfoDialog(mContext,url);
                        infoDialog.show();
                    } else {

                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
                            PermissionUtils.showCameraDialog(getString(R.string.content_str_read)
                                    ,RegisterActivity.this);
                        } else {

                            PermissionUtils.openSinglePermission(RegisterActivity.this
                                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    , PermissionUtils.CODE_MULTI);

                        }

                    }
                }else{
                    NoteInfoDialog infoDialog = new NoteInfoDialog(mContext,url);
                    infoDialog.show();
                }

            }
        });

    }


    private void getCode(){
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SHOP_SCVAN+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ShopQRCodeBean qrCodeBean = gson.fromJson(gson.toJson(baseModel.getDatas()),ShopQRCodeBean.class);
                    if (!TextUtils.isEmpty(qrCodeBean.getShopQRCode())){
                       url=qrCodeBean.getShopQRCode();
                    }
                }
            }
        });
    }
    public void showStopDialog(final String code) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(RegisterActivity.this,R.style.mydialog).create();
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
        //lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
           if (edt_phone.getText().length()!=0){
               if (edt_phone.getText().length()<11){
                   btn_send_code.setTextColor(getResources().getColor(R.color.color_air_blue));
                   btn_send_code.setClickable(false);
               }else {
                   btn_send_code.setTextColor(getResources().getColor(R.color.colorPrimary));
                   btn_send_code.setClickable(true);
               }
           }else {
               btn_send_code.setTextColor(getResources().getColor(R.color.color_air_blue));
               btn_send_code.setClickable(false);

           }
    }
}
