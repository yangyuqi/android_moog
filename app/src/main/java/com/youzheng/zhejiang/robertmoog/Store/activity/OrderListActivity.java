package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsTimeAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OrderListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 订单列表界面ss
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, OnRecyclerViewAdapterItemClickListener, TextWatcher {


    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 搜索订单编号
     */
    private EditText tv_search;
    private ImageView iv_search;
    private LinearLayout lin_search;
    /**
     * 时间
     */
    private TextView tv_time;
    private RecyclerView rv_list;
    private OrderListAdapter adapter;
    private List<NewOrderListBean.OrderListBean> list = new ArrayList<>();
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
    private GoodsTimeAdapter goodsTimeAdapter;
    private List<EnumsDatasBeanDatas> strlist = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;
    private String customerId;
    private String orderCode = "";
    private String timeQuantum = "";
    private Boolean isCustomer = false;
    private int who;
    private String edit;
    private String type = "ALL";//订单类型（ALL:全部，GROOM:推荐订单，MAJOR:专业）默认是全部
    private SpringView mSpringView;
    private ImageView iv_clear;
    private View no_data,no_web;

    /**
     * 暂无数据!
     */
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        isCustomer = getIntent().getBooleanExtra("identifion", false);
        customerId = getIntent().getStringExtra("customerId");
        initView();
        setListener();
        initGetDate();
        initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
    }

    private void initGetDate() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("获取时间枚举", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()), EnumsDatas.class);

                    if (enumsDatas.getEnums()!=null){
                        if (enumsDatas.getEnums().size() > 0) {
                            for (final EnumsDatasBean bean : enumsDatas.getEnums()) {
                                if (!TextUtils.isEmpty(bean.getClassName())){
                                    if (bean.getClassName().equals("TimeQuantum")) {//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();
                                        List<EnumsDatasBeanDatas> list1 = new ArrayList<>();
                                        for (int i = 0; i < bean.getDatas().size(); i++) {
                                            list1.add(bean.getDatas().get(i));
                                        }
                                        strlist = list1;
                                    }
                                }

                            }
                            if (strlist.size()!=0){
                                goodsTimeAdapter.setUI(strlist);
                            }

                        }
                    }


                }
            }
        });

    }

    private void setListener() {


        mSpringView.setListener(new SpringView.OnFreshListener() {
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
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status == -1) {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        } else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);

        }
    }


        private void initView() {
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.order_list));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_search = (EditText) findViewById(R.id.tv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        lin_search = (LinearLayout) findViewById(R.id.lin_search);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        gv_time = (GridView) findViewById(R.id.gv_time);
        tv_again = (TextView) findViewById(R.id.tv_again);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_again.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止手势滑动
        gv_time.setOnItemClickListener(this);
        btnBack.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(manager);
        rv_list.setAdapter(adapter);
        rv_list.addItemDecoration(new RecycleViewDivider(OrderListActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_list.addItemDecoration(new RecycleViewDivider(
//                this, LinearLayoutManager.VERTICAL, 15, getResources().getColor(R.color.bg_all)));
//        rv_list.setLinearLayout();
//        rv_list.setColorSchemeResources(R.color.colorPrimary);
//
//        rv_list.setPushRefreshEnable(false);
//        rv_list.setPullRefreshEnable(false);

        adapter = new OrderListAdapter(list, this);
        rv_list.setAdapter(adapter);


        goodsTimeAdapter = new GoodsTimeAdapter(strlist, this);
        gv_time.setAdapter(goodsTimeAdapter);


        adapter.setOnItemClickListener(this);

        tv_search.addTextChangedListener(this);


        mSpringView = (SpringView) findViewById(R.id.springView);
        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });

        tv_text = (TextView) findViewById(R.id.tv_text);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(final int pages, int pageSize, String orderCode, String timeQuantum, Boolean isCustomer, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", pages);
        map.put("pageSize", pageSize);
        map.put("orderCode", orderCode);
        map.put("timeQuantum", timeQuantum);
        map.put("identifion", isCustomer);
        if (isCustomer == true) {
            map.put("customerId", customerId);
        }
        map.put("type", type);//订单类型（ALL:全部，GROOM:推荐订单，MAJOR:专业）默认是全部

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDERLIST_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // rv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("订单列表", response);
                // rv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    NewOrderListBean listBean = gson.fromJson(gson.toJson(baseModel.getDatas()), NewOrderListBean.class);
                    setData(listBean);

                } else {

                    showToasts(baseModel.getMsg());
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
            adapter.setUI(list, this);
            no_data.setVisibility(View.GONE);
            mSpringView.setVisibility(View.VISIBLE);
        } else {
            if (page == 1) {
                no_data.setVisibility(View.VISIBLE);
                tv_text.setText("暂无订单信息");
                mSpringView.setVisibility(View.GONE);
            } else {
                showToasts(getString(R.string.load_list_erron));
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_search:
                SoftInputUtils.hideSoftInput(OrderListActivity.this);
                edit = tv_search.getText().toString();
                //tv_search.setText("D1548784201901070011");
                if (TextUtils.isEmpty(edit)) {
                    showToasts(getString(R.string.please_write_order_number));
                } else {
                    orderCode = edit;
                    //list.clear();
                    adapter.clear();
                    initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
                }
                break;
            case R.id.tv_time:
                if (strlist!=null){
                    if (strlist.size()!=0){
                        drawer_layout.openDrawer(GravityCompat.END);
                        SoftInputUtils.hideSoftInput(this);
                        tv_search.clearFocus();
                    }else {
                        showToasts("暂无数据");
                    }
                }else {
                    showToasts("暂无数据");
                }

                break;

            case R.id.btnBack:
                finish();
                break;

            case R.id.tv_again:
                goodsTimeAdapter.setSelectItem(0);
                timeQuantum = strlist.get(0).getId();
                break;

            case R.id.tv_confirm:
                list.clear();
                adapter.clear();
                initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
                drawer_layout.closeDrawer(GravityCompat.END);
                goodsTimeAdapter.setSelectItem(who);
                tv_search.setClickable(true);
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
        Intent intent = new Intent(this, StoreOrderlistDetailActivity.class);
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
            iv_clear.setVisibility(View.GONE);
            list.clear();
            orderCode = "";
            page = 1;
            initData(page, pageSize, orderCode, timeQuantum, isCustomer, type);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
}
