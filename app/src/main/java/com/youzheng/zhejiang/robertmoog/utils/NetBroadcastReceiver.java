package com.youzheng.zhejiang.robertmoog.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.youzheng.zhejiang.robertmoog.Base.BaseActivity;
import com.youzheng.zhejiang.robertmoog.Base.BaseFragment;

public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetChangeListener listener = BaseActivity.listener;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            if (listener != null) {
                listener.onChangeListener(netWorkState);
            }

        }
//        context.unregisterReceiver(this);
    }

    // 自定义接口
    public interface NetChangeListener {
        void onChangeListener(int status);
    }
}

