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
import com.youzheng.zhejiang.robertmoog.Count.adapter.TodayGoodsTypeSalesBestDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.GoodsTypeDetail;
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
 * 今日商品品类排名详情界面
 */
public class TodayGoodsTypeSalesBestDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 销售金额
     */
    private TextView tv_rule;
    private LinearLayout lin_title;
    private RecyclerView pr_list;
    private List<GoodsTypeDetail.ProductListBean> list = new ArrayList<>();
    private TodayGoodsTypeSalesBestDetailAdapter adapter;

    private int page = 1;
    private int pageSize = 10;
    private boolean isDay = true;
    private String startstr = "";
    private String endstr = "";
    private int categoryId;
    private String type = "COUNT";
    private SpringView springView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_goods_type_ranking_detail);
        categoryId = getIntent().getIntExtra("todaygoodsId", 0);
        if (categoryId == 0) {
            categoryId = 0;
        }
        initView();
        setListener();
        initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
    }

    private void setListener() {
//        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
//                page++;
//                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
//            }
//        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
            }

            @Override
            public void onLoadmore() {
                page++;
                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
            }
        });


    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_rule = (TextView) findViewById(R.id.tv_rule);
        lin_title = (LinearLayout) findViewById(R.id.lin_title);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(TodayGoodsTypeSalesBestDetailActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));


        // TODO: 2019/1/2 标题判断
        textHeadTitle.setText(getString(R.string.goods_sale_number));
        adapter = new TodayGoodsTypeSalesBestDetailAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        springView = (SpringView) findViewById(R.id.springView);
        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int page, int pageSize, boolean isDay, String startDate, String endDate, int categoryId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("categoryId", categoryId);
        map.put("rule", type);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GOODS_TYPE_RANKING_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //pr_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("今日商品品类排名详情", response);
                //pr_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    GoodsTypeDetail goodsTypeDetail = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsTypeDetail.class);
                    setData(goodsTypeDetail);
                } else {
                    showToast(baseModel.getMsg());
                }
            }
        });


    }

    private void setData(GoodsTypeDetail goodsTypeDetail) {
        if (goodsTypeDetail == null) return;
        if (goodsTypeDetail.getProductList() == null) return;

        List<GoodsTypeDetail.ProductListBean> beanList = goodsTypeDetail.getProductList();
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
}
