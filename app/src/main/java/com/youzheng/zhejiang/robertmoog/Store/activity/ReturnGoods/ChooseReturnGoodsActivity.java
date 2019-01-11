package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ProfessionalOrderDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.adapter.MoreChooseReturnGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.MoreOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OneChooseReturnGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OneOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择退货商品界面ss
 */
public class ChooseReturnGoodsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 2016/01/19 09:19:04
     */
    private TextView tv_time;
    /**
     * 987656201801020002ssss
     */
    private TextView tv_num;
    /**
     * 187 0000 0009
     */
    private TextView tv_referee;
    /**
     * 李白(导购)
     */
    private TextView tv_maker;
    /**
     * 187 0000 0009
     */
    private TextView tv_customer;
    private RecyclerView rv_list_one;
    private RecyclerView rv_list_more;
    /**
     * 确定
     */
    private TextView tv_confirm;
    private OneChooseReturnGoodsAdapter oneOrderDetailAdapter;
    private MoreChooseReturnGoodsAdapter moreChooseReturnGoodsAdapter;

    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> onelist=new ArrayList<>();
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean> morelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_return_goods);
        initView();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadTitle.setText("选择商品");
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_referee = (TextView) findViewById(R.id.tv_referee);
        tv_maker = (TextView) findViewById(R.id.tv_maker);
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        rv_list_one = (RecyclerView) findViewById(R.id.rv_list_one);
        rv_list_more = (RecyclerView) findViewById(R.id.rv_list_more);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_one.setLayoutManager(manager);
        rv_list_one.setAdapter(oneOrderDetailAdapter);
        rv_list_one.addItemDecoration(new RecycleViewDivider(ChooseReturnGoodsActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));



        LinearLayoutManager manager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_more.setLayoutManager(manager2);
        rv_list_more.setAdapter(moreChooseReturnGoodsAdapter);
        rv_list_more.addItemDecoration(new RecycleViewDivider(ChooseReturnGoodsActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));



        oneOrderDetailAdapter=new OneChooseReturnGoodsAdapter(onelist,this);
        rv_list_one.setAdapter(oneOrderDetailAdapter);


        moreChooseReturnGoodsAdapter=new MoreChooseReturnGoodsAdapter(morelist,this);
        rv_list_more.setAdapter(moreChooseReturnGoodsAdapter);



    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.tv_confirm:
                break;
        }
    }
}
