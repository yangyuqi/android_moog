package com.youzheng.zhejiang.robertmoog.Count.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.adapter.GoodsTypeRankingDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.GoodsTypeDetail;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 商品品类排名详情界面
 */
public class GoodsTypeRankingDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 销售金额
     */
    private TextView tv_rule;
    private LinearLayout lin_title;
    private RecyclerView pr_list;
    private List<GoodsTypeDetail.ProductListBean> list = new ArrayList<>();
    private GoodsTypeRankingDetailAdapter adapter;

    private int page = 1;
    private int pageSize = 10;
    private boolean isDay = false;
    private String startstr = "";
    private String endstr = "";
    private int categoryId;
    private String type = "";
    private TimePickerView pvTime;
    private String time = "";
    private int who;
    /**
     * 请选择时间
     */
    private TextView tv_startDate;
    /**
     * 请选择时间
     */
    private TextView tv_endDate;
    /**
     * 查询
     */
    private TextView tv_check;
    private SpringView mSpringView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_type_ranking_detail);
        categoryId = getIntent().getIntExtra("goodsId", 0);
        if (categoryId == 0) {
            categoryId = 0;
        }
        type = getIntent().getStringExtra("type");

        initView();
        initTimer();
        swtListener();
        initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
    }

    private void swtListener() {
//        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
//            }
//
//            @Override
//            public void onLoadMore() {
//                //list.clear();
//                page++;
//                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
//            }
//        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
            }

            @Override
            public void onLoadmore() {
                page++;
                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
            }
        });


    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_rule = (TextView) findViewById(R.id.tv_rule);
        lin_title = (LinearLayout) findViewById(R.id.lin_title);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(GoodsTypeRankingDetailActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        // TODO: 2019/1/2 标题判断
        if (type.equals("COUNT")) {
            textHeadTitle.setText(getString(R.string.goods_sale_number));
            tv_rule.setText(getString(R.string.sale_number));
        } else {
            textHeadTitle.setText(getString(R.string.goods_sale_money));
            tv_rule.setText(getString(R.string.sale_money));
        }


        adapter = new GoodsTypeRankingDetailAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setOnClickListener(this);
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);
        tv_check = (TextView) findViewById(R.id.tv_check);
        tv_check.setOnClickListener(this);
        mSpringView = (SpringView) findViewById(R.id.springView);
        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int page, int pageSize, boolean isDay, String startDate, String endDate, int categoryId, String rule) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("categoryId", categoryId);
        map.put("rule", rule);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GOODS_TYPE_RANKING_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("商品品类排名详情", response);
                //pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    GoodsTypeDetail goodsTypeDetail = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsTypeDetail.class);
                    setData(goodsTypeDetail);
                } else {
                    showToast(baseModel.getMsg());
                }
            }
        });
    }

    private void setData(GoodsTypeDetail goodsTypeDetail) {
        if (goodsTypeDetail == null) return;
        if (goodsTypeDetail.getProductList() == null) return;

        List<GoodsTypeDetail.ProductListBean> beanList = goodsTypeDetail.getProductList();
        if (beanList.size() != 0) {
            list.addAll(beanList);
            adapter.setUI(beanList);
        } else {
            showToast(getString(R.string.load_list_erron));
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
            case R.id.tv_startDate:
                who = 1;
                pvTime.show(v, false);
                break;
            case R.id.tv_endDate:
                who = 2;
                pvTime.show(v, false);
                break;
            case R.id.tv_check:
                isDay = false;
                list.clear();
                initData(page, pageSize, isDay, startstr, endstr, categoryId, type);
                break;
        }
    }

    private void initTimer() {
        Calendar selectedDate = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = selectedDate.get(Calendar.YEAR);
        //月
        int month = selectedDate.get(Calendar.MONTH) + 1;
        //日
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        final Calendar startDate = Calendar.getInstance();
        startDate.set(2010, 1, 1);

        final Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, day);

        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                time = getTime(date);
                if (!time.equals("")) {
                    if (who == 1) {
                        tv_startDate.setText(time);
                        startstr = time;
                    } else {
                        tv_endDate.setText(time);
                        endstr = time;
                    }
                }

            }
        })
                .setLayoutRes(R.layout.item_picker_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_confirm);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .isDialog(true)
                //  .setDecorView(pr_list)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(Color.parseColor("#F5F5F5"))
                .setOutSideCancelable(true)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉


    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }
}
