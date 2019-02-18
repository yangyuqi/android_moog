package com.youzheng.zhejiang.robertmoog.Store.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.StoreOrderlistDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsTimeAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ProfessionalCustomerOrderListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class CustomerOrderFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, OnRecyclerViewAdapterItemClickListener, TextWatcher {
    private View view;
    /**
     * 搜索订单编号sssssss
     */
    private EditText tv_search;
    private ImageView iv_search;
    private LinearLayout lin_search;
    /**
     * 时间
     */
    private TextView tv_time;
    private PullLoadMoreRecyclerView rv_list;
    private GridView gv_time;
    /**
     * 重置
     */
    private TextView tv_again;
    /**
     * 确定
     */
    private TextView tv_confirm;
    private DrawerLayout drawer_layout;
    private List<NewOrderListBean.OrderListBean> list = new ArrayList<>();
    private List<String> piclist = new ArrayList<>();
    private ProfessionalCustomerOrderListAdapter adapter;
    private GoodsTimeAdapter goodsTimeAdapter;
    private List<EnumsDatasBeanDatas> strlist = new ArrayList<>();
    private String type = "";
    private int page = 1;
    private int pageSize = 10;
    private int customerId;
    private String orderCode = "";
    private String timeQuantum = "";
    private Boolean isCustomer = false;
    private int who;
    private String edit;
    private String token;
    private boolean isFirstLoad = false;
    private SpringView springView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_customer_order, null);
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = (String) arguments.get(MyConstant.LIST_TYPE);
        }
        token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token, "");
        initView();

        isFirstLoad = true;//视图创建完成，将变量置为true

        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
            initGetDate();
            setListener();
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
            initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
            initGetDate();
            setListener();
            isFirstLoad = false;
        }
    }

    private void setListener() {
//        rv_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                //list.clear();
//                page++;
//                initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
//            }
//        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
            }

            @Override
            public void onLoadmore() {
                page++;
                initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        tv_search = (EditText) view.findViewById(R.id.tv_search);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        lin_search = (LinearLayout) view.findViewById(R.id.lin_search);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        rv_list = (PullLoadMoreRecyclerView) view.findViewById(R.id.rv_list);
        gv_time = (GridView) view.findViewById(R.id.gv_time);
        tv_again = (TextView) view.findViewById(R.id.tv_again);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        drawer_layout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止手势滑动
        tv_again.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        gv_time.setOnItemClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 15, getResources().getColor(R.color.bg_all)));
        rv_list.setLinearLayout();
        rv_list.setColorSchemeResources(R.color.colorPrimary);


        rv_list.setPullRefreshEnable(false);
        rv_list.setPushRefreshEnable(false);
        adapter = new ProfessionalCustomerOrderListAdapter(list, getActivity());
        rv_list.setAdapter(adapter);


        goodsTimeAdapter = new GoodsTimeAdapter(strlist, getActivity());
        gv_time.setAdapter(goodsTimeAdapter);


        adapter.setOnItemClickListener(this);

        tv_search.addTextChangedListener(this);

        springView = (SpringView) view.findViewById(R.id.springView);

        springView.setHeader(new MyHeader(getActivity()));
        springView.setFooter(new MyFooter(getActivity()));
    }

    private void initGetDate() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("获取时间枚举", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()), EnumsDatas.class);
                    if (enumsDatas.getEnums().size() > 0) {
                        for (final EnumsDatasBean bean : enumsDatas.getEnums()) {
                            if (bean.getClassName().equals("TimeQuantum")) {//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();
                                List<EnumsDatasBeanDatas> list1 = new ArrayList<>();
                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                    list1.add(bean.getDatas().get(i));
                                }
                                strlist = list1;
                            }
                        }

                        goodsTimeAdapter.setUI(strlist);


                    }

                }
            }
        });

    }

    private void initData(int page, int pageSize, String orderCode, String timeQuantum, Boolean isCustomer, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("orderCode", orderCode);
        map.put("timeQuantum", timeQuantum);
        map.put("identifion", isCustomer);
        if (isCustomer == true) {
            map.put("customerId", customerId);
        }
        map.put("type", type);//订单类型（ALL:全部，GROOM:推荐订单，MAJOR:专业）默认是全部

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDERLIST_LIST + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //rv_list.setPullLoadMoreCompleted();
               springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("专业订单列表", response);
              //  rv_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    NewOrderListBean listBean = gson.fromJson(gson.toJson(baseModel.getDatas()), NewOrderListBean.class);
                    setData(listBean);
                } else {
                    showToast(baseModel.getMsg());
                }
            }

        });

    }


    private void setData(NewOrderListBean listBean) {
        if (listBean == null) return;
        if (listBean.getOrderList() == null) return;

        List<NewOrderListBean.OrderListBean> orderListBeans = listBean.getOrderList();

        if (orderListBeans.size() != 0) {
            list.addAll(orderListBeans);
            adapter.setUI(list);
        } else {
            showToast(getString(R.string.load_list_erron));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_search:
                SoftInputUtils.hideSoftInput(getActivity());
                edit = tv_search.getText().toString();
                //tv_search.setText("D1548784201901070011");
                if (TextUtils.isEmpty(edit)) {
                    showToast(getString(R.string.please_write_order_number));
                } else {
                    orderCode = edit;
                    adapter.clear();
                    initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
                }
                break;
            case R.id.tv_time:
                drawer_layout.openDrawer(GravityCompat.END);
                break;
            case R.id.tv_again:
                goodsTimeAdapter.setSelectItem(0);
                timeQuantum = strlist.get(0).getId();
                break;
            case R.id.tv_confirm:
                adapter.clear();
                initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
                drawer_layout.closeDrawer(GravityCompat.END);
                goodsTimeAdapter.setSelectItem(who);
                timeQuantum = strlist.get(who).getId();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goodsTimeAdapter.setSelectItem(position);
        timeQuantum = strlist.get(position).getId();
        who = position;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), StoreOrderlistDetailActivity.class);
        intent.putExtra("OrderGoodsId", list.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (tv_search.length() == 0) {
            list.clear();
            orderCode = "";
            initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
        }
    }
}
