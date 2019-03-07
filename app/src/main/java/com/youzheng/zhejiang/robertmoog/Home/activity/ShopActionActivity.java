package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

public class ShopActionActivity extends BaseActivity {

    ListView ls;
    private String promoType = "current";
    int pageNum = 1, pageSize = 20, totalPage;
    private Integer customerId;

    SpringView springView;
    private View no_data, no_web;

    CommonAdapter<GetPromoListBean> adapter;
    private List<GetPromoListBean> data = new ArrayList<>();
    private boolean types;
    /**
     * 暂无数据!
     */
    private TextView tv_text;
    private RelativeLayout layout_header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_action_layout);

        initView();

    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
        }else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
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
                            showToasts(getString(R.string.load_list_erron));
                        }
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

    private void initView() {
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        ls = findViewById(R.id.ls);
        types = getIntent().getBooleanExtra("is_appear", false);
        TextView tv_title = findViewById(R.id.textHeadTitle);
        TextView tv_next = ((TextView) findViewById(R.id.textHeadNext));
        if (types == true) {
            tv_title.setText("门店活动");
            tv_next.setVisibility(View.VISIBLE);
            tv_next.setText("历史信息");

        } else {
            tv_title.setText("客户活动");
            tv_next.setVisibility(View.GONE);
            tv_next.setText("历史信息");
        }

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

                helper.setText(R.id.tv_name, item.getPromoName());

//                String regEx="[^0-9]";
//                Pattern p = Pattern.compile(regEx);
//                Matcher m = p.matcher(item.getPromoDes());
                // helper.setText(R.id.tv_day,m.replaceAll("").trim());

                helper.setTexto(R.id.tv_day, setNumColor(item.getPromoDes()));


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
                        adapter.clear();
                        Intent intent = new Intent(mContext, ShopActionDetailsActivity.class);
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

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                data.clear();
                adapter.clear();
                initData();
            }

            @Override
            public void onLoadmore() {
                if (totalPage > pageNum) {
                    pageNum++;
                    initData();
                } else {
                    springView.onFinishFreshAndLoad();
                }
            }
        });
        tv_text = (TextView) findViewById(R.id.tv_text);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
    }
}
