package com.youzheng.zhejiang.robertmoog.Count.activity;

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
import com.youzheng.zhejiang.robertmoog.Count.adapter.TodayStoreSaleAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.ShopSale;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 今日门店销售额界面
 */
public class TodayStoreSalesActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 请选择时间
     */
    private TextView tv_startDate;
    /**
     * 请选择时间
     */
    private TextView tv_endDate;
    /**
     * 查询
     */
    private TextView tv_check;
    private RecyclerView pr_list;
    /**
     * 800
     */
    private TextView tv_order_total;
    /**
     * 10000
     */
    private TextView tv_order_money;
    /**
     * 2000
     */
    private TextView tv_order_value;
    private List<ShopSale.ShopDataBean> list = new ArrayList<>();
    private TodayStoreSaleAdapter adapter;

    //    private int page=1;
//    private int pageSize=10;
    private boolean isDay = true;
    private String starstDate = "";
    private String endsDate = "";
    private String orderCount, orderAmountCount, customerTransaction;
    private SpringView springView;
    private View no_data,no_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_store_sales);
        initView();
        initData(isDay, starstDate, endsDate);
        //setListener();
    }

//    private void setListener() {
//       pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//           @Override
//           public void onRefresh() {
//               page=1;
//               list.clear();
//               initData(page,pageSize,isDay);
//           }
//
//           @Override
//           public void onLoadMore() {
//               page++;
//               initData(page,pageSize,isDay);
//           }
//       });
//
//    }
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
    private void initView() {
        no_web = findViewById(R.id.no_web);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.today_store_sale_money));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setOnClickListener(this);
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);
        tv_check = (TextView) findViewById(R.id.tv_check);
        tv_check.setOnClickListener(this);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);
        tv_order_total = (TextView) findViewById(R.id.tv_order_total);
        tv_order_money = (TextView) findViewById(R.id.tv_order_money);
        tv_order_value = (TextView) findViewById(R.id.tv_order_value);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(TodayStoreSalesActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));

        adapter = new TodayStoreSaleAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        springView = (SpringView) findViewById(R.id.springView);
//        springView.setHeader(new MyHeader(this));
//        springView.setFooter(new MyFooter(this));
        no_data=findViewById(R.id.no_data);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(boolean isDay, String startDate, String endDate) {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("pageNum",page);
//        map.put("pageSize",pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SHOP_SALE + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
              //  pr_list.setPullLoadMoreCompleted();
               // springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("今日门店销量", response);
               // pr_list.setPullLoadMoreCompleted();
                //springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    ShopSale shopSale = gson.fromJson(gson.toJson(baseModel.getDatas()), ShopSale.class);
                    setData(shopSale);
                } else {
                    showToasts(baseModel.getMsg());
                }

            }
        });
    }

    private void setData(ShopSale shopSale) {
        if (shopSale.getShopData() == null) return;
        orderCount = shopSale.getOrderCount();
        orderAmountCount = shopSale.getOrderAmountCount();
        customerTransaction = shopSale.getCustomerTransaction();

        if (!orderCount.equals("") || orderCount != null) {
            tv_order_total.setText(orderCount);
        }

        if (!orderAmountCount.equals("") || orderAmountCount != null) {
            tv_order_money.setText(orderAmountCount);
        }

        if (!customerTransaction.equals("") || customerTransaction != null) {
            tv_order_value.setText(customerTransaction);
        }

        List<ShopSale.ShopDataBean> beans = shopSale.getShopData();
        if (beans.size() != 0) {
            list.addAll(beans);
            adapter.setUI(list);
            no_data.setVisibility(View.GONE);
            pr_list.setVisibility(View.VISIBLE);
        } else {

            no_data.setVisibility(View.VISIBLE);
            pr_list.setVisibility(View.GONE);

                //showToast(getString(R.string.load_list_erron));

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
            case R.id.tv_startDate:
                break;
            case R.id.tv_endDate:
                break;
            case R.id.tv_check:
                break;
        }
    }
}
