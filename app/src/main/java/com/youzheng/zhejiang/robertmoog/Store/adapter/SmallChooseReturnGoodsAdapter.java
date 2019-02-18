package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class SmallChooseReturnGoodsAdapter extends BaseAdapter {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<TextView> textViews;
    private int addnumber;


    public SmallChooseReturnGoodsAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        textViews=new ArrayList<>();
    }

    public void setRefreshUI(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_small_choose_return_goods, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_goods = convertView.findViewById(R.id.iv_goods);
            viewHolder.tv_goods_code = convertView.findViewById(R.id.tv_goods_code);
            viewHolder.tv_goods_content = convertView.findViewById(R.id.tv_goods_content);
            viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
            viewHolder.tv_return_number = convertView.findViewById(R.id.tv_return_number);
            viewHolder.tv_cut = convertView.findViewById(R.id.tv_cut);
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
            viewHolder.tv_add = convertView.findViewById(R.id.tv_add);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(viewHolder.iv_goods);
        viewHolder.tv_goods_code.setText(bean.getSku());
        viewHolder.tv_goods_content.setText(bean.getName());
        viewHolder.tv_money.setText(context.getString(R.string.label_money) + bean.getRefundAmount());
        viewHolder.tv_return_number.setText(bean.getCount() + "");

        if (bean.getCount()==0){
            viewHolder.tv_number.setText("0");
        }else {
            viewHolder.tv_number.setText("0");//默认为1
        }
        final String num=viewHolder.tv_number.getText().toString();
        addnumber= Integer.parseInt(num);
        bean.setNum(addnumber+"");

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (addnumber==bean.getCount()){
                   //  finalViewHolder.tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                     Toast.makeText(context,"商品数量不能超过可退商品数量",Toast.LENGTH_SHORT).show();
                 }else {
//                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                         finalViewHolder.tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
//                     }
                     addnumber++;
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                         finalViewHolder.tv_cut.setBackground(context.getDrawable(R.drawable.bg_order));

                     }
                 }
                if (addnumber==bean.getCount()){
                    finalViewHolder.tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finalViewHolder.tv_add.setBackground(context.getDrawable(R.drawable.bg_order));

                    }

                }

                finalViewHolder.tv_number.setText(addnumber+"");
                bean.setNum(addnumber+"");
            }
        });



       // final String num1=viewHolder.tv_number.getText().toString();

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.tv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addnumber==0){
                    Toast.makeText(context,"商品数量不能小于0",Toast.LENGTH_SHORT).show();
                }else {
                    addnumber--;

                }
                if (addnumber==0){
                    finalViewHolder1.tv_cut.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finalViewHolder1.tv_cut.setBackground(context.getDrawable(R.drawable.bg_order));

                    }

                }

                finalViewHolder1.tv_number.setText(addnumber+"");
                bean.setNum(addnumber+"");
            }
        });





//        String goodsId=bean.getOrderItemProductId();
//        finalViewHolder1.tv_number.setTag(goodsId);
//
//        textViews.add(finalViewHolder1.tv_number);


        return convertView;
    }

    public List<TextView> getText(){

        return textViews;
    }

    class ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods_code, tv_goods_content,
                tv_money, tv_return_number,tv_cut,tv_number,tv_add;
    }
}
