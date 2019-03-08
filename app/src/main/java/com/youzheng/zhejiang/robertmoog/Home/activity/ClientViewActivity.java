package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.PhoneUtil;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import rx.functions.Action1;

public class ClientViewActivity extends BaseActivity implements TextWatcher {

    EditText tv_search;
    ImageView iv_click;
    private ImageView iv_clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_view_layout);

        initView();
    }

    private void initView() {
        findViewById(R.id.iv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ScanSellerActivity.class));
            }
        });
        ((TextView) findViewById(R.id.textHeadTitle)).setText("客户识别");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_click = findViewById(R.id.iv_click);
        tv_search = findViewById(R.id.tv_search);

        iv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_search.getText().toString().equals("")) {
                    showToasts(getString(R.string.login_input_phone));
                    return;

                } else if (PhoneUtil.isCellphone(tv_search.getText().toString()) == false) {
                    showToasts("手机号码格式不正确");
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("phone", tv_search.getText().toString());
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_CUSTOMER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            final BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                            if (baseModel.getCode() == PublicUtils.code) {

                                RxPermissions permissions = new RxPermissions(ClientViewActivity.this);
                                permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean aBoolean) {
                                        if (aBoolean) {
                                            Intent intent = new Intent(mContext, CaptureActivity.class);
                                            CustomerBean customerBean = gson.fromJson(gson.toJson(baseModel.getDatas()), CustomerBean.class);
                                            intent.putExtra("customerId", customerBean.getCustomer().getCustomerId());
                                            startActivity(intent);
                                        }
                                    }
                                });

                            } else {
                                //showToasts(baseModel.getMsg());
                                if (baseModel.getCode() == PublicUtils.no_exist) {
                                    final RemindDialog dialog = new RemindDialog(mContext, new RemindDialog.onSuccessClick() {
                                        @Override
                                        public void onSuccess() {
                                            Intent intent = new Intent(mContext, RegisterActivity.class);
                                            intent.putExtra("no_phone", tv_search.getText().toString());
                                            startActivity(intent);
                                        }
                                    }, "2");
                                    dialog.show();
                                }else {
                                    showToasts(baseModel.getMsg());
                                }
                            }
                        }
                    });
                }

            }
        });
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });
        tv_search.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (tv_search.length()==0){
            iv_clear.setVisibility(View.GONE);
        }else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
}
