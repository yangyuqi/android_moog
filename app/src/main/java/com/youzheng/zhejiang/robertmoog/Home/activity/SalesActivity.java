package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.AddressDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderGoodsId;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderProductDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderSetMealDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.SaleData;
import com.youzheng.zhejiang.robertmoog.Model.Home.SaleDataBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;

import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnGoodsSuccessActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.StoreOrderlistDetailActivity;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;

public class SalesActivity extends BaseActivity {

    LinearLayout ll_address ;
    private String customerId ,addressId ;
    private ArrayList<ScanDatasBean> data = new ArrayList<>();
    private RecyclerView recyclerView ;
    SearchResultAdapter addapter ;
    int widWidth ;
    TextView tv_confrim ;
    Map<String,Object> map = new HashMap<>();
    private OptionsPickerView pvCustomTime;
    TextView tv_name ,tv_activity,tv_phone ,tv_details ,tv_get_state ,tv_cut_money_of_juan, tv_dispatching_type ,tv_get_money_type ,tv_get_tivket,tv_get_money_of_now ,tv_should_money ,edt_content ,tv_cut_money_of_store,tv_cut_money_of_promotion ,tv_get_ticket ,tv_all_count;
    String PickUpStatus ,ShoppingMethod ,paymentMethod;
    Switch sv_life ,sv_present ;
    private String payAmount ,assetId ,payValue ;
    EditText edt_door_ticket ;
    View rl_activity ;

    private ArrayList<CouponListBean> useCouponList  = new ArrayList<>();
    private ArrayList<CouponListBean> notUseCouponList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.sales_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        EventBus.getDefault().register(this);
        initView();

