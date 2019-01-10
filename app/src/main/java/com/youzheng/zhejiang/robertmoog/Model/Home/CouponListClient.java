package com.youzheng.zhejiang.robertmoog.Model.Home;

import java.util.ArrayList;
import java.util.List;

public class CouponListClient {
    private int totalSize ;
    private int totalPage ;
    private int pageSize ;
    private int pageNum ;
    private ArrayList<CouponListBean> couponList = new ArrayList<>();

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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public ArrayList<CouponListBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<CouponListBean> couponList) {
        this.couponList = couponList;
    }
}
