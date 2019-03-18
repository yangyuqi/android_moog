package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Home.bean.SalesListener;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.AddressDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderGoodsId;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderProductDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderSetMealDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.SaleDataBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnGoodsSuccessActivity;
import com.youzheng.zhejiang.robertmoog.Store.listener.GetListener;
import com.youzheng.zhejiang.robertmoog.Store.view.SingleOptionsPicker;
import com.youzheng.zhejiang.robertmoog.utils.ClickUtils;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;

public class SalesActivity extends BaseActivity implements GetListener, SalesListener {

    LinearLayout ll_address;
    private String customerId, addressId;
    private ArrayList<ScanDatasBean> data = new ArrayList<>();
    private RecyclerView recyclerView;
    SearchResultAdapter addapter;
    int widWidth;
    TextView tv_confrim;
    Map<String, Object> map = new HashMap<>();
    private OptionsPickerView pvCustomTime;
    TextView tv_name, tv_activity, tv_phone, tv_details, tv_get_state, tv_cut_money_of_juan, tv_dispatching_type, tv_get_money_type, tv_get_tivket, tv_get_money_of_now, tv_should_money, edt_content, tv_cut_money_of_store, tv_cut_money_of_promotion, tv_get_ticket, tv_all_count;
    String PickUpStatus, ShoppingMethod, paymentMethod;
    Switch sv_life, sv_present;
    private String payAmount, assetId, payValue, old_pay;
    EditText edt_door_ticket;
    View rl_activity;
    int clickid;
    // private String get_state,dispatching_type,get_money_type;

    private ArrayList<CouponListBean> useCouponList = new ArrayList<>();
    private ArrayList<CouponListBean> notUseCouponList = new ArrayList<>();


    private List<String> get_state = new ArrayList<>();
    private List<String> dispatching_type = new ArrayList<>();
    private List<String> get_money_type = new ArrayList<>();
    private LinearLayout lin_send;
    private LinearLayout lin_juan;
    private LinearLayout lin_cuxiao;
    private LinearLayout lin_store;
    private boolean is_have_adress;
    private int no_adress;
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
    private LinearLayout mLinIsCut;
    private List<EnumsDatasBeanDatas> list1 = new ArrayList<>();
    private List<EnumsDatasBeanDatas> list2 = new ArrayList<>();
    private List<EnumsDatasBeanDatas> list3 = new ArrayList<>();
    private String should_moeny;
    private String edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.sales_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        EventBus.getDefault().register(this);
        initView();

        initGetDate();
        initClick();

//        initData("0");
//        initClick();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        no_adress = getIntent().getIntExtra("address_aa", 0);
        if (no_adress == 1) {
            is_have_adress = true;
        } else {
            is_have_adress = false;
            addressId = "";
        }
        if(notUseCouponList.size()==0){
            notUseCouponList.clear();
        }
        if(useCouponList.size()==0){
            useCouponList.clear();
        }