        initClick();
    }

    private void initClick() {
        tv_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PickUpStatus == null) {
                    showToast("选择提货方式");
                    return;
                }
                if (ShoppingMethod == null) {
                    showToast("选择配送方式");
                    return;
                }
                if (paymentMethod == null) {
                    showToast("选择收款方式");
                    return;
                }

                RemindDialog dialog = new RemindDialog(mContext, new RemindDialog.onSuccessClick() {
                    @Override
                    public void onSuccess() {
                        orderConfirm();
                    }
                },"1");
                dialog.show();
            }
        });
        findViewById(R.id.rl_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,LocationManageActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("customerId",customerId);
                startActivityForResult(intent,2);
            }
        });

        tv_get_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()),EnumsDatas.class);
                            if (enumsDatas.getEnums().size()>0){
                                for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                                    if (bean.getClassName().equals("PickUpStatus")){
                                        tv_get_state.setText(bean.getDatas().get(0).getDes());
                                        PickUpStatus = bean.getDatas().get(0).getId();
                                        final List<String> date = new ArrayList<String>();
                                        for (int i = 0; i < bean.getDatas().size(); i++) {
                                            date.add(bean.getDatas().get(i).getDes());
                                        }
                                        pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                                    if (date.get(options1).equals(bean.getDatas().get(i).getDes())) {
                                                        tv_get_state.setText(date.get(options1));
                                                        PickUpStatus = bean.getDatas().get(i).getId();
                                                    }
                                                }
                                            }
                                        }).setTitleText("选择提货方式").build();
                                        pvCustomTime.setPicker(date);
                                        pvCustomTime.show();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

        tv_dispatching_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()),EnumsDatas.class);
                            if (enumsDatas.getEnums().size()>0){
                                for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                                    if (bean.getClassName().equals("ShoppingMethod")){
                                        tv_dispatching_type.setText(bean.getDatas().get(0).getDes());
                                        ShoppingMethod = bean.getDatas().get(0).getId();
                                        final List<String> date = new ArrayList<String>();
                                        for (int i = 0; i < bean.getDatas().size(); i++) {
                                            date.add(bean.getDatas().get(i).getDes());
                                        }
                                        pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                                    if (date.get(options1).equals(bean.getDatas().get(i).getDes())) {
                                                        tv_dispatching_type.setText(date.get(options1));
                                                        ShoppingMethod = bean.getDatas().get(i).getId();
                                                    }
                                                }
                                            }
                                        }).setTitleText("选择配送方式").build();
                                        pvCustomTime.setPicker(date);
                                        pvCustomTime.show();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

        tv_get_money_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()),EnumsDatas.class);
                            if (enumsDatas.getEnums().size()>0){
                                for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                                    if (bean.getClassName().equals("PaymentMethod")){
                                        tv_get_money_type.setText(bean.getDatas().get(0).getDes());
                                        paymentMethod = bean.getDatas().get(0).getId();
                                        final List<String> date = new ArrayList<String>();
                                        for (int i = 0; i < bean.getDatas().size(); i++) {
                                            date.add(bean.getDatas().get(i).getDes());
                                        }
                                        pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                                    if (date.get(options1).equals(bean.getDatas().get(i).getDes())) {
                                                        tv_get_money_type.setText(date.get(options1));
                                                        paymentMethod = bean.getDatas().get(i).getId();
                                                    }
                                                }
                                            }
                                        }).setTitleText("选择收款方式").build();
                                        pvCustomTime.setPicker(date);
                                        pvCustomTime.show();
                                    }
                                }
                            }
                        }
                    }
                });
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
                if (payAmount==null){
                    return;
                }

                try {
                    if (Integer.parseInt(s.toString())<=Integer.parseInt(payAmount)){
                        initData();
                        tv_cut_money_of_store.setText("-¥"+s.toString());
                    }else {
                        showToast("门店优惠金额不能大于实际需付金额");
                    }

                }catch (Exception e){

                }

            }
        });

        tv_get_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CouponActivity.class);
                intent.putExtra("useCouponList",useCouponList);
                intent.putExtra("notUseCouponList",notUseCouponList);
                startActivityForResult(intent,3);
            }
        });
    }

    private void orderConfirm() {
        Map<String, Object> map = new HashMap<>();
        map.put("isFreeGift", sv_present.isChecked());
        map.put("isMoen", sv_life.isChecked());
        map.put("customerId", customerId);
        map.put("pickUpStatus", PickUpStatus);
        map.put("paymentMethod", paymentMethod);
        map.put("shopDerate",edt_door_ticket.getText().toString());
        map.put("shoppingMethod", ShoppingMethod);
        if (addressId!=null){
            map.put("addressId",addressId);
        }
        if (assetId!=null){
            map.put("assetId",assetId);
        }
        if (data.size() > 0) {
            int allCount = 0 ;
            List<OrderProductDatasBean> orderProductDatasBeans = new ArrayList<>();
            List<OrderSetMealDatasBean> orderSetMealDatasBeans = new ArrayList<>();
            for (ScanDatasBean scanDatasBean : data) {
                if (!scanDatasBean.isSetMeal()) {
                    OrderProductDatasBean datasBean = new OrderProductDatasBean();
                    datasBean.setNum("" + scanDatasBean.getNum());
                    datasBean.setProductId(scanDatasBean.getId());
                    if (scanDatasBean.isSpecial()){
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
            map.put("payAmount",payAmount);
            map.put("info",edt_content.getText().toString());
        }

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDER_DETAOLS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    OrderGoodsId goodsId = gson.fromJson(gson.toJson(baseModel.getDatas()),OrderGoodsId.class);
                    Intent intent = new Intent(mContext, ReturnGoodsSuccessActivity.class);
                    intent.putExtra("returnid",String.valueOf(goodsId.getId()));
                    intent.putExtra("type","2");//卖货柜台
                    startActivity(intent);
                    finish();
                }else {
                    showToast(baseModel.getMsg());
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {

        map.put("shopDerate",edt_door_ticket.getText().toString());
        map.put("customerId",customerId);
        if (addressId!=null){
            map.put("addressId",addressId);
        }
        if (assetId!=null){
            map.put("assetId",assetId);
        }

        if (data.size()>0){
            int allCount = 0 ;
            List<OrderProductDatasBean> orderProductDatasBeans = new ArrayList<>();
            List<OrderSetMealDatasBean> orderSetMealDatasBeans = new ArrayList<>();
            for (ScanDatasBean scanDatasBean :data){
                if (!scanDatasBean.isSetMeal()) {
                    OrderProductDatasBean datasBean = new OrderProductDatasBean();
                    datasBean.setNum("" + scanDatasBean.getNum());
                    allCount = allCount+scanDatasBean.getNum();
                    datasBean.setProductId(scanDatasBean.getId());
                    if (scanDatasBean.isSpecial()){
                        datasBean.setCodePU(scanDatasBean.getCodePU());
                        datasBean.setAddPrice(scanDatasBean.getAddPrice());
                        datasBean.setSquare(String.valueOf(scanDatasBean.getSquare()));
                    }
                    orderProductDatasBeans.add(datasBean);
                }else {
                    OrderSetMealDatasBean mealDatasBean = new OrderSetMealDatasBean();
                    mealDatasBean.setSetMealId(scanDatasBean.getId());
                    mealDatasBean.setNum("" + scanDatasBean.getNum());
                    allCount = allCount+scanDatasBean.getNum();
                    orderSetMealDatasBeans.add(mealDatasBean);
                }
            }
            map.put("orderProductDatas",orderProductDatasBeans);
            map.put("orderSetMealDatas",orderSetMealDatasBeans);
            tv_all_count.setText(""+allCount);
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SALES_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    SaleDataBean saleData = gson.fromJson(gson.toJson(baseModel.getDatas()),SaleDataBean.class);
                    payAmount = saleData.getSaleData().getPayAmount();
                    tv_get_money_of_now.setText("¥"+payAmount);
                    tv_should_money.setText("¥"+saleData.getSaleData().getAmountPayable());
                    tv_cut_money_of_promotion.setText("-¥"+saleData.getSaleData().getOrderDerate());
                    tv_cut_money_of_juan.setText("-¥"+saleData.getSaleData().getCouponDerate());
                    tv_cut_money_of_store.setText("-¥"+saleData.getSaleData().getShopDerate());
                    notUseCouponList = saleData.getSaleData().getNotUseCouponList();
                    useCouponList = saleData.getSaleData().getUseCouponList();
                    if (saleData.getSaleData().getAddressId()==null){
                        findViewById(R.id.rl_address).setVisibility(View.GONE);
                        findViewById(R.id.ll_address).setVisibility(View.VISIBLE);
                    }else {
                        findViewById(R.id.rl_address).setVisibility(View.VISIBLE);
                        findViewById(R.id.ll_address).setVisibility(View.GONE);
                        tv_name.setText(saleData.getSaleData().getShipPerson());
                        tv_phone.setText(PublicUtils.phoneNum(saleData.getSaleData().getShipMobile()));
                        tv_details.setText(""+saleData.getSaleData().getShipAddress());
                        addressId = saleData.getSaleData().getAddressId();
                    }
                    if (saleData.getSaleData().getRules()!=null){
                        findViewById(R.id.rl_activity).setVisibility(View.VISIBLE);
                        tv_activity.setText(saleData.getSaleData().getRules());
                    }
                    if (saleData.getSaleData().getUseCouponList().size()>0){
                        tv_get_tivket.setVisibility(View.VISIBLE);
                        if (clickTicket==null) {
                            tv_get_tivket.setText("" + saleData.getSaleData().getUseCouponList().size() + "张可用");
                        }else {
                            tv_get_tivket.setText("已选1张");
                        }
                    }else {
                        tv_get_tivket.setVisibility(View.GONE);
                        tv_get_ticket.setText("无优惠券");
                    }
                }
            }
        });
    }

    private String clickTicket ;

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText(R.string.seller_table);
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
                Intent intent = new Intent(mContext,LocationManageActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("type","1");
                startActivityForResult(intent,2);
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
        if (data.size()>0){
            addapter.setDate(data,SalesActivity.this,"3",widWidth);
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
        tv_all_count =findViewById(R.id.tv_all_count);
        rl_activity = findViewById(R.id.rl_activity);
        tv_activity = findViewById(R.id.tv_activity);
        tv_get_tivket = findViewById(R.id.tv_get_tivket);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode==2){
            addressId = ((AddressDatasBean)data.getSerializableExtra("address")).getAddressId();
            tv_name.setText(((AddressDatasBean)data.getSerializableExtra("address")).getShipPerson());
            tv_phone.setText(((AddressDatasBean)data.getSerializableExtra("address")).getShipMobile());
            tv_details.setText(((AddressDatasBean)data.getSerializableExtra("address")).getShipAddress());
        }

        if (requestCode==3&&resultCode==3){
            assetId = data.getStringExtra("assetId");
            payValue = data.getStringExtra("payValue");
//            tv_get_ticket.setText(payValue);
            clickTicket = "";
        }
        initData();
    }

    @Subscribe
    public void onEvent(ArrayList<ScanDatasBean> beanArrayList){
        if (beanArrayList!=null){
            data = beanArrayList;
            initData();
        }
    }
}
