package com.youzheng.zhejiang.robertmoog.utils.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.CheckPicActivity;
import com.youzheng.zhejiang.robertmoog.Store.utils.DonwloadSaveImg;

import xyz.zpayh.hdimage.HDImageView;

public class NoteInfoDialog extends Dialog implements View.OnClickListener, View.OnLongClickListener {

    Context context ;
    View show ;
    String pic ;
    HDImageView iv ;
    private LinearLayout inflate;
    private Dialog dialog;
    private TextView tv_save_photo,tv_cancel;

    public NoteInfoDialog(@NonNull Context context ,String pic) {
        super(context , R.style.DeleteDialogStyle);
        this.context = context ;
        this.pic = pic ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.note_info_dialog, null);
        setContentView(view);
        view.findViewById(R.id.rl_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        iv = view.findViewById(R.id.iv);
        iv.setImageURI(pic);
        iv.setOnLongClickListener(this);
       // Glide.with(context).load(pic).error(R.mipmap.type_icon).into(iv);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        showDialog(pic);
        return true;
    }

    private void showDialog(final String picUrl) {
        dialog = new Dialog(context, R.style.ActionDialogStyle);
        //填充对话框的布局
        inflate = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_save_photo, null);
        //初始化控件
        tv_save_photo = (TextView) inflate.findViewById(R.id.tv_save_photo);
        tv_save_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonwloadSaveImg.donwloadImg(context,picUrl);
                dialog.dismiss();
            }
        });
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        lp.y = 20;//设置Dialog距离底部的距离
////       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
}
