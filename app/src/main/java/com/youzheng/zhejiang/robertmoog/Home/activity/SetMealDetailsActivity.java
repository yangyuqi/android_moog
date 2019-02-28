package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.HomeListDataBeanBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.MealDataS;
import com.youzheng.zhejiang.robertmoog.Model.Home.ProductListData;
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

public class SetMealDetailsActivity extends BaseActivity {

    private Integer comId;
    NoScrollListView ls, ls_two;
    List<HomeListDataBeanBean> data = new ArrayList<>();
    CommonAdapter<HomeListDataBeanBean> adapter;
    List<ProductListData> productListDataList = new ArrayList<>();
    CommonAdapter<ProductListData> commonAdapter;

    TextView tv_name, tv_des;
    /**  */
    private TextView tv_como_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_meal_details_layout);
        initView();
        comId = getIntent().getIntExtra("id", 0);
        initData();
    }

    private void initView() {
        ((TextView) findViewById(R.id.textHeadTitle)).setText("套餐详情");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ls = findViewById(R.id.ls);
        ls_two = findViewById(R.id.ls_two);
        adapter = new CommonAdapter<HomeListDataBeanBean>(mContext, data, R.layout.shop_details_ls_item) {
            @Override
            public void convert(ViewHolder helper, HomeListDataBeanBean item) {
                helper.setText(R.id.tv_activity, item.getName());
                helper.setText(R.id.tv_price, "¥" + item.getPrice());
            }
        };
        ls.setAdapter(adapter);

        commonAdapter = new CommonAdapter<ProductListData>(mContext, productListDataList, R.layout.goods_ls_item) {
            @Override
            public void convert(ViewHolder helper, ProductListData item) {
                helper.setText(R.id.tv_name, item.getSkuId());
                helper.setText(R.id.tv_desc, item.getName());
                helper.setText(R.id.tv_price, "¥" + item.getPrice());
                helper.setText(R.id.tv_confirm, "X" + item.getNum());
                Glide.with(mContext).load(item.getImtUrl()).error(R.mipmap.type_icon).into((ImageView) helper.getView(R.id.iv_icon));
            }
        };
        ls_two.setAdapter(commonAdapter);
        tv_des = findViewById(R.id.tv_des);
        tv_name = findViewById(R.id.tv_name);
        tv_como_name = (TextView) findViewById(R.id.tv_como_name);
    }

    private void initData() {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", comId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.NEW_TAOCAN_DETAILS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    MealDataS mealDataS = gson.fromJson(gson.toJson(baseModel.getDatas()), MealDataS.class);
                    tv_name.setText("" + mealDataS.getMealData().getComboCode());
                    tv_como_name.setText("" +mealDataS.getMealData().getComboName());
                    tv_des.setText("" + mealDataS.getMealData().getComboDescribe());
                    if (mealDataS.getMealData().getList().size() > 0) {
                        adapter.setData(mealDataS.getMealData().getList());
                        adapter.notifyDataSetChanged();
                    }
                    if (mealDataS.getMealData().getProductList().size() > 0) {
                        commonAdapter.setData(mealDataS.getMealData().getProductList());
                        commonAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
