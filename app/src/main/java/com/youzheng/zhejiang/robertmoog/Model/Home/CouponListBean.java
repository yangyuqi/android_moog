package com.youzheng.zhejiang.robertmoog.Model.Home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CouponListBean implements Serializable{
    private String couponType ;
    private String payValue ;
    private String useCondition ;
    private String startDate ;
    private String endDate ;
    private String info ;
    private String couponCategory ;
    private String assetId ;
    private List<CouponListBeanDetail> shopList = new ArrayList<>();

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getPayValue() {
        return payValue;
    }

    public void setPayValue(String payValue) {
        this.payValue = payValue;
    }

    public String getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCouponCategory() {
        return couponCategory;
    }

    public void setCouponCategory(String couponCategory) {
        this.couponCategory = couponCategory;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public List<CouponListBeanDetail> getShopList() {
        return shopList;
    }

    public void setShopList(List<CouponListBeanDetail> shopList) {
        this.shopList = shopList;
    }
}
