package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.util.List;

public class  OneOrderDetailAdapter  extends RecyclerView.Adapter<OneOrderDetailAdapter.OneHolder> {
    private List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public OneOrderDetailAdapter(List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    public void setUI(List<OrderlistDetail.OrderItemDataBean.OrderProductListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.item_order_detail_one,viewGroup,false);
        OneHolder oneHolder=new OneHolder(view);
        return oneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OneHolder oneHolder, int position) {
        OrderlistDetail.OrderItemDataBean.OrderProductListBean bean=list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(oneHolder.iv_goods);
        oneHolder.tv_goods_code.setText(bean.getSku());
        oneHolder.tv_goods_content.setText(bean.getName());
        oneHolder.tv_money.setText(context.getString(R.string.label_money)+bean.getPrice());
        oneHolder.tv_number.setText("X "+bean.getCount()+"");

        oneHolder.tv_goods_code.setText(bean.getSku());


        if (bean.getReturnCount()!=0){
            oneHolder.tv_state.setText("已退"+bean.getReturnCount()+""+"件");
        }else {
            oneHolder.tv_state.setVisibility(View.GONE);
        }


       // oneHolder.tv_pu_code.setText(bean.getCodePu());

        if (bean.isIsSpecial()==true){
            if (bean.getSquare()!=0){
                oneHolder.tv_area.setText(bean.getSquare()+"平方");
            }else {
                oneHolder.tv_area.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(bean.getAddPrice())){
                oneHolder.tv_add_money.setText("增项加价 "+context.getString(R.string.label_money)+" "+bean.getAddPrice());
            }else {
                oneHolder.tv_add_money.setVisibility(View.GONE);
            }
          if (!TextUtils.isEmpty(bean.getCodePu())){
              oneHolder.tv_pu_code.setText("PU单号: "+" "+bean.getCodePu());
          }else {
              oneHolder.tv_pu_code.setVisibility(View.GONE);
          }
        }else {
            oneHolder.tv_area.setVisibility(View.GONE);
            oneHolder.tv_add_money.setVisibility(View.GONE);
            oneHolder.tv_pu_code.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OneHolder  extends RecyclerView.ViewHolder{
        private ImageView iv_goods;
        private TextView tv_goods_code,tv_goods_content,
                tv_money,tv_number,tv_area,tv_add_money,tv_state,tv_pu_code;


        public OneHolder(@NonNull View itemView) {
            super(itemView);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            tv_goods_code=itemView.findViewById(R.id.tv_goods_code);
            tv_goods_content=itemView.findViewById(R.id.tv_goods_content);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_area=itemView.findViewById(R.id.tv_area);
            tv_add_money=itemView.findViewById(R.id.tv_add_money);
            tv_state=itemView.findViewById(R.id.tv_state);
            tv_pu_code=itemView.findViewById(R.id.tv_pu_code);


        }
    }
}
