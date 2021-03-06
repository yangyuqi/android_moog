package com.youzheng.zhejiang.robertmoog.utils.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.AttentionGoodsActivity;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.Request;

public class InPutCodeDialog extends Dialog implements View.OnClickListener{

    Context mcontext ;
    private ScanDatasBean remark ;
    EditText confrim_dialog_tv_content ;
    TextView tv_no ,tv_ok ;
    String token ;
    SearchResultAdapter adapter ;
    ArrayList<ScanDatasBean> objects ;
    public InPutCodeDialog(Context context, ScanDatasBean remark ,SearchResultAdapter adapter ,ArrayList<ScanDatasBean> objects){
        super(context, R.style.DeleteDialogStyle);
        mcontext = context;
        if (remark!=null){
            this.remark = remark;
        }
        this.adapter = adapter ;
        this.objects = objects ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view =inflater.inflate(R.layout.input_dialog_layout, null);
        setContentView(view);
        confrim_dialog_tv_content = view.findViewById(R.id.confrim_dialog_tv_content);
        if (remark.getCodePU()!=null) {
            confrim_dialog_tv_content.setText(remark.getCodePU());
        }
        tv_ok = view.findViewById(R.id.tv_ok);
        tv_no = view.findViewById(R.id.tv_no);
        tv_no.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mcontext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.74); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        if (TextUtils.isEmpty(confrim_dialog_tv_content.getText().toString())){
            tv_no.setText("取消");
        }else {
            tv_no.setText("清除");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok :
                if (!confrim_dialog_tv_content.getText().toString().equals("")) {
                    remark.setCodePU(confrim_dialog_tv_content.getText().toString());
                    adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(objects);
                }
                dismiss();

                break;

            case R.id.tv_no :
                if (tv_no.getText().toString().equals("取消")) {
                    dismiss();
                }else {
                    remark.setCodePU("");
                    adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(objects);
                    dismiss();
                }

                break;
        }
    }

}

