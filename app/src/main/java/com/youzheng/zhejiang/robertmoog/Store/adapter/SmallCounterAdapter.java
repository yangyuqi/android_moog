package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnAllCounterActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnGoodsCounterActivity;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.listener.GetData;

import java.util.ArrayList;
import java.util.List;

/**
 *sss
 */
public class SmallCounterAdapter extends RecyclerView.Adapter<SmallCounterAdapter.ViewHolder> {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<TextView> textViews;
    private String money;
    public static int  all;
    private List<Integer> money_all=new ArrayList<>();
    public static int totals=0;
    private List<EditText> textList=new ArrayList<>();
    private int refund;
    private GetData listener ;
    public static boolean ismoren=true;

    public SmallCounterAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list, Context context,GetData listener) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.listener=listener;

    }

    public void setRefreshUI(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list) {
        this.list = list;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view=layoutInflater.inflate(R.layout.item_return_goods_counter,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(viewHolder.iv_goods);
        viewHolder.tv_goods_code.setText(bean.getSku());
        viewHolder.tv_goods_content.setText(bean.getName());
        viewHolder.tv_money.setText(context.getString(R.string.label_money) + bean.getRefundAmount());
        viewHolder.tv_number.setText("X "+bean.getCount()+"");
//
        Log.e("111",refund+"呵呵");
        ismoren=true;
       int refunds= Integer.parseInt(bean.getRefundAmount());
        if (bean.getCount()!=0){
            viewHolder.et_money.setText(bean.getRefundAmount());
            bean.setMoney(refunds);
            viewHolder.tv_item_total.setText(bean.getRefundAmount());
        }else {
            viewHolder.et_money.setText("0");
            viewHolder.tv_item_total.setText("0");
            bean.setMoney(0);
            viewHolder.lin_ed.setVisibility(View.GONE);
        }


        viewHolder.et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

               if (s.length()==0){
                   //viewHolder.tv_item_total.setText("0");
                   bean.setMoney(0);
                   //viewHolder.et_money.setText("0");
               }else {
//                   viewHolder.et_money.setText("");
                    money=s.toString();
                    int edit= Integer.parseInt(money);
                    bean.setMoney(edit);
                    int num= Integer.parseInt(bean.getRefundAmount());

                   if (edit>num){
                       Toast toast=  Toast.makeText(context, null, Toast.LENGTH_SHORT);
                       toast.setText("实退金额不能大于退货金额");
                       toast.setGravity(Gravity.CENTER, 0, 0);
                       toast.show();
                     //  Toast.makeText(context, "实退金额不能大于退货金额", Toast.LENGTH_SHORT).show();
                     //  viewHolder.tv_item_total.setText(num+"");
                       viewHolder.et_money.setText(num + "");
                       bean.setMoney(num);
//                       ReturnAllCounterActivity.more_total = num;
                   }else {
                      // viewHolder.tv_item_total.setText(s.toString());

                       bean.setMoney(edit);
//                       ReturnAllCounterActivity.more_total = edit;
//                       money_all.add(num);
                   }
                   int all_should=0;
                   for (int i = 0; i < list.size(); i++) {
                           all_should = all_should+list.get(i).getMoney();
                           Log.e("111", all + "集合1======"+i);
                   }
                   //totals = all_should + totals;
                   Log.e("111", all_should + "计算总和");
                     ismoren=false;


               }

                listener.getMoreTotal(list);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



   public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods_code,tv_goods_content,
                tv_money,tv_number,tv_area,et_money,tv_item_total;
        private LinearLayout lin_ed;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           iv_goods=itemView.findViewById(R.id.iv_goods);
           tv_goods_code=itemView.findViewById(R.id.tv_goods_code);
           tv_goods_content=itemView.findViewById(R.id.tv_goods_content);
           tv_money=itemView.findViewById(R.id.tv_money);
           tv_number=itemView.findViewById(R.id.tv_number);
           tv_area=itemView.findViewById(R.id.tv_area);
            et_money=itemView.findViewById(R.id.et_money);
           tv_item_total=itemView.findViewById(R.id.tv_item_total);
           lin_ed=itemView.findViewById(R.id.lin_ed);
       }
   }
}
