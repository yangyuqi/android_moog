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
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;

import java.util.List;

/**
 *sss
 */
public class SmallChooseReturnGoodsAdapter extends BaseAdapter {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list;
    private Context context;
    private LayoutInflater layoutInflater;


    public SmallChooseReturnGoodsAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.group_9_1).into(viewHolder.iv_goods);
        viewHolder.tv_goods_code.setText(bean.getSku());
        viewHolder.tv_goods_content.setText(bean.getName());
        viewHolder.tv_money.setText(context.getString(R.string.label_money) + bean.getRefundAmount());
        viewHolder.tv_return_number.setText(bean.getCount() + "");


        return convertView;
    }

    class ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods_code, tv_goods_content, tv_money, tv_return_number;
    }
}
