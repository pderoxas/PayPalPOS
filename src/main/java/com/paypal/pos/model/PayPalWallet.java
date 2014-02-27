package com.paypal.pos.model;

/**
 * Created by pderoxas on 2/26/14.
 */
public class PayPalWallet {
    private String paycode;
    private String customerName;
    private String loyaltyNumber;

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLoyaltyNumber() {
        return loyaltyNumber;
    }

    public void setLoyaltyNumber(String loyaltyNumber) {
        this.loyaltyNumber = loyaltyNumber;
    }
}
