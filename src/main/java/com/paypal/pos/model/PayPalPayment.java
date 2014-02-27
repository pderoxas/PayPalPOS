package com.paypal.pos.model;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 */
public class PayPalPayment extends Payment {
    private PayPalWallet paypalWallet;

    public PayPalPayment(PayPalWallet paypalWallet) {
        this.paypalWallet = paypalWallet;
    }
}
