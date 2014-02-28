package com.paypal.pos.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by pderoxas on 2/24/14.
 */
public class InStoreItem implements Item{

    private String id;
    private Currency currency;
    private BigDecimal unitPrice;
    private String description;
    private int numberInStock;
    private double discountPercent;

    public InStoreItem(String id, BigDecimal unitPrice, String description, double discountPercent, int numberInStock){
        this.id = id;
        this.unitPrice = unitPrice;
        this.description = description;
        this.discountPercent = discountPercent;
        this.numberInStock = numberInStock;
    }

    @Override
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    @Override
    public Object getId() { return this.id; }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Currency getCurrency() {
        return currency != null ? currency : Currency.getInstance(Locale.US);
    }

    @Override
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
