package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.utils.PhoneUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class ReturnRecognitionActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 输入客户手机号,识别后快速进入服务
     */
    private EditText tv_search;
    private ImageView iv_search;
    private ImageView iv_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_recognition);
        initView();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.customer_recognition));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_search = (EditText) findViewById(R.id.tv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.iv_search:
                SoftInputUtils.hideSoftInput(ReturnRecognitionActivity.this);
                if (tv_search.getText().toString().equals("")) {
                    showToasts(getString(R.string.login_input_phone));
                } else if (tv_search.getText().toString().length() < 11) {
                    showToasts("手机号码不正确");
                } else if (PhoneUtil.isCellphone(tv_search.getText().toString()) == false) {
                    showToasts("手机号码格式不正确");
                } else {
                    Recognition();
                }

                break;
        }
    }

    private void Recognition() {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", tv_search.getText().toString());
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_CUSTOMER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    CustomerBean customerBean = gson.fromJson(gson.toJson(baseModel.getDatas()), CustomerBean.class);
                    if (!TextUtils.isEmpty(customerBean.getCustomer().getCustomerId())) {
                        Intent intent6 = new Intent(mContext, ChooseOrderListActivity.class);
                        intent6.putExtra("customerId", customerBean.getCustomer().getCustomerId());
                        intent6.putExtra("identifion", true);
                        startActivity(intent6);
                    }

                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });
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
