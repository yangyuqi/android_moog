package com.youzheng.zhejiang.robertmoog.utils.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.R;

public class RemindDialog extends Dialog {
    Context mcontext ;
    TextView tv_no ,tv_ok ;
    onSuccessClick click ;
    public RemindDialog(@NonNull Context context ,onSuccessClick click) {
        super(context ,R.style.DeleteDialogStyle);
        mcontext = context ;
        this.click = click ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view =inflater.inflate(R.layout.remind_dialog_layout, null);
        setContentView(view);
        tv_ok = view.findViewById(R.id.tv_ok);
        tv_no = view.findViewById(R.id.tv_no);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onSuccess();
                dismiss();
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mcontext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.74); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public interface onSuccessClick{
        void onSuccess();
    }
}
