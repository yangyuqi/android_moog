package com.youzheng.zhejiang.robertmoog.Model.Home;

import java.io.Serializable;

public class AddressDatasBean implements Serializable{
    private String shipPerson ;
    private String shipMobile ;
    private String shipAddress ;
    private String addressId ;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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
}
