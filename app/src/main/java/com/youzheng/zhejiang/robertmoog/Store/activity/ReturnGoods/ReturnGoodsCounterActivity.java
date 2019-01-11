package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

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
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ChooseGoodsListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ReturnGoodsCounterAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseGoodsRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsCounter;
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
public class ReturnGoodsCounterActivity extends BaseActivity implements View.OnClickListener {

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
    private TextView tv_really_cut_money;
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

    private int returnId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_goods_counter);
        //returnId=getIntent().getIntExtra("returnGoodsId",0);

        initView();
        initGetDate();
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    private void initData(int id){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        ChooseGoodsRequest.OrderProductListBean chooseGoodsRequest=new ChooseGoodsRequest.OrderProductListBean();
        chooseGoodsRequest.setCount("");
        chooseGoodsRequest.setOrderItemProductId("");
        map.put("orderProductList",chooseGoodsRequest);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CHOOSE_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
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

          if (counter.getReturnOrderInfo().getReturnCount()!=0){
              tv_goods_number.setText(counter.getReturnOrderInfo().getReturnCount()+"");
          }else {
              tv_goods_number.setText("0");
          }

          if (!TextUtils.isEmpty(counter.getReturnOrderInfo().getRefundAmount())){
              tv_should_cut_money.setText(getString(R.string.label_money)+counter.getReturnOrderInfo().getRefundAmount());
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
                            if (bean.getClassName().equals("RefundMethod")){//  TimeQuantum
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

                }
            }
        });

    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("退货柜台");
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


        adapter=new ReturnGoodsCounterAdapter(list,this);
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
                SingleOptionsPicker.openOptionsPicker(this, type, tv_get_state);
                break;
            case R.id.lin_return_type:
                SingleOptionsPicker.openOptionsPicker(this, returns, tv_return_type);
                break;
            case R.id.lin_return_reason:
                SingleOptionsPicker.openOptionsPicker(this, reason, tv_return_reason);
                break;
            case R.id.tv_confirm_return:

                break;
        }
    }
}
