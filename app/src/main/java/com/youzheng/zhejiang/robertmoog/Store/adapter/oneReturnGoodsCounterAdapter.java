package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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

    public oneReturnGoodsCounterAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
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
        //ReturnGoodsCounterActivity.tv_really_cut_money.setText("");

//        bean.setMoney(bean.getMoney());
        oneHolder.et_money.setTag(position);
        textList.add((EditText) oneHolder.et_money);
        oneHolder.et_money.setText(bean.getRefundAmount());
        money_all.clear();
        totals=0;
        for (EditText editText:textList){
            String mo=editText.getText().toString().trim();
            int num_mon= Integer.parseInt(mo);
            int count=bean.getCount();
            int alls=num_mon*count;
            bean.setMoney(alls);

            Log.e("111",alls+"总和");

            money_all.add(alls);

            for (int i = 0; i <money_all.size() ; i++) {
                all=money_all.get(i);
                Log.e("111",all+"集合");

            }
            totals=all+totals;
            Log.e("111",totals+"计算总和");
            ReturnAllCounterActivity.one_list_total=totals;
            int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
            ReturnAllCounterActivity.tv_really_cut_money.setText(all+"");
        }




        oneHolder.et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                  if (money.length()==0){
//                      money_all.clear();
//                      totals=0;
//                      all=0;
//                      ReturnGoodsCounterActivity.tv_really_cut_money.setText("");
//                  }


//                   for (ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean productListBean:list){
//                       money_num+=productListBean.getMoney()*productListBean.getCount();
//                   }


              //  notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

                money_all.clear();
                totals=0;
                bean.setMoney(0);
                //money=oneHolder.et_money.getText().toString().trim();
                Log.e("111",money+"内容");

                     for (EditText text:textList){
                         money=text.getText().toString().trim();
                         if (money.length()!=0){
                             // int pos= (int) text.getTag();

                             int num_mon= Integer.parseInt(money);

                             int refund= Integer.parseInt(bean.getRefundAmount());

                             if (num_mon>refund){
                                 Toast.makeText(context,"实退金额不能大于退货金额",Toast.LENGTH_SHORT).show();
                                 oneHolder.et_money.setText(refund+"");
                                 ReturnAllCounterActivity.one_list_total=refund;
                                 bean.setMoney(refund);
                                 int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                                 ReturnAllCounterActivity.tv_really_cut_money.setText(all+"");

                             }else {
                                 int count=bean.getCount();
                                 int alls=num_mon*count;
                                 bean.setMoney(alls);
                                 Log.e("111",alls+"总和");

                                 money_all.add(alls);

                                 for (int i = 0; i <money_all.size() ; i++) {
                                     all=money_all.get(i);
                                     Log.e("111",all+"集合");

                                 }
                                 totals=all+totals;
                                 Log.e("111",totals+"计算总和");
                                 ReturnAllCounterActivity.one_list_total=totals;
                                 int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                                 ReturnAllCounterActivity.tv_really_cut_money.setText(all+"");

                             }

                         }else {
                             money_all.clear();
                             totals=0;
                             all=0;
                             bean.setMoney(0);
                             ReturnAllCounterActivity.one_list_total=0;
                            // int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                             ReturnAllCounterActivity.tv_really_cut_money.setText("");
                         }
                     }


                    //String money=oneHolder.et_money.getText().toString().trim();


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
                tv_money,tv_number,tv_area,et_money;


        public OneHolder(@NonNull View itemView) {
            super(itemView);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            tv_goods_code=itemView.findViewById(R.id.tv_goods_code);
            tv_goods_content=itemView.findViewById(R.id.tv_goods_content);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_area=itemView.findViewById(R.id.tv_area);
            et_money=itemView.findViewById(R.id.et_money);



        }
    }


}
