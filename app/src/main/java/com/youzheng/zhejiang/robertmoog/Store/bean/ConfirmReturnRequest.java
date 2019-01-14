package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ConfirmReturnRequest {

    private List<ReshippedGoodsDataListBean> reshippedGoodsDataList;

    public List<ReshippedGoodsDataListBean> getReshippedGoodsDataList() {
        return reshippedGoodsDataList;
    }

    public void setReshippedGoodsDataList(List<ReshippedGoodsDataListBean> reshippedGoodsDataList) {
        this.reshippedGoodsDataList = reshippedGoodsDataList;
    }

    public static class ReshippedGoodsDataListBean {
        /**
         * orderItemProductId : mock
         * count : mock
         * refundAmount : mock
         * actualRefundAmount : mock
         */

        private String orderItemProductId;
        private String count;
        private String refundAmount;
        private String actualRefundAmount;

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

        public String getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(String refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getActualRefundAmount() {
            return actualRefundAmount;
        }

        public void setActualRefundAmount(String actualRefundAmount) {
            this.actualRefundAmount = actualRefundAmount;
        }
    }
}
