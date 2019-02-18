package com.youzheng.zhejiang.robertmoog.Model.login;

import java.io.Serializable;

public class RegisterBean implements Serializable{
    private String phone ;
    private long customerId ;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
