package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ReturnGoodsDetail {



        /**ssss
         * returnItemsss : {"id":"mock","orderCode":"mock","code":"mock","userName":"mock","custCode":"mock","businessRole":null,"pickUpStatus":null,"paymentMethod":"mock","productCount":"mock","reason":"mock","refundAmount":"mock","actualRefundAmount":"mock","productList":[{"name":"mock","sku":"mock","count":1,"price":"mock","photo":"mock"}]}
         */

        private ReturnItemBean returnItem;

        public ReturnItemBean getReturnItem() {
            return returnItem;
        }

        public void setReturnItem(ReturnItemBean returnItem) {
            this.returnItem = returnItem;
        }

        public static class ReturnItemBean {
            /**
             * id : mock
             * orderCode : mock
             * code : mock
             * userName : mock
             * custCode : mock
             * businessRole : null
             * pickUpStatus : null
             * paymentMethod : mock
             * productCount : mock
             * reason : mock
             * refundAmount : mock
             * actualRefundAmount : mock
             * productList : [{"name":"mock","sku":"mock","count":1,"price":"mock","photo":"mock"}]
             */

            private String id;
            private String orderCode;
            private String code;
            private String userName;
            private String custCode;
            private String businessRole;
            private String pickUpStatus;
            private String paymentMethod;
            private int productCount;
            private String reason;
            private String refundAmount;
            private String actualRefundAmount;
            private List<ProductListBean> productList;
            private String otherReson;

            public String getOtherReson() {
                return otherReson;
            }

            public void setOtherReson(String otherReson) {
                this.otherReson = otherReson;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getCustCode() {
                return custCode;
            }

            public void setCustCode(String custCode) {
                this.custCode = custCode;
            }

            public String getBusinessRole() {
                return businessRole;
            }

            public void setBusinessRole(String businessRole) {
                this.businessRole = businessRole;
            }

            public String getPickUpStatus() {
                return pickUpStatus;
            }

            public void setPickUpStatus(String pickUpStatus) {
                this.pickUpStatus = pickUpStatus;
            }

            public String getPaymentMethod() {
                return paymentMethod;
            }

            public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
            }

            public int getProductCount() {
                return productCount;
            }

            public void setProductCount(int productCount) {
                this.productCount = productCount;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
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
                 * price : mock
                 * photo : mock
                 */

                private String name;
                private String sku;
                private int count;
                private String price;
                private String photo;

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

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
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
