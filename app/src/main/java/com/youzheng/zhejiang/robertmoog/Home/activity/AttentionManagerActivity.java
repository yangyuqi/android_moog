package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CustomerGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ViewHolder;
import com.youzheng.zhejiang.robertmoog.Home.bean.MangerListener;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerIntentDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerIntentListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.NotLabelList;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalListBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.PhoneUtil;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;

public class AttentionManagerActivity extends BaseActivity implements MangerListener, TextWatcher {

    private ShopPersonalListBean listBean;
    private TabLayout tabLayout;
    private String personalId, phone;
    private int pageNum = 1, pageSize = 20, totalPage;
    Map<String, Object> map = new HashMap<>();
    RecyclerView recyclerView;
    ListView ls;
    List<CustomerIntentListBean> data = new ArrayList<>();
    CustomerGoodsAdapter adapter;
    int widWidth;
    EditText tv_search;
    CommonAdapter<ShopPersonalListBean> com_adapter;
    List<ShopPersonalListBean> da_list = new ArrayList<>();
    ImageView iv_search;
    SpringView springView;
    private ImageView iv_clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_manager_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        EventBus.getDefault().register(this);
        initView();


        initClick();
    }


    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    springView.setVisibility(View.VISIBLE);
                    ls.setVisibility(View.GONE);
                    findViewById(R.id.rl_search).setVisibility(View.VISIBLE);
                    initData();
                } else if (tab.getPosition() == 1) {
                    springView.setVisibility(View.GONE);
                    ls.setVisibility(View.VISIBLE);
                    findViewById(R.id.rl_search).setVisibility(View.GONE);
                    refreshData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                data.clear();
                initData();
            }

            @Override
            public void onLoadmore() {
                if (totalPage > pageNum) {
                    pageNum++;
                    initData();
                } else {
                    springView.onFinishFreshAndLoad();
                }
            }
        });
    }

    private void refreshData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.NOT_LABEEL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    NotLabelList list = gson.fromJson(gson.toJson(baseModel.getDatas()), NotLabelList.class);
                    com_adapter.setData(list.getNotLabelList());
                    com_adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void initData() {
        if (personalId != null) {
            map.put("personalId", personalId);
        }
        if (phone != null) {
            map.put("phone", phone);
        }
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CUSTOMER_INTENT + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    CustomerIntentDatas datas = gson.fromJson(gson.toJson(baseModel.getDatas()), CustomerIntentDatas.class);
                    if (datas.getCustomerIntentList().size() > 0) {
                        totalPage = datas.getTotalPage();
                        List<CustomerIntentListBean> customerList = datas.getCustomerIntentList();
                        for (int i = 0; i < customerList.size(); i++) {
                            if (customerList.get(i).getProductList().size() > 0) {
                                customerList.get(i).setGoodsId(customerList.get(i).getProductList().get(0).getId());
                                customerList.get(i).setPhoto(customerList.get(i).getProductList().get(0).getPhoto());
                                customerList.get(i).setName(customerList.get(i).getProductList().get(0).getName());
                                customerList.get(i).setSku(customerList.get(i).getProductList().get(0).getSku());
                                customerList.get(i).setCreateDate(customerList.get(i).getProductList().get(0).getCreateDate());

                            } else {
//                                adapter.clear();
//                                adapter.setData(new ArrayList<CustomerIntentListBean>(),mContext);
                                //showToast(getString(R.string.load_list_erron));
                            }
                        }
                        adapter.setData(customerList, mContext);
//                        if (customerList.size()==0){
//                            showToast(getString(R.string.load_list_erron));
//                        }
                    } else {
                        showToast(getString(R.string.load_list_erron));
                        adapter.setData(new ArrayList<CustomerIntentListBean>(), mContext);
                    }
                }
            }
        });
    }

    private void initView() {
        listBean = (ShopPersonalListBean) getIntent().getSerializableExtra("label");
        if (listBean != null) {
            personalId = listBean.getId();
        }
        tabLayout = findViewById(R.id.tab);
        ((TextView) findViewById(R.id.textHeadTitle)).setText(R.string.attention_intent_user);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new CustomerGoodsAdapter(data, mContext, widWidth, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
        springView = findViewById(R.id.sv);

        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
        if (role.equals(PublicUtils.SHOP_SELLER)) {
            tabLayout.setVisibility(View.VISIBLE);
        } else if (role.equals(PublicUtils.SHOP_LEADER)) {
            tabLayout.setVisibility(View.GONE);
        }

        ls = findViewById(R.id.ls);

        com_adapter = new CommonAdapter<ShopPersonalListBean>(mContext, da_list, R.layout.attention_intent_layout_other_item) {
            @Override
            public void convert(ViewHolder helper, final ShopPersonalListBean item) {

                helper.setText(R.id.tv_time, item.getCreateDate());
                helper.setText(R.id.tv_phone, item.getCustCode());
                helper.setText(R.id.tv_from, item.getChannel());
                helper.getView(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AttentionGoodsActivity.class);
                        intent.putExtra("label", item);
                        startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(com_adapter);

        tv_search = findViewById(R.id.tv_search);
        iv_search = findViewById(R.id.iv_search);

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_search.getText().toString().equals("")) {
                    showToast(getString(R.string.phone_not_null));
                    return;
                } else if (tv_search.getText().toString().length() < 11) {
                    showToast("手机号有误,请重新输入");
                } else if (PhoneUtil.isCellphone(tv_search.getText().toString()) == false) {
                    showToast("手机号格式错误,请重新输入");
                } else {
                    phone = tv_search.getText().toString();
                    initData();
                }


            }
        });

        tv_search.addTextChangedListener(this);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });
    }

    @Subscribe
    public void onEvent(String refresh) {
        if (refresh.equals("refresh")) {
            initData();
        }
    }

    @Override
    public void refresh(List<CustomerIntentListBean> lists) {
        initData();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (tv_search.length() == 0) {
            iv_clear.setVisibility(View.GONE);
            data.clear();
            phone = "";
            initData();
        }else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
