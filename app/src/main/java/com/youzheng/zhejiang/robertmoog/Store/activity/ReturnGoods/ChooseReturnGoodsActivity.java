package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.content.Intent;
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
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ProfessionalOrderDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.adapter.MoreChooseReturnGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.MoreOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OneChooseReturnGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OneOrderDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseGoodsRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.CommitRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.CustomerList;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

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

    private boolean isall;
    private String id;
    private List<ChooseGoodsRequest.OrderProductListBean> requests=new ArrayList<>();
    private String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_return_goods);
        id=getIntent().getStringExtra("returnGoodsId");
        isall=getIntent().getBooleanExtra("isAll",false);
        initView();
        requests.clear();
        initData(id,isall);
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadTitle.setText(getString(R.string.choose_goods));
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(String id, boolean isAll){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("isAll",isAll);

//        List<ChooseGoodsRequest.OrderProductListBean> beanList=new ArrayList<>();
//        ChooseGoodsRequest.OrderProductListBean bean=new ChooseGoodsRequest.OrderProductListBean();
//        bean.setCount("");
//        bean.setOrderItemProductId("");
//        beanList.add(bean);
//        map.put("orderProductList",beanList);


        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CHOOSE_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(String response) {
                Log.e("选择商品列表",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ChooseReturnGoodsDetail chooseReturnGoodsDetail = gson.fromJson(gson.toJson(baseModel.getDatas()),ChooseReturnGoodsDetail.class);
                    setData(chooseReturnGoodsDetail);
                }else {
                    showToast(baseModel.getMsg());
                }
            }
        });


    }

    private void setData(ChooseReturnGoodsDetail chooseReturnGoodsDetail) {
          if (chooseReturnGoodsDetail.getReturnOrderInfo()==null) return;
        ChooseReturnGoodsDetail.ReturnOrderInfoBean infoBean=chooseReturnGoodsDetail.getReturnOrderInfo();

        orderid=infoBean.getId();

        if (!TextUtils.isEmpty(infoBean.getCreateDate())){
            tv_time.setText(infoBean.getCreateDate());
        }

          if (!TextUtils.isEmpty(infoBean.getCode())){
              tv_num.setText(infoBean.getCode());
          }

        if (!TextUtils.isEmpty(infoBean.getCreateUser())){
            if (!TextUtils.isEmpty(infoBean.getBusinessRole())){
                tv_maker.setText(infoBean.getCreateUser()+"("+infoBean.getBusinessRole()+")");
            }

        }


        if (!TextUtils.isEmpty(infoBean.getAccount())){
            tv_customer.setText(infoBean.getAccount());
        }

        List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> one=infoBean.getProductList();

        if (one.size()!=0){
            onelist.addAll(one);
            oneOrderDetailAdapter.setUI(one);
            requests.clear();
        }else {
            rv_list_one.setVisibility(View.GONE);
        }


        List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean> more=infoBean.getSetMealList();

        if (more.size()!=0){
            morelist.addAll(more);
            moreChooseReturnGoodsAdapter.setUI(more);
//            requests.addAll(moreChooseReturnGoodsAdapter.getRequests());
            Log.e("sada",requests+"");
        }else {
            rv_list_more.setVisibility(View.GONE);
        }


    }

//    public void setRequest(List<ChooseGoodsRequest.OrderProductListBean> request){
//        this.requests=request;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.tv_confirm:
                requests.clear();

                if (onelist.size()!=0){

                    for (ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean oneBean:onelist){
                        ChooseGoodsRequest.OrderProductListBean request=new ChooseGoodsRequest.OrderProductListBean();
                        if (!oneBean.getNum().equals("0")){
                            request.setCount(oneBean.getNum());
                            request.setOrderItemProductId(oneBean.getOrderItemProductId());
                            requests.add(request);
                        }
                    }
                }

                if (morelist.size()!=0){
                        requests.addAll(moreChooseReturnGoodsAdapter.getRequests());
                }


                if (requests.size()==0){
                    showToast("暂无可退商品");
                }else {
                    Intent intent=new Intent(this,ReturnGoodsCounterActivity.class);
                    intent.putExtra("request", (Serializable) requests);
                    intent.putExtra("is_all",isall);
                    intent.putExtra("orderID",orderid);
                    startActivity(intent);
                    finish();
                }


                Log.e("11231",requests.size()+"");

                break;
        }
    }
    }