        initData(edt_door_ticket.getText().toString(), is_have_adress);
        Log.e("213213", "1");

    }


    private void initGetDate() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("获取时间枚举", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()), EnumsDatas.class);
                    if (enumsDatas.getEnums() != null) {
                        if (enumsDatas.getEnums().size() > 0) {

                            for (final EnumsDatasBean bean : enumsDatas.getEnums()) {
                                if (!TextUtils.isEmpty(bean.getClassName())) {
                                    if (bean.getClassName().equals("PickUpStatus")) {//
                                        // TimeQuantum
                                        list1 = new ArrayList<>();

                                        for (int i = 0; i < bean.getDatas().size(); i++) {
//                                              tv_get_state.setText(bean.getDatas().get(0).getDes());
                                            list1.add(bean.getDatas().get(i));
                                            get_state.add(bean.getDatas().get(i).getDes());
                                            if (bean.getDatas().get(i).getDes().equals(getString(R.string.all_lift))) {
                                                tv_get_state.setText(bean.getDatas().get(i).getDes());
                                                PickUpStatus = bean.getDatas().get(i).getId();
                                            }
                                        }

                                        if (tv_get_state.getText().toString().equals("")) {
                                            tv_get_state.setText(bean.getDatas().get(0).getDes());
                                            PickUpStatus = list1.get(0).getId();
                                        }


                                        // typelist=list1;
                                        if (tv_get_state.getText().toString().equals(getString(R.string.all_lift))) {
                                            lin_send.setVisibility(View.GONE);
                                        } else {
                                            lin_send.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }

                            }


                            for (final EnumsDatasBean bean : enumsDatas.getEnums()) {
                                if (bean.getClassName() != null) {
                                    if (bean.getClassName().equals("ShoppingMethod")) {//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();

                                        list2 = new ArrayList<>();

                                        for (int i = 0; i < bean.getDatas().size(); i++) {
                                            list2.add(bean.getDatas().get(i));
                                            dispatching_type.add(bean.getDatas().get(i).getDes());
                                            if (bean.getDatas().get(i).getDes().equals("顾客自提")) {
                                                tv_dispatching_type.setText(bean.getDatas().get(i).getDes());
                                                ShoppingMethod=bean.getDatas().get(i).getId();

                                            }

                                        }
                                        if (lin_send.getVisibility() == View.VISIBLE) {
                                            if (tv_dispatching_type.getText().toString().equals("")) {
                                                tv_dispatching_type.setText(bean.getDatas().get(0).getDes());
                                                ShoppingMethod = list1.get(0).getId();
                                            }
                                        }else {
                                            ShoppingMethod = "";
                                        }

//                                        if (lin_send.getVisibility() == View.VISIBLE) {
//                                            ShoppingMethod = list2.get(0).getId();
//                                        } else {
//                                            ShoppingMethod = "";
//                                        }
                                        // returnlist=list2;
                                    }
                                }

                            }


                            for (final EnumsDatasBean bean : enumsDatas.getEnums()) {
                                if (bean.getClassName() != null) {
                                    if (bean.getClassName().equals("PaymentMethod")) {//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();
                                        list3 = new ArrayList<>();
                                        for (int i = 0; i < bean.getDatas().size(); i++) {

                                            list3.add(bean.getDatas().get(i));
                                            get_money_type.add(bean.getDatas().get(i).getDes());
                                             if (bean.getDatas().get(i).getDes().equals("银行卡")){
                                                 tv_get_money_type.setText(bean.getDatas().get(i).getDes());
                                                 paymentMethod = list3.get(i).getId();
                                             }
                                        }
                                        if (tv_get_money_type.getText().toString().equals("")) {
                                            tv_get_money_type.setText(bean.getDatas().get(0).getDes());
                                            paymentMethod = list1.get(0).getId();
                                        }

//                                        paymentMethod = list3.get(0).getId();
                                        //  reasonlist=list3;
                                    }
                                }

                            }

                        }
                    }


                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });

    }

    @Override
    public void getTextStr(String str, String title, int position) {
        if (title.equals(getString(R.string.get_type))) {
            if (list1 != null) {
                if (list1.size() != 0) {
                    PickUpStatus = list1.get(position).getId();
                }
            }

            Log.e("1111", "list1-----" + list1.size() + "------" + PickUpStatus);
            if (str.equals(getString(R.string.all_lift))) {
                lin_send.setVisibility(View.GONE);
                ShoppingMethod = "";
            } else {
                lin_send.setVisibility(View.VISIBLE);
                if (list2 != null) {
                    if (list2.size() != 0) {
                        for (int i = 0; i < list2.size(); i++) {
                            if (list2.get(i).getDes().equals("顾客自提")){
                                tv_dispatching_type.setText(list2.get(i).getDes());
                                ShoppingMethod = list2.get(i).getId();
                            }
                        }
                        if (tv_dispatching_type.getText().toString().equals("")){
                            tv_dispatching_type.setText(list2.get(0).getDes());
                            ShoppingMethod = list2.get(0).getId();
                        }

                    }
                }
            }
        } else if (title.equals(getString(R.string.get_money_type))) {
            if (list3 != null) {
                if (list3.size() != 0) {
                    paymentMethod = list3.get(position).getId();
                }
            }


            Log.e("1111", "list2-----" + list2.size() + "------" + paymentMethod);
        } else if (title.equals(getString(R.string.send_type))) {
            if (list3 != null) {
                if (list3.size() != 0) {
                    ShoppingMethod = list2.get(position).getId();
                }
            }


            Log.e("1111", "list3-----" + list3.size() + "------" + ShoppingMethod);
        }
    }

    private void initClick() {
        tv_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tv_get_state.getText().toString())) {
                    showToasts("选择提货方式");
                    return;
                }
