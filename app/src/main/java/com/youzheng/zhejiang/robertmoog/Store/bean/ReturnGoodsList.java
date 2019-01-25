package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ReturnGoodsList {

        /**
         * pageNum : 1ssss
         * pageSize : 10
         * totalSize : 25
         * totalPage : 3
         * returnOrderList : [{"createDate":"mock","orderCode":"mock","actualRefundAmount":"mock","productNum":"mock","productList":{"sku":"mock","photo":"mock","name":"mock"},"id":"mock"}]
         */

        private int pageNum;
        private int pageSize;
        private int totalSize;
        private int totalPage;
        private List<ReturnOrderListBean> returnOrderList;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ReturnOrderListBean> getReturnOrderList() {
            return returnOrderList;
        }

        public void setReturnOrderList(List<ReturnOrderListBean> returnOrderList) {
            this.returnOrderList = returnOrderList;
        }

        public static class ReturnOrderListBean {
            /**
             * createDate : mock
             * orderCode : mock
             * actualRefundAmount : mock
             * productNum : mock
             * productList : {"sku":"mock","photo":"mock","name":"mock"}
             * id : mock
             */

            private String createDate;
            private String orderCode;
            private int actualRefundAmount;
            private int productNum;
            private List<ProductListBean> productList;
            private int id;
            private String returnOrderCode;

            public String getReturnOrderCode() {
                return returnOrderCode;
            }

            public void setReturnOrderCode(String returnOrderCode) {
                this.returnOrderCode = returnOrderCode;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public int getActualRefundAmount() {
                return actualRefundAmount;
            }

            public void setActualRefundAmount(int actualRefundAmount) {
                this.actualRefundAmount = actualRefundAmount;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public static class ProductListBean {
                /**
                 * sku : mock
                 * photo : mock
                 * name : mock
                 */

                private String sku;
                private String photo;
                private String name;

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

    }
}
