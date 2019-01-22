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

/**
 * 专业客户订单详情ss
 */
public class ProfessionalOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    /**  */
    private TextView mTextHeadTitle;
    /**  */
    private TextView mTextHeadNext;
    private ImageView mIvNext;
    private RelativeLayout mLayoutHeader;
    /**
     * 2016/01/19 09:19:04
     */
    private TextView mTvTime;
    /**
     * 987656201801020002
     */
    private TextView mTvNum;
    /**
     * 187 0000 0009
     */
    private TextView mTvReferee;
    /**
     * 李白(导购)
     */
    private TextView mTvMaker;
    /**
     * 187 0000 0009
     */
    private TextView mTvCustomer;
    /**
     * 刘思思
     */
    private TextView mTvName;
    /**
     * 187 0000 0009
     */
    private TextView mTvPhone;
    /**
     * 北京东城区内环三环银河sohoD座809
     */
    private TextView mTvAddress;
    /**
     * 安装进度
     */
    private TextView mTvProgress;
    /**
     * 已申请
     */
    private TextView mTvProgressState;
    private RecyclerView rv_list_one, rv_list_more;
    /**
     * 订单促销
     */
    private TextView mTvOrderPromotion;
    /**
     * 1000
     */
    private TextView mTvOverMoney;
    /**
     * ￥100
     */
    private TextView mTvCutMoney;
    /**
     * 属于摩恩全国活动
     */
    private TextView mTvBelong;
    /**
     * 未赠送礼品
     */
    private TextView mTvOrSend;
    /**
     * 提货
     */
    private TextView mTvGet;
    /**
     * 已提货
     */
    private TextView mTvGetState;
    /**
     * 配送
     */
    private TextView mTvDispatching;
    /**
     * 大仓配送
     */
    private TextView mTvDispatchingType;
    /**
     * 收款方式
     */
    private TextView mTvGetMoney;
    /**
     * 商场付款
     */
    private TextView mTvGetMoneyType;
    /**
     * ￥16698
     */
    private TextView mTvShouldMoney;
    /**
     * -￥8
     */
    private TextView mTvCutMoneyOfJuan;
    /**
     * -￥100
     */
    private TextView mTvCutMoneyOfPromotion;
    /**
     * -￥80
     */
    private TextView mTvCutMoneyOfStore;
    /**
     * ￥17700
     */
    private TextView mTvGetMoneyOfNow;
    /**
     * 该订单是长工CC推荐来的，未结算
     */
    private TextView mTvContent;
    /**
     * 提交修改
     */
    private TextView mTvCommit;
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
    /**
     * 提交修改
     */
    private TextView tv_commit,tv_goods_num;

    private List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> onelist=new ArrayList<>();
    private List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean> morelist=new ArrayList<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_order_detail);
        id=getIntent().getStringExtra("ProfessionalId");
        initView();
        initData(id);
    }

    private void initView() {

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("订单列表");
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
        rv_list_more = (RecyclerView) findViewById(R.id.rv_list_more);
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
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_goods_num=findViewById(R.id.tv_goods_num);
        lin_is_cut=findViewById(R.id.lin_is_cut);


        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_one.setLayoutManager(manager);
        rv_list_one.setAdapter(oneOrderDetailAdapter);
        rv_list_one.addItemDecoration(new RecycleViewDivider(ProfessionalOrderDetailActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));



        LinearLayoutManager manager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_more.setLayoutManager(manager2);
        rv_list_more.setAdapter(moreOrderDetailAdapter);
        rv_list_more.addItemDecoration(new RecycleViewDivider(ProfessionalOrderDetailActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));



        oneOrderDetailAdapter=new OneOrderDetailAdapter(onelist,this);
        rv_list_one.setAdapter(oneOrderDetailAdapter);


        moreOrderDetailAdapter=new MoreOrderDetailAdapter(morelist,this);
        rv_list_more.setAdapter(moreOrderDetailAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(String id) {
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


        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getAccount())){
            account=orderlistDetail.getOrderItemData().getAccount();
            tv_customer.setText(account);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getBusinessRole())){
            businessRole=orderlistDetail.getOrderItemData().getBusinessRole();
            if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCreateUser())){
                createUser=orderlistDetail.getOrderItemData().getCreateUser();
                tv_maker.setText(createUser+"("+businessRole+")");
            }
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShipPerson())){
            shipPerson=orderlistDetail.getOrderItemData().getShipPerson();
            tv_name.setText(shipPerson);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShipMobile())){
            shipMobile=orderlistDetail.getOrderItemData().getShipMobile();
            tv_phone.setText(settingphone(shipMobile));
        }


        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShipAddress())){
            shipAddress=orderlistDetail.getOrderItemData().getShipAddress();
            tv_address.setText(shipAddress);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getInstallStatus())){
            installStatus=orderlistDetail.getOrderItemData().getInstallStatus();
            tv_progress_state.setText(installStatus);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getMaxAmount())){
            maxAmount=orderlistDetail.getOrderItemData().getMaxAmount();
            tv_over_money.setText(maxAmount);
        }

        if (orderlistDetail.getOrderItemData().isIsOrderDerate()==true){
            tv_cut_money.setText(getString(R.string.label_money)+orderDerate);
        }else {
            lin_is_cut.setVisibility(View.GONE);
        }

        if (orderlistDetail.getOrderItemData().isIsMoen()==true){
            tv_belong.setVisibility(View.VISIBLE);
        }else {
            tv_belong.setVisibility(View.GONE);
        }


        if (orderlistDetail.getOrderItemData().isIsFreeGift()==true){
            tv_or_send.setText(getString(R.string.have_award));
        }else {
            tv_or_send.setText(getString(R.string.no_award));
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getPickUpStatus())){
            pickUpStatus=orderlistDetail.getOrderItemData().getPickUpStatus();
            tv_get_state.setText(pickUpStatus);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShoppingMethod())){
            shoppingMethod=orderlistDetail.getOrderItemData().getShoppingMethod();
            tv_dispatching_type.setText(shoppingMethod);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getPaymentMethod())){
            paymentMethod=orderlistDetail.getOrderItemData().getPaymentMethod();
            tv_get_money_type.setText(paymentMethod);
        }

        if(orderlistDetail.getOrderItemData().getProductCount()!=0){
            productCount=orderlistDetail.getOrderItemData().getProductCount();
            tv_goods_num.setText(productCount+"");
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getReturnOrderStatus())){
            returnOrderStatus=orderlistDetail.getOrderItemData().getReturnOrderStatus();
//            tv_goods_num.setText(productCount);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getAmountPayable())){
            amountPayable=orderlistDetail.getOrderItemData().getAmountPayable();
            tv_should_money.setText(amountPayable);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getCouponDerate())){
            couponDerate=orderlistDetail.getOrderItemData().getCouponDerate();
            tv_cut_money_of_juan.setText("-"+getString(R.string.label_money)+couponDerate);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getOrderDerate())){
            orderDerate=orderlistDetail.getOrderItemData().getOrderDerate();
            tv_cut_money_of_promotion.setText("-"+getString(R.string.label_money)+orderDerate);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getShopDerate())){
            shopDerate=orderlistDetail.getOrderItemData().getShopDerate();
            tv_cut_money_of_store.setText("-"+getString(R.string.label_money)+shopDerate);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getPayAmount())){
            payAmount=orderlistDetail.getOrderItemData().getPayAmount();
            tv_get_money_of_now.setText(getString(R.string.label_money)+payAmount);
        }

        if (!TextUtils.isEmpty(orderlistDetail.getOrderItemData().getComment())){
            comment=orderlistDetail.getOrderItemData().getComment();
            tv_content.setText(comment);
        }else {
            tv_content.setText("无");
        }



        List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> onelists=orderlistDetail.getOrderItemData().getOrderProductList();
        if (onelists.size()!=0){
            onelist.addAll(onelists);
            oneOrderDetailAdapter.setUI(onelist);
        }else {
            rv_list_one.setVisibility(View.GONE);
        }


        List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean> morelists=orderlistDetail.getOrderItemData().getOrderSetMealList();
        if (morelists.size()!=0){
            morelist.addAll(morelists);
            moreOrderDetailAdapter.setUI(morelist);
        }else {
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
            case R.id.tv_commit:
                break;
        }
    }
}
