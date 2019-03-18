package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.GetPromoListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.GetPromoListDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;
import com.youzheng.zhejiang.robertmoog.utils.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class ShopActionHistoryActivity extends BaseActivity {

    ListView ls;
    private String promoType = "current";
    int pageNum = 1, pageSize = 10, totalPage;
    private Integer customerId;

    SpringView springView;

    CommonAdapter<GetPromoListBean> adapter;
    private List<GetPromoListBean> data = new ArrayList<>();
    private boolean types;
    private View no_data, no_web;
    private RelativeLayout layout_header;
//    private View head;
    /**
     * 暂无数据!
     */
    private TextView tv_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_action_layout);

        initView();
        setListener();
        initData(pageNum,pageSize);
    }

    private void setListener() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                adapter.clear();
                initData(pageNum,pageSize);
            }

            @Override
            public void onLoadmore() {
                if (totalPage > pageNum) {
                    pageNum++;
                    initData(pageNum,pageSize);
                } else {
                    springView.onFinishFreshAndLoad();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int pagNum,int pagSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagNum);
        map.put("pageSize", pagSize);
        if (customerId != 0) {
            map.put("customerId", customerId);
        }
        if (promoType == null) {
            map.put("promoType", "current");
        } else {
            map.put("promoType", promoType);
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ACTION_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    GetPromoListDatas getPromoListDatas = gson.fromJson(gson.toJson(baseModel.getDatas()), GetPromoListDatas.class);
                    totalPage = getPromoListDatas.getTotalPage();
                    if (getPromoListDatas.getGetPromoList().size() > 0) {
                        data.addAll(getPromoListDatas.getGetPromoList());
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                        no_data.setVisibility(View.GONE);
                        springView.setVisibility(View.VISIBLE);
                    } else {
                        if (pageNum == 1) {
                            no_data.setVisibility(View.VISIBLE);
                            tv_text.setText("暂无促销活动信息");
                            springView.setVisibility(View.GONE);
                        } else {
                            showToast(getString(R.string.load_list_erron));
                        }
                        //   adapter.setData(new ArrayList<GetPromoListBean>());
                        // springView.onFinishFreshAndLoad();
                        // adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public SpannableStringBuilder setNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                style.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.text_golden2)), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }


    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status == -1) {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        } else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);

        }
    }

    private void initView() {
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_text = (TextView) findViewById(R.id.tv_text);
//        head = findViewById(R.id.head);
//        tv_text = head.findViewById(R.id.tv_text);
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        ls = findViewById(R.id.ls);
        //types=getIntent().getBooleanExtra("is_appear",false);
        TextView tv_title = findViewById(R.id.textHeadTitle);
        TextView tv_next = ((TextView) findViewById(R.id.textHeadNext));
        tv_title.setText("门店历史活动");


        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customerId = getIntent().getIntExtra("customerId", 0);
        promoType = getIntent().getStringExtra("promoType");
        if (promoType != null) {
            ((TextView) findViewById(R.id.textHeadNext)).setVisibility(View.GONE);
        }
        adapter = new CommonAdapter<GetPromoListBean>(mContext, data, R.layout.shop_action_otem) {
            @Override
            public void convert(ViewHolder helper, final GetPromoListBean item) {

//                if (promoType.equals("history")){
//                  helper.getView(R.id.lin_time).setVisibility(View.GONE);
//                }

                helper.setText(R.id.tv_name, item.getPromoName());
                helper.setText(R.id.tv_day, item.getPromoDes());


                Log.e("123131", item.getPromoDes());


                if (item.getPromoTime() != null) {
                    String[] strings = item.getPromoTime().split("/");
                    if (strings.length > 1) {
                        helper.setText(R.id.tv_start_time, strings[0]);
                        helper.setText(R.id.tv_end_time, strings[1]);
                    } else {
                        helper.setText(R.id.tv_start_time, item.getPromoTime());
                    }
                }

                helper.getView(R.id.tv_see_details).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShopActionDetailsActivity.class);
                        if (item.getProType().equals("SET_MEAL")){
                            intent.putExtra("promtypes", item.getProType());
                        }else {
                            intent.putExtra("promtypes","");
                        }
                        intent.putExtra("promoId", item.getPromoId());
                        startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(adapter);

        ((TextView) findViewById(R.id.textHeadNext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopActionHistoryActivity.class);
                intent.putExtra("promoType", "history");
                startActivity(intent);
            }
        });
        springView = findViewById(R.id.sv);
        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));



    }
}
