package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.GoodsList;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.util.List;

public class SmallDetailAdapter extends BaseAdapter {
    private List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean.ProductListBean> list;
    private Context context;
    private LayoutInflater layoutInflater;


    public SmallDetailAdapter(List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean.ProductListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setRefreshUI(List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean.ProductListBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_order_small_list, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_goods = convertView.findViewById(R.id.iv_goods);
            viewHolder.tv_goods_code = convertView.findViewById(R.id.tv_goods_code);
            viewHolder.tv_goods_content = convertView.findViewById(R.id.tv_goods_content);
            viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OrderlistDetail.OrderItemDataBean.OrderSetMealListBean.ProductListBean bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(viewHolder.iv_goods);
        viewHolder.tv_goods_code.setText(bean.getSku());
        viewHolder.tv_goods_content.setText(bean.getName());
        viewHolder.tv_money.setText(context.getString(R.string.label_money) + bean.getPrice());
        viewHolder.tv_number.setText("X" + bean.getCount() + "");


        return convertView;
    }

    class ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods_code, tv_goods_content, tv_money, tv_number;
    }
}
