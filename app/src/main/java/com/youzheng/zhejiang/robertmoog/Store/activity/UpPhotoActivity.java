package com.youzheng.zhejiang.robertmoog.Store.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.bean.ShopSale;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.AddphotoAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.SampleOutPic;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.AndroidScheduler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 上传图片界面sss
 */
public class UpPhotoActivity extends BaseCameraActivity implements View.OnClickListener, OnRecyclerViewAdapterItemClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private GridView gv_photo;
    private List<String> list = new ArrayList<>();
    private AddphotoAdapter adapter;
    private String path;
    private LinearLayout inflate;
    private Dialog dialog;
    /**
     * 拍摄
     */
    private TextView tv_take_photo;
    /**
     * 从手机相册选择
     */
    private TextView tv_find_from;
    /**
     * 取消
     */
    private TextView tv_cancel;
    private List<String> addlist = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private String response;
    private LinearLayout lin_show;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.getCode() == PublicUtils.code) {
                        showToast("图片上传成功");
                        finish();
                    }

                    break;

                case 2:
                    showToast("图片上传失败");
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_photo);
        initView();
    }


    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        textHeadNext.setOnClickListener(this);

        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        gv_photo = (GridView) findViewById(R.id.gv_photo);
        textHeadNext.setOnClickListener(this);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.bg_background_white));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        layout_header.setBackgroundColor(getResources().getColor(R.color.bg_background_white));

        textHeadNext.setText("发表");
        textHeadNext.setTextColor(getResources().getColor(R.color.colorPrimary));

        btnBack.setImageResource(R.mipmap.group_126_9);

        path = getIntent().getStringExtra("picturePath");
        list.add(path);
        Log.e("ppp", path + "");
        adapter = new AddphotoAdapter(list, this);
        gv_photo.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);
        lin_show = findViewById(R.id.lin_show);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LuBan(path);
            }
        }).start();

        Log.e("集合路径", path);


    }

    @Override
    protected void setHeadIvEvenSendMine(Bitmap bm, final String picturePath) {
        super.setHeadIvEvenSendMine(bm, picturePath);
        list.add(picturePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LuBan(picturePath);
            }
        }).start();
        Log.e("www", picturePath + "");
        Log.e("2131", list.size() + "");

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                list.clear();
                fileList.clear();
                finish();
                break;
            case R.id.textHeadNext:
                showStopDialog();
                break;
            case R.id.tv_take_photo:
                skipCamera();
                dialog.dismiss();
                break;
            case R.id.tv_find_from:
                skipPhoto();
                dialog.dismiss();
                break;
            case R.id.tv_cancel:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
        }
    }


    private void LuBan(String picpath) {
        Luban.with(this)
                .load(picpath)                                   // 传人要压缩的图片列表
                .ignoreBy(500)                                  // 忽略不压缩图片的大小
//                .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                        lin_show.setVisibility(View.VISIBLE);
                        Log.e("213", "正在压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.e("213", "压缩完成");
//                        lin_show.setVisibility(View.GONE);
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        fileList.add(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();    //启动压缩

    }

    private void upPic(List<File> pic) {
        lin_show.setVisibility(View.VISIBLE);
        OkHttpClientManager.getInstance().sendMultipart(UrlUtils.UPLOAD_FILE + "?access_token=" + access_token, new HashMap<String, Object>(), "posters", pic)
                .observeOn(AndroidScheduler.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        lin_show.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("图片上传失败", throwable.getMessage());
//                        showToast("图片上传失败");
                        lin_show.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("图片上传成功", s);
                        lin_show.setVisibility(View.GONE);
                        BaseModel baseModel = gson.fromJson(s, BaseModel.class);
                        if (baseModel.getCode() == PublicUtils.code) {
                            showToast("图片上传成功");
                            startActivity(new Intent(UpPhotoActivity.this,SampleOutDetailActivity.class));
                            finish();
                        }else {
                            showToast(baseModel.getMsg());
                        }
                    }
                });
    }

    public void showStopDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(UpPhotoActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_send_pic, null);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.show();
        dialogBuilder.setContentView(dialogView);

        TextView tv_no = dialogView.findViewById(R.id.tv_no);
        TextView tv_ok = dialogView.findViewById(R.id.tv_ok);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileList.size()==list.size()){
                    upPic(fileList);
                }else {
                    showToast(getString(R.string.pic_not_finish_yas));
                }
                //showToast("正在上传图片，请稍候！");
                dialogBuilder.dismiss();
            }
        });

        Window window = dialogBuilder.getWindow();
        //这句设置我们dialog在底部
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        DisplayMetrics d = this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.74); // 高度设置为屏幕的0.6
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    @Override
    public void onItemClick(View view, int position) {
        showDialog();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void showDialog() {
        dialog = new Dialog(UpPhotoActivity.this, R.style.ActionDialogStyle);
        //填充对话框的布局
        inflate = (LinearLayout) LayoutInflater.from(UpPhotoActivity.this).inflate(R.layout.layout_photo, null);
        //初始化控件
        tv_take_photo = (TextView) inflate.findViewById(R.id.tv_take_photo);
        tv_take_photo.setOnClickListener(this);
        tv_find_from = (TextView) inflate.findViewById(R.id.tv_find_from);
        tv_find_from.setOnClickListener(this);
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
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
