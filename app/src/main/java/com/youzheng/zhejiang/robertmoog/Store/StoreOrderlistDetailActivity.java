package com.youzheng.zhejiang.robertmoog.Store;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.R;

public class StoreOrderlistDetailActivity extends BaseActivity implements View.OnClickListener {

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
     * 987656201801020002
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
    /**
     * 刘思思
     */
    private TextView tv_name;
    /**
     * 187 0000 0009
     */
    private TextView tv_phone;
    /**
     * 北京东城区内环三环银河sohoD座809
     */
    private TextView tv_address;
    /**
     * 安装进度
     */
    private TextView tv_progress;
    /**
     * 已申请
     */
    private TextView tv_progress_state;
    private RecyclerView rv_list;
    /**
     * 订单促销
     */
    private TextView tv_order_promotion;
    /**
     * 1000
     */
    private TextView tv_over_money;
    /**
     * ￥100
     */
    private TextView tv_cut_money;
    /**
     * 属于摩恩全国活动
     */
    private TextView tv_belong;
    /**
     * 未赠送礼品
     */
    private TextView tv_or_send;
    /**
     * 提货
     */
    private TextView tv_get;
    /**
     * 已提货
     */
    private TextView tv_get_state;
    /**
     * 配送
     */
    private TextView tv_dispatching;
    /**
     * 大仓配送
     */
    private TextView tv_dispatching_type;
    /**
     * 收款方式
     */
    private TextView tv_get_money;
    /**
     * 商场付款
     */
    private TextView tv_get_money_type;
    /**
     * ￥16698
     */
    private TextView tv_should_money;
    /**
     * -￥8
     */
    private TextView tv_cut_money_of_juan;
    /**
     * -￥100
     */
    private TextView tv_cut_money_of_promotion;
    /**
     * -￥80
     */
    private TextView tv_cut_money_of_store;
    /**
     * ￥17700
     */
    private TextView tv_get_money_of_now;
    /**
     * 该订单是长工CC推荐来的，未结算
     */
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orderlist_detail);
        initView();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("订单详情");
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_referee = (TextView) findViewById(R.id.tv_referee);
        tv_maker = (TextView) findViewById(R.id.tv_maker);
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_progress_state = (TextView) findViewById(R.id.tv_progress_state);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        tv_order_promotion = (TextView) findViewById(R.id.tv_order_promotion);
        tv_over_money = (TextView) findViewById(R.id.tv_over_money);
        tv_cut_money = (TextView) findViewById(R.id.tv_cut_money);
        tv_belong = (TextView) findViewById(R.id.tv_belong);
        tv_or_send = (TextView) findViewById(R.id.tv_or_send);
        tv_get = (TextView) findViewById(R.id.tv_get);
        tv_get_state = (TextView) findViewById(R.id.tv_get_state);
        tv_dispatching = (TextView) findViewById(R.id.tv_dispatching);
        tv_dispatching_type = (TextView) findViewById(R.id.tv_dispatching_type);
        tv_get_money = (TextView) findViewById(R.id.tv_get_money);
        tv_get_money_type = (TextView) findViewById(R.id.tv_get_money_type);
        tv_should_money = (TextView) findViewById(R.id.tv_should_money);
        tv_cut_money_of_juan = (TextView) findViewById(R.id.tv_cut_money_of_juan);
        tv_cut_money_of_promotion = (TextView) findViewById(R.id.tv_cut_money_of_promotion);
        tv_cut_money_of_store = (TextView) findViewById(R.id.tv_cut_money_of_store);
        tv_get_money_of_now = (TextView) findViewById(R.id.tv_get_money_of_now);
        tv_content = (TextView) findViewById(R.id.tv_content);
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
