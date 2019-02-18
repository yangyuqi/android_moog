package com.youzheng.zhejiang.robertmoog.utils.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.Request;

public class AddPriceDialog extends Dialog implements View.OnClickListener{

    EditText confrim_dialog_tv_content ;
    Context mcontext ;
    private ScanDatasBean remark ;
    TextView tv_no ,tv_ok ;
    SearchResultAdapter adapter ;
    private ArrayList<ScanDatasBean> objects;
    public AddPriceDialog(@NonNull Context context , ScanDatasBean remark , SearchResultAdapter adapter ,ArrayList<ScanDatasBean> objects) {
        super(context, R.style.DeleteDialogStyle);
        this.remark = remark;
        this.mcontext = context ;
        this.adapter = adapter ;
        this.objects = objects;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view =inflater.inflate(R.layout.add_price_dialog, null);
        setContentView(view);
        confrim_dialog_tv_content = view.findViewById(R.id.confrim_dialog_tv_content);
        if (remark.getAddPrice()!=null) {
            confrim_dialog_tv_content.setText(remark.getAddPrice());
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok :
                if (!confrim_dialog_tv_content.getText().toString().equals("")) {
                    remark.setAddPrice(confrim_dialog_tv_content.getText().toString());
                    adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(objects);
                }
                dismiss();
                break;

            case R.id.tv_no :
                //dismiss();
                confrim_dialog_tv_content.setText("");
                break;
        }
    }
}
