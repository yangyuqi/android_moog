package com.youzheng.zhejiang.robertmoog.Count.activity;

import android.content.Intent;
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
import com.youzheng.zhejiang.robertmoog.Count.adapter.TodayGoodsTypeSalesBestAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.GoodsTypeRankingList;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class TodayGoodsTypeSaleBestActivity extends BaseActivity implements View.OnClickListener, OnRecyclerViewAdapterItemClickListener {

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
    private TodayGoodsTypeSalesBestAdapter adapter;
    private List<GoodsTypeRankingList.CategoryListBean> list = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;
    private boolean isDay = true;
    private String startstr = "";
    private String endstr = "";
    private String rulestr = "COUNT";//默认是数量
    private SpringView mSpringView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_goods_type_sale_best);
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
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
//                page++;
//                initData(page, pageSize, isDay, startstr, endstr, rulestr);
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

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.today_type_ranking));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_rule = (TextView) findViewById(R.id.tv_rule);
        lin_title = (LinearLayout) findViewById(R.id.lin_title);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(TodayGoodsTypeSaleBestActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));

        adapter = new TodayGoodsTypeSalesBestAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(this);

        mSpringView = (SpringView) findViewById(R.id.springView);

        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int page, int pageSize, boolean isDay, String startDate, String endDate, String rule) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("rule", rule);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GOODS_TYPE_RANKING_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("今日商品品类排名", response);
               // pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    GoodsTypeRankingList goodsTypeRankingList = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsTypeRankingList.class);
                    setData(goodsTypeRankingList);
                } else {
                    showToast(baseModel.getMsg());
                }
            }
        });
    }

    private void setData(GoodsTypeRankingList goodsTypeRankingList) {
        if (goodsTypeRankingList == null) return;
        if (goodsTypeRankingList.getCategoryList() == null) return;

        List<GoodsTypeRankingList.CategoryListBean> beanList = goodsTypeRankingList.getCategoryList();
        if (beanList.size() != 0) {
            list.addAll(beanList);
            adapter.setUI(beanList);
        } else {
            showToast(getString(R.string.load_list_erron));
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
        Intent intent = new Intent(this, TodayGoodsTypeSalesBestDetailActivity.class);
        intent.putExtra("todaygoodsId", list.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
