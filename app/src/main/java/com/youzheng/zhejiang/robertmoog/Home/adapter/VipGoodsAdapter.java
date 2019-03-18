package com.youzheng.zhejiang.robertmoog.Home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youzheng.zhejiang.robertmoog.Base.request.OkHttpClientManager;
import com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils;
import com.youzheng.zhejiang.robertmoog.Base.utils.UrlUtils;
import com.youzheng.zhejiang.robertmoog.Home.bean.VipGoods;
import com.youzheng.zhejiang.robertmoog.Model.BaseModel;
import com.youzheng.zhejiang.robertmoog.Model.Home.IntentProductList;
import com.youzheng.zhejiang.robertmoog.R;
import com.youzheng.zhejiang.robertmoog.Store.listener.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.robertmoog.Store.listener.VipDeleteListener;
import com.youzheng.zhejiang.robertmoog.utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialog;
import com.youzheng.zhejiang.robertmoog.utils.View.DeleteDialogInterface;
import com.youzheng.zhejiang.robertmoog.utils.View.NoScrollListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.youzheng.zhejiang.robertmoog.Base.utils.PublicUtils.access_token;

public class VipGoodsAdapter extends RecyclerView.Adapter<VipGoodsAdapter.VipGoodsHolder> {
    private List<VipGoods.IntentInfoListBean> intentInfoListBeans;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnRecyclerViewAdapterItemClickListener mOnItemClickListener;
    CommonAdapter<VipGoods.IntentInfoListBean.ProductListBean> adapter;
    private List<VipGoods.IntentInfoListBean.ProductListBean> inlist;
    int Width;
    VipDeleteListener listener;
    int layout_id;
    private List<VipGoods.IntentInfoListBean.ProductListBean> S=new ArrayList<>();
     String access_token;

    public void setOnItemClickListener(OnRecyclerViewAdapterItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public VipGoodsAdapter(List<VipGoods.IntentInfoListBean> intentInfoListBeans, Context context, int Width, VipDeleteListener listener) {
        this.intentInfoListBeans = intentInfoListBeans;
        this.context = context;
        this.Width = Width;
        this.listener = listener;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setLists(List<VipGoods.IntentInfoListBean> intentInfoListBeans) {
        this.intentInfoListBeans = intentInfoListBeans;
        notifyDataSetChanged();
    }

    public void clear() {
        intentInfoListBeans.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VipGoodsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_vip_goods, viewGroup, false);
        final VipGoodsHolder vipGoodsHolder = new VipGoodsHolder(view);

        vipGoodsHolder.tv_update_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = vipGoodsHolder.getLayoutPosition();
                //设置监听
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }

            }
        });

        return vipGoodsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VipGoodsHolder vipGoodsHolder, int i) {
        final VipGoods.IntentInfoListBean bean = intentInfoListBeans.get(i);
        vipGoodsHolder.tv_name.setText(bean.getName());
        vipGoodsHolder.tv_role.setText("(" + bean.getBusinessRole() + ")");
        vipGoodsHolder.tv_attention.setText(bean.getRemark());
        if (!TextUtils.isEmpty(bean.getRemark())) {
            vipGoodsHolder.tv_update_intent.setText("修改备注");
        } else {
            vipGoodsHolder.tv_update_intent.setText("添加备注");
        }

        inlist = new ArrayList<>();
        inlist = intentInfoListBeans.get(i).getProductList();



        boolean ishaveData=false;
        for (int j = 0; j <intentInfoListBeans.size() ; j++) {
            if (intentInfoListBeans.get(j).getProductList().size()>0){
                ishaveData=true;
                break;
            }
        }
        if (ishaveData==false){
            if (i==intentInfoListBeans.size()-1){
                vipGoodsHolder.tv_no_data.setVisibility(View.VISIBLE);
                vipGoodsHolder.ls.setVisibility(View.GONE);
            }else {
                vipGoodsHolder.ls.setVisibility(View.VISIBLE);
                vipGoodsHolder.tv_no_data.setVisibility(View.GONE);
            }
        }else {
            vipGoodsHolder.ls.setVisibility(View.VISIBLE);
            vipGoodsHolder.tv_no_data.setVisibility(View.GONE);
        }




        final String employedid = (String) SharedPreferencesUtils.getParam(context, PublicUtils.EMPLOYEDID, "");
        if (employedid.equals(bean.getId())) {
            vipGoodsHolder.tv_update_intent.setVisibility(View.VISIBLE);
        } else {
            vipGoodsHolder.tv_update_intent.setVisibility(View.GONE);

        }

        if (vipGoodsHolder.tv_update_intent.getVisibility()==View.GONE){
            if (TextUtils.isEmpty(bean.getRemark())){
                vipGoodsHolder.lin_over.setVisibility(View.GONE);
            }else {
                vipGoodsHolder.tv_attention.setVisibility(View.VISIBLE);
            }
        }

        if (employedid.equals(bean.getId())) {
            layout_id = R.layout.common_goods_vh_item;
        } else {
            layout_id = R.layout.common_goods_vh_item22;
        }

        adapter = new CommonAdapter<VipGoods.IntentInfoListBean.ProductListBean>(context, inlist,layout_id) {
            @Override
            public void convert(final ViewHolder helper, final VipGoods.IntentInfoListBean.ProductListBean item) {
                helper.setText(R.id.tv_name, item.getSku());
                helper.setText(R.id.tv_desc, item.getName());
                helper.setText(R.id.tv_price, "¥" + item.getPrice());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.type_icon).into((ImageView) helper.getView(R.id.iv_icon));


                access_token = (String) SharedPreferencesUtils.getParam(context, PublicUtils.access_token, "");
                RelativeLayout view = helper.getView(R.id.rl_width);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.width = (int) PublicUtils.dip2px(PublicUtils.px2dip(Width));
                view.setLayoutParams(params);
                helper.getView(R.id.main_right_drawer_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog dialog = new DeleteDialog(mContext, "提示", "是否删除此意向商品", "确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", item.getId());
                                final Gson gson = new Gson();
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.DELETE_GOODS + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                                        if (baseModel.getCode() == PublicUtils.code) {
                                            adapter.clear();
                                            listener.delete();
                                            //sendRemark(bean.getIntentId() + "");
                                            //  initData();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });

            }
        };

        vipGoodsHolder.ls.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    private void sendRemark(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("remark", "");
        OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.UPDATE_INTENT_REMARK + "?access_token=" + access_token, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseModel baseModel = new Gson().fromJson(response, BaseModel.class);
                if (baseModel.getCode() == PublicUtils.code) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return intentInfoListBeans.size();
    }

    class VipGoodsHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_role, tv_attention, tv_update_intent,tv_no_data;
        private NoScrollListView ls;
        private LinearLayout lin_over,lin_role;
        private View no_data;

        public VipGoodsHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_role = itemView.findViewById(R.id.tv_role);
            tv_attention = itemView.findViewById(R.id.tv_attention);
            tv_update_intent = itemView.findViewById(R.id.tv_update_intent);
            ls = itemView.findViewById(R.id.ls);
            lin_over = itemView.findViewById(R.id.lin_over);
            tv_no_data= itemView.findViewById(R.id.tv_no_data);
            lin_role= itemView.findViewById(R.id.lin_role);
        }
    }

}
