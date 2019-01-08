package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.MoreOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OneOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.CustomerList;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

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
    private RecyclerView rv_list_one,rv_list_more;
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

    private List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> onelist=new ArrayList<>();
    private List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean> morelist=new ArrayList<>();

    private OneOrderDetailAdapter oneOrderDetailAdapter;
    private MoreOrderDetailAdapter moreOrderDetailAdapter;
    private int id;

    private String code;
    private String createDate;
    private String account;
    private String createUser;
    private String businessRole;
    private Boolean isOrderDerate;
    private String maxAmount;
    private String orderDerate;
    private Boolean isFreeGift;
    private Boolean isMoen;
    private String pickUpStatus;
    private String paymentMethod;
    private String shoppingMethod;

    private String productCount;
    private String amountPayable;
    private String payAmount;
    private String couponDerate;
    private String shopDerate;
    private String installStatus;
    private String shipPerson;
    private String shipMobile;
    private String shipAddress;

    private String comment;
    private String returnOrderStatus;







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
        rv_list_one = (RecyclerView) findViewById(R.id.rv_list_one);
        rv_list_more=findViewById(R.id.rv_list_more);
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


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list_one.setLayoutManager(linearLayoutManager);
        rv_list_more.setLayoutManager(linearLayoutManager);

        oneOrderDetailAdapter=new OneOrderDetailAdapter(onelist,this);
        rv_list_one.setAdapter(oneOrderDetailAdapter);


        moreOrderDetailAdapter=new MoreOrderDetailAdapter(morelist,this);
        rv_list_more.setAdapter(moreOrderDetailAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(id);
    }

    private void initData(int id) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDERLIST_LIST_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("订单详情",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    OrderlistDetail orderlistDetail = gson.fromJson(gson.toJson(baseModel.getDatas()),OrderlistDetail.class);
                    setData(orderlistDetail);
                }
            }
        });

    }

    private void setData(OrderlistDetail orderlistDetail) {
        if (orderlistDetail==null) return;
        if (orderlistDetail.getOrderItemData()==null) return;

        if (orderlistDetail.getOrderItemData().getOrderProductList()==null) return;

        if (orderlistDetail.getOrderItemData().getOrderSetMealList()==null) return;

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCode())){
            code=orderlistDetail.getOrderItemData().getCode();
            tv_num.setText(code);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCreateDate())){
           createDate=orderlistDetail.getOrderItemData().getCreateDate();
            tv_time.setText(createDate);
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
