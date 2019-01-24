package com.youzheng.zhejiang.robertmoog.Store.listener;

import com.youzheng.zhejiang.robertmoog.Store.bean.ChooseReturnGoodsDetail;

import java.util.List;

public interface GetData {

    public void getoneTotal(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.ProductListBean> lists);

    void getMoreTotal(List<ChooseReturnGoodsDetail.ReturnOrderInfoBean.SetMealListBean.ProductListBeanX> list);
}
