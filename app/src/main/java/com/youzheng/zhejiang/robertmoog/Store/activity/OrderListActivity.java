package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsTimeAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.OrderListAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderList;
import com.youzheng.zhejiang.robertmoog.Store.bean.StoreCustomerDetail;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 订单列表界面
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 搜索订单编号
     */
    private EditText tv_search;
    private ImageView iv_search;
    private LinearLayout lin_search;
    /**
     * 时间
     */
    private TextView tv_time;
    private PullLoadMoreRecyclerView rv_list;
    private OrderListAdapter adapter;
    private List<NewOrderListBean.OrderListBean> list = new ArrayList<>();
    private List<String> piclist = new ArrayList<>();
    private GridView gv_time;
    /**
     * 重置
     */
    private TextView tv_again;
    /**
     * 确定
     */
    private TextView tv_confirm;
    private DrawerLayout drawer_layout;
    private GoodsTimeAdapter goodsTimeAdapter;
    private List<String> strlist=new ArrayList<>();

    private int page=1;
    private int pageSize=10;
    private int customerId;
    private String orderCode="";
    private String startDate="";
    private String endDate="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
        setListener();
    }

    private void setListener() {
        rv_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page=1;
                list.clear();
                initData(page,pageSize,customerId,orderCode,startDate,endDate);

            }

            @Override
            public void onLoadMore() {
                page++;
                initData(page,pageSize,customerId,orderCode,startDate,endDate);
            }
        });




    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("订单列表");
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_search = (EditText) findViewById(R.id.tv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        lin_search = (LinearLayout) findViewById(R.id.lin_search);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        rv_list = (PullLoadMoreRecyclerView) findViewById(R.id.rv_list);
        gv_time = (GridView) findViewById(R.id.gv_time);
        tv_again = (TextView) findViewById(R.id.tv_again);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        drawer_layout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止手势滑动
        gv_time.setOnItemClickListener(this);
        btnBack.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 15, getResources().getColor(R.color.bg_all)));
        rv_list.setLinearLayout();
        rv_list.setColorSchemeResources(R.color.colorPrimary);

        adapter = new OrderListAdapter(list, piclist, this);
        rv_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        strlist.add("全部");
        strlist.add("一到三个月");
        strlist.add("一到三个月");
        strlist.add("一到三个月");
        strlist.add("一到三个月");
        strlist.add("一到三个月");

        goodsTimeAdapter=new GoodsTimeAdapter(strlist,this);
        gv_time.setAdapter(goodsTimeAdapter);
        goodsTimeAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(page,pageSize,customerId,orderCode,startDate,endDate);
    }

    private void initData(int page, int pageSize, int customerId, String orderCode, String startDate, String endDate) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("pageNum",page);
        map.put("pageSize",pageSize);
        map.put("customerId",customerId);
        map.put("orderCode",orderCode);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ORDERLIST_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                rv_list.setPullLoadMoreCompleted();
            }

            @Override
            public void onResponse(String response) {
                Log.e("订单列表",response);
                rv_list.setPullLoadMoreCompleted();
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    NewOrderListBean listBean = gson.fromJson(gson.toJson(baseModel.getDatas()),NewOrderListBean.class);
                    setData(listBean);

                }
            }

        });

    }

    private void setData(NewOrderListBean listBean) {
        if (listBean==null) return;
        if (listBean.getOrderList()==null) return;

        List<NewOrderListBean.OrderListBean> orderListBeans=listBean.getOrderList();

        if (orderListBeans.size()!=0){
            for (int i = 0; i <orderListBeans.size() ; i++) {
                if (list.get(i).getProductNum()==1){
                    list.add(orderListBeans.get(i));
                }else {
                    piclist.add(orderListBeans.get(i).getOrderItemInfos().get(i).getPhoto());
                }
            }
            adapter.setUI(list,piclist,this);
        }else {
            showToast(getString(R.string.load_list_erron));
        }
        rv_list.setPullLoadMoreCompleted();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_search:
                break;
            case R.id.tv_time:
                drawer_layout.openDrawer(GravityCompat.END);
                break;

            case R.id.btnBack:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goodsTimeAdapter.setSelectItem(position);
    }
}
