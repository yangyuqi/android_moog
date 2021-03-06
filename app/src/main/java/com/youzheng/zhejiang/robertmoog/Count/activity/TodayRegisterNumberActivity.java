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

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.adapter.TodayRegisterAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.TodayRegisterList;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 今日客户注册数 界面
 */
public class TodayRegisterNumberActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private RecyclerView pr_list;
    private List<TodayRegisterList.CustomerListBean> list = new ArrayList<>();
    private TodayRegisterAdapter adapter;
    private LinearLayout state_layout_empty;
    private View no_data,no_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_register_number);
        initView();
        initData();
        //disappearTitle();
    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        }else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }
    }
    private void initView() {
        no_web = findViewById(R.id.no_web);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.today_register_number));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new RecycleViewDivider(TodayRegisterNumberActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));


        adapter = new TodayRegisterAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        no_data=findViewById(R.id.no_data);
        state_layout_empty = (LinearLayout) findViewById(R.id.state_layout_empty);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.TODAT_REGISTER_NUMBER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("今日注册数", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    TodayRegisterList registerList = gson.fromJson(gson.toJson(baseModel.getDatas()), TodayRegisterList.class);
                    setData(registerList);
                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });


    }

    private void setData(TodayRegisterList registerList) {
        if (registerList == null) return;
        if (registerList.getCustomerList() == null) {
            return;
        }

        List<TodayRegisterList.CustomerListBean> beanList = registerList.getCustomerList();
        if (beanList.size() != 0) {
            list.addAll(beanList);
            adapter.refreshUI(list);
            no_data.setVisibility(View.GONE);
            pr_list.setVisibility(View.VISIBLE);
        } else {
           // showToasts(getString(R.string.load_list_erron));
            no_data.setVisibility(View.VISIBLE);
            pr_list.setVisibility(View.GONE);
            //initTitle();

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
