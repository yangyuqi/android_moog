package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.GoodsTitleAdapter2;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.HomeListDataBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.HomeListDataBeanBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.MealMainDataBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.MealMainDataList;
import com.youzheng.zhejiang.robertmoog.Model.Home.SetMealData;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
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

public class SetMealActivity extends BaseActivity implements TextWatcher {
    private List<MealMainDataBean> MealMainData = new ArrayList<>();
    ListView ls;
    TabLayout tab;
    List<String> tab_str = new ArrayList<>();
    List<Integer> tab_id = new ArrayList<>();
    SpringView springView;
    CommonAdapter<HomeListDataBean> adapter;
    List<HomeListDataBean> data = new ArrayList<>();
    private GridView mGvTitle;
    int pageNum = 1, pageSize = 20, typeId, totalPage;
    private GoodsTitleAdapter2 goodsTitleAdapter;
    EditText tv_search;
    private PopupWindow window;
    private ImageView iv_clear;
    private View no_data, no_web;
    private String SKU;
    private View view_down;
    private ImageView iv_more;
    /**
     * 暂无数据!
     */
    private TextView tv_text;
    private RelativeLayout layout_header;
    private int who;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_meal_layout);

        initView();
        initData();
        initEvent();
    }

    public void refreshData(int typeId) {

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("skuCode", SKU);
        map.put("typeId", typeId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.MEAL_USER_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                springView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    SetMealData setMealData = gson.fromJson(gson.toJson(baseModel.getDatas()), SetMealData.class);
                    if (setMealData.getListData().size() > 0) {
                        adapter.setData(setMealData.getListData());
                        adapter.notifyDataSetChanged();
                        no_data.setVisibility(View.GONE);
                        springView.setVisibility(View.VISIBLE);
                    } else {
                        if (pageNum == 1) {
                            no_data.setVisibility(View.VISIBLE);
                            springView.setVisibility(View.GONE);
                            tv_text.setText("暂无套餐信息");
                        } else {
                            showToasts(getString(R.string.load_list_erron));
                        }

                        if (!TextUtils.isEmpty(tv_search.getText().toString())) {
                            no_data.setVisibility(View.VISIBLE);
                            springView.setVisibility(View.GONE);
                            tv_text.setText("未搜索到相关套餐");
                        }
                    }
                }
            }
        });
    }

    private void initEvent() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                typeId = tab_id.get(tab.getPosition());
                pageNum=1;
                adapter.clear();
                refreshData(tab_id.get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                data.clear();
                refreshData(typeId);
            }

            @Override
            public void onLoadmore() {

                pageNum++;
                refreshData(typeId);

            }
        });

        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopuWindow();
                goodsTitleAdapter.setSelectItem(who);

            }
        });
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
        no_web = findViewById(R.id.no_web);
        iv_more = findViewById(R.id.iv_more);
        no_data = findViewById(R.id.no_data);
        ((TextView) findViewById(R.id.textHeadTitle)).setText("套餐列表");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tab = findViewById(R.id.tab);
        tv_search = findViewById(R.id.tv_search);
        ls = findViewById(R.id.ls);
        springView = findViewById(R.id.sv);
        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
        adapter = new CommonAdapter<HomeListDataBean>(mContext, data, R.layout.set_meal_ls_item) {
            @Override
            public void convert(ViewHolder helper, final HomeListDataBean item) {
                helper.setText(R.id.tv_name, item.getComboCode());
                helper.setText(R.id.tv_desc, item.getComboName());
                Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.type_icon).into((ImageView) helper.getView(R.id.iv_icon));
                helper.setText(R.id.tv_golden, item.getComboDescribe());
                ListView listView = helper.getView(R.id.ls);
                CommonAdapter<HomeListDataBeanBean> adapter = new CommonAdapter<HomeListDataBeanBean>(mContext, item.getList(), R.layout.shop_details_ls_item) {
                    @Override
                    public void convert(ViewHolder helper, HomeListDataBeanBean item) {
                        helper.setText(R.id.tv_activity, item.getName());
                        helper.setText(R.id.tv_price, "¥" + item.getPrice());
                    }
                };
                listView.setAdapter(adapter);

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, SetMealDetailsActivity.class);
                        intent.putExtra("id", item.getComId());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);

        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(SetMealActivity.this);
                if (TextUtils.isEmpty(tv_search.getText().toString())) {
                    showToasts("请输入商品sku/套餐号");
                } else {
                    SKU = tv_search.getText().toString().trim();
                    pageNum=1;
                    refreshData(typeId);
                }

            }
        });
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });

        tv_search.addTextChangedListener(this);
        tv_text = (TextView) findViewById(R.id.tv_text);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SET_MEAL_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);

                if (baseModel.getCode() == PublicUtils.code) {
                    MealMainDataList list = gson.fromJson(gson.toJson(baseModel.getDatas()), MealMainDataList.class);
                    if (list.getMealMainData().size() > 0) {

                        MealMainData = list.getMealMainData();
                        for (MealMainDataBean bean : list.getMealMainData()) {
                            tab_str.add(bean.getName());
                            tab_id.add(bean.getId());
                            tab.addTab(tab.newTab().setText(bean.getName()));
                        }
                    } else {
                        showToasts(getString(R.string.load_list_erron));

                    }
                }
            }
        });
    }

    private void showPopuWindow() {
        View inflate = getLayoutInflater().inflate(R.layout.pupwindow_goods_title2, null);
        mGvTitle = (GridView) inflate.findViewById(R.id.gv_title);
        view_down = inflate.findViewById(R.id.view_down);
        goodsTitleAdapter = new GoodsTitleAdapter2(MealMainData, this);
        mGvTitle.setAdapter(goodsTitleAdapter);
//        goodsTitleAdapter.notifyDataSetChanged();

        iv_more.setImageResource(R.mipmap.group_12_3);
        mGvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goodsTitleAdapter.setSelectItem(position);
                tab.getTabAt(position).select();
                who=position;
                window.dismiss();
            }
        });

        view_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setAnimationStyle(R.style.ActionDialogStyle);

        window.setBackgroundDrawable(getDrawable());
        springView.setBackgroundColor(getResources().getColor(R.color.text_drak_black));
        springView.setAlpha(0.3f);
        window.setOnDismissListener(new poponDismissListener());
        window.setTouchable(true); // 设置popupwindow可点击
        window.setOutsideTouchable(true); // 设置popupwindow外部可点击
        window.showAsDropDown(tab);
        window.update();
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            iv_more.setImageResource(R.mipmap.group_14_1);
            springView.setBackgroundColor(getResources().getColor(R.color.bg_background_white));
            springView.setAlpha(1f);

        }
    }

    private Drawable getDrawable() {
        ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(this.getResources().getColor(android.R.color.transparent));
        return bgdrawable;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (tv_search.length() == 0) {
            iv_clear.setVisibility(View.GONE);
            data.clear();
            adapter.clear();
            SKU = "";
            no_data.setVisibility(View.GONE);
            refreshData(typeId);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
}
