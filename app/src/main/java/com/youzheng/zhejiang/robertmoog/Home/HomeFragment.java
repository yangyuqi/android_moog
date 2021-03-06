package com.youzheng.zhejiang.robertmoog.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.AttentionIntentActivity;
import com.youzheng.zhejiang.robertmoog.Home.activity.ClientViewActivity;
import com.youzheng.zhejiang.robertmoog.Home.activity.RegisterActivity;
import com.youzheng.zhejiang.robertmoog.Home.activity.RegisterSuccessActivity;
import com.youzheng.zhejiang.robertmoog.Home.activity.SetMealActivity;
import com.youzheng.zhejiang.robertmoog.Home.activity.ShopActionActivity;
import com.youzheng.zhejiang.robertmoog.Home.adapter.BannerNormalAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.HomePageBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.utils.SoftInputUtils;
import com.youzheng.zhejiang.robertmoog.utils.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.utils.PhoneUtil;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;
import com.youzheng.zhejiang.robertmoog.utils.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class HomeFragment extends BaseFragment implements BaseFragment.ReloadInterface, TextWatcher {

    View mView, getmView;
    GridView gv;
    EditText tv_search;
    CommonAdapter<HomeBean> adapter;
    List<HomeBean> data = new ArrayList<>();
    RollPagerView rollPagerView;
    private View view;
    /**  */
    private TextView textHeadTitle;
    private boolean isnewCu;
    private boolean isNewMeal;
    private ImageView iv_clear;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_fragment_layout, null);
        getmView = mView.findViewById(R.id.include_state);
        initData();
        initView();
        setUpView(mView);
        setReloadInterface(this);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        mView.findViewById(R.id.btnBack).setVisibility(View.GONE);
        gv = mView.findViewById(R.id.gv);
        tv_search = mView.findViewById(R.id.tv_search);
        rollPagerView = mView.findViewById(R.id.rv);
        rollPagerView.setPlayDelay(2000);
        rollPagerView.setAnimationDurtion(500);

        data.clear();
        data.add(new HomeBean(getString(R.string.home_gv_one), R.mipmap.group_5_3));
        data.add(new HomeBean(getString(R.string.home_gv_two), R.mipmap.group_5_4));
        data.add(new HomeBean(getString(R.string.home_gv_three), R.mipmap.group_5_5));
        data.add(new HomeBean(getString(R.string.home_gv_five), R.mipmap.group_5_6));
        data.add(new HomeBean(getString(R.string.home_gv_six), R.mipmap.group_5_7));
        adapter = new CommonAdapter<HomeBean>(mContext, data, R.layout.home_ls_item) {
            @Override
            public void convert(ViewHolder helper, HomeBean item) {
                helper.setText(R.id.tv_text, item.name);
                helper.setImageResource(R.id.iv_icon, item.icon);
                if (helper.getPosition() == 2) {
                    if (isnewCu == true) {
                        helper.getView(R.id.iv_emp).setVisibility(View.VISIBLE);
                    } else {
                        helper.getView(R.id.iv_emp).setVisibility(View.GONE);
                    }
                }
                if (helper.getPosition() == 4) {
                    if (isNewMeal == true) {
                        helper.getView(R.id.iv_emp).setVisibility(View.VISIBLE);
                    } else {
                        helper.getView(R.id.iv_emp).setVisibility(View.GONE);
                    }
                }
            }
        };
        gv.setAdapter(adapter);
        mView.findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(getActivity());
                if (tv_search.getText().toString().equals("")) {
                    showToasts(getString(R.string.login_input_phone));
                    return;
                } else if (PhoneUtil.isCellphone(tv_search.getText().toString()) == false) {
                    showToasts("手机号码格式不正确");
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("phone", tv_search.getText().toString());
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_CUSTOMER + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                            if (baseModel.getCode() == PublicUtils.code) {
                                CustomerBean customerBean = gson.fromJson(gson.toJson(baseModel.getDatas()), CustomerBean.class);
                                Intent intent = new Intent(mContext, RegisterSuccessActivity.class);
                                intent.putExtra("customer", customerBean.getCustomer());
                                intent.putExtra("referee", customerBean.getCustomer().getReferee());
                                intent.putExtra("identity", customerBean.getCustomer().getIdentity());
                                startActivity(intent);
                            } else {
                                //showToast(baseModel.getMsg());
                                if (baseModel.getCode() == PublicUtils.no_exist) {
                                    final RemindDialog dialog = new RemindDialog(mContext, new RemindDialog.onSuccessClick() {
                                        @Override
                                        public void onSuccess() {
                                            Intent intent = new Intent(mContext, RegisterActivity.class);
                                            intent.putExtra("no_phone", tv_search.getText().toString());
                                            startActivity(intent);
                                        }
                                    }, "2");
                                    dialog.show();
                                }
                            }
                        }
                    });
                }
            }
        });
        textHeadTitle = (TextView) mView.findViewById(R.id.textHeadTitle);
        String title = (String) SharedPreferencesUtils.getParam(getActivity(), PublicUtils.shop_title, "");
        textHeadTitle.setText(title);
        iv_clear = (ImageView) mView.findViewById(R.id.iv_clear);

        tv_search.addTextChangedListener(this);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText("");
            }
        });
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (tv_search.length()==0){
            iv_clear.setVisibility(View.GONE);
        }else {
            iv_clear.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initClick();
        initData();
    }

    private void initData() {
        access_token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token, "");
        if (!access_token.equals("")) {
            OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.HOME_INFO + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    Log.e("首页信息", response);
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        HomePageBean homePageData = gson.fromJson(gson.toJson(baseModel.getDatas()), HomePageBean.class);
                        if (homePageData.getHomePageData().getBannerImageData().size() > 0) {
                            rollPagerView.setAdapter(new BannerNormalAdapter(homePageData.getHomePageData().getBannerImageData(), access_token));
                            if (homePageData.getHomePageData().getBannerImageData().size()==1){
                                rollPagerView.setPlayDelay(0);
                                rollPagerView.setHintView(null);
                            }else {
                                //自定义指示器图片
                                rollPagerView.setHintView(new IconHintView(getActivity(), R.mipmap.group_72_2, R.mipmap.group_72_1));
                                rollPagerView.setPlayDelay(2000);
                            }
                        }

                        if (homePageData.getHomePageData().isNewCombo()==true) {
                            isNewMeal = true;
                            gv.setAdapter(adapter);
                            // gv.notify();
                            //ata.get(4).newCombo = true;
                        }else {
                            isNewMeal = false;
                            gv.setAdapter(adapter);
                            // gv.notify();
                        }
                        if (homePageData.getHomePageData().isNewPromotion()==true) {
                            isnewCu = true;
                            gv.setAdapter(adapter);
                            //  gv.notify();
                            //data.get(2).newPromotion = true;
                        }else {
                            isnewCu = false;
                            gv.setAdapter(adapter);
                            //  gv.notify();
                        }


                    }
                }
            });
        }
    }

    private void initClick() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        startActivity(new Intent(mContext, LoginActivity.class));
                        startActivity(new Intent(mContext, RegisterActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, ClientViewActivity.class));
                        break;

                    case 2:
//                        startActivity(new Intent(mContext,LoginActivity.class));
                        Intent intent = new Intent(mContext, ShopActionActivity.class);
                        intent.putExtra("is_appear", true);
                        startActivity(intent);
                        break;

                    case 3:
                        startActivity(new Intent(mContext, AttentionIntentActivity.class));
                        break;

                    case 4:
                        startActivity(new Intent(mContext, SetMealActivity.class));
                        break;
                }
            }
        });
    }


    @Override
    public void reloadClickListener() {

    }



    public class HomeBean {
        String name;
        int icon;
        boolean newPromotion;
        boolean newCombo;

        public HomeBean(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }
    }


}
