package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.Home.adapter.ViewHolder;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.AddressDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.AddressDatasBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.ClickUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class LocationManageActivity extends BaseActivity implements View.OnClickListener {

    ListView ls;
    CommonAdapter<AddressDatasBean> adapter;
    List<AddressDatasBean> data = new ArrayList<>();
    private String pageNum = "1", pageSize = "20", type;
    private String customerId;
    private View no_data, no_web;
    private String use;
    /**  */
    private TextView textHeadNext;
    /**
     * 暂无数据!
     */
    private TextView tv_text;
    private RelativeLayout layout_header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_manage_layout);
        customerId = getIntent().getStringExtra("customerId");
        use = getIntent().getStringExtra("use");
        Log.e("customerid", "地址管理" + customerId);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        data.clear();
        initData();
    }

    private void initData() {
        data.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("customerId", customerId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADDRESS_MANAGER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    AddressDatas addressDatas = gson.fromJson(gson.toJson(baseModel.getDatas()), AddressDatas.class);
                    if (addressDatas.getAddressList().size() > 0) {
                        data.addAll(addressDatas.getAddressList());
                        adapter.notifyDataSetChanged();
                        no_data.setVisibility(View.GONE);
                        ls.setVisibility(View.VISIBLE);
                    } else {
                        no_data.setVisibility(View.VISIBLE);
                        tv_text.setText("暂无收货地址");
                        ls.setVisibility(View.GONE);
                        //showToast(getString(R.string.load_list_erron));
                    }
                }
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
        ((TextView) findViewById(R.id.textHeadTitle)).setText("地址管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type = getIntent().getStringExtra("type");
        data.clear();
        ls = (ListView) findViewById(R.id.ls_location);
        adapter = new CommonAdapter<AddressDatasBean>(mContext, data, R.layout.location_ls_item) {
            @Override
            public void convert(ViewHolder helper, final AddressDatasBean item) {
                helper.setText(R.id.tv_name, item.getShipPerson());
                if (item.getShipMobile().length()!=11){
                    helper.setText(R.id.tv_phone, item.getShipMobile());
                }else {
                    String phone = item.getShipMobile().substring(0, 3) + " " + item.getShipMobile().substring(3, 7) + " " + item.getShipMobile().substring(7, 11);
                    helper.setText(R.id.tv_phone, phone);

                }

                helper.setText(R.id.tv_details, item.getShipAddress());

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type != null) {
                            Intent intent = new Intent();
                            intent.putExtra("address", item);
                            setResult(2, intent);
                            finish();
                        }
                    }
                });
            }
        };
        ls.setAdapter(adapter);

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastDoubleClick()){
                    return;
                }else {
                    Intent intent = new Intent(mContext, AddNewAddressActivity.class);
                    intent.putExtra("customerId", customerId);
                    startActivity(intent);
                }
            }
        });
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);

        if (use.equals("1")) {
            textHeadNext.setVisibility(View.VISIBLE);
            textHeadNext.setText("不使用");
        } else {
            textHeadNext.setVisibility(View.GONE);
        }
        textHeadNext.setOnClickListener(this);
        tv_text = (TextView) findViewById(R.id.tv_text);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.textHeadNext:
                // EventBus.getDefault().post("no_adress");
                Intent intent = new Intent(this, SalesActivity.class);
                intent.putExtra("address_aa", 0);
                startActivity(intent);
                finish();
                break;
        }
    }
}
