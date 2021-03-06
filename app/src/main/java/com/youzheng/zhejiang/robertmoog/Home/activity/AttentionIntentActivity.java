package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ViewHolder;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.NotLabelList;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalList;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalListBean;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


public class AttentionIntentActivity extends BaseActivity {

    ListView ls;
    CommonAdapter<ShopPersonalListBean> adapter;
    List<String> data = new ArrayList<>();
    TabLayout tabLayout;

    int layout_id;
    private View no_data, no_web;
    private int tabposition;
    /**
     * 暂无数据!
     */
    private TextView tv_text;
    private RelativeLayout layout_header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_intent_layout);
        if (access_token.equals("")) {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
            return;
        }

        ((TextView) findViewById(R.id.textHeadTitle)).setText("门店员工");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab);
        ls = (ListView) findViewById(R.id.ls);
        no_data = findViewById(R.id.no_data);
        no_web = findViewById(R.id.no_web);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tabLayout.getTabAt(0).select();
//        if (role.equals(PublicUtils.SHOP_SELLER)){
//            tabLayout.setVisibility(View.GONE);
//            startActivity(new Intent(mContext,AttentionManagerActivity.class));
//            finish();
//        }else if (role.equals(PublicUtils.SHOP_LEADER)){
//            tabLayout.setVisibility(View.VISIBLE);
//            initData(0);
//        }
        initEvent();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (role.equals(PublicUtils.SHOP_SELLER)) {
            tabLayout.setVisibility(View.GONE);
            startActivity(new Intent(mContext, AttentionManagerActivity.class));
            finish();
        } else if (role.equals(PublicUtils.SHOP_LEADER)) {
            tabLayout.setVisibility(View.VISIBLE);
            Log.e("eqeq", tabposition + "");
            data.clear();
            initData(tabposition);
        }
    }

    private void initData(final int i) {
        if (i == 0) {
            OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SHOP_PERSONAL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        ShopPersonalList list = gson.fromJson(gson.toJson(baseModel.getDatas()), ShopPersonalList.class);
                        initView(i, list.getShopPersonalList());
                    }
                }
            });
        } else if (i == 1) {
            OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.NOT_LABEEL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        NotLabelList list = gson.fromJson(gson.toJson(baseModel.getDatas()), NotLabelList.class);
                        initView(i, list.getNotLabelList());
                    }
                }
            });
        }


    }

    private void initEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabposition = tab.getPosition();
                Log.e("sadwa", tab.getPosition() + "");
                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView(final int i, List<ShopPersonalListBean> list) {

        if (i == 0) {
            layout_id = R.layout.attention_intent_layout_item;
        } else if (i == 1) {
            layout_id = R.layout.attention_intent_layout_other_item;
        }

        if (list.size() == 0) {
            if (i == 0) {
                no_data.setVisibility(View.VISIBLE);
                tv_text.setText("暂无意向信息");
                ls.setVisibility(View.GONE);
            } else {
                no_data.setVisibility(View.VISIBLE);
                tv_text.setText("暂无未标记客户");
                ls.setVisibility(View.GONE);
            }

        } else {
            no_data.setVisibility(View.GONE);
            ls.setVisibility(View.VISIBLE);
        }

        adapter = new CommonAdapter<ShopPersonalListBean>(mContext, list, layout_id) {
            @Override
            public void convert(ViewHolder helper, final ShopPersonalListBean item) {
                if (i == 0) {
                    helper.setText(R.id.tv_name, item.getName() + "  (" + item.getBusinessRole() + ")");
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, AttentionManagerActivity.class);
                            intent.putExtra("label", item);
                            startActivity(intent);
                        }
                    });

                } else if (i == 1) {
                    helper.setText(R.id.tv_time, item.getCreateDate());
                    helper.setText(R.id.tv_phone, item.getCustCode());
                    helper.setText(R.id.tv_from, item.getChannel());
                    helper.getView(R.id.rv_click).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, AttentionGoodsActivity.class);
                            intent.putExtra("label", item);
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        ls.setAdapter(adapter);


    }
}
