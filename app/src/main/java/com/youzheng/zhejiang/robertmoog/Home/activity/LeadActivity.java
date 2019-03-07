package com.youzheng.zhejiang.robertmoog.Home.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.MainActivity;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.SPUtils;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class LeadActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    /**
     * 立即开始
     */
    private Button mBtStart;
    private int []imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        initView();
        //加载ViewPager
        initViewPager();
    }

    private void initViewPager() {
        imageIdArray = new int[]{R.drawable.lead_1,R.drawable.lead_2,R.drawable.lead_3};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0;i<len;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        //View集合初始化好后，设置Adapter
        mPager.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        mPager.setOnPageChangeListener(this);



    }

    private void initView() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mBtStart = (Button) findViewById(R.id.bt_start);
        mBtStart.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_start:
                startActivity(new Intent(this,LoginActivity.class));
                SPUtils.setParam(this,"IsFir",false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        //判断是否是最后一页，若是则显示按钮
        if (position == 2){
            mBtStart.setVisibility(View.VISIBLE);
        }else {
            mBtStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class GuidePageAdapter extends PagerAdapter {

        private List<View> viewList;

        public GuidePageAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        /**
         * @return 返回页面的个数
         */
        @Override
        public int getCount() {
            if (viewList != null) {
                return viewList.size();
            }
            return 0;
        }

        /**
         * 判断对象是否生成界面
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 初始化position位置的界面
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }
}
