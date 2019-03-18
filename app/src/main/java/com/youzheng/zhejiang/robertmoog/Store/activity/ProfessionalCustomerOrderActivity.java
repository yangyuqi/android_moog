package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ProfessionalCustomerPagerAdapter;
import com.youzheng.zhejiang.robertmoog.Store.fragment.CustomerOrder2Fragment;
import com.youzheng.zhejiang.robertmoog.Store.fragment.CustomerOrderFragment;
import com.youzheng.zhejiang.robertmoog.Store.listener.CounterListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 专业客户订单界面ssss
 */
public class ProfessionalCustomerOrderActivity extends BaseActivity implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private TabLayout tab;
    private ViewPager pager;
    private List<Fragment> list=new ArrayList<>();
    private ProfessionalCustomerPagerAdapter pagerAdapter;
    private View no_data,no_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_customer_order);
        initView();
    }

    private void initView() {

        no_web = findViewById(R.id.no_web);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.order_list));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        tab.addOnTabSelectedListener(this);
        initPager();
    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            EventBus.getDefault().post("-1");


        }else {
            layout_header.setVisibility(View.VISIBLE);
            EventBus.getDefault().post("1");

        }
    }

    private void initPager() {
        //专业客户订单
        CustomerOrderFragment onefragment=new CustomerOrderFragment();
        Bundle upcomingBundle = new Bundle();
        upcomingBundle.putString(MyConstant.LIST_TYPE ,"MAJOR");
        onefragment.setArguments(upcomingBundle);
        list.add(onefragment);
        //推荐客户订单
        CustomerOrder2Fragment twofragment=new CustomerOrder2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(MyConstant.LIST_TYPE ,"GROOM");
        twofragment.setArguments(bundle);
        list.add(twofragment);


        FragmentManager fm = getSupportFragmentManager();
        pagerAdapter = new ProfessionalCustomerPagerAdapter(fm, list);
        pager.setAdapter(pagerAdapter);
        //pager.setOffscreenPageLimit(0);

        tab.setupWithViewPager(pager);
        //默认选中
        tab.getTabAt(0).select();


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


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition()==0){
            EventBus.getDefault().post("MAJOR");
        }else {
            EventBus.getDefault().post("GROOM");

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
