package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.Home.bean.SearchMeal;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;

import java.util.ArrayList;
import java.util.List;

/**
 *sss
 */
public class SmallMealAdapter extends BaseAdapter {
    private List<SearchMeal.SelectProductsBean.ProductListBean> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<TextView> textViews;
    private int addnumber;


    public SmallMealAdapter(List<SearchMeal.SelectProductsBean.ProductListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
       // textViews=new ArrayList<>();
    }

    public void setRefreshUI(List<SearchMeal.SelectProductsBean.ProductListBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_goods2, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_goods = convertView.findViewById(R.id.iv_goods);
            viewHolder.tv_goods_name = convertView.findViewById(R.id.tv_goods_name);
            viewHolder.tv_goods_content = convertView.findViewById(R.id.tv_goods_content);
            viewHolder.tv_goods_money = convertView.findViewById(R.id.tv_goods_money);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SearchMeal.SelectProductsBean.ProductListBean bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(viewHolder.iv_goods);
        viewHolder.tv_goods_name.setText(bean.getSku());
        viewHolder.tv_goods_content.setText(bean.getName());
        viewHolder.tv_goods_money.setText(context.getString(R.string.label_money) + bean.getPrice());
//        viewHolder.tv_return_number.setText(bean.getCount() + "");

        return convertView;
    }

    public List<TextView> getText(){

        return textViews;
    }

    class ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods_name, tv_goods_content,
                tv_goods_money;
    }
}
