package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Model.Home.EnumsDatasBeanDatas;
import com.youzheng.zhejiang.robertmoog.R;

import java.util.List;

/**
 *ss
 */
public class ReturnGoodsTimeAdapter extends BaseAdapter {
    private List<EnumsDatasBeanDatas> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private int selectItem =0;

    public ReturnGoodsTimeAdapter(List<EnumsDatasBeanDatas> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    public void setUI(List<EnumsDatasBeanDatas> list){
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


    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.item_time,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_time=convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_time.setText(list.get(position).getDes());

        if (selectItem==position){
            viewHolder.tv_time.setSelected(true);
            viewHolder.tv_time.setBackgroundResource(R.drawable.time_on);
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else {
            viewHolder.tv_time.setSelected(false);
            viewHolder.tv_time.setBackgroundResource(R.drawable.time_off);
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.text_main));
        }

        return convertView;
    }
    class ViewHolder{
       private TextView tv_time;
    }
}
