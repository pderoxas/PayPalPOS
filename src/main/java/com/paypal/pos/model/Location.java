package com.paypal.pos.model;

/**
 * Created by pderoxas on 2/27/14.
 * POJO for basic store details
 */
public class Location {
    private String registerNumber;
    private String storeName;
    private String storeNumber;
    private String addressLine1;
    private String addressLine2;
    private String phoneNumber;

    public Location(String registerNumber, String storeName, String storeNumber, String addressLine1, String addressLine2, String phoneNumber) {
        this.registerNumber = registerNumber;
        this.storeName = storeName;
        this.storeNumber = storeNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.phoneNumber = phoneNumber;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
