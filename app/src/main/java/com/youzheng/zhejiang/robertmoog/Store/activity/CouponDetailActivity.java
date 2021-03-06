package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.CouponDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.CouponDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 优惠券记录使用详情界面
 */
public class CouponDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private RecyclerView lv_list;
    private String date;
    private List<CouponDetail.CouponUsageRecordDetailBean> list = new ArrayList<>();
    private CouponDetailAdapter adapter;
    private View no_data,no_web;
    /**
     * 暂无数据!
     */
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
        date = getIntent().getStringExtra("couponDate");
        initView();
        initData(date);
    }

    private void initView() {
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.coupon_use_record));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        lv_list = (RecyclerView) findViewById(R.id.lv_list);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lv_list.setLayoutManager(manager);
        lv_list.setAdapter(adapter);
        lv_list.addItemDecoration(new RecycleViewDivider(CouponDetailActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        adapter = new CouponDetailAdapter(list, this);
        lv_list.setAdapter(adapter);
        tv_text = (TextView) findViewById(R.id.tv_text);
    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        }else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(String date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("registerDate", date);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.COUPON_RECORD_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //lv_list.setPullLoadMoreCompleted();
            }

            @Override
            public void onResponse(String response) {
                Log.e("优惠券使用详情", response);
                //lv_list.setPullLoadMoreCompleted();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    CouponDetail couponDetail = gson.fromJson(gson.toJson(baseModel.getDatas()), CouponDetail.class);
                    setData(couponDetail);

                } else {
                    showToasts(baseModel.getMsg());
                }
            }

        });

    }

    private void setData(CouponDetail couponDetail) {
        if (couponDetail.getCouponUsageRecordDetail() == null) return;

        List<CouponDetail.CouponUsageRecordDetailBean> beans = couponDetail.getCouponUsageRecordDetail();
        if (beans.size() != 0) {
            list.addAll(beans);
            adapter.refreshUI(list);
            no_data.setVisibility(View.GONE);
            lv_list.setVisibility(View.VISIBLE);
        } else {
            no_data.setVisibility(View.VISIBLE);
            tv_text.setText("暂无优惠券使用信息");
            lv_list.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
