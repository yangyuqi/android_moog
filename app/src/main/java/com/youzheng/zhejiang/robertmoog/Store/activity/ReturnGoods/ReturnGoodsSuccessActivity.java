package com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.activity.ReturnGoodsManger.ReturnGoodsDetailActivity;
import com.youzheng.zhejiang.robertmoog.Store.activity.StoreOrderlistDetailActivity;

/**
 * 退货成功界面ssss
 */
public class ReturnGoodsSuccessActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    private LinearLayout lin_to_detail;
    private int id;
    private ImageView iv_title;
    /**
     * 退货完成!
     */
    private TextView tv_success;
    private ImageView iv_pic;
    /**
     * 退货单详情
     */
    private TextView tv_name;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_goods_success);
        id = getIntent().getIntExtra("returnid", 0);
        type=getIntent().getStringExtra("type");
        initView();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);

        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        lin_to_detail = (LinearLayout) findViewById(R.id.lin_to_detail);
        lin_to_detail.setOnClickListener(this);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_success = (TextView) findViewById(R.id.tv_success);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        tv_name = (TextView) findViewById(R.id.tv_name);
        if (type.equals("1")){
            textHeadTitle.setText("退货完成");
            iv_title.setImageResource(R.mipmap.group_55_1);
            tv_success.setText("退货完成!");
            iv_pic.setImageResource(R.mipmap.group_55_2);
            tv_name.setText("退货单详情");



        }else if (type.equals("2")){
            textHeadTitle.setText("订单完成");
            iv_title.setImageResource(R.mipmap.group_153_1);
            tv_success.setText("退货完成!");
            iv_pic.setImageResource(R.mipmap.group_153_2);
            tv_name.setText("订单详情");
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
            case R.id.lin_to_detail:
                if (tv_name.getText().equals("退货单详情")){
                    Intent intent = new Intent(this, ReturnGoodsDetailActivity.class);
                    intent.putExtra("returnGoodsId", id);
                    startActivity(intent);
                    finish();
                }else if (tv_name.getText().equals("订单详情")){
                    Intent intent = new Intent(this, StoreOrderlistDetailActivity.class);
                    intent.putExtra("OrderGoodsId", id);
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }
}
