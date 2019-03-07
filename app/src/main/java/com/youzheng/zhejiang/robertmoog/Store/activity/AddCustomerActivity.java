package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.AddProfessionalCustomerRequest;
import com.youzheng.zhejiang.robertmoog.Store.listener.GetListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.ButtonUtils;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.Store.view.SingleOptionsPicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * 添加专业客户界面
 */
public class AddCustomerActivity extends BaseActivity implements View.OnClickListener, GetListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext,tv_degree;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 请输入手机号码
     */
    private EditText edt_phone;
    /**
     * 请输入姓名
     */
    private EditText edt_name;
    private LinearLayout lin_degree;
    /**
     * 添加
     */
    private TextView tv_add;
    private List<String> list=new ArrayList<>();
    private String phone,name,id;
    private LinearLayout lin_show;
    private List<EnumsDatasBeanDatas> list1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        initView();
        initGetDate();
    }

    @Override
    public void getTextStr(String str,String title,int position) {

    }
    private void initGetDate() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LIST_DATA+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("获取时间枚举",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    EnumsDatas enumsDatas = gson.fromJson(gson.toJson(baseModel.getDatas()),EnumsDatas.class);
                    if (enumsDatas.getEnums()!=null){
                        if (enumsDatas.getEnums().size()>0){
                            for (final EnumsDatasBean bean : enumsDatas.getEnums()){
                                if (!TextUtils.isEmpty(bean.getClassName())){
                                    if (bean.getClassName().equals("SpecialtyType")){//  TimeQuantum
//                                final List<String> date = new ArrayList<String>();
                                        list1=new ArrayList<>();
                                        for (int i = 0; i < bean.getDatas().size(); i++) {
                                            list1.add(bean.getDatas().get(i));
                                            list.add(bean.getDatas().get(i).getDes());
                                        }

                                    }
                                }

                            }
                        }
                    }


                }
            }
        });

    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadTitle.setText(getString(R.string.add_professional_customer));
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_name = (EditText) findViewById(R.id.edt_name);
        lin_degree = (LinearLayout) findViewById(R.id.lin_degree);
        lin_degree.setOnClickListener(this);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_degree= (TextView) findViewById(R.id.tv_degree);
        lin_show=findViewById(R.id.lin_show);
        tv_add.setOnClickListener(this);

//        edt_name.addTextChangedListener(this);
//        edt_phone.addTextChangedListener(this);

        InputFilter typeFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
                Matcher m = p.matcher(source.toString());
                if (!m.matches()) return "";
                return null;
            }
        };

        edt_name.setFilters(new InputFilter[]{typeFilter});

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.lin_degree:
                 if (list!=null){
                     if (list.size()!=0){
                         SingleOptionsPicker.openOptionsPicker(this, list, tv_degree,getString(R.string.choose_rule),this);
                         SoftInputUtils.hideSoftInput(this);
                     }else {
                         showToasts("暂无数据");
                     }
                 }else {
                     showToasts("暂无数据");
                 }

                break;
            case R.id.tv_add:
                phone=edt_phone.getText().toString().trim();
                name=edt_name.getText().toString().trim();
                if (phone.equals("")){
                    showToasts(getString(R.string.no_phone));
                }else if (name.equals("")){
                    showToasts(getString(R.string.no_name));
                }else if (tv_degree.getText().toString().equals(getString(R.string.please_choose_rule))||tv_degree.getText().toString().equals("")){
                    showToasts(getString(R.string.have_no_rule));
                }else {
                    if (tv_degree.getText().toString().equals(getString(R.string.gong_zhang))){
                        id="FOREMAN";
                    }else if (tv_degree.getText().toString().equals(getString(R.string.desinger))){
                        id="DESIGNER";
                    }
                        //写你相关操作即可
                    addProfessionalCustomer(phone,name,id);


        }
                break;
        }
    }

    private void  addProfessionalCustomer(String phone, String name,String id){
        Log.e("走了几次啊","11111");
        tv_add.setClickable(false);
        lin_show.setVisibility(View.VISIBLE);
        HashMap<String,Object> map=new HashMap<>();
        map.put("custCode",phone);
        map.put("custName",name);
        AddProfessionalCustomerRequest request=new AddProfessionalCustomerRequest();
        request.setId(id);
        map.put("career",request);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADD_PROFESSIONAL_CUSTOMER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                lin_show.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
                Log.e("添加专业客户",response);
                lin_show.setVisibility(View.GONE);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel!=null){
                    if (baseModel.getCode()==PublicUtils.code){
                        tv_add.setClickable(true);
                        finish();
                    }else {
                        if (!baseModel.getMsg().equals("")){
                            showToasts(baseModel.getMsg());
                            tv_add.setClickable(true);
                        }
                    }

                }

            }
        });
    }





}
