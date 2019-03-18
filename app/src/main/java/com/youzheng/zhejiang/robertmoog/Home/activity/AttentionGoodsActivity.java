package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ViewHolder;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerData;
import com.youzheng.zhejiang.robertmoog.Model.Home.IntentProductList;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalListBean;
import com.youzheng.zhejiang.robertmoog.Model.login.RegisterBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialogInterface;
import com.youzheng.zhejiang.robertmoog.utils.View.RemarkDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;
import rx.functions.Action1;

public class AttentionGoodsActivity extends BaseActivity {


    ListView ls;
    RegisterBean registerBean;
    ShopPersonalListBean bean;
    CommonAdapter<IntentProductList> adapter;
    TextView tv_attention, tv_update_intent;
    int widWidth;
    private LinearLayout lin_over;
    private String remark_id;
    CustomerData intentDataBean;
    private View no_data;
    private ImageView iv_pic;
    /**
     * 暂无数据!
     */
    private TextView tv_text;
    /**
     * 暂无意向商品信息
     */
    private TextView tv_no_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_goods_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        initView();
        bean = (ShopPersonalListBean) getIntent().getSerializableExtra("label");
        registerBean = (RegisterBean) getIntent().getSerializableExtra("registerBean");


        EventBus.getDefault().register(this);
        initClick();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String d) {
        if (d.equals("refresh")) {
            adapter.clear();

            initData();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        adapter.clear();
        initData();
    }

    private void initClick() {
        ((ImageView) findViewById(R.id.iv_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions permissions = new RxPermissions(AttentionGoodsActivity.this);
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
            }
        });
    }

    public void initData() {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            map.put("id", bean.getCustomerId());
        }
        if (registerBean != null) {
            map.put("id", registerBean.getCustomerId());
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ATTENTION_GOODS_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    intentDataBean = gson.fromJson(gson.toJson(baseModel.getDatas()), CustomerData.class);
                    if (!TextUtils.isEmpty(intentDataBean.getCustomerIntentData().getRemark())) {
                        tv_update_intent.setText("修改备注");
                        tv_attention.setText(intentDataBean.getCustomerIntentData().getRemark());
                    } else {
                        tv_attention.setText("");
                        tv_update_intent.setText("添加备注");
                        //tv_update_intent.setVisibility(View.GONE);
                    }


                    if (intentDataBean.getCustomerIntentData().isIntent() == true) {
                        if (intentDataBean.getCustomerIntentData().getIntentProductList().size() != 0) {
                            Log.e("112312", intentDataBean.getCustomerIntentData().getIntentProductList().size() + "");
                            adapter.setData(intentDataBean.getCustomerIntentData().getIntentProductList());
                            adapter.notifyDataSetChanged();
                            lin_over.setVisibility(View.VISIBLE);
//                            no_data.setVisibility(View.GONE);
                            tv_no_data.setVisibility(View.GONE);
                            ls.setVisibility(View.VISIBLE);
                        } else {

                            tv_no_data.setVisibility(View.VISIBLE);
                            ls.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            //showToast(getString(R.string.load_list_erron));
                        }
                    } else {
                        if (intentDataBean.getCustomerIntentData().getIntentProductList().size() != 0) {
                            lin_over.setVisibility(View.VISIBLE);
//                            no_data.setVisibility(View.GONE);
                            tv_no_data.setVisibility(View.GONE);
                            ls.setVisibility(View.VISIBLE);
                        } else {
//                            no_data.setVisibility(View.VISIBLE);
//                            iv_pic.setVisibility(View.GONE);
//                            tv_text.setText("暂无意向商品信息");
                            tv_no_data.setVisibility(View.VISIBLE);
                            ls.setVisibility(View.GONE);
                        }
                    }
                    tv_update_intent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(intentDataBean.getCustomerIntentData().getId())) {
                                RemarkDialog remarkDialog = new RemarkDialog(AttentionGoodsActivity.this, intentDataBean.getCustomerIntentData().getId(), intentDataBean.getCustomerIntentData().getRemark(), true);
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
                                RemarkDialog remarkDialog = new RemarkDialog(AttentionGoodsActivity.this, remark_id, intentDataBean.getCustomerIntentData().getRemark(), false);
                                remarkDialog.show();

                            }
                        }
                    });

                }
            }
        });
    }


    private void sendRemark(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("remark", "");
        OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.UPDATE_INTENT_REMARK + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = new Gson().fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {

                }
            }
        });
    }

    private void initView() {
        tv_text = (TextView) findViewById(R.id.tv_text);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        no_data = findViewById(R.id.no_data);
        ((TextView) findViewById(R.id.textHeadTitle)).setText("意向管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.iv_next)).setImageResource(R.mipmap.group_75_1);
        ls = (ListView) findViewById(R.id.ls);
        tv_attention = findViewById(R.id.tv_attention);
        tv_update_intent = findViewById(R.id.tv_update_intent);
        lin_over = findViewById(R.id.lin_over);
        adapter = new CommonAdapter<IntentProductList>(mContext, new ArrayList<IntentProductList>(), R.layout.common_goods_vh_item) {
            @Override
            public void convert(ViewHolder helper, final IntentProductList item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_desc, item.getSku());
                helper.setText(R.id.tv_price, "¥" + item.getPrice());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.type_icon).into((ImageView) helper.getView(R.id.iv_icon));
                RelativeLayout view = helper.getView(R.id.rl_width);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.width = (int) PublicUtils.dip2px(PublicUtils.px2dip(widWidth));
                view.setLayoutParams(params);

                helper.getView(R.id.main_right_drawer_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog dialog = new DeleteDialog(mContext, "提示", "是否删除此意向商品", "确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", item.getId());
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.DELETE_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                                        if (baseModel.getCode() == PublicUtils.code) {
                                            adapter.clear();
                                            sendRemark(intentDataBean.getCustomerIntentData().getId());
                                            initData();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        };

        ls.setAdapter(adapter);


        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }
}
