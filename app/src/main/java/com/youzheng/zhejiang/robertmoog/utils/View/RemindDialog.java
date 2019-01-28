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
    TextView tv_no ,tv_ok ,confrim_dialog_tv_title ,confrim_dialog_tv_content;
    onSuccessClick click ;
    String type ; //1 提交订单 2.注册 3.退出 4.是否退出地址
    public RemindDialog(@NonNull Context context ,onSuccessClick click ,String type) {
        super(context ,R.style.mydialog);
        mcontext = context ;
        this.click = click ;
        this.type = type;
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
        confrim_dialog_tv_title = view.findViewById(R.id.confrim_dialog_tv_title);
        confrim_dialog_tv_content = view.findViewById(R.id.confrim_dialog_tv_content);
        if (type.equals("3")){
            confrim_dialog_tv_content.setText("确认退出当前账号");
        }
        if (type.equals("2")){
            confrim_dialog_tv_content.setText("用户还未注册，请确认是否注册？");
        }
        if (type.equals("4")){
            confrim_dialog_tv_content.setText("地址信息未保存，是否确认返回");
        }

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mcontext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public interface onSuccessClick{
        void onSuccess();
    }
}
