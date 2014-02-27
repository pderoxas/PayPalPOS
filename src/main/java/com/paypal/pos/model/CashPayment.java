package com.paypal.pos.model;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 */
public class CashPayment extends Payment{
    private BigDecimal changeAmount;

    public CashPayment(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }
}
