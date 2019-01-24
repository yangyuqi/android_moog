package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.PeopleMangerActivity;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ChooseGoodsListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ReturnGoodsCounterAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseGoodsRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.ConfirmReturnRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsCounter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsSuccess;
import com.youzheng.zhejiang.robertmoog.Store.listener.CounterListener;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Store.view.SingleOptionsPicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 退货柜台界面ss
 */
public class ReturnGoodsCounterActivity extends BaseActivity implements View.OnClickListener,CounterListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private RecyclerView rv_list_one;
    /**
     * 请选择
     */
    private TextView tv_get_state;
    private LinearLayout lin_get_goods;
    /**
     * 请选择
     */
    private TextView tv_return_type;
    private LinearLayout lin_return_type;
    /**
     * 请选择
     */
    private TextView tv_return_reason;
    private LinearLayout lin_return_reason;
    /**
     * 5
     */
    private TextView tv_goods_number;
    /**
     * 应退金额
     */
    private TextView tv_get;
    /**  */
    private TextView tv_should_cut_money;
    /**
     * 实退金额
     */
    private TextView tv_dispatching;
    /**  */
    public static TextView tv_really_cut_money;
    /**
     * 确认退货
     */
    private TextView tv_confirm_return;

    private List<EnumsDatasBeanDatas> typelist=new ArrayList<>();
    private List<EnumsDatasBeanDatas> returnlist=new ArrayList<>();
    private List<EnumsDatasBeanDatas> reasonlist=new ArrayList<>();


    private List<String> type=new ArrayList<>();
    private List<String> returns=new ArrayList<>();
    private List<String> reason=new ArrayList<>();


    private List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> list=new ArrayList<>();
    private ReturnGoodsCounterAdapter adapter;

    private String returnId;
    private boolean isall;
    private List<ChooseGoodsRequest.OrderProductListBean> requests=new ArrayList<>();
    private String pick_state;
    private String paymentMethod;
    private String reasons;
    private String refundAmount;
    private String orderID;
    private List<ConfirmReturnRequest.ReshippedGoodsDataListBean> request=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_goods_counter);
        returnId=getIntent().getStringExtra("orderID");
        isall=getIntent().getBooleanExtra("is_all",false);
        requests= (List<ChooseGoodsRequest.OrderProductListBean>) getIntent().getSerializableExtra("request");
        initView();
        initGetDate();
        initData(returnId);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(String id){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
//        ChooseGoodsRequest.OrderProductListBean chooseGoodsRequest=new ChooseGoodsRequest.OrderProductListBean();
//        chooseGoodsRequest.setCount("");
//        chooseGoodsRequest.setOrderItemProductId("");
        map.put("orderProductList",requests);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.RETURN_COUNTER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("退货柜台",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ReturnGoodsCounter counter = gson.fromJson(gson.toJson(baseModel.getDatas()),ReturnGoodsCounter.class);
                    setData(counter);
                }else {
                    showToast(baseModel.getMsg());
                }
            }
        });
    }

    private void setData(ReturnGoodsCounter counter) {
          if (counter==null) return;
          if (counter.getReturnOrderInfo()==null) return;
          List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> beans=counter.getReturnOrderInfo().getProductList();
          if (beans.size()!=0){
              list.addAll(beans);
              adapter.setUI(beans);
          }
          int totals=0;
          if (beans.size()!=0){
              for (int i = 0; i < beans.size(); i++) {
                  int item= Integer.parseInt(beans.get(i).getRefundAmount());
                  int count=beans.get(i).getCount();
                  int all=item*count;
                  totals=totals+all;
              }

              tv_really_cut_money.setText(getString(R.string.label_money)+totals+"");

          }


            orderID=counter.getReturnOrderInfo().getId();
          if (counter.getReturnOrderInfo().getReturnCount()!=0){
              tv_goods_number.setText(counter.getReturnOrderInfo().getReturnCount()+"");
          }else {
              tv_goods_number.setText("0");
          }

          if (!TextUtils.isEmpty(counter.getReturnOrderInfo().getRefundAmount())){
              tv_should_cut_money.setText(getString(R.string.label_money)+counter.getReturnOrderInfo().getRefundAmount());
              refundAmount=counter.getReturnOrderInfo().getRefundAmount();
          }



    }

    private void initGetDate() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("获取时间枚举",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()),EnumsDatas.class);
                    if (enumsDatas.getEnums().size()>0){
                        for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                            if (bean.getClassName().equals("PickUpStatus")){//  TimeQuantum
                                List<EnumsDatasBeanDatas> list1=new ArrayList<>();
                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                    list1.add(bean.getDatas().get(i));
                                    type.add(bean.getDatas().get(i).getDes());
                                }
                                typelist=list1;
                            }
                        }


                        for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                            if (bean.getClassName().equals("RefundMethod")){//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();
                                List<EnumsDatasBeanDatas> list2=new ArrayList<>();
                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                    list2.add(bean.getDatas().get(i));
                                    returns.add(bean.getDatas().get(i).getDes());
                                }
                                returnlist=list2;
                            }
                        }


                        for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                            if (bean.getClassName().equals("ReturnReason")){//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();
                                List<EnumsDatasBeanDatas> list3=new ArrayList<>();
                                for (int i = 0; i < bean.getDatas().size(); i++) {
                                    list3.add(bean.getDatas().get(i));
                                    reason.add(bean.getDatas().get(i).getDes());
                                }
                                reasonlist=list3;
                            }
                        }

                    }

                }else {
                    showToast(baseModel.getMsg());
                }
            }
        });

    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.return_counter));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        rv_list_one = (RecyclerView) findViewById(R.id.rv_list_one);
        tv_get_state = (TextView) findViewById(R.id.tv_get_state);
        lin_get_goods = (LinearLayout) findViewById(R.id.lin_get_goods);
        lin_get_goods.setOnClickListener(this);
        tv_return_type = (TextView) findViewById(R.id.tv_return_type);
        lin_return_type = (LinearLayout) findViewById(R.id.lin_return_type);
        lin_return_type.setOnClickListener(this);
        tv_return_reason = (TextView) findViewById(R.id.tv_return_reason);
        lin_return_reason = (LinearLayout) findViewById(R.id.lin_return_reason);
        lin_return_reason.setOnClickListener(this);
        tv_goods_number = (TextView) findViewById(R.id.tv_goods_number);
        tv_get = (TextView) findViewById(R.id.tv_get);
        tv_should_cut_money = (TextView) findViewById(R.id.tv_should_cut_money);
        tv_dispatching = (TextView) findViewById(R.id.tv_dispatching);
        tv_really_cut_money = (TextView) findViewById(R.id.tv_really_cut_money);
        tv_confirm_return = (TextView) findViewById(R.id.tv_confirm_return);
        tv_confirm_return.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_one.setLayoutManager(manager);
        rv_list_one.setAdapter(adapter);
        rv_list_one.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(ReturnGoodsCounterActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        adapter=new ReturnGoodsCounterAdapter(list,this,this);
        rv_list_one.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.lin_get_goods:
                //SingleOptionsPicker.tv_choose_degree.setText("提货状态");
                SingleOptionsPicker.openOptionsPicker(this, type, tv_get_state,getString(R.string.get_goods_state));
                break;
            case R.id.lin_return_type:
               // SingleOptionsPicker.tv_choose_degree.setText("退款方式");
                SingleOptionsPicker.openOptionsPicker(this, returns, tv_return_type,getString(R.string.return_goods_type));
                break;
            case R.id.lin_return_reason:
                //SingleOptionsPicker.tv_choose_degree.setText("退货理由");
                SingleOptionsPicker.openOptionsPicker(this, reason, tv_return_reason,getString(R.string.return_reason));
                break;
            case R.id.tv_confirm_return:
                if (tv_get_state.getText().equals(getString(R.string.please_choose))){
                    showToast(getString(R.string.please_choose_get_type));
                }else if (tv_return_type.getText().equals(getString(R.string.please_choose))){
                    showToast(getString(R.string.please_choose_return_type));
                }else if (tv_return_type.getText().equals(getString(R.string.please_choose))){
                    showToast(getString(R.string.please_choose_return_reason));
                }else if (TextUtils.isEmpty(tv_really_cut_money.getText().toString())){
                    showToast(getString(R.string.please_write_really_money));
                }else {
                    showStopDialog();
                }
                break;
        }
    }

    private void confirmReturn() {

        if (tv_return_type.getText().equals(getString(R.string.bank_card))){
            paymentMethod="BANK_CARD";
        }else if (tv_return_type.getText().equals(getString(R.string.we_chat))){
            paymentMethod="WECHAT";
        }else if (tv_return_type.getText().equals(getString(R.string.alipay))){
            paymentMethod="ALIPAY";
        }else if (tv_return_type.getText().equals(getString(R.string.cash))){
            paymentMethod="CASH";
        }else if (tv_return_type.getText().equals(getString(R.string.market_get))){
            paymentMethod="MARKET";
        }else if (tv_return_type.getText().equals(getString(R.string.other))){
            paymentMethod="OTHER";
        }

        if (tv_get_state.getText().equals(getString(R.string.all_lift))){
            pick_state="ALL_LIFT";
        }else if (tv_get_state.getText().equals(getString(R.string.limit_lift))){
            pick_state="LIMIT_LIFT";
        }else if (tv_get_state.getText().equals(getString(R.string.no_lift))){
            pick_state="NO_LIFT";
        }

        if (tv_return_reason.getText().equals(getString(R.string.quality_problem))){
            reasons="QUALITY_PROBLEM";
        }else if (tv_return_reason.getText().equals(getString(R.string.brand_problem))){
            reasons="BRAND_PROBLEM";
        }else if (tv_return_reason.getText().equals(getString(R.string.price_problem))){
            reasons="PRICE_PROBLEM";
        }else if (tv_return_reason.getText().equals(getString(R.string.other))){
            reasons="OTHER";
        }
        HashMap<String,Object> map=new HashMap<>();
        map.put("pickUpStatus",pick_state);
        map.put("paymentMethod",paymentMethod);
        map.put("reason",reasons);
        map.put("refundAmount",refundAmount);
        map.put("actualRefundAmount",ReturnGoodsCounterAdapter.totals+"");
        map.put("id",orderID);

        if (list.size()!=0){
            for (ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean bean1:list){
                ConfirmReturnRequest.ReshippedGoodsDataListBean requestBean=new ConfirmReturnRequest.ReshippedGoodsDataListBean();
                requestBean.setOrderItemProductId(bean1.getOrderItemProductId());
                requestBean.setCount(bean1.getCount()+"");
                requestBean.setRefundAmount(bean1.getRefundAmount());
                requestBean.setActualRefundAmount(bean1.getMoney()+"");
                request.add(requestBean);
            }
        }
        map.put("reshippedGoodsDataList",request);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CONFIRM_RETURN + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("退货柜台",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ReturnGoodsSuccess success = gson.fromJson(gson.toJson(baseModel.getDatas()),ReturnGoodsSuccess.class);
                    toSuccess(success);
                }else {
                    showToast(getString(R.string.return_failure));
                    request.clear();
                }
            }
        });
    }

    private void toSuccess(ReturnGoodsSuccess success) {
        Intent intent=new Intent(this,ReturnGoodsSuccessActivity.class);
        intent.putExtra("returnid",success.getId()+"");
        intent.putExtra("type","1");
        startActivity(intent);
        finish();
    }

    public void showStopDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(ReturnGoodsCounterActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_return, null);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.show();
        dialogBuilder.setContentView(dialogView);

        TextView tv_no=dialogView.findViewById(R.id.tv_no);
        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 confirmReturn();
                dialogBuilder.dismiss();
            }
        });

        Window window = dialogBuilder.getWindow();
        //这句设置我们dialog在底部
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        DisplayMetrics d = this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.74); // 高度设置为屏幕的0.6
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    @Override
    public void getTotal(List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> list) {
        if (list!=null||list.size()!=0){
            int total=0;

            for (int i = 0; i <list.size() ; i++) {
                int item_total=list.get(i).getMoney();
                int count =list.get(i).getCount();
                int all=item_total*count;
                total=total+all;
            }

            tv_really_cut_money.setText(getString(R.string.label_money)+total+"");


        }
    }
}
