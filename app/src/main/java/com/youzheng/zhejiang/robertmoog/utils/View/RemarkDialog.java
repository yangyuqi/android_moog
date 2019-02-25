package com.youzheng.zhejiang.robertmoog.utils.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.AttentionGoodsActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.Request;


public class RemarkDialog extends Dialog implements View.OnClickListener{

    Context mcontext ;
    private String id , remark ;
    EditText confrim_dialog_tv_content ;
    TextView tv_no ,tv_ok ;
    String token ;
    boolean have_customerid;
    public RemarkDialog(Context context, String id, String remark,boolean have_customerid){
        super(context, R.style.mydialog);
        mcontext = context;
       this.have_customerid=have_customerid;
        token = (String) SharedPreferencesUtils.getParam(context, PublicUtils.access_token,"");
        if (id!=null){
            this.id = id ;
        }
        if (remark!=null){
            this.remark = remark;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view =inflater.inflate(R.layout.remark_dialog, null);
        setContentView(view);
        confrim_dialog_tv_content = view.findViewById(R.id.confrim_dialog_tv_content);
        confrim_dialog_tv_content.setText(remark);
        tv_ok = view.findViewById(R.id.tv_ok);
        tv_no = view.findViewById(R.id.tv_no);
        tv_no.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mcontext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok :
                if (id==null){
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                if (have_customerid==true){
                    map.put("id",id);
                }else {
                    map.put("customerId",id);
                }

                map.put("remark",confrim_dialog_tv_content.getText().toString());
                OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.UPDATE_INTENT_REMARK + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseModel baseModel = new Gson().fromJson(response,BaseModel.class);
                        if (baseModel.getCode()==PublicUtils.code){

                                EventBus.getDefault().post("refresh");

                            dismiss();
                        }else {
                            if (!TextUtils.isEmpty(baseModel.getMsg())){
                                Toast.makeText(mcontext,baseModel.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;

            case R.id.tv_no :
                dismiss();
                break;
        }
    }
}
