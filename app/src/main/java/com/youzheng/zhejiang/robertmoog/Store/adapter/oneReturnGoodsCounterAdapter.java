package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnAllCounterActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnGoodsCounterActivity;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.ConfirmReturnRequest;
import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsCounter;
import com.youzheng.zhejiang.robertmoog.Store.listener.GetData;

import java.util.ArrayList;
import java.util.List;

public class oneReturnGoodsCounterAdapter extends RecyclerView.Adapter<oneReturnGoodsCounterAdapter.OneHolder> {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list;
    private LayoutInflater layoutInflater;
    private Context context;
    public static int money_num=0;
    private List<ConfirmReturnRequest.ReshippedGoodsDataListBean> request=new ArrayList<>();
    private String money;
    private int  all;
    private List<Integer> money_all=new ArrayList<>();
    public static int totals=0;
    private List<EditText> textList=new ArrayList<>();
    private GetData listener;

    public oneReturnGoodsCounterAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list, Context context, GetData listener) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
        this.listener=listener;
    }

    public void setUI(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list){
        this.list = list;
        money_all.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.item_return_goods_counter,viewGroup,false);
        OneHolder oneHolder=new OneHolder(view);
        return oneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OneHolder oneHolder, int position) {
        final ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean bean=list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(oneHolder.iv_goods);
        oneHolder.tv_goods_code.setText(bean.getSku());
        oneHolder.tv_goods_content.setText(bean.getName());
        oneHolder.tv_money.setText(context.getString(R.string.label_money)+bean.getRefundAmount());
        oneHolder.tv_number.setText("X "+bean.getCount()+"");

        oneHolder.tv_goods_code.setText(bean.getSku());



        if (bean.isIsSpecial()==true){
            oneHolder.tv_area.setVisibility(View.VISIBLE);
            if (bean.getSquare()!=0){
                oneHolder.tv_area.setText(bean.getSquare()+"平方");
            }

        }else {
            oneHolder.tv_area.setVisibility(View.GONE);

        }
        int refunds= Integer.parseInt(bean.getRefundAmount());
        if (bean.getCount()!=0){
            oneHolder.et_money.setText(bean.getRefundAmount());
            bean.setMoney(refunds);
            oneHolder.tv_item_total.setText(bean.getRefundAmount());

        }else {
            oneHolder.et_money.setText("0");
            oneHolder.tv_item_total.setText("0");
            bean.setMoney(0);
        }




        oneHolder.et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())){
                    oneHolder.tv_item_total.setText("0");
                    bean.setMoney(0);
                }else {
                    money=s.toString();
                    int edit= Integer.parseInt(money);
                    bean.setMoney(edit);
                    int num= Integer.parseInt(bean.getRefundAmount());

                    if (edit>num){
                        Toast.makeText(context, "实退金额不能大于退货金额", Toast.LENGTH_SHORT).show();
                        oneHolder.tv_item_total.setText(num+"");
                        oneHolder.et_money.setText(num + "");
                        bean.setMoney(num);
//                       ReturnAllCounterActivity.more_total = num;
                    }else {
                        oneHolder.tv_item_total.setText(s.toString());

                        bean.setMoney(edit);
                    }
                    int all_should=0;
                    for (int i = 0; i < list.size(); i++) {
                        all_should = all_should+list.get(i).getMoney();
                        Log.e("111", all + "集合1======"+i);
                    }
                    //totals = all_should + totals;
                    Log.e("111", all_should + "计算总和");
                }

                listener.getoneTotal(list);
            }
        });


    }



    public List<EditText> getEdList(){

        return textList;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OneHolder  extends RecyclerView.ViewHolder{
        private ImageView iv_goods;
        private TextView tv_goods_code,tv_goods_content,
                tv_money,tv_number,tv_area,et_money,tv_item_total;


        public OneHolder(@NonNull View itemView) {
            super(itemView);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            tv_goods_code=itemView.findViewById(R.id.tv_goods_code);
            tv_goods_content=itemView.findViewById(R.id.tv_goods_content);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_area=itemView.findViewById(R.id.tv_area);
            et_money=itemView.findViewById(R.id.et_money);
            tv_item_total=itemView.findViewById(R.id.tv_item_total);




        }
    }


}
