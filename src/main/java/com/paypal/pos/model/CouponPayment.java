package com.paypal.pos.model;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 */
public class CouponPayment extends Payment{
    private BigDecimal remainingBalance;

    public CouponPayment(BigDecimal changeAmount) {
        this.remainingBalance = changeAmount;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
