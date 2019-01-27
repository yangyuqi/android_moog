package com.youzheng.zhejiang.robertmoog.Store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;

import java.util.List;

import xyz.zpayh.hdimage.HDImageView;

/**
 * 图片浏览器适配器
 */
public class ImageAdapter extends PagerAdapter {
   private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnRecyclerViewAdapterItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnRecyclerViewAdapterItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ImageAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {//必须实现
        View view =layoutInflater.inflate(R.layout.item_image,container,false);
        HDImageView imageView=view.findViewById(R.id.iv_image);
        if (TextUtils.isEmpty(list.get(position))){

        }else {
            imageView.setImageURI(list.get(position));
        }

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //自己获取position
             //   int position = goodsHolder.getLayoutPosition();
                //设置监听
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemLongClick(v,position);
                }
                //true代表消费事件 不继续传递
                return true;
            }
        });

       // Glide.with(context).load(list.get(position)).error(R.mipmap.type_icon).into(imageView);
        container.addView(view);
        return view;
    }

}
