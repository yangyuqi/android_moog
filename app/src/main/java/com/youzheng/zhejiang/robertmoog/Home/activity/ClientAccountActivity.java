package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CouponAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListClient;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class ClientAccountActivity extends BaseActivity {
    private RecyclerView recyclerView;
    CouponAdapter addapter;
    String customerId;
    String pageNum = "1", pageSize = "30";
    ArrayList<CouponListBean> data = new ArrayList<>();
    private View no_data, no_web;
    /**
     * 暂无数据!
     */
    private TextView tv_text;
    private RelativeLayout layout_header;
    // private SpringView springView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_account_layout);

        initView();

        initData();

    }


    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("customerId", customerId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CLIENT_ACCOUNT + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    CouponListClient couponListClient = gson.fromJson(gson.toJson(baseModel.getDatas()), CouponListClient.class);
                    if (couponListClient.getCouponList().size() > 0) {
                        addapter.setData(couponListClient.getCouponList(), mContext, "3");
                        no_data.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        no_data.setVisibility(View.VISIBLE);
                        tv_text.setText("暂无优惠券");
                        recyclerView.setVisibility(View.GONE);
                        // showToast(getString(R.string.load_list_erron));
                    }
                } else {
                    if (!TextUtils.isEmpty(baseModel.getMsg())) {
                        showToasts(baseModel.getMsg());
                    }
                }
            }
        });
    }

    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status == -1) {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        } else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }
    }


    private void initView() {
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        ((TextView) findViewById(R.id.textHeadTitle)).setText("客户账户");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customerId = getIntent().getStringExtra("customerId");


        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        addapter = new CouponAdapter();
        recyclerView.setAdapter(addapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
        addapter.setData(data, ClientAccountActivity.this, "3");

        tv_text = (TextView) findViewById(R.id.tv_text);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
    }
}
