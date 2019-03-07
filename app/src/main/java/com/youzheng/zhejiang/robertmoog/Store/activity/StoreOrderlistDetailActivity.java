package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.MoreOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OneOrderDetailAdapter;
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
     * 2016/01/19 09:19:04ssss
     */
    private TextView tv_time;
    /**
     * 987656201801020002sss
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
    private RecyclerView rv_list_one, rv_list_more;
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
    private TextView tv_get_money_of_now, tv_goods_num;
    /**
     * 该订单是长工CC推荐来的，未结算
     */
    private TextView tv_content;

    private List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> onelist = new ArrayList<>();
    private List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean> morelist = new ArrayList<>();

    private OneOrderDetailAdapter oneOrderDetailAdapter;
    private MoreOrderDetailAdapter moreOrderDetailAdapter;
    private String id;
    private LinearLayout lin_is_cut;

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

    private int productCount;
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
    private LinearLayout lin_send;
    private LinearLayout lin_juan;
    private LinearLayout lin_cuxiao;
    private LinearLayout lin_store;
    private LinearLayout lin_address;
    private View no_data,no_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orderlist_detail);
        id = getIntent().getStringExtra("OrderGoodsId");
        initView();
        initData(id);
    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
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
        textHeadTitle.setText(getString(R.string.order_detail));
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
        rv_list_more = findViewById(R.id.rv_list_more);
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
        lin_is_cut = findViewById(R.id.lin_is_cut);
        tv_goods_num = findViewById(R.id.tv_goods_num);
        lin_send = findViewById(R.id.lin_send);


        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_one.setLayoutManager(manager);
        rv_list_one.setAdapter(oneOrderDetailAdapter);
        rv_list_one.addItemDecoration(new RecycleViewDivider(StoreOrderlistDetailActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        LinearLayoutManager manager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_more.setLayoutManager(manager2);
        rv_list_more.setAdapter(moreOrderDetailAdapter);
        rv_list_more.addItemDecoration(new RecycleViewDivider(StoreOrderlistDetailActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        oneOrderDetailAdapter = new OneOrderDetailAdapter(onelist, this);
        rv_list_one.setAdapter(oneOrderDetailAdapter);


        moreOrderDetailAdapter = new MoreOrderDetailAdapter(morelist, this);
        rv_list_more.setAdapter(moreOrderDetailAdapter);


        lin_juan = (LinearLayout) findViewById(R.id.lin_juan);
        lin_cuxiao = (LinearLayout) findViewById(R.id.lin_cuxiao);
        lin_store = (LinearLayout) findViewById(R.id.lin_store);
        lin_address = (LinearLayout) findViewById(R.id.lin_address);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDERLIST_LIST_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("订单详情", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    OrderlistDetail orderlistDetail = gson.fromJson(gson.toJson(baseModel.getDatas()), OrderlistDetail.class);
                    setData(orderlistDetail);
                } else {
                    showToast(baseModel.getMsg());
                }
            }
        });

    }

    private void setData(OrderlistDetail orderlistDetail) {
        if (orderlistDetail == null) return;
        if (orderlistDetail.getOrderItemData() == null) return;

        if (orderlistDetail.getOrderItemData().getOrderProductList() == null) return;

        if (orderlistDetail.getOrderItemData().getOrderSetMealList() == null) return;

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCode())) {
            code = orderlistDetail.getOrderItemData().getCode();
            tv_num.setText(code);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCreateDate())) {
            createDate = orderlistDetail.getOrderItemData().getCreateDate();
            tv_time.setText(createDate);
        }


        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getAccount())) {
            account = orderlistDetail.getOrderItemData().getAccount();
            tv_customer.setText(account);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getBusinessRole())) {
            businessRole = orderlistDetail.getOrderItemData().getBusinessRole();
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCreateUser())) {
            createUser = orderlistDetail.getOrderItemData().getCreateUser();
            tv_maker.setText(createUser);
        }



        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShipPerson())) {
            shipPerson = orderlistDetail.getOrderItemData().getShipPerson();
            tv_name.setText(shipPerson);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShipMobile())) {
            shipMobile = orderlistDetail.getOrderItemData().getShipMobile();
            tv_phone.setText(settingphone(shipMobile));
        }


        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShipAddress())) {
            shipAddress = orderlistDetail.getOrderItemData().getShipAddress();
            tv_address.setText(shipAddress);
        }


        if (TextUtils.isEmpty(shipPerson)&&TextUtils.isEmpty(shipMobile)&&TextUtils.isEmpty(shipAddress)){
            lin_address.setVisibility(View.GONE);
        }else {
            lin_address.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getInstallStatus())) {
            installStatus = orderlistDetail.getOrderItemData().getInstallStatus();
            tv_progress_state.setText(installStatus);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getMaxAmount())) {
            maxAmount = orderlistDetail.getOrderItemData().getMaxAmount();
            tv_over_money.setText(maxAmount);
        }

        if (orderlistDetail.getOrderItemData().isIsOrderDerate() == true) {
            tv_cut_money.setText(getString(R.string.label_money) + orderDerate);
        } else {
            lin_is_cut.setVisibility(View.GONE);
        }

        if (orderlistDetail.getOrderItemData().isIsMoen() == true) {
            tv_belong.setText("属于摩恩全国活动");
        } else {
            tv_belong.setText("不属于摩恩全国活动");
        }


        if (orderlistDetail.getOrderItemData().isIsFreeGift() == true) {
            tv_or_send.setText(getString(R.string.have_award));
        } else {
            tv_or_send.setText(getString(R.string.no_award));
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getPickUpStatus())) {
            pickUpStatus = orderlistDetail.getOrderItemData().getPickUpStatus();
            tv_get_state.setText(pickUpStatus);
        }

          if (pickUpStatus.equals("全部已提")){
              lin_send.setVisibility(View.GONE);
          }else {
              lin_send.setVisibility(View.VISIBLE);
          }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShoppingMethod())) {
           // shoppingMethod = orderlistDetail.getOrderItemData().getShoppingMethod();
            tv_dispatching_type.setText(orderlistDetail.getOrderItemData().getShoppingMethod());
            //lin_send.setVisibility(View.VISIBLE);
        } else {
           // lin_send.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getPaymentMethod())) {
            paymentMethod = orderlistDetail.getOrderItemData().getPaymentMethod();
            tv_get_money_type.setText(paymentMethod);
        }

        if (orderlistDetail.getOrderItemData().getProductCount() != 0) {
            productCount = orderlistDetail.getOrderItemData().getProductCount();
            tv_goods_num.setText(productCount + "");
        }

