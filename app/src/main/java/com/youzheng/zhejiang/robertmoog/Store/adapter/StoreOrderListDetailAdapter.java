package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class StoreOrderListDetailAdapter extends RecyclerView.Adapter<StoreOrderListDetailAdapter.ViewHolde> {
     private List<String> list=new ArrayList<>();
     private LayoutInflater layoutInflater;
     private Context context;

    public StoreOrderListDetailAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde viewHolde, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolde extends RecyclerView.ViewHolder{

        public ViewHolde(@NonNull View itemView) {
            super(itemView);
        }
    }


}
