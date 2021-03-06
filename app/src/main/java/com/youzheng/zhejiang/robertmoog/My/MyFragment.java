package com.youzheng.zhejiang.robertmoog.My;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.youzheng.zhejiang.robertmoog.Store.utils.PermissionUtils;
import com.youzheng.zhejiang.robertmoog.utils.ClickUtils;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.NoteInfoDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class MyFragment extends BaseFragment implements BaseFragment.ReloadInterface, View.OnClickListener {

    View mView;
    TextView tv_shop_name, tv_role, tv_loginOut, tv_about;
    public static String shopid;
    String token;
    ImageView iv_user_icon;

    /**  */
    private TextView textHeadTitle;
    private String url;

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    private View view;
    /**
     * wdada
     */
    private TextView tv_version;
    private LinearLayout lin_about;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment_layout, null);
        setUpView(mView);
        setReloadInterface(this);
        initView(mView);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initData() {
        token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token, "");
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.SHOP_SCVAN + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    ShopQRCodeBean qrCodeBean = gson.fromJson(gson.toJson(baseModel.getDatas()), ShopQRCodeBean.class);
                    if (qrCodeBean.getShopQRCode() != null) {
                        url = qrCodeBean.getShopQRCode();
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
        tv_about = mView.findViewById(R.id.tv_about);
//        tv_about.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), AboutAppActivity.class));
//            }
//        });
        iv_user_icon = mView.findViewById(R.id.iv_user_icon);
        mView.findViewById(R.id.tv_change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AlterPasswordActivity.class));
            }
        });

        iv_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url)) {

                    if (Build.VERSION.SDK_INT >= 23) {
                        if (PermissionUtils.permissionIsOpen(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                ) {


                            NoteInfoDialog infoDialog = new NoteInfoDialog(mContext, url);
                            infoDialog.show();
                        } else {

                            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                PermissionUtils.showCameraDialog(getString(R.string.content_str_read)
                                        , getActivity());
                            } else {

                                PermissionUtils.openSinglePermission(getActivity()
                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , PermissionUtils.CODE_MULTI);

                            }

                        }
                    } else {
                        NoteInfoDialog infoDialog = new NoteInfoDialog(mContext, url);
                        infoDialog.show();
                    }

                } else {
                    NoteInfoDialog infoDialog = new NoteInfoDialog(mContext, "");
                    infoDialog.show();
                }
            }
        });

        textHeadTitle = (TextView) mView.findViewById(R.id.textHeadTitle);
        String title = (String) SharedPreferencesUtils.getParam(getActivity(), PublicUtils.shop_title, "");
        textHeadTitle.setText(title);
        tv_version = (TextView) mView.findViewById(R.id.tv_version);
        if (!TextUtils.isEmpty(getAppVersionName(getActivity()))){
            tv_version.setText(getAppVersionName(getActivity()));
        }

        lin_about = (LinearLayout) mView.findViewById(R.id.lin_about);
        lin_about.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        final String token = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.access_token, "");
        if (!token.equals("")) {
            OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GET_USER_ONFO + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        UserConfigDataBean dataBean = gson.fromJson(gson.toJson(baseModel.getDatas()), UserConfigDataBean.class);
                        shopid = dataBean.getUserConfigData().getShopId();
                        if (dataBean.getUserConfigData().getUserRole().equals("SHOP_SELLER")) {
                            tv_role.setText(R.string.goods_guide);
                        } else {
                            tv_role.setText(R.string.goods_manager);
                        }
                        String phone = (String) SharedPreferencesUtils.getParam(mContext, PublicUtils.shop_phone, "");
                        tv_shop_name.setText(PublicUtils.phoneNum(phone));
                    }
                }

            });

            tv_loginOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ClickUtils.isFastDoubleClick()){
                        return;
                    }else {
                        RemindDialog dialog = new RemindDialog(mContext, new RemindDialog.onSuccessClick() {
                            @Override
                            public void onSuccess() {
                                OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.LOGIN_OUT + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        try {
                                            SharedPreferencesUtils.clear(mContext);
                                            getActivity().finish();
                                            startActivity(new Intent(mContext, LoginActivity.class));
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                                        if (baseModel.getCode() == PublicUtils.code) {
                                            SharedPreferencesUtils.clear(mContext);
                                            getActivity().finish();
                                            startActivity(new Intent(mContext, LoginActivity.class));
                                        } else {
                                            SharedPreferencesUtils.clear(mContext);
                                            getActivity().finish();
                                            startActivity(new Intent(mContext, LoginActivity.class));
                                            showToasts(baseModel.getMsg());
                                        }
                                    }
                                });
                            }
                        }, "3");

                        dialog.show();
                    }

                }
            });
        }
    }

    @Override
    public void reloadClickListener() {

    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.lin_about:
               // startActivity(new Intent(getActivity(), AboutAppActivity.class));
                break;
        }
    }
}
