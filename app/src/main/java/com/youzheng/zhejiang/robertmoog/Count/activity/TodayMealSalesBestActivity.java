package com.youzheng.zhejiang.robertmoog.Count.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.adapter.TodayMealSalesBestAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.MealRankingList;
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
 * 今日套餐销量最佳界面
 */
public class TodayMealSalesBestActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 销售数量
     */
    private TextView tv_rule;
    private LinearLayout lin_title;
    private RecyclerView pr_list;
    private TodayMealSalesBestAdapter adapter;
    private List<MealRankingList.SetMealListBean> list = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;
    private boolean isDay = true;
    private String startstr = "";
    private String endstr = "";
    private String rulestr = "COUNT";//默认是数量
    private SpringView mSpringView;

    private View no_data,no_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_meal_sales_best);
        initView();
        setListener();
        initData(page, pageSize, isDay, startstr, endstr, rulestr);
    }

    private void setListener() {
//        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize, isDay, startstr, endstr, rulestr);
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
//                page++;
//                initData(page, pageSize, isDay, startstr, endstr, rulestr);
//
//            }
//        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize, isDay, startstr, endstr, rulestr);
            }

            @Override
            public void onLoadmore() {
                //list.clear();
                page++;
                initData(page, pageSize, isDay, startstr, endstr, rulestr);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
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
        textHeadTitle.setText(getString(R.string.today_meal_ranking));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_rule = (TextView) findViewById(R.id.tv_rule);
        lin_title = (LinearLayout) findViewById(R.id.lin_title);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(TodayMealSalesBestActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));

        adapter = new TodayMealSalesBestAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mSpringView = (SpringView) findViewById(R.id.springView);

        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));
        no_data=findViewById(R.id.no_data);
    }

    private void initData(int page, int pageSize, boolean isDay, String startDate, String endDate, String rule) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("rule", rule);


        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.MEAL_RANKING_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
               // pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("套餐排名", response);
               // pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    MealRankingList mealRankingList = gson.fromJson(gson.toJson(baseModel.getDatas()), MealRankingList.class);
                    setData(mealRankingList);
                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });

    }

    private void setData(MealRankingList mealRankingList) {
        if (mealRankingList == null) return;
        if (mealRankingList.getSetMealList() == null) return;
        List<MealRankingList.SetMealListBean> beanList = mealRankingList.getSetMealList();
        if (beanList.size() != 0) {
            list.addAll(beanList);
            adapter.setUI(beanList);
            no_data.setVisibility(View.GONE);
            mSpringView.setVisibility(View.VISIBLE);
        } else {
            if (page==1){
                no_data.setVisibility(View.VISIBLE);
                mSpringView.setVisibility(View.GONE);
            }else {
                showToasts(getString(R.string.load_list_erron));
            }
        }
       // pr_list.setPullLoadMoreCompleted();

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