//                if (TextUtils.isEmpty(tv_dispatching_type.getText().toString())) {
//                    showToast("选择配送方式");
//                    return;
//                }
                if (TextUtils.isEmpty(tv_get_money_type.getText().toString())) {
                    showToasts("选择收款方式");
                    return;
                }

//                if (TextUtils.isEmpty(addressId)) {
//                    showToast("请添加收货地址");
//                    return;
//                }

                if (ClickUtils.isFastDoubleClick()){
                    return;
                }else {
                    edit=edt_door_ticket.getText().toString();
                    RemindDialog dialog = new RemindDialog(mContext, new RemindDialog.onSuccessClick() {
                        @Override
                        public void onSuccess() {
                            orderConfirm();
                        }
                    }, "1");
                    dialog.show();
                }


            }
        });
        findViewById(R.id.rl_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LocationManageActivity.class);
                intent.putExtra("type", "1");
                intent.putExtra("use", "1");
                intent.putExtra("customerId", customerId);
                startActivityForResult(intent, 2);
            }
        });

        tv_get_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (get_state != null) {
                    if (get_state.size() != 0) {
                        SingleOptionsPicker.openOptionsPicker(SalesActivity.this, get_state, tv_get_state, getString(R.string.get_type), SalesActivity.this);
                    } else {
                        showToasts("暂无数据");
                    }
                } else {
                    showToasts("暂无数据");
                }

            }
        });

        tv_dispatching_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dispatching_type != null) {
                    if (dispatching_type.size() != 0) {
                        SingleOptionsPicker.openOptionsPicker(SalesActivity.this, dispatching_type, tv_dispatching_type, getString(R.string.send_type), SalesActivity.this);

                    } else {
                        showToasts("暂无数据");
                    }
                } else {
                    showToasts("暂无数据");
                }

            }
        });

        tv_get_money_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (get_money_type != null) {
                    if (get_money_type.size() != 0) {
                        SingleOptionsPicker.openOptionsPicker(SalesActivity.this, get_money_type, tv_get_money_type, getString(R.string.get_money_type), SalesActivity.this);

                    } else {
                        showToasts("暂无数据");
                    }
                } else {
                    showToasts("暂无数据");
                }

            }
        });

        edt_door_ticket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (payAmount == null) {
                    return;
                }

                if (s.length() != 0) {
                    initData(s.toString(), is_have_adress);
                    Log.e("213213", payAmount+"-----------"+s.toString());

                    // payAmount=old_pay;
                    //tv_get_money_of_now.setText("¥"+old_pay);
                    try {
                        if (Integer.parseInt(s.toString()) <= Integer.parseInt(payAmount)-Integer.parseInt(s.toString())-Integer.parseInt(payValue)) {


                            tv_cut_money_of_store.setText("-¥" + s.toString());
                        } else {
                            showToasts("门店优惠金额不能大于实收金额");
                            edt_door_ticket.setText("");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        // tv_get_money_of_now.setText("¥"+old_pay);
                    }
                } else {
                    //edt_door_ticket.setText("");
                    initData("0", is_have_adress);
                    Log.e("213213", "3");
                }


            }
        });

