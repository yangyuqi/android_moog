package com.youzheng.zhejiang.robertmoog.Home.bean;

import com.youzheng.zhejiang.robertmoog.Model.Home.CustomerIntentListBean;

import java.util.List;

public interface MangerListener  {
    void refresh(List<CustomerIntentListBean> lists);
}
