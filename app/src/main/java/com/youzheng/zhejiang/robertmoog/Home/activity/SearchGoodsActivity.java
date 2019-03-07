package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class SearchGoodsActivity extends BaseActivity implements TextWatcher {

    RecyclerView recyclerView;
    ImageView iv_click;
    EditText tv_search;
    List<ScanDatasBean> data = new ArrayList<>();
    int widWidth;
    private ImageView iv_clear;
    private String goodsCode;
    private View no_data, no_web;
    private RelativeLayout layout_header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_goods_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        goodsCode = getIntent().getStringExtra("goodsCode");
        initView();

        initClick();
        if (!TextUtils.isEmpty(goodsCode)) {
            search(goodsCode);
        }

    }

    private void initClick() {
        iv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(SearchGoodsActivity.this);
                if (tv_search.getText().toString().equals("")) {
                    showToasts("请输入搜索条件");
                    return;
                }
                goodsCode = tv_search.getText().toString();
                search(goodsCode);

            }
        });
    }

    private void search(String goodsCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", goodsCode);//f36451db-3efa-4ccb-8260-d7fb7e2128f9
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SCAN_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    ScanDatas scanDatas = gson.fromJson(gson.toJson(baseModel.getDatas()), ScanDatas.class);
                    if (scanDatas.getSelectProducts().size() > 0) {
                        addapter.setDate(scanDatas.getSelectProducts(), mContext, "1", widWidth);
                    }
                } else {
                    if (!TextUtils.isEmpty(baseModel.getMsg())) {
                        showToasts(baseModel.getMsg());
                    }


                }
            }
        });
    }

//    @Override
//    public void onChangeListener(int status) {
//        super.onChangeListener(status);
//        if (status == -1) {
//            layout_header.setVisibility(View.VISIBLE);
//            no_web.setVisibility(View.VISIBLE);
//        } else {
//            layout_header.setVisibility(View.VISIBLE);
//            no_web.setVisibility(View.GONE);
//        }
//    }

    SearchResultAdapter addapter;

    private void initView() {
       // layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        no_web = findViewById(R.id.no_web);
        ((TextView) findViewById(R.id.textHeadTitle)).setText("查找商品/套餐");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_click = findViewById(R.id.iv_click);
        tv_search = findViewById(R.id.tv_search);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        addapter = new SearchResultAdapter();
        recyclerView.setAdapter(addapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));

        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
//                data.clear();
//                addapter.clear();
                // search();
            }
        });

        tv_search.addTextChangedListener(this);

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
//            data.clear();
//            addapter.clear();
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
}
