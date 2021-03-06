package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.VipGoodsAdapter;
import com.youzheng.zhejiang.robertmoog.Home.bean.VipGoods;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalListBean;
import com.youzheng.zhejiang.robertmoog.Model.login.RegisterBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.listener.VipDeleteListener;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.RemarkDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;
import rx.functions.Action1;

public class VipAttentionMangerActivity extends BaseActivity implements View.OnClickListener, VipDeleteListener, OnRecyclerViewAdapterItemClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RecyclerView rv_list;
    RegisterBean registerBean;
    ShopPersonalListBean bean;
    List<VipGoods.IntentInfoListBean> intentInfoListBeans = new ArrayList<>();
    private VipGoods vipGoods;
    private VipGoodsAdapter adapter;
    private int widWidth;
    private View no_data, no_web;
    private String remark_id;
    /**
     * 暂无数据
     */
    private TextView tv_text;
    private RelativeLayout layout_header;
    private View include_state;
    private LinearLayout lin_over;
    /**
     * 暂无意向商品信息
     */
    private TextView tv_no_data;
    private TextView tv_attention;
    /**
     * 修改备注
     */
    private TextView tv_update_intent;
    private String id;
    String employedid;
    private TextView mTvTexts;
    /**
     * 添加备注
     */
    private TextView mTvUpdateContent;
    private LinearLayout mLinOverUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_attention_manger);
        bean = (ShopPersonalListBean) getIntent().getSerializableExtra("label");
        registerBean = (RegisterBean) getIntent().getSerializableExtra("registerBean");
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        EventBus.getDefault().register(this);
        employedid = (String) SharedPreferencesUtils.getParam(this, PublicUtils.EMPLOYEDID, "");
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        intentInfoListBeans.clear();
        adapter.clear();
        initData();

    }

    @Subscribe
    public void onEvent(String d) {
        if (d.equals("refresh")) {
            adapter.clear();
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            map.put("customerId", bean.getCustomerId());
        }
        if (registerBean != null) {
            map.put("customerId", registerBean.getCustomerId());
        }

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CUSTOMER_SHOP + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("会员识别意向", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    vipGoods = gson.fromJson(gson.toJson(baseModel.getDatas()), VipGoods.class);
                    setData(vipGoods);
                } else {

                }
            }
        });


    }

    private void setData(VipGoods vipGoods) {
        if (vipGoods == null) return;

        List<VipGoods.IntentInfoListBean> listBeans = vipGoods.getIntentInfoList();
        if (listBeans.size() != 0) {
            intentInfoListBeans.addAll(listBeans);
            adapter.setLists(intentInfoListBeans);
            rv_list.setVisibility(View.VISIBLE);
            lin_over.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.GONE);
            boolean isappear=false;
            for (VipGoods.IntentInfoListBean bean : listBeans) {
                if (bean.getId().equals(employedid)){
                    isappear=true;
                    break;
                }
            }
            if (isappear) {
                mLinOverUp.setVisibility(View.GONE);
            } else {
                mLinOverUp.setVisibility(View.VISIBLE);
            }

//            no_data.setVisibility(View.GONE);
        } else {
            rv_list.setVisibility(View.GONE);
            lin_over.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.VISIBLE);

//            rv_list.setVisibility(View.GONE);
//            tv_text.setText("暂无意向商品");
//            no_data.setVisibility(View.VISIBLE);
        }
    }


    private void initView() {
        include_state = findViewById(R.id.include_state);
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("意向管理");
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_next.setVisibility(View.VISIBLE);
        iv_next.setImageResource(R.mipmap.group_75_1);

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        iv_next.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(manager);
        rv_list.setAdapter(adapter);
        rv_list.addItemDecoration(new RecycleViewDivider(VipAttentionMangerActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));


        adapter = new VipGoodsAdapter(intentInfoListBeans, this, widWidth, this);
        rv_list.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        tv_text = (TextView) findViewById(R.id.tv_text);

        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        lin_over = (LinearLayout) findViewById(R.id.lin_over);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        tv_attention = (TextView) findViewById(R.id.tv_attention);
        tv_update_intent = (TextView) findViewById(R.id.tv_update_intent);
        tv_update_intent.setOnClickListener(this);
        mTvTexts = (TextView) findViewById(R.id.tv_texts);
        mTvUpdateContent = (TextView) findViewById(R.id.tv_update_content);
        mTvUpdateContent.setOnClickListener(this);
        mLinOverUp = (LinearLayout) findViewById(R.id.lin_over_up);
    }

    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);

        if (status == -1) {
            include_state.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        } else {
            include_state.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }

//        try{
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;

            case R.id.iv_next:
                RxPermissions permissions = new RxPermissions(VipAttentionMangerActivity.this);
                permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(mContext, CaptureActivity.class);
                            intent.putExtra("type", "");
                            if (bean != null) {
                                intent.putExtra("customerId", bean.getCustomerId());
                            }

                            if (registerBean != null) {
                                intent.putExtra("customerId", "" + registerBean.getCustomerId());
                            }
                            startActivity(intent);
                        }
                    }
                });

                break;
            case R.id.tv_update_intent:
                if (bean != null) {
                    remark_id = bean.getCustomerId();
                    Log.e("remark_id", remark_id + "111111111" + bean.getCustomerId());
                }
                if (registerBean != null) {
                    remark_id = registerBean.getCustomerId() + "";
                    Log.e("remark_id", remark_id + "22222222" + registerBean.getCustomerId());

                }
                Log.e("remark_id", remark_id);
                RemarkDialog remarkDialog = new RemarkDialog(VipAttentionMangerActivity.this, remark_id, "", false);
                remarkDialog.show();


                break;
            case R.id.tv_update_content:
                if (bean != null) {
                    remark_id = bean.getCustomerId();
                    Log.e("remark_id", remark_id + "111111111" + bean.getCustomerId());
                }
                if (registerBean != null) {
                    remark_id = registerBean.getCustomerId() + "";
                    Log.e("remark_id", remark_id + "22222222" + registerBean.getCustomerId());

                }
                Log.e("remark_id", remark_id);
                RemarkDialog remarkDialog1 = new RemarkDialog(VipAttentionMangerActivity.this, remark_id, "", false);
                remarkDialog1.show();

                break;
        }
    }

    @Override
    public void delete() {
        adapter.clear();
        initData();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (intentInfoListBeans.get(position).getIntentId() != 0) {
            RemarkDialog remarkDialog = new RemarkDialog(VipAttentionMangerActivity.this, intentInfoListBeans.get(position).getIntentId() + "", intentInfoListBeans.get(position).getRemark(), true);
            remarkDialog.show();
        } else {
            if (bean != null) {
                remark_id = bean.getCustomerId();
                Log.e("remark_id", remark_id + "111111111" + bean.getCustomerId());
            }
            if (registerBean != null) {
                remark_id = registerBean.getCustomerId() + "";
                Log.e("remark_id", remark_id + "22222222" + registerBean.getCustomerId());

            }
            Log.e("remark_id", remark_id);
            RemarkDialog remarkDialog = new RemarkDialog(VipAttentionMangerActivity.this, remark_id, intentInfoListBeans.get(position).getRemark(), false);
            remarkDialog.show();

        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
