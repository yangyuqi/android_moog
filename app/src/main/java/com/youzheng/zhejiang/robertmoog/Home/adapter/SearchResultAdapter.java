package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Model.Home.ProductListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.View.AddPriceDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialogInterface;
import com.youzheng.zhejiang.robertmoog.utils.View.InPutCodeDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.NoScrollListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM_TITLE = 1;
    private int ITEM_CONTENT = 2;
    private ArrayList<ScanDatasBean> objects;
    private Context context ;
    private int widWidth ;
    private String type = "";//1表示搜索  2.表示扫一扫  3.表示卖货  4.表示意向商品
    int count=1;

    public void setDate(ArrayList<ScanDatasBean> objects , Context context ,String type ,int widWidth) {
        this.objects = objects;
        this.context = context ;
        notifyDataSetChanged();
        this.type = type ;
        this.widWidth = widWidth ;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void clear(){
        objects.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        if (viewType == ITEM_TITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_goods_vh_item_search, parent, false);
            holder = new CommonGoodsViewHolder(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_goods_vh_ls_item_search, parent, false);
            holder = new CommonGoodsTypeViewHolder(view);
        } return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CommonGoodsViewHolder) {
            if (((CommonGoodsViewHolder) holder).edt_num.getTag() != null && ((CommonGoodsViewHolder) holder).edt_num.getTag() instanceof TextWatcher) {
                ((CommonGoodsViewHolder) holder).edt_num.removeTextChangedListener((TextWatcher) ((CommonGoodsViewHolder) holder).edt_num.getTag());
            }
            RelativeLayout view = ((CommonGoodsViewHolder) holder).rl_width;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = (int) PublicUtils.dip2px(PublicUtils.px2dip(widWidth));
            view.setLayoutParams(params);

            ((CommonGoodsViewHolder) holder).tv_name.setText(objects.get(position).getCode());
            Glide.with(context).load(objects.get(position).getPhoto()).error(R.mipmap.type_icon).into(((CommonGoodsViewHolder) holder).iv_icon);
            ((CommonGoodsViewHolder) holder).tv_desc.setText(objects.get(position).getName());
            ((CommonGoodsViewHolder) holder).tv_price.setText("¥"+objects.get(position).getPrice());

            if (type.equals("1")){

                Log.e("走到1里面","11111");
                ((CommonGoodsViewHolder) holder).tv_get_num.setVisibility(View.GONE);
                ((CommonGoodsViewHolder) holder).tv_confirm.setVisibility(View.VISIBLE);
                ((CommonGoodsViewHolder) holder).rl_width.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("data",objects.get(position));
                        ((BaseActivity)context).setResult(2,intent);
                        ((BaseActivity)context).finish();
                    }
                });

            }else if (type.equals("2")){
//                if (((CommonGoodsViewHolder) holder).edt_num.getTag() != null && ((CommonGoodsViewHolder) holder).edt_num.getTag() instanceof TextWatcher) {
//                    ((CommonGoodsViewHolder) holder).edt_num.removeTextChangedListener((TextWatcher) ((CommonGoodsViewHolder) holder).edt_num.getTag());
//                }
                Log.e("走到2里面","11111");
                ((CommonGoodsViewHolder) holder).tv_confirm.setVisibility(View.GONE);
                ((CommonGoodsViewHolder) holder).ll_num.setVisibility(View.VISIBLE);
                ((CommonGoodsViewHolder) holder).tv_get_num.setVisibility(View.GONE);

                ((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.GONE);



                   // Log.e("13123123",objects.get(position).getSquare()+""+objects.get(position).getSquare__suffix()+"11111111");
                if (objects.get(position).isSpecial()) {


                    BigDecimal b = new BigDecimal(objects.get(position).getMinNum());
                    //保留5位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
                    String str=  b.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
                    ((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.GONE);
                    Log.e("最小",objects.get(position).getMinNum()+"---------"+str);
                    //String minNum=objects.get(position).getMinNum();
                    if (count==1){
                        objects.get(position).setSquare(objects.get(position).getMinNum());
                        objects.get(position).setSquare_num(objects.get(position).getMinNum());
                    }

//                    if (objects.get(position).getSquare_num().equals("999.999")){
//                        ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
//                    }else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
//                        }
//                    }

                    ((CommonGoodsViewHolder) holder).tv_specail.setText(objects.get(position).getSquare() + objects.get(position).getSquare__suffix());
                    ((CommonGoodsViewHolder) holder).edt_num.setText(objects.get(position).getSquare_num());

//                    ((CommonGoodsViewHolder) holder).tv_specail.setText(""+objects.get(position).getSquare() + objects.get(position).getSquare__suffix());
//                    ((CommonGoodsViewHolder) holder).edt_num.setText(""+objects.get(position).getSquare_num());
                }else {
                    if (objects.get(position).getNum()==999){
                        ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                        }
                    }
                    ((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.GONE);
                    ((CommonGoodsViewHolder) holder).edt_num.setText(""+objects.get(position).getNum());
                }
                ((CommonGoodsViewHolder) holder).main_right_drawer_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog dialog = new DeleteDialog(context,"提示","是否删除此商品","确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                objects.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }else if (type.equals("3")){

                Log.e("走到3里面","11111");
                ((CommonGoodsViewHolder) holder).tv_confirm.setVisibility(View.GONE);
                ((CommonGoodsViewHolder) holder).tv_get_num.setVisibility(View.VISIBLE);
                if (objects.get(position).getNum()==999){
                    ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                    }
                }
                if (objects.get(position).isSpecial()){
                    ((CommonGoodsViewHolder) holder).ll_no_code.setVisibility(View.VISIBLE);
                    ((CommonGoodsViewHolder) holder).ll_has_code.setVisibility(View.VISIBLE);
                    ((CommonGoodsViewHolder) holder).ll_num.setVisibility(View.VISIBLE);
                    ((CommonGoodsViewHolder) holder).edt_num.setText(""+objects.get(position).getNum());
                    ((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.VISIBLE);
                    Log.e("13123123",objects.get(position).getSquare()+""+objects.get(position).getSquare__suffix()+"222222");

                    BigDecimal b = new BigDecimal(objects.get(position).getSquare());
                    //保留5位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
                    String str1=  b.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
                    ((CommonGoodsViewHolder) holder).tv_specail.setText(""+str1+objects.get(position).getSquare__suffix());
                    if (TextUtils.isEmpty(objects.get(position).getCodePU())){
                        ((CommonGoodsViewHolder) holder).tv_add_code.setVisibility(View.VISIBLE);
                        ((CommonGoodsViewHolder) holder).tv_pu_code.setVisibility(View.GONE);
                    }else {
                        ((CommonGoodsViewHolder) holder).tv_pu_code.setText(objects.get(position).getCodePU__suffix()+objects.get(position).getCodePU());
                        ((CommonGoodsViewHolder) holder).tv_pu_code.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
                        ((CommonGoodsViewHolder) holder).tv_pu_code.setVisibility(View.VISIBLE);
                        ((CommonGoodsViewHolder) holder).tv_add_code.setVisibility(View.GONE);
                    }

                    if (TextUtils.isEmpty(objects.get(position).getAddPrice())){
                        ((CommonGoodsViewHolder) holder).tv_add_price.setVisibility(View.VISIBLE);
                        ((CommonGoodsViewHolder) holder).tv_zengxiang.setVisibility(View.GONE);
                    }else {
                        ((CommonGoodsViewHolder) holder).tv_zengxiang.setText(objects.get(position).getAddPrice__suffix()+ objects.get(position).getAddPrice());
                        ((CommonGoodsViewHolder) holder).tv_zengxiang.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
                        //((CommonGoodsViewHolder) holder).tv_zengxiang.getPaint().setAntiAlias(true);//抗锯齿
                        ((CommonGoodsViewHolder) holder).tv_zengxiang.setVisibility(View.VISIBLE);
                        ((CommonGoodsViewHolder) holder).tv_add_price.setVisibility(View.GONE);
                    }

                    ((CommonGoodsViewHolder) holder).tv_zengxiang.setOnClickListener(new AddPriceListener(context,objects.get(position)));
                    ((CommonGoodsViewHolder) holder).tv_add_price.setOnClickListener(new AddPriceListener(context,objects.get(position)));
                    ((CommonGoodsViewHolder) holder).tv_pu_code.setOnClickListener(new InPutCodeListener(context, objects.get(position)));
                    ((CommonGoodsViewHolder) holder).tv_add_code.setOnClickListener(new InPutCodeListener(context,objects.get(position)));
                }else {
                    ((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.GONE);
                    ((CommonGoodsViewHolder) holder).ll_num.setVisibility(View.GONE);
                    ((CommonGoodsViewHolder) holder).tv_get_num.setText(context.getResources().getString(R.string.label_X)+objects.get(position).getNum());
                }
            }else if (type.equals("4")){
                ((CommonGoodsViewHolder) holder).ll_num.setVisibility(View.GONE);
                ((CommonGoodsViewHolder) holder).tv_confirm.setVisibility(View.GONE);
                ((CommonGoodsViewHolder) holder).main_right_drawer_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog dialog = new DeleteDialog(context,"提示","是否删除此商品","确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                objects.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            if (type.equals("1")||type.equals("3")){
                ((CommonGoodsViewHolder) holder).hsv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }

            if (objects.get(position).isSpecial()) {
                ((CommonGoodsViewHolder) holder).tv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (type.equals("2")) {
                            if (objects.get(position).isSpecial()){
                                if (objects.get(position).getSquare_num().equals("999.999")){
                                    Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                    toast.setText("不能大于999.999");
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    //Toast.makeText(context,"不能大于999",Toast.LENGTH_SHORT).show();
//                                    ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                                }else {
                                    float num= Float.parseFloat(((CommonGoodsViewHolder) holder).edt_num.getText().toString());
//                                    float sss= (float) (num*0.001);
                                    //BigDecimal b = new BigDecimal((sss + 0.001));
                                    BigDecimal b = new BigDecimal(num + 0.001);
                                    //保留5位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
                                    String str=  b.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
                                    Log.e("sssssss", str);
                                    objects.get(position).setSquare(str);
                                    objects.get(position).setSquare_num(str);
                                       count++;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                    }

                                }


                            }else {

                                if (objects.get(position).getNum()==999){
                                    Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                    toast.setText("不能大于999");
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }else {
                                    objects.get(position).setNum(objects.get(position).getNum() + 1);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                    }

                                }


                                notifyDataSetChanged();



                            }

                        } else if (type.equals("3")) {
                            if (objects.get(position).getNum()==999){
                                Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                toast.setText("不能大于999");
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                //Toast.makeText(context,"不能大于999",Toast.LENGTH_SHORT).show();
                                //((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                            }else {
                                objects.get(position).setNum(objects.get(position).getNum() + 1);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                }

                            }
//                            if (objects.get(position).getNum()==999){
//                                ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
//                            }else {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
//                                }
//                            }


                            EventBus.getDefault().post(objects);
                        }

                        notifyDataSetChanged();
                        // notifyItemChanged(position);

                    }
                });
            } else {
                ((CommonGoodsViewHolder) holder).tv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (objects.get(position).getNum()==999){
                            Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                            toast.setText("不能大于999");
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            // Toast.makeText(context,"不能大于999",Toast.LENGTH_SHORT).show();
                            //((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                        }else {
                            objects.get(position).setNum(objects.get(position).getNum() + 1);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                            }

                        }
                        if (objects.get(position).getNum()==999){
                            ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                        }else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                            }
                        }

                        notifyDataSetChanged();
                    }
                });


            }

            TextWatcher textWatcher=new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().equals("")){
                        if (objects.get(position).isSpecial()){
                            objects.get(position).setSquare_num(objects.get(position).getMinNum());
                            count++;
                            Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                            toast.setText("最小值不能小于"+objects.get(position).getMinNum());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }else {
                            objects.get(position).setNum(1);
                            Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                            toast.setText("最小值不能小于1");
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }


                        return;
                    }else {
                        if (objects.get(position).isSpecial()){
                            objects.get(position).setSquare_num("1");
                            count++;
                        }else {
                            objects.get(position).setNum(Integer.parseInt(s.toString()));
                        }

                    }

                    if (s.toString().equals("0")){
                        objects.get(position).setNum(1);
                        s.replace(0,s.length(),"1");
//
                    }

