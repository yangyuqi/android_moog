package com.youzheng.zhejiang.robertmoog.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.LoginActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class AlterPasswordActivity extends BaseActivity {

    EditText edt_phone ,edt_password ,edt_again_password ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_password_layout);
        initView();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_phone = findViewById(R.id.edt_phone);
        edt_password = findViewById(R.id.edt_password);
        edt_again_password = findViewById(R.id.edt_again_password);

        findViewById(R.id.tv_confrim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone.getText().toString().equals("")){
                    showToast("请输入原密码");
                    return;
                }
                if (edt_password.getText().toString().equals("")){
                    showToast(mContext.getResources().getString(R.string.login_new_pwd));
                    return;
                }

                if (edt_again_password.getText().toString().equals("")){
                    showToast(mContext.getResources().getString(R.string.login_pwd_again));
                    return;
                }
                if (edt_phone.getText().toString().equals(edt_again_password.getText().toString())){
                    showToast("新密不能与原密码重复");
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("originalPassword", PublicUtils.getSHA256StrJava(edt_phone.getText().toString()));
                map.put("newPassword",PublicUtils.getSHA256StrJava(edt_password.getText().toString()));
                map.put("confirmNewPassword",PublicUtils.getSHA256StrJava(edt_again_password.getText().toString()));
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CHANGE_PSW + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            startActivity(new Intent(mContext,LoginActivity.class));
                            finish();
                        }else {
                            showToast(baseModel.getMsg());
                        }
                    }
                });
            }
        });
    }
}
