package com.youzheng.zhejiang.robertmoog.utils.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youzheng.zhejiang.robertmoog.R;

public class NoteInfoDialog extends Dialog implements View.OnClickListener {

    Context context ;
    View show ;
    String pic ;
    ImageView iv ;

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
        Glide.with(context).load(pic).error(R.mipmap.type_icon).into(iv);
    }

    @Override
    public void onClick(View v) {

    }
}
