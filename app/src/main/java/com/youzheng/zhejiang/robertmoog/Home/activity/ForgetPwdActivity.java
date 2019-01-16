package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.app.AlertDialog;
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
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.Code;
import com.youzheng.zhejiang.robertmoog.utils.View.MyCountDownLoginTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class ForgetPwdActivity extends BaseActivity {

    EditText edt_phone ,edt_code ,edt_password ,edt_again_password ;
    TextView tv_confrim ;
    private MyCountDownLoginTimer timer ;
    Button btn_send_code ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pwd_layout);

        initView();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edt_phone =findViewById(R.id.edt_phone);
        edt_code = findViewById(R.id.edt_code);
        edt_password = findViewById(R.id.edt_password);
        edt_again_password = findViewById(R.id.edt_again_password);
        tv_confrim = findViewById(R.id.tv_confrim);
        btn_send_code = findViewById(R.id.btn_send_code);
        timer = new MyCountDownLoginTimer(btn_send_code,60000,1000);

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

        tv_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone.getText().toString().equals("")){
                    showToast(getString(R.string.phone_not_null));
                    return;
                }
                if (edt_code.getText().toString().equals("")){
                    showToast("验证码不能为空");
                    return;
                }
                if (edt_password.getText().toString().equals("")) {
                    showToast(getString(R.string.pwd_not_null));
                    return;
                }
                if (!edt_password.getText().toString().equals(edt_again_password.getText().toString())){
                    showToast("两次密码不一致");
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("phone",edt_phone.getText().toString());
                map.put("checkCode",edt_code.getText().toString());
                map.put("newPassword",PublicUtils.getSHA256StrJava(edt_password.getText().toString()));
                map.put("confirmNewPassword",PublicUtils.getSHA256StrJava(edt_again_password.getText().toString()));
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.FORGET_PASSWORD, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        showToast(baseModel.getMsg());
                    }
                });
            }
        });
    }

    public void showStopDialog(final String code) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(ForgetPwdActivity.this).create();
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
