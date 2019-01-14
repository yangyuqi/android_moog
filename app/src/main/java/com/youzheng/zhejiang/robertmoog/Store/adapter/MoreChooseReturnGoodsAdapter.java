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
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderlistDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 *ssss
 */
public class MoreChooseReturnGoodsAdapter extends RecyclerView.Adapter<MoreChooseReturnGoodsAdapter.MoreHolder> {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private SmallChooseReturnGoodsAdapter adapter;
   // private List<OrderlistDetail.OrderItemDataBean.OrderSetMealListBean.ProductListBean> smalllist;

    public MoreChooseReturnGoodsAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean> list,
                                        Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setUI(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_choose_return_goods_more, viewGroup, false);
        MoreHolder oneHolder = new MoreHolder(view);
        return oneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MoreHolder moreHolder, final int position) {
        ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.group_9_1).into(moreHolder.iv_goods);
        moreHolder.tv_goods_code.setText(bean.getCode());
        moreHolder.tv_goods_content.setText(bean.getComboName());
        moreHolder.tv_money.setText(context.getString(R.string.label_money) + bean.getRefundAmount());
        moreHolder.tv_meal_name.setText(bean.getComboDescribe());

        moreHolder.iv_isShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> productListBeanList = new ArrayList<>();
                if (list.size() > 0) {
                    if (list.get(position).isIsexpress()) {
                        productListBeanList.clear();
                        list.get(position).setIsexpress(false);
                        moreHolder.iv_isShow.setImageResource(R.mipmap.group_14_1);
                    } else {
                        productListBeanList = list.get(position).getProductList();
                        list.get(position).setIsexpress(true);
                        moreHolder.iv_isShow.setImageResource(R.mipmap.group_12_3);
                    }
                }

                adapter = new SmallChooseReturnGoodsAdapter(productListBeanList, context);
                moreHolder.listView.setAdapter(adapter);

                adapter.setRefreshUI(productListBeanList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MoreHolder extends RecyclerView.ViewHolder {
        private ImageView iv_goods, iv_isShow;
        private TextView tv_goods_code, tv_goods_content,
                tv_money, tv_meal_name;
        private MyListView listView;


        public MoreHolder(@NonNull View itemView) {
            super(itemView);
            iv_goods = itemView.findViewById(R.id.iv_goods);
            iv_isShow = itemView.findViewById(R.id.iv_isShow);
            tv_goods_code = itemView.findViewById(R.id.tv_goods_code);
            tv_goods_content = itemView.findViewById(R.id.tv_goods_content);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_meal_name = itemView.findViewById(R.id.tv_meal_name);
            listView = itemView.findViewById(R.id.listView);


        }
    }


}