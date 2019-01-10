package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CouponAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.R;

import java.util.ArrayList;

public class CouponActivity extends BaseActivity {

    private ArrayList<CouponListBean> useCouponList  = new ArrayList<>();
    private ArrayList<CouponListBean> notUseCouponList = new ArrayList<>();

    private TabLayout tabLayout ;
    private RecyclerView recyclerView ;
    CouponAdapter addapter ;
    TextView tv_add ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        initView();

        initClick();
    }

    private void initClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    addapter.setData(useCouponList,CouponActivity.this,"1");
                }else if (tab.getPosition()==1){
                    addapter.setData(notUseCouponList,CouponActivity.this,"2");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tabLayout = findViewById(R.id.tab);
        useCouponList = (ArrayList<CouponListBean>) getIntent().getSerializableExtra("useCouponList");
        notUseCouponList = (ArrayList<CouponListBean>) getIntent().getSerializableExtra("notUseCouponList");
        if (useCouponList.size()==0){
            tabLayout.addTab(tabLayout.newTab().setText("可使用（0）"));
        }else {
            tabLayout.addTab(tabLayout.newTab().setText("可使用"+"（"+useCouponList.size()+")"));
        }
        if (notUseCouponList.size()==0){
            tabLayout.addTab(tabLayout.newTab().setText("不可使用（0）"));
        }else {
            tabLayout.addTab(tabLayout.newTab().setText("不可使用"+"（"+notUseCouponList.size()+"）"));
        }
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        addapter = new CouponAdapter();
        recyclerView.setAdapter(addapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
        addapter.setData(useCouponList,CouponActivity.this,"1");
        tv_add = findViewById(R.id.tv_add);

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CouponListBean bean :useCouponList){
                    if (bean.isClick()){
                        if (bean.getAssetId()!=null){
                            Intent intent = new Intent();
                            intent.putExtra("assetId",bean.getAssetId());
                            intent.putExtra("payValue",bean.getPayValue());
                            setResult(3,intent);
                        }
                    }
                }
                finish();
            }
        });
    }
}
