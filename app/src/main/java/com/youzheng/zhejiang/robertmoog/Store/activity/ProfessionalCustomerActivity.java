package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ProfessionalCustomerAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ProfessionalCustomerList;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 专业客户界面sss
 */
public class ProfessionalCustomerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 3
     */
    private TextView tv_number;
    private RecyclerView lv_list;
    private List<ProfessionalCustomerList.SpecialtyCustomerListBean> list = new ArrayList<>();
    private ProfessionalCustomerAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private SpringView springView;
    private View no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_customer);
        initView();
        setListener();


    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        initData(page, pageSize);
    }

    private void setListener() {
//        lv_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize);
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
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
                list.clear();
                page++;
                initData(page, pageSize);
            }
        });

    }

    private void initView() {

        no_data=findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.profess_customer));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadNext.setVisibility(View.GONE);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_next.setVisibility(View.VISIBLE);
        iv_next.setImageResource(R.mipmap.group_93_1);
        iv_next.setOnClickListener(this);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_number = (TextView) findViewById(R.id.tv_number);
        lv_list = (RecyclerView) findViewById(R.id.lv_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lv_list.setLayoutManager(manager);
        lv_list.setAdapter(adapter);
        lv_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(ProfessionalCustomerActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));

        adapter = new ProfessionalCustomerAdapter(list, this);
        lv_list.setAdapter(adapter);


        springView = (SpringView) findViewById(R.id.springView);
        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
    }

    private void initData(int page, int pageSize) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.PROFESSIONAL_CUSTOMER_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
               // lv_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();

            }

            @Override
            public void onResponse(String response) {
                Log.e("专业客户列表", response);
               // lv_list.setPullLoadMoreCompleted();
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    ProfessionalCustomerList professionalCustomerList = gson.fromJson(gson.toJson(baseModel.getDatas()), ProfessionalCustomerList.class);
                    setData(professionalCustomerList);
                } else {
                    showToast(baseModel.getMsg());
                }
            }

        });
    }

    private void setData(ProfessionalCustomerList professionalCustomerList) {
        if (professionalCustomerList.getSpecialtyCustomerList() == null) return;
        if (professionalCustomerList.getCount() != 0) {
            tv_number.setText(professionalCustomerList.getCount() + "");
        } else {
            tv_number.setText("0");
        }

        List<ProfessionalCustomerList.SpecialtyCustomerListBean> beanList = professionalCustomerList.getSpecialtyCustomerList();
        if (beanList.size() != 0) {
            list.addAll(beanList);
            adapter.setListRefreshUi(beanList);
            no_data.setVisibility(View.GONE);
            springView.setVisibility(View.VISIBLE);
        } else {
            if (page==1){
                no_data.setVisibility(View.VISIBLE);
                springView.setVisibility(View.GONE);
            }else {
                showToast(getString(R.string.load_list_erron));
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
            case R.id.iv_next:
                startActivity(new Intent(this, AddCustomerActivity.class));
                break;
        }
    }
}
