package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchMealAdapter;
import com.youzheng.zhejiang.robertmoog.Home.bean.SearchMeal;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnAllCounterActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SearchMealInfoActivity extends BaseActivity implements View.OnClickListener {

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
    private List<SearchMeal.SelectProductsBean> list=new ArrayList<>();
    private SearchMealAdapter adapter;
    private String code;
    private String promoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal_info);
        promoId=getIntent().getStringExtra("codeid");
        initView();
    }

    private void initData(String code, String promoId) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("code",code);
        map.put("promoId",promoId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEARCH_MEAL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("搜索结果",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    SearchMeal searchMeal = gson.fromJson(gson.toJson(baseModel.getDatas()),SearchMeal.class);
                    setData(searchMeal);
                }
            }
        });
    }

    private void setData(SearchMeal searchMeal) {
     if (searchMeal.getSelectProducts()==null) return;
        List<SearchMeal.SelectProductsBean> beans=searchMeal.getSelectProducts();
        if (beans.size()!=0){
            list.addAll(beans);
            adapter.setUI(beans);
        }else {
            showToast(getString(R.string.load_list_erron));
        }
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("搜索套餐");
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
        rv_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(SearchMealInfoActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));

        adapter=new SearchMealAdapter(list,this);
        rv_list.setAdapter(adapter);
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
                code=tv_search.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    showToast("请输入套餐号");
                }else {
                    adapter.clear();
                    initData(code,promoId);
                }

                break;
        }
    }
}
