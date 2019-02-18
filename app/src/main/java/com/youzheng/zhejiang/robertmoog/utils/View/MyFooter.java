package com.youzheng.zhejiang.robertmoog.utils.View;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.youzheng.zhejiang.robertmoog.R;

public class MyFooter implements SpringView.DragHander {
    private Context context;
    private ImageView imageView;
    AnimationDrawable animationDrawable;

    public MyFooter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.layout_footer, viewGroup, true);
        imageView=view.findViewById(R.id.iv_footer_anim);
        imageView.setImageResource(R.drawable.anim_footer);
         animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
       // Glide.with(context).load(R.drawable.anim_footer).asGif().into(imageView);
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
       // animationDrawable.stop();
    }

    @Override
    public void onDropAnim(View rootView, int dy) {

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        //animationDrawable.stop();
    }

    @Override
    public void onStartAnim() {

    }

    @Override
    public void onFinishAnim() {

       // Glide.with(context).load(R.drawable.anim_footer).asGif().into(imageView);
    }
}
