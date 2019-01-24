package com.youzheng.zhejiang.robertmoog.Store.listener;

import com.youzheng.zhejiang.robertmoog.Store.bean.ReturnGoodsCounter;

import java.util.List;

public interface CounterListener {

    void getTotal(List<ReturnGoodsCounter.ReturnOrderInfoBean.ProductListBean> list);
}