//

                    if (objects.get(position).isSpecial()){

                    }else {

                    }


                    if (objects.get(position).isSpecial()){

                        if (type.equals("2")) {

                            float num= Float.parseFloat(s.toString());
//                            float sss= (float) (num*0.001);
                            objects.get(position).setSquare(num+"");
                            objects.get(position).setSquare_num(num+"");
                            count++;
                            //((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.VISIBLE);
                            Log.e("1222",num+"------2222");
                           // Log.e("13123123",Integer.parseInt(s.toString())+"----"+Integer.parseInt(s.toString())*0.001+""+objects.get(position).getSquare__suffix()+"33333333");
                            ((CommonGoodsViewHolder) holder).tv_specail.setText(num+objects.get(position).getSquare__suffix());

                            if (num>1000){
                                objects.get(position).setSquare_num("999.999");
                                ((CommonGoodsViewHolder) holder).edt_num.setText("999.999");
                                ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                }
                                Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                toast.setText("不能大于999.999");
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                count++;
                                return;
                            }else if (Float.parseFloat(s.toString())<Float.parseFloat(objects.get(position).getMinNum())){
                                BigDecimal b = new BigDecimal(Float.parseFloat(objects.get(position).getMinNum()));
                                //保留3位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
                                String str=  b.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
                                ((CommonGoodsViewHolder) holder).edt_num.setText(objects.get(position).getMinNum());
                                objects.get(position).setSquare_num(str);

                                Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                toast.setText("最小值不能小于"+objects.get(position).getMinNum());
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                                count++;
                                return;
                            } else {
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                    }
                                }catch (Exception e){

                                }


                            }
                        }else if (type.equals("3")){
                            objects.get(position).setNum(Integer.parseInt(s.toString()));
                            EventBus.getDefault().post(objects);
                        }
                    }else {
                        ((CommonGoodsViewHolder) holder).tv_specail.setVisibility(View.GONE);


                        if (Integer.parseInt(s.toString())>999){
                            objects.get(position).setNum(999);
                            ((CommonGoodsViewHolder) holder).edt_num.setText("999");
                            ((CommonGoodsViewHolder) holder).edt_num.clearFocus();
                           // ((CommonGoodsViewHolder) holder).edt_num.setSelection(s.length());
                            ((CommonGoodsViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                            }
                            // Toast.makeText(context,"不能大于999",Toast.LENGTH_SHORT).show();
                            Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                            toast.setText("不能大于999");
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }else if (Integer.parseInt(s.toString())<=1){
                            ((CommonGoodsViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                        }
                        else {
                            objects.get(position).setNum(Integer.parseInt(s.toString()));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                            }
                        }
                    }
                }
            };

            ((CommonGoodsViewHolder) holder).edt_num.addTextChangedListener(textWatcher);
            ((CommonGoodsViewHolder) holder).edt_num.setTag(textWatcher);



            if (objects.get(position).isSpecial()) {
                ((CommonGoodsViewHolder) holder).tv_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (type.equals("2")) {
                            Log.e("shuliang",objects.get(position).getSquare_num()+"---square_num");
                            Log.e("shuliang",objects.get(position).getMinNum()+"---MinNum");
                            if (objects.get(position).getSquare_num().equals(objects.get(position).getMinNum())){
                                Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                toast.setText("最小不能小于"+objects.get(position).getMinNum());
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                //Toast.makeText(context, "最小不能小于1", Toast.LENGTH_SHORT).show();
                                  ((CommonGoodsViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                            }else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                                }
                                float num= Float.parseFloat(((CommonGoodsViewHolder) holder).edt_num.getText().toString());
//                                float sss= (float) (num*0.001);
                                BigDecimal b = new BigDecimal((num - 0.001));
                                //保留3位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
                                String str=  b.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
                                Log.e("sssssss", str);
                                objects.get(position).setSquare(str);
                                objects.get(position).setSquare_num(str);
                                count++;

                            }

//                            if (objects.get(position).getSquare_num().equals("")){
//                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
//                            }else {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
//
//                                }
//
//                            }


                        } else if (type.equals("3")) {
                            if (objects.get(position).getNum() == 1) {
                                //Toast.makeText(context,"最小不能小于1",Toast.LENGTH_SHORT).show();
                                Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                                toast.setText("最小不能小于1");
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else {
                                objects.get(position).setNum(objects.get(position).getNum() - 1);
                                EventBus.getDefault().post(objects);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                                }

                            }

                            if (objects.get(position).getNum() == 1) {
                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                                // Toast.makeText(context, "最小不能小于1", Toast.LENGTH_SHORT).show();
                            }else {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                    ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));

                                }
                            }


                        }
                        notifyDataSetChanged();

                    }
                });
            }else {
                ((CommonGoodsViewHolder) holder).tv_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (objects.get(position).getNum() == 1) {
                            Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                            toast.setText("最小不能小于1");
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            //  Toast.makeText(context,"最小不能小于1",Toast.LENGTH_SHORT).show();
                        }else {
                            objects.get(position).setNum(objects.get(position).getNum() - 1);
                            EventBus.getDefault().post(objects);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                            }

                        }

                        if (objects.get(position).getNum() == 1) {
                            ((CommonGoodsViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                            // Toast.makeText(context, "最小不能小于1", Toast.LENGTH_SHORT).show();
                        }else {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ((CommonGoodsViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                                ((CommonGoodsViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));

                            }
                        }

//
                        notifyDataSetChanged();
                    }
                });

            }

        }else if (holder instanceof CommonGoodsTypeViewHolder){

            if (((CommonGoodsTypeViewHolder) holder).edt_num.getTag() != null && ((CommonGoodsTypeViewHolder) holder).edt_num.getTag() instanceof TextWatcher) {
                ((CommonGoodsTypeViewHolder) holder).edt_num.removeTextChangedListener((TextWatcher) ((CommonGoodsTypeViewHolder) holder).edt_num.getTag());
            }
            LinearLayout view = ((CommonGoodsTypeViewHolder) holder).ll_width;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = (int) PublicUtils.dip2px(PublicUtils.px2dip(widWidth));
            view.setLayoutParams(params);

            if (objects.get(position).getNum()==999){
                ((CommonGoodsTypeViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((CommonGoodsTypeViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                }
            }
            ((CommonGoodsTypeViewHolder) holder).tv_name.setText(objects.get(position).getCode());
            ((CommonGoodsTypeViewHolder) holder).lin_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ProductListBean> productListBeanList = new ArrayList<>();
                    if (objects.get(position).getProductList().size()>0){
                        if (objects.get(position).isIsexpress()){
                            productListBeanList.clear();
                            objects.get(position).setIsexpress(false);
                            ((CommonGoodsTypeViewHolder) holder).iv_show.setImageResource(R.mipmap.group_14_1);
                        }else {
                            productListBeanList = objects.get(position).getProductList();
                            objects.get(position).setIsexpress(true);
                            ((CommonGoodsTypeViewHolder) holder).iv_show.setImageResource(R.mipmap.group_12_3);
                        }
                    }

                    CommonAdapter<ProductListBean> adapter = new CommonAdapter<ProductListBean>(context,productListBeanList,R.layout.common_goods_ls_search) {
                        @Override
                        public void convert(ViewHolder helper, ProductListBean item) {
                            helper.setText(R.id.tv_name,item.getSku());
                            helper.setText(R.id.tv_desc,item.getName());
                            helper.setText(R.id.tv_price,"¥"+item.getPrice());
                            if (type.equals("3")){
                                if (item.getCount()==0||item.getCount()==null){
                                    helper.getView(R.id.tv_count).setVisibility(View.GONE);
                                }else {
                                    helper.getView(R.id.tv_count).setVisibility(View.VISIBLE);
                                    helper.setText(R.id.tv_count,"X"+item.getCount());
                                }
                            }else {
                                helper.getView(R.id.tv_count).setVisibility(View.GONE);
                            }


                            Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.type_icon).into((ImageView) helper.getView(R.id.iv_icon));
                        }
                    };
                    ((CommonGoodsTypeViewHolder) holder).ls.setAdapter(adapter);
                }
            });

            if (type.equals("1")){
                if (objects.get(position).getActivityName()!=null){
                    ((CommonGoodsTypeViewHolder) holder).rl_activity.setVisibility(View.VISIBLE);
                    ((CommonGoodsTypeViewHolder) holder).tv_activity.setText(objects.get(position).getActivityName());
                }else {
                    ((CommonGoodsTypeViewHolder) holder).rl_activity.setVisibility(View.GONE);
                }
            }


            ((CommonGoodsTypeViewHolder) holder).tv_price.setText("¥"+objects.get(position).getPrice());
            ((CommonGoodsTypeViewHolder) holder).tv_desc.setText(objects.get(position).getName());
            Glide.with(context).load(objects.get(position).getPhoto()).error(R.mipmap.type_icon).into(((CommonGoodsTypeViewHolder) holder).iv_icon);
            ((CommonGoodsTypeViewHolder) holder).tv_golden.setText(objects.get(position).getComboDescribe());
            if (type.equals("1")){
                ((CommonGoodsTypeViewHolder) holder).tv_get_num.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).tv_confirm.setVisibility(View.VISIBLE);
                ((CommonGoodsTypeViewHolder) holder).rv_choose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("data",objects.get(position));
                        ((BaseActivity)context).setResult(2,intent);
                        ((BaseActivity)context).finish();
                    }
                });
            }else if (type.equals("2")){
                ((CommonGoodsTypeViewHolder) holder).tv_get_num.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).tv_confirm.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).ll_num.setVisibility(View.VISIBLE);
                ((CommonGoodsTypeViewHolder) holder).edt_num.setText(""+objects.get(position).getNum());
                ((CommonGoodsTypeViewHolder) holder).main_right_drawer_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog dialog = new DeleteDialog(context,"提示","是否删除此商品","确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                objects.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }else if (type.equals("3")){

                ((CommonGoodsTypeViewHolder) holder).tv_get_num.setVisibility(View.VISIBLE);
                ((CommonGoodsTypeViewHolder) holder).tv_confirm.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).ll_num.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).tv_get_num.setText(context.getResources().getString(R.string.label_X)+objects.get(position).getNum());
            }else if(type.equals("4")){

                ((CommonGoodsTypeViewHolder) holder).ll_num.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).tv_confirm.setVisibility(View.GONE);
                ((CommonGoodsTypeViewHolder) holder).main_right_drawer_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog dialog = new DeleteDialog(context,"提示","是否删除此商品","确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                objects.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            if (type.equals("1")||type.equals("3")){
                ((CommonGoodsTypeViewHolder) holder).hsv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }

            ((CommonGoodsTypeViewHolder) holder).tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (objects.get(position).getNum()==999){
                        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                        toast.setText("最大不能超过999");
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        //Toast.makeText(context,"最大不能超过999",Toast.LENGTH_SHORT).show();
                        // ((CommonGoodsTypeViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                    }else {
                        objects.get(position).setNum(objects.get(position).getNum()+1) ;


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                        }
                    }

