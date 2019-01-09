package com.youzheng.zhejiang.robertmoog.Model.Home;

import java.io.Serializable;

public class CouponListBeanDetail implements Serializable{
    private String shopId ;
    private String shopName ;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
