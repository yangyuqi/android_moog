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
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.adapter.TodaySingeleGoodSalesBestDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.GoodsSale;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 今日单品销售最佳界面
 */
public class TodaySingleGoodsSalesBestDetailActivity extends BaseActivity implements View.OnClickListener {

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
    private List<GoodsSale.ProductListBean> list = new ArrayList<>();
    private TodaySingeleGoodSalesBestDetailAdapter adapter;

    private int page = 1;
    private int pageSize = 10;
    private boolean isDay = true;
    private String starstDate = "";
    private String endsDate = "";
    private SpringView springView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_goods_type_ranking_detail);
        initView();
        swtListener();
        initData(page, pageSize, isDay, starstDate, endsDate);
    }

    private void swtListener() {
//        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page=1;
//                list.clear();
//                initData(page,pageSize,isDay,starstDate,endsDate);
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
//                page++;
//                initData(page,pageSize,isDay,starstDate,endsDate);
//            }
//        });
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page,pageSize,isDay,starstDate,endsDate);
            }

            @Override
            public void onLoadmore() {
                page++;
                initData(page,pageSize,isDay,starstDate,endsDate);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

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
        pr_list.addItemDecoration(new RecycleViewDivider(TodaySingleGoodsSalesBestDetailActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));


        // TODO: 2019/1/2 标题判断
        textHeadTitle.setText(getString(R.string.today_single_sale_ranking));
        adapter = new TodaySingeleGoodSalesBestDetailAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        springView = (SpringView) findViewById(R.id.springView);

        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
    }

    private void initData(int page, int pageSize, boolean isDay, String startDate, String endDate) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GOODS_SALE + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //pr_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("商品销量", response);
                // pr_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    GoodsSale goodsSale = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsSale.class);
                    setData(goodsSale);
                } else {
                    showToast(baseModel.getMsg());
                }
            }
        });


    }

    private void setData(GoodsSale goodsSale) {
        if (goodsSale == null) return;
        if (goodsSale.getProductList() == null) return;

        List<GoodsSale.ProductListBean> beanList = goodsSale.getProductList();
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
