package com.youzheng.zhejiang.robertmoog.Count.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.liaoinstan.springview.widget.SpringView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Count.adapter.CheckRuleAdapter;
import com.youzheng.zhejiang.robertmoog.Count.adapter.MealRankingAdapter;
import com.youzheng.zhejiang.robertmoog.Count.bean.MealRankingList;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.view.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.utils.View.MyFooter;
import com.youzheng.zhejiang.robertmoog.utils.View.MyHeader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 套餐排名界面
 */
public class MealRankingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 请选择时间
     */
    private TextView tv_startDate;
    /**
     * 请选择时间
     */
    private TextView tv_endDate;
    /**
     * 查询
     */
    private TextView tv_check;
    private LinearLayout lin_search;
    /**
     * 销售金额
     */
    private TextView tv_rule;
    private ImageView iv_more;
    private RecyclerView pr_list;
    private PopupWindow window;
    private LinearLayout mLinTitle;
    private ListView listView;
    private CheckRuleAdapter ruleAdapter;
    private List<MealRankingList.SetMealListBean> list = new ArrayList<>();
    private MealRankingAdapter adapter;
    private TimePickerView pvTime;
    private String time = "";
    private int who;

    private int page = 1;
    private int pageSize = 10;
    private boolean isDay = false;
    private String startstr = "";
    private String endstr = "";
    private String rulestr = "COUNT";//默认是数量
    private SpringView mSpringView;
    SimpleDateFormat dateFormater;
    Calendar cal;
    Date date;
    private View no_data,no_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_ranking);
        initView();
        setListener();
        initTimer();

         dateFormater = new SimpleDateFormat("yyyy/MM/dd");
         cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTime();
        tv_startDate.setText(dateFormater.format(cal.getTime()) + "");
        startstr = dateFormater.format(cal.getTime()) + "";
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
         date = new Date(System.currentTimeMillis());

        tv_endDate.setText(dateFormater.format(date));
        endstr = dateFormater.format(date);
        initData(page, pageSize, isDay, startstr, endstr, rulestr);
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        lp.dimAmount = bgAlpha;//设置灰度
        if (bgAlpha == 1) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为 红米米手机上半透明效果无效的bug
        }
        getWindow().setAttributes(lp);
    }
    private void setListener() {
//        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                list.clear();
//                initData(page, pageSize, isDay, startstr, endstr, rulestr);
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                list.clear();
//                page++;
//                initData(page, pageSize, isDay, startstr, endstr, rulestr);
//
//            }
//        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                initData(page, pageSize, isDay, startstr, endstr, rulestr);
            }

            @Override
            public void onLoadmore() {
                //list.clear();
                page++;
                initData(page, pageSize, isDay, startstr, endstr, rulestr);
            }
        });
    }

    @Override
    public void onChangeListener(int status) {
        super.onChangeListener(status);
        if (status==-1){
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.VISIBLE);
        }else {
            layout_header.setVisibility(View.VISIBLE);
            no_web.setVisibility(View.GONE);
        }
    }
    private void initView() {
        no_web = findViewById(R.id.no_web);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.meal_ranking));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setOnClickListener(this);
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);
        tv_check = (TextView) findViewById(R.id.tv_check);
        tv_check.setOnClickListener(this);
        lin_search = (LinearLayout) findViewById(R.id.lin_search);
        tv_rule = (TextView) findViewById(R.id.tv_rule);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);
        pr_list = (RecyclerView) findViewById(R.id.pr_list);
        mLinTitle = (LinearLayout) findViewById(R.id.lin_title);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pr_list.setLayoutManager(manager);
        pr_list.setAdapter(adapter);
        pr_list.addItemDecoration(new com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider(MealRankingActivity.this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_all)));

        adapter = new MealRankingAdapter(list, this);
        pr_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mSpringView = (SpringView) findViewById(R.id.springView);
        mSpringView.setHeader(new MyHeader(this));
        mSpringView.setFooter(new MyFooter(this));

        no_data=findViewById(R.id.no_data);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(int page, int pageSize, boolean isDay, String startDate, String endDate, String rule) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("isDay", isDay);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("rule", rule);


        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.MEAL_RANKING_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                Log.e("套餐排名", response);
                //pr_list.setPullLoadMoreCompleted();
                mSpringView.onFinishFreshAndLoad();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {
                    MealRankingList mealRankingList = gson.fromJson(gson.toJson(baseModel.getDatas()), MealRankingList.class);
                    setData(mealRankingList);
                } else {
                    showToasts(baseModel.getMsg());
                }
            }
        });


    }

    private void setData(MealRankingList mealRankingList) {
        if (mealRankingList == null) return;
        if (mealRankingList.getSetMealList() == null) return;
        List<MealRankingList.SetMealListBean> beanList = mealRankingList.getSetMealList();
        if (beanList.size() != 0) {
            list.addAll(beanList);
            adapter.setUI(beanList);
            no_data.setVisibility(View.GONE);
            mSpringView.setVisibility(View.VISIBLE);
        } else {
            if (page==1){
                no_data.setVisibility(View.VISIBLE);
                mSpringView.setVisibility(View.GONE);
            }else {
                showToasts(getString(R.string.load_list_erron));
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.tv_startDate:
                who = 1;
                pvTime.show(v, false);
                break;
            case R.id.tv_endDate:
                who = 2;
                pvTime.show(v, false);
                break;
            case R.id.tv_check:
                isDay = false;
                list.clear();
                adapter.clear();
                initData(page, pageSize, isDay, startstr, endstr, rulestr);
                break;
            case R.id.iv_more:
                if (tv_rule.getText().toString().equals("销售数量")) {
                    showPopuWindow(0);
                } else if (tv_rule.getText().toString().equals("销售金额")) {
                    showPopuWindow(1);
                }
                break;
        }
    }

    private void showPopuWindow(int who) {
        View inflate = getLayoutInflater().inflate(R.layout.popuwindow_rule, null);
        listView = inflate.findViewById(R.id.lv_list);
        final List<String> lists = new ArrayList<>();
        lists.add("销售数量");
        lists.add("销售金额");

        ruleAdapter = new CheckRuleAdapter(lists, this);
        listView.setAdapter(ruleAdapter);
        iv_more.setImageResource(R.mipmap.group_12_3);
        if (who == 0) {
            ruleAdapter.setSelectItem(0);
        } else {
            ruleAdapter.setSelectItem(1);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ruleAdapter.setSelectItem(position);
                if (position == 0) {
                    tv_rule.setText("销售数量");
                    // TODO: 2019/1/2 调用接口
                    rulestr = "COUNT";
                    list.clear();
                    initData(page, pageSize, isDay, startstr, endstr, rulestr);
                } else if (position == 1) {
                    tv_rule.setText("销售金额");
                    // TODO: 2019/1/2 调用接口
                    rulestr = "PRICE";
                    list.clear();
                    initData(page, pageSize, isDay, startstr, endstr, rulestr);
                }
                window.dismiss();
            }
        });
        window = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setAnimationStyle(R.style.ActionDialogStyle);

        window.setBackgroundDrawable(getDrawable());
        //backgroundAlpha(0.5f);

      //  mSpringView.setBackgroundColor(getResources().getColor(R.color.text_drak_black));
        mSpringView.setBackgroundColor(getResources().getColor(R.color.text_drak_black));
        mSpringView.setAlpha(0.35f);
      //  mSpringView.setAlpha(0.5f);
       // pr_list.setVisibility(View.GONE);
