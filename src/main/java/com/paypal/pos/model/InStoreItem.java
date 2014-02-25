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
    public String getId() {
        return this.id;
    }

    @Override
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    @Override
    public double getDiscountPercent() {
        return this.discountPercent;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Currency getCurrency() {
        return currency != null ? currency : Currency.getInstance(Locale.US);
    }

    public int getNumberInStock() {
        return this.numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

}
