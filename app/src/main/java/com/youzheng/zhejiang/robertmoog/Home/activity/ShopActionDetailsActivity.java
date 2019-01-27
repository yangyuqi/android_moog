package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CustomerGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ShopActionAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.PromoIdDetails;
import com.youzheng.zhejiang.robertmoog.Model.Home.PromoIdDetailsData;
import com.youzheng.zhejiang.robertmoog.R;
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

    private int promoId ;

    private NoScrollListView ls ;
    TextView tv_name ,tv_start_time ,tv_desc ,tv_end_time;
    CommonAdapter<String> adapter ;
    List<String> data = new ArrayList<>();

    RecyclerView recycler_view ;
    ShopActionAdapter shop_adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_action_details_layout);
        promoId = getIntent().getIntExtra("promoId",0);
        initView();
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("promoId",promoId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ACTION_LIST_DETAILS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()== PublicUtils.code){
                    PromoIdDetailsData promoIdDetails = gson.fromJson(gson.toJson(baseModel.getDatas()),PromoIdDetailsData.class);
                    tv_name.setText(promoIdDetails.getData().getPromoName());
                    tv_start_time.setText(promoIdDetails.getData().getStartTime());
                    tv_end_time.setText(promoIdDetails.getData().getEndTime());
                    tv_desc.setText(promoIdDetails.getData().getActivityAbstract());
                    if (promoIdDetails.getData().getOrderPromo().size()>0){
                        adapter.setData(promoIdDetails.getData().getOrderPromo());
                        adapter.notifyDataSetChanged();
                    }
                    if (promoIdDetails.getData().getComboPromo().size()>0){
                        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        recycler_view.setLayoutManager(manager);
                        shop_adapter = new ShopActionAdapter(promoIdDetails.getData().getComboPromo(),mContext );
                        recycler_view.setAdapter(shop_adapter);
                        recycler_view.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
                    }
                }
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("促销活动");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);

        ls = findViewById(R.id.ls);
        tv_desc = findViewById(R.id.tv_desc);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_name = findViewById(R.id.tv_name);
        tv_end_time = findViewById(R.id.tv_end_time);
        adapter = new CommonAdapter<String>(mContext,data,R.layout.shop_details_ls_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.tv_activity,item);
            }
        };
        ls.setAdapter(adapter);

        recycler_view = findViewById(R.id.recycler_view);


    }
}
