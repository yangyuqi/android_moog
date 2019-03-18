package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchMealAdapter;
import com.youzheng.zhejiang.robertmoog.Home.bean.SearchMeal;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SearchMealInfoActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 输入商品SKU/套餐号
     */
    private EditText tv_search;
    private ImageView iv_search;
    private RecyclerView rv_list;
    private List<SearchMeal.SelectProductsBean> list = new ArrayList<>();
    private SearchMealAdapter adapter;
    private String code;
    private String promoId;
    private ImageView iv_clear;
    private View no_data, no_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal_info);
        promoId = getIntent().getStringExtra("codeid");
        initView();
    }

    private void initData(String code, String promoId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("promoId", promoId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEARCH_MEAL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("搜索结果", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    SearchMeal searchMeal = gson.fromJson(gson.toJson(baseModel.getDatas()), SearchMeal.class);
                    setData(searchMeal);
                }
            }
        });
    }

    private void setData(SearchMeal searchMeal) {
        if (searchMeal.getSelectProducts() == null) return;
        List<SearchMeal.SelectProductsBean> beans = searchMeal.getSelectProducts();
        if (beans.size() != 0) {
            list.clear();
            list.addAll(beans);
            adapter.setUI(list);
        } else {
            showToasts(getString(R.string.load_list_erron));
        }
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
        textHeadTitle.setText("查找套餐");
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_search = (EditText) findViewById(R.id.tv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(manager);
        rv_list.setAdapter(adapter);
        rv_list.addItemDecoration(new RecycleViewDivider(SearchMealInfoActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));

        adapter = new SearchMealAdapter(list, this);
        rv_list.setAdapter(adapter);

        tv_search.addTextChangedListener(this);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.iv_search:
                SoftInputUtils.hideSoftInput(SearchMealInfoActivity.this);
                code = tv_search.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    showToasts(getString(R.string.write_sku_meal));
                } else {
                    adapter.clear();
                    initData(code, promoId);
                }

                break;
        }
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
            //list.clear();
            iv_clear.setVisibility(View.GONE);
            adapter.clear();
            code = "";
            initData(code, promoId);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
}
