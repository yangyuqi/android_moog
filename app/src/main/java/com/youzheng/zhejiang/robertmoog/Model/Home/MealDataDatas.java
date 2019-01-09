package com.youzheng.zhejiang.robertmoog.Model.Home;

import java.util.List;

public class MealDataDatas {
    private Integer comId ;
    private String comboDescribe ;
    private String comboName ;
    private String comboCode ;
    private List<HomeListDataBeanBean> list ;
    private List<ProductListData> productList ;

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
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

    public String getComboCode() {
        return comboCode;
    }

    public void setComboCode(String comboCode) {
        this.comboCode = comboCode;
    }

    public List<HomeListDataBeanBean> getList() {
        return list;
    }

    public void setList(List<HomeListDataBeanBean> list) {
        this.list = list;
    }

    public List<ProductListData> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListData> productList) {
        this.productList = productList;
    }
}
