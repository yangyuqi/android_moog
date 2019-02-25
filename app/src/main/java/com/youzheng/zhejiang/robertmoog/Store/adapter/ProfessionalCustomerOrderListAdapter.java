package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.StoreOrderlistDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.bean.NewOrderListBean;
import com.youzheng.zhejiang.robertmoog.Store.bean.OrderList;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 *sss
 */
public class ProfessionalCustomerOrderListAdapter extends RecyclerView.Adapter {
    private List<NewOrderListBean.OrderListBean> list;
    private List<String> piclist;
    private Context context;
    private LayoutInflater layoutInflater;
    public static final int TYPE_ONE_IMAGE = 0;
    public static final int TYPE_MORE_IMAGE = 1;
    private OnRecyclerViewAdapterItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnRecyclerViewAdapterItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public ProfessionalCustomerOrderListAdapter(List<NewOrderListBean.OrderListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setUI(List<NewOrderListBean.OrderListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        NewOrderListBean.OrderListBean orderList = list.get(position);
        if (orderList.getProductNum() == 1) {
            return TYPE_ONE_IMAGE;
        } else {
            return TYPE_MORE_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE_IMAGE) {
            View view = layoutInflater.inflate(R.layout.item_orderlist, parent,false);
            final OneImageHolder oneImageHolder=new OneImageHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = oneImageHolder.getLayoutPosition();
                    //设置监听
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view ,position );
                    }

                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //自己获取position
                    int position = oneImageHolder.getLayoutPosition();
                    //设置监听
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemLongClick( v,position);
                    }
                    //true代表消费事件 不继续传递
                    return true;
                }
            });
            return oneImageHolder;
        } else {
            View view = layoutInflater.inflate(R.layout.item_order_style, parent,false);
            final MoreImageHolder moreImageHolder=new MoreImageHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = moreImageHolder.getLayoutPosition();
                    //设置监听
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view ,position );
                    }

                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //自己获取position
                    int position = moreImageHolder.getLayoutPosition();
                    //设置监听
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemLongClick( v,position);
                    }
                    //true代表消费事件 不继续传递
                    return true;
                }
            });


            return moreImageHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneImageHolder) {
            setOneImageData((OneImageHolder) holder,position);
        } else {
            setMoreImageData((MoreImageHolder) holder,position);
        }
    }

    private void setMoreImageData(final MoreImageHolder holder, final int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRvListPic.setLayoutManager(linearLayoutManager);
        MoreGoodsAdapter moreGoodsAdapter=new MoreGoodsAdapter(piclist,context);
        holder.mRvListPic.setAdapter(moreGoodsAdapter);
        moreGoodsAdapter.notifyDataSetChanged();

        piclist=new ArrayList<>();
        if (list.size() != 0) {
            for (NewOrderListBean.OrderListBean.OrderItemInfosBean itemInfosBean : list.get(position).getOrderItemInfos()) {
                piclist.add(itemInfosBean.getPhoto());
            }
        }

        moreGoodsAdapter.setPic(piclist);
        NewOrderListBean.OrderListBean beans = list.get(position);
        holder.mTvDate.setText(beans.getCreateDate());
        holder.mTvOrderNum.setText(beans.getOrderCode());
        holder.mTvCount.setText("共" + beans.getProductNum() + "件商品");
        holder.mTvMoney.setText(context.getString(R.string.label_money)+beans.getPayAmount());
        holder.tv_people.setText(beans.getRecommender());
        if (piclist.size()<4&&piclist.size()!=0){
            holder.mRvListPic.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return holder.item.onTouchEvent(event);
                }
            });
        }
//        holder.mRvListPic.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return holder.item.onTouchEvent(event);
//            }
//        });
    }

    private void setOneImageData(OneImageHolder holder,int position) {
        NewOrderListBean.OrderListBean beans = list.get(position);
        holder.tv_date.setText(beans.getCreateDate());
        holder.tv_order_num.setText(beans.getOrderCode());
        holder.tv_count.setText("共" + beans.getProductNum() + "件商品");
        holder.tv_money.setText(context.getString(R.string.label_money)+beans.getPayAmount());
        holder.tv_people.setText(beans.getRecommender());

        if (list.size() != 0) {
            for (NewOrderListBean.OrderListBean.OrderItemInfosBean itemInfosBean : list.get(position).getOrderItemInfos()) {
                Glide.with(context).load(itemInfosBean.getPhoto()).error(R.mipmap.type_icon).into(holder.iv_goods);
                holder.tv_goods_number.setText(itemInfosBean.getCode());
                holder.tv_goods_content.setText(itemInfosBean.getName());
            }

        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    private class OneImageHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        TextView tv_order_num;
        ImageView iv_goods;
        TextView tv_goods_number;
        TextView tv_goods_content;
        TextView tv_count;
        TextView tv_money;
        TextView tv_people;

        public OneImageHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_order_num = itemView.findViewById(R.id.tv_order_num);
            iv_goods = itemView.findViewById(R.id.iv_goods);
            tv_goods_number = itemView.findViewById(R.id.tv_goods_number);
            tv_goods_content = itemView.findViewById(R.id.tv_goods_content);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_people= itemView.findViewById(R.id.tv_people);
        }
    }

    private class MoreImageHolder extends RecyclerView.ViewHolder {
        TextView mTvDate;
        TextView mTvOrderNum;
        RecyclerView mRvListPic;
        TextView mTvCount;
        TextView mTvMoney;
        LinearLayout item;
        TextView tv_people;
        public MoreImageHolder(View itemView) {
            super(itemView);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mTvOrderNum = itemView.findViewById(R.id.tv_order_num);
            mRvListPic = itemView.findViewById(R.id.rv_list_pic);
            mTvCount = itemView.findViewById(R.id.tv_count);
            mTvMoney = itemView.findViewById(R.id.tv_money);
            item= itemView.findViewById(R.id.item);
            tv_people=itemView.findViewById(R.id.tv_people);
        }
    }


}
