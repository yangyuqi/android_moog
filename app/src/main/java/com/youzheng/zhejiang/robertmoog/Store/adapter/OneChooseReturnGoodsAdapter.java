package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.util.ArrayList;
import java.util.List;

/**
 *sss
 */
public class OneChooseReturnGoodsAdapter extends RecyclerView.Adapter<OneChooseReturnGoodsAdapter.OneHolder> {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<TextView> textlist;
    private int addnumber;

    public OneChooseReturnGoodsAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
        textlist=new ArrayList<>();
    }

    public void setUI(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.item_choose_return_goods_one,viewGroup,false);
        OneHolder oneHolder=new OneHolder(view);
        return oneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OneHolder oneHolder, int position) {
        final ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean bean=list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.group_9_1).into(oneHolder.iv_goods);
        oneHolder.tv_goods_code.setText(bean.getSku());
        oneHolder.tv_goods_content.setText(bean.getName());
        oneHolder.tv_money.setText(context.getString(R.string.label_money)+bean.getRefundAmount());
        oneHolder.tv_goods_code.setText(bean.getSku());
        oneHolder.tv_return_number.setText(bean.getCount()+"");


        if (bean.isIsSpecial()==true){
            oneHolder.tv_area.setVisibility(View.VISIBLE);
            oneHolder.tv_area.setText(bean.getSquare());

        }else {
            oneHolder.tv_area.setVisibility(View.GONE);
        }
        if (bean.getCount()==0){
            oneHolder.tv_number.setText("0");
        }else {
            oneHolder.tv_number.setText("1");//默认为1
        }

        final String num=oneHolder.tv_number.getText().toString();
        addnumber= Integer.parseInt(num);
        bean.setNum(addnumber+"");

        oneHolder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addnumber==bean.getCount()){
                    Toast.makeText(context,"商品数量不能超过可退商品数量",Toast.LENGTH_SHORT).show();
                }else {
                    addnumber++;
                }


                oneHolder.tv_number.setText(addnumber+"");
                bean.setNum(addnumber+"");
            }
        });


        oneHolder.tv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addnumber==0){
                    Toast.makeText(context,"商品数量不能小于0",Toast.LENGTH_SHORT).show();
                }else {
                    addnumber--;
                }
                oneHolder.tv_number.setText(addnumber+"");
                bean.setNum(addnumber+"");
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OneHolder  extends RecyclerView.ViewHolder{
        private ImageView iv_goods;
        private TextView tv_goods_code,tv_goods_content,
                tv_money,tv_area,tv_cut,tv_number,tv_add,tv_return_number;


        public OneHolder(@NonNull View itemView) {
            super(itemView);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            tv_goods_code=itemView.findViewById(R.id.tv_goods_code);
            tv_goods_content=itemView.findViewById(R.id.tv_goods_content);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_area=itemView.findViewById(R.id.tv_area);
            tv_cut=itemView.findViewById(R.id.tv_cut);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_add=itemView.findViewById(R.id.tv_add);
            tv_return_number=itemView.findViewById(R.id.tv_return_number);

        }
    }


}
