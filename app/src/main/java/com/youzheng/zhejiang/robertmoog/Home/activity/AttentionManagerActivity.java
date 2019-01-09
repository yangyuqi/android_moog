package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CustomerGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ViewHolder;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerIntentDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerIntentListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.NotLabelList;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalListBean;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class AttentionManagerActivity extends BaseActivity {

    private ShopPersonalListBean listBean ;
    private TabLayout tabLayout ;
    private String personalId ,phone;
    private int pageNum = 1 ,pageSize=20;
    Map<String,Object> map = new HashMap<>();
    RecyclerView recyclerView ;
    ListView ls ;
    List<CustomerIntentListBean> data = new ArrayList<>();
    CustomerGoodsAdapter adapter ;
    int widWidth ;
    EditText tv_search ;
    CommonAdapter<ShopPersonalListBean> com_adapter ;
    List<ShopPersonalListBean> da_list = new ArrayList<>();
    ImageView iv_search ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_manager_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        initView();
        initData();

        initClick();
    }

    private void initClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    recyclerView.setVisibility(View.VISIBLE);
                    ls.setVisibility(View.GONE);
                    findViewById(R.id.rl_search).setVisibility(View.VISIBLE);
                    initData();
                }else if (tab.getPosition()==1){
                    recyclerView.setVisibility(View.GONE);
                    ls.setVisibility(View.VISIBLE);
                    findViewById(R.id.rl_search).setVisibility(View.GONE);
                    refreshData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void refreshData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.NOT_LABEEL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()== PublicUtils.code){
                    NotLabelList list = gson.fromJson(gson.toJson(baseModel.getDatas()),NotLabelList.class);
                    com_adapter.setData(list.getNotLabelList());
                    com_adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initData() {
        if (personalId!=null){
            map.put("personalId",personalId);
        }
        if (phone!=null){
            map.put("phone",phone);
        }
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CUSTOMER_INTENT + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    CustomerIntentDatas datas = gson.fromJson(gson.toJson(baseModel.getDatas()),CustomerIntentDatas.class);
                    if (datas.getCustomerIntentList().size()>0){
                        List<CustomerIntentListBean> customerList = datas.getCustomerIntentList();
                        for (int  i = 0 ; i<customerList.size();i++){
                            if (customerList.get(i).getProductList().size()>0) {
                                customerList.get(i).setGoodsId(customerList.get(i).getProductList().get(0).getId());
                                customerList.get(i).setPhoto(customerList.get(i).getProductList().get(0).getPhoto());
                                customerList.get(i).setName(customerList.get(i).getProductList().get(0).getName());
                                customerList.get(i).setSku(customerList.get(i).getProductList().get(0).getSku());
                                customerList.get(i).setCreateDate(customerList.get(i).getProductList().get(0).getCreateDate());
                            }
                        }
                        adapter.setData(customerList,mContext);
                    }else {
                        adapter.setData(new ArrayList<CustomerIntentListBean>(),mContext);
                    }
                }
            }
        });
    }

    private void initView() {
        listBean = (ShopPersonalListBean) getIntent().getSerializableExtra("label");
        if (listBean!=null){
            personalId = listBean.getId();
        }
        tabLayout = findViewById(R.id.tab);
        ((TextView)findViewById(R.id.textHeadTitle)).setText(R.string.attention_intent_user);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new CustomerGoodsAdapter(data,mContext ,widWidth);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
        if (role.equals(PublicUtils.SHOP_SELLER)){
            tabLayout.setVisibility(View.VISIBLE);
        }else if (role.equals(PublicUtils.SHOP_LEADER)){
            tabLayout.setVisibility(View.GONE);
        }

        ls = findViewById(R.id.ls);

        com_adapter = new CommonAdapter<ShopPersonalListBean>(mContext,da_list, R.layout.attention_intent_layout_other_item) {
            @Override
            public void convert(ViewHolder helper, final ShopPersonalListBean item) {

                    helper.setText(R.id.tv_time,item.getCreateDate());
                    helper.setText(R.id.tv_phone,item.getCustCode());
                    helper.setText(R.id.tv_from,item.getChannel());
                    helper.getView(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,AttentionGoodsActivity.class);
                            intent.putExtra("label",item);
                            startActivity(intent);
                        }
                    });

            }
        };
        ls.setAdapter(com_adapter);

        tv_search = findViewById(R.id.tv_search);
        iv_search = findViewById(R.id.iv_search);

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = tv_search.getText().toString();
                initData();
            }
        });
    }
}
