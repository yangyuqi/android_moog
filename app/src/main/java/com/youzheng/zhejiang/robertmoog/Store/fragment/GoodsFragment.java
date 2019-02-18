package com.youzheng.zhejiang.robertmoog.Store.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.GoodsDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.GoodsList;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
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
    private PullLoadMoreRecyclerView pr_goods;
    private int pageSize = 10;
    private int page = 1;
    private String sku;
    private int goodsId;
    private boolean isFirstLoad = false;
    private SpringView springView;

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
            adapter.setRefreshUI(productListDetailDataBeans);
        } else {
            showToast(getString(R.string.load_list_erron));
        }
        //list=goodsList.getProductListDetailData();


    }

    private void initView() {
        pr_goods = (PullLoadMoreRecyclerView) view.findViewById(R.id.pr_goods);
        pr_goods.setLinearLayout();
        pr_goods.setColorSchemeResources(R.color.colorPrimary);
        pr_goods.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 15, getResources().getColor(R.color.bg_all)));

        pr_goods.setPullRefreshEnable(false);
        pr_goods.setPushRefreshEnable(false);

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
