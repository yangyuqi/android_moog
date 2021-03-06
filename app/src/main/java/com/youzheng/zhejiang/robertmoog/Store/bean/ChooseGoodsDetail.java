package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ChooseGoodsDetail {


        /**
         * returnOrderInfo : {"id":"mock","code":"mock","createUser":"mock","account":"mock","businessRole":"mock","productList":[{"name":"mock","sku":"mock","count":1,"refundAmount":"mock","photo":"mock","orderItemProductId":"mock","square":"mock","isSpecial":true}],"setMealList":[{"code":"mock","comboName":"mock","comboDescribe":"mock","photo":"mock","refundAmount":"mock","productList":[{"name":"mock","sku":"mock","refundAmount":"mock","photo":"mock","count":1,"orderItemProductId":"mock"}]}]}
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
             * code : mock
             * createUser : mock
             * account : mock
             * businessRole : mock
             * productList : [{"name":"mock","sku":"mock","count":1,"refundAmount":"mock","photo":"mock","orderItemProductId":"mock","square":"mock","isSpecial":true}]
             * setMealList : [{"code":"mock","comboName":"mock","comboDescribe":"mock","photo":"mock","refundAmount":"mock","productList":[{"name":"mock","sku":"mock","refundAmount":"mock","photo":"mock","count":1,"orderItemProductId":"mock"}]}]
             */

            private String id;
            private String code;
            private String createUser;
            private String account;
            private String businessRole;
            private List<ProductListBean> productList;
            private List<SetMealListBean> setMealList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getBusinessRole() {
                return businessRole;
            }

            public void setBusinessRole(String businessRole) {
                this.businessRole = businessRole;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public List<SetMealListBean> getSetMealList() {
                return setMealList;
            }

            public void setSetMealList(List<SetMealListBean> setMealList) {
                this.setMealList = setMealList;
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

            public static class SetMealListBean {
                /**
                 * code : mock
                 * comboName : mock
                 * comboDescribe : mock
                 * photo : mock
                 * refundAmount : mock
                 * productList : [{"name":"mock","sku":"mock","refundAmount":"mock","photo":"mock","count":1,"orderItemProductId":"mock"}]
                 */

                private String code;
                private String comboName;
                private String comboDescribe;
                private String photo;
                private String refundAmount;
                private List<ProductListBeanX> productList;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getComboName() {
                    return comboName;
                }

                public void setComboName(String comboName) {
                    this.comboName = comboName;
                }

                public String getComboDescribe() {
                    return comboDescribe;
                }

                public void setComboDescribe(String comboDescribe) {
                    this.comboDescribe = comboDescribe;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getRefundAmount() {
                    return refundAmount;
                }

                public void setRefundAmount(String refundAmount) {
                    this.refundAmount = refundAmount;
                }

                public List<ProductListBeanX> getProductList() {
                    return productList;
                }

                public void setProductList(List<ProductListBeanX> productList) {
                    this.productList = productList;
                }

                public static class ProductListBeanX {
                    /**
                     * name : mock
                     * sku : mock
                     * refundAmount : mock
                     * photo : mock
                     * count : 1
                     * orderItemProductId : mock
                     */

                    private String name;
                    private String sku;
                    private String refundAmount;
                    private String photo;
                    private int count;
                    private String orderItemProductId;

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

                    public int getCount() {
                        return count;
                    }

                    public void setCount(int count) {
                        this.count = count;
                    }

                    public String getOrderItemProductId() {
                        return orderItemProductId;
                    }

                    public void setOrderItemProductId(String orderItemProductId) {
                        this.orderItemProductId = orderItemProductId;
                    }
                }
            }

    }
}
