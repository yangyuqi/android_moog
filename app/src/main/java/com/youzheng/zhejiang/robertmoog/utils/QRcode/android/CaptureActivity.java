package com.youzheng.zhejiang.robertmoog.utils.QRcode.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.activity.SalesActivity;
import com.youzheng.zhejiang.robertmoog.Home.activity.SearchGoodsActivity;
import com.youzheng.zhejiang.robertmoog.Home.adapter.RecycleViewDivider;
import com.youzheng.zhejiang.robertmoog.Home.adapter.SearchResultAdapter;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerData;
import com.youzheng.zhejiang.robertmoog.Model.Home.IntentProductList;
import com.youzheng.zhejiang.robertmoog.Model.Home.IntentProductsBeanAdd;
import com.youzheng.zhejiang.robertmoog.Model.Home.OrderSetMealDatasBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ProductListBean;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatas;
import com.youzheng.zhejiang.robertmoog.Model.Home.ScanDatasBean;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.utils.CommonAdapter;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.bean.ZxingConfig;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.camera.CameraManager;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.common.Constant;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.decode.DecodeImgCallback;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.decode.DecodeImgThread;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.decode.ImageUtil;
import com.youzheng.zhejiang.robertmoog.utils.QRcode.view.ViewfinderView;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;


/**
 * @author: yzq
 * @date: 2017/10/26 15:22
 * @declare :扫一扫
 */

