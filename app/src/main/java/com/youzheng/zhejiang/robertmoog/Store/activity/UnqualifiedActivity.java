package com.youzheng.zhejiang.robertmoog.Store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.MyConstant;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.adapter.UnqualifiedAdapter;
import com.youzheng.zhejiang.robertmoog.Store.bean.CheckStoreDetail;
import com.youzheng.zhejiang.robertmoog.Store.bean.UnqualifiedContent;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * 巡店不合格界面
 */
public class UnqualifiedActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView btnBack;
    /**  */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView iv_next;
    private RelativeLayout layout_header;
    /**
     * 我我我我我我
     */
    private TextView tv_result;
    /**
     * 我我我我我我
     */
    private TextView tv_reason;
    private GridView gv_pic;
    private int patrolShopId,questionId;
    private UnqualifiedAdapter adapter;
    private List<UnqualifiedContent.PatrolShopProblemDetailBean.QuestionImagesBean> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unqualified);
        initView();
        initData();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(getString(R.string.not_good_reason));
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_reason = (TextView) findViewById(R.id.tv_reason);
        gv_pic = (GridView) findViewById(R.id.gv_pic);
        questionId=getIntent().getIntExtra("questionId",0);
        patrolShopId=getIntent().getIntExtra("patrolShopId",0);

        adapter=new UnqualifiedAdapter(list,this);
        gv_pic.setAdapter(adapter);

        gv_pic.setOnItemClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData() {
        HashMap<String,Object> map=new HashMap<>();
        map.put("questionId",questionId);
        map.put("patrolShopId",patrolShopId);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.UNQUALIFIED_DETAIL + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("不合格详情",response);
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    UnqualifiedContent unqualifiedContent = gson.fromJson(gson.toJson(baseModel.getDatas()),UnqualifiedContent.class);
                    setData(unqualifiedContent);
                }else {
                    showToast(baseModel.getMsg());
                }
            }

        });
    }

    private void setData(UnqualifiedContent unqualifiedContent) {
        if (unqualifiedContent.getPatrolShopProblemDetail()==null) return;
        if (unqualifiedContent.getPatrolShopProblemDetail().getQuestionImages()==null) return;

        if (!unqualifiedContent.getPatrolShopProblemDetail().getQuestionName().equals("")||
                unqualifiedContent.getPatrolShopProblemDetail().getQuestionName()!=null){
            tv_result.setText(unqualifiedContent.getPatrolShopProblemDetail().getQuestionName());
        }

        if (!unqualifiedContent.getPatrolShopProblemDetail().getQuestionDes().equals("")||
                unqualifiedContent.getPatrolShopProblemDetail().getQuestionDes()!=null){
            tv_reason.setText(unqualifiedContent.getPatrolShopProblemDetail().getQuestionDes());
        }

        List<UnqualifiedContent.PatrolShopProblemDetailBean.QuestionImagesBean> beans=unqualifiedContent.getPatrolShopProblemDetail().getQuestionImages();

        if (beans.size()!=0){
            list.addAll(beans);
            adapter.setPic(beans);
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
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,CheckPicActivity.class);
        intent.putExtra(MyConstant.PRINT_GLANCE_OVER_LIST, (Serializable) list);
        intent.putExtra(MyConstant.PRINT_GLANCE_OVER_POS,position);
        startActivity(intent);
    }
}
