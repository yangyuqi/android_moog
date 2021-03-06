package com.youzheng.zhejiang.robertmoog.Store.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.GoodsDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.GoodsList;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class GoodsFragment extends BaseFragment {
    private View view;
    private GoodsListAdapter adapter;
    private List<GoodsList.ProductListDetailDataBean> list = new ArrayList<>();
    private RecyclerView pr_goods;
    private int pageSize = 10;
    private int page = 1;
    private String sku;
    private int goodsId;
    private boolean isFirstLoad = false;
    public SpringView springView;
    private View no_data;
    /**
     * 暂无数据!
     */
    private TextView tv_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_goods_list, null);
        initView();

        isFirstLoad = true;//视图创建完成，将变量置为true

        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            setListener();
            initData(page, pageSize);
            isFirstLoad = false;
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;//视图销毁将变量置为false
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {//视图变为可见并且是第一次加载
            setListener();
            initData(page, pageSize);
            isFirstLoad = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData(int page, int pageSize) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            sku = (String) arguments.get(MyConstant.GOODS_LIST_TYPE);
            goodsId = (int) arguments.get(MyConstant.GOODS_ID);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
//        map.put("sku",goodsName);
        map.put("sku", sku);//测试用
        map.put("firstCategoryId", goodsId);
        String token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token, "");
        if (token != null) {
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GOODS_LIST + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    // pr_goods.setPullLoadMoreCompleted();
                    springView.onFinishFreshAndLoad();
                }

                @Override
                public void onResponse(String response) {
                    Log.e("商品列表", response);
                    // pr_goods.setPullLoadMoreCompleted();
                    springView.onFinishFreshAndLoad();
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        GoodsList goodsList = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsList.class);
                        setData(goodsList);
                    }
                }
            });
        }
    }

    private void setData(GoodsList goodsList) {
        if (goodsList.getProductListDetailData() == null) return;
        List<GoodsList.ProductListDetailDataBean> productListDetailDataBeans = goodsList.getProductListDetailData();

        if (productListDetailDataBeans.size() != 0) {
            list.addAll(productListDetailDataBeans);
            adapter.setRefreshUI(list);
            no_data.setVisibility(View.GONE);
            springView.setVisibility(View.VISIBLE);
        } else {
            if (page == 1) {
                no_data.setVisibility(View.VISIBLE);
                tv_text.setText("暂无商品信息");
                springView.setVisibility(View.GONE);
            } else {
                showToasts(getString(R.string.load_list_erron));
            }


        }
        //list=goodsList.getProductListDetailData();


    }

    private void initView() {
        no_data = view.findViewById(R.id.no_data);
        pr_goods = (RecyclerView) view.findViewById(R.id.pr_goods);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        pr_goods.setLayoutManager(manager);
        pr_goods.setAdapter(adapter);
        pr_goods.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        adapter = new GoodsListAdapter(list, getActivity());
        pr_goods.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnRecyclerViewAdapterItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodsID", list.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        springView = (SpringView) view.findViewById(R.id.springView);
        springView.setHeader(new MyHeader(getActivity()));
        springView.setFooter(new MyFooter(getActivity()));
        tv_text = (TextView) view.findViewById(R.id.tv_text);
    }

    private void setListener() {
//        pr_goods.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize);
//            }
//
//            @Override
//            public void onLoadMore() {
//                // list.clear();
//                page++;
//                initData(page, pageSize);
//            }
//        });


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize);
            }

            @Override
            public void onLoadmore() {
                page++;
                initData(page, pageSize);
            }
        });

    }


}
