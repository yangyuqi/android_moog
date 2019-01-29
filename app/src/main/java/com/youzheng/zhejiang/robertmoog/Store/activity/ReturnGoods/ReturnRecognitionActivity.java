package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.RegisterSuccessActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.PhoneUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class ReturnRecognitionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 输入客户手机号,识别后快速进入服务
     */
    private EditText tv_search;
    private ImageView iv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_recognition);
        initView();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.customer_recognition));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_search = (EditText) findViewById(R.id.tv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.iv_search:
                if (tv_search.getText().toString().equals("")){
                    showToast(getString(R.string.phone_not_null));
                }else if (tv_search.getText().toString().length()<11){
                    showToast("手机号有误,请重新输入");
                }else if (PhoneUtil.isCellphone(tv_search.getText().toString())==false){
                    showToast("手机号格式错误,请重新输入");
                }else {
                    Recognition();
                }

                break;
        }
    }

    private void Recognition(){
        Map<String,Object> map = new HashMap<>();
        map.put("phone",tv_search.getText().toString());
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_CUSTOMER+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void  onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    CustomerBean customerBean = gson.fromJson(gson.toJson(baseModel.getDatas()),CustomerBean.class);
                    if (!TextUtils.isEmpty(customerBean.getCustomer().getCustomerId())){
                        Intent intent6 = new Intent(mContext,ChooseOrderListActivity.class);
                        intent6.putExtra("customerId",customerBean.getCustomer().getCustomerId());
                        intent6.putExtra("identifion",true);
                        startActivity(intent6);
                    }

                }else {
                    showToast(baseModel.getMsg());
                }
            }
        });
    }
}
