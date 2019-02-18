package com.youzheng.zhejiang.robertmoog.utils.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.R;

public class MyHeader implements SpringView.DragHander  {
    private Context context;
    private ImageView imageView;

    public MyHeader(Context context) {
        this.context = context;
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.layout_head, viewGroup, true);
         imageView=view.findViewById(R.id.iv_head_anim);
         Glide.with(context).load(R.drawable.is_load).asGif().into(imageView);
        return view;
    }

    @Override
    public int getDragLimitHeight(View rootView) {
        return 0;
    }

    @Override
    public int getDragMaxHeight(View rootView) {
        return 0;
    }

    @Override
    public int getDragSpringHeight(View rootView) {
        return 0;
    }

    @Override
    public void onPreDrag(View rootView) {

    }

    @Override
    public void onDropAnim(View rootView, int dy) {

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {

    }

    @Override
    public void onStartAnim() {
        Glide.with(context).load(R.drawable.is_load).asGif().into(imageView);
    }

    @Override
    public void onFinishAnim() {
        Glide.with(context).load(R.drawable.is_load).asGif().into(imageView);
    }
}