public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    public ZxingConfig config;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;
    private RecyclerView recycler_view ;
    private String token ;
    private Gson gson = new Gson();
    SearchResultAdapter addapter ;
    private ArrayList<ScanDatasBean> datasBeanList = new ArrayList<>();
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }
    private TextView tv_confrim ;
    private String customerId ,type ;
    public Handler getHandler() {
        return handler;
    }
    int widWidth ;
    private ImageView iv_preview ;

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }


        /*先获取配置信息*/
        try {
            config = (ZxingConfig) getIntent().getExtras().get(Constant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {

            Log.i("config", e.toString());
        }

        if (config == null) {
            config = new ZxingConfig();
            config.setReactColor(R.color.color_air_blue);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.bg_background_white);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.color_air_blue);//设置扫描线的颜色 默认白色
        }
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_capture);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;

        token = (String) SharedPreferencesUtils.getParam(this, PublicUtils.access_token,"");
        initView();

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        beepManager.setPlayBeep(config.isPlayBeep());
        beepManager.setVibrate(config.isShake());

        findViewById(R.id.rl_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPreview) {
                    iv_preview.setImageResource(R.mipmap.group_14_1);
                    findViewById(R.id.largeLabel).setVisibility(View.GONE);
                    isPreview = false;
                }else {
                    iv_preview.setImageResource(R.mipmap.group_12_3);
                    findViewById(R.id.largeLabel).setVisibility(View.VISIBLE);
                    isPreview = true;
                }
            }
        });
    }

    private boolean isPreview = true;

    private void initView() {
        previewView = findViewById(R.id.preview_view);
        previewView.setOnClickListener(this);

        viewfinderView = findViewById(R.id.viewfinder_view);
        viewfinderView.setZxingConfig(config);
        ((TextView)findViewById(R.id.textHeadTitle)).setText(this.getResources().getString(R.string.home_gv_two));
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
        recycler_view = findViewById(R.id.recycler_view);
        tv_confrim = findViewById(R.id.tv_confrim);
        customerId = getIntent().getStringExtra("customerId");
        type = getIntent().getStringExtra("type");
        if (type!=null){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("添加意向商品");
        }
        addapter = new SearchResultAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(manager);
        recycler_view.setAdapter(addapter);
        recycler_view.addItemDecoration(new RecycleViewDivider(CaptureActivity.this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bg_all)));

        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CaptureActivity.this, SearchGoodsActivity.class),2);
            }
        });

        iv_preview = findViewById(R.id.iv_preview);

        tv_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (datasBeanList.size() > 0) {
                        if (type==null) {
                            Intent intent = new Intent(CaptureActivity.this, SalesActivity.class);
                            intent.putExtra("customerId", customerId);
                            intent.putExtra("data", datasBeanList);
                            startActivity(intent);
                        }else {
                            addIntention();
                        }
                    } else {
                        showToast("请添加商品");
                    }
            }
        });
    }

    private void addIntention() {
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id",customerId);
        OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtils.ATTENTION_GOODS_LIST + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()== PublicUtils.code){
                    final CustomerData intentDataBean = gson.fromJson(gson.toJson(baseModel.getDatas()),CustomerData.class);
                    for (IntentProductList productList : intentDataBean.getCustomerIntentData().getIntentProductList()){
                        String id = productList.getId();
                        for (ScanDatasBean bean :datasBeanList){
//                            if (id.equals(bean.getId()))
                        }
                    }
                }
            }
        });

        Map<String,Object> map = new HashMap<>();
        map.put("customerId",customerId);
        ArrayList<IntentProductsBeanAdd> beanAdds = new ArrayList<>();
        for (ScanDatasBean bean :datasBeanList){
            IntentProductsBeanAdd beanAdd = new IntentProductsBeanAdd();
            beanAdd.setDealerProductId(bean.getId());
            beanAdd.setSetMeal(bean.isSetMeal());
            beanAdds.add(beanAdd);
        }
        map.put("intentProducts",beanAdds);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADD_INTENTION_GOODS + "?access_token=" + token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    finish();
                }
                showToast(baseModel.getMsg());
            }
        });
    }


    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {

        inactivityTimer.onActivity();

        beepManager.playBeepSoundAndVibrate();

        Intent intent = getIntent();
        intent.putExtra(Constant.CODED_CONTENT, rawResult.getText());
        setResult(RESULT_OK, intent);
//        this.finish();
        addData(rawResult.getText());
//        Toast.makeText(this,rawResult.getText(),Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication(), config);

        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {

        Log.i("CaptureActivity","onPause");
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void onClick(View view) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_IMAGE && resultCode == RESULT_OK) {
            String path = ImageUtil.getImageAbsolutePath(this, data.getData());

            new DecodeImgThread(path, new DecodeImgCallback() {
                @Override
                public void onImageDecodeSuccess(Result result) {
                    handleDecode(result);
                }

                @Override
                public void onImageDecodeFailed() {
                    Toast.makeText(CaptureActivity.this, "抱歉，解析失败,换个图片试试.", Toast.LENGTH_SHORT).show();
                }
            }).run();

        }

        if (requestCode==2&&resultCode==2){
            ScanDatasBean scanDatasBean = (ScanDatasBean) data.getSerializableExtra("data");
            if (scanDatasBean!=null){
                datasBeanList.add(scanDatasBean);

                Map<String,ScanDatasBean> map = new HashMap<>();
                for (ScanDatasBean bean : datasBeanList){
                    String id = bean.getId();
                    if (map.containsKey(id)){
                        ScanDatasBean datasBean = map.get(id);
                        bean.setNum(datasBean.getNum()+bean.getNum());
                    }
                    map.put(id, bean);
                }
                datasBeanList.clear();
                datasBeanList.addAll(map.values());
                if (type==null) {
                    addapter.setDate(datasBeanList, mContext, "2", widWidth);
                }else {
                    addapter.setDate(datasBeanList,mContext,"4",widWidth);
                }
            }
        }
    }

    public void addData(String code){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.SCAN_GOODS+"?access_token="+token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = gson.fromJson(response,BaseModel.class);
                if (baseModel.getCode()==PublicUtils.code){
                    ScanDatas scanDatas = gson.fromJson(gson.toJson(baseModel.getDatas()),ScanDatas.class);
                    if (scanDatas.getSelectProducts().size()>0){
                        datasBeanList.addAll(scanDatas.getSelectProducts());
                        Map<String,ScanDatasBean> map = new HashMap<>();
                        for (ScanDatasBean bean : datasBeanList){
                            String id = bean.getId();
                            if (map.containsKey(id)){
                                ScanDatasBean datasBean = map.get(id);
                                bean.setNum(datasBean.getNum()+bean.getNum());
                            }
                            map.put(id, bean);
                        }
                        datasBeanList.clear();
                        datasBeanList.addAll(map.values());
                        if (type==null) {
                            addapter.setDate(datasBeanList, mContext, "2", widWidth);
                        }else {
                            addapter.setDate(datasBeanList,mContext,"4",widWidth);
                        }
                    }
                }
            }
        });
    }



}
