package com.youzheng.zhejiang.robertmoog.Model.Home;

import java.util.List;

public class CouponPromo {


    /**
     * couponMoney : 5
     * couponCondition : 满200可用
     * couponType : 订单优惠
     * couponTypeId : ORDER
     * startTime : 2019/02/01 14:12:36
     * endTime : 2020/06/01 00:00:00
     * couponCategory :
     * couponShops : [{"shopName":"庆和摩恩专卖"}]
     */

    private int couponMoney;
    private String couponCondition;
    private String couponType;
    private String couponTypeId;
    private String startTime;
    private String endTime;
    private String couponCategory;
    private List<CouponShopsBean> couponShops;
    private boolean isExpress ;//是否展开
    private boolean isClick ;//是否选中

    public boolean isExpress() {
        return isExpress;
    }

    public void setExpress(boolean express) {
        isExpress = express;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(int couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(String couponCondition) {
        this.couponCondition = couponCondition;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponTypeId() {
        return couponTypeId;
    }

    public void setCouponTypeId(String couponTypeId) {
        this.couponTypeId = couponTypeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCouponCategory() {
        return couponCategory;
    }

    public void setCouponCategory(String couponCategory) {
        this.couponCategory = couponCategory;
    }

    public List<CouponShopsBean> getCouponShops() {
        return couponShops;
    }

    public void setCouponShops(List<CouponShopsBean> couponShops) {
        this.couponShops = couponShops;
    }

    public static class CouponShopsBean {
        /**
         * shopName : 庆和摩恩专卖
         */

        private String shopName;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
}
