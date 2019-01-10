package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CityList;
import com.youzheng.zhejiang.robertmoog.Model.Home.DistrictList;
import com.youzheng.zhejiang.robertmoog.Model.Home.ProvinceList;
import com.youzheng.zhejiang.robertmoog.Model.Home.StreetList;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class AddNewAddressActivity extends BaseActivity {

    private String customerId ,provinceList_id ,cityList_id ,districtList_id ,streetList;
    private EditText edt_name ,edt_phone ,edt_address ;
    private TextView edt_provice ,edt_city ,edt_town ,edt_street;
    private OptionsPickerView pvCustomTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_address_layout);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText(R.string.add_new_address);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customerId = getIntent().getStringExtra("customerId");
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_provice = findViewById(R.id.edt_provice);
        edt_city = findViewById(R.id.edt_city);
        edt_town = findViewById(R.id.edt_town);
        edt_address = findViewById(R.id.edt_address);
        edt_street = findViewById(R.id.edt_street);

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddress();
            }
        });

        edt_provice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_city.setText("");
                edt_town.setText("");
                OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GET_PRIVICE+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            final ProvinceList list = gson.fromJson(gson.toJson(baseModel.getDatas()),ProvinceList.class);
                            if (list.getProvinceList().size()>0){

                                final List<String> date = new ArrayList<String>();
                                for (int i = 0; i < list.getProvinceList().size(); i++) {
                                    date.add(list.getProvinceList().get(i).getPvceName());
                                }

                                pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        for (int i = 0; i < list.getProvinceList().size(); i++) {
                                            if (date.get(options1).equals(list.getProvinceList().get(i).getPvceName())) {
                                                edt_provice.setText(date.get(options1));
                                                provinceList_id = list.getProvinceList().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择省份").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });

        edt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (provinceList_id==null){
                    showToast("请先获取省份");
                    return;
                }
                edt_town.setText("");
                Map<String,Object> map = new HashMap<>();
                map.put("id",provinceList_id);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_CITY + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                           final CityList cityList = gson.fromJson(gson.toJson(baseModel.getDatas()),CityList.class);
                            if (cityList.getCityList().size()>0) {

                                final List<String> date = new ArrayList<String>();
                                for (int i = 0; i < cityList.getCityList().size(); i++) {
                                    date.add(cityList.getCityList().get(i).getCtName());
                                }

                                pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        for (int i = 0; i < cityList.getCityList().size(); i++) {
                                            if (date.get(options1).equals(cityList.getCityList().get(i).getCtName())) {
                                                edt_city.setText(date.get(options1));
                                                cityList_id = cityList.getCityList().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择城市").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });


        edt_town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (provinceList_id==null){
                    showToast("请先获取省份");
                    return;
                }
                if (cityList_id==null){
                    showToast("请先获取城市");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("id",cityList_id);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.DOSTRICT_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            final DistrictList districtList = gson.fromJson(gson.toJson(baseModel.getDatas()),DistrictList.class);
                            if (districtList.getDistrictList().size()>0){
                                final List<String> date = new ArrayList<String>();
                                for (int i = 0; i < districtList.getDistrictList().size(); i++) {
                                    date.add(districtList.getDistrictList().get(i).getDisName());
                                }

                                pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        for (int i = 0; i < districtList.getDistrictList().size(); i++) {
                                            if (date.get(options1).equals(districtList.getDistrictList().get(i).getDisName())) {
                                                edt_town.setText(date.get(options1));
                                                districtList_id = districtList.getDistrictList().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择县／区").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });

        edt_street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",districtList_id);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_STREET + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){
                            final StreetList districtList = gson.fromJson(gson.toJson(baseModel.getDatas()),StreetList.class);
                            if (districtList.getStreetList().size()>0){
                                final List<String> date = new ArrayList<String>();
                                for (int i = 0; i < districtList.getStreetList().size(); i++) {
                                    date.add(districtList.getStreetList().get(i).getStName());
                                }

                                pvCustomTime = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        for (int i = 0; i < districtList.getStreetList().size(); i++) {
                                            if (date.get(options1).equals(districtList.getStreetList().get(i).getStName())) {
                                                edt_street.setText(date.get(options1));
                                                streetList = districtList.getStreetList().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择街道").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void addAddress() {
        if (edt_name.getText().toString().equals("")){
            showToast("请先填写姓名");
            return;
        }
        if (edt_phone.getText().toString().equals("")){
            showToast("请先填写手机号");
            return;
        }

        if (provinceList_id==null){
            showToast("请先获取省份");
            return;
        }
        if (cityList_id==null){
            showToast("请先获取城市");
            return;
        }
        if (districtList_id==null){
            showToast("请先获取县／区");
            return;
        }
        if (streetList==null){
            showToast("请先获取街道");
            return;
        }
        if (edt_address.getText().toString().equals("")){
            showToast("请先填写详细地址");
            return;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("shipPerson",edt_name.getText().toString());
        map.put("shipMobile",edt_phone.getText().toString());
        map.put("shipProvince",provinceList_id);
        map.put("shipCity",cityList_id);
        map.put("shipDistrict",districtList_id);
        map.put("shipStreet",streetList);
        map.put("shipAddress",edt_address.getText().toString());
        map.put("customerId",customerId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADD_ADDRESS+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    finish();
                }
            }
        });
    }
}
