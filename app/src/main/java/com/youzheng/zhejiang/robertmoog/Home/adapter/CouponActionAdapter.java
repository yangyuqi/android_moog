package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBeanDetail;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponPromo;
import com.youzheng.zhejiang.robertmoog.Model.Home.PromoIdDetails;
import com.youzheng.zhejiang.robertmoog.Model.Home.PromoIdDetailsData;
import com.youzheng.zhejiang.robertmoog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponActionAdapter extends RecyclerView.Adapter<CouponActionAdapter.CouponViewHolder> {

    List<CouponPromo> couponPromos ;
    Context context ;
    String type ;//1使用 2未使用 3.账户
    List<CouponPromo.CouponShopsBean> shop_List;
    public CouponActionAdapter(List<CouponPromo> couponPromo, Context mContext) {
        this.couponPromos = couponPromo ;
        this.context = mContext;

    }

    public void setData(List<CouponPromo> couponPromos){
        this.couponPromos = couponPromos;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coupon_action_item,null);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CouponViewHolder couponViewHolder, final int i) {
        final CouponPromo couponPromo=couponPromos.get(i);


        couponViewHolder.tv_money.setText(couponPromo.getCouponMoney()+"");
        couponViewHolder.tv_use_money.setText(couponPromo.getCouponCondition());
        couponViewHolder.tv_name.setText(couponPromo.getCouponType());


        try {
            couponViewHolder.tv_time.setText(StringToDate(couponPromo.getStartTime())+"-"+StringToDate(couponPromo.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (couponPromo.getCouponTypeId().equals("ORDER")){
            couponViewHolder.rl_bg.setBackgroundResource(R.mipmap.group_24_4);
        }else if (couponPromo.getCouponTypeId().equals("CATEGORY")){
            couponViewHolder.rl_bg.setBackgroundResource(R.mipmap.group_24_5);
        }

//        if (couponPromo.isClick()){
//            couponViewHolder.cb.setImageResource(R.mipmap.group_24_1);
//        }else {
//            couponViewHolder.cb.setImageResource(R.mipmap.group_24_2);
//        }

        if (couponPromo.getCouponShops().size()>0){
            couponViewHolder.tv_show.setVisibility(View.VISIBLE);
            shop_List = couponPromo.getCouponShops();
            couponViewHolder.iv_show.setImageResource(R.mipmap.group_25_1);
            couponPromo.setExpress(true);
            CommonAdapter<CouponPromo.CouponShopsBean> com_adapter = new CommonAdapter<CouponPromo.CouponShopsBean>(context,shop_List,R.layout.coupin_ls_item) {
                @Override
                public void convert(ViewHolder helper, CouponPromo.CouponShopsBean item) {
                    helper.setText(R.id.tv_details,item.getShopName());
                }
            };
            couponViewHolder.ls.setAdapter(com_adapter);
            couponViewHolder.lin_show.setVisibility(View.VISIBLE);
            couponViewHolder.lin_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      shop_List = new ArrayList<>();
                    if (couponPromo.isExpress()){
                        couponViewHolder.iv_show.setImageResource(R.mipmap.group_24_3);
                        shop_List.clear();
                        couponPromo.setExpress(false);
                        couponViewHolder.tv_together.setVisibility(View.GONE);
                        couponViewHolder.tv_show.setVisibility(View.GONE);
                    }else {
                        couponViewHolder.tv_show.setVisibility(View.VISIBLE);
                        shop_List = couponPromo.getCouponShops();
                        couponViewHolder.tv_together.setVisibility(View.VISIBLE);
                        couponViewHolder.iv_show.setImageResource(R.mipmap.group_25_1);
                        couponPromo.setExpress(true);
                    }

                    CommonAdapter<CouponPromo.CouponShopsBean> com_adapter = new CommonAdapter<CouponPromo.CouponShopsBean>(context,shop_List,R.layout.coupin_ls_item) {
                        @Override
                        public void convert(ViewHolder helper, CouponPromo.CouponShopsBean item) {
                                helper.setText(R.id.tv_details,item.getShopName());
                        }
                    };
                    couponViewHolder.ls.setAdapter(com_adapter);
                }
            });
        }else {

            couponViewHolder.lin_show.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(couponPromo.getCouponCategory())){
            couponViewHolder.tv_together.setVisibility(View.VISIBLE);
            couponViewHolder.tv_together.setText("参与活动的品类 :"+couponPromo.getCouponCategory());
        }else {
            couponViewHolder.tv_together.setVisibility(View.GONE);
        }
    }
    private String StringToDate(String time) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        date = format.parse(time);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String s = format1.format(date);
        return s;

    }
    @Override
    public int getItemCount() {
        return couponPromos.size();
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder{

        TextView tv_money ,tv_use_money ,tv_name ,tv_time,tv_show,tv_together ;
        RelativeLayout rl_bg ;
        ImageView cb ;
        ListView ls ;
        ImageView iv_show ;
        LinearLayout lin_show;
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_use_money = itemView.findViewById(R.id.tv_use_money);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            cb = itemView.findViewById(R.id.cb);
            iv_show = itemView.findViewById(R.id.iv_show);
            ls = itemView.findViewById(R.id.ls);
            tv_show = itemView.findViewById(R.id.tv_show);
            tv_together = itemView.findViewById(R.id.tv_together);
            lin_show=itemView.findViewById(R.id.lin_show);
        }
    }
}