//                    if (objects.get(position).getNum()==999){
//                        ((CommonGoodsTypeViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
//                    }else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            ((CommonGoodsTypeViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
//                        }
//
//                    }


                    notifyDataSetChanged();
                }
            });

            ((CommonGoodsTypeViewHolder) holder).tv_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (objects.get(position).getNum()==1){
                        // ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                        //Toast.makeText(context,"最小不能小于1",Toast.LENGTH_SHORT).show();
                        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                        toast.setText("最小不能小于1");
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else {
                        objects.get(position).setNum(objects.get(position).getNum()-1) ;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((CommonGoodsTypeViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));

                        }
                    }
//                    if (objects.get(position).getNum()==1){
//                        ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
//                        //Toast.makeText(context,"最小不能小于1",Toast.LENGTH_SHORT).show();
//                    }else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
//                            //((CommonGoodsTypeViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
//
//                        }
//                    }



                    notifyDataSetChanged();
//                    if (objects.get(position).getNum()>1){
//
//
//                     }else {
//                        if (objects.get(position).getNum()==1){
//                            ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
//                            Toast.makeText(context,"最小不能小于1",Toast.LENGTH_SHORT).show();
//                        }else {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
//                                ((CommonGoodsTypeViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
//
//                            }
//                        }
//                    }
                }
            });

            TextWatcher watcher=new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().equals("")){
                        objects.get(position).setNum(1);
//                        ((CommonGoodsTypeViewHolder) holder).edt_num.setText("1");
                        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                        toast.setText("最小不能小于1");
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }else {
                        objects.get(position).setNum(Integer.parseInt(s.toString()));
                    }

                    if (s.toString().equals("0")){
                        objects.get(position).setNum(1);
                        s.replace(0,s.length(),"1");
//                        DeleteDialog dialog = new DeleteDialog(context,"提示","是否删除此商品","确定");
//                        dialog.show();
//                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
//                            @Override
//                            public void isDelete(boolean isdelete) {
//                                objects.remove(position);
//                                notifyDataSetChanged();
//                            }
//                        });
                    }
