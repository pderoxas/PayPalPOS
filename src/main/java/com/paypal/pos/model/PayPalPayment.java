package com.paypal.pos.model;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 */
public class PayPalPayment extends Payment {
    private PayPalWallet paypalWallet;
    private String authorizationCode;

    public PayPalPayment(PayPalWallet paypalWallet) {
        this.paypalWallet = paypalWallet;
    }

    public PayPalWallet getPaypalWallet() {
        return paypalWallet;
    }

    public void setPaypalWallet(PayPalWallet paypalWallet) {
        this.paypalWallet = paypalWallet;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
