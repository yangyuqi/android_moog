package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.CouponRecordAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.CouponRecord;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 优惠券使用记录界面
 */
public class CouponRecordActivity extends BaseActivity implements View.OnClickListener, OnRecyclerViewAdapterItemClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**  */
    private TextView tv_number;
    private RecyclerView lv_list;
    private List<CouponRecord.OrderMonthDataListBean> list = new ArrayList<>();
    private CouponRecordAdapter adapter;
    private int year;
    private Calendar selectedDate;
    private SpringView mSpringView;
    private View no_data,no_web;
    /**
     * 暂无数据!
     */
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_record);
        initView();
        selectedDate = Calendar.getInstance();
        //获取系统的日期
        //年
        year = selectedDate.get(Calendar.YEAR);
        setListener();
        initData(year);
    }

    private void setListener() {
//        lv_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                if (year == selectedDate.get(Calendar.YEAR)) {
//                    list.clear();
//                    initData(year);
//                } else {
//                    year++;
//                    list.clear();
//                    initData(year);
//                }
//            }
//
//            @Override
//            public void onLoadMore() {
//                year = year - 1;
//                list.clear();
//                initData(year);
//            }
//        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (year == selectedDate.get(Calendar.YEAR)) {
                    list.clear();
                    initData(year);
                } else {
                    year++;
                    initData(year);
                }
            }

            @Override
            public void onLoadmore() {
//                year = year - 1;
                if (year==2019){
                    mSpringView.onFinishFreshAndLoad();
                    showToasts(getString(R.string.load_list_erron));
                }else {
                    year=year-1;
                    initData(year);
                }

            }
        });

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
        tv_number = (TextView) findViewById(R.id.tv_number);
        lv_list = (RecyclerView) findViewById(R.id.lv_list);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lv_list.setLayoutManager(manager);
        lv_list.setAdapter(adapter);
        lv_list.addItemDecoration(new RecycleViewDivider(CouponRecordActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));

        adapter = new CouponRecordAdapter(list, this);
        lv_list.setAdapter(adapter);

        adapter.setOnItemClickListener(this);


        mSpringView = (SpringView) findViewById(R.id.springView);
        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));
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

    private void initData(final int year) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("year", year);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.COUPON_RECORD_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //lv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("优惠券使用列表", response);
                //lv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    CouponRecord couponRecord = gson.fromJson(gson.toJson(baseModel.getDatas()), CouponRecord.class);
                    setData(couponRecord);
                } else {
                    showToasts(baseModel.getMsg());

                }
            }
        });


    }

    private void setData(CouponRecord couponRecord) {
        if (couponRecord.getOrderMonthDataList() == null) return;

        if (!couponRecord.getTotalAmount().equals("") || couponRecord.getTotalAmount() != null) {
            tv_number.setText(couponRecord.getTotalAmount());
        } else {
            tv_number.setText("0");
        }

        List<CouponRecord.OrderMonthDataListBean> beanList = couponRecord.getOrderMonthDataList();
        if (beanList!=null){
            if (beanList.size() != 0) {
                list.clear();
                list.addAll(beanList);
                adapter.setListRefreshUi(list);
                no_data.setVisibility(View.GONE);
                mSpringView.setVisibility(View.VISIBLE);
            } else {

                    no_data.setVisibility(View.VISIBLE);
                    tv_text.setText("暂无优惠券使用信息");
                    mSpringView.setVisibility(View.GONE);



            }
        }else {
            no_data.setVisibility(View.VISIBLE);
            tv_text.setText("暂无优惠券使用信息");
            mSpringView.setVisibility(View.GONE);
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


    @Override
    public void onItemClick(View view, int position) {
        String date = list.get(position).getMonth();
        Intent intent = new Intent(this, CouponDetailActivity.class);
        intent.putExtra("couponDate", date);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