//                    if (Integer.parseInt(s.toString().trim())<1){
//                        objects.get(position).setNum(0);
////                        ((CommonGoodsTypeViewHolder) holder).edt_num.setText("1");
////                        Toast.makeText(context,"最小值不能小于1",Toast.LENGTH_SHORT).show();
//                        return;
//                    }



                    if (Integer.parseInt(s.toString())>999){
                        objects.get(position).setNum(999);
                        ((CommonGoodsTypeViewHolder) holder).edt_num.setText("999");
                        //((CommonGoodsTypeViewHolder) holder).edt_num.setText("999");
                        ((CommonGoodsTypeViewHolder) holder).edt_num.clearFocus();
                        //((CommonGoodsTypeViewHolder) holder).edt_num.setSelection(s.length());
                        ((CommonGoodsTypeViewHolder) holder).tv_add.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                        }

                        Toast toast=  Toast.makeText(context, null, Toast.LENGTH_SHORT);
                        toast.setText("不能大于999");
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }else if (Integer.parseInt(s.toString())<=1){
                        ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackgroundColor(context.getResources().getColor(R.color.text_drak_gray));

                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((CommonGoodsTypeViewHolder) holder).tv_add.setBackground(context.getDrawable(R.drawable.bg_order));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((CommonGoodsTypeViewHolder) holder).tv_reduce.setBackground(context.getDrawable(R.drawable.bg_order));
                        }
                    }
                    if (s.toString().startsWith("0")){
                        ((CommonGoodsTypeViewHolder) holder).edt_num.setText(s.toString().substring(1));
                        return;
                    }
                    try {
                        objects.get(position).setNum(Integer.parseInt(s.toString()));
                    }catch (Exception e){}
                }

            };
            ((CommonGoodsTypeViewHolder) holder).edt_num.addTextChangedListener(watcher);
            ((CommonGoodsTypeViewHolder) holder).edt_num.setTag(watcher);


        }
    }

    @Override
    public int getItemCount() {
        return objects == null ? 0 : objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.size() == 0) {
//            return EMPTY_VIEW;
        } else if (!objects.get(position).isSetMeal()) {
            return ITEM_TITLE;
        } else if (objects.get(position).isSetMeal()) {
            return ITEM_CONTENT;
        } else {
            return super.getItemViewType(position);

        }
        return super.getItemViewType(position);
    }


    public class CommonGoodsViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name ,tv_desc ,tv_price ,tv_confirm ,tv_get_num ,tv_add ,tv_reduce ,tv_specail
                ,tv_pu_code ,tv_zengxiang ,tv_add_code ,tv_add_price ;
        ImageView iv_icon ;
        RelativeLayout rl_width ,main_right_drawer_layout;
        HorizontalScrollView hsv;
        View ll_num , ll_no_code ,ll_has_code ;
        EditText edt_num ;
        public CommonGoodsViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
            rl_width = itemView.findViewById(R.id.rl_width);
            hsv = itemView.findViewById(R.id.hsv);
            ll_num = itemView.findViewById(R.id.ll_num);
            tv_get_num = itemView.findViewById(R.id.tv_get_num);
            edt_num = itemView.findViewById(R.id.edt_num);
            tv_reduce = itemView.findViewById(R.id.tv_reduce);
            tv_add = itemView.findViewById(R.id.tv_add);
            tv_specail = itemView.findViewById(R.id.tv_specail);
            tv_pu_code = itemView.findViewById(R.id.tv_pu_code);
            tv_zengxiang = itemView.findViewById(R.id.tv_zengxiang);
            tv_add_code = itemView.findViewById(R.id.tv_add_code);
            tv_add_price = itemView.findViewById(R.id.tv_add_price);
            ll_no_code = itemView.findViewById(R.id.ll_no_code);
            ll_has_code = itemView.findViewById(R.id.ll_has_code);
            main_right_drawer_layout = itemView.findViewById(R.id.main_right_drawer_layout);
        }
    }

    public class CommonGoodsTypeViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name ,tv_confirm , tv_desc ,tv_price ,tv_golden ,tv_activity ,tv_get_num ,tv_add ,tv_reduce,tv_count;
        NoScrollListView ls ;
        View rl_activity ,ll_num ,main_right_drawer_layout;
        ImageView iv_show ,iv_icon ;
        LinearLayout ll_width ,lin_on;
        HorizontalScrollView hsv ;
        EditText edt_num ;
        RelativeLayout rv_show,rv_choose;
        public CommonGoodsTypeViewHolder(View itemView) {
            super(itemView);
            tv_count=itemView.findViewById(R.id.tv_count);
            tv_name = itemView.findViewById(R.id.tv_name);
            ls = itemView.findViewById(R.id.no_ls);
            iv_show = itemView.findViewById(R.id.iv_show);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_golden = itemView.findViewById(R.id.tv_golden);
            rl_activity = itemView.findViewById(R.id.rl_activity);
            tv_activity = itemView.findViewById(R.id.tv_activity);
            ll_width = itemView.findViewById(R.id.ll_width);
            hsv = itemView.findViewById(R.id.hsv);
            ll_num = itemView.findViewById(R.id.ll_num);
            tv_get_num = itemView.findViewById(R.id.tv_get_num);
            edt_num = itemView.findViewById(R.id.edt_num);
            tv_reduce = itemView.findViewById(R.id.tv_reduce);
            tv_add = itemView.findViewById(R.id.tv_add);
            main_right_drawer_layout = itemView.findViewById(R.id.main_right_drawer_layout);
            rv_show=itemView.findViewById(R.id.rv_show);
            rv_choose=itemView.findViewById(R.id.rv_choose);
            lin_on=itemView.findViewById(R.id.lin_on);
        }
    }

    public class AddPriceListener implements View.OnClickListener{

        ScanDatasBean bean ;
        Context context ;
        AddPriceDialog priceDialog ;
        public AddPriceListener(Context context ,ScanDatasBean bean){
            this.bean = bean ;
            this.context = context;
            priceDialog = new AddPriceDialog(context,bean ,SearchResultAdapter.this ,objects);
        }

        @Override
        public void onClick(View v) {
            priceDialog.show();
        }
    }

    public class InPutCodeListener implements View.OnClickListener{

        ScanDatasBean bean ;
        Context context ;
        InPutCodeDialog priceDialog ;
        public InPutCodeListener(Context context ,ScanDatasBean bean){
            this.bean = bean ;
            this.context = context;
            priceDialog = new InPutCodeDialog(context,bean ,SearchResultAdapter.this ,objects);
        }

        @Override
        public void onClick(View v) {
            priceDialog.show();
        }
    }

}