//        pr_list.setBackgroundColor(getResources().getColor(R.color.contents_text));
//        pr_list.setAlpha(0.5f);

        window.setOnDismissListener(new poponDismissListener());
        window.setTouchable(true); // 设置popupwindow可点击
        window.setOutsideTouchable(true); // 设置popupwindow外部可点击
        window.showAsDropDown(mLinTitle);
        window.update();

    }

    class poponDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            //backgroundAlpha(1f);
            iv_more.setImageResource(R.mipmap.group_14_1);
            mSpringView.setBackgroundColor(getResources().getColor(R.color.bg_background_white));
            mSpringView.setAlpha(1f);
           // pr_list.setVisibility(View.VISIBLE);
//            pr_list.setBackgroundColor(getResources().getColor(R.color.bg_background_white));
//            pr_list.setAlpha(1f);
        }
    }

    //获取屏幕的高度
    public static int getScreenHeight(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return height;

    }

    /**
     * 生成一个 透明的背景图片
     *
     * @return
     */
    private Drawable getDrawable() {
        ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(this.getResources().getColor(R.color.transparent));
        return bgdrawable;
    }

    private void initTimer() {
        Calendar selectedDate = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = selectedDate.get(Calendar.YEAR);
        //月
        int month = selectedDate.get(Calendar.MONTH) + 1;
        //日
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, 1, 1);

        final Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, day);

        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                time = getTime(date);
                if (!time.equals("")) {
                    if (who == 1) {
                        tv_startDate.setText(time);
                        startstr = time;
                    } else {
                        tv_endDate.setText(time);
                        endstr = time;
                    }
                }

            }
        })
                .setLayoutRes(R.layout.item_picker_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_confirm);
                        TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .isDialog(true)
                //  .setDecorView(pr_list)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(Color.parseColor("#F5F5F5"))
                .setOutSideCancelable(true)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉


    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }
}
