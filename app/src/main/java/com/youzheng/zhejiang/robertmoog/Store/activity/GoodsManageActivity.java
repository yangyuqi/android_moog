package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsPagerAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsSearchAdapter;
import com.youzheng.zhejiang.robertmoog.Store.adapter.GoodsTitleAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.GoodsList;
import com.youzheng.zhejiang.robertmoog.Store.bean.GoodsType;
import com.youzheng.zhejiang.robertmoog.Store.fragment.GoodsFragment;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 商品管理界面
 */
public class GoodsManageActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 输入商品SKU
     */
    private EditText tv_search;
    private ImageView iv_search;
    private TabLayout tab;
    private ImageView iv_more;
    private ViewPager pager;
    private List<Fragment> list = new ArrayList<>();
    private GoodsPagerAdapter adapter;
    private GridView mGvTitle;
    private PopupWindow window;
    private List<GoodsType.ListDataBean> stringList = new ArrayList<>();
    private GoodsTitleAdapter goodsTitleAdapter;
    private String edit;
    private LinearLayout lin_tab;
    private ListView search_list;
    private GoodsSearchAdapter searchAdapter;
    private List<GoodsList.ProductListDetailDataBean> searchlist = new ArrayList<>();
    private int pageSize = 10;
    private int page = 1;
    public static GoodsFragment goodsFragment;
    private int who;
    private ImageView iv_clear;
    private View view_dowm;
    private View no_data, no_web;
    /**
     * 暂无数据!
     */
    private TextView mTvText;
    List<String> tab_str = new ArrayList<>();
    List<Integer> tab_id = new ArrayList<>();
    private int goodsId;
    private SpringView springView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manage);
        initView();
        intEvent();


    }

    private void intEvent() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goodsId = stringList.get(tab.getPosition()).getId();
                searchAdapter.clear();
                page=1;
                initSearch(goodsId);
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
                page = 1;
                searchlist.clear();
                searchAdapter.clear();
                initSearch(goodsId);
            }

            @Override
            public void onLoadmore() {
                page++;
                initSearch(goodsId);
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
        no_data = findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadTitle.setText(getString(R.string.goods_list));
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_search = (EditText) findViewById(R.id.tv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        tab = (TabLayout) findViewById(R.id.tab);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);
        pager = (ViewPager) findViewById(R.id.pager);
        btnBack.setOnClickListener(this);
        tv_search.addTextChangedListener(this);


        initData();

        lin_tab = (LinearLayout) findViewById(R.id.lin_tab);
        search_list = (ListView) findViewById(R.id.search_list);

        search_list.setOnItemClickListener(this);

        searchAdapter = new GoodsSearchAdapter(searchlist, this);
        search_list.setAdapter(searchAdapter);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });
        mTvText = (TextView) findViewById(R.id.tv_text);


        springView = (SpringView) findViewById(R.id.springView);
        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
    }

    private void initData() {

        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GOODS_LIST_TYPE + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("商品标签", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    GoodsType goodsType = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsType.class);
                    setData(goodsType);
                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });
    }

    private void setData(GoodsType goodsType) {
        FragmentManager fm = getSupportFragmentManager();
        adapter = new GoodsPagerAdapter(fm, list, stringList);

        if (goodsType.getListData() == null) return;
        List<GoodsType.ListDataBean> listDataBeans = goodsType.getListData();
        //stringList = goodsType.getListData();
        if (listDataBeans.size() != 0) {
            stringList=listDataBeans;
            for (GoodsType.ListDataBean bean : listDataBeans) {
                tab_str.add(bean.getName());
                tab_id.add(bean.getId());
                tab.addTab(tab.newTab().setText(bean.getName()));
            }

        } else {
            showToasts(getString(R.string.load_list_erron));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_search:
                SoftInputUtils.hideSoftInput(GoodsManageActivity.this);
                edit = tv_search.getText().toString().trim();
                if (TextUtils.isEmpty(edit)) {
                    showToasts(getString(R.string.please_write_sku));
                } else {
                    //edit = tv_search.getText().toString().trim();
                    page=1;
                    searchAdapter.clear();
                    initSearch(goodsId);
//                    lin_tab.setVisibility(View.GONE);
//                    pager.setVisibility(View.GONE);
//                    search_list.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_more:

                showPopuWindow();
                goodsTitleAdapter.setSelectItem(who);
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    //搜索之后的数据
    private void initSearch(int goodsId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
//        map.put("sku",goodsName);
        map.put("sku", edit);//测试用
        map.put("firstCategoryId", goodsId);
        String token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token, "");
        if (token != null) {
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GOODS_LIST + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                       springView.onFinishFreshAndLoad();
                }

                @Override
                public void onResponse(String response) {
                    Log.e("商品列表", response);
                    springView.onFinishFreshAndLoad();
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        GoodsList goodsList = gson.fromJson(gson.toJson(baseModel.getDatas()), GoodsList.class);
                        setSearchData(goodsList);
                    } else {
                        showToasts(baseModel.getMsg());
                    }
                }
            });
        }
    }

    private void setSearchData(GoodsList goodsList) {
        if (goodsList.getProductListDetailData() == null) return;
        List<GoodsList.ProductListDetailDataBean> productListDetailDataBeans = goodsList.getProductListDetailData();

        if (productListDetailDataBeans.size() != 0) {
            searchlist.addAll(productListDetailDataBeans);
            searchAdapter.setRefreshUI(searchlist);
            no_data.setVisibility(View.GONE);
            springView.setVisibility(View.VISIBLE);
        } else {
            if (page == 1) {
                no_data.setVisibility(View.VISIBLE);
                springView.setVisibility(View.GONE);
                springView.setVisibility(View.GONE);
                mTvText.setText("暂无商品信息");
            } else {
                showToasts(getString(R.string.load_list_erron));
            }



        }



    }

    private void showPopuWindow() {
        View inflate = getLayoutInflater().inflate(R.layout.popuwindow_goods_title, null);
        mGvTitle = (GridView) inflate.findViewById(R.id.gv_title);
        view_dowm = inflate.findViewById(R.id.view_dowm);
        goodsTitleAdapter = new GoodsTitleAdapter(stringList, this);
        mGvTitle.setAdapter(goodsTitleAdapter);
        iv_more.setImageResource(R.mipmap.group_12_3);
        mGvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goodsTitleAdapter.setSelectItem(position);
                tab.getTabAt(position).select();
                who = position;
                window.dismiss();
            }
        });

        view_dowm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setAnimationStyle(R.style.ActionDialogStyle);

        window.setBackgroundDrawable(getDrawable());

        springView.setBackgroundColor(getResources().getColor(R.color.text_drak_black));
        springView.setAlpha(0.35f);

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
            iv_more.setImageResource(R.mipmap.group_14_1);
            springView.setBackgroundColor(getResources().getColor(R.color.bg_background_white));
            springView.setAlpha(1f);

        }
    }

    //获取屏幕的高度
    public static int getScreenHeight(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return height;

    }

    /**
     * 生成一个 透明的背景图片
     *
     * @return
     */
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
            searchlist.clear();
            searchAdapter.clear();
           edit="";
            no_data.setVisibility(View.GONE);
            initSearch(goodsId);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsID", searchlist.get(position).getId());
        startActivity(intent);
    }
}
