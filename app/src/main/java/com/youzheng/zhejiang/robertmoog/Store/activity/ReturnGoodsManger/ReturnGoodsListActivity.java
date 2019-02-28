package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoodsManger;

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
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ReturnGoodsListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ReturnGoodsTimeAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsList;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 退货订单列表
 */
public class ReturnGoodsListActivity extends BaseActivity implements View.OnClickListener, OnRecyclerViewAdapterItemClickListener, AdapterView.OnItemClickListener, TextWatcher {

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
    private GridView gv_time;
    /**
     * 重置
     */
    private TextView tv_again;
    /**
     * 确定sssss
     */
    private TextView tv_confirm;
    private DrawerLayout drawer_layout;
    private List<ReturnGoodsList.ReturnOrderListBean> list = new ArrayList<>();
    private List<String> piclist = new ArrayList<>();
    private ReturnGoodsTimeAdapter goodsTimeAdapter;
    private ReturnGoodsListAdapter adapter;
    private List<EnumsDatasBeanDatas> strlist = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;
    private String customerId;
    private String orderCode = "";
    private String timeQuantum = "";
    private Boolean isCustomer = false;
    private int who;
    private String edit;
    private String type;
    private SpringView mSpringView;
    private ImageView iv_clear;
    private View no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_goods_list);
        isCustomer = getIntent().getBooleanExtra("identifion", false);
        customerId = getIntent().getStringExtra("customerId");
        type = getIntent().getStringExtra("type");
        initView();
        setListener();
        initGetDate();
        initData(page, pageSize, orderCode, timeQuantum, isCustomer);
    }

    private void setListener() {
//        rv_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize, orderCode, timeQuantum, isCustomer);
//            }
//
//            @Override
//            public void onLoadMore() {
//                //list.clear();
//                page++;
//                initData(page, pageSize, orderCode, timeQuantum, isCustomer);
//            }
//        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize, orderCode, timeQuantum, isCustomer);
            }

            @Override
            public void onLoadmore() {
                page++;
                initData(page, pageSize, orderCode, timeQuantum, isCustomer);
            }
        });

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

                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });

    }


    private void initView() {
        no_data=findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        if (type.equals("1")) {
            textHeadTitle.setText(getString(R.string.customer_return));
        } else {
            textHeadTitle.setText(getString(R.string.store_return));
        }


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
        tv_search.addTextChangedListener(this);
        tv_again.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止手势滑动

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(manager);
        rv_list.setAdapter(adapter);
        rv_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(ReturnGoodsListActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        adapter = new ReturnGoodsListAdapter(list, this);
        rv_list.setAdapter(adapter);


        goodsTimeAdapter = new ReturnGoodsTimeAdapter(strlist, this);
        gv_time.setAdapter(goodsTimeAdapter);


        adapter.setOnItemClickListener(this);

        gv_time.setOnItemClickListener(this);

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

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int page, int pageSize, String orderCode, String timeQuantum, Boolean isCustomer) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("orderCode", orderCode);
        map.put("timeQuantum", timeQuantum);
        map.put("identifion", isCustomer);
        if (isCustomer == true) {
            map.put("customerId", customerId);
        }


        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.RETURN_ORDERLIST_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //rv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("退货单列表", response);
                // rv_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    ReturnGoodsList returnGoodsList = gson.fromJson(gson.toJson(baseModel.getDatas()), ReturnGoodsList.class);
                    setData(returnGoodsList);

                } else {
                    showToasts(baseModel.getMsg());
                }
            }

        });
    }

    private void setData(ReturnGoodsList returnGoodsList) {
        if (returnGoodsList == null) return;
        if (returnGoodsList.getReturnOrderList() == null) return;

        List<ReturnGoodsList.ReturnOrderListBean> listBeans = returnGoodsList.getReturnOrderList();
        if (listBeans.size() != 0) {
            list.addAll(listBeans);
            adapter.setUI(listBeans);
            no_data.setVisibility(View.GONE);
            mSpringView.setVisibility(View.VISIBLE);
        } else {
            if (page==1&&TextUtils.isEmpty(orderCode)&&TextUtils.isEmpty(timeQuantum)){
                no_data.setVisibility(View.VISIBLE);
                mSpringView.setVisibility(View.GONE);
            }else {
                showToasts(getString(R.string.load_list_erron));
            }
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
            case R.id.tv_time:
                drawer_layout.openDrawer(GravityCompat.END);
                tv_search.setClickable(false);
                SoftInputUtils.hideSoftInput(this);
                tv_search.clearFocus();
                break;
            case R.id.iv_search:
                SoftInputUtils.hideSoftInput(ReturnGoodsListActivity.this);
                edit = tv_search.getText().toString();
                //tv_search.setText("D1548784201901070011");
                if (TextUtils.isEmpty(edit)) {
                    showToasts(getString(R.string.please_write_return_number));
                } else {
                    orderCode = edit;
                    list.clear();
                    adapter.clear();
                    initData(page, pageSize, orderCode, timeQuantum, isCustomer);
                }

                break;

            case R.id.tv_again:
                goodsTimeAdapter.setSelectItem(0);
                timeQuantum = strlist.get(0).getId();
                break;

            case R.id.tv_confirm:
                adapter.clear();
                initData(page, pageSize, orderCode, timeQuantum, isCustomer);
                drawer_layout.closeDrawer(GravityCompat.END);
                tv_search.setClickable(true);
                goodsTimeAdapter.setSelectItem(who);
                timeQuantum = strlist.get(who).getId();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ReturnGoodsDetailActivity.class);
        intent.putExtra("returnGoodsId", list.get(position).getId() + "");
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goodsTimeAdapter.setSelectItem(position);
        timeQuantum = strlist.get(position).getId();
        who = position;
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
            page=1;
            initData(page, pageSize, orderCode, timeQuantum, isCustomer);
        }else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
}
