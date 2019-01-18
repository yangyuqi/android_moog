package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.youzheng.zhejiang.robertmoog.Store.utils.ButtonUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

/**
 * 添加导购界面
 */
public class AddStaffActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 门店导购
     */
    private TextView tv_job;
    /**
     * 手机号码
     */
    private TextView tv_yu_shi_gui_num;
    /**
     * 必填
     */
    private EditText et_phone;
    /**
     * 确认添加
     */
    private TextView tv_add;
    /**
     * 姓名
     */
    private TextView mTvName;
    /**
     * 必填
     */
    private EditText mEtName;

    private String name,phone;
    private LinearLayout lin_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        initView();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.add_staff_account));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_job = (TextView) findViewById(R.id.tv_job);
        tv_yu_shi_gui_num = (TextView) findViewById(R.id.tv_yu_shi_gui_num);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mEtName = (EditText) findViewById(R.id.et_name);
        lin_show=findViewById(R.id.lin_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.tv_add:
                 phone=et_phone.getText().toString().trim();
                 name=mEtName.getText().toString().trim();

                 if (TextUtils.isEmpty(phone)){
                     showToast(getString(R.string.no_phone));
                 }else if (TextUtils.isEmpty(name)){
                     showToast(getString(R.string.no_name));
                 }else {
                     addStaff(name,phone);



                 }
                break;
        }
    }

    private void addStaff(String name,String phone){
        tv_add.setClickable(false);
        lin_show.setVisibility(View.VISIBLE);
        HashMap<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        map.put("name",name);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADD_SELLER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                lin_show.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
                Log.e("添加导购",response);
                lin_show.setVisibility(View.GONE);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel!=null){
                    if (baseModel.getCode()==PublicUtils.code){
                        finish();
                        tv_add.setClickable(true);
                    }else {
                        if (!baseModel.getMsg().equals("")){
                            showToast(baseModel.getMsg());
                            tv_add.setClickable(true);
                        }
                    }
                }
            }
        });
    }
}
