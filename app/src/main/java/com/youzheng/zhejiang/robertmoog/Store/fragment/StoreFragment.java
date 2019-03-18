package com.youzheng.zhejiang.robertmoog.Store.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.LoginActivity;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.CheckResultActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.CouponRecordActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.GoodsManageActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.OrderListActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.PeopleMangerActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ProfessionalCustomerActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ProfessionalCustomerOrderActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnRecognitionActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoodsManger.ReturnGoodsListActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.SampleOutInformationActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.SampleOutMangerActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.StoreCustomerActivity;
import com.youzheng.zhejiang.robertmoog.utils.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends BaseFragment implements BaseFragment.ReloadInterface {

    View mView;
    GridView gridView;
    CommonAdapter<HomeBean> adapter;
    List<HomeBean> data = new ArrayList<>();
    private View view;
    private ImageView btnBack;
    private String role;
    /**  */
    private TextView textHeadTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        role = (String) SharedPreferencesUtils.getParam(getActivity(), PublicUtils.role, "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.store_fragment_layout, null);

        initView();
        setUpView(mView);
        setReloadInterface(this);

        return mView;
    }



    @Override
    public void onResume() {
        super.onResume();

        data.clear();
        if (role.equals("SHOP_SELLER")) {
            data.add(new HomeBean("门店客户", R.mipmap.group_7_1));
            data.add(new HomeBean("订单管理", R.mipmap.group_7_2));
            data.add(new HomeBean("商品管理", R.mipmap.group_7_3));
            data.add(new HomeBean("退货", R.mipmap.group_7_10));
            data.add(new HomeBean("退货单管理", R.mipmap.group_7_11));

        } else {
            data.add(new HomeBean("门店客户", R.mipmap.group_7_1));
            data.add(new HomeBean("订单管理", R.mipmap.group_7_2));
            data.add(new HomeBean("商品管理", R.mipmap.group_7_3));
            data.add(new HomeBean("专业客户", R.mipmap.group_7_4));
            data.add(new HomeBean("专业客户订单", R.mipmap.group_7_5));
            data.add(new HomeBean("员工管理", R.mipmap.group_7_6));
            data.add(new HomeBean("出样填报", R.mipmap.group_7_7));
            data.add(new HomeBean("出样管理", R.mipmap.group_7_8));
            data.add(new HomeBean("巡店查询", R.mipmap.group_7_9));
            data.add(new HomeBean("退货", R.mipmap.group_7_10));
            data.add(new HomeBean("退货单管理", R.mipmap.group_7_11));
            data.add(new HomeBean("优惠券记录", R.mipmap.group_7_12));
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        gridView = mView.findViewById(R.id.gv);


        adapter = new CommonAdapter<HomeBean>(mContext, data, R.layout.home_ls_item) {
            @Override
            public void convert(ViewHolder helper, HomeBean item) {


                helper.setText(R.id.tv_text, item.name);
                helper.setImageResource(R.id.iv_icon, item.icon);


            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (role.equals("SHOP_SELLER")) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), StoreCustomerActivity.class));
                            break;

                        case 1:
                            startActivity(new Intent(getActivity(), OrderListActivity.class));
                            break;

                        case 2:
                            startActivity(new Intent(getActivity(), GoodsManageActivity.class));
                            break;

                        case 3:
                            startActivity(new Intent(getActivity(), ReturnRecognitionActivity.class));
                            break;

                        case 4:
                            Intent intent=new Intent(getActivity(), ReturnGoodsListActivity.class);
                            intent.putExtra("type","2");
                            startActivity(intent);
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), StoreCustomerActivity.class));
                            break;

                        case 1:
                            startActivity(new Intent(getActivity(), OrderListActivity.class));
                            break;

                        case 2:
                            startActivity(new Intent(getActivity(), GoodsManageActivity.class));
                            break;

                        case 3:
                            startActivity(new Intent(getActivity(), ProfessionalCustomerActivity.class));
                            break;

                        case 4:
                            startActivity(new Intent(getActivity(), ProfessionalCustomerOrderActivity.class));
                            break;

                        case 5:
                            startActivity(new Intent(getActivity(), PeopleMangerActivity.class));
                            break;

                        case 6:
                            startActivity(new Intent(getActivity(), SampleOutInformationActivity.class));
                            break;

                        case 7:
                            startActivity(new Intent(getActivity(), SampleOutMangerActivity.class));
                            break;

                        case 8:
                            startActivity(new Intent(getActivity(), CheckResultActivity.class));
                            break;

                        case 9:
                            startActivity(new Intent(getActivity(), ReturnRecognitionActivity.class));
                            break;

                        case 10:
                            Intent intent=new Intent(getActivity(), ReturnGoodsListActivity.class);
                            intent.putExtra("type","2");
                            startActivity(intent);
                            break;
                        case 11:
                            startActivity(new Intent(getActivity(), CouponRecordActivity.class));
                            break;
                    }
                }

            }
        });
        btnBack = (ImageView) mView.findViewById(R.id.btnBack);
        btnBack.setVisibility(View.GONE);


        textHeadTitle = (TextView) mView.findViewById(R.id.textHeadTitle);
        String title= (String) SharedPreferencesUtils.getParam(getActivity(),PublicUtils.shop_title,"");
        textHeadTitle.setText(title);
    }

    @Override
    public void reloadClickListener() {

    }

    public class HomeBean {
        String name;
        int icon;

        public HomeBean(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }
    }
}