//        //从这个界面跳进来的？嗯嗯
//        tv_get_ticket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CouponActivity.class);
//                intent.putExtra("useCouponList", useCouponList);
//                intent.putExtra("notUseCouponList", notUseCouponList);
//                startActivityForResult(intent, 3);
//            }
//        });
    }


    private void orderConfirm() {
        Map<String, Object> map = new HashMap<>();
        map.put("isFreeGift", sv_present.isChecked());
        map.put("isMoen", sv_life.isChecked());
        map.put("customerId", customerId);

//        if (tv_get_state.getText().equals(getString(R.string.all_lift))) {
//            PickUpStatus = "ALL_LIFT";
//        } else if (tv_get_state.getText().equals(getString(R.string.limit_lift))) {
//            PickUpStatus = "LIMIT_LIFT";
//        } else if (tv_get_state.getText().equals(getString(R.string.no_lift))) {
//            PickUpStatus = "NO_LIFT";
//        }
//
//        if (tv_get_money_type.getText().equals(getString(R.string.bank_card))) {
//            paymentMethod = "BANK_CARD";
//        } else if (tv_get_money_type.getText().equals(getString(R.string.we_chat))) {
//            paymentMethod = "WECHAT";
//        } else if (tv_get_money_type.getText().equals(getString(R.string.alipay))) {
//            paymentMethod = "ALIPAY";
//        } else if (tv_get_money_type.getText().equals(getString(R.string.cash))) {
//            paymentMethod = "CASH";
//        } else if (tv_get_money_type.getText().equals(getString(R.string.market_get))) {
//            paymentMethod = "MARKET";
//        } else if (tv_get_money_type.getText().equals(getString(R.string.other))) {
//            paymentMethod = "OTHER";
//        }
//
//        if (tv_dispatching_type.getText().equals("顾客自提")) {
//            ShoppingMethod = "CUSTOMER_SELF_EXTRACTION";
//        } else if (tv_dispatching_type.getText().equals("大仓配送")) {
//            ShoppingMethod = "LARGE_WAREHOUSE_DELIVERY";
//        } else if (tv_dispatching_type.getText().equals("")) {
//            ShoppingMethod = "";
//        }


        map.put("pickUpStatus", PickUpStatus);
        map.put("paymentMethod", paymentMethod);
        map.put("shopDerate", edit);
        map.put("shoppingMethod", ShoppingMethod);
        if (!TextUtils.isEmpty(addressId)) {
            map.put("addressId", addressId);
        }
        if (!TextUtils.isEmpty(assetId)) {
            map.put("assetId", assetId);
        }
        if (data.size() > 0) {
            int allCount = 0;
            List<OrderProductDatasBean> orderProductDatasBeans = new ArrayList<>();
            List<OrderSetMealDatasBean> orderSetMealDatasBeans = new ArrayList<>();
            for (ScanDatasBean scanDatasBean : data) {
                if (!scanDatasBean.isSetMeal()) {
                    OrderProductDatasBean datasBean = new OrderProductDatasBean();
                    datasBean.setNum("" + scanDatasBean.getNum());
                    datasBean.setProductId(scanDatasBean.getId());
                    if (scanDatasBean.isSpecial()) {
                        datasBean.setCodePU(scanDatasBean.getCodePU());
                        datasBean.setAddPrice(scanDatasBean.getAddPrice());
                        datasBean.setSquare(String.valueOf(scanDatasBean.getSquare()));
                    }
                    orderProductDatasBeans.add(datasBean);
                } else {
                    OrderSetMealDatasBean mealDatasBean = new OrderSetMealDatasBean();
                    mealDatasBean.setSetMealId(scanDatasBean.getId());
                    mealDatasBean.setNum("" + scanDatasBean.getNum());
                    orderSetMealDatasBeans.add(mealDatasBean);
                }
            }
            map.put("orderProductDatas", orderProductDatasBeans);
            map.put("orderSetMealDatas", orderSetMealDatasBeans);
            map.put("payAmount", payAmount);
            map.put("info", edt_content.getText().toString());
        }

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDER_DETAOLS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    OrderGoodsId goodsId = gson.fromJson(gson.toJson(baseModel.getDatas()), OrderGoodsId.class);
                    Intent intent = new Intent(mContext, ReturnGoodsSuccessActivity.class);
                    intent.putExtra("returnid", String.valueOf(goodsId.getId()));
                    intent.putExtra("type", "2");//卖货柜台
                    startActivity(intent);
                    finish();
                    CaptureActivity.instance.finish();
                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });

    }


    private void initData(String num, boolean is_have_adress) {

        map.put("shopDerate", num);
        map.put("isUse", is_have_adress);

        map.put("customerId", customerId);
        if (addressId != null) {
            map.put("addressId", addressId);
        }
        if (assetId != null) {
            map.put("assetId", assetId);
        }

        if (data.size() > 0) {
            int allCount = 0;
            List<OrderProductDatasBean> orderProductDatasBeans = new ArrayList<>();
            List<OrderSetMealDatasBean> orderSetMealDatasBeans = new ArrayList<>();
            for (ScanDatasBean scanDatasBean : data) {
                if (!scanDatasBean.isSetMeal()) {
                    OrderProductDatasBean datasBean = new OrderProductDatasBean();
                    datasBean.setNum("" + scanDatasBean.getNum());
                    allCount = allCount + scanDatasBean.getNum();
                    datasBean.setProductId(scanDatasBean.getId());
                    if (scanDatasBean.isSpecial()) {
                        datasBean.setCodePU(scanDatasBean.getCodePU());
                        datasBean.setAddPrice(scanDatasBean.getAddPrice());
                        datasBean.setSquare(String.valueOf(scanDatasBean.getSquare()));
                    }
                    orderProductDatasBeans.add(datasBean);
                } else {
                    OrderSetMealDatasBean mealDatasBean = new OrderSetMealDatasBean();
                    mealDatasBean.setSetMealId(scanDatasBean.getId());
                    mealDatasBean.setNum("" + scanDatasBean.getNum());
                    allCount = allCount + scanDatasBean.getNum();
                    orderSetMealDatasBeans.add(mealDatasBean);
                }
            }
            map.put("orderProductDatas", orderProductDatasBeans);
            map.put("orderSetMealDatas", orderSetMealDatasBeans);
            tv_all_count.setText("" + allCount);
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SALES_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    SaleDataBean saleData = gson.fromJson(gson.toJson(baseModel.getDatas()), SaleDataBean.class);
                    payAmount = saleData.getSaleData().getPayAmount();
                   // old_pay = saleData.getSaleData().getPayAmount();
                    tv_get_money_of_now.setText("¥" + payAmount);
                    tv_should_money.setText("¥" + saleData.getSaleData().getAmountPayable());
                    should_moeny=saleData.getSaleData().getAmountPayable();
                    if (!TextUtils.isEmpty(saleData.getSaleData().getRules())) {
                        tv_over_money.setText(saleData.getSaleData().getRules());
                        mLinIsCut.setVisibility(View.VISIBLE);
                    } else {
                        mLinIsCut.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(saleData.getSaleData().getOrderDerate())) {
                        if (saleData.getSaleData().getOrderDerate().equals("0")) {
                            lin_cuxiao.setVisibility(View.GONE);
                        } else {
                            lin_cuxiao.setVisibility(View.VISIBLE);
                            tv_cut_money_of_promotion.setText("-¥" + saleData.getSaleData().getOrderDerate());
                        }
                    } else {
                        lin_cuxiao.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(saleData.getSaleData().getShopDerate())) {
                        if (saleData.getSaleData().getShopDerate().equals("0")) {
                            lin_store.setVisibility(View.GONE);
                        } else {
                            lin_store.setVisibility(View.VISIBLE);
                            tv_cut_money_of_store.setText("-¥" + saleData.getSaleData().getShopDerate());
                        }

                    } else {
                        lin_store.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(saleData.getSaleData().getCouponDerate())) {
                        if (saleData.getSaleData().getCouponDerate().equals("0")) {
                            lin_juan.setVisibility(View.GONE);
                        } else {
                            lin_juan.setVisibility(View.VISIBLE);
                            tv_cut_money_of_juan.setText("-¥" + saleData.getSaleData().getCouponDerate());
                        }
                    } else {
                        lin_juan.setVisibility(View.GONE);
                    }




                    if(notUseCouponList.size()==0){
                        notUseCouponList = saleData.getSaleData().getNotUseCouponList();
                    }
                    if(useCouponList.size()==0){
                        useCouponList = saleData.getSaleData().getUseCouponList();
                    }


                    if (TextUtils.isEmpty(saleData.getSaleData().getAddressId())) {
                        findViewById(R.id.rl_address).setVisibility(View.GONE);
                        findViewById(R.id.ll_address).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.rl_address).setVisibility(View.VISIBLE);
                        findViewById(R.id.ll_address).setVisibility(View.GONE);
                        tv_name.setText(saleData.getSaleData().getShipPerson());
                        tv_phone.setText(PublicUtils.phoneNum(saleData.getSaleData().getShipMobile()));
                        tv_details.setText("" + saleData.getSaleData().getShipAddress());
                        addressId = saleData.getSaleData().getAddressId();
                    }
                    if (saleData.getSaleData().getRules() != null) {
                        findViewById(R.id.rl_activity).setVisibility(View.GONE);
                        tv_activity.setText(saleData.getSaleData().getRules());
                    }
                    if (saleData.getSaleData().getUseCouponList().size() > 0) {
                        tv_get_tivket.setVisibility(View.VISIBLE);
                        if (clickTicket == null) {
                            tv_get_tivket.setText("" + saleData.getSaleData().getUseCouponList().size() + "张可用");
                        } else {
                            tv_get_tivket.setText("已选1张");
                        }
                    } else {

                        if (saleData.getSaleData().getNotUseCouponList().size() > 0) {
                            tv_get_tivket.setVisibility(View.GONE);
                            tv_get_ticket.setText("无可用");
                        } else {
                            tv_get_tivket.setVisibility(View.GONE);
                            tv_get_ticket.setText("无优惠券");
                            tv_get_ticket.setEnabled(false);
                        }
                    }

                    try {
                        tv_get_ticket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, CouponActivity.class);
                                intent.putExtra("useCouponList", useCouponList);
                                intent.putExtra("notUseCouponList", notUseCouponList);
                                startActivityForResult(intent, 3);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                } else {
                    if (!TextUtils.isEmpty(baseModel.getMsg())) {
                        showToasts(baseModel.getMsg());
                    }
                }
            }
        });
    }

    private String clickTicket;

    private void initView() {
        lin_send = findViewById(R.id.lin_send);
        ((TextView) findViewById(R.id.textHeadTitle)).setText(R.string.seller_table);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_next).setVisibility(View.GONE);
        ll_address = findViewById(R.id.ll_address);
        customerId = getIntent().getStringExtra("customerId");
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LocationManageActivity.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("type", "1");
                intent.putExtra("use", "1");
                startActivityForResult(intent, 2);
            }
        });
        tv_confrim = findViewById(R.id.tv_confrim);
        data = (ArrayList<ScanDatasBean>) getIntent().getSerializableExtra("data");
        recyclerView = findViewById(R.id.recycler_view);
        addapter = new SearchResultAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(addapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(SalesActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));
        if (data.size() > 0) {
            addapter.setDate(data, SalesActivity.this, "3", widWidth);
        }
        tv_details = findViewById(R.id.tv_details);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_get_state = findViewById(R.id.tv_get_state);
        tv_dispatching_type = findViewById(R.id.tv_dispatching_type);
        tv_get_money_type = findViewById(R.id.tv_get_money_type);
        sv_life = findViewById(R.id.sv_life);
        sv_present = findViewById(R.id.sv_present);
        tv_get_money_of_now = findViewById(R.id.tv_get_money_of_now);
        tv_should_money = findViewById(R.id.tv_should_money);
        edt_content = findViewById(R.id.edt_content);
        edt_door_ticket = findViewById(R.id.edt_door_ticket);
        tv_cut_money_of_store = findViewById(R.id.tv_cut_money_of_store);
        tv_cut_money_of_promotion = findViewById(R.id.tv_cut_money_of_promotion);
        tv_get_ticket = findViewById(R.id.tv_get_ticket);
        tv_cut_money_of_juan = findViewById(R.id.tv_cut_money_of_juan);
        tv_all_count = findViewById(R.id.tv_all_count);
        rl_activity = findViewById(R.id.rl_activity);
        tv_activity = findViewById(R.id.tv_activity);
        tv_get_tivket = findViewById(R.id.tv_get_tivket);

        InputFilter inputFilter = new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    showToasts("只能输入汉字,英文,数字");
                    return "";
                }

            }
        };

        edt_content.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(100)});
        lin_juan = (LinearLayout) findViewById(R.id.lin_juan);
        lin_cuxiao = (LinearLayout) findViewById(R.id.lin_cuxiao);
        lin_store = (LinearLayout) findViewById(R.id.lin_store);
        tv_order_promotion = (TextView) findViewById(R.id.tv_order_promotion);
        tv_over_money = (TextView) findViewById(R.id.tv_over_money);
        tv_cut_money = (TextView) findViewById(R.id.tv_cut_money);
        mLinIsCut = (LinearLayout) findViewById(R.id.lin_is_cut);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {
            addressId = ((AddressDatasBean) data.getSerializableExtra("address")).getAddressId();
            tv_name.setText(((AddressDatasBean) data.getSerializableExtra("address")).getShipPerson());
            tv_phone.setText(((AddressDatasBean) data.getSerializableExtra("address")).getShipMobile());
            tv_details.setText(((AddressDatasBean) data.getSerializableExtra("address")).getShipAddress());
            initData(edt_door_ticket.getText().toString(), is_have_adress);
            Log.e("213213", "4");
        }

        if (requestCode == 3 && resultCode == 3) {
            assetId = data.getStringExtra("assetId");
            payValue = data.getStringExtra("payValue");


//            intent.putExtra("useCouponList", useCouponList);
//            intent.putExtra("notUseCouponList", notUseCouponList);

            ArrayList<CouponListBean> useCouponList = (ArrayList<CouponListBean>) data.getSerializableExtra("useCouponList");
            ArrayList<CouponListBean> notUseCouponList = (ArrayList<CouponListBean>) data.getSerializableExtra("notUseCouponList");
            if (useCouponList != null) {
                this.useCouponList = useCouponList;
            }
            if (notUseCouponList != null) {
                this.notUseCouponList = notUseCouponList;
            }
            edit="";
            edt_door_ticket.setText("");
            initData(edt_door_ticket.getText().toString(), is_have_adress);
            Log.e("213213", "5");
            if (assetId.equals("")) {
                tv_get_ticket.setText("未使用");
                clickTicket = null;
            } else {
                tv_get_ticket.setText(getString(R.string.label_money) + payValue);
                clickTicket = "";
            }
        }


    }

    @Subscribe
    public void onEvent(ArrayList<ScanDatasBean> beanArrayList) {
        if (beanArrayList != null) {
            data = beanArrayList;
            edt_door_ticket.setText("");
            edit="";
            initData(edt_door_ticket.getText().toString(), is_have_adress);
            Log.e("213213", "6");
        }
    }


    @Override
    public void refreshData(EditText editText) {

    }
}
