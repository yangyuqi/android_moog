package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.SampleDetailAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.SampleOutPic;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SampleOutDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 李学东
     */
    private TextView tv_name;
    private GridView gv_photo;
    /**
     * 2016/01/19 09:19:04
     */
    private TextView tv_time;
    private List<SampleOutPic.SampleImgIssueDataBean.ListBean> list = new ArrayList<>();
    private SampleDetailAdapter adapter;
    private List<String> piclist = new ArrayList<>();
    private View no_data,no_web;
    /**
     * 暂无数据!
     */
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_out_detail);
        initView();
        initData();
    }

    private void initView() {
        no_web = findViewById(R.id.no_web);
        no_data = findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.sample_out_detail));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_name = (TextView) findViewById(R.id.tv_name);
        gv_photo = (GridView) findViewById(R.id.gv_photo);
        tv_time = (TextView) findViewById(R.id.tv_time);
        adapter = new SampleDetailAdapter(list, this);
        gv_photo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        gv_photo.setOnItemClickListener(this);


        tv_text = (TextView) findViewById(R.id.tv_text);
    }
    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
        }else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }
    }


    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SAMPLE_HISTORY + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("出样历史", response);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    SampleOutPic sampleOutPic = gson.fromJson(gson.toJson(baseModel.getDatas()), SampleOutPic.class);
                    setData(sampleOutPic);
                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });
    }

    private void setData(SampleOutPic sampleOutPic) {
        if (sampleOutPic == null) return;
        List<SampleOutPic.SampleImgIssueDataBean.ListBean> pic = sampleOutPic.getSampleImgIssueData().getList();
        if (pic!=null){
            if (pic.size() != 0) {
                list.addAll(pic);
                adapter.setPic(list);
                no_data.setVisibility(View.GONE);
            } else {
                no_data.setVisibility(View.VISIBLE);
                tv_text.setText("暂无出样信息");
            }
        }else {
            no_data.setVisibility(View.VISIBLE);
            tv_text.setText("暂无出样信息");
        }

       // String Operator = sampleOutPic.getSampleImgIssueData().getOperator();
        if (!TextUtils.isEmpty(sampleOutPic.getSampleImgIssueData().getOperator())) {
            tv_name.setText(sampleOutPic.getSampleImgIssueData().getOperator());
        }

     //   String operationTime = sampleOutPic.getSampleImgIssueData().getOperationTime();
        if (!TextUtils.isEmpty(sampleOutPic.getSampleImgIssueData().getOperationTime())) {
            tv_time.setText(sampleOutPic.getSampleImgIssueData().getOperationTime());
        }



        for (int i = 0; i < pic.size(); i++) {
            piclist.add(list.get(i).getBigUrl());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                UpPhotoActivity.list.clear();
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(SampleOutDetailActivity.this, CheckPicActivity.class);
        intent.putExtra(MyConstant.PRINT_GLANCE_OVER_LIST, (Serializable) piclist);
        intent.putExtra(MyConstant.PRINT_GLANCE_OVER_POS, position);
        startActivity(intent);//选中图片
    }
}
