package com.youzheng.zhejiang.robertmoog.Model.Home;

import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ComboPromoBean {
    private int comboId ;
    private String comboCode ;
    private String comboName ;
    private String comboImageUrl ;
    private double comboPrice ;
    private String comboDes ;
    private List<ProductsBean> products = new ArrayList<>();
    private String orderPromo ;
    private String couponPromo ;
    private boolean isExpress = false ;

    public boolean isExpress() {
        return isExpress;
    }

    public void setExpress(boolean express) {
        isExpress = express;
    }

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
    }

    public String getComboCode() {
        return comboCode;
    }

    public void setComboCode(String comboCode) {
        this.comboCode = comboCode;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public String getComboImageUrl() {
        return comboImageUrl;
    }

    public void setComboImageUrl(String comboImageUrl) {
        this.comboImageUrl = comboImageUrl;
    }

    public double getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(double comboPrice) {
        this.comboPrice = comboPrice;
    }

    public String getComboDes() {
        return comboDes;
    }

    public void setComboDes(String comboDes) {
        this.comboDes = comboDes;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public String getOrderPromo() {
        return orderPromo;
    }

    public void setOrderPromo(String orderPromo) {
        this.orderPromo = orderPromo;
    }

    public String getCouponPromo() {
        return couponPromo;
    }

    public void setCouponPromo(String couponPromo) {
        this.couponPromo = couponPromo;
    }
}
