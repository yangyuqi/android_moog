package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnAllCounterActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods.ReturnGoodsCounterActivity;
import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;

import java.util.ArrayList;
import java.util.List;

/**
 *sss
 */
public class SmallCounterAdapter extends BaseAdapter {
    private List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<TextView> textViews;
    private String money;
    private int  all;
    private List<Integer> money_all=new ArrayList<>();
    public static int totals=0;
    private List<EditText> textList=new ArrayList<>();

    public SmallCounterAdapter(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        textViews=new ArrayList<>();
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
            convertView = layoutInflater.inflate(R.layout.item_return_goods_counter, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_goods=convertView.findViewById(R.id.iv_goods);
            viewHolder.tv_goods_code=convertView.findViewById(R.id.tv_goods_code);
            viewHolder.tv_goods_content=convertView.findViewById(R.id.tv_goods_content);
            viewHolder.tv_money=convertView.findViewById(R.id.tv_money);
            viewHolder. tv_number=convertView.findViewById(R.id.tv_number);
            viewHolder.tv_area=convertView.findViewById(R.id.tv_area);
            viewHolder. et_money=convertView.findViewById(R.id.et_money);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX bean = list.get(position);
        Glide.with(context).load(bean.getPhoto()).error(R.mipmap.type_icon).into(viewHolder.iv_goods);
        viewHolder.tv_goods_code.setText(bean.getSku());
        viewHolder.tv_goods_content.setText(bean.getName());
        viewHolder.tv_money.setText(context.getString(R.string.label_money) + bean.getRefundAmount());
        viewHolder.tv_number.setText("X "+bean.getCount()+"");

        viewHolder.et_money.setTag(position);
        textList.add((EditText) viewHolder.et_money);
        viewHolder.et_money.setText(bean.getRefundAmount());
        money_all.clear();
        totals=0;
        for (EditText editText:textList){
            String mo=editText.getText().toString().trim();
            if (!TextUtils.isEmpty(mo)){
                int num_mon= Integer.parseInt(mo);
                int count=bean.getCount();
                int alls=num_mon*count;
                Log.e("111",alls+"总和");

                money_all.add(alls);

                for (int i = 0; i <money_all.size() ; i++) {
                    all=money_all.get(i);
                    Log.e("111",all+"集合");

                }
                totals=all+totals;
                Log.e("111",totals+"计算总和");
                ReturnAllCounterActivity.more_total=totals;
                int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                ReturnAllCounterActivity.tv_really_cut_money.setText(all+"");
            }else {
                viewHolder.et_money.setText("");
            }



        }


        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.et_money.addTextChangedListener(new TextWatcher() {
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
                bean.setMoney(0);
                money_all.clear();
                totals=0;
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
                            finalViewHolder.et_money.setText(refund+"");
                            ReturnAllCounterActivity.more_total=refund;
                            int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                            ReturnAllCounterActivity.tv_really_cut_money.setText(all+"");
                        }else {
                            int count=bean.getCount();
                            int alls=num_mon*count;

                            Log.e("111",alls+"总和");

                            money_all.add(alls);

                            for (int i = 0; i <money_all.size() ; i++) {
                                all=money_all.get(i);
                                Log.e("111",all+"集合");

                            }
                            totals=all+totals;
                            Log.e("111",totals+"计算总和");
                            ReturnAllCounterActivity.more_total=totals;
                            int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                            ReturnAllCounterActivity.tv_really_cut_money.setText(all+"");
                        }

                    }else {
                        money_all.clear();
                        totals=0;
                        all=0;
                        ReturnAllCounterActivity.more_total=0;
                        bean.setMoney(0);
                       // int all=ReturnAllCounterActivity.one_list_total+ReturnAllCounterActivity.more_total;
                        ReturnAllCounterActivity.tv_really_cut_money.setText("0");
                    }
                }


                //String money=oneHolder.et_money.getText().toString().trim();


            }
        });

        return convertView;
    }



    class ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods_code,tv_goods_content,
                tv_money,tv_number,tv_area,et_money;

    }
}