//        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getReturnOrderStatus())){
//            returnOrderStatus=orderlistDetail.getOrderItemData().getReturnOrderStatus();
////            tv_goods_num.setText(productCount);
//        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getAmountPayable())) {
            amountPayable = orderlistDetail.getOrderItemData().getAmountPayable();
            tv_should_money.setText(getString(R.string.label_money) + amountPayable);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCouponDerate())) {
            if (orderlistDetail.getOrderItemData().getCouponDerate().equals("0")) {
                lin_juan.setVisibility(View.GONE);
            } else {
                couponDerate = orderlistDetail.getOrderItemData().getCouponDerate();
                lin_juan.setVisibility(View.VISIBLE);
                tv_cut_money_of_juan.setText("-" + getString(R.string.label_money) + couponDerate);
            }
        } else {
            lin_juan.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getOrderDerate())) {
            if (orderlistDetail.getOrderItemData().getOrderDerate().equals("0")) {
                lin_cuxiao.setVisibility(View.GONE);
            } else {
                orderDerate = orderlistDetail.getOrderItemData().getOrderDerate();
                lin_cuxiao.setVisibility(View.VISIBLE);
                tv_cut_money_of_promotion.setText("-" + getString(R.string.label_money) + orderDerate);
            }

        } else {
            lin_cuxiao.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShopDerate())) {
            if (orderlistDetail.getOrderItemData().getShopDerate().equals("0")) {
                lin_store.setVisibility(View.GONE);
            } else {
                shopDerate = orderlistDetail.getOrderItemData().getShopDerate();
                lin_store.setVisibility(View.VISIBLE);
                tv_cut_money_of_store.setText("-" + getString(R.string.label_money) + shopDerate);
            }

        } else {
            lin_store.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getPayAmount())) {
            payAmount = orderlistDetail.getOrderItemData().getPayAmount();
            tv_get_money_of_now.setText(getString(R.string.label_money) + payAmount);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getComment())) {
            comment = orderlistDetail.getOrderItemData().getComment();
            tv_content.setText(comment);
        } else {
            tv_content.setText(getString(R.string.have_no));
        }


        List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> onelists = orderlistDetail.getOrderItemData().getOrderProductList();
        if (onelists.size() != 0) {
            onelist.addAll(onelists);
            oneOrderDetailAdapter.setUI(onelist);
        } else {
            rv_list_one.setVisibility(View.GONE);
        }


        List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean> morelists = orderlistDetail.getOrderItemData().getOrderSetMealList();
        if (morelists.size() != 0) {
            morelist.addAll(morelists);
            moreOrderDetailAdapter.setUI(morelist);
        } else {
            rv_list_more.setVisibility(View.GONE);
        }
    }

    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
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
