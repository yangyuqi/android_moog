package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class ProfessionalOrderDetail {



        /**
         * orderItemData : {"code":"D1548784201901230001","createDate":"2019-01-23 11:09:29","account":"18761652968","createUser":"Sean1","businessRole":"店长","isOrderDerate":true,"maxAmount":"满1000减200","orderDerate":"200","isFreeGift":false,"isMoen":false,"pickUpStatus":"全部已提","paymentMethod":"银行卡","shoppingMethod":"顾客自提","productCount":2,"amountPayable":"18976","payAmount":"18776","couponDerate":"0","shopDerate":"0","installStatus":"未申请","shipPerson":"凯文","shipMobile":"18761652968","shipAddress":"江苏省南京市雨花台区雨花街道软件大道","comment":null,"orderSetMealList":[{"comboDescribe":"H-123+cs1003","comboName":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜","code":"moen(cood2)","price":"17776","count":1,"photo":"https://demo.waycomtech.com/image/detail/originalImg/490ba3e1-2c28-4995-84ef-80370b32fef5.jpg","productList":[{"sku":"H-123","name":"H-马桶","photo":"","price":"1000","count":1,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":null},{"sku":"cs1003","name":"测试商品  马桶","photo":"","price":"600","count":1,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":null}]}],"orderProductList":[{"sku":"cs1003","name":"测试商品  马桶","photo":"","price":"600","count":2,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":false}],"returnOrderStatus":"未退货"}
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
             * code : D1548784201901230001
             * createDate : 2019-01-23 11:09:29
             * account : 18761652968
             * createUser : Sean1
             * businessRole : 店长
             * isOrderDerate : true
             * maxAmount : 满1000减200
             * orderDerate : 200
             * isFreeGift : false
             * isMoen : false
             * pickUpStatus : 全部已提
             * paymentMethod : 银行卡
             * shoppingMethod : 顾客自提
             * productCount : 2
             * amountPayable : 18976
             * payAmount : 18776
             * couponDerate : 0
             * shopDerate : 0
             * installStatus : 未申请
             * shipPerson : 凯文
             * shipMobile : 18761652968
             * shipAddress : 江苏省南京市雨花台区雨花街道软件大道
             * comment : null
             * orderSetMealList : [{"comboDescribe":"H-123+cs1003","comboName":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜","code":"moen(cood2)","price":"17776","count":1,"photo":"https://demo.waycomtech.com/image/detail/originalImg/490ba3e1-2c28-4995-84ef-80370b32fef5.jpg","productList":[{"sku":"H-123","name":"H-马桶","photo":"","price":"1000","count":1,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":null},{"sku":"cs1003","name":"测试商品  马桶","photo":"","price":"600","count":1,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":null}]}]
             * orderProductList : [{"sku":"cs1003","name":"测试商品  马桶","photo":"","price":"600","count":2,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":false}]
             * returnOrderStatus : 未退货
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
            private Object comment;
            private String returnOrderStatus;
            private List<OrderSetMealListBean> orderSetMealList;
            private List<OrderProductListBean> orderProductList;

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

            public Object getComment() {
                return comment;
            }

            public void setComment(Object comment) {
                this.comment = comment;
            }

            public String getReturnOrderStatus() {
                return returnOrderStatus;
            }

            public void setReturnOrderStatus(String returnOrderStatus) {
                this.returnOrderStatus = returnOrderStatus;
            }

            public List<OrderSetMealListBean> getOrderSetMealList() {
                return orderSetMealList;
            }

            public void setOrderSetMealList(List<OrderSetMealListBean> orderSetMealList) {
                this.orderSetMealList = orderSetMealList;
            }

            public List<OrderProductListBean> getOrderProductList() {
                return orderProductList;
            }

            public void setOrderProductList(List<OrderProductListBean> orderProductList) {
                this.orderProductList = orderProductList;
            }

            public static class OrderSetMealListBean {
                /**
                 * comboDescribe : H-123+cs1003
                 * comboName : 摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜
                 * code : moen(cood2)
                 * price : 17776
                 * count : 1
                 * photo : https://demo.waycomtech.com/image/detail/originalImg/490ba3e1-2c28-4995-84ef-80370b32fef5.jpg
                 * productList : [{"sku":"H-123","name":"H-马桶","photo":"","price":"1000","count":1,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":null},{"sku":"cs1003","name":"测试商品  马桶","photo":"","price":"600","count":1,"codePu":null,"addPrice":null,"square":null,"returnCount":0,"isSpecial":null}]
                 */

                private String comboDescribe;
                private String comboName;
                private String code;
                private String price;
                private int count;
                private String photo;
                private List<ProductListBean> productList;

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
                     * sku : H-123
                     * name : H-马桶
                     * photo :
                     * price : 1000
                     * count : 1
                     * codePu : null
                     * addPrice : null
                     * square : null
                     * returnCount : 0
                     * isSpecial : null
                     */

                    private String sku;
                    private String name;
                    private String photo;
                    private String price;
                    private int count;
                    private Object codePu;
                    private Object addPrice;
                    private Object square;
                    private int returnCount;
                    private Object isSpecial;

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

                    public Object getCodePu() {
                        return codePu;
                    }

                    public void setCodePu(Object codePu) {
                        this.codePu = codePu;
                    }

                    public Object getAddPrice() {
                        return addPrice;
                    }

                    public void setAddPrice(Object addPrice) {
                        this.addPrice = addPrice;
                    }

                    public Object getSquare() {
                        return square;
                    }

                    public void setSquare(Object square) {
                        this.square = square;
                    }

                    public int getReturnCount() {
                        return returnCount;
                    }

                    public void setReturnCount(int returnCount) {
                        this.returnCount = returnCount;
                    }

                    public Object getIsSpecial() {
                        return isSpecial;
                    }

                    public void setIsSpecial(Object isSpecial) {
                        this.isSpecial = isSpecial;
                    }
                }
            }

            public static class OrderProductListBean {
                /**
                 * sku : cs1003
                 * name : 测试商品  马桶
                 * photo :
                 * price : 600
                 * count : 2
                 * codePu : null
                 * addPrice : null
                 * square : null
                 * returnCount : 0
                 * isSpecial : false
                 */

                private String sku;
                private String name;
                private String photo;
                private String price;
                private int count;
                private Object codePu;
                private Object addPrice;
                private Object square;
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

                public Object getCodePu() {
                    return codePu;
                }

                public void setCodePu(Object codePu) {
                    this.codePu = codePu;
                }

                public Object getAddPrice() {
                    return addPrice;
                }

                public void setAddPrice(Object addPrice) {
                    this.addPrice = addPrice;
                }

                public Object getSquare() {
                    return square;
                }

                public void setSquare(Object square) {
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
        }

}
