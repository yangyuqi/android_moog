package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.Manifest;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.Home.activity.RegisterActivity;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.ImageAdapter;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.utils.DepthPageTransformer;
import com.youzheng.zhejiang.robertmoog.Store.utils.DonwloadSaveImg;
import com.youzheng.zhejiang.robertmoog.Store.utils.PermissionUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.NoteInfoDialog;

import java.util.List;

/**
 *图片浏览器界面
 */
public class CheckPicActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 1/4
     */
    private TextView tv_num;
    private TextView tv_finish,tv_save_photo,tv_cancel;
    private ViewPager pager;
    private List<String> list;
    private int pos;
    private ImageAdapter adapter;
    private LinearLayout lin_back;
    private LinearLayout inflate;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pic);
        initView();
    }

    protected void initView() {


        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        tv_finish.setOnClickListener(this);
        pager = (ViewPager) findViewById(R.id.pager);

        list = getIntent().getStringArrayListExtra(MyConstant.PRINT_GLANCE_OVER_LIST);
        pos = getIntent().getIntExtra(MyConstant.PRINT_GLANCE_OVER_POS, 0);

        adapter = new ImageAdapter(list, this);
        pager.setAdapter(adapter);

        pager.setCurrentItem(pos);//设置起始位置
        pager.setPageTransformer(true, new DepthPageTransformer());//修改动画效果

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
                tv_num.setText((position + 1) + "/" + list.size());//显示当前图片的位置
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        lin_back = (LinearLayout) findViewById(R.id.lin_back);
        lin_back.setOnClickListener(this);

        adapter.setOnItemClickListener(new OnRecyclerViewAdapterItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                  String url=list.get(position);
                  showDialog(url);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_finish:
                finish();
                break;
            case R.id.lin_back:
               // finish();
                break;

        }
    }
    private void showDialog(final String picUrl) {
        dialog = new Dialog(CheckPicActivity.this, R.style.ActionDialogStyle);
        //填充对话框的布局
        inflate = (LinearLayout) LayoutInflater.from(CheckPicActivity.this).inflate(R.layout.layout_save_photo, null);
        //初始化控件
        tv_save_photo = (TextView) inflate.findViewById(R.id.tv_save_photo);
        tv_save_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (PermissionUtils.permissionIsOpen(CheckPicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            ){


                        DonwloadSaveImg.donwloadImg(CheckPicActivity.this,picUrl);
                        dialog.dismiss();
                    } else {

                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
                            PermissionUtils.showCameraDialog(getString(R.string.content_str_read)
                                    ,CheckPicActivity.this);
                        } else {

                            PermissionUtils.openSinglePermission(CheckPicActivity.this
                                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    , PermissionUtils.CODE_MULTI);

                        }

                    }
                }else{
                    DonwloadSaveImg.donwloadImg(CheckPicActivity.this,picUrl);
                    dialog.dismiss();
                }


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
