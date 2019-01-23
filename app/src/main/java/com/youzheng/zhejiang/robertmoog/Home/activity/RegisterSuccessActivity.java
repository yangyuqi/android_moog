package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Home.HomeFragment;
import com.youzheng.zhejiang.robertmoog.Model.Home.Customer;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ShopPersonalListBean;
import com.youzheng.zhejiang.robertmoog.Model.login.RegisterBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.OrderListActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ChooseOrderListActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ChooseReturnGoodsActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoodsManger.ReturnGoodsListActivity;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderList;
import com.youzheng.zhejiang.robertmoog.utils.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.android.CaptureActivity;
import com.youzheng.zhejiang.robertmoog.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class RegisterSuccessActivity extends BaseActivity {

    GridView gv ;
    CommonAdapter<HomeBean> adapter ;
    List<HomeBean> data = new ArrayList<>();
    RegisterBean registerBean ;
    TextView tv_phone ;
    Customer customer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_success_layout);
        initView();

        initEvent();
    }

    private void initEvent() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 3 :
                        if (registerBean!=null){
                            Intent intent = new Intent(mContext,LocationManageActivity.class);
                            intent.putExtra("customerId",registerBean.getCustomerId());
                            startActivity(intent);
                        }

                        if (customer!=null){
                            Intent intent = new Intent(mContext,LocationManageActivity.class);
                            intent.putExtra("customerId",customer.getCustomerId());
                            startActivity(intent);
                        }
                        break;

                    case 4 :
                        if (registerBean!=null){
                            Intent intent = new Intent(mContext,AttentionGoodsActivity.class);
                            intent.putExtra("registerBean",registerBean);
                            startActivity(intent);
                        }
                        if (customer!=null){
                            Intent intent = new Intent(mContext,AttentionGoodsActivity.class);
                            ShopPersonalListBean listBean = new ShopPersonalListBean();
                            listBean.setCustomerId(""+customer.getCustomerId());
                            intent.putExtra("label",listBean);
                            startActivity(intent);
                        }

                        break;

                    case 1:
                        Intent intent = new Intent(mContext,ShopActionActivity.class);
                        if (customer!=null){
                            intent.putExtra("customerId",Integer.parseInt(customer.getCustomerId()));
                        }else {
                            intent.putExtra("customerId",registerBean.getCustomerId());
                        }
                        startActivity(intent);
                        break;
                    case 0 :

                        RxPermissions permissions = new RxPermissions(RegisterSuccessActivity.this);
                        permissions.request(Manifest.permission.CAMERA,Manifest.permission.VIBRATE ,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean){
                                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
                                    if (customer!=null){
                                        intent1.putExtra("customerId",customer.getCustomerId());
                                    }else {
                                        intent1.putExtra("customerId",registerBean.getCustomerId());
                                    }
                                    startActivity(intent1);
                                }
                            }
                        });

                        break;

                    case 2 :
                        Intent intent2 = new Intent(mContext,ClientAccountActivity.class);
                        if (customer!=null){
                            intent2.putExtra("customerId",customer.getCustomerId());
                        }else {
                            intent2.putExtra("customerId",registerBean.getCustomerId());
                        }
                        startActivity(intent2);
                        break;

                    case 5:
                        Intent intent5 = new Intent(mContext,OrderListActivity.class);
                        intent5.putExtra("customerId",customer.getCustomerId());
                        intent5.putExtra("identifion",true);
                        startActivity(intent5);
                        break;

                    case 6:
                        Intent intent6 = new Intent(mContext,ChooseOrderListActivity.class);
                        intent6.putExtra("customerId",customer.getCustomerId());
                        intent6.putExtra("identifion",true);
                        startActivity(intent6);

                        break;


                    case 7:

                        Intent intent7 = new Intent(mContext,ReturnGoodsListActivity.class);
                        intent7.putExtra("customerId",customer.getCustomerId());
                        intent7.putExtra("identifion",true);
                        startActivity(intent7);

                        break;
                }
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText(R.string.register_success);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        customer = (Customer) getIntent().getSerializableExtra("customer");
        if (customer!=null){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("识别成功");
            ((TextView)findViewById(R.id.tv_type)).setText("识别成功");
            String phone = customer.getAccount().substring(0,3)+" "+customer.getAccount().substring(3,7)+" "+customer.getAccount().substring(7,11);
            tv_phone.setText("手机号码 : "+phone);
        }
        gv = (GridView) findViewById(R.id.gv);
        data.clear();
        registerBean = (RegisterBean) getIntent().getSerializableExtra("register");
        if (registerBean!=null){
            String phone = registerBean.getPhone().substring(0,3)+" "+registerBean.getPhone().substring(3,7)+" "+registerBean.getPhone().substring(7,11);
            tv_phone.setText(phone);
        }
        if (registerBean!=null) {
            data.add(new HomeBean("卖货", R.mipmap.group_34_2));
            data.add(new HomeBean("客户活动", R.mipmap.group_34_3));
            data.add(new HomeBean("客户账户", R.mipmap.group_34_4));
            data.add(new HomeBean("地址管理", R.mipmap.group_34_5));
            data.add(new HomeBean("意向管理", R.mipmap.group_5_6));
        }else {
            data.add(new HomeBean("卖货", R.mipmap.group_34_2));
            data.add(new HomeBean("客户活动", R.mipmap.group_34_3));
            data.add(new HomeBean("客户账户", R.mipmap.group_34_4));
            data.add(new HomeBean("地址管理", R.mipmap.group_34_5));
            data.add(new HomeBean("意向管理", R.mipmap.group_5_6));
            data.add(new HomeBean("客户订单", R.mipmap.group_35_1));
            data.add(new HomeBean("退货", R.mipmap.group_7_10));
            data.add(new HomeBean("退货单", R.mipmap.group_7_11));
        }
        adapter = new CommonAdapter<HomeBean>(mContext,data,R.layout.home_ls_item) {
            @Override
            public void convert(ViewHolder helper, HomeBean item) {
                helper.setText(R.id.tv_text,item.name);
                helper.setImageResource(R.id.iv_icon,item.icon);
            }
        };
        gv.setAdapter(adapter);
    }

    public class HomeBean{
        String name ;
        int icon ;
        public HomeBean(String name, int icon){
            this.name = name;
            this.icon = icon ;
        }
    }
}
