package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ChooseGoodsListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderList;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 选择退货订单界面
 */
public class ChooseOrderListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private PullLoadMoreRecyclerView pr_list;
    private List<NewOrderListBean.OrderListBean> list = new ArrayList<>();
    private ChooseGoodsListAdapter adapter;

    private int page=1;
    private int pageSize=10;
    private String customerId;
    private String orderCode="";
    private String timeQuantum="";
    private Boolean isCustomer=false;
    private int who;
    private String edit;
  //  private String type="ALL";//订单类型（ALL:全部，GROOM:推荐订单，MAJOR:专业）默认是全部


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_order_list);
        isCustomer=getIntent().getBooleanExtra("identifion",false);
        customerId=getIntent().getStringExtra("customerId");
        initView();
        setListener();
    }
    private void setListener() {
        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page=1;
                list.clear();
                initData(page,pageSize,orderCode,timeQuantum,isCustomer);

            }

            @Override
            public void onLoadMore() {
                page++;
                initData(page,pageSize,orderCode,timeQuantum,isCustomer);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(page,pageSize,orderCode,timeQuantum,isCustomer);
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadTitle.setText(getString(R.string.choose_order));
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        pr_list = (PullLoadMoreRecyclerView) findViewById(R.id.pr_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pr_list.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 15, getResources().getColor(R.color.bg_all)));
        pr_list.setLinearLayout();
        pr_list.setColorSchemeResources(R.color.colorPrimary);

        adapter=new ChooseGoodsListAdapter(list,this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initData(int page, int pageSize, String orderCode,String timeQuantum,Boolean isCustomer) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("pageNum",page);
        map.put("pageSize",pageSize);
        map.put("orderCode",orderCode);
        map.put("timeQuantum",timeQuantum);
        map.put("identifion",isCustomer);
        if (isCustomer==true){
            map.put("customerId",customerId);
        }

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDERLIST_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                pr_list.setPullLoadMoreCompleted();
            }

            @Override
            public void onResponse(String response) {
                Log.e("订单列表",response);
                pr_list.setPullLoadMoreCompleted();
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    NewOrderListBean listBean = gson.fromJson(gson.toJson(baseModel.getDatas()),NewOrderListBean.class);
                    setData(listBean);

                }else {
                    showToast(baseModel.getMsg());
                }
            }

        });


    }

    private void setData(NewOrderListBean listBean) {
        if (listBean==null) return;
        if (listBean.getOrderList()==null) return;

        List<NewOrderListBean.OrderListBean> orderListBeans=listBean.getOrderList();

        if (orderListBeans.size()!=0){
            list.addAll(orderListBeans);
            adapter.setUI(list);
        }else {
            //showToast(getString(R.string.load_list_erron));
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
