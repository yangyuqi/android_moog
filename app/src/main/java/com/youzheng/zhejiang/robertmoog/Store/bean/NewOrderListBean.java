package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class NewOrderListBean {

    /**
     * pageNum : 1
     * pageSize : 10
     * totalSize : 25
     * totalPage : 3
     * orderList : [{"createDate":"mock","orderCode":"mock","payAmount":"mock","productNum":1,"orderItemInfos":[{"code":"mock","name":"mock","photo":"mock"}],"id":"mock"}]
     */

    private int pageNum;
    private int pageSize;
    private int totalSize;
    private int totalPage;
    private List<OrderListBean> orderList;

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

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * createDate : mock
         * orderCode : mock
         * payAmount : mock
         * productNum : 1
         * orderItemInfos : [{"code":"mock","name":"mock","photo":"mock"}]
         * id : mock
         */

        private String createDate;
        private String orderCode;
        private String payAmount;
        private int productNum;
        private String id;
        private List<OrderItemInfosBean> orderItemInfos;

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

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<OrderItemInfosBean> getOrderItemInfos() {
            return orderItemInfos;
        }

        public void setOrderItemInfos(List<OrderItemInfosBean> orderItemInfos) {
            this.orderItemInfos = orderItemInfos;
        }

        public static class OrderItemInfosBean {
            /**
             * code : mock
             * name : mock
             * photo : mock
             */

            private String code;
            private String name;
            private String photo;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }

    }


}
