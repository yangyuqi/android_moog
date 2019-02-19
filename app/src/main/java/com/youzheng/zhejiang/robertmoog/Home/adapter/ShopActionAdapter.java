package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Model.Home.ComboPromoBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ProductListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ProductsBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.View.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class ShopActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ComboPromoBean> comboPromo;
    Context context ;
    List<ProductsBean> listBeanList ;

    public ShopActionAdapter(List<ComboPromoBean> comboPromo, Context mContext) {
        this.comboPromo = comboPromo ;
        this.context = mContext ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_action_details_rv_item,null);
        return new ShoptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        Glide.with(context).load(comboPromo.get(i).getComboImageUrl()).error(R.mipmap.type_icon).into(((ShoptViewHolder) viewHolder).iv_icon);
        ((ShoptViewHolder) viewHolder).tv_name.setText(comboPromo.get(i).getComboCode());
        ((ShoptViewHolder) viewHolder).tv_desc.setText(comboPromo.get(i).getComboName());
        ((ShoptViewHolder) viewHolder).tv_price.setText("¥"+comboPromo.get(i).getComboPrice());
        ((ShoptViewHolder) viewHolder).tv_num.setText(comboPromo.get(i).getComboDes());
        ((ShoptViewHolder) viewHolder).iv_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBeanList = new ArrayList<>();
                if (comboPromo.get(i).getProducts().size()>0) {
                    if (comboPromo.get(i).isExpress()) {
                        listBeanList.clear();
                        ((ShoptViewHolder) viewHolder).iv_show_more.setImageResource(R.mipmap.group_14_1);
                        comboPromo.get(i).setExpress(false);
                    } else {
                       // listBeanList = comboPromo.get(i).getProducts().subList(1, comboPromo.get(i).getProducts().size());
                        listBeanList=comboPromo.get(i).getProducts();
                        ((ShoptViewHolder) viewHolder).iv_show_more.setImageResource(R.mipmap.group_12_3);
                        comboPromo.get(i).setExpress(true);
                    }
                }
                CommonAdapter<ProductsBean> commonAdapter = new CommonAdapter<ProductsBean>(context,listBeanList,R.layout.common_goods_vh_item3) {
                    @Override
                    public void convert(ViewHolder helper, ProductsBean item) {
                        helper.setText(R.id.tv_name,item.getProductSku());
                        helper.setText(R.id.tv_desc,item.getProductName());
                        Glide.with(context).load(item.getProductImageUrl()).error(R.mipmap.type_icon).into((ImageView) helper.getView(R.id.iv_icon));
                    }
                };
                ((ShoptViewHolder) viewHolder).no_ls.setAdapter(commonAdapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comboPromo.size();
    }

    public class  ShoptViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_icon ,iv_show_more;
        public TextView tv_name ,tv_desc ,tv_price ,tv_num ;
        NoScrollListView no_ls ;

        public ShoptViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_price = itemView.findViewById(R.id.tv_price);
            no_ls = itemView.findViewById(R.id.no_ls);
            iv_show_more = itemView.findViewById(R.id.iv_show_more);
        }
    }
}
