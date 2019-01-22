package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class OrderlistDetail {



        /**
         * orderItemData : {"code":"mock","createDate":"mock","account":"mock","createUser":"mock","businessRole":"mock","isOrderDerate":true,"maxAmount":"mock","orderDerate":"mock","isFreeGift":true,"isMoen":true,"pickUpStatus":"mock","paymentMethod":"mock","shoppingMethod":"mock","productCount":"mock","amountPayable":"mock","payAmount":"mock","couponDerate":"mock","shopDerate":"mock","installStatus":"mock","shipPerson":"mock","shipMobile":"mock","shipAddress":"mock","comment":"mock","returnOrderStatus":"mock","orderProductList":[{"sku":"mock","name":"mock","photo":"mock","price":"mock","count":1,"codePu":"mock","addPrice":"mock","square":"mock","returnCount":1,"isSpecial":true}],"orderSetMealList":[{"comboDescribe":"mock","comboName":"mock","code":"mock","price":"mock","count":1,"photo":"mock","productList":[{"sku":"mock","name":"mock","photo":"mock","price":"mock","count":1}]}]}
         */

        private OrderItemDataBean orderItemData;

        public OrderItemDataBean getOrderItemData() {
            return orderItemData;
        }

        public void setOrderItemData(OrderItemDataBean orderItemData) {
            this.orderItemData = orderItemData;
        }

        public static class OrderItemDataBean {
            /**
             * code : mock
             * createDate : mock
             * account : mock
             * createUser : mock
             * businessRole : mock
             * isOrderDerate : true
             * maxAmount : mock
             * orderDerate : mock
             * isFreeGift : true
             * isMoen : true
             * pickUpStatus : mock
             * paymentMethod : mock
             * shoppingMethod : mock
             * productCount : mock
             * amountPayable : mock
             * payAmount : mock
             * couponDerate : mock
             * shopDerate : mock
             * installStatus : mock
             * shipPerson : mock
             * shipMobile : mock
             * shipAddress : mock
             * comment : mock
             * returnOrderStatus : mock
             * orderProductList : [{"sku":"mock","name":"mock","photo":"mock","price":"mock","count":1,"codePu":"mock","addPrice":"mock","square":"mock","returnCount":1,"isSpecial":true}]
             * orderSetMealList : [{"comboDescribe":"mock","comboName":"mock","code":"mock","price":"mock","count":1,"photo":"mock","productList":[{"sku":"mock","name":"mock","photo":"mock","price":"mock","count":1}]}]
             */

            private String code;
            private String createDate;
            private String account;
            private String createUser;
            private String businessRole;
            private boolean isOrderDerate;
            private String maxAmount;
            private String orderDerate;
            private boolean isFreeGift;
            private boolean isMoen;
            private String pickUpStatus;
            private String paymentMethod;
            private String shoppingMethod;
            private int productCount;
            private String amountPayable;
            private String payAmount;
            private String couponDerate;
            private String shopDerate;
            private String installStatus;
            private String shipPerson;
            private String shipMobile;
            private String shipAddress;
            private String comment;
            private String returnOrderStatus;
            private List<OrderProductListBean> orderProductList;
            private List<OrderSetMealListBean> orderSetMealList;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getBusinessRole() {
                return businessRole;
            }

            public void setBusinessRole(String businessRole) {
                this.businessRole = businessRole;
            }

            public boolean isIsOrderDerate() {
                return isOrderDerate;
            }

            public void setIsOrderDerate(boolean isOrderDerate) {
                this.isOrderDerate = isOrderDerate;
            }

            public String getMaxAmount() {
                return maxAmount;
            }

            public void setMaxAmount(String maxAmount) {
                this.maxAmount = maxAmount;
            }

            public String getOrderDerate() {
                return orderDerate;
            }

            public void setOrderDerate(String orderDerate) {
                this.orderDerate = orderDerate;
            }

            public boolean isIsFreeGift() {
                return isFreeGift;
            }

            public void setIsFreeGift(boolean isFreeGift) {
                this.isFreeGift = isFreeGift;
            }

            public boolean isIsMoen() {
                return isMoen;
            }

            public void setIsMoen(boolean isMoen) {
                this.isMoen = isMoen;
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

            public String getShoppingMethod() {
                return shoppingMethod;
            }

            public void setShoppingMethod(String shoppingMethod) {
                this.shoppingMethod = shoppingMethod;
            }

            public int getProductCount() {
                return productCount;
            }

            public void setProductCount(int productCount) {
                this.productCount = productCount;
            }

            public String getAmountPayable() {
                return amountPayable;
            }

            public void setAmountPayable(String amountPayable) {
                this.amountPayable = amountPayable;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getCouponDerate() {
                return couponDerate;
            }

            public void setCouponDerate(String couponDerate) {
                this.couponDerate = couponDerate;
            }

            public String getShopDerate() {
                return shopDerate;
            }

            public void setShopDerate(String shopDerate) {
                this.shopDerate = shopDerate;
            }

            public String getInstallStatus() {
                return installStatus;
            }

            public void setInstallStatus(String installStatus) {
                this.installStatus = installStatus;
            }

            public String getShipPerson() {
                return shipPerson;
            }

            public void setShipPerson(String shipPerson) {
                this.shipPerson = shipPerson;
            }

            public String getShipMobile() {
                return shipMobile;
            }

            public void setShipMobile(String shipMobile) {
                this.shipMobile = shipMobile;
            }

            public String getShipAddress() {
                return shipAddress;
            }

            public void setShipAddress(String shipAddress) {
                this.shipAddress = shipAddress;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getReturnOrderStatus() {
                return returnOrderStatus;
            }

            public void setReturnOrderStatus(String returnOrderStatus) {
                this.returnOrderStatus = returnOrderStatus;
            }

            public List<OrderProductListBean> getOrderProductList() {
                return orderProductList;
            }

            public void setOrderProductList(List<OrderProductListBean> orderProductList) {
                this.orderProductList = orderProductList;
            }

            public List<OrderSetMealListBean> getOrderSetMealList() {
                return orderSetMealList;
            }

            public void setOrderSetMealList(List<OrderSetMealListBean> orderSetMealList) {
                this.orderSetMealList = orderSetMealList;
            }

            public static class OrderProductListBean {
                /**
                 * sku : mock
                 * name : mock
                 * photo : mock
                 * price : mock
                 * count : 1
                 * codePu : mock
                 * addPrice : mock
                 * square : mock
                 * returnCount : 1
                 * isSpecial : true
                 */

                private String sku;
                private String name;
                private String photo;
                private String price;
                private int count;
                private String codePu;
                private String addPrice;
                private int square;
                private int returnCount;
                private boolean isSpecial;

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
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

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getCodePu() {
                    return codePu;
                }

                public void setCodePu(String codePu) {
                    this.codePu = codePu;
                }

                public String getAddPrice() {
                    return addPrice;
                }

                public void setAddPrice(String addPrice) {
                    this.addPrice = addPrice;
                }

                public int getSquare() {
                    return square;
                }

                public void setSquare(int square) {
                    this.square = square;
                }

                public int getReturnCount() {
                    return returnCount;
                }

                public void setReturnCount(int returnCount) {
                    this.returnCount = returnCount;
                }

                public boolean isIsSpecial() {
                    return isSpecial;
                }

                public void setIsSpecial(boolean isSpecial) {
                    this.isSpecial = isSpecial;
                }
            }

            public static class OrderSetMealListBean {
                /**
                 * comboDescribe : mock
                 * comboName : mock
                 * code : mock
                 * price : mock
                 * count : 1
                 * photo : mock
                 * productList : [{"sku":"mock","name":"mock","photo":"mock","price":"mock","count":1}]
                 */

                private String comboDescribe;
                private String comboName;
                private String code;
                private String price;
                private int count;
                private String photo;
                private List<ProductListBean> productList;
                private boolean isexpress ;//是否展开

                public boolean isIsexpress() {
                    return isexpress;
                }

                public void setIsexpress(boolean isexpress) {
                    this.isexpress = isexpress;
                }

                public String getComboDescribe() {
                    return comboDescribe;
                }

                public void setComboDescribe(String comboDescribe) {
                    this.comboDescribe = comboDescribe;
                }

                public String getComboName() {
                    return comboName;
                }

                public void setComboName(String comboName) {
                    this.comboName = comboName;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public List<ProductListBean> getProductList() {
                    return productList;
                }

                public void setProductList(List<ProductListBean> productList) {
                    this.productList = productList;
                }

                public static class ProductListBean {
                    /**
                     * sku : mock
                     * name : mock
                     * photo : mock
                     * price : mock
                     * count : 1
                     */

                    private String sku;
                    private String name;
                    private String photo;
                    private String price;
                    private int count;

                    public String getSku() {
                        return sku;
                    }

                    public void setSku(String sku) {
                        this.sku = sku;
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

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public int getCount() {
                        return count;
                    }

                    public void setCount(int count) {
                        this.count = count;
                    }
                }
            }

    }
}
