package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoodsManger;

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
import com.youzheng.zhejiang.robertmoog.Store.adapter.ReturnGoodsDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 退货订单详情
 */
public class ReturnGoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 987656201801020002
     */
    private TextView tv_new_return_num;
    /**
     * 987656201801020002
     */
    private TextView tv_old_order_num;
    /**
     * 李白(导购)
     */
    private TextView tv_maker;
    /**
     * 187 0000 0009
     */
    private TextView tv_customer;
    private RecyclerView rv_list;
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
    /**  */
    private TextView tv_cut_reason;
    private List<ReturnGoodsDetail.ReturnItemBean.ProductListBean> list = new ArrayList<>();
    private ReturnGoodsDetailAdapter adapter;
    /**
     * 吾问无为谓吾问无为谓
     */
    private TextView tv_reason_content;
    private String regoodsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_goods_detail);
        regoodsid=getIntent().getStringExtra("returnGoodsId");
        initView();
        initData(Integer.parseInt(regoodsid));
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.return_goods_detail));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_new_return_num = (TextView) findViewById(R.id.tv_new_return_num);
        tv_old_order_num = (TextView) findViewById(R.id.tv_old_order_num);
        tv_maker = (TextView) findViewById(R.id.tv_maker);
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        tv_goods_number = (TextView) findViewById(R.id.tv_goods_number);
        tv_get = (TextView) findViewById(R.id.tv_get);
        tv_should_cut_money = (TextView) findViewById(R.id.tv_should_cut_money);
        tv_dispatching = (TextView) findViewById(R.id.tv_dispatching);
        tv_really_cut_money = (TextView) findViewById(R.id.tv_really_cut_money);
        tv_cut_reason = (TextView) findViewById(R.id.tv_cut_reason);
        tv_reason_content = (TextView) findViewById(R.id.tv_reason_content);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);
        rv_list.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 15, getResources().getColor(R.color.bg_all)));
        adapter = new ReturnGoodsDetailAdapter(list, this);
        rv_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initData(int returnID) {


        HashMap<String,Object> map=new HashMap<>();
        map.put("id",returnID);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.RETURN_ORDERLIST_LIST_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("退货单详情",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ReturnGoodsDetail returnGoodsDetail = gson.fromJson(gson.toJson(baseModel.getDatas()),ReturnGoodsDetail.class);
                    setData(returnGoodsDetail);
                }else {
                    showToast(baseModel.getMsg());
                }
            }
        });





    }

    private void setData(ReturnGoodsDetail returnGoodsDetail) {

        if (returnGoodsDetail==null) return;
        if (returnGoodsDetail.getReturnItem()==null) return;
        if (returnGoodsDetail.getReturnItem().getProductList()==null) return;

        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getCode())){
            tv_new_return_num.setText(returnGoodsDetail.getReturnItem().getCode());
        }


        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getOrderCode())){
            tv_old_order_num.setText(returnGoodsDetail.getReturnItem().getOrderCode());
        }


        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getUserName())){
            if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getBusinessRole())){
                tv_maker.setText(returnGoodsDetail.getReturnItem().getUserName()+"("+returnGoodsDetail.getReturnItem().getBusinessRole()+")");
            }
        }



        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getCustCode())){
            tv_customer.setText(returnGoodsDetail.getReturnItem().getCustCode());
        }

        if (returnGoodsDetail.getReturnItem().getProductCount()!=0){
            tv_goods_number.setText(returnGoodsDetail.getReturnItem().getProductCount()+"");
        }else {
            tv_goods_number.setText("0");
        }


        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getRefundAmount())){
            tv_should_cut_money.setText(getString(R.string.label_money)+returnGoodsDetail.getReturnItem().getRefundAmount());
        }

        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getActualRefundAmount())){
            tv_really_cut_money.setText(getString(R.string.label_money)+returnGoodsDetail.getReturnItem().getActualRefundAmount());
        }

        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getReason())){
            tv_cut_reason.setText(returnGoodsDetail.getReturnItem().getReason());
        }

        if (!TextUtils.isEmpty(returnGoodsDetail.getReturnItem().getOtherReson())){
            tv_reason_content.setVisibility(View.VISIBLE);
            tv_reason_content.setText(returnGoodsDetail.getReturnItem().getOtherReson());
        }else {
            tv_reason_content.setVisibility(View.GONE);
        }


        List<ReturnGoodsDetail.ReturnItemBean.ProductListBean> beanList=returnGoodsDetail.getReturnItem().getProductList();

        if (beanList.size()!=0){
            list.addAll(beanList);
            adapter.setRefreshUI(beanList);
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
