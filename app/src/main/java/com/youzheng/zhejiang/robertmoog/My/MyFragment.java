package com.youzheng.zhejiang.robertmoog.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.LoginActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.login.ShopQRCodeBean;
import com.youzheng.zhejiang.robertmoog.Model.login.UserConfigDataBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialogInterface;
import com.youzheng.zhejiang.robertmoog.utils.View.NoteInfoDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class MyFragment extends BaseFragment implements BaseFragment.ReloadInterface{

    View mView ;
    TextView tv_shop_name ,tv_role ,tv_loginOut ,tv_about;
    public static String shopid;
     String token ;
    ImageView iv_user_icon ;

    /**  */
    private TextView textHeadTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment_layout,null);
        setUpView(mView);
        setReloadInterface(this);
        token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token,"");
        initView(mView);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SHOP_SCVAN+"?access_token="+access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ShopQRCodeBean qrCodeBean = gson.fromJson(gson.toJson(baseModel.getDatas()),ShopQRCodeBean.class);
                    if (qrCodeBean.getShopQRCode()!=null){
                        Glide.with(mContext).load(qrCodeBean.getShopQRCode()).error(R.mipmap.type_icon).into(iv_user_icon);
                    }
                }
            }
        });

    }

    private void initView(View mView) {
        mView.findViewById(R.id.btnBack).setVisibility(View.GONE);
        tv_shop_name = mView.findViewById(R.id.tv_shop_name);
        tv_role = mView.findViewById(R.id.tv_role);
        tv_loginOut = mView.findViewById(R.id.tv_loginOut);
        tv_about=mView.findViewById(R.id.tv_about);
        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AboutAppActivity.class));
            }
        });
        iv_user_icon = mView.findViewById(R.id.iv_user_icon);
        mView.findViewById(R.id.tv_change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AlterPasswordActivity.class));
            }
        });

        textHeadTitle = (TextView) mView.findViewById(R.id.textHeadTitle);
        String title= (String) SharedPreferencesUtils.getParam(getActivity(),PublicUtils.shop_title,"");
        textHeadTitle.setText(title);
    }

    @Override
    public void onResume() {
        super.onResume();
        final String token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token,"");
        if (!token.equals("")){
            OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GET_USER_ONFO + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                    if (baseModel.getCode()==PublicUtils.code){
                        UserConfigDataBean dataBean = gson.fromJson(gson.toJson(baseModel.getDatas()),UserConfigDataBean.class);
                        shopid=dataBean.getUserConfigData().getShopId();
                        if (dataBean.getUserConfigData().getUserRole().equals("SHOP_SELLER")){
                            tv_role.setText(R.string.goods_guide);
                        }else {
                            tv_role.setText(R.string.goods_manager);
                        }
                        tv_shop_name.setText(dataBean.getUserConfigData().getShopName());
                    }
                }

            });

            tv_loginOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RemindDialog dialog = new RemindDialog(mContext, new RemindDialog.onSuccessClick() {
                        @Override
                        public void onSuccess() {
                            OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LOGIN_OUT + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    SharedPreferencesUtils.clear(mContext);
                                    getActivity().finish();
                                    startActivity(new Intent(mContext,LoginActivity.class));
                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                                    if (baseModel.getCode()==PublicUtils.code){
                                        SharedPreferencesUtils.clear(mContext);
                                        getActivity().finish();
                                        startActivity(new Intent(mContext,LoginActivity.class));
                                    }else {
                                        SharedPreferencesUtils.clear(mContext);
                                        getActivity().finish();
                                        startActivity(new Intent(mContext,LoginActivity.class));
                                        showToast(baseModel.getMsg());
                                    }
                                }
                            });
                        }
                    },"3");

                    dialog.show();

                }
            });
        }
    }

    @Override
    public void reloadClickListener() {

    }
}
