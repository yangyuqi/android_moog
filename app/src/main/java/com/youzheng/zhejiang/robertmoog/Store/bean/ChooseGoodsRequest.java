package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ChooseGoodsRequest {


    private List<OrderProductListBean> orderProductList;

    public List<OrderProductListBean> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProductListBean> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public static class OrderProductListBean {
        /**
         * orderItemProductId : mock
         * count : mock
         */

        private String orderItemProductId;
        private String count;

        public String getOrderItemProductId() {
            return orderItemProductId;
        }

        public void setOrderItemProductId(String orderItemProductId) {
            this.orderItemProductId = orderItemProductId;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
