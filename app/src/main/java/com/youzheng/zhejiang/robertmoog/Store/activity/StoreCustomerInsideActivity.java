package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.activity.StoreSaleInsideActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.StoreCustomerInsideAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.StoreCustomerDetail;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 门店客户点击列表界面
 */
public class StoreCustomerInsideActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private RecyclerView lv_list;
    private List<StoreCustomerDetail.MonthCoustomerDetailBean> list = new ArrayList<>();
    private StoreCustomerInsideAdapter adapter;
    private String month;
    private int pageSize = 10;
    private int page = 1;
    private SpringView mSpringView;
    private View no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_customer_inside);
        initView();
        setListener();
        initData(page, pageSize);
    }

    private void setListener() {
//        lv_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize);
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
//                page++;
//                initData(page, pageSize);
//            }
//        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize);
            }

            @Override
            public void onLoadmore() {
               // list.clear();
                page++;
                initData(page, pageSize);
            }
        });
    }

    private void initView() {
        no_data=findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.store_customer));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        lv_list = (RecyclerView) findViewById(R.id.lv_list);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lv_list.setLayoutManager(manager);
        lv_list.setAdapter(adapter);
        lv_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(StoreCustomerInsideActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        adapter = new StoreCustomerInsideAdapter(list, this);
        lv_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        month = getIntent().getStringExtra("month");


        mSpringView = (SpringView) findViewById(R.id.springView);

        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int page, int pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("registerDate", month);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_CUSTOMER_LIST_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //lv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("门店客户详情", response);
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    StoreCustomerDetail storeCustomerDetail = gson.fromJson(gson.toJson(baseModel.getDatas()), StoreCustomerDetail.class);
                    setData(storeCustomerDetail);
                   // lv_list.setPullLoadMoreCompleted();
                } else {
                    showToast(baseModel.getMsg());
                }
            }

        });

    }

    private void setData(StoreCustomerDetail storeCustomerDetail) {
        if (storeCustomerDetail.getMonthCoustomerDetail() == null) return;
        List<StoreCustomerDetail.MonthCoustomerDetailBean> monthCoustomerDetailBeans = storeCustomerDetail.getMonthCoustomerDetail();
        if (monthCoustomerDetailBeans.size() != 0) {
            list.addAll(monthCoustomerDetailBeans);
            adapter.refreshUI(monthCoustomerDetailBeans);
            no_data.setVisibility(View.GONE);
            mSpringView.setVisibility(View.VISIBLE);
        } else {
            if (page==1){
                no_data.setVisibility(View.VISIBLE);
                mSpringView.setVisibility(View.GONE);
            }else {
                showToast(getString(R.string.load_list_erron));
            }
        }


    }

    @Override
    public void onClick(View v) {
        finish();
    }


}
