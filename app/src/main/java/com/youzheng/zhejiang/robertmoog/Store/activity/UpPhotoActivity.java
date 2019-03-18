package com.youzheng.zhejiang.robertmoog.Store.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.MainActivity;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.AddphotoAdapter;
import com.youzheng.zhejiang.robertmoog.Store.listener.DeleteListener;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.AndroidScheduler;
import com.youzheng.zhejiang.robertmoog.utils.View.RemindDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 上传图片界面sss
 */
public class UpPhotoActivity extends BaseCameraActivity implements View.OnClickListener, OnRecyclerViewAdapterItemClickListener, DeleteListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private GridView gv_photo;
    public static List<String> list = new ArrayList<>();
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
    private ArrayList<File> fileList = new ArrayList<>();
    private String response;
    private LinearLayout lin_show;
    private int count=1;


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
        addlist= (List<String>) getIntent().getSerializableExtra("picturePathlist");



        if (addlist!=null){
            list.addAll(addlist);
        }else {
            list.add(path);
        }
        Log.e("ppp", list.size() + "");
        Log.e("ppp", path + "");
        adapter = new AddphotoAdapter(list, this);
        gv_photo.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);
        lin_show = findViewById(R.id.lin_show);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <list.size() ; i++) {
                    LuBan(list.get(i));
                }

            }
        }).start();

       // Log.e("集合路径", path);

        adapter.setDeleteListener(this);


    }

    @Override
    protected void setHeadIvEvenSendMine(Bitmap bm, final String picturePath) {
        super.setHeadIvEvenSendMine(bm, picturePath);
        if (list.size()<10){
            list.add(picturePath);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    LuBan(picturePath);


                }
            }).start();
            Log.e("www", picturePath + "");
            Log.e("2131", list.size() + "");
        }else {
            return;
        }


    }

    @Override
    protected void setPicList(List<PhotoInfo> resultList) {
        super.setPicList(resultList);

        for (final PhotoInfo photoInfo:resultList){
            if (list.size()<10){
                list.add(photoInfo.getPhotoPath());
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        LuBan(photoInfo.getPhotoPath());


                    }
                }).start();
             //   Log.e("www", picturePath + "");
                Log.e("2131", list.size() + "");
            }else {
                return;
            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    public void stopfinish() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(UpPhotoActivity.this, R.style.mydialog).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_finish, null);
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
                list.clear();
                fileList.clear();
                adapter.clear();
                count++;
                finish();
                dialogBuilder.dismiss();
            }
        });

        Window window = dialogBuilder.getWindow();
        //这句设置我们dialog在底部
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        DisplayMetrics d = this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
        // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                if (list.size()!=0){
                    stopfinish();
                }else {
                    list.clear();
                    fileList.clear();
                    adapter.clear();
                    count++;
                    finish();

                }

                break;
            case R.id.textHeadNext:
                if (list.size()==0){
                    showToasts("请选择图片");
                }else {
                    showStopDialog();
                }

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (list.size()!=0){
                stopfinish();
            }else {
                list.clear();
                fileList.clear();
                adapter.clear();
                count++;
                finish();

            }


        }


        return super.onKeyDown(keyCode, event);
    }

    private void LuBan(String picpath) {
        Luban.with(this)
                .load(picpath)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
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
                        Log.e("213", "压缩完成"+"--------------"+file.length()/1024+"k");
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



    private void upPic(ArrayList<File> pic) {
        lin_show.setVisibility(View.VISIBLE);
        OkHttpClientManager.getInstance().sendMultipart(UrlUtils.UPLOAD_FILE + "?access_token=" + access_token,"posters", pic)
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
                        if (count==1){
                            showToasts("服务器忙,请稍候重试");
                        }

                        lin_show.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(String s) {
                        lin_show.setVisibility(View.GONE);
                        BaseModel baseModel = gson.fromJson(s, BaseModel.class);
                        if (baseModel.getCode() == PublicUtils.code) {
                            Log.e("图片上传成功", s);
                            showToasts("图片上传成功");
                            startActivity(new Intent(UpPhotoActivity.this,SampleOutDetailActivity.class));
                            list.clear();
                            fileList.clear();
                            adapter.clear();
                            finish();
                        }else {
                            Log.e("图片上传失败",s);
                            if (!TextUtils.isEmpty(baseModel.getMsg())){
                                showToasts(baseModel.getMsg());
                            }

                        }
                    }
                });
    }

    public void showStopDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(UpPhotoActivity.this,R.style.mydialog).create();
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
                    //upLoad(filespath);
                }else {
                    showToasts(getString(R.string.pic_not_finish_yas));
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
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
        //lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count++;
        OkHttpClientManager.stopRequest();
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

    @Override
    public void deletePic(View view, int position) {
        list.remove(position);
        fileList.remove(position);
        adapter.notifyDataSetChanged();
    }
}
