package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.CouponListBeanDetail;
import com.youzheng.zhejiang.robertmoog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    ArrayList<CouponListBean> useCouponList ;
    Context context ;
    String type ;//1使用 2未使用 3.账户

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
    public void onBindViewHolder(@NonNull final CouponViewHolder couponViewHolder, final int i) {

        if (type.equals("1")){
            couponViewHolder.cb.setVisibility(View.VISIBLE);
            couponViewHolder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        for (CouponListBean bean : useCouponList){
                            if (bean.getAssetId().equals(useCouponList.get(i).getAssetId())){
                                if (bean.isClick()){
                                    bean.setClick(false);
                                }else {
                                    bean.setClick(true);
                                }
                            }else {
                                bean.setClick(false);
                            }
                        }
                    notifyDataSetChanged();
                }
            });

        }else if (type.equals("2")){
            couponViewHolder.cb.setVisibility(View.GONE);
            couponViewHolder.cb.setOnClickListener(null);
        }else {
            couponViewHolder.cb.setVisibility(View.GONE);
        }




        couponViewHolder.tv_money.setText(useCouponList.get(i).getPayValue());
        couponViewHolder.tv_use_money.setText("满"+useCouponList.get(i).getUseCondition()+"元可用");
        couponViewHolder.tv_name.setText(useCouponList.get(i).getCouponType());
        try {
            couponViewHolder.tv_time.setText(StringToDate(useCouponList.get(i).getStartDate())+" - "+StringToDate(useCouponList.get(i).getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (useCouponList.get(i).getCouponType().equals("订单满减")){
            couponViewHolder.rl_bg.setBackgroundResource(R.mipmap.group_24_4);
        }else if (useCouponList.get(i).getCouponType().equals("品类满减")){
            couponViewHolder.rl_bg.setBackgroundResource(R.mipmap.group_24_5);
        }

        if (useCouponList.get(i).isClick()){
            couponViewHolder.cb.setImageResource(R.mipmap.group_24_1);
        }else {
            couponViewHolder.cb.setImageResource(R.mipmap.group_24_2);
        }

        if (useCouponList.get(i).getShopList().size()>0){
            couponViewHolder.iv_show.setVisibility(View.VISIBLE);
            couponViewHolder.iv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     List<CouponListBeanDetail> shop_List = new ArrayList<>();
                    if (useCouponList.get(i).isExpress()){
                        couponViewHolder.iv_show.setImageResource(R.mipmap.group_24_3);
                        shop_List.clear();
                        useCouponList.get(i).setExpress(false);
                        couponViewHolder.tv_show.setVisibility(View.GONE);
                    }else {
                        couponViewHolder.tv_show.setVisibility(View.VISIBLE);
                        shop_List = useCouponList.get(i).getShopList();
                        couponViewHolder.iv_show.setImageResource(R.mipmap.group_25_1);
                        useCouponList.get(i).setExpress(true);
                    }

                    CommonAdapter<CouponListBeanDetail> com_adapter = new CommonAdapter<CouponListBeanDetail>(context,shop_List,R.layout.coupin_ls_item) {
                        @Override
                        public void convert(ViewHolder helper, CouponListBeanDetail item) {
                                helper.setText(R.id.tv_details,item.getShopName());
                        }
                    };
                    couponViewHolder.ls.setAdapter(com_adapter);
                }
            });
        }else {

            couponViewHolder.iv_show.setVisibility(View.GONE);
        }

        if (useCouponList.get(i).getCouponCategory()!=null){
            couponViewHolder.tv_together.setVisibility(View.VISIBLE);
            couponViewHolder.tv_together.setText("参与活动的品类 :"+useCouponList.get(i).getCouponCategory());
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
        return useCouponList.size();
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder{

        TextView tv_money ,tv_use_money ,tv_name ,tv_time,tv_show,tv_together ;
        RelativeLayout rl_bg ;
        ImageView cb ;
        ListView ls ;
        ImageView iv_show ;
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
        }
    }
}
