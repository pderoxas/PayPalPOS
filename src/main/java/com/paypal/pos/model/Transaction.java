package com.paypal.pos.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by pderoxas on 2/24/14.
 */
public class Transaction {
    private BigDecimal amount;
    private Currency currency;

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrencyType() {
        return currency != null ? currency : Currency.getInstance(Locale.US);
    }

    public void setCurrencyType(Locale locale) {
        this.currency = Currency.getInstance(locale != null ? locale : Locale.US);
    }
}
