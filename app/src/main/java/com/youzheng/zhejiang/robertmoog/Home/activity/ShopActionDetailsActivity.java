package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CouponActionAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ShopActionAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.PromoIdDetailsData;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.utils.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.utils.View.NoScrollListView;
import com.youzheng.zhejiang.robertmoog.utils.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class ShopActionDetailsActivity extends BaseActivity {

    private int promoId;

    private NoScrollListView ls;
    TextView tv_name, tv_start_time, tv_desc, tv_end_time, tv_coupon;
    CommonAdapter<String> adapter;
    List<String> data = new ArrayList<>();

    RecyclerView recycler_view, rv_coupon;
    ShopActionAdapter shop_adapter;
    private String codeid;
    private TextView tv_goods, tv_meal;
    private CouponActionAdapter actionAdapter;
    private LinearLayout lin_event;
    /**
     * 请店员引导消费者到店扫描门店张贴二维码领
     */
    private TextView tv_text;
    private View no_data, no_web;
    private RelativeLayout layout_header;
    private View head;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_action_details_layout);
        promoId = getIntent().getIntExtra("promoId", 0);
        initView();
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("promoId", promoId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ACTION_LIST_DETAILS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    PromoIdDetailsData promoIdDetails = gson.fromJson(gson.toJson(baseModel.getDatas()), PromoIdDetailsData.class);
                    tv_name.setText(promoIdDetails.getData().getPromoName());
                    tv_start_time.setText(promoIdDetails.getData().getStartTime());
                    tv_end_time.setText(promoIdDetails.getData().getEndTime());
                    if (!TextUtils.isEmpty(promoIdDetails.getData().getActivityAbstract())) {
                        tv_desc.setText(promoIdDetails.getData().getActivityAbstract());
                        lin_event.setVisibility(View.VISIBLE);
                    } else {
                        lin_event.setVisibility(View.GONE);
                    }

                    codeid = promoIdDetails.getData().getPromoId();
                    if (!TextUtils.isEmpty(promoIdDetails.getData().getType())) {
                        tv_goods.setText(promoIdDetails.getData().getType());
                    }
                    if (promoIdDetails.getData().getOrderPromo().size() > 0) {
                        adapter.setData(promoIdDetails.getData().getOrderPromo());
                        adapter.notifyDataSetChanged();
                        // tv_goods.setVisibility(View.VISIBLE);
                        ls.setVisibility(View.VISIBLE);

                    } else {
                        // tv_goods.setVisibility(View.GONE);
                        ls.setVisibility(View.GONE);

                    }
                    if (promoIdDetails.getData().getComboPromo().size() > 0) {
                        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
                        // tv_meal.setVisibility(View.VISIBLE);
                        recycler_view.setVisibility(View.VISIBLE);
                        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        recycler_view.setLayoutManager(manager);
                        shop_adapter = new ShopActionAdapter(promoIdDetails.getData().getComboPromo(), mContext);
                        recycler_view.setAdapter(shop_adapter);
                        recycler_view.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
                    } else {
                        //tv_meal.setVisibility(View.GONE);
                        recycler_view.setVisibility(View.VISIBLE);
                        findViewById(R.id.iv_next).setVisibility(View.GONE);
                    }

                    if (promoIdDetails.getData().getCouponPromo().size() > 0) {
                        //tv_coupon.setVisibility(View.VISIBLE);
                        tv_text.setVisibility(View.VISIBLE);
                        rv_coupon.setVisibility(View.VISIBLE);
                        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        rv_coupon.setLayoutManager(manager);
                        actionAdapter = new CouponActionAdapter(promoIdDetails.getData().getCouponPromo(), mContext);
                        rv_coupon.setAdapter(actionAdapter);
                        rv_coupon.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
                    } else {
                        //tv_coupon.setVisibility(View.GONE);
                        tv_text.setVisibility(View.GONE);
                        rv_coupon.setVisibility(View.GONE);
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
        } else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }
    }

    private void initView() {
//        head=findViewById(R.id.head);
//        tv_text=head.findViewById(R.id.tv_text);
        tv_text = (TextView) findViewById(R.id.tv_text);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        no_web = findViewById(R.id.no_web);
        tv_goods = findViewById(R.id.tv_goods);
        tv_meal = findViewById(R.id.tv_meal);
        lin_event = findViewById(R.id.lin_event);
        ((TextView) findViewById(R.id.textHeadTitle)).setText("促销活动");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(ShopActionDetailsActivity.this);
                Intent intent = new Intent(ShopActionDetailsActivity.this, SearchMealInfoActivity.class);
                intent.putExtra("codeid", codeid);
                startActivity(intent);

            }
        });
        ls = findViewById(R.id.ls);
        tv_desc = findViewById(R.id.tv_desc);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_name = findViewById(R.id.tv_name);
        tv_end_time = findViewById(R.id.tv_end_time);
        tv_coupon = findViewById(R.id.tv_coupon);
        rv_coupon = findViewById(R.id.rv_coupon);
        adapter = new CommonAdapter<String>(mContext, data, R.layout.shop_details_ls_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.tv_activity, item);
            }
        };
        ls.setAdapter(adapter);

        recycler_view = findViewById(R.id.recycler_view);



    }
}
