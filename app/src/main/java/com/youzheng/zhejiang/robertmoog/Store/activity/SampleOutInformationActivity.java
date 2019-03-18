package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.SampleOutAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.CommitRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.SampleOutList;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.Store.view.MyListView;
import com.youzheng.zhejiang.robertmoog.utils.ClickUtils;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SampleOutInformationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private MyListView lv_information;
    private MyListView lv_rain_information;
    private List<SampleOutList.SampleResDataBean.SampleSingleDataListBean> list=new ArrayList<>();
    private List<SampleOutList.SampleResDataBean.SampleSingleDataListBean> list2=new ArrayList<>();
    private SampleOutAdapter adapter;
    private SampleOutAdapter out2Adapter;
    private TextView tv_commit;
    private int pos;
    private String text;
    private List<CommitRequest.ProductSampleDataBean> request=new ArrayList<>();
    private LinearLayout lin_title;
    private List<SampleOutList.SampleResDataBean.SampleSingleDataListBean> beans=new ArrayList<>();

    private View no_data,no_web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_out_information);
        initView();
        initData();

    }

    private void initView() {
        no_web = findViewById(R.id.no_web);
        no_data=findViewById(R.id.no_data);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadTitle.setText(getString(R.string.sample_out_information));
        textHeadNext.setText(getString(R.string.edit));
        textHeadNext.setOnClickListener(this);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        lv_information = (MyListView) findViewById(R.id.lv_information);
        lv_rain_information = (MyListView) findViewById(R.id.lv_rain_information);
        tv_commit=findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        lin_title=findViewById(R.id.lin_title);

    
//        initData();
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
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData() {

        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SAMPLE_OUT_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("出样信息列表",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    SampleOutList sampleOutList = gson.fromJson(gson.toJson(baseModel.getDatas()),SampleOutList.class);
                    setData(sampleOutList);
                }else {
                    showToasts(baseModel.getMsg());
                }
            }
        });

    }

    private void setData(SampleOutList sampleOutList) {
        if (sampleOutList.getSampleResData()==null) return;
        if (sampleOutList.getSampleResData().getSampleSingleDataList()==null) return;
         beans=sampleOutList.getSampleResData().getSampleSingleDataList();
        if (beans.size()!=0){
            for (int i = 0; i <beans.size() ; i++) {
                if (beans.get(i).getSampleType().equals(getString(R.string.sample_out_information))){
                    list.add(beans.get(i));
                    adapter=new SampleOutAdapter(list,this);
                    lv_information.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else if (beans.get(i).getSampleType().equals(getString(R.string.try_water_information))){
                    list2.add(beans.get(i));
                    out2Adapter= new SampleOutAdapter(list2,this);
                    lv_rain_information.setAdapter(out2Adapter);
                    out2Adapter.notifyDataSetChanged();
                }
            }
            no_data.setVisibility(View.GONE);
            lv_rain_information .setVisibility(View.VISIBLE);
            lv_information .setVisibility(View.VISIBLE);

            boolean isfirst= (boolean) SharedPreferencesUtils.getParam(this,PublicUtils.ISGO,true);
            if (isfirst){
                adapter.setAppear(true);
                out2Adapter.setAppear(true);
                textHeadNext.setText(getString(R.string.cancel));
                tv_commit.setVisibility(View.VISIBLE);
            }else {
                adapter.setAppear(false);
                out2Adapter.setAppear(false);
                textHeadNext.setText(getString(R.string.edit));
                tv_commit.setVisibility(View.GONE);
            }

        }else {
            lin_title.setVisibility(View.GONE);
            textHeadNext.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            lv_rain_information .setVisibility(View.GONE);
            lv_information .setVisibility(View.GONE);
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean ischanges=false;
            for (int i = 0; i < list.size() ; i++) {
                if (beans.get(i).isChange()){
                    ischanges=true;
                    break;
                }
            }

            for (int i = 0; i < list2.size() ; i++) {
                if (beans.get(i).isChange()){
                    ischanges=true;
                    break;
                }
            }

            if (ischanges==true){
                showStopDialog();
            }else {
                finish();
            }
            SharedPreferencesUtils.setParam(this,PublicUtils.ISGO,false);


        }
        return super.onKeyDown(keyCode, event);
    }

    public void showStopDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(SampleOutInformationActivity.this, R.style.mydialog).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_return22, null);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.show();
        dialogBuilder.setContentView(dialogView);

        TextView tv_no = dialogView.findViewById(R.id.tv_no);
        TextView tv_ok = dialogView.findViewById(R.id.tv_ok);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialogBuilder.dismiss();
            }
        });

        Window window = dialogBuilder.getWindow();
        //这句设置我们dialog在底部
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        DisplayMetrics d = this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
        // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                boolean ischanges=false;
                for (int i = 0; i < list.size() ; i++) {
                    if (beans.get(i).isChange()){
                        ischanges=true;
                        break;
                    }
                }

                for (int i = 0; i < list2.size() ; i++) {
                    if (beans.get(i).isChange()){
                        ischanges=true;
                        break;
                    }
                }

                if (ischanges==true){
                    showStopDialog();
                }else {
                    finish();
                }
                SharedPreferencesUtils.setParam(this,PublicUtils.ISGO,false);
                break;
            case R.id.textHeadNext:
                SoftInputUtils.hideSoftInput(this);
                if (beans!=null){
                    if (beans.size()!=0){
                        if (textHeadNext.getText().equals(getString(R.string.edit))){
                            adapter.setAppear(true);
                            out2Adapter.setAppear(true);
                            textHeadNext.setText(getString(R.string.cancel));
                            tv_commit.setVisibility(View.VISIBLE);
                        }else {
                            adapter.setAppear(false);
                            out2Adapter.setAppear(false);
                            textHeadNext.setText(getString(R.string.edit));
                            tv_commit.setVisibility(View.GONE);
                        }
                    }
                }

                break;
            case R.id.tv_commit:
                SoftInputUtils.hideSoftInput(this);
                if (ClickUtils.isFastDoubleClick()){
                    return;
                }else {
                    CommitData();
                }

                break;
        }
    }

    public void getEditData(int position,String str){
        //showToast("第"+position+"个"+str);
        pos=position;
        text=str;
    }



    public void setRequest(List<CommitRequest.ProductSampleDataBean> request){
               this.request=request;
    }

    private void CommitData(){
        request.clear();

        List<EditText> edList = adapter.getEdList();

        for (EditText editText : edList){
            String contentStr = editText.getText().toString().trim();
            int  id = (int) editText.getTag();
            Log.e("edstr ===" ,contentStr);
            Log.e("srrr ===" ,id+"");

            if (!TextUtils.isEmpty(contentStr)){
                CommitRequest.ProductSampleDataBean bean = new CommitRequest.ProductSampleDataBean();
                bean.setSampleId(id);
                bean.setSampleQuantity(Integer.parseInt(contentStr));
                request.add(bean);
            }

        }

        List<EditText> edList1 = out2Adapter.getEdList();
        for (EditText editText : edList1){
            String contentStr = editText.getText().toString().trim();
            int  id = (int) editText.getTag();
            Log.e("edstr ===" ,contentStr);
            Log.e("srrr ===" ,id+"");

            if (!TextUtils.isEmpty(contentStr)){
                CommitRequest.ProductSampleDataBean bean = new CommitRequest.ProductSampleDataBean();
                bean.setSampleId(id);
                bean.setSampleQuantity(Integer.parseInt(contentStr));
                request.add(bean);
            }

        }


        HashMap<String,Object> map=new HashMap<>();
            map.put("productSampleData",request);
            Log.e("12313",gson.toJson(map));

            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SAMPLE_OUT_COMMIT + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    Log.e("门店客户列表",response);
                    BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                    if (baseModel.getCode()==PublicUtils.code){
                        showToasts(getString(R.string.commit_success));
                       // finish();
                        SharedPreferencesUtils.setParam(SampleOutInformationActivity.this,PublicUtils.ISGO,false);
                        list.clear();
                        list2.clear();
                        initData();
                        adapter.setAppear(false);
                        out2Adapter.setAppear(false);
                        textHeadNext.setText(getString(R.string.edit));
                        tv_commit.setVisibility(View.GONE);
                    }
                }
            });


    }
}
