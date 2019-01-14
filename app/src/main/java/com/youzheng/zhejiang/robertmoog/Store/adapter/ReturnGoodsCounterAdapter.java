package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsCounter;

import java.util.List;

public class ReturnGoodsCounterAdapter extends RecyclerView.Adapter<ReturnGoodsCounterAdapter.OneHolder> {
    private List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public ReturnGoodsCounterAdapter(List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    public void setUI(List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.item_return_goods_counter,viewGroup,false);
        OneHolder oneHolder=new OneHolder(view);
        return oneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OneHolder oneHolder, int position) {
        ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean bean=list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.group_9_1).into(oneHolder.iv_goods);
        oneHolder.tv_goods_code.setText(bean.getSku());
        oneHolder.tv_goods_content.setText(bean.getName());
        oneHolder.tv_money.setText(context.getString(R.string.label_money)+bean.getRefundAmount());
        oneHolder.tv_number.setText("X "+bean.getCount()+"");

        oneHolder.tv_goods_code.setText(bean.getSku());



        if (bean.isIsSpecial()==true){
            oneHolder.tv_area.setVisibility(View.VISIBLE);
            oneHolder.tv_area.setText(bean.getSquare());
        }else {
            oneHolder.tv_area.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OneHolder  extends RecyclerView.ViewHolder{
        private ImageView iv_goods;
        private TextView tv_goods_code,tv_goods_content,
                tv_money,tv_number,tv_area,et_money;


        public OneHolder(@NonNull View itemView) {
            super(itemView);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            tv_goods_code=itemView.findViewById(R.id.tv_goods_code);
            tv_goods_content=itemView.findViewById(R.id.tv_goods_content);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_area=itemView.findViewById(R.id.tv_area);
            et_money=itemView.findViewById(R.id.et_money);



        }
    }


}