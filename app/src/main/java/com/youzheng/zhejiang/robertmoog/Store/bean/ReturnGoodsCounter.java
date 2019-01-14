package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ReturnGoodsCounter {


        /**
         * returnOrderInfo : {"id":"mock","refundAmount":"mock","returnCount":1,"productList":[{"name":"mock","sku":"mock","count":1,"refundAmount":"mock","photo":"mock","orderItemProductId":"mock","square":"mock","isSpecial":true}]}
         */

        private ReturnOrderInfoBean returnOrderInfo;

        public ReturnOrderInfoBean getReturnOrderInfo() {
            return returnOrderInfo;
        }

        public void setReturnOrderInfo(ReturnOrderInfoBean returnOrderInfo) {
            this.returnOrderInfo = returnOrderInfo;
        }

        public static class ReturnOrderInfoBean {
            /**
             * id : mock
             * refundAmount : mock
             * returnCount : 1
             * productList : [{"name":"mock","sku":"mock","count":1,"refundAmount":"mock","photo":"mock","orderItemProductId":"mock","square":"mock","isSpecial":true}]
             */

            private String id;
            private String refundAmount;
            private int returnCount;
            private List<ProductListBean> productList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRefundAmount() {
                return refundAmount;
            }

            public void setRefundAmount(String refundAmount) {
                this.refundAmount = refundAmount;
            }

            public int getReturnCount() {
                return returnCount;
            }

            public void setReturnCount(int returnCount) {
                this.returnCount = returnCount;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public static class ProductListBean {
                /**
                 * name : mock
                 * sku : mock
                 * count : 1
                 * refundAmount : mock
                 * photo : mock
                 * orderItemProductId : mock
                 * square : mock
                 * isSpecial : true
                 */

                private String name;
                private String sku;
                private int count;
                private String refundAmount;
                private String photo;
                private String orderItemProductId;
                private String square;
                private boolean isSpecial;
                private int money;

                public int getMoney() {
                    return money;
                }

                public void setMoney(int money) {
                    this.money = money;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getRefundAmount() {
                    return refundAmount;
                }

                public void setRefundAmount(String refundAmount) {
                    this.refundAmount = refundAmount;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getOrderItemProductId() {
                    return orderItemProductId;
                }

                public void setOrderItemProductId(String orderItemProductId) {
                    this.orderItemProductId = orderItemProductId;
                }

                public String getSquare() {
                    return square;
                }

                public void setSquare(String square) {
                    this.square = square;
                }

                public boolean isIsSpecial() {
                    return isSpecial;
                }

                public void setIsSpecial(boolean isSpecial) {
                    this.isSpecial = isSpecial;
                }
            }
        }

}
