package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.R;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    ArrayList<CouponListBean> useCouponList ;
    Context context ;
    String type ;//1使用 2未使用

    public void setData(ArrayList<CouponListBean> useCouponList , Context context ,String type){
        this.useCouponList = useCouponList ;
        this.context = context;
        this.type = type ;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coupon_rv_item,null);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder couponViewHolder, int i) {

        if (type.equals("2")){
            couponViewHolder.rl_bg.setBackgroundResource(R.mipmap.group_24_5);
            couponViewHolder.cb.setVisibility(View.VISIBLE);
        }else {
            couponViewHolder.rl_bg.setBackgroundResource(R.mipmap.group_24_4);
            couponViewHolder.cb.setVisibility(View.GONE);
        }

        couponViewHolder.tv_money.setText(useCouponList.get(i).getPayValue());
        couponViewHolder.tv_use_money.setText("满"+useCouponList.get(i).getUseCondition()+"元可用");
        couponViewHolder.tv_name.setText(useCouponList.get(i).getCouponType());
        couponViewHolder.tv_time.setText(useCouponList.get(i).getStartDate()+"-"+useCouponList.get(i).getEndDate());
        
    }

    @Override
    public int getItemCount() {
        return useCouponList.size();
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder{

        TextView tv_money ,tv_use_money ,tv_name ,tv_time ;
        RelativeLayout rl_bg ;
        CheckBox cb ;
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_use_money = itemView.findViewById(R.id.tv_use_money);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            cb = itemView.findViewById(R.id.cb);
        }
    }
}
