package com.paypal.pos.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by pderoxas on 2/26/14.
 */
public abstract class Payment {

    private BigDecimal TotalAmountPaid;
    private BigDecimal totalAmountDue;
    private BigDecimal subTotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private double discountPercentage;
    private Currency currency;

    protected Payment() {
    }

    public BigDecimal getTotalAmountPaid() {
        return TotalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        TotalAmountPaid = totalAmountPaid;
    }

    public BigDecimal getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(BigDecimal totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(BigDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
